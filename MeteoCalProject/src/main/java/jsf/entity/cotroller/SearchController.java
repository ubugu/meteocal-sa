/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jsf.entity.Calendar;
import jsf.entity.User;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.UserFacade;

/**
 *
 * @author Walter
 */

@ManagedBean(name = "SearchController", eager = true)
@SessionScoped
public class SearchController {
        
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
    
    public String searchUser(){
        
        user = userFacade.searchForUser(getSearchedUser());
        if(user == null){
            //TODO error
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
