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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Walter
 */
public class SettingsTest {
            
    private SettingsController settingController;
    
    private User user;
    
    private Calendar calendar1;
    
    private List<User> list;
    
    private List<Calendar> calendarlist;
    
    private Calendar calendar2;
    
    private User user2;
    
    //TODO missing test for adding shared user
    @Before
    public void setUp() {
        settingController = new SettingsController();
        settingController.userFacade = mock(UserFacade.class);
        settingController.calendarFacade = mock(CalendarFacade.class);
               
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
    public void initTest(){                
        when(settingController.userFacade.getLoggedUser()).thenReturn(user);
        when(settingController.calendarFacade.searchByUser(any(User.class))).thenReturn(calendar1);
        when(settingController.userFacade.findAll()).thenReturn(list);
        
        settingController.setUser(user);
        
        settingController.init();
        
        assertTrue(settingController.getSetSharedPrivacy());
        assertTrue(settingController.getSharedUsersString().contains(user2.getUsername()));
        assertTrue(settingController.getOldSharedUsers().get(0).equals(user2));
    }
    
    @Test
    public void checkPasswordTest(){  
        when(settingController.userFacade.getLoggedUser()).thenReturn(user);
        assertFalse(settingController.checkPassowrd());
        settingController.setOldPassword("testpass");
        assertTrue(settingController.checkPassowrd());
    }

    @Test
    public void changeMailPassTest(){  
        when(settingController.userFacade.getLoggedUser()).thenReturn(user2);
        when(settingController.calendarFacade.searchByUser(any(User.class))).thenReturn(calendar1);
        list.remove(user);
        when(settingController.userFacade.findAll()).thenReturn(list);
        
        settingController.setOldPassword("testpass2");
        settingController.setPrivacy("PRIVATE");
        settingController.setSetNewEmail(Boolean.TRUE);
        settingController.setSetNewPassword(Boolean.TRUE);
        settingController.setUser(user);
        
        settingController.updateSettings();
        
        assertSame(settingController.getUser(),user);
        verify(settingController.userFacade,times(3)).getLoggedUser();
        verify(settingController.calendarFacade,times(1)).edit(calendar1);
    }
    
    @Test
    public void changePrivacyToShared(){  
        when(settingController.userFacade.getLoggedUser()).thenReturn(user2);
        when(settingController.calendarFacade.searchByUser(any(User.class))).thenReturn(calendar1);
        list.remove(user);
        when(settingController.userFacade.findAll()).thenReturn(list);
        
        settingController.setOldPassword("testpass2");
        settingController.setPrivacy("SHARED");
        settingController.setSetNewEmail(Boolean.FALSE);
        settingController.setSetSharedPrivacy(Boolean.TRUE);
        settingController.setSetNewPassword(Boolean.FALSE);
        settingController.setUser(user);
        
        settingController.updateSettings();
        
        assertSame(settingController.getUser(),user);
        verify(settingController.userFacade,times(3)).getLoggedUser();
        verify(settingController.calendarFacade,times(1)).edit(calendar1);
    }
}
