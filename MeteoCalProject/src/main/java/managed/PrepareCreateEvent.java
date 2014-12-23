/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jsf.classes.BadconditionsController;
import jsf.classes.EventController;

/**
 *
 * @author Walter
 */

@ManagedBean(name = "prepareCreateEvent", eager = true)
@SessionScoped
public class PrepareCreateEvent {

    public static int ID =0;
    
    
    // variables useful to set the correct events in the database
    
    private Date endate;
    
    private Date untillDate;
    
    private String invitations;
    
    private String repeats;
    
    //end variables

    EventController event = new EventController();
    BadconditionsController badconditions = new BadconditionsController();
    
    public void controlCreation() {
        ID++;
        event.getEvent().setId(ID);
        badconditions.getBadconditions().setEventID(event.getEvent());
        event.create();
        badconditions.create();
    }
    
    
    // variables not belonging to database
    
    public Date getEndate() {
        return endate;
    }

    public void setEndate(Date date) {
        this.endate = date;
    }
    
    public Date getUntillDate() {
        return untillDate;
    }

    public void setUntilldate(Date date) {
        this.untillDate = date;
    }
    
    public String getRepeats() {
        return repeats;
    }

    public void setRepeats(String rep) {
        this.repeats = rep;
    }
    
    public String getInvitations() {
        return invitations;
    }

    public void setInvitations(String invitations) {
        this.invitations = invitations;
    }
    
    // end variables

    
}
