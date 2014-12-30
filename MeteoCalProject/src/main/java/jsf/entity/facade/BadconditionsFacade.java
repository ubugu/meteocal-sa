/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsf.entity.Badconditions;
import jsf.entity.Event;

/**
 *
 * @author claudio
 */
@Stateless
public class BadconditionsFacade extends AbstractFacade<Badconditions> {
    @PersistenceContext(unitName = "com.mycompany_MeteoCalProject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BadconditionsFacade() {
        super(Badconditions.class);
    }
    
    /**
     * method to get the 
     * @return the maximum number of all the ids of the records in the BadConditions table or 0 if there are no records
     *  
     */
    public int getMaxBadConditionsID(){
        try{
            return (Integer) em.createNativeQuery("Select MAX(ID) from Badconditions").getSingleResult();    
        }catch(Exception e){
            return 0;
        }
    }

    public Badconditions searchByEvent(Event event) {
        try {
            return (Badconditions) em.createNamedQuery("Badconditions.findByEvent").setParameter("event", event).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
