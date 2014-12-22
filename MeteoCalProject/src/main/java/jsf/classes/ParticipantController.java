/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.classes;

import entity.bean.Participant;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.faces.FacesException;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import jsf.classes.util.JsfUtil;
import entity.bean.ParticipantPK;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import jsf.classes.util.PagingInfo;
import session.bean.ParticipantFacade;

/**
 *
 * @author Walter
 */
public class ParticipantController {

    public ParticipantController() {
        pagingInfo = new PagingInfo();
        converter = new ParticipantConverter();
    }
    private Participant participant = null;
    private List<Participant> participantItems = null;
    private ParticipantFacade jpaController = null;
    private ParticipantConverter converter = null;
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

    public ParticipantFacade getJpaController() {
        if (jpaController == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            jpaController = (ParticipantFacade) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "participantJpa");
        }
        return jpaController;
    }

    public SelectItem[] getParticipantItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), false);
    }

    public SelectItem[] getParticipantItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), true);
    }

    public Participant getParticipant() {
        if (participant == null) {
            participant = (Participant) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentParticipant", converter, null);
        }
        if (participant == null) {
            participant = new Participant();
        }
        return participant;
    }

    public String listSetup() {
        reset(true);
        return "participant_list";
    }

    public String createSetup() {
        reset(false);
        participant = new Participant();
        participant.setParticipantPK(new ParticipantPK());
        return "participant_create";
    }

    public String create() {
        participant.getParticipantPK().setEvent(participant.getEvent1().getId());
        participant.getParticipantPK().setUser(participant.getUser1().getUsername());
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().create(participant);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Participant was successfully created.");
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
        return scalarSetup("participant_detail");
    }

    public String editSetup() {
        return scalarSetup("participant_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        participant = (Participant) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentParticipant", converter, null);
        if (participant == null) {
            String requestParticipantString = JsfUtil.getRequestParameter("jsfcrud.currentParticipant");
            JsfUtil.addErrorMessage("The participant with id " + requestParticipantString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        participant.getParticipantPK().setEvent(participant.getEvent1().getId());
        participant.getParticipantPK().setUser(participant.getUser1().getUsername());
        String participantString = converter.getAsString(FacesContext.getCurrentInstance(), null, participant);
        String currentParticipantString = JsfUtil.getRequestParameter("jsfcrud.currentParticipant");
        if (participantString == null || participantString.length() == 0 || !participantString.equals(currentParticipantString)) {
            String outcome = editSetup();
            if ("participant_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit participant. Try again.");
            }
            return outcome;
        }
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().edit(participant);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Participant was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentParticipant");
        ParticipantPK id = converter.getId(idAsString);
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
                JsfUtil.addSuccessMessage("Participant was successfully deleted.");
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

    public List<Participant> getParticipantItems() {
        if (participantItems == null) {
            getPagingInfo();
            participantItems = getJpaController().findRange(new int[]{pagingInfo.getFirstItem(), pagingInfo.getFirstItem() + pagingInfo.getBatchSize()});
        }
        return participantItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "participant_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "participant_list";
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
        participant = null;
        participantItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Participant newParticipant = new Participant();
        newParticipant.setParticipantPK(new ParticipantPK());
        String newParticipantString = converter.getAsString(FacesContext.getCurrentInstance(), null, newParticipant);
        String participantString = converter.getAsString(FacesContext.getCurrentInstance(), null, participant);
        if (!newParticipantString.equals(participantString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }
    
}
