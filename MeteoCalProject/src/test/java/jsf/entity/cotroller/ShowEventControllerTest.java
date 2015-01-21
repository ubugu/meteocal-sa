/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jsf.entity.Badconditions;
import jsf.entity.Calendar;
import jsf.entity.Event;
import jsf.entity.Notification;
import jsf.entity.Participant;
import jsf.entity.ParticipantPK;
import jsf.entity.User;
import jsf.entity.Weather;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Walter
 */
public class ShowEventControllerTest {
 
        
    private ShowEventController showEventController;
    
    private User user,user2;
    
    private Event event;
    
    private Participant participant,participant2;
    
    private List<Participant> participants;
    
    private Calendar calendar1;
    
    private List<User> list;
    
    private List<Calendar> calendarlist;
    
    private Calendar calendar2;

    @Before
    public void setUp() {
        showEventController = new ShowEventController();
        showEventController.userFacade = mock(UserFacade.class);
        showEventController.badConditionsFacade = mock(BadconditionsFacade.class);
        showEventController.eventFacade = mock(EventFacade.class);
        showEventController.participantFacade = mock(ParticipantFacade.class);
        showEventController.notificationFacade = mock(NotificationFacade.class);
        
        event = new Event();
        event.setId(1);
        event.setTitle("test");
        event.setLocation("testinglocation");
        event.setDate(new DateTime(2100,1,1,00,00).toDate());
        event.setStartingTime(new DateTime(1990,1,1,12,00).toDate());
        event.setEndingTime(new DateTime(1990,1,1,18,12).toDate());

        user = new User();
        user.setUsername("testname");
        user.setPassword("testpass");
        user.setEmail("test@test.it");
        user.setGroupName("USERS");
        
        user2 = new User();
        user2.setUsername("testname2");
        user2.setPassword("testpass2");
        user2.setEmail("test2@test.it");
        user2.setGroupName("USERS");
        
        calendar1 = new Calendar();
        calendar1.setOwner(user);
        calendar1.setPrivacy("SHARED");
        
        participant = new Participant();
        participant.setEvent1(event);
        participant.setUser1(user);
        participant.setParticipantPK(new ParticipantPK(user.getUsername(),event.getId()));
        participant.setParticipant("YES");
        participant.setOrganiser("YES");
        
        participant2 = new Participant();
        participant2.setEvent1(event);
        participant2.setUser1(user2);
        participant2.setParticipantPK(new ParticipantPK(user2.getUsername(),event.getId()));
        participant2.setParticipant("UNKNOWN");
        participant2.setOrganiser("NO");
        
        participants = new ArrayList<>();       
        participants.add(participant);
        participants.add(participant2);  
    
    }

    // test getDate,setselectedEvent,setCreator,
    @Test
    public void getDateTest(){
        when(showEventController.participantFacade.searchByEvent(event.getId())).thenReturn(participants);
        showEventController.setSelectedEvent(event);
        
        assertSame(participants,showEventController.getParticipants());
        assertSame(user,showEventController.getCreator());
        assertTrue(showEventController.getDate().equals("Fri Jan 01 2100"));
        verify(showEventController.participantFacade,times(1)).searchByEvent(event.getId());
        
        assertTrue(showEventController.getStartingTime().equals("12:00:0"));
        assertTrue(showEventController.getEndingTime().equals("18:12:0"));

    }
    
    //isinvited,getresponse,hasbadconditions
    @Test
    public void isInvitedTest(){
        when(showEventController.participantFacade.searchByEvent(event.getId())).thenReturn(participants);
        showEventController.setSelectedEvent(event);

        when(showEventController.userFacade.getLoggedUser()).thenReturn(user2);
        assertTrue(showEventController.isInvited());
        assertTrue(showEventController.getResponse().equals("UNKNOWN"));
        
        when(showEventController.userFacade.getLoggedUser()).thenReturn(user);
        assertFalse(showEventController.isInvited());
        assertTrue(showEventController.getResponse().equals("YES"));
        
        verify(showEventController.participantFacade,times(1)).searchByEvent(event.getId());
        verify(showEventController.userFacade,times(4)).getLoggedUser();
        
        Badconditions badConditions = new Badconditions();
        when(showEventController.badConditionsFacade.searchByEvent(event)).thenReturn(null);
        assertTrue(showEventController.hasBadCondition().equals("none"));
        when(showEventController.badConditionsFacade.searchByEvent(event)).thenReturn(badConditions);
        assertTrue(showEventController.hasBadCondition().equals("display"));
    }
    
    @Test
    public void responseNotification(){
        when(showEventController.participantFacade.searchByEvent(event.getId())).thenReturn(participants);
        
        showEventController.setSelectedEvent(event);
        
        when(showEventController.eventFacade.dateAndTimeInTheMiddleCreate(any(java.util.Date.class),any(java.util.Date.class),any(java.util.Date.class),any(java.util.Date.class),anyInt(),anyString())).thenReturn(Boolean.FALSE);   
        when(showEventController.userFacade.getLoggedUser()).thenReturn(user2);
        
        showEventController.setInvited("NO");
        
        verify(showEventController.participantFacade,times(1)).edit(participant2);
        verify(showEventController.notificationFacade,times(1)).create(any(Notification.class));
    }
    
    @Test
    public void weatherImagesTest(){
        Weather weather = new Weather();
        weather.setCity("Milan");
        
        float flo;
        weather.setPrecipitationType("SNOW");
        showEventController.setWeather(weather);
        
        flo=0;
        showEventController.getWeather().setClouds(flo);
        showEventController.getWeather().setPrecipitations(0);
        assertTrue(showEventController.getWeatherImage().contains("sun"));
        
        flo=0;
        showEventController.getWeather().setPrecipitations(1);
        assertTrue(showEventController.getWeatherImage().contains("snow"));
        
        showEventController.getWeather().setPrecipitationType("RAIN");
        
        flo=0;
        showEventController.getWeather().setPrecipitations(11);
        assertTrue(showEventController.getWeatherImage().contains("rainHard"));
        
        flo=0;
        showEventController.getWeather().setPrecipitations(2);
        assertTrue(showEventController.getWeatherImage().contains("rainLow"));
        
        flo=10;
        showEventController.getWeather().setClouds(flo);
        showEventController.getWeather().setPrecipitations(0);
        assertTrue(showEventController.getWeatherImage().contains("cloudyLow"));
        
        flo=60;
        showEventController.getWeather().setClouds(flo);
        showEventController.getWeather().setPrecipitations(0);
        assertTrue(showEventController.getWeatherImage().contains("cloudyHard"));
    
    }
    
    //TODO test init,oneventselect (full of request execute),showevent,updateevent,delete,isinvited
    
}
