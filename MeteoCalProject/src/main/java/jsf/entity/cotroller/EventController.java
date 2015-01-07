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
import jsf.entity.Notification;
import jsf.entity.Participant;
import jsf.entity.ParticipantPK;
import jsf.entity.facade.NotificationFacade;
import jsf.entity.facade.ParticipantFacade;
import jsf.entity.facade.UserFacade;
import org.joda.time.DateTime; 
import org.joda.time.DateTimeComparator;
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
    
    private Notification notification = new Notification();
    
    private Participant participant = new Participant();
    
    @EJB
    private EventFacade eventFacade = new EventFacade();
    @EJB
    private BadconditionsFacade badconditionsFacade = new BadconditionsFacade();
    @EJB
    private UserFacade userFacade = new UserFacade();
    @EJB
    private NotificationFacade notificationFacade = new NotificationFacade();
    @EJB
    private ParticipantFacade participantFacade = new ParticipantFacade();
    
    
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
    
    private String rejectedUsers="";
    
    private String style = "none";

    private Boolean edit = false;
    
    private Boolean editAddingBad = false;

    // end variables not belonging to database 
    
    public Boolean getEditAddingBad() {
        return editAddingBad;
    }

    public void setEditAddingBad(Boolean editAddingBad) {
        this.editAddingBad = editAddingBad;
    }
    
    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }
    
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
    
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
        RequestContext requestContext = RequestContext.getCurrentInstance();
        //controlData

        if(  eventFacade.dateAndTimeInTheMiddle(event.getDate(),getEndate(),event.getStartingTime(),event.getEndingTime(),userFacade.getLoggedUser().getCalendar().getId(),userFacade.getLoggedUser().getUsername(),event.getId())  ){
            requestContext.execute("PF('DateInTheMiddle Error').show();");
            error=true;
        }
        
        if(DateTimeComparator.getDateOnlyInstance().compare( new DateTime(event.getDate()) , new DateTime()) < 0){     
            requestContext.execute("PF('NoPast Error').show();");
            error=true;
        }
        
        if((DateTimeComparator.getTimeOnlyInstance().compare( new DateTime(event.getStartingTime()) , new DateTime()) < 0) && (DateTimeComparator.getDateOnlyInstance().compare( new DateTime() , new DateTime(event.getDate())) == 0)){
            requestContext.execute("PF('StartTime Error').show();");
            error=true;
        }
                    
        if(DateTimeComparator.getDateOnlyInstance().compare( new DateTime(getEndate()) , new DateTime(event.getDate())) < 0){
            requestContext.execute("PF('EndDate Error').show();");
            error=true;
        }
        
        if( (DateTimeComparator.getTimeOnlyInstance().compare( new DateTime(event.getEndingTime()) , new DateTime(event.getStartingTime())) < 0) && (DateTimeComparator.getDateOnlyInstance().compare( new DateTime(getEndate()) , new DateTime(event.getDate())) == 0)){
            requestContext.execute("PF('EndTime Error').show();");
            error=true;
        }
        
        if((!getRepeats().equals("no")) && (getUntillDate()!=null) && (DateTimeComparator.getDateOnlyInstance().compare( new DateTime(getUntillDate()) , new DateTime(event.getDate())) < 0)){
            requestContext.execute("PF('EndUntillDate Error').show();");
            error=true;
        }
        
        if((!getRepeats().equals("no")) && (DateTimeComparator.getDateOnlyInstance().compare( new DateTime(getEndate()) , new DateTime(event.getDate())) != 0)){
            requestContext.execute("PF('Repeat Error').show();");
            error=true;
        }
        
        if((!getRepeats().equals("no")) && (getUntillDate()==null)){
            requestContext.execute("PF('Untill Error').show();");
            error=true;
        }
        
        //it will return false also if the field is disabled
        if(getInviteSelect()){
            if(!searchInvited()){
                requestContext.execute("PF('Invite Error').show();");
                error=true;
            }
        }
        
        if(error){
           return ""; 
        }
        
        //creationEvent it will return the redirect
        String ret = prepareCreateEvent();
        if(ret.equals("")){
            requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('complete').show();");
        }
        
        return "";
    }

    /**
     * method that will control if the users invited by the organiser exists or not;
     * @return if some of they does not exist a false value is returned
     */
    private Boolean searchInvited(){
        
        Boolean result = true;
        
        if(getInvitations()== null || getInvitations().equals("")){
            return false;
        }
        
        setInvitatedUsers(getInvitations().split(";"));
        
        for (String invitatedUser : getInvitatedUsers()) {
            if(invitatedUser != null){
                if (userFacade.searchForUser(invitatedUser) == null) {
                    setRejectedUsers(getRejectedUsers() + invitatedUser + " ;");
                    result = false;
                }
            }
        }
        
        return result;
    }
    
    /**
     * method that will control the creation of the events and then it will use
     * the facades to create the records in the database
     * @return the redirection link to the jsf page
     */ 
    private String prepareCreateEvent(){
        
        //we can set here the calendar for the event
        event.setCalendar(userFacade.getLoggedUser().getCalendar());
        
        if(getRepeats().equals("no")){
            return normalCreation();
        }else{
            return repeatingCreation();
        }
        
    } 
    
    /**
     * creation in case of repetitions
     * @return the redirection link to a jsf page
     */
    private String repeatingCreation(){
        
        Date nextDate=event.getDate();
        
        Date controlDate=nextDate;
        
        //control loop, if there are only one event in the middle of the other an error message is sent to the user
 
        while(nextDate.compareTo(getUntillDate()) < 0){
            
            if(  eventFacade.dateAndTimeInTheMiddle(nextDate,nextDate,event.getStartingTime(),event.getEndingTime(),userFacade.getLoggedUser().getCalendar().getId(),userFacade.getLoggedUser().getUsername(),event.getId())  ){
                RequestContext requestContext = RequestContext.getCurrentInstance();        
                requestContext.execute("PF('DateInTheMiddleRepeat Error').show();");
                return "error";
            }
            
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
  
        }
        
        //reset to controlDate
        
        nextDate = controlDate;
        
        //creation loop
        
        while(nextDate.compareTo(getUntillDate()) < 0){
            
            if(!edit){
                event.setId( eventFacade.getMaxEventID() + 1);
            }
            
            //event creation
            try{
                if(edit){
                    eventFacade.edit(event);
                }else{
                    eventFacade.create(event);
                }
            }catch(Exception e){
                //TODO
            };
            
            //badcondition creation if they are set
            if(getBad()){
                prepareCreateBadConditions();
            }
            
            //invite & notify if there are invited
            if(getInviteSelect() || edit ){
                
                //create notification
                prepareCreateNotification();
                
            }
            
            if(getInviteSelect()){
                
                //create participant
                prepareCreateParticipant();
            }
            
            //set the owner as a participant if it is not an update
            if(!edit){
                setOwnerParticipant();
            }
  
                
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
            
            if(  eventFacade.dateAndTimeInTheMiddle(nextDate,nextDate,event.getStartingTime(),event.getEndingTime(),userFacade.getLoggedUser().getCalendar().getId(),userFacade.getLoggedUser().getUsername(),event.getId())  ){
                RequestContext requestContext = RequestContext.getCurrentInstance();        
                requestContext.execute("PF('DateInTheMiddle Error').show();");
                return "";
            }
            
            event.setDate(nextDate);
            
        }
        
        return "";
    }
    
    /**
     * creation in case of no repetitions
     * if the event last for more than 1 day, we save the ending time in a variable
     * after having calculate the days in which we have to repeat the event we set the starting time to
     * 00:00 and the ending time to 23:59 unless the days is the first or the last
     */
    private String normalCreation(){
        int days = 0;
        Date endingTime=null;
        if( getEndate().compareTo(event.getDate()) > 0){
            
            days = Days.daysBetween(new DateTime(event.getDate()), new DateTime(getEndate())).getDays(); 
            endingTime = event.getEndingTime();
        }
        
        Date controlDate = event.getDate();
        
        //check error loop
        for(int i=0; i <= days; i++ ){
            if(  eventFacade.dateAndTimeInTheMiddle(event.getDate(),getEndate(),event.getStartingTime(),event.getEndingTime(),userFacade.getLoggedUser().getCalendar().getId(),userFacade.getLoggedUser().getUsername(),event.getId())  ){
                RequestContext requestContext = RequestContext.getCurrentInstance();        
                requestContext.execute("PF('DateInTheMiddleRepeat Error').show();");
                return "error";
            } 
            
            //add one day and repeat
            if(days>0){            
                DateTime datetime = new DateTime(event.getDate());
                datetime = datetime.plusDays(1);
                event.setDate(datetime.toDate());
            }
        }
        
        event.setDate(controlDate);
        
        //creation loop
        for(int i=0; i <= days; i++ ){
        
            if(!edit){
                event.setId( eventFacade.getMaxEventID() + 1);
            }
            
            //if the cycle is repeated then the successive starting date must be set to 00:00
            // we have to do this only one time.
            if(i==1){
                event.setStartingTime(new Date(Time.valueOf("00:00:00").getTime()));
            }
            if(i+1 > days && days > 0){
                event.setEndingTime(endingTime);
            }else{ 
                if(days!=0){
                    event.setEndingTime(new Date(Time.valueOf("23:59:59").getTime()));
                }
            }
                        
            //we can try to create the event
            try{
                if(edit){
                    eventFacade.edit(event);
                }else{
                    eventFacade.create(event);
                }
            }catch(Exception e){
                //TODO
            }
            
            //badcondition creation if they are set
            if(getBad()){
                prepareCreateBadConditions();
            }
            
            //invite & notify if there are invited
            if(getInviteSelect() || edit ){
                
                //create notification
                prepareCreateNotification();
                
            }
            
            if(getInviteSelect()){
                
                //create participant
                prepareCreateParticipant();
            }
            
            //set the owner as a participant if it is not an update
            if(!edit){
                setOwnerParticipant();
            }
            
            //add one day and repeat
            if(days>0){            
                DateTime datetime = new DateTime(event.getDate());
                datetime = datetime.plusDays(1);
                event.setDate(datetime.toDate());
            }
            
        }
        
        return "";
    }
    
    /**
     * prepare and create the bad conditions associated to the event
     * @param event in order to set the foreign key
     */
    private void prepareCreateBadConditions(){
        
        if((!edit && bad) || (editAddingBad && bad)){
            badconditions.setId( badconditionsFacade.getMaxBadConditionsID() + 1 );
            badconditions.setEventID(event);
                                
            if(temp==false){
                badconditions.setTemperature(null);
            }

            if(prec==false){
                badconditions.setPrecipitations(null);
            }
            
        }
        
        //we can delete it if it was the user decision or we can try to create the badconditions
            
        if( (!bad) && (badconditions.getId()!=null) ){
            badconditionsFacade.remove(badconditions);
        }else{
            try{
                if(edit && !editAddingBad){
                    badconditionsFacade.edit(badconditions);
                }else{
                    if(bad){
                        badconditionsFacade.create(badconditions);
                    }
                }           
            }catch(Exception e){
                //TODO
            }
        }
    }
    
    /**
     * method that will create and send a notification to all the invited User for the current Event
     * @param event 
     */
    private void prepareCreateNotification(){
        notification.setEventID(event);
        notification.setVisualized("NO");
        
        if(edit){
            prepareUpdateNotification();
        }
            
        if(getInviteSelect()){
            prepareInviteNotification();
        }
        
    }
   
    /**
     * method that will create a new Update Notification
     */
    private void prepareUpdateNotification() {
        notification.setType("UPDATE");
        notification.setDescription("The event " + event.getTitle() + "that will be held on the " + event.getDate() + " has been modified by " + event.getCalendar().getOwner().getUsername() );
 
        for(Participant participants : participantFacade.searchByEvent(event.getId())){
            //TODO prova
            notification.setId( notificationFacade.getMaxNotificationID() +1 );
            notification.setUser(participants.getUser1());
            notificationFacade.create(notification); 
        }
    }
        
    /**
     * method that will create a new Invite Notification
     */
    private void prepareInviteNotification(){
        notification.setType("INVITED");
        notification.setDescription("You have been invited to the event " + event.getTitle() + " by the user " + event.getCalendar().getOwner().getUsername() + " on the " + event.getDate());
        
        for (String invitatedUser : getInvitatedUsers()) {
            notification.setId( notificationFacade.getMaxNotificationID() +1 );
            notification.setUser(userFacade.searchForUser(invitatedUser));
            notificationFacade.create(notification);
        }
    }
    
    /**
     * method that will create a record in the participant table in which the organiser is setted 
     * and the invited User are setted to "unknown" response to the participation
     * @param event 
     */
    private void prepareCreateParticipant(){
        //participants invited
        participant.setEvent1(event);
        participant.setOrganiser("NO");
        participant.setParticipant("UNKNOWN");
        for (String invitatedUser : getInvitatedUsers()) { 
            participant.setUser1(userFacade.searchForUser(invitatedUser));
            participant.setParticipantPK(new ParticipantPK(participant.getUser1().getUsername(),participant.getEvent1().getId()));
            participantFacade.create(participant);
        }    
    }
    
    /**
     * method useful to set in the database the owner as a participant
     */
    private void setOwnerParticipant(){
        participant.setEvent1(event);
        participant.setOrganiser("YES");
        participant.setUser1(event.getCalendar().getOwner());
        participant.setParticipant("YES");
        participant.setParticipantPK(new ParticipantPK(participant.getUser1().getUsername(),participant.getEvent1().getId()));
        participantFacade.create(participant);
    }
    
    /**
     * method that will be called in order to set to the Edit Mode the class addEvent
     * it will check and set the value of the event that the user want to change in 
     * order to show the precompiled field, this avoid us to create an updateeventcontroller
     * and an updateevent page
     * @param id of the event to update, is obtained by the schedule controller
     * @return redirect to the addeventpage
     */
    public String changeEvent(int id){
        
        event = eventFacade.find(id);
        badconditions = badconditionsFacade.searchByEvent(event);
        
        if(badconditions!=null){
            
            setBad(true);
            setStyle("block");
            
            if(badconditions.getPrecipitations()!=null){
                setPrec(true);
            }
            if(badconditions.getTemperature()!=null){
                setTemp(true);
            } 
            
            setEditAddingBad(false);

        }else{
            badconditions = new Badconditions();
            setEditAddingBad(true);
            setBad(false);
            setPrec(false);
            setTemp(false);
        }
        
        setInviteSelect(false);
        setEdit(true);
        setEndate(event.getDate());
        
        return "addEvent?faces-redirect=true"; 
    }
    
    /**
     * method that will be called in order to create new event and badconditions
     * @return redirect to the addeventpage
     */
    public String newEvent(){
        
        event = new Event();
        badconditions = new Badconditions();
        
        //reset all the field
        setPrec(false);
        setTemp(false);
        setBad(false);
        setStyle("none");
        setEdit(false);
        setEndate(null);
        setInvitations("");
        setInviteSelect(false);
        setRejectedUsers("");
        setUntillDate(null);
        setRepeats("no");
        setEditAddingBad(false);
        setTemp(false);
        setPrec(false);
        
        return "addEvent?faces-redirect=true"; 
    }
    
}
