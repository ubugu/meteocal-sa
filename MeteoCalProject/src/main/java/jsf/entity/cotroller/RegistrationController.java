/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import jsf.entity.User;
import jsf.entity.facade.UserFacade;
import org.primefaces.context.RequestContext;

/**
 *
 * @author claudio
 */
@ManagedBean(name = "registrationController", eager = true)
@RequestScoped
public class RegistrationController {
    
    private User user;
    @EJB
    private UserFacade facade = new UserFacade();


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

    public String register() {
        user.setGroupName("USER");
        if (checkEmail()) {
            return "";
        }
        try {
            facade.create(user);
        } catch (Exception e) {
            e.getMessage();
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('usernameError').show();");
            return "";
        }
        return "index?faces-redirect=true";
    }

    private boolean checkEmail() {
        List<User> allUser = facade.findAll();
  
        for (User u : allUser) {
            if (u.getEmail().equals(user.getEmail())) {
                RequestContext requestContext = RequestContext.getCurrentInstance();  
                requestContext.execute("PF('emailError').show();");
                return true;
            }
        }
       return false;
    }
    

    
}
