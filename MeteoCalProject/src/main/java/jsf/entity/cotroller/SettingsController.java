/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import jsf.entity.Calendar;
import jsf.entity.User;
import jsf.entity.facade.CalendarFacade;
import jsf.entity.facade.UserFacade;
import org.primefaces.context.RequestContext;

/**
 *
 * @author claudio
 */
@ManagedBean( name= "settingsController" , eager = true)
@ViewScoped
public class SettingsController {
    
    private User user = new User();
    private Boolean setNewPassword = false;

    private Boolean setNewEmail = false;
    
    private String privacy;
    
    private String oldPassword = "";

 
    @EJB
    UserFacade userFacade = new UserFacade();
    
    @EJB
    CalendarFacade calendarFacade = new CalendarFacade();

    public String getPrivacy() {
        if (privacy == null) {
           privacy = calendarFacade.searchByUser(userFacade.getLoggedUser()).getPrivacy();
        }
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
 
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Boolean getSetNewPassword() {
        return setNewPassword;
    }

    public void setSetNewPassword(Boolean setNewPassword) {
        this.setNewPassword = setNewPassword;
    }

    public Boolean getSetNewEmail() {
        return setNewEmail;
    }

    public void setSetNewEmail(Boolean setNewEmail) {
        this.setNewEmail = setNewEmail;
    }
    
    public Boolean displayNewPassword() {
        return !this.setNewPassword;
    }
    
    public Boolean displayNewEmail() {
        return !this.setNewEmail;
    }


    public void setOldPassword(String oldPassword) {
        this.oldPassword = PasswordEncrypter.encryptPassword(oldPassword);
    }

    public String getOldPassword() {
        return oldPassword;
    }
    
    
    public Boolean checkPassowrd() {
        if (userFacade.getLoggedUser().getPassword().equals(this.oldPassword)) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean checkEmail() {
        List<User> allUser = userFacade.findAll();

        for (User u : allUser) {
            if (u.getEmail().equals(user.getEmail())) {
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.execute("PF('emailError').show();");
                return true;
            }
        }
        return false;
    }

    
    public String updateSettings() {
        if (!checkPassowrd()) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('wrongPassword').show();");
            return "";
        } else {
            Calendar calendar = this.calendarFacade.searchByUser(this.userFacade.getLoggedUser());
            calendar.setPrivacy(this.privacy);
            calendarFacade.edit(calendar);
            User loggedUser = this.userFacade.getLoggedUser();
            
            if ((!this.setNewEmail) && (!this.setNewPassword)) {
                return "/settings?faces-redirect=true";
            }
            
            if (!this.setNewEmail) {
                this.user.setEmail(loggedUser.getEmail());
            } else { 
                if (checkEmail()) {
                    return "";
                }   
            }
            
            if (!this.setNewPassword) {
                this.user.setAlreadyEncryptedPassword(loggedUser.getPassword());
            }
            
            this.user.setUsername(loggedUser.getUsername());
            this.user.setGroupName("USER");
            this.userFacade.edit(user);
            
            return "/settings?faces-redirect=true";
        }
    }

    
}
