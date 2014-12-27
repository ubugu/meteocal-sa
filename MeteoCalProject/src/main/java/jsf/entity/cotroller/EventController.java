/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import java.sql.Time;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jsf.entity.Badconditions;
import jsf.entity.Event;
import jsf.entity.facade.BadconditionsFacade;
import jsf.entity.facade.EventFacade;
import java.util.Date; 
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.joda.time.DateTime; 
import org.joda.time.Days;

/**
 *
 * @author Walter
 */
@ManagedBean( name= "EventController" , eager = true)
@SessionScoped
public class EventController {
    
    private Event event = new Event();

    private Badconditions badconditions = new Badconditions();
    
    @EJB
    private EventFacade eventFacade = new EventFacade();
    private BadconditionsFacade badconditionsFacade = new BadconditionsFacade();
    
    public static int ID =0;
    
    
    // variables useful to set the correct events in the database
    
    private Date endate;
    
    private Date startdate;
    
    private Date untillDate;
    
    private String invitations;
    
    private String repeats = "no" ;
    
    private Boolean bad = false;
        
    // variables not belonging to database
    
    public Boolean getBad() {
        return bad;
    }

    public void setBad(Boolean bad) {
        this.bad = bad;
    }
    
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
    
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
 
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
    public Badconditions getBadconditions() {
        return badconditions;
    }

    public void setBadconditions(Badconditions badconditions) {
        this.badconditions = badconditions;
    }
    // end variables
    
    /**
     * method that will control the creation of the events and then it will use
     * the facades to create the records in the database
     */ 
    public void prepareCreateEvent(){
        
        if(getRepeats().equals("no")){
            normalCreation();
        }else{
            repeatingCreation();
        }
        
    } 
    
    /**
     * creation in case of repetitions
     */
    private void repeatingCreation(){
        Date nextDate=event.getDate();
 
        while(nextDate.compareTo(getUntillDate()) < 0){
            
            event.setId(ID);
            
            //event creation
            try{
                eventFacade.create(event);
            }catch(Exception e){
            // TODO
            };
            
            //badcondition creation if they are set
            if(getBad()){
                prepareCreateBadConditions(event);
            }
            
            switch(getRepeats()){
                case "everyday"   : nextDate= (new DateTime(nextDate).plusDays(1).toDate());
                case "everyweek"  : nextDate= (new DateTime(nextDate).plusWeeks(1).toDate());
                case "everymonth" : nextDate= (new DateTime(nextDate).plusMonths(1).toDate());
                case "everyyear"  : nextDate= (new DateTime(nextDate).plusYears(1).toDate());
            }
            
            ID++;
            
        }
    }
    
    /**
     * creation in case of no repetitions
     * if the event last for more than 1 day, we save the ending time in a variable
     * after having calculate the days in which we have to repeat the event we set the starting time to
     * 00:00 and the ending time to 23:59 unless the days is the first or the last
     */
    private void normalCreation(){
        int days = 1;
        Time endingTime=null;
        if( getEndate().compareTo(event.getDate()) > 0){
            
            days = Days.daysBetween(new DateTime(event.getDate()), new DateTime(getEndate())).getDays();            
            endingTime = (Time) event.getEndingTime();
        }
        
        for(int i=0; i< days; i++ ){
        
            event.setId(ID);
            
            //if the cycle is repeated then the successive starting date must be set to 00:00
            // we have to do this only one time.
            if(i==1){
                event.setStartingTime(Time.valueOf("00:00"));
            }
            if(i+1 > days && days > 1){
                event.setEndingTime(endingTime);
            }else{
                event.setEndingTime(Time.valueOf("23:59"));
            }
            
            //we can try to create the event
            
            try{
                eventFacade.create(event);
            }catch(Exception e){
                //TODO
            }
                        
            if(days>1 && i+1 < days){            
                DateTime datetime = new DateTime(event.getDate());
                datetime = datetime.plusDays(1);
                event.setDate(datetime.toDate());
            }
            
            ID++;
    
        }
    }
    
    /**
     * prepare and create the bad conditions associated to the event
     * @param event in order to set the foreign key
     */
    public void prepareCreateBadConditions(Event event){
        
    }
    
    // validators
    
    /**
     * control that The ending date is before the starting date
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException 
     */
    public void validateEndate(FacesContext context, UIComponent component, Object value) throws ValidatorException{
        if( getEndate().compareTo(event.getDate()) < 0){
            throw new ValidatorException(new FacesMessage("The ending date is before the starting time"));
        }
    }
    
    /**
     * control that The ending time is before the starting date
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException 
     */
    public void validateEndtime(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if( event.getEndingTime().compareTo(event.getStartingTime()) < 0){
            throw new ValidatorException(new FacesMessage("The ending time is before the starting time"));
        }
    }
    
    /**
     * control that The ending date is before the starting date
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException 
     */
    public void validateUntilldate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if( getUntillDate().compareTo(event.getDate()) < 0){
            throw new ValidatorException(new FacesMessage("The ending date is before the starting date"));
        }
    }
    
    /**
     * control that You can not repeat an event that last more than 1 day for now
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException 
     */
    public void validateRepeats(FacesContext context, UIComponent component, Object value) throws ValidatorException{
        if( (!getRepeats().equals("no")) && (getEndate().compareTo(event.getDate())!=0) ){
            throw new ValidatorException(new FacesMessage("You can not repeat an event that last more than 1 day for now"));
        }
    }
    
}
