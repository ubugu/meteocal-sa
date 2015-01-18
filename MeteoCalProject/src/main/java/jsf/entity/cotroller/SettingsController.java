/*
 * Bean that manages the settings.
 */
package jsf.entity.cotroller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
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
@ManagedBean(name = "settingsController", eager = true)
@ViewScoped
public class SettingsController implements Serializable{
    private User user = new User();
    private Boolean setNewPassword = false;

    private Boolean setNewEmail = false;

    private Boolean setSharedPrivacy = false;

    private String privacy;

    private String oldPassword = "";

    private String sharedUsersString = "";

    private List<User> oldSharedUsers = new ArrayList<>();

    @EJB
    UserFacade userFacade;

    @EJB
    CalendarFacade calendarFacade;


    /**
     * Find and create the list of oldSharedUser.
     */
    @PostConstruct
    public void init() {
        if (this.calendarFacade.searchByUser(this.userFacade.getLoggedUser()).getPrivacy().equals("SHARED")) {
            this.setSharedPrivacy = true;
        }
        List<User> allUser = this.userFacade.findAll();
        Calendar calendar = this.calendarFacade.searchByUser(this.userFacade.getLoggedUser());
        List<Calendar> sharedCalendar;
        for (User u : allUser) {
            sharedCalendar = u.getCalendarList();
            if (sharedCalendar == null) {
                continue;
            }

            if (sharedCalendar.contains(calendar)) {
                this.sharedUsersString += u.getUsername() + "; ";
                this.oldSharedUsers.add(u);
            }
        }

    }

    public String getPrivacy() {
        if (privacy == null) {
            privacy = calendarFacade.searchByUser(userFacade.getLoggedUser()).getPrivacy();
        }
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
        this.setSharedPrivacy = privacy.equals("SHARED");
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

    public void setOldPassword(String oldPassword) {
        this.oldPassword = PasswordEncrypter.encryptPassword(oldPassword);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public Boolean getSetSharedPrivacy() {
        return setSharedPrivacy;
    }

    public void setSetSharedPrivacy(Boolean setSharedPrivacy) {
        this.setSharedPrivacy = setSharedPrivacy;
    }

    public String getSharedUsersString() {
        return sharedUsersString;
    }

    public void setSharedUsersString(String sharedUsersString) {
        this.sharedUsersString = sharedUsersString;
    }

    /**
     * Check if the passowrd inserted is equals to the old one
     * @return  true if password matches otherwise false
     */
    public Boolean checkPassowrd() {
        if (userFacade.getLoggedUser().getPassword().equals(this.oldPassword)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if the new email inserted is already inserted in the database.
     * @return  true if an user with the same email inserted is found.
     */
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

    /**
     * Update new settings (password,email,privacy) if changed
     * @return the redirect to the settings page
     */
    public String updateSettings() {
        
        if (!checkPassowrd()) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('wrongPassword').show();");
            return "";
        } else {
            Calendar calendar = this.calendarFacade.searchByUser(this.userFacade.getLoggedUser());
            calendar.setPrivacy(this.privacy);
            User addUser = null;

            if (this.privacy.equals("SHARED")) {
                List<String> sharedUsernames = getUsername(this.sharedUsersString);
                List<User> newSharedUsers = new ArrayList<>();
        
                if (!sharedUsernames.isEmpty()) {
                    //Check if a wrong username is inserted
                    for (String s : sharedUsernames) {
                        addUser = this.userFacade.searchForUser(s);
                        if (addUser == null) {
                            RequestContext requestContext = RequestContext.getCurrentInstance();
                            requestContext.execute("PF('wrongUsername').show();");
                            return "";
                        }
                        newSharedUsers.add(addUser);
                    }
                }
               

                //Construct delete list and update newSharedUser so to not recreate sharedUsers if already presents in the database
                List<User> deleteSharedUsers = new ArrayList<>();
                for (User u : oldSharedUsers) {
                    if (newSharedUsers.contains(u)) {
                        newSharedUsers.remove(u);
                    } else {
                        deleteSharedUsers.add(u);
                    }
                }
                
                //Delete new Shared Users
                for (User u : deleteSharedUsers) {
                    List<Calendar> deleteList = u.getCalendarList();
                    deleteList.remove(calendar);
                    u.setCalendarList(deleteList);
                    this.userFacade.edit(u);
                }
                
                
                //Add new shared Users
               for (User u : newSharedUsers) {
                    List<Calendar> calendarList = u.getCalendarList();
                    if (calendarList == null) {
                        calendarList = new ArrayList<>();
                    }
                    calendarList.add(calendar);
                    u.setCalendarList(calendarList);
                    this.userFacade.edit(u);
                }
            }

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

    /**
     * Method that gets substring divided by ; from a String.
     * @param sharedUsers String of the input text; all users must be divided by ";"
     * @return a List that contains the username of the shared users. if no username is entere an empy list is returne;
     */
    private List<String> getUsername(String sharedUsers) {
        sharedUsers = sharedUsers.trim();
        
        if (sharedUsers.length() == 0) {
            return new ArrayList<>();
        }

        String[] sharedUsername = sharedUsers.split(";");
        for (int i = 0; i < sharedUsername.length; i++) {
            sharedUsername[i] = sharedUsername[i].replaceAll(" ", "");
        }
        return Arrays.asList(sharedUsername);
    }

}
