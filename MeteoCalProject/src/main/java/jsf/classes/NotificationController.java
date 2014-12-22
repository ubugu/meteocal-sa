/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes;

import entity.bean.Notification;
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
import session.bean.NotificationFacade;

/**
 *
 * @author Walter
 */
public class NotificationController {

    public NotificationController() {
        pagingInfo = new PagingInfo();
        converter = new NotificationConverter();
    }
    private Notification notification = null;
    private List<Notification> notificationItems = null;
    private NotificationFacade jpaController = null;
    private NotificationConverter converter = null;
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

    public NotificationFacade getJpaController() {
        if (jpaController == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            jpaController = (NotificationFacade) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "notificationJpa");
        }
        return jpaController;
    }

    public SelectItem[] getNotificationItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), false);
    }

    public SelectItem[] getNotificationItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), true);
    }

    public Notification getNotification() {
        if (notification == null) {
            notification = (Notification) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentNotification", converter, null);
        }
        if (notification == null) {
            notification = new Notification();
        }
        return notification;
    }

    public String listSetup() {
        reset(true);
        return "notification_list";
    }

    public String createSetup() {
        reset(false);
        notification = new Notification();
        return "notification_create";
    }

    public String create() {
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().create(notification);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Notification was successfully created.");
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
        return scalarSetup("notification_detail");
    }

    public String editSetup() {
        return scalarSetup("notification_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        notification = (Notification) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentNotification", converter, null);
        if (notification == null) {
            String requestNotificationString = JsfUtil.getRequestParameter("jsfcrud.currentNotification");
            JsfUtil.addErrorMessage("The notification with id " + requestNotificationString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String notificationString = converter.getAsString(FacesContext.getCurrentInstance(), null, notification);
        String currentNotificationString = JsfUtil.getRequestParameter("jsfcrud.currentNotification");
        if (notificationString == null || notificationString.length() == 0 || !notificationString.equals(currentNotificationString)) {
            String outcome = editSetup();
            if ("notification_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit notification. Try again.");
            }
            return outcome;
        }
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().edit(notification);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Notification was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentNotification");
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
                JsfUtil.addSuccessMessage("Notification was successfully deleted.");
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

    public List<Notification> getNotificationItems() {
        if (notificationItems == null) {
            getPagingInfo();
            notificationItems = getJpaController().findRange(new int[]{pagingInfo.getFirstItem(), pagingInfo.getFirstItem() + pagingInfo.getBatchSize()});
        }
        return notificationItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "notification_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "notification_list";
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
        notification = null;
        notificationItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Notification newNotification = new Notification();
        String newNotificationString = converter.getAsString(FacesContext.getCurrentInstance(), null, newNotification);
        String notificationString = converter.getAsString(FacesContext.getCurrentInstance(), null, notification);
        if (!newNotificationString.equals(notificationString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }
    
}
