/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;



import java.security.Principal;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsf.entity.User;

/**
 *
 * @author miglie
 */
@Stateless
public class UserManager {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    Principal principal;

    public void save(User user) {
        user.setGroupName("USER");
        em.persist(user);
    }

    public void unregister() {
        em.remove(getLoggedUser());
    }

    public User getLoggedUser() {
        return em.find(User.class, principal.getName());
    }
}
