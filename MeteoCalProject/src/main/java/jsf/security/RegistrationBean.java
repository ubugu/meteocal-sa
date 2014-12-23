/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.security;

import entity.bean.User;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import session.bean.UserFacade;

/**
 *
 * @author miglie
 */
@Named
@RequestScoped
public class RegistrationBean {

    @EJB
    private UserFacade um;

    private User user;

    public RegistrationBean() {
    }

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String register() {
        um.save(user);
        return "user/home?faces-redirect=true";
    }

}
