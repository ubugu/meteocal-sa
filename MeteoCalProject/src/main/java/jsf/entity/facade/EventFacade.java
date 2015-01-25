/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.facade;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsf.entity.Calendar;
import jsf.entity.Event;

/**
 *
 * @author claudio
 */
@Stateless
public class EventFacade extends AbstractFacade<Event> {
    @PersistenceContext(unitName = "com.mycompany_MeteoCalProject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventFacade() {
        super(Event.class);
    }

    /**
     * method that control if there are other event in the middle of the one the user want to create
     * @param startDate of the event that is going to be created
     * @param endDate of the event that is going to be created
     * @param startTime of the event that is going to be created
     * @param endTime of the event that is going to be created
     * @param CalendarID of the logged user
     * @param usern the username of the logged user
     * @param eventID the id of the current event, usefull to check for the update
     * @return true, if there are other event, of the logged user or where the logged user want to participate, in the middle, false otherwise
     */
    public Boolean dateAndTimeInTheMiddle(Date startDate,Date startTime, Date endTime,int CalendarID,String usern,int eventID) {
        java.sql.Date startSqlDate;
        startSqlDate = new java.sql.Date(startDate.getTime());
        java.sql.Time startSqlTime;
        startSqlTime = new java.sql.Time(startTime.getTime());
        java.sql.Time endSqlTime;
        endSqlTime = new java.sql.Time(endTime.getTime());
        
        try{
            List<Event> events = em.createNamedQuery("Event.findDateTimeInTheMiddle").setParameter("startSqlDate",startSqlDate).setParameter("startSqlTime",startSqlTime).setParameter("endSqlTime",endSqlTime).setParameter("calendarId",CalendarID).setParameter("username",usern).getResultList();

            if(!events.isEmpty()){
                return events.get(0).getId()!= eventID;
            }else{
                return false;
            }
            
        }catch(Exception e){
            //TODO
        }
        
        return false;
    }
   
    /**
     * method that control if there are other event in the middle of the one the user want to create
     * @param startDate of the event that is going to be created
     * @param endDate of the event that is going to be created
     * @param startTime of the event that is going to be created
     * @param endTime of the event that is going to be created
     * @param CalendarID of the logged user
     * @param usern the username of the logged user
     * @return true, if there are other event, of the logged user or where the logged user want to participate, in the middle, false otherwise
     */
    public Boolean dateAndTimeInTheMiddleCreate(Date startDate,Date startTime, Date endTime,int CalendarID,String usern) {
        java.sql.Date startSqlDate;
        startSqlDate = new java.sql.Date(startDate.getTime());
        java.sql.Time startSqlTime;
        startSqlTime = new java.sql.Time(startTime.getTime());
        java.sql.Time endSqlTime;
        endSqlTime = new java.sql.Time(endTime.getTime());
        
        try{
            List<Event> events = em.createNamedQuery("Event.findDateTimeInTheMiddle").setParameter("startSqlDate",startSqlDate).setParameter("startSqlTime",startSqlTime).setParameter("endSqlTime",endSqlTime).setParameter("calendarId",CalendarID).setParameter("username",usern).getResultList();
            return !events.isEmpty();          
        }catch(Exception e){
            //TODO
        }     
        return false;
    }

    public List<Event> searchByCalendar(Calendar calendar) {
        return (List<Event>) em.createNamedQuery("Event.findByCalendar").setParameter("calendar", calendar).getResultList(); 
    }
    
    public List<Event> searchByNullWeather() {
        return (List<Event>) em.createNamedQuery("Event.findNullWeather").getResultList(); 
    }

    public boolean isAlreadyThere(Integer eventID) {
            if(eventID==null){
                return false;
            }
            return find(eventID)!=null;
    }
       
}
