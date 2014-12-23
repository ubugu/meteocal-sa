/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes;

import entity.bean.Event;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import jsf.classes.util.JsfUtil;
import jsf.classes.util.PagingInfo;
import session.bean.EventFacade;

/**
 *
 * @author Walter
 */
public class EventController {

    public EventController() {
        pagingInfo = new PagingInfo();
        converter = new EventConverter();
    }
    private Event event = null;
    private List<Event> eventItems = null;
    private EventFacade jpaController = null;
    private EventConverter converter = null;
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

    public EventFacade getJpaController() {
        if (jpaController == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            jpaController = (EventFacade) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "eventJpa");
        }
        return jpaController;
    }

    public SelectItem[] getEventItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), false);
    }

    public SelectItem[] getEventItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), true);
    }

    public Event getEvent() {
        if (event == null) {
            event = (Event) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentEvent", converter, null);
        }
        if (event == null) {
            event = new Event();
        }
        return event;
    }

    public String listSetup() {
        reset(true);
        return "event_list";
    }

    public String createSetup() {
        reset(false);
        event = new Event();
        return "event_create";
    }

    public String create() {
        try {
            
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().create(event);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Event was successfully created.");
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
        return scalarSetup("event_detail");
    }

    public String editSetup() {
        return scalarSetup("event_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        event = (Event) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentEvent", converter, null);
        if (event == null) {
            String requestEventString = JsfUtil.getRequestParameter("jsfcrud.currentEvent");
            JsfUtil.addErrorMessage("The event with id " + requestEventString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String eventString = converter.getAsString(FacesContext.getCurrentInstance(), null, event);
        String currentEventString = JsfUtil.getRequestParameter("jsfcrud.currentEvent");
        if (eventString == null || eventString.length() == 0 || !eventString.equals(currentEventString)) {
            String outcome = editSetup();
            if ("event_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit event. Try again.");
            }
            return outcome;
        }
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().edit(event);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Event was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentEvent");
        Integer id = new Integer(idAsString);
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
                JsfUtil.addSuccessMessage("Event was successfully deleted.");
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

    public List<Event> getEventItems() {
        if (eventItems == null) {
            getPagingInfo();
            eventItems = getJpaController().findRange(new int[]{pagingInfo.getFirstItem(), pagingInfo.getFirstItem() + pagingInfo.getBatchSize()});
        }
        return eventItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "event_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "event_list";
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
        event = null;
        eventItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Event newEvent = new Event();
        String newEventString = converter.getAsString(FacesContext.getCurrentInstance(), null, newEvent);
        String eventString = converter.getAsString(FacesContext.getCurrentInstance(), null, event);
        if (!newEventString.equals(eventString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }
    
}
