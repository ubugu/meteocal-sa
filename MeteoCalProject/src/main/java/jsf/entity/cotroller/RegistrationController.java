/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import jsf.entity.User;
import jsf.entity.facade.UserFacade;

/**
 *
 * @author claudio
 */
@Named(value = "registration")
@RequestScoped
public class RegistrationController {
    
    private User user;
    private UserFacade facade;
    
    /**
     * Creates a new instance of RegistrationController
     */
    public RegistrationController() {
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
    
    public void register() {
        this.facade.create(user);
    }
    
}
