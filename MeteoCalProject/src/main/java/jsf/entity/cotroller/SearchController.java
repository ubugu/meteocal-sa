/*
 * Bean that manages the searching system.
 */
package jsf.entity.cotroller;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jsf.entity.Calendar;
import jsf.entity.User;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.UserFacade;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Walter
 */

@ManagedBean(name = "SearchController", eager = true)
@SessionScoped
public class SearchController implements Serializable{
        
    private User user;
    
    private String searchedUser=null;
    
    private Boolean privateCalendar = true; 
    
           
    @EJB
    UserFacade userFacade;
    
    @EJB
    CalendarFacade calendarFacade;

    //getter & setter
    
    public Boolean getPrivateCalendar() {
        return privateCalendar;
    }

    public void setPrivateCalendar(Boolean privateCalendar) {
        this.privateCalendar = privateCalendar;
    }
    
    public String getSearchedUser() {
        return searchedUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSearchedUser(String searchedUser) {
        this.searchedUser = searchedUser;
    }
    
    //end getter & setter
    
    /**
     * search for User
     * @return the redirect to the search page.
     */
    public String searchUser(){
        
        user = userFacade.searchForUser(getSearchedUser());
        if(user == null){
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('UserNotFound Error').show();");
            return "search?faces-redirect=true";
        }
        
        Calendar searchedCalendar = calendarFacade.searchByUser(user);
        user.setCalendar(searchedCalendar);
        
         if(searchedCalendar.getPrivacy().equals("PRIVATE") ){
            setPrivateCalendar(true);
        }
        
        if(searchedCalendar.getPrivacy().equals("PUBLIC") ){
            setPrivateCalendar(false);
        }
        
        if(searchedCalendar.getPrivacy().equals("SHARED") ){
            
            User loggedUser = this.userFacade.getLoggedUser();
            if (loggedUser.getCalendarList().contains(searchedCalendar)) {
                setPrivateCalendar(false);
            } else {
                setPrivateCalendar(true);
            }
            
            
        }
        
        return("search?faces-redirect=true");
    }
    
    
}
