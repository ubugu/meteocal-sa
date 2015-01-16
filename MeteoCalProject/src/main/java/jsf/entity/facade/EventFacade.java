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
     * method to get the 
     * @return the maximum number of all the ids of the records in the Event database or 0 if there are no records
     *  
     */
    public int getMaxEventID(){
        try{
            return (Integer) em.createNativeQuery("Select MAX(ID) from Event").getSingleResult();    
        }catch(Exception e){
            return 0;
        } 
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
    public Boolean dateAndTimeInTheMiddle(Date startDate,Date endDate,Date startTime, Date endTime,int CalendarID,String usern,int eventID) {
        java.sql.Date startSqlDate;
        startSqlDate = new java.sql.Date(startDate.getTime());
        java.sql.Date endSqlDate;
        endSqlDate = new java.sql.Date(endDate.getTime());
        java.sql.Time startSqlTime;
        startSqlTime = new java.sql.Time(startTime.getTime());
        java.sql.Time endSqlTime;
        endSqlTime = new java.sql.Time(endTime.getTime());
        
        try{
            List<Event> events = em.createNamedQuery("Event.findDateTimeInTheMiddle").setParameter("startSqlDate",startSqlDate).setParameter("endSqlDate",endSqlDate).setParameter("startSqlTime",startSqlTime).setParameter("endSqlTime",endSqlTime).setParameter("calendarId",CalendarID).setParameter("username",usern).getResultList();

            if(!events.isEmpty()){
                if( events.get(0).getId()!= eventID ){
                    System.out.println(eventID);
                    System.out.println("ci sono risultati o mona");
                    System.out.println(em.createNamedQuery("Event.findDateTimeInTheMiddle").setParameter("startSqlDate",startSqlDate).setParameter("endSqlDate",endSqlDate).setParameter("startSqlTime",startSqlTime).setParameter("endSqlTime",endSqlTime).setParameter("calendarId",CalendarID).setParameter("username",usern).getResultList());
                    return true;
                }else{
                    return false;
                }
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
    public Boolean dateAndTimeInTheMiddleCreate(Date startDate,Date endDate,Date startTime, Date endTime,int CalendarID,String usern) {
        java.sql.Date startSqlDate;
        startSqlDate = new java.sql.Date(startDate.getTime());
        java.sql.Date endSqlDate;
        endSqlDate = new java.sql.Date(endDate.getTime());
        java.sql.Time startSqlTime;
        startSqlTime = new java.sql.Time(startTime.getTime());
        java.sql.Time endSqlTime;
        endSqlTime = new java.sql.Time(endTime.getTime());
        
        try{
            List<Event> events = em.createNamedQuery("Event.findDateTimeInTheMiddle").setParameter("startSqlDate",startSqlDate).setParameter("endSqlDate",endSqlDate).setParameter("startSqlTime",startSqlTime).setParameter("endSqlTime",endSqlTime).setParameter("calendarId",CalendarID).setParameter("username",usern).getResultList();

            if(!events.isEmpty()){
                    System.out.println("ci sono risultati o mona");
                    System.out.println(em.createNamedQuery("Event.findDateTimeInTheMiddle").setParameter("startSqlDate",startSqlDate).setParameter("endSqlDate",endSqlDate).setParameter("startSqlTime",startSqlTime).setParameter("endSqlTime",endSqlTime).setParameter("calendarId",CalendarID).setParameter("username",usern).getResultList());
                    return true;
            }else{
                return false;
            }
            
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
    
    
    
}
