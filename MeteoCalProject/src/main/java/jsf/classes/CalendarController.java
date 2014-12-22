/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes;

import entity.bean.Calendar;
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
import session.bean.CalendarFacade;

/**
 *
 * @author Walter
 */
public class CalendarController {

    public CalendarController() {
        pagingInfo = new PagingInfo();
        converter = new CalendarConverter();
    }
    private Calendar calendar = null;
    private List<Calendar> calendarItems = null;
    private CalendarFacade jpaController = null;
    private CalendarConverter converter = null;
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

    public CalendarFacade getJpaController() {
        if (jpaController == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            jpaController = (CalendarFacade) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "calendarJpa");
        }
        return jpaController;
    }

    public SelectItem[] getCalendarItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), false);
    }

    public SelectItem[] getCalendarItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), true);
    }

    public Calendar getCalendar() {
        if (calendar == null) {
            calendar = (Calendar) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentCalendar", converter, null);
        }
        if (calendar == null) {
            calendar = new Calendar();
        }
        return calendar;
    }

    public String listSetup() {
        reset(true);
        return "calendar_list";
    }

    public String createSetup() {
        reset(false);
        calendar = new Calendar();
        return "calendar_create";
    }

    public String create() {
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().create(calendar);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Calendar was successfully created.");
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
        return scalarSetup("calendar_detail");
    }

    public String editSetup() {
        return scalarSetup("calendar_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        calendar = (Calendar) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentCalendar", converter, null);
        if (calendar == null) {
            String requestCalendarString = JsfUtil.getRequestParameter("jsfcrud.currentCalendar");
            JsfUtil.addErrorMessage("The calendar with id " + requestCalendarString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String calendarString = converter.getAsString(FacesContext.getCurrentInstance(), null, calendar);
        String currentCalendarString = JsfUtil.getRequestParameter("jsfcrud.currentCalendar");
        if (calendarString == null || calendarString.length() == 0 || !calendarString.equals(currentCalendarString)) {
            String outcome = editSetup();
            if ("calendar_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit calendar. Try again.");
            }
            return outcome;
        }
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().edit(calendar);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Calendar was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentCalendar");
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
                JsfUtil.addSuccessMessage("Calendar was successfully deleted.");
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

    public List<Calendar> getCalendarItems() {
        if (calendarItems == null) {
            getPagingInfo();
            calendarItems = getJpaController().findRange(new int[]{pagingInfo.getFirstItem(), pagingInfo.getFirstItem() + pagingInfo.getBatchSize()});
        }
        return calendarItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "calendar_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "calendar_list";
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
        calendar = null;
        calendarItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Calendar newCalendar = new Calendar();
        String newCalendarString = converter.getAsString(FacesContext.getCurrentInstance(), null, newCalendar);
        String calendarString = converter.getAsString(FacesContext.getCurrentInstance(), null, calendar);
        if (!newCalendarString.equals(calendarString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }
    
}
