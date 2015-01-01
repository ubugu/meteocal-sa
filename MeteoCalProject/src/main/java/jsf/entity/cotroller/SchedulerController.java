/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;
import java.io.Serializable;
import java.util.ArrayList;
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
import jsf.entity.Calendar;
import jsf.entity.Event;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.EventFacade;
import jsf.entity.facade.ParticipantFacade;
import jsf.entity.facade.UserFacade;
import org.joda.time.DateTime;
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
    private ShowEventController eventController;
  
    private ScheduleEvent event = new DefaultScheduleEvent();

    private Set<Event> events = new HashSet<Event>();
    
    private Map<String,Integer> eventMap = new HashMap<>();
    
    @EJB
    CalendarFacade calendarFacade = new CalendarFacade();
    
    @EJB
    EventFacade eventFacade = new EventFacade();
    
    @EJB
    UserFacade userFacade = new UserFacade();
    
    @EJB
    ParticipantFacade participantFacade = new ParticipantFacade();
 
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        Calendar calendar = calendarFacade.searchByUser(this.userFacade.getLoggedUser());
        List<Event> ownEvents = eventFacade.searchByCalendar(calendar);
        List<Event>  invitedEvents = this.participantFacade.searchEventByUsername(this.userFacade.getLoggedUser().getUsername());
        events.addAll(ownEvents);
        events.addAll(invitedEvents);
        
        for (Event e : events) {
            Date startingDate = dataMerge(e.getDate(), e.getStartingTime());
            Date endingDate = dataMerge(e.getDate(), e.getEndingTime());
            ScheduleEvent newEvent = new DefaultScheduleEvent(e.getTitle(), startingDate, endingDate);
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
    }
    
    public ShowEventController getEventController() {
        return eventController;
    }
 
    public void showEvent() {
        try {
            int id = eventMap.get(event.getId());
            for (Event e : events) {
            if (e.getId() == id) {
                eventController.setSelectedEvent(e);
            }
        }
        } catch( NullPointerException e) {
            return;
        }
    }

    public String prova() {
       
     
        return "/showEvent?faces-redirect=true";
    }

    public void setEventController(ShowEventController eventController) {
        this.eventController = eventController;
    }
     
}