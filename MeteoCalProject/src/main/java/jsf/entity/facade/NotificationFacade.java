/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsf.entity.Notification;

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

    public NotificationFacade() {
        super(Notification.class);
    }
    
    /**
     * method to get the 
     * @return the maximum number of all the ids of the records in the Notification table or 0 if there are no records
     *  
     */
    public int getMaxNotificationID(){
        try{
            return (Integer) em.createNativeQuery("Select MAX(ID) from Notification").getSingleResult();    
        }catch(Exception e){
            return 0;
        } 
    }
    
}
