/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


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
    

    
    public void controlCreation() {
        ID++;
      
    }
    
    // variables not belonging to database
    
    public Date getEndate() {
        return endate;
    }

    public void setEndate(Date date) {
        this.endate = date;
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
    
    public Date getUntillDate() {
        return untillDate;
    }

    public void setUntillDate(Date untillDate) {
        this.untillDate = untillDate;
    }
    
    // end variables

}
