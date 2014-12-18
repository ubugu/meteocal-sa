/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes.controller.bean;

import entity.bean.Badconditions;
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
import session.bean.BadconditionsFacade;

/**
 *
 * @author Walter
 */
public class BadconditionsController {

    public BadconditionsController() {
        pagingInfo = new PagingInfo();
        converter = new BadconditionsConverter();
    }
    private Badconditions badconditions = null;
    private List<Badconditions> badconditionsItems = null;
    private BadconditionsFacade jpaController = null;
    private BadconditionsConverter converter = null;
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

    public BadconditionsFacade getJpaController() {
        if (jpaController == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            jpaController = (BadconditionsFacade) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "badconditionsJpa");
        }
        return jpaController;
    }

    public SelectItem[] getBadconditionsItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), false);
    }

    public SelectItem[] getBadconditionsItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), true);
    }

    public Badconditions getBadconditions() {
        if (badconditions == null) {
            badconditions = (Badconditions) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentBadconditions", converter, null);
        }
        if (badconditions == null) {
            badconditions = new Badconditions();
        }
        return badconditions;
    }

    public String listSetup() {
        reset(true);
        return "badconditions_list";
    }

    public String createSetup() {
        reset(false);
        badconditions = new Badconditions();
        return "badconditions_create";
    }

    public String create() {
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().create(badconditions);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Badconditions was successfully created.");
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
        return scalarSetup("badconditions_detail");
    }

    public String editSetup() {
        return scalarSetup("badconditions_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        badconditions = (Badconditions) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentBadconditions", converter, null);
        if (badconditions == null) {
            String requestBadconditionsString = JsfUtil.getRequestParameter("jsfcrud.currentBadconditions");
            JsfUtil.addErrorMessage("The badconditions with id " + requestBadconditionsString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String badconditionsString = converter.getAsString(FacesContext.getCurrentInstance(), null, badconditions);
        String currentBadconditionsString = JsfUtil.getRequestParameter("jsfcrud.currentBadconditions");
        if (badconditionsString == null || badconditionsString.length() == 0 || !badconditionsString.equals(currentBadconditionsString)) {
            String outcome = editSetup();
            if ("badconditions_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit badconditions. Try again.");
            }
            return outcome;
        }
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().edit(badconditions);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Badconditions was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentBadconditions");
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
                JsfUtil.addSuccessMessage("Badconditions was successfully deleted.");
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

    public List<Badconditions> getBadconditionsItems() {
        if (badconditionsItems == null) {
            getPagingInfo();
            badconditionsItems = getJpaController().findRange(new int[]{pagingInfo.getFirstItem(), pagingInfo.getFirstItem() + pagingInfo.getBatchSize()});
        }
        return badconditionsItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "badconditions_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "badconditions_list";
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
        badconditions = null;
        badconditionsItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Badconditions newBadconditions = new Badconditions();
        String newBadconditionsString = converter.getAsString(FacesContext.getCurrentInstance(), null, newBadconditions);
        String badconditionsString = converter.getAsString(FacesContext.getCurrentInstance(), null, badconditions);
        if (!newBadconditionsString.equals(badconditionsString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }
    
}
