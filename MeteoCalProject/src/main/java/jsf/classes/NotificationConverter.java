/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes;

import entity.bean.Notification;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Walter
 */
public class NotificationConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Integer id = new Integer(string);
        NotificationController controller = (NotificationController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "notification");
        return controller.getJpaController().find(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Notification) {
            Notification o = (Notification) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: entity.bean.Notification");
        }
    }
    
}
