/*
 * Bean that manages the schedule.
 */
package jsf.entity.cotroller;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import jsf.entity.Badconditions;
import jsf.entity.Calendar;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
 
@ManagedBean  (name="scheduleController")
@SessionScoped
public class SchedulerController implements Serializable {
 
    private ScheduleModel eventModel; 
    
    @ManagedProperty(value="#{showEventController}")
    ShowEventController showEventController;
    
    @ManagedProperty(value="#{EventController}")
    EventController eventController;

    @ManagedProperty(value = "#{SearchController}")
    SearchController searchController;

    private ScheduleEvent event = new DefaultScheduleEvent();

    private Set<Event> events = new HashSet<>();
    
    private Map<String,Integer> eventMap = new HashMap<>();
    
    boolean isPublic = false;

    
    @EJB
    CalendarFacade calendarFacade;
    
    @EJB
    EventFacade eventFacade;
    
    @EJB
    UserFacade userFacade;
    
    @EJB
    NotificationFacade notificationFacade;
    
    @EJB
    ParticipantFacade participantFacade;
    
    @EJB
    WeatherFacade weatherFacade;
 
    @EJB
    BadconditionsFacade badConditionsFacade;
    /**
     * Contruct the schedule in the xhtml form, both for logged calendar and searched public calendar.
     */
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        User user = null;
        //set the user of the calendar.
        if (this.isPublic == false) {
           user = this.userFacade.getLoggedUser();
        } else {
            user = this.userFacade.searchForUser(this.searchController.getSearchedUser());
        }
        
        //collects data of event in created by the calendar owner and in which he has been invited to.
        events.clear();
        Calendar calendar = calendarFacade.searchByUser(user);
        List<Event> ownEvents = eventFacade.searchByCalendar(calendar);
        List<Event>  invitedEvents = this.participantFacade.searchAcceptedEventByUsername(user.getUsername());
        events.addAll(ownEvents);
        events.addAll(invitedEvents);
        
        // add each event to the schedule.
        for (Event e : events) {
            Date startingDate = dataMerge(e.getDate(), e.getStartingTime());
            Date endingDate = dataMerge(e.getDate(), e.getEndingTime());
            DefaultScheduleEvent newEvent = null;
            if (this.isPublic == false) {
                newEvent = new DefaultScheduleEvent(e.getTitle(), startingDate, endingDate, e.getColor());
            } else {
                if (e.getPrivacy().equals("public")) {
                    newEvent = new DefaultScheduleEvent(e.getTitle(), startingDate, endingDate, e.getColor());

                } else {
                    newEvent = new DefaultScheduleEvent("Private Event", startingDate, endingDate, "private");
                }

            }

            eventModel.addEvent(newEvent);
            eventMap.put(newEvent.getId(), e.getId());
        }
    }

    /**
     * Merge the date and time into one date.
     * @param date 
     * @param time
     * @return  the Date merged
     */
    public Date dataMerge(Date date, Date time) {
        DateTime dateTime = new DateTime(date);
        DateTime timeTime = new DateTime(time);
        DateTime finalTime = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), timeTime.getHourOfDay(), timeTime.getMinuteOfHour());
        return finalTime.toDate();
    }

     /**
      * Set the selected event. Open the event details dialog.
      * @param selectEvent with a mouse click.
      */
    public void onEventSelect(SelectEvent selectEvent) {
        this.event = (ScheduleEvent) selectEvent.getObject();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.isPublic == false) {
            requestContext.execute("PF('event').show();");
        } else {
            if (event.getTitle().equals("Private Event")) {
                return;
            } else {
                requestContext.execute("PF('event').show();");
            }
        }

    }

    /**
     * Set the event selected into ShowEventController and creat its chart.
     */
    public void showEvent() {
        try {
            int id = eventMap.get(event.getId());
            for (Event e : events) {
                if (e.getId() == id) {
                    showEventController.setSelectedEvent(e);
                showEventController.initChart();
                break;
            }
        }
        } catch( NullPointerException e) {
            return;
        }
    }
    
    /**
     * method that will redirect to the addEvent page in order to update the current selected event
     * @return the redirecting string
     */
    public String updateEvent(){
        int id = eventMap.get(event.getId());
        return eventController.changeEvent(id);
        
    }
    


    /**
     * Delete an event.
     * @return the redirect to the home page.
     */
    public String delete() {
        try {
            int id = eventMap.get(event.getId());
            Event event = null;

            for (Event e : this.events) {
                if (e.getId() == id) {
                    event = e;
                    break;
                }
            }

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

            //remove bad conditions if added
            Badconditions conditions = this.badConditionsFacade.searchByEvent(event);
            if (conditions != null) {
                this.badConditionsFacade.remove(conditions);
            }


            //Remove notifications about that event
            List<Notification> notifications = this.notificationFacade.searchByEventID(id);
            for (Notification n : notifications) {
                this.notificationFacade.remove(n);
            }
            
            //remove the event
            this.eventFacade.remove(event);
            events.remove(event);
            
            //remove the weather if no other events has link to it
            Weather weatherEvent = event.getWeatherID();
         
            if (weatherEvent == null) {
                init();
                return "/mainUserPage?faces-redirect=true";
            }

            for (Event e : eventFacade.findAll()) {
                if (e.getWeatherID() == null) {
                    continue;
                }
                if (e.getWeatherID().equals(weatherEvent)) {
                    init();
                    return "/mainUserPage?faces-redirect=true";
                }
            }
            
            weatherFacade.remove(weatherEvent);
            init();
            return "/mainUserPage?faces-redirect=true";
        } catch (NullPointerException e) {
            return "";
        }
    }
    
    
    /**
     * Check if the logged user is invited to the event selected.
     * @return "none" if the logged user is invitend to the event otherwise "display"
     */
      public String isInvited() {
          int id;
          try {
              id = eventMap.get(event.getId());
          } catch (NullPointerException e) {
            return "display";
        }
        User user = userFacade.getLoggedUser();
        List<Participant> participants = this.participantFacade.searchByEvent(id);
        User creator = null;
        for (Participant p : participants) {
            if (p.getOrganiser().equals("YES")) {
                creator = p.getUser1();
                break;
            }
        }
        if (user.equals(creator)) {
            return "display";
        } else {
            return "none";
        }
    }

    public void setShowEventController(ShowEventController showEventController) {
        this.showEventController = showEventController;
    }
    
    /**
     * Creat the public calendar. 
     * @return redirect to the homepage
     */
    public String loadPublicCalendar() {
        setIsPublic(true);
        init();
        return "/publicCalendar?faces-redirect=true";
    }
    
    /**
     * Create the logged user calendar
     * @return redirect to the homepage
     */
    public String loadOwnCalendar() {
        setIsPublic(false);
        init();
        return "/mainUserPage?faces-redirect=true";
    }
 
    //getter and setter
    
          public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }
    
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
    
       public ShowEventController getShowEventController() {
        return showEventController;
    }
     
    public EventController getEventController() {
        return eventController;
    }

    public void setEventController(EventController eventController) {
        this.eventController = eventController;
    }
    
        
    public SearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }
    
    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    

}
