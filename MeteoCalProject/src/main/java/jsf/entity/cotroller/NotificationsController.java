/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import jsf.entity.Notification;
import jsf.entity.facade.NotificationFacade;
import jsf.entity.facade.UserFacade;
import org.primefaces.context.RequestContext;

/**
 *
 * @author claudio
 */
@ManagedBean(name = "notificationsController", eager = true)
@SessionScoped
public class NotificationsController {
    @ManagedProperty(value="#{showEventController}")
    private ShowEventController eventController;
    
    private int ID = 0;
    private enum NotificationType {
        INVITED, UPDATE, RESPONSE, BADCONDITIONS, SYSTEM,
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
        List<Notification> list = facade.searchForUser(userFacade.getLoggedUser());
        return list;
    }
    
    public int searchForLastID() {
        return   facade.findAll().size();
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
    
    
    public void createNotification(){
        notification = new Notification();
        this.ID = searchForLastID();
        notification.setEventID(null);
        notification.setId(ID);
        notification.setUser(this.userFacade.searchForUser("squalo2"));
        notification.setVisualized("NO");
       
        String description = new String();
        
        description += "The event " + /*event.getTitle() + */ " has been modified";
        notification.setType("UPDATE");
       
        notification.setDescription(description);

        facade.create(notification);  
   
    }

    public void showEvent() {
        
    }
    
    public void setSelectedNotification(Notification selectedNotification) {
        this.selectedNotification = selectedNotification;
        
        if (selectedNotification.getVisualized().equals("NO")) {
            selectedNotification.setVisualized("YES");
            facade.edit(selectedNotification);
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('notificationDialog').show();");
  
    }
    
    public String hasEvent() {
        if (this.selectedNotification == null) {
            return "none";
        }
        if (this.selectedNotification.getType().equals(NotificationType.SYSTEM.toString())) {
            return "none";
        } else {
            return "display";
        }
    }
    
    public ShowEventController getEventController() {
        return eventController;
    }

    public void setEventController(ShowEventController eventController) {
        this.eventController = eventController;
    }
}
