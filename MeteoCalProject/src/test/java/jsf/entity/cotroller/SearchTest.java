/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import java.util.ArrayList;
import java.util.List;
import jsf.entity.Calendar;
import jsf.entity.User;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.UserFacade;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Walter
 */
public class SearchTest {
     
    private SearchController searchController;
    
    private User user,user2;
    
    private Calendar calendar,calendar2;
    
    private List<Calendar> calendarlist;
    
    @Before
    public void setUp() {
        searchController = new SearchController();
        searchController.calendarFacade = mock(CalendarFacade.class);
        searchController.userFacade = mock(UserFacade.class);
        
        user = new User();
        user.setUsername("testname");
        user.setPassword("testpass");
        user.setGroupName("USERS");
        
        calendar = new Calendar();
        calendar.setOwner(user);
        calendar.setPrivacy("PUBLIC");
        
        user2 = new User();
        user2.setUsername("testdoublename");
        user2.setPassword("testpass2");
        user2.setEmail("test2@test2.it");
        user2.setGroupName("USERS");
        
        calendar2 = new Calendar();
        calendar2.setOwner(user);
        calendar2.setPrivacy("PUBLIC");
        
        calendarlist = new ArrayList<>();
        calendarlist.add(calendar);
        
        user2.setCalendarList(calendarlist);
        
    }
    
    @Test
    public void showEventWithoutWeather(){
        
        //calendar public
        when(searchController.userFacade.searchForUser(anyString())).thenReturn(user);
        when(searchController.calendarFacade.searchByUser(any(User.class))).thenReturn(calendar);
        when(searchController.userFacade.getLoggedUser()).thenReturn(user2);

        searchController.setSearchedUser("testname");
        
        searchController.searchUser();
        
        assertFalse(searchController.getPrivateCalendar());
        
        //calendar private
        calendar.setPrivacy("PRIVATE");
        
        searchController.searchUser();
        
        assertSame(searchController.getPrivateCalendar(),true);
        
        //calendar shared
        calendar.setPrivacy("SHARED");

        searchController.searchUser();
        
        assertFalse(searchController.getPrivateCalendar());
        
        verify(searchController.userFacade,times(3)).searchForUser(anyString());
        verify(searchController.userFacade,times(1)).getLoggedUser();
        verify(searchController.calendarFacade,times(3)).searchByUser(user);
    }
    
}
