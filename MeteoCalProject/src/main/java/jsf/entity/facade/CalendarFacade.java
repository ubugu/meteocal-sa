/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsf.entity.Calendar;
import jsf.entity.User;

/**
 *
 * @author claudio
 */
@Stateless
public class CalendarFacade extends AbstractFacade<Calendar> {
    @PersistenceContext(unitName = "com.mycompany_MeteoCalProject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CalendarFacade() {
        super(Calendar.class);
    }
    
     public Calendar searchByUser(User user){
        try{
            return (Calendar) em.createNamedQuery("Calendar.findByOwner").setParameter("owner", user).getSingleResult();    
        }catch(Exception e){
            return null;
        } 
    }
    
}
