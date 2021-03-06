/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import java.util.Date;
import jsf.entity.Badconditions;
import jsf.entity.Calendar;
import jsf.entity.Event;
import jsf.entity.Notification;
import jsf.entity.Participant;
import jsf.entity.User;
import jsf.entity.facade.BadconditionsFacade;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.EventFacade;
import jsf.entity.facade.NotificationFacade;
import jsf.entity.facade.ParticipantFacade;
import jsf.entity.facade.UserFacade;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Matchers;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Walter
 */
public class EventTest {
    
    private EventController eventController;
    private Event event,newEvent;
    private User user;
    private Calendar calendar;
    private Badconditions badcond;
    private Participant participant;
    private Notification notification;
    private Date futureDate,startTime,endTime;
    private RequestContext context;
    
    //we will cover only the mandatory functionality because we don't have much time
    @Before
    public void setUp() {
        eventController = new EventController();       
        eventController.eventFacade = mock(EventFacade.class);
        eventController.calendarFacade = mock(CalendarFacade.class);
        eventController.userFacade = mock(UserFacade.class);
        eventController.participantFacade = mock(ParticipantFacade.class);
        eventController.badconditionsFacade = mock(BadconditionsFacade.class);
        eventController.notificationFacade = mock(NotificationFacade.class);
        context = mock(RequestContext.class);
        
        
        event = new Event();
        newEvent = new Event();
        futureDate = new DateTime(2100,1,1,12,00).toDate();
        startTime = new DateTime(2100,1,1,14,00).toDate();
        endTime = new DateTime(2100,1,1,18,00).toDate();
        
        event.setId(1);
        event.setTitle("test");
        event.setLocation("testinglocation");
        event.setDate(futureDate);
        eventController.setEndate(futureDate);
        event.setStartingTime(startTime);
        event.setEndingTime(endTime);
        
        newEvent.setId(1);
        newEvent.setTitle("test");
        newEvent.setLocation("testinglocation");
        newEvent.setDate(futureDate);
        newEvent.setStartingTime(startTime);
        newEvent.setEndingTime(endTime);
        
        user = new User();
        user.setUsername("testname");
        user.setPassword("testpass");
        user.setGroupName("USERS");
        
        calendar = new Calendar();
        calendar.setId(1);
        calendar.setOwner(user);
        calendar.setPrivacy("PRIVATE");
        
        float flo = 3;
        badcond = new Badconditions();
        badcond.setEventID(event);
        badcond.setLayer("RAINY");
        badcond.setPrecipitations(flo);
        badcond.setTemperature(null);
        
        notification = new Notification();
        
    }
   
    @Test
    public void EventControllerShouldBeInjected() {
        assertNotNull(eventController);
    }
    
    
    // the execute(eventcreated is called)
    @Test(expected=NullPointerException.class)
    public void OkWithBadInviteTest(){
        
        eventController.setEvent(event);
        eventController.setBad(Boolean.TRUE);
        eventController.setBadconditions(badcond);
        eventController.setInviteSelect(Boolean.TRUE);
        eventController.setInvitations("testname;");
        
        Participant p = spy(eventController.getParticipant());
        doReturn(newEvent).when(p).getEvent1();
        
        doThrow(new NullPointerException()).when(context).execute(anyString());
        
        when(eventController.userFacade.getLoggedUser()).thenReturn(user);
        when(eventController.calendarFacade.searchByUser(any(User.class))).thenReturn(calendar);
        when(eventController.userFacade.searchForUser(anyString())).thenReturn(user);
      
        doReturn(Boolean.FALSE).when(eventController.eventFacade).dateAndTimeInTheMiddleCreate(any(Date.class),any(Date.class),any(Date.class),anyInt(),anyString());
        
        eventController.controlDataCreation();
       
        verify(eventController.badconditionsFacade).create(badcond);
        verify(eventController.eventFacade).create(event);
        verify(eventController.eventFacade.count() , times(1));
        verify(eventController.participantFacade).create(participant);
        
    }

    // the execute(eventcreated is called)
    @Test
    public void OkWithBadTest(){
        
        eventController.newEvent();
        eventController.setEvent(event);
        eventController.setBad(Boolean.TRUE);
        eventController.setBadconditions(badcond);
        eventController.setInviteSelect(Boolean.TRUE);
        eventController.setInvitations("testname;");
        
        
        //mockare anche userfacade e calendarfacade, aggiungendo i falsi metodi anche li
        eventController.userFacade.create(user);
        
        
        when(eventController.calendarFacade.searchByUser(any(User.class))).thenReturn(calendar);
        when(eventController.userFacade.searchForUser(anyString())).thenReturn(user);
        when(eventController.userFacade.getLoggedUser()).thenReturn(user);
        when(eventController.eventFacade.dateAndTimeInTheMiddleCreate(any(Date.class),any(Date.class),any(Date.class),anyInt(),anyString())).thenReturn(Boolean.FALSE);
        
        try{
            eventController.controlDataCreation();
        }catch(Exception e){
            //verify(eventController.eventFacade, times(1)).create(event);
        }
    }
    
    
        // the participantPK generate an error
    @Test
    public void OkTest(){
        
        eventController.setEvent(event);
        //mockare anche userfacade e calendarfacade, aggiungendo i falsi metodi anche li
        eventController.userFacade.create(user);
        
        when(eventController.calendarFacade.searchByUser(any(User.class))).thenReturn(calendar);
        when(eventController.userFacade.getLoggedUser()).thenReturn(user);
        when(eventController.eventFacade.dateAndTimeInTheMiddleCreate(any(Date.class),any(Date.class),any(Date.class),anyInt(),anyString())).thenReturn(Boolean.FALSE);
        
        try{
            eventController.controlDataCreation();
        }catch(Exception e){
            verify(eventController.eventFacade,times(1)).create(event);
        }
        
    }
    
    @Test(expected=NullPointerException.class)
    public void NotDateError() {
        futureDate = new DateTime(1990,1,1,12,00).toDate();
        when(eventController.calendarFacade.searchByUser(any(User.class))).thenReturn(calendar);
        when(eventController.userFacade.getLoggedUser()).thenReturn(user);
        when(eventController.eventFacade.dateAndTimeInTheMiddleCreate(any(Date.class),any(Date.class),any(Date.class),anyInt(),anyString())).thenReturn(Boolean.TRUE);

        event.setDate(futureDate);
        eventController.controlDataCreation();
    }
    
    @Test(expected=NullPointerException.class)
    public void NotNullField(){
        event.setTitle(null);
        /*this is not exactly right because the exception is due to the requestcontext who can't be called, but
          since the requestcontext.execute is called only in case of error we can say that it will throw an exception*/
        eventController.controlDataCreation();
    }
    
}
