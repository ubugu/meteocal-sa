<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Participant Detail</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Participant Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Organiser:"/>
                    <h:outputText value="#{participant.participant.organiser}" title="Organiser" />
                    <h:outputText value="Participant:"/>
                    <h:outputText value="#{participant.participant.participant}" title="Participant" />
                    <h:outputText value="User1:"/>
                    <h:panelGroup>
                        <h:outputText value="#{participant.participant.user1}"/>
                        <h:panelGroup rendered="#{participant.participant.user1 != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{user.detailSetup}">
                                <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant][participant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant.user1][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="participant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.ParticipantController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{user.editSetup}">
                                <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant][participant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant.user1][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="participant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.ParticipantController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{user.destroy}">
                                <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant][participant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant.user1][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="participant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.ParticipantController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                    <h:outputText value="Event1:"/>
                    <h:panelGroup>
                        <h:outputText value="#{participant.participant.event1}"/>
                        <h:panelGroup rendered="#{participant.participant.event1 != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{event.detailSetup}">
                                <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant][participant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant.event1][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="participant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.ParticipantController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{event.editSetup}">
                                <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant][participant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant.event1][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="participant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.ParticipantController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{event.destroy}">
                                <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant][participant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant.event1][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="participant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.ParticipantController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>


                </h:panelGrid>
                <br />
                <h:commandLink action="#{participant.remove}" value="Destroy">
                    <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant][participant.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{participant.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant][participant.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{participant.createSetup}" value="New Participant" />
                <br />
                <h:commandLink action="#{participant.listSetup}" value="Show All Participant Items"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
