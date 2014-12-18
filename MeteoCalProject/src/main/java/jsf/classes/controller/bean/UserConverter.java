/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes.controller.bean;

import entity.bean.User;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Walter
 */
public class UserConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        UserController controller = (UserController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "user");
        return controller.getJpaController().find(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof User) {
            User o = (User) object;
            return o.getUsername() == null ? "" : o.getUsername().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: entity.bean.User");
        }
    }
    
}
