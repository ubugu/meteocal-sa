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
 * @author Walter
 */
@Named
@RequestScoped
public class UserSecurityBean {
  
    @EJB
    UserFacade um;
    
    public UserSecurityBean() {
    }
    
    public String getName() {
        return um.getLoggedUser().getUsername();
    }
    
  
}
