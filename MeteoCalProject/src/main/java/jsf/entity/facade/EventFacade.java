/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.facade;

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
    
    public List<Event> searchByCalendar(Calendar calendar) {
        return (List<Event>) em.createNamedQuery("Event.findByCalendar").setParameter("calendar", calendar).getResultList(); 
    }
    
}
