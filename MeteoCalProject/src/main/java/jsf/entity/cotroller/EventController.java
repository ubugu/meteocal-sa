/*
 * Bean that controls the creation and update of an event.
 */
package jsf.entity.cotroller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jsf.entity.Badconditions;
import jsf.entity.Event;
import jsf.entity.facade.BadconditionsFacade;
import jsf.entity.facade.EventFacade;
import java.util.Date; 
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import jsf.entity.Notification;
import jsf.entity.Participant;
import jsf.entity.ParticipantPK;
import jsf.entity.User;
import jsf.entity.Weather;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.NotificationFacade;
import jsf.entity.facade.ParticipantFacade;
import jsf.entity.facade.UserFacade;
import jsf.entity.facade.WeatherFacade;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Days;
import org.json.JSONException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Walter
 */
@ManagedBean(name = "EventController", eager = true)
@SessionScoped
public class EventController implements Serializable {

    private Event event = new Event();

    private Badconditions badconditions = new Badconditions();

    private Notification notification = new Notification();

    private Participant participant = new Participant();

    @EJB
    EventFacade eventFacade;
    @EJB
    BadconditionsFacade badconditionsFacade;
    @EJB
    UserFacade userFacade;
    @EJB
    CalendarFacade calendarFacade;
    @EJB
    NotificationFacade notificationFacade;
    @EJB
    ParticipantFacade participantFacade;
    @EJB
    WeatherFacade weatherFacade;

    // variables useful to set the correct events in the database
    private Integer oldEventID;
    
    private Date endate;

    private Date startdate;

    private Date untillDate;

    private String invitations;

    private String repeats = "no";

    private Boolean bad = false;

    private Boolean temp = false;

    private Boolean prec = false;

    private Boolean InviteSelect = false;

    private String[] invitatedUsers;

    private String rejectedUsers = "";

    private String style = "none";

    private Boolean edit = false;

    private Boolean editAddingBad = false;
    
    private Boolean first = false;

    private List<Participant> oldParticipants = null;
    // end variables not belonging to database 

    /**
     * method that will be called in order to set to the Edit Mode the class
     * addEvent it will check and set the value of the event that the user want
     * to change in order to show the precompiled field, this avoid us to create
     * an updateeventcontroller and an updateevent page
     *
     * @param id of the event to update, is obtained by the schedule controller
     * @return redirect to the addeventpage
     */
    public String changeEvent(int id) {
        
        event = eventFacade.find(id);
        badconditions = badconditionsFacade.searchByEvent(event);
        
        this.oldEventID = event.getId();

        setEditBadConditions();

        resetUpdateFields();

        return "addEvent?faces-redirect=true";
    }

    
    /**
     * UPDATE FUNCTION 
     * method that will set some field based on the value of the previous bad conditions
     */
    private void setEditBadConditions(){
        
        if (badconditions != null) {

            setBad(true);
            setStyle("block");

            if (badconditions.getPrecipitations() != null) {
                setPrec(true);
            }
            if (badconditions.getTemperature() != null) {
                setTemp(true);
            }

            setEditAddingBad(false);

        } else {
            badconditions = new Badconditions();
            setEditAddingBad(true);
            setBad(false);
            setPrec(false);
            setTemp(false);
        }
        
    }
    
    /**
     * EDIT FUNCTION
     * method that will reset some fields of the add event page
     */
    private void resetUpdateFields(){
        
        setInvitations("");
        setInvitatedUsers(new String[0]);
        setInviteSelect(false);
        setRejectedUsers("");
        setEdit(true);
        setEndate(event.getDate());
        setUntillDate(null);
        setRepeats("NO");    
    }
    
    /**
     * method that will be called in order to create new event and badconditions
     *
     * @return redirect to the addeventpage
     */
    public String newEvent() {

        event = new Event();
        badconditions = new Badconditions();

        resetNewFields();

        return "addEvent?faces-redirect=true";
    }
    
    /**
     * NEW EVENT
     * method that will reset the fields in order to create a new event
     */
    private void resetNewFields(){

        setPrec(false);
        setTemp(false);
        setBad(false);
        setStyle("none");
        setEdit(false);
        setEndate(null);
        setInvitations("");
        setInviteSelect(false);
        setRejectedUsers("");
        setUntillDate(null);
        setRepeats("no");
        setEditAddingBad(false);
        setTemp(false);
        setPrec(false);
    }

    /**
     * method that will delete (with dependencies) the event specified.
     * @param event, that will be deleted
     */
    public void delete(Event event) {
        try {
            int id = event.getId();

            //Delete the participant 
            List<Participant> participants = this.participantFacade.searchByEvent(id);
            for (Participant p : participants) {
                if (p.getParticipant().equals("YES") && p.getOrganiser().equals("NO")) {
                    User user = p.getUser1();
                    Notification newNotification = new Notification();
                    // newNotification.setId(this.notificationFacade.getMaxNotificationID() + 1);
                    newNotification.setDescription("The event " + event.getTitle() + " has been deleted.");
                    newNotification.setEventID(null);
                    newNotification.setType("DELETE");
                    newNotification.setUser(user);
                    newNotification.setVisualized("NO");

                    newNotification.setId(null);
                    this.notificationFacade.create(newNotification);
                }
                this.participantFacade.remove(p);

            }

            //Remove notifications about that event
            List<Notification> notifications = this.notificationFacade.searchByEventID(id);
            for (Notification n : notifications) {
                this.notificationFacade.remove(n);
            }
            
             //remove bad conditions if added
            Badconditions conditions = this.badconditionsFacade.searchByEvent(event);
            if (conditions != null) {
                this.badconditionsFacade.remove(conditions);
            }

            //remove the event
            this.eventFacade.remove(event);

            //remove the weather if no other events has link to it
            Weather weatherEvent = event.getWeatherID();

            if (weatherEvent == null) {
                return;
            }

            for (Event e : eventFacade.findAll()) {
                if (e.getWeatherID().equals(weatherEvent)) {
                    return;
                }
            }

            weatherFacade.remove(weatherEvent);
            return;
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Create a weather associated to the event. If a weather already exists
     * with the same city and date of the event, that weather is chosen.
     * Otherwise a new event will be provided throught the weather api.
     *
     * @return the weather created
     */
    private Weather createWeather() {
        //if no city inserted return null weather
        if (event.getCity() == null) {
            return null;
        }

        RequestContext requestContext = RequestContext.getCurrentInstance();
        OpenWeatherMap owm = new OpenWeatherMap("");

        DailyForecast forecast = null;

        DateTime currentDate = new DateTime();
        DateTime targetDate = new DateTime(event.getDate());
        Integer dayForecast = targetDate.getDayOfYear() - currentDate.getDayOfYear() + 1;

        if (dayForecast > 13 || dayForecast < 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "The weather was not inserted for one or more of the events created"));

            return null;
        }

        //check if meteo for inserted date and city already exixst in the database
        Weather oldWeather = this.weatherFacade.searchByCityAndDate(event.getCity(), event.getDate());
        if (oldWeather != null) {
            return oldWeather;
        }

        //creates new weather
        Weather weather = new Weather();
        DailyForecast forescast;
        try {
            forecast = owm.dailyForecastByCityName(event.getCity(), dayForecast.byteValue());
            //weather.setId(this.weatherFacade.getMaxNotificationID() + 1);
            if (forecast.getCityInstance().getCityName().equals("")) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "The weather was not inserted for one or more of the events created"));

                return null;
            }
            weather.setCity(forecast.getCityInstance().getCityName());
            weather.setClouds(forecast.getForecastInstance(dayForecast - 1).getPercentageOfClouds());
            weather.setDate(forecast.getForecastInstance(dayForecast - 1).getDateTime());
            weather.setHumidity(forecast.getForecastInstance(dayForecast - 1).getHumidity());
            int rain = (int) forecast.getForecastInstance(dayForecast - 1).getRain();
            int snow = (int) forecast.getForecastInstance(dayForecast - 1).getSnow();
            if (rain > snow) {
                weather.setPrecipitations(rain);
                weather.setPrecipitationType("RAIN");
            } else {
                weather.setPrecipitations(snow);
                weather.setPrecipitationType("SNOW");
            }

            
            if (rain == 0 && snow == 0) {
                weather.setPrecipitationType("NONE");
            }
            
            weather.setPressure(forecast.getForecastInstance(dayForecast - 1).getPressure());
            weather.setTemperature(forecast.getForecastInstance(dayForecast - 1).getTemperatureInstance().getDayTemperature());
            weather.setMaxTemperature(forecast.getForecastInstance(dayForecast - 1).getTemperatureInstance().getMaximumTemperature());
            weather.setMinTemperature(forecast.getForecastInstance(dayForecast - 1).getTemperatureInstance().getMinimumTemperature());
            weather.setWind(forecast.getForecastInstance(dayForecast - 1).getWindSpeed());
            weather.setId(null);
            this.weatherFacade.create(weather);
            return weather;
        } catch (IOException | JSONException ex) {
            System.out.println("Error while contacting the weather website");
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            System.out.println("No weather found for the city searched.");
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "The weather was not inserted for one or more of the events created"));
        return null;
    }
    
    /**
     * method that will start the creation of the event, it will control the
     * input fields
     *
     * @return always a redirection to the current page, showing error or
     * creating events
     */
    public String controlDataCreation() {       
        
        Boolean error;
        RequestContext requestContext = RequestContext.getCurrentInstance();

        // here we control if there are input errors
        error = checkInsertionErrors(requestContext);

        /*if there are simplier error we will return to the event creation page 
         showing the errors to avoid useless further computation */
        if (error) {
            requestContext.execute("PF('creation').hide();");
            return "";
        }

        if(!edit){
            resolvedBug();
        }
                
        /*creationEvent will return the redirection to the page
         if there are some error regarding a wrong insertion of the data,
         like repeated event or event that last more than 1 day we will
         return to the event page showing the errors*/
        String ret = prepareCreateEvent();

        if (ret.equals("")) {
           
            requestContext.execute("PF('creation').hide();");
            requestContext.execute("PF('complete').show();");
        }

        return "";
    }

    /**
     * method that we want to eliminate but is useful to delete a bug from the system
     */
    private void resolvedBug(){
        //EVENT CREATION AND DELETING TO FIX BUX
            String oldInvitations = invitations;
            Boolean oldInvite = this.InviteSelect;
            Date oldStart =  this.startdate;
            Date oldEnd =  this.endate;
            Boolean oldBad =  this.bad;
            String oldRepeat = this.repeats;
            Date oldUntillDate = this.untillDate;
            
            this.InviteSelect = false;
            invitations = "";
            startdate = new Date(1);
            endate = new Date(2);
            bad = false;
            repeats = "no";
            untillDate = null;
            
            prepareCreateEvent();
 
            delete(event);
            first = true;
            
            this.InviteSelect = oldInvite;
            invitations = oldInvitations;
            this.startdate = oldStart;
            this.endate = oldEnd;
            this.bad = oldBad;
            this.repeats = oldRepeat;
            this.untillDate = oldUntillDate;
        //END OF BUG FIX CODE
    }
    
    /**
     * method that will control the input from the user and if the data are
     * feasible or not it will show the errors in the dialog executing them.
     *
     * @param requestContext Incapsula le informazioni sulla richiesta HTTP
     * corrente,corrispondente a quella dello user loggato
     * @return true if there are error, false otherwise
     */
    private Boolean checkInsertionErrors(RequestContext requestContext) {

        Boolean error = false;

        //control if there are event in the middle, if we are in the updateEvent case here we don't have to consider the current event in the query
        if (getEdit()) {
            if (eventFacade.dateAndTimeInTheMiddle(event.getDate(), getEndate(), event.getStartingTime(), event.getEndingTime(), calendarFacade.searchByUser(userFacade.getLoggedUser()).getId(), userFacade.getLoggedUser().getUsername(), event.getId())) {
                requestContext.execute("PF('DateInTheMiddle Error').show();");
                error = true;
            }
        } else {
            if (eventFacade.dateAndTimeInTheMiddleCreate(event.getDate(), getEndate(), event.getStartingTime(), event.getEndingTime(), calendarFacade.searchByUser(userFacade.getLoggedUser()).getId(), userFacade.getLoggedUser().getUsername())) {
                requestContext.execute("PF('DateInTheMiddle Error').show();");
                error = true;
            }
        }

        //control if the starting date is before today
        if (DateTimeComparator.getDateOnlyInstance().compare(new DateTime(event.getDate()), new DateTime()) < 0) {
            requestContext.execute("PF('NoPast Error').show();");
            error = true;
        }

        //control if the event date is today but the time is in the past
        if ((DateTimeComparator.getTimeOnlyInstance().compare(new DateTime(event.getStartingTime()), new DateTime()) < 0) && (DateTimeComparator.getDateOnlyInstance().compare(new DateTime(), new DateTime(event.getDate())) == 0)) {
            requestContext.execute("PF('StartTime Error').show();");
            error = true;
        }

        //control if the enddate is before the starting date
        if (DateTimeComparator.getDateOnlyInstance().compare(new DateTime(getEndate()), new DateTime(event.getDate())) < 0) {
            requestContext.execute("PF('EndDate Error').show();");
            error = true;
        }

        //control if the endtime is before the starting time
        if ((DateTimeComparator.getTimeOnlyInstance().compare(new DateTime(event.getEndingTime()), new DateTime(event.getStartingTime())) < 0) && (DateTimeComparator.getDateOnlyInstance().compare(new DateTime(getEndate()), new DateTime(event.getDate())) == 0)) {
            requestContext.execute("PF('EndTime Error').show();");
            error = true;
        }

        //control if the untilldate is before the event date in the case of repetitions
        if ((!getRepeats().equals("no")) && (getUntillDate() != null) && (DateTimeComparator.getDateOnlyInstance().compare(new DateTime(getUntillDate()), new DateTime(event.getDate())) < 0)) {
            requestContext.execute("PF('EndUntillDate Error').show();");
            error = true;
        }

        //in order to avoid further advanced features for now we don't allow a repetition for the events that last more than 1 day
        if ((!getRepeats().equals("no")) && (DateTimeComparator.getDateOnlyInstance().compare(new DateTime(getEndate()), new DateTime(event.getDate())) != 0)) {
            requestContext.execute("PF('Repeat Error').show();");
            error = true;
        }

        //control if the user select a repetition without naming an untilldate
        if ((!getRepeats().equals("no")) && (getUntillDate() == null)) {
            requestContext.execute("PF('Untill Error').show();");
            error = true;
        }

        /*control if the user select the invite field without naming at least 1 username 
         or if the username doesn't exist 
         it will return false also if the field is disabled
         */
        if (getInviteSelect()) {
            if (!searchInvited()) {
                requestContext.execute("PF('Invite Error').show();");
                error = true;
            }
        }

        return error;
    }

    /**
     * method that will control if the users invited by the organiser exists or
     * not;
     *
     * @return if some of they does not exist a false value is returned
     */
    private Boolean searchInvited() {

        Boolean result = true;

        //false if there are no invited people in the textarea
        if (getInvitations() == null || getInvitations().equals("")) {
            return false;
        }
 
        setInvitatedUsers(getInvitations().split(";"));
         
        for (String invitatedUser : getInvitatedUsers()) {
            if ((invitatedUser != null) && (!invitatedUser.equals(""))) {
                if (userFacade.searchForUser(invitatedUser) == null) {
                    setRejectedUsers(getRejectedUsers() + invitatedUser + " ;");
                    setInvitations(getInvitations().replace(invitatedUser,""));
                    result = false;
                }
            }
        }

        return result;
    }

    /**
     * method that will control the creation of the events and then it will use
     * the facades to create the records in the database
     *
     * @return the redirection link to the jsf page
     */
    private String prepareCreateEvent() {

        //we can set here the calendar for the event
        event.setCalendar(calendarFacade.searchByUser(userFacade.getLoggedUser()));

        if (getRepeats().equals("no")) {
            return normalCreation();
        } else {
            return repeatingCreation();
        }

    }

    /**
     * creation in case of repetitions
     *
     * @return the redirection link to a jsf page
     */
    private String repeatingCreation() {

        Date nextDate = event.getDate();

        Date controlDate = nextDate;
        RequestContext requestContext = RequestContext.getCurrentInstance();

        //control loop, if there are only one event in the middle of the other an error message is sent to the user
        while (nextDate.compareTo(getUntillDate()) < 0) {

            if (edit) {
                if (eventFacade.dateAndTimeInTheMiddle(event.getDate(), getEndate(), event.getStartingTime(), event.getEndingTime(), calendarFacade.searchByUser(userFacade.getLoggedUser()).getId(), userFacade.getLoggedUser().getUsername(), event.getId())) {
                    requestContext.execute("PF('DateInTheMiddleRepeat Error').show();");
                    return "error";
                }
            } else {
                if (eventFacade.dateAndTimeInTheMiddleCreate(event.getDate(), getEndate(), event.getStartingTime(), event.getEndingTime(), calendarFacade.searchByUser(userFacade.getLoggedUser()).getId(), userFacade.getLoggedUser().getUsername())) {
                    requestContext.execute("PF('DateInTheMiddleRepeat Error').show();");
                    return "error";
                }
            }

            //repetition
            nextDate = nextRepetitionDate(nextDate);

        }

        //reset to the initial date
        nextDate = controlDate;

        //creation loop
        Weather weather;
        while (nextDate.compareTo(getUntillDate()) < 0) {

            /*
             if(!getEdit()){
             event.setId( eventFacade.getMaxEventID() + 1);
             }
             */
            //create wheather for the event
            weather = createWeather();
            event.setWeatherID(weather);

            //event creation
            managerOfEvents();

            //invite & notify if there are invited
            if (getInviteSelect() || edit) {
                //create notification
                prepareCreateNotification();
            }

            //set the owner as a participant if it is not an update
            if (!edit) {
                setOwnerParticipant();
            }
            
            if (getInviteSelect()) {
                //create participant
                prepareCreateParticipant();
            }
         
            //badcondition creation if they are set
            prepareCreateBadConditions();

            //repetition & set of the new date
            nextDate = nextRepetitionDate(nextDate);
            event.setDate(nextDate);

        }

        return "";
    }
    
    /**
     * method that will calculate the nextdate based on the repeats user's input
     *
     * @param currentDate, last date of the added event
     * @return newDate, the nextDate
     */
    private Date nextRepetitionDate(Date currentDate) {

        Date newDate = currentDate;

        switch (getRepeats()) {
            case "everyday": {
                newDate = (new DateTime(newDate).plusDays(1).toDate());
                break;
            }
            case "everyweek": {
                newDate = (new DateTime(newDate).plusWeeks(1).toDate());
                break;
            }
            case "everymonth": {
                int numberOfAddingMonths = 1;
                int oldDayOfMonth = new DateTime(newDate).getDayOfMonth();
                while(true){                   
                    if(oldDayOfMonth == new DateTime(newDate).plusMonths(numberOfAddingMonths).getDayOfMonth()){
                        newDate = (new DateTime(newDate).plusMonths(numberOfAddingMonths).toDate());
                        break;
                    }else{
                       numberOfAddingMonths++; 
                    }                   
                }
                break;
            }
            case "everyyear": {
                int numberOfAddingYears = 1;
                int oldDayOfMonth = new DateTime(newDate).getDayOfMonth();
                while(true){                   
                    if(oldDayOfMonth == new DateTime(newDate).plusYears(numberOfAddingYears).getDayOfMonth()){
                        newDate = (new DateTime(newDate).plusYears(numberOfAddingYears).toDate());
                        break;
                    }else{
                        if(numberOfAddingYears ==1){
                           numberOfAddingYears=4; 
                        }else{
                            numberOfAddingYears += 4;
                        }
                    }                   
                }
                break;
            }
        }

        return newDate;
    }

    /**
     * creation in case of no repetitions if the event last for more than 1 day,
     * we save the ending time in a variable after having calculate the days in
     * which we have to repeat the event we set the starting time to 00:00 and
     * the ending time to 23:59 unless the days is the first or the last
     */
    private String normalCreation() {
        int days = 0;
        Date endingTime;
        Date startingTime;
        
        //here we find the difference in days between the enddate and the startingdate
        if (getEndate().compareTo(event.getDate()) > 0) {
            days = Days.daysBetween(new DateTime(event.getDate()), new DateTime(getEndate())).getDays();
        }

        Date controlDate = event.getDate();
        startingTime = event.getStartingTime();
        endingTime = event.getEndingTime();
        RequestContext requestContext = RequestContext.getCurrentInstance();
                
        //check error loop
        for (int i = 0; i <= days; i++) {

            //if the cycle is repeated then the successive starting date must be set to 00:00
            // we have to do this only one time in order to optimize the code.
            if (i != 0) {
                event.setStartingTime(new Date(Time.valueOf("00:00:00").getTime()));
            }
            if (i + 1 > days && days > 0) {
                event.setEndingTime(endingTime);
            } else {
                if (days != 0) {
                    event.setEndingTime(new Date(Time.valueOf("23:59:59").getTime()));
                }
            }

            //control errors
            if (edit) {
                if (eventFacade.dateAndTimeInTheMiddle(event.getDate(), getEndate(), event.getStartingTime(), event.getEndingTime(), calendarFacade.searchByUser(userFacade.getLoggedUser()).getId(), userFacade.getLoggedUser().getUsername(), event.getId())) {
                    requestContext.execute("PF('DateInTheMiddle Error').show();");
                    return "error";
                }
            } else {
                if (eventFacade.dateAndTimeInTheMiddleCreate(event.getDate(), getEndate(), event.getStartingTime(), event.getEndingTime(), calendarFacade.searchByUser(userFacade.getLoggedUser()).getId(), userFacade.getLoggedUser().getUsername())) {
                    requestContext.execute("PF('DateInTheMiddle Error').show();");
                    return "error";
                }
            }

            //add one day and repeat
            if (days > 0) {
                DateTime datetime = new DateTime(event.getDate());
                datetime = datetime.plusDays(1);
                event.setDate(datetime.toDate());
            }
        }

        event.setDate(controlDate);
        event.setStartingTime(startingTime);
        event.setEndingTime(endingTime);
                
        //creation loop
        for (int i = 0; i <= days; i++) {

            /*
             if(!edit){
             event.setId( eventFacade.getMaxEventID() + 1);
             }
             */
            //if the cycle is repeated then the successive starting date must be set to 00:00
            // we have to do this only one time in order to optimize the code.
            if (i == 1) {
                event.setStartingTime(new Date(Time.valueOf("00:00:00").getTime()));
            }
            if (i + 1 > days && days > 0) {
                event.setEndingTime(endingTime);
            } else {
                if (days != 0) {
                    event.setEndingTime(new Date(Time.valueOf("23:59:59").getTime()));
                }
            }

            //create weather for the event
            Weather weather = createWeather();
            event.setWeatherID(weather);

            //we can try to create the event 
            
            managerOfEvents();

            //invite & notify if there are invited
            if (getInviteSelect() || edit) {

                //create notification
                prepareCreateNotification();

            }        
            
             //set the owner as a participant if it is not an update
            if (!edit) {
                setOwnerParticipant();
            }

            if (getInviteSelect()) {
                //create participant
                prepareCreateParticipant();
            }

            //badcondition creation if they are set
            prepareCreateBadConditions();
            
            //add one day and repeat
            if (days > 0) {
                DateTime datetime = new DateTime(event.getDate());
                datetime = datetime.plusDays(1);
                event.setDate(datetime.toDate());
            }

        }

        return "";
    }

    /**
     * method that will update or create new events
     */
    private void managerOfEvents(){
        if (edit) {
            if(oldEventID!=null){
                this.oldParticipants = participantFacade.searchByEvent(oldEventID);
            }
            
            if(eventFacade.isAlreadyThere(this.oldEventID)){
                eventFacade.edit(event); 
                for(Participant p: this.oldParticipants){
                    if(!p.getOrganiser().equals("YES")){
                        if(eventFacade.dateAndTimeInTheMiddle(event.getDate(), event.getDate(), event.getStartingTime(),event.getEndingTime(),p.getUser1().getCalendar().getId(),p.getUser1().getUsername(),event.getId())){      
                            p.setParticipant("UNKNOWN");
                        }
                        
                        participantFacade.edit(p);
                    }                   
                }
            }else{ 
                event.setId(null);
                eventFacade.create(event);
                
                //participant creation
                participant.setEvent1(event);
                participant.setOrganiser("NO");
                participant.setParticipant("UNKNOWN");
                for (Participant p : this.oldParticipants) {
                    if(!p.getOrganiser().equals("YES")){
                        participant.setUser1(p.getUser1());
                        participant.setParticipantPK(new ParticipantPK(p.getUser1().getUsername(),event.getId()));
                        participantFacade.create(participant);
                    }
                }
               
                //owner creation
                setOwnerParticipant();
                
            }                   
        } else {
            event.setId(null);
            eventFacade.create(event);
        }
    }
    
    /**
     * prepare and create the bad conditions associated to the event
     */
    private void prepareCreateBadConditions() {

        if ((!edit && bad) || (editAddingBad && bad)) {

            //badconditions.setId( badconditionsFacade.getMaxBadConditionsID() + 1 );
            badconditions.setEventID(event);

            if (temp == false) {
                badconditions.setTemperature(null);
            }

            if (prec == false) {
                badconditions.setPrecipitations(null);
            }

        }

        
        //we can delete it if it was the user decision or we can try to create the badconditions
        if ((!bad) && (badconditions.getId() != null) && (oldEventID!=null)) {          
            badconditionsFacade.remove(badconditions);
        } else {
                if ( (edit) && (!editAddingBad) && (oldEventID!=null) ) {
                    badconditions.setEventID(event);
                    badconditionsFacade.edit(badconditions);
                    badconditions.setId(null);
                } else {
                    if (bad) {
                        badconditions.setId(null);
                        badconditionsFacade.create(badconditions);
                    }
                }
        }
        
        if(edit){
        //in order to modify correctly also the first bad conditions
        oldEventID=null;
        }
        
    }

    /**
     * method that will create and send a notification to all the invited User
     * for the current Event
     */
    private void prepareCreateNotification() {
        notification.setEventID(event);
        notification.setVisualized("NO");

        if (edit) {
            prepareUpdateNotification();
        }

        if (getInviteSelect()) {
            prepareInviteNotification();
        }

    }

    /**
     * method that will create a new Update Notification
     */
    private void prepareUpdateNotification() {
        notification.setType("UPDATE");
        notification.setDescription("The event " + event.getTitle() + " that will be held on the " + event.getDate() + " has been modified by " + event.getCalendar().getOwner().getUsername());

        for (Participant participants : participantFacade.searchByEvent(event.getId())) {

            notification.setUser(participants.getUser1());
            notification.setId(null);
            notificationFacade.create(notification);
        }
    }

    /**
     * method that will create a new Invite Notification
     */
    private void prepareInviteNotification() {
        notification.setType("INVITED");
        notification.setDescription("You have been invited to the event " + event.getTitle() + " by the user " + userFacade.getLoggedUser().getUsername() + " on the " + event.getDate());
        String currentUser = userFacade.getLoggedUser().getUsername();
        for (String invitatedUser : getInvitatedUsers()) {
            if(!invitatedUser.equals("") && !invitatedUser.equals(currentUser)){
                notification.setUser(userFacade.searchForUser(invitatedUser));
                notification.setId(null);
                notificationFacade.create(notification);
            }
        }
    }

    /**
     * method that will create a record in the participant table in which the
     * organiser is setted and the invited User are setted to "unknown" response
     * to the participation
     */
    private void prepareCreateParticipant() {
        //participants invited
        participant.setEvent1(event);
        participant.setOrganiser("NO");
        participant.setParticipant("UNKNOWN");
        Participant alreadyParticipant;
        String currentUser = userFacade.getLoggedUser().getUsername();
        for (String invitatedUser : getInvitatedUsers()) {
            if(!invitatedUser.equals("") && !invitatedUser.equals(currentUser)){
                
                alreadyParticipant = participantFacade.searchByUserEvent(invitatedUser,event.getId());
                
                if(alreadyParticipant == null){
                    participant.setUser1(userFacade.searchForUser(invitatedUser));
                    participant.setParticipantPK(new ParticipantPK(userFacade.searchForUser(invitatedUser).getUsername(), event.getId()));
                    participantFacade.create(participant);
                }
               
            }
        }

    }

    /**
     * method useful to set in the database the owner as a participant
     */
    private void setOwnerParticipant() {
        participant.setEvent1(event);
        participant.setOrganiser("YES");
        participant.setUser1(userFacade.getLoggedUser());
        participant.setParticipant("YES");
        participant.setParticipantPK(new ParticipantPK(userFacade.getLoggedUser().getUsername(), event.getId()));
        participantFacade.create(participant);
    }

    //getter&setter
    
    public Boolean getEditAddingBad() {
        return editAddingBad;
    }

    public void setEditAddingBad(Boolean editAddingBad) {
        this.editAddingBad = editAddingBad;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String[] getInvitatedUsers() {
        return invitatedUsers;
    }

    public void setInvitatedUsers(String[] invitatedUsers) {
        for (int i = 0; i<invitatedUsers.length; i++) {
            invitatedUsers[i] = invitatedUsers[i].replaceAll(" ", "");
        }
        this.invitatedUsers = invitatedUsers;
    }
    
    public String getRejectedUsers() {
        return rejectedUsers;
    }

    public void setRejectedUsers(String rejectedUsers) {
        this.rejectedUsers = rejectedUsers;
    }

    public Boolean getInviteSelect() {
        return InviteSelect;
    }

    public void setInviteSelect(Boolean InviteSelect) {
        this.InviteSelect = InviteSelect;
    }

    public Boolean getBad() {
        return bad;
    }

    public void setBad(Boolean bad) {
        this.bad = bad;
    }

    public Date getEndate() {
        return endate;
    }

    public void setEndate(Date date) {
        this.endate = date;
    }

    public String getRepeats() {
        return repeats;
    }

    public void setRepeats(String rep) {
        this.repeats = rep;
    }

    public String getInvitations() {
        return invitations;
    }

    public void setInvitations(String invitations) {
        this.invitations = invitations;
    }

    public Date getUntillDate() {
        return untillDate;
    }

    public void setUntillDate(Date untillDate) {
        this.untillDate = untillDate;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Badconditions getBadconditions() {
        return badconditions;
    }

    public void setBadconditions(Badconditions badconditions) {
        this.badconditions = badconditions;
    }

    public Boolean getTemp() {
        return temp;
    }

    public void setTemp(Boolean temp) {
        this.temp = temp;
    }

    public Boolean getPrec() {
        return prec;
    }

    public void setPrec(Boolean prec) {
        this.prec = prec;
    }
    
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
    
    // end getter&setter
}
