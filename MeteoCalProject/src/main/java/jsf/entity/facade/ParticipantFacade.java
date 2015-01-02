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
import jsf.entity.Event;
import jsf.entity.Participant;

/**
 *
 * @author claudio
 */
@Stateless
public class ParticipantFacade extends AbstractFacade<Participant> {
    @PersistenceContext(unitName = "com.mycompany_MeteoCalProject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipantFacade() {
        super(Participant.class);
    }
    
    public List<Participant> searchByEvent (int eventID)  {
        return (List<Participant>) em.createNamedQuery("Participant.findByEvent").setParameter("event", eventID).getResultList();
    } 
    
    public List<Event> searchAcceptedEventByUsername (String username)  {
        return (List<Event>) em.createNamedQuery("Participant.findAcceptedEventByUser").setParameter("user", username).getResultList();
    } 
    
}
