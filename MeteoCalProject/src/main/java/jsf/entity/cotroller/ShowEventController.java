/*
 * Bean that manages the show event.
 */
package jsf.entity.cotroller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jsf.entity.Badconditions;
import jsf.entity.Event;
import jsf.entity.Notification;
import jsf.entity.Participant;
import jsf.entity.User;
import jsf.entity.Weather;
import jsf.entity.facade.BadconditionsFacade;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.EventFacade;
import jsf.entity.facade.NotificationFacade;
import jsf.entity.facade.ParticipantFacade;
import jsf.entity.facade.UserFacade;
import jsf.entity.facade.WeatherFacade;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

/**
 *
 * @author claudio
 */
@ManagedBean( name= "showEventController" , eager = true)
@SessionScoped
public class ShowEventController implements Serializable{

    private LineChartModel multiAxisModel;
    Event selectedEvent;
    
    @EJB
    ParticipantFacade participantFacade;
    @EJB
    WeatherFacade weatherFacade;
    @EJB
    BadconditionsFacade badConditionsFacade;
    @EJB
    NotificationFacade notificationFacade;
    @EJB
    UserFacade userFacade;
    @EJB
    EventFacade eventFacade;
    @EJB
    CalendarFacade calendarFacade;
    
    private List<Participant> participants;
    private List<Participant> invited;
    
    private Weather weather;

    private Badconditions badConditions;
    private User creator;
    private boolean IsWeatherNull;

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public LineChartModel getMultiAxisModel() {
        return multiAxisModel;
    }

    public void setMultiAxisModel(LineChartModel multiAxisModel) {
        this.multiAxisModel = multiAxisModel;
    }

    public boolean isIsWeatherNull() {
        return IsWeatherNull;
    }

    public void setIsWeatherNull(boolean IsWeatherNull) {
        this.IsWeatherNull = IsWeatherNull;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
    
    public float getWind() {
        return this.weather.getWind();
    }

    public float getTemperature() {
        return this.weather.getTemperature();
    }

    public float getHumidity() {
        return this.weather.getHumidity();
    }

    public float getPrecipitations() {
        return this.weather.getPrecipitations();
    }
    
    public String getCity() {
        return this.weather.getCity();
    }

    public String getPrecipitationType() {
        return this.weather.getPrecipitationType();
    }

    public float getClouds() {
        return this.weather.getClouds();
    }
    
    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
    
    public Badconditions getBadConditions() {
        return badConditions;
    }

    public void setBadConditions(Badconditions badConditions) {
        this.badConditions = badConditions;
    }
    
    public Event getSelectedEvent() {
        return selectedEvent;
    }
    
    /**
     * @return the date of the event formatted.
     */
    public String getDate() {
        Date time = this.selectedEvent.getDate();
        String[] date = time.toString().split(" ");
        return date[0] + " " + date[1] + " " + date[2] + " " + date[5];
    }

    /**
     * @return  starting time of the event formatted.
     */
    public String getStartingTime() {
        DateTime date = new DateTime(this.selectedEvent.getStartingTime());
        if (date.getMinuteOfHour() == 0) {
            return date.getHourOfDay() + ":00:" + date.getSecondOfMinute();
        }
        return date.getHourOfDay() + ":" + date.getMinuteOfHour() + ":" + date.getSecondOfMinute();
    }

     /**
     * @return  ending time of the event formatted.
     */
    public String getEndingTime() {
        DateTime date = new DateTime(this.selectedEvent.getEndingTime());
        if (date.getMinuteOfHour() == 0) {
            return date.getHourOfDay() + ":00:" + date.getSecondOfMinute();
        }
        return date.getHourOfDay() + ":" + date.getMinuteOfHour() + ":" + date.getSecondOfMinute();
    }
    
    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
        int id = selectedEvent.getId();
        this.participants =  this.participantFacade.searchByEvent(id);
        setCreator();
    }
    
    public void setCreator() {
        for (Participant u : this.participants) { 
           if (u.getOrganiser().equals("YES")) {
                this.creator = u.getUser1();
            }
        }
    }
    
    /**
     * Show the participants in the dialog.
     */
    public void loadParticipants() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('participants').show();");
    }
    
    /**
     * create the chart. if no weather is assigned to the event, isWeatherNull is set to false.
     */
    public void initChart() {
        multiAxisModel = new LineChartModel();
        if (this.selectedEvent.getWeatherID() == null) {
            this.IsWeatherNull = true;
            return;
        }
        this.weather = weatherFacade.searchById(this.selectedEvent.getWeatherID().getId());
        
        //set precipitations chart
        BarChartSeries precipitations = new BarChartSeries();
        precipitations.setLabel("Precipitations");
        precipitations.set(weather.getPrecipitationType(), weather.getPrecipitations());

        //set temperature chart
        LineChartSeries Temperature = new LineChartSeries();
        Temperature.setLabel("Temperature");
        Temperature.setXaxis(AxisType.X2);
        Temperature.setYaxis(AxisType.Y2);
        Temperature.set("min T", weather.getMinTemperature());
        Temperature.set("day T", weather.getTemperature());
        Temperature.set("max T", weather.getMaxTemperature());
        

 
        multiAxisModel.addSeries(precipitations);
        multiAxisModel.addSeries(Temperature);
         
        multiAxisModel.setMouseoverHighlight(false);
         
        multiAxisModel.getAxes().put(AxisType.X, new CategoryAxis("Precipitation type"));
        multiAxisModel.getAxes().put(AxisType.X2, new CategoryAxis("Temperature"));
         
        Axis yAxis = multiAxisModel.getAxis(AxisType.Y);
        yAxis.setLabel("Mm");
        yAxis.setMin(0);
        yAxis.setMax(30);
                 
        Axis y2Axis = new LinearAxis("C°");
        y2Axis.setMin(-20);
        y2Axis.setMax(35);
         
        multiAxisModel.getAxes().put(AxisType.Y2, y2Axis);
        
        this.IsWeatherNull = false;
    }
    
    public boolean isInvited() {
        User user = userFacade.getLoggedUser();
        
        for (Participant p : this.participants) {
            if (user.equals(p.getUser1())) {
                if (user.equals(this.creator)) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 
     * @return the src of the image to be shown due to the weather type
     */
    public String getWeatherImage() {
        if (weather.getPrecipitations() > 0) {
            if (weather.getPrecipitationType().equals("SNOW")) {
                return"Images/snow.png";
            }
            
            if (weather.getPrecipitations() > 10) {
                return"Images/rainHard.png";
            } else {
                return"Images/rainLow.png";
            }
        }
        
        if (weather.getClouds() > 50) {
            return"Images/cloudyHard.png";
        } 
        
        if (weather.getClouds() > 0) {
            return"Images/cloudyLow.png";
        }
        return"Images/sun.png";
        
    }
  
    
    public String getResponse() {
        User user = this.userFacade.getLoggedUser();
        for (Participant u : this.participants) {
            if (u.getUser1().equals(user)) {
                return u.getParticipant();
            }
        }
        return "ERROR";
    }
    
    /**
     * set the response to the invitation, update the xhtml element, and create a new response notification.
     * @param response made by the user
     */
    public void setInvited(String response) {
        User user = this.userFacade.getLoggedUser();
        
        
        if( (response.equals("YES")) && (eventFacade.dateAndTimeInTheMiddleCreate(selectedEvent.getDate(),selectedEvent.getDate(),selectedEvent.getStartingTime(),selectedEvent.getEndingTime(),calendarFacade.searchByUser(userFacade.getLoggedUser()).getId(),userFacade.getLoggedUser().getUsername()) ) ){         
            RequestContext.getCurrentInstance().execute("PF('EventInTheMiddle Error').show();");
            return;
        }
        
         for (Participant u : this.participants) {
            if (u.getUser1().equals(user)) {
                u.setParticipant(response);
                this.participantFacade.edit(u);
            }
        }
        
        sendNotification(response, user);
    }
    
    /**
     * Create a new notification of response type
     * @param response made by the user.
     * @param user that has response to the event invitation
     */
    public void sendNotification(String response, User user) {
        Notification notification = new Notification();
        notification.setEventID(selectedEvent);
        notification.setType("RESPONSE");
        notification.setVisualized("NO");
        String description = "";
        
        if (response.equals("YES")) {
           description += "The user " + user.getUsername() + " has accepted your invitation to the event " + selectedEvent.getTitle();  
        } else {
            description += "The user " + user.getUsername() + " has refused your invitation to the event " + selectedEvent.getTitle();
        }
        notification.setDescription(description);
        notification.setUser(this.creator);
        
        notification.setId(null);
        this.notificationFacade.create(notification);
    }
    
    /**
     * check if the selected event has bad conditions
     * @return "none" if the selected event has not bad conditions otherwise "display"
     */
    public String hasBadCondition() {
        this.badConditions = badConditionsFacade.searchByEvent(selectedEvent);
        if (badConditions == null) {
            return "none";
        } else {
            return "display";
        }
    }
    
}
