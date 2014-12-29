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
import jsf.entity.facade.UserFacade;
import org.joda.time.DateTime; 
import org.joda.time.Days;
import org.primefaces.context.RequestContext;

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
    @EJB
    private BadconditionsFacade badconditionsFacade = new BadconditionsFacade();
    @EJB
    private UserFacade userFacade = new UserFacade();
    
    
    // variables useful to set the correct events in the database
    
    private Date endate;
    
    private Date startdate;
    
    private Date untillDate;
    
    private String invitations;
    
    private String repeats = "no" ;
    
    private Boolean bad = false;
    
    private Boolean temp = false;
    
    private Boolean prec = false;
    
    private Boolean InviteSelect = false;
    
    private String[] invitatedUsers;
    
    private String rejectedUsers;
        
    // variables not belonging to database
    
    public String[] getInvitatedUsers() {
        return invitatedUsers;
    }

    public void setInvitatedUsers(String[] invitatedUsers) {
        this.invitatedUsers = invitatedUsers;
    }
    
    public String getRejectedUsers() {
        return rejectedUsers;
    }

    public void setRejectedUsers(String rejectedUsers) {
        this.rejectedUsers = rejectedUsers;
    }
    
    public Boolean getInviteSelect() {
        return InviteSelect;
    }

    public void setInviteSelect(Boolean InviteSelect) {
        this.InviteSelect = InviteSelect;
    }
    
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
    
    public Boolean getTemp() {
        return temp;
    }

    public void setTemp(Boolean temp) {
        this.temp = temp;
    }

    public Boolean getPrec() {
        return prec;
    }

    public void setPrec(Boolean prec) {
        this.prec = prec;
    }
    // end variables
    
    public String controlDataCreation(){
        
        Boolean error = false;
        
        //controlData

        if( event.getDate().compareTo(new Date()) < 0){
           RequestContext requestContext = RequestContext.getCurrentInstance();  
           requestContext.execute("PF('NoPast Error').show();");
           error=true;
        }
                
        if( getEndate().compareTo(event.getDate()) < 0){
           RequestContext requestContext = RequestContext.getCurrentInstance();  
           requestContext.execute("PF('EndDate Error').show();");
           error=true;
        }
        
        if( event.getEndingTime().compareTo(event.getStartingTime()) < 0){
            RequestContext requestContext = RequestContext.getCurrentInstance();  
            requestContext.execute("PF('EndTime Error').show();");
            error=true;
        }
        
        if( getUntillDate().compareTo(event.getDate()) < 0){
            RequestContext requestContext = RequestContext.getCurrentInstance();  
            requestContext.execute("PF('EndUntillDate Error').show();");
            error=true;
        }
        
        if( (!getRepeats().equals("no")) && (getEndate().compareTo(event.getDate())!=0) ){
            RequestContext requestContext = RequestContext.getCurrentInstance();  
            requestContext.execute("PF('Repeat Error').show();");
            error=true;
        }
        
        //it will return false also if the field is disabled
        if(!searchInvited()){
            RequestContext requestContext = RequestContext.getCurrentInstance();  
            requestContext.execute("PF('Invite Error').show();");
            error=true;
        }
        
        if(error){
           return ""; 
        }
        
        //creationEvent
        prepareCreateEvent();
        
        return "Success" ;
    }
    
    /**
     * method that will control if the users invited by the organiser exists or not;
     * @return if some of they does not exist a false value is returned
     */
    private Boolean searchInvited(){
        
        if(getInvitations().equals("")){
            return false;
        }
        
        setInvitatedUsers(getInvitations().split(";"));
        
        for (String invitatedUser : getInvitatedUsers()) {
            if (userFacade.searchForUser(invitatedUser) == null) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * method that will control the creation of the events and then it will use
     * the facades to create the records in the database
     */ 
    private void prepareCreateEvent(){
        
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
            
            event.setId( eventFacade.getMaxEventID() + 1);
            
            //event creation
            try{
                eventFacade.create(event);
            }catch(Exception e){
                //TODO
            };
            
            //badcondition creation if they are set
            if(getBad()){
                prepareCreateBadConditions(event);
            }
            
            //create notification
            prepareCreateNotification(event);
            
            //create participant
            prepareCreateParticipant(event);
            
            //repetition
            switch(getRepeats()){
                case "everyday"   : {
                    nextDate= (new DateTime(nextDate).plusDays(1).toDate());
                    break;
                }
                case "everyweek"  : {
                    nextDate= (new DateTime(nextDate).plusWeeks(1).toDate());
                    break;
                }
                case "everymonth" : {
                    nextDate= (new DateTime(nextDate).plusMonths(1).toDate());
                    break;
                }
                case "everyyear"  : {
                    nextDate= (new DateTime(nextDate).plusYears(1).toDate());
                    break;
                }
            }
            
            event.setDate(nextDate);
            
        }
    }
    
    /**
     * creation in case of no repetitions
     * if the event last for more than 1 day, we save the ending time in a variable
     * after having calculate the days in which we have to repeat the event we set the starting time to
     * 00:00 and the ending time to 23:59 unless the days is the first or the last
     */
    private void normalCreation(){
        int days = 0;
        Date endingTime=null;
        if( getEndate().compareTo(event.getDate()) > 0){
            
            days = Days.daysBetween(new DateTime(event.getDate()), new DateTime(getEndate())).getDays();            
            endingTime = event.getEndingTime();
        }
        
        for(int i=0; i <= days; i++ ){
        
            event.setId( eventFacade.getMaxEventID() + 1);
            
            //if the cycle is repeated then the successive starting date must be set to 00:00
            // we have to do this only one time.
            if(i==1){
                event.setStartingTime(new Date(Time.valueOf("00:00:00").getTime()));
            }
            if(i+1 > days && days > 1){
                event.setEndingTime(endingTime);
            }else{ 
                if(days!=0){
                    event.setEndingTime(new Date(Time.valueOf("23:59:59").getTime()));
                }
            }
            
            //we can try to create the event
            
            try{
                eventFacade.create(event);
            }catch(Exception e){
                //TODO
            }
            
            //badcondition creation if they are set
            if(getBad()){
                prepareCreateBadConditions(event);
            }
            
            //create notification
            prepareCreateNotification(event);
            
            //create participant
            prepareCreateParticipant(event);
            
            
            if(days>1){            
                DateTime datetime = new DateTime(event.getDate());
                datetime = datetime.plusDays(1);
                event.setDate(datetime.toDate());
            }
            
        }
    }
    
    /**
     * prepare and create the bad conditions associated to the event
     * @param event in order to set the foreign key
     */
    private void prepareCreateBadConditions(Event event){
        
        badconditions.setId( badconditionsFacade.getMaxBadConditionsID() + 1 );
        badconditions.setEventID(event);
        
        if(temp==false){
            badconditions.setTemperature(null);
        }
        
        if(prec==false){
            badconditions.setPrecipitations(null);
        }
        
        //we can try to create the badconditions
            
        try{
            System.out.print("layer = " + badconditions.getLayer() + " temp: " + badconditions.getTemperature() + "prec: " + badconditions.getPrecipitations());
            badconditionsFacade.create(badconditions);
        }catch(Exception e){
            //TODO
        }
        
    }
    
    /**
     * method that will create and send a notification to all the invited User for the current Event
     * @param event 
     */
    private void prepareCreateNotification(Event event){
        
    }
   
    /**
     * method that will create a record in the participant table in which the organiser is setted 
     * and the invited User are setted to "unknown" response to the participation
     * @param event 
     */
    private void prepareCreateParticipant(Event event){
        
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
