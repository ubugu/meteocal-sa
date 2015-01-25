/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jsf.entity.Calendar;
import jsf.entity.User;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.UserFacade;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Walter
 */
public class SchedulerControllerTest {
    private SchedulerController schedulerController;
    
    private User user;
    
    private Calendar calendar1;
    
    private List<User> list;
    
    private List<Calendar> calendarlist;
    
    private Calendar calendar2;
    
    private User user2;
    
    //TODO missing test for adding shared user
    @Before
    public void setUp() {
        schedulerController = new SchedulerController();
        schedulerController.userFacade = mock(UserFacade.class);
        schedulerController.calendarFacade = mock(CalendarFacade.class);
               
        user = new User();
        user.setUsername("testname");
        user.setPassword("testpass");
        user.setEmail("test@test.it");
        user.setGroupName("USERS");
        
        user2 = new User();
        user2.setUsername("testdoublename");
        user2.setPassword("testpass2");
        user2.setEmail("test2@test2.it");
        user2.setGroupName("USERS");
        
        list = new ArrayList<>();
        list.add(user);
        list.add(user2);
        
        calendar1 = new Calendar();
        calendar1.setOwner(user);
        calendar1.setPrivacy("SHARED");
        
        calendar2 = new Calendar();
        calendar2.setOwner(user);
        calendar2.setPrivacy("PUBLIC");
        
        calendarlist = new ArrayList<>();
        calendarlist.add(calendar1);
        user.setCalendarList(null);
        user2.setCalendarList(calendarlist);
        
    }

    @Test
    public void dataMergeTest(){
        Date dateTime = new DateTime(2100,1,1,12,00).toDate();
        Date timeTime = new DateTime(1990,12,12,21,23).toDate();
        Date resultDate = schedulerController.dataMerge(dateTime, timeTime);
        
        assertTrue(new DateTime(2100,1,1,21,23).toDate().compareTo(resultDate)==0);
    }
    
    //TODO test init,oneventselect (full of request execute),showevent,updateevent,delete,isinvited
    
    @Test
    public void loadPublicCalendarTest(){
        try{
            schedulerController.loadPublicCalendar();
        }catch(Exception e){
            assertTrue(schedulerController.isPublic);
        }        
    }
    
    @Test
    public void loadOwnCalendarTest(){
        try{
            schedulerController.loadOwnCalendar();
        }catch(Exception e){
            assertFalse(schedulerController.isPublic);
        }  
    }
    
}
