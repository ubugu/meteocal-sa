/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes.controller.bean;

import entity.bean.Participant;
import entity.bean.ParticipantPK;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import session.bean.ParticipantFacade;

/**
 *
 * @author Walter
 */
public class ParticipantConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        ParticipantPK id = getId(string);
        ParticipantController controller = (ParticipantController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "participant");
        return controller.getJpaController().find(id);
    }

    ParticipantPK getId(String string) {
        ParticipantPK id = new ParticipantPK();
        String[] params = new String[2];
        int p = 0;
        int grabStart = 0;
        String delim = "#";
        String escape = "~";
        Pattern pattern = Pattern.compile(escape + "*" + delim);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String found = matcher.group();
            if (found.length() % 2 == 1) {
                params[p] = string.substring(grabStart, matcher.start());
                p++;
                grabStart = matcher.end();
            }
        }
        if (p != params.length - 1) {
            throw new IllegalArgumentException("string " + string + " is not in expected format. expected 2 ids delimited by " + delim);
        }
        params[p] = string.substring(grabStart);
        for (int i = 0; i < params.length; i++) {
            params[i] = params[i].replace(escape + delim, delim);
            params[i] = params[i].replace(escape + escape, escape);
        }
        id.setUser(params[0]);
        id.setEvent(Integer.parseInt(params[1]));
        return id;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Participant) {
            Participant o = (Participant) object;
            ParticipantPK id = o.getParticipantPK();
            if (id == null) {
                return "";
            }
            String delim = "#";
            String escape = "~";
            String user = id.getUser();
            user = user == null ? "" : user.replace(escape, escape + escape);
            user = user.replace(delim, escape + delim);
            String event = String.valueOf(id.getEvent());
            event = event.replace(escape, escape + escape);
            event = event.replace(delim, escape + delim);
            return user + delim + event;
            // TODO: no setter methods were found in your primary key class
            //    entity.bean.ParticipantPK
            // and therefore getAsString() method could not be pre-generated.
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: entity.bean.Participant");
        }
    }
    
}
