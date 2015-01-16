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
import jsf.entity.Notification;
import jsf.entity.User;

/**
 *
 * @author claudio
 */
@Stateless
public class NotificationFacade extends AbstractFacade<Notification> {
    @PersistenceContext(unitName = "com.mycompany_MeteoCalProject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
     public List<Notification> searchForUser (User username)  {
        return (List<Notification>) em.createNamedQuery("Notification.findByUser").setParameter("user", username).getResultList();
    } 
   
      public List<Notification> searchByEventID (int id)  {
        return (List<Notification>) em.createNamedQuery("Notification.findByEventID").setParameter("eventID", id).getResultList();
    } 
     
    
    public NotificationFacade() {
        super(Notification.class);
    }
    
}
