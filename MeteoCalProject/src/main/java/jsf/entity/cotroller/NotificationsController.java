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
import jsf.entity.Event;
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
    ShowEventController eventController;

    private enum NotificationType {
        INVITED, UPDATE, RESPONSE, BADCONDITIONS, SYSTEM, DELETE,
    }
    
    private enum BadConditionsType {
        THREEDAYS, ONEDAY
    }
    
    private Notification notification;
    private Notification selectedNotification;
    
    @EJB
    private NotificationFacade facade;
    
    @EJB
    private UserFacade userFacade;
    
    public Notification getSelectedNotification() {
        return selectedNotification;
    }
    
    public ShowEventController getEventController() {
        return eventController;
    }

    public void setEventController(ShowEventController eventController) {
        this.eventController = eventController;
    }
    
    public List<Notification> getUserNotification() {
        List<Notification> list = facade.searchForUser(userFacade.getLoggedUser());
        return list;
    }
    
    /**
     * Check if a notification is visualized or not
     * @param notification to check
     * @return none if the notification hasn't been view yet, otherwise display.
     */
    public String isVisualized(Notification notification) {
        if (notification.getVisualized().equals("NO")) {
            return "none";
        } else {
            return "display";
        }
    }
    
    /**
     * Check if a notification is not visualized
     * @param notification
     * @return 
     */
    public String isNotVisualized(Notification notification) {
        if (notification.getVisualized().equals("YES")) {
            return "none";
        } else {
            return "display";
        }
    }


    /**
     * 
     * @return 
     */
    public String showEvent() {
        if (this.selectedNotification == null) {
            return "";
        }
        Event event = this.selectedNotification.getEventID();
        if (event == null) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('eventDeleted').show();");
            return "";
        } else {
            eventController.setSelectedEvent(event);
            eventController.initChart();
            return "/showEvent?faces-redirect=true";
           
        }

    }

    /**
     * Set the current selected notification and chage its status in the database from visualized = NO to visualized = YES
     * @param selectedNotification to set
     */
    public void setSelectedNotification(Notification selectedNotification) {
        this.selectedNotification = selectedNotification;
        
        if (selectedNotification.getVisualized().equals("NO")) {
            selectedNotification.setVisualized("YES");
            facade.edit(selectedNotification);
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('notificationDialog').show();");
  
    }
    
    /**
     * specify the selected notification has an event
     * @return none if the selectedNotification is null or its associated event is null otherwise will return display
     */
    public String hasEvent() {
        if (this.selectedNotification == null) {
            return "none";
        }
        if (this.selectedNotification.getEventID() == null) {
            return "none";
        } else {
            return "display";
        }
    }
}
