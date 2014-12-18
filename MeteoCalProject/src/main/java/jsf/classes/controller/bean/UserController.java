/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes.controller.bean;

import entity.bean.User;
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
import jsf.classes.controller.bean.util.JsfUtil;
import jsf.classes.controller.bean.util.PagingInfo;
import session.bean.UserFacade;

/**
 *
 * @author Walter
 */
public class UserController {

    public UserController() {
        pagingInfo = new PagingInfo();
        converter = new UserConverter();
    }
    private User user = null;
    private List<User> userItems = null;
    private UserFacade jpaController = null;
    private UserConverter converter = null;
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

    public UserFacade getJpaController() {
        if (jpaController == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            jpaController = (UserFacade) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "userJpa");
        }
        return jpaController;
    }

    public SelectItem[] getUserItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), false);
    }

    public SelectItem[] getUserItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), true);
    }

    public User getUser() {
        if (user == null) {
            user = (User) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentUser", converter, null);
        }
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public String listSetup() {
        reset(true);
        return "user_list";
    }

    public String createSetup() {
        reset(false);
        user = new User();
        return "user_create";
    }

    public String create() {
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().create(user);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("User was successfully created.");
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
        return scalarSetup("user_detail");
    }

    public String editSetup() {
        return scalarSetup("user_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        user = (User) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentUser", converter, null);
        if (user == null) {
            String requestUserString = JsfUtil.getRequestParameter("jsfcrud.currentUser");
            JsfUtil.addErrorMessage("The user with id " + requestUserString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String userString = converter.getAsString(FacesContext.getCurrentInstance(), null, user);
        String currentUserString = JsfUtil.getRequestParameter("jsfcrud.currentUser");
        if (userString == null || userString.length() == 0 || !userString.equals(currentUserString)) {
            String outcome = editSetup();
            if ("user_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit user. Try again.");
            }
            return outcome;
        }
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().edit(user);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("User was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentUser");
        String id = idAsString;
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
                JsfUtil.addSuccessMessage("User was successfully deleted.");
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

    public List<User> getUserItems() {
        if (userItems == null) {
            getPagingInfo();
            userItems = getJpaController().findRange(new int[]{pagingInfo.getFirstItem(), pagingInfo.getFirstItem() + pagingInfo.getBatchSize()});
        }
        return userItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "user_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "user_list";
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
        user = null;
        userItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        User newUser = new User();
        String newUserString = converter.getAsString(FacesContext.getCurrentInstance(), null, newUser);
        String userString = converter.getAsString(FacesContext.getCurrentInstance(), null, user);
        if (!newUserString.equals(userString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }
    
}
