/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes.controller.bean;

import entity.bean.Weather;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.faces.FacesException;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import jsf.classes.controller.bean.util.JsfUtil;
import entity.bean.WeatherPK;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import jsf.classes.controller.bean.util.PagingInfo;
import session.bean.WeatherFacade;

/**
 *
 * @author Walter
 */
public class WeatherController {

    public WeatherController() {
        pagingInfo = new PagingInfo();
        converter = new WeatherConverter();
    }
    private Weather weather = null;
    private List<Weather> weatherItems = null;
    private WeatherFacade jpaController = null;
    private WeatherConverter converter = null;
    private PagingInfo pagingInfo = null;
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "com.mycompany_MeteoCalProject_war_1.0-SNAPSHOTPU")
    private EntityManagerFactory emf = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(getJpaController().count());
        }
        return pagingInfo;
    }

    public WeatherFacade getJpaController() {
        if (jpaController == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            jpaController = (WeatherFacade) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "weatherJpa");
        }
        return jpaController;
    }

    public SelectItem[] getWeatherItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), false);
    }

    public SelectItem[] getWeatherItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), true);
    }

    public Weather getWeather() {
        if (weather == null) {
            weather = (Weather) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentWeather", converter, null);
        }
        if (weather == null) {
            weather = new Weather();
        }
        return weather;
    }

    public String listSetup() {
        reset(true);
        return "weather_list";
    }

    public String createSetup() {
        reset(false);
        weather = new Weather();
        weather.setWeatherPK(new WeatherPK());
        return "weather_create";
    }

    public String create() {
        // TODO: no setter methods were found in your primary key class
        //    entity.bean.WeatherPK
        // and therefore initialization code need manual adjustments.
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().create(weather);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Weather was successfully created.");
            } else {
                JsfUtil.ensureAddErrorMessage(transactionException, "A persistence error occurred.");
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception ex) {
            }
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("weather_detail");
    }

    public String editSetup() {
        return scalarSetup("weather_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        weather = (Weather) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentWeather", converter, null);
        if (weather == null) {
            String requestWeatherString = JsfUtil.getRequestParameter("jsfcrud.currentWeather");
            JsfUtil.addErrorMessage("The weather with id " + requestWeatherString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        // TODO: no setter methods were found in your primary key class
        //    entity.bean.WeatherPK
        // and therefore initialization code need manual adjustments.
        String weatherString = converter.getAsString(FacesContext.getCurrentInstance(), null, weather);
        String currentWeatherString = JsfUtil.getRequestParameter("jsfcrud.currentWeather");
        if (weatherString == null || weatherString.length() == 0 || !weatherString.equals(currentWeatherString)) {
            String outcome = editSetup();
            if ("weather_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit weather. Try again.");
            }
            return outcome;
        }
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().edit(weather);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Weather was successfully updated.");
            } else {
                JsfUtil.ensureAddErrorMessage(transactionException, "A persistence error occurred.");
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception ex) {
            }
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return detailSetup();
    }

    public String remove() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentWeather");
        WeatherPK id = converter.getId(idAsString);
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().remove(getJpaController().find(id));
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Weather was successfully deleted.");
            } else {
                JsfUtil.ensureAddErrorMessage(transactionException, "A persistence error occurred.");
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception ex) {
            }
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return relatedOrListOutcome();
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<Weather> getWeatherItems() {
        if (weatherItems == null) {
            getPagingInfo();
            weatherItems = getJpaController().findRange(new int[]{pagingInfo.getFirstItem(), pagingInfo.getFirstItem() + pagingInfo.getBatchSize()});
        }
        return weatherItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "weather_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "weather_list";
    }

    private String relatedControllerOutcome() {
        String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
        String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
        if (relatedControllerString != null && relatedControllerTypeString != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
            try {
                Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
                Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
                return (String) detailSetupMethod.invoke(relatedController);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            } catch (NoSuchMethodException e) {
                throw new FacesException(e);
            } catch (IllegalAccessException e) {
                throw new FacesException(e);
            } catch (InvocationTargetException e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    private void reset(boolean resetFirstItem) {
        weather = null;
        weatherItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Weather newWeather = new Weather();
        newWeather.setWeatherPK(new WeatherPK());
        String newWeatherString = converter.getAsString(FacesContext.getCurrentInstance(), null, newWeather);
        String weatherString = converter.getAsString(FacesContext.getCurrentInstance(), null, weather);
        if (!newWeatherString.equals(weatherString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }
    
}
