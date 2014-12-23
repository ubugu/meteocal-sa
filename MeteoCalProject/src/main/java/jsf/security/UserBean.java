/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.security;

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
public class UserBean{

    @EJB
    UserFacade um;
    
    public UserBean() {
    }
    
    public String getName() {
        return um.getLoggedUser().getUsername();
    }
    
}
