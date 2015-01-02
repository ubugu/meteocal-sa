/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javax.faces.bean.ViewScoped;
import jsf.entity.Calendar;
import jsf.entity.Event;
import jsf.entity.Notification;
import jsf.entity.Participant;
import jsf.entity.User;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.EventFacade;
import jsf.entity.facade.NotificationFacade;
import jsf.entity.facade.ParticipantFacade;
import jsf.entity.facade.UserFacade;
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
    private ShowEventController showEventController;
    
    @ManagedProperty(value="#{EventController}")
    private EventController eventController;

    @ManagedProperty(value = "#{SearchController}")
    private SearchController searchController;

    private ScheduleEvent event = new DefaultScheduleEvent();

    private Set<Event> events = new HashSet<>();
    
    private Map<String,Integer> eventMap = new HashMap<>();
    
    boolean isPublic = false;

    
    @EJB
    CalendarFacade calendarFacade = new CalendarFacade();
    
    @EJB
    EventFacade eventFacade = new EventFacade();
    
    @EJB
    UserFacade userFacade = new UserFacade();
    
    @EJB
    NotificationFacade notificationFacade = new NotificationFacade();
    
    @EJB
    ParticipantFacade participantFacade = new ParticipantFacade();
 
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        User user = null;
        if (this.isPublic == false) {
           user = this.userFacade.getLoggedUser();
        } else {
            user = this.userFacade.searchForUser(this.searchController.getSearchedUser());
        }
        events.clear();
        Calendar calendar = calendarFacade.searchByUser(user);
        List<Event> ownEvents = eventFacade.searchByCalendar(calendar);
        List<Event>  invitedEvents = this.participantFacade.searchEventByUsername(user.getUsername());
        events.addAll(ownEvents);
        events.addAll(invitedEvents);
        
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

    public Date dataMerge(Date date, Date time) {
        DateTime dateTime = new DateTime(date);
        DateTime timeTime = new DateTime(time);
        DateTime finalTime = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), timeTime.getHourOfDay(), timeTime.getMinuteOfHour());
        return finalTime.toDate();
    }

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

    public ShowEventController getShowEventController() {
        return showEventController;
    }
     
    public EventController getEventController() {
        return eventController;
    }

    public void setEventController(EventController eventController) {
        this.eventController = eventController;
    }
    
    public void showEvent() {
        try {
            int id = eventMap.get(event.getId());
            for (Event e : events) {
            if (e.getId() == id) {
                showEventController.setSelectedEvent(e);
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
    
    
    public SearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }


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

            List<Participant> participants = this.participantFacade.searchByEvent(id);

            for (Participant p : participants) {
                if (p.getParticipant().equals("YES") && p.getOrganiser().equals("NO")) {
                    User user = p.getUser1();
                    Notification newNotification = new Notification();
                    newNotification.setId(this.notificationFacade.getMaxNotificationID() + 1);
                    newNotification.setDescription("The event " + event.getTitle() + " has been deleted.");
                    newNotification.setEventID(null);
                    newNotification.setType("DELETE");
                    newNotification.setUser(user);
                    newNotification.setVisualized("NO");
                    this.notificationFacade.create(newNotification);  
                }
                this.participantFacade.remove(p);
                
            }

            List<Notification> notifications = this.notificationFacade.searchByEventID(id);
            
            for (Notification n : notifications) {
                this.notificationFacade.remove(n);
            }

            this.eventFacade.remove(event);
            init();
            return "/mainUserPage?faces-redirect=true";
        } catch (NullPointerException e) {
            return "";
        }
    }
    
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
    
    public String loadPublicCalendar() {
        setIsPublic(true);
        init();
        return "/publicCalendar?faces-redirect=true";
    }
    
    public String loadOwnCalendar() {
        setIsPublic(false);
        init();
        return "/mainUserPage?faces-redirect=true";
    }
    
    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    

}
