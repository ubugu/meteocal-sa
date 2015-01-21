
    /*
 * Bean that manages the login.
 */
package jsf.entity.cotroller;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import jsf.entity.Calendar;
import jsf.entity.Event;
import jsf.entity.Participant;
import jsf.entity.ParticipantPK;
import jsf.entity.User;
import jsf.entity.facade.EventFacade;
import jsf.entity.facade.ParticipantFacade;
import jsf.entity.facade.UserFacade;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;

/**
 *
 * @author claudio
 */
@ManagedBean( name= "loginBean" , eager = true)
@RequestScoped
public class LoginBean implements Serializable{
    

    private String username;
    private String password;
    
    @EJB
    ParticipantFacade participantFacade;
    
    @EJB
    EventFacade eventFacade;
    
    @EJB
    UserFacade facade;

    public LoginBean() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Perform login.
     * @return redirect to the user homepage.
     */
    public String login() {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(this.username, this.password);
            resetParticipant();
            return "mainUserPage"; 
        } catch (ServletException ex) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('loginFailed').show();");
            return "";   
        }          
    }

    
    public void resetParticipant() {
        System.out.println("Resitting participant");
        Participant participant = new Participant();
        ParticipantPK participantPK = new ParticipantPK();
        User user = this.getLoggedUser();
        Calendar calendar = user.getCalendar();
        Event event = new Event();
      
        event.setCalendar(calendar);
        DateTime today = new DateTime();
        Date date = new Date(today.getMillis());
        Time time = new Time(today.getMillis());
        event.setDate(date);
        event.setPrivacy("PRIVATE");
        event.setStartingTime(time);
        event.setEndingTime(time);
        event.setColor("grey");
        event.setTitle("Server Starting");
        event.setLocation("Meteocal website");
        event.setId(null);
        eventFacade.create(event);
        
        participant.setEvent1(event);
        participant.setOrganiser("YES");
        participant.setParticipant("YES");
        participant.setUser1(user);
        participantPK.setEvent(event.getId());
        participantPK.setUser(user.getUsername());
        participant.setParticipantPK(participantPK);
        
     
        participantFacade.create(participant);
    }
    
    /**
     * @return  the logged User
     */
    public User getLoggedUser() {
        return facade.getLoggedUser();
    }
    
    /**
     * perfom logout.
     * @return redirect to the guest homepage.
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().invalidate();
        return "/index?faces-redirect=true";
    }
}
