/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import jsf.entity.Calendar;
import jsf.entity.User;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.UserFacade;
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
        
        verify(registrationController.facade,times(1)).create(user);
        verify(registrationController.calendarFacade,times(1)).create(calendar);
        
        //try to register with the same user
        try{
            registrationController.register();
        }catch(Exception e){
                verify(registrationController.facade,times(1)).create(user);
                verify(registrationController.calendarFacade,times(1)).create(calendar);
        }        
        
        user.setUsername("pluto");
        
        registrationController.register();       
        
        verify(registrationController.facade,times(3)).create(user);
        verify(registrationController.calendarFacade,times(3)).create(calendar);
        
    }
    
    @Test
    public void newUserNull() {
        user.setEmail("mail@mail.mail");
        user.setPassword("password");
        registrationController.getUser().setUsername(null);
                
        doThrow(new NullPointerException()).when(registrationController.facade).create(user);
    }
    
    
 }
