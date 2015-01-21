/*
 * Bean that manages register.
 */
package jsf.entity.cotroller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import jsf.entity.Calendar;
import jsf.entity.Event;
import jsf.entity.User;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.EventFacade;
import jsf.entity.facade.UserFacade;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;

/**
 *
 * @author claudio
 */
@ManagedBean(name = "registrationController", eager = true)
@RequestScoped
public class RegistrationController {
    
    private User user;
    
    private Calendar calendar;
    
    @EJB
    UserFacade facade;
    
    @EJB
    CalendarFacade calendarFacade;
    
    @EJB
    EventFacade eventFacade;
    
    
    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Calendar getCalendar() {
        if (calendar == null) {
            calendar = new Calendar();
        }
        return calendar;
    }
    
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Register in the database a new user with the inserted information. A new calendar associated to the user is also created.
     * Email and Username validity is checked. If one rule is violated an error is shown on the web page.
     * @return the redirect to the home
     */
    public String register() {
        user.setGroupName("USER");
        if (checkEmail()) {
            return "";
        }
        try {
            facade.create(user);
            calendar.setOwner(user);
            calendarFacade.create(calendar);
        } catch (Exception e) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('usernameError').show();");
            return "";
        }
    
        return "index?faces-redirect=true";
    }

    /**
     * check if the email inserted has been already used.
     * @return  true if the email inserted is found in the database.
     */
    private boolean checkEmail() {
        List<User> allUser = facade.findAll();
  
        for (User u : allUser) {
            if (u.getEmail().equals(user.getEmail())) {
                RequestContext requestContext = RequestContext.getCurrentInstance();  
                requestContext.execute("PF('emailError').show();");
                return true;
            }
        }
       return false;
    }
    

    
}
