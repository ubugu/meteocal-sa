/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import jsf.entity.Calendar;
import jsf.entity.User;
import jsf.entity.cotroller.RegistrationController;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.UserFacade;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Walter
 */
public class UserRegistrationTest {
    
    private RegistrationController registrationController;
    private User user;
    private Calendar calendar;
    
    @Before
    public void setUp() {
        registrationController = new RegistrationController();       
        registrationController.facade = mock(UserFacade.class);
        registrationController.calendarFacade = mock(CalendarFacade.class);
        user = new User();
        calendar = new Calendar();
    }

    @Test
    public void newUsersOkAndRepeated() {
        user.setEmail("mail@mail.mail");
        user.setUsername("test");
        user.setPassword("password");
        calendar.setPrivacy("PRIVATE");
        registrationController.setUser(user);
        registrationController.setCalendar(calendar);
        calendar.setOwner(user);
      
        //register method
        registrationController.register();       
        
        verify(registrationController.facade).create(user);
        verify(registrationController.calendarFacade).create(calendar);

        //try to register with the same user
        registrationController.register();
                
        doThrow(new NullPointerException()).when(registrationController.facade).create(user);
        
    }
    
    @Test
    public void newUserNull() {
        user.setEmail("mail@mail.mail");
        user.setPassword("password");
        registrationController.getUser().setUsername(null);
                
        doThrow(new NullPointerException()).when(registrationController.facade).create(user);
    }
    
    
 }
