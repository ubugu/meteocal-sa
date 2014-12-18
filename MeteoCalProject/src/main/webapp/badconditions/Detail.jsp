<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Badconditions Detail</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Badconditions Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{badconditions.badconditions.id}" title="Id" />
                    <h:outputText value="Layer:"/>
                    <h:outputText value="#{badconditions.badconditions.layer}" title="Layer" />
                    <h:outputText value="Precipitations:"/>
                    <h:outputText value="#{badconditions.badconditions.precipitations}" title="Precipitations" />
                    <h:outputText value="Temperature:"/>
                    <h:outputText value="#{badconditions.badconditions.temperature}" title="Temperature" />
                    <h:outputText value="EventID:"/>
                    <h:panelGroup>
                        <h:outputText value="#{badconditions.badconditions.eventID}"/>
                        <h:panelGroup rendered="#{badconditions.badconditions.eventID != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{event.detailSetup}">
                                <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][badconditions.badconditions][badconditions.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][badconditions.badconditions.eventID][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="badconditions"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.BadconditionsController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{event.editSetup}">
                                <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][badconditions.badconditions][badconditions.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][badconditions.badconditions.eventID][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="badconditions"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.BadconditionsController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{event.destroy}">
                                <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][badconditions.badconditions][badconditions.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][badconditions.badconditions.eventID][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="badconditions"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.BadconditionsController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>


                </h:panelGrid>
                <br />
                <h:commandLink action="#{badconditions.remove}" value="Destroy">
                    <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][badconditions.badconditions][badconditions.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{badconditions.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][badconditions.badconditions][badconditions.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{badconditions.createSetup}" value="New Badconditions" />
                <br />
                <h:commandLink action="#{badconditions.listSetup}" value="Show All Badconditions Items"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
