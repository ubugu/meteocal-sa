/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes.controller.bean;

import entity.bean.Weather;
import entity.bean.WeatherPK;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import session.bean.WeatherFacade;

/**
 *
 * @author Walter
 */
public class WeatherConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        WeatherPK id = getId(string);
        WeatherController controller = (WeatherController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "weather");
        return controller.getJpaController().find(id);
    }

    WeatherPK getId(String string) {
        WeatherPK id = new WeatherPK();
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
        id.setCity(params[0]);
        id.setDate((Date) FacesContext.getCurrentInstance().getApplication().createConverter(Date.class).getAsObject(FacesContext.getCurrentInstance(), null, params[1]));
        return id;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Weather) {
            Weather o = (Weather) object;
            WeatherPK id = o.getWeatherPK();
            if (id == null) {
                return "";
            }
            String delim = "#";
            String escape = "~";
            String city = id.getCity();
            city = city == null ? "" : city.replace(escape, escape + escape);
            city = city.replace(delim, escape + delim);
            Object dateObj = id.getDate();
            String date = dateObj == null ? "" : String.valueOf(dateObj);
            date = date.replace(escape, escape + escape);
            date = date.replace(delim, escape + delim);
            return city + delim + date;
            // TODO: no setter methods were found in your primary key class
            //    entity.bean.WeatherPK
            // and therefore getAsString() method could not be pre-generated.
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: entity.bean.Weather");
        }
    }
    
}
