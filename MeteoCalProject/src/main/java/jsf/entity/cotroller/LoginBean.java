/*
 * Bean that manages the login.
 */
package jsf.entity.cotroller;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import jsf.entity.User;
import jsf.entity.facade.UserFacade;
import org.primefaces.context.RequestContext;

/**
 *
 * @author claudio
 */
@ManagedBean( name= "loginBean" , eager = true)
@RequestScoped
public class LoginBean implements Serializable{
    

    private String username;
    private String password;
    @EJB
    UserFacade facade;

    public LoginBean() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Perform login.
     * @return redirect to the user homepage.
     */
    public String login() {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(this.username, this.password);
            return "mainUserPage"; 
        } catch (ServletException ex) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('loginFailed').show();");
            return "";   
        }          
    }

    /**
     * @return  the logged User
     */
    public User getLoggedUser() {
        return facade.getLoggedUser();
    }
    
    /**
     * perfom logout.
     * @return redirect to the guest homepage.
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().invalidate();
        return "/index?faces-redirect=true";
    }
}

