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
     * method that will return the unique bad condition that is associated to this event, if there are more bad conditions in future we have to update this field
     * @param event, event of the bad conditions that we want to retrieve
     * @return Badcondition associate to the event
     */
    public Badconditions searchByEvent(Event event) {
        try {
            return (Badconditions) em.createNamedQuery("Badconditions.findByEvent").setParameter("event", event).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
