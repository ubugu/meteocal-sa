/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import jsf.entity.Event;
import jsf.entity.Notification;
import jsf.entity.facade.NotificationFacade;
import jsf.entity.facade.UserFacade;

/**
 *
 * @author claudio
 */
@ManagedBean(name = "notificationsController", eager = true)
@SessionScoped
public class NotificationsController {
    private int ID = 0;
    private enum NotificationType {
        INVITED, UPDATE, RESPONSE, BADCONDITIONS,
    }
    
    private enum BadConditionsType {
        THREEDAYS, ONEDAY
    }
    
    private Notification notification;
    private Notification selectedNotification;
    
    @EJB
    private NotificationFacade facade = new NotificationFacade();
    @EJB
    private UserFacade userFacade = new UserFacade();
    
    
    public List<Notification> getUserNotification() {
        //TODO modificare mettendo l'user loggato
        return this.facade.findAll();
    }
    
    public int searchForLastID() {
        return   facade.findAll().size();
    }

    public void createResponseNotification(Event event, String user) {
        notification = new Notification();
        this.ID = searchForLastID();
        notification.setEventID(event);
        notification.setId(ID);
        notification.setUser(this.userFacade.searchForUser(user));
        notification.setVisualized("NO");
    }
    
    public void createBadConditionsNotification (Event event, BadConditionsType type, String user) {
        notification = new Notification();
        this.ID = searchForLastID();
        notification.setEventID(event);
        notification.setId(ID);
        notification.setUser(this.userFacade.searchForUser(user));
        notification.setVisualized("NO");
    }
    
    public void createNotification(String type, Event event, String user){
        notification = new Notification();
        this.ID = searchForLastID();
        notification.setEventID(event);
        notification.setId(ID);
        notification.setUser(this.userFacade.searchForUser(user));
        notification.setVisualized("NO");
        
        String description = new String();
        
       if (type.equals(NotificationType.INVITED.toString())) {
           description += "You have been invited to the event " + event.getTitle() + " by the user " + event.getCalendar().getOwner() + " on the " + event.getDate();
           notification.setType(type);
       } 
           
       if (type.equals(NotificationType.UPDATE.toString())) {
            description += "The event " + /*event.getTitle() + */ " has been modified";
            notification.setType(type);
        }

        notification.setDescription(description);

        facade.create(notification);  
   
    }
    
    public String isVisualized(Notification notification) {
        if (notification.getVisualized().equals("NO")) {
            return "none";
        } else {
            return "display";
        }
    }
    
    public String isNotVisualized(Notification notification) {
        if (notification.getVisualized().equals("YES")) {
            return "none";
        } else {
            return "display";
        }
    }
    
   
    public Notification getSelectedNotification() {
        return selectedNotification;
    }

    public void setSelectedNotification(Notification selectedNotification) {
        this.selectedNotification = selectedNotification;
       
        if (selectedNotification.getVisualized().equals("NO")) {
            selectedNotification.setVisualized("YES");
            facade.edit(selectedNotification);
        }
            
    }
}
