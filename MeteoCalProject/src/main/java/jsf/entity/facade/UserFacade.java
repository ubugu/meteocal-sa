/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.facade;

import java.security.Principal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsf.entity.User;

/**
 *
 * @author claudio
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "com.mycompany_MeteoCalProject_war_1.0-SNAPSHOTPU")
    private EntityManager em ;

    @Inject
    Principal principal;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public User searchForUser (String username)  {
        return (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
    } 
    
      public User getLoggedUser() {
        return em.find(User.class, principal.getName());
    }
    
}
