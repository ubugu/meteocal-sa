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
import jsf.entity.Badconditions;
import jsf.entity.Event;
import jsf.entity.Notification;
import jsf.entity.Participant;
import jsf.entity.User;
import jsf.entity.facade.BadconditionsFacade;
import jsf.entity.facade.NotificationFacade;
import jsf.entity.facade.ParticipantFacade;
import jsf.entity.facade.UserFacade;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

/**
 *
 * @author claudio
 */
@ManagedBean( name= "showEventController" , eager = true)
@SessionScoped
public class ShowEventController {

    private LineChartModel multiAxisModel;
    Event selectedEvent;
    
    @EJB
    ParticipantFacade participantFacade;
    @EJB
    BadconditionsFacade badConditionsFacade;
    @EJB
    NotificationFacade notificationFacade;
    @EJB
    UserFacade userFacade;
    
    private List<Participant> participants;
    private List<Participant> invited;

    private Badconditions badConditions;
    private User creator;

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public LineChartModel getMultiAxisModel() {
        return multiAxisModel;
    }

    public void setMultiAxisModel(LineChartModel multiAxisModel) {
        this.multiAxisModel = multiAxisModel;
    }

    
    
    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
    
    public Badconditions getBadConditions() {
        return badConditions;
    }

    public void setBadConditions(Badconditions badConditions) {
        this.badConditions = badConditions;
    }
    
    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
        int id = selectedEvent.getId();
        this.participants =  this.participantFacade.searchByEvent(id);
        setCreator();
    }
    
    public void setCreator() {
        for (Participant u : this.participants) { 
           if (u.getOrganiser().equals("YES")) {
                this.creator = u.getUser1();
            }
        }
    }
    
    public void loadParticipants() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('participants').show();");
    }
    
    public void initChart() {
         multiAxisModel = new LineChartModel();
 
        BarChartSeries boys = new BarChartSeries();
        boys.setLabel("Boys");
 
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);
 
        LineChartSeries girls = new LineChartSeries();
        girls.setLabel("Girls");
        girls.setXaxis(AxisType.X2);
        girls.setYaxis(AxisType.Y2);
         
        girls.set("A", 52);
        girls.set("B", 60);
        girls.set("C", 110);
        girls.set("D", 135);
        girls.set("E", 120);
 
        multiAxisModel.addSeries(boys);
        multiAxisModel.addSeries(girls);
         
        multiAxisModel.setTitle("Multi Axis Chart");
        multiAxisModel.setMouseoverHighlight(false);
         
        multiAxisModel.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        multiAxisModel.getAxes().put(AxisType.X2, new CategoryAxis("Period"));
         
        Axis yAxis = multiAxisModel.getAxis(AxisType.Y);
        yAxis.setLabel("Birth");
        yAxis.setMin(0);
        yAxis.setMax(200);
                 
        Axis y2Axis = new LinearAxis("Number");
        y2Axis.setMin(0);
        y2Axis.setMax(200);
         
        multiAxisModel.getAxes().put(AxisType.Y2, y2Axis);
    }
    
    public String isInvited() {
        User user = userFacade.getLoggedUser();
        
        for (Participant p : this.participants) {
            if (user.equals(p.getUser1())) {
                if (user.equals(this.creator)) {
                    return "none";
                } else {
                    return "display";
                }
            }
        }
        return "none";
    }
  

    public String getResponse() {
        User user = this.userFacade.getLoggedUser();
        for (Participant u : this.participants) {
            if (u.getUser1().equals(user)) {
                return u.getParticipant();
            }
        }
        return "ERROR";
    }
    
    public void setInvited(String response) {
        User user = this.userFacade.getLoggedUser();
         for (Participant u : this.participants) {
            if (u.getUser1().equals(user)) {
                u.setParticipant(response);
                this.participantFacade.edit(u);
            }
        }
        
        sendNotification(response, user);
    }
    
    public void sendNotification(String response, User user) {
        Notification notification = new Notification();
        notification.setEventID(selectedEvent);
        notification.setType("RESPONSE");
        notification.setVisualized("NO");
        String description = "";
        
        if (response.equals("YES")) {
           description += "The user " + user.getUsername() + " has accepted your invitation to the event " + selectedEvent.getTitle();  
        } else {
            description += "The user " + user.getUsername() + " has refused your invitation to the event " + selectedEvent.getTitle();
        }
        notification.setDescription(description);
        notification.setUser(this.creator);
        
        notification.setId(null);
        this.notificationFacade.create(notification);
    }
    
    public String hasBadCondition() {
        this.badConditions = badConditionsFacade.searchByEvent(selectedEvent);
        if (badConditions == null) {
            return "none";
        } else {
            return "display";
        }
    }
    
}
