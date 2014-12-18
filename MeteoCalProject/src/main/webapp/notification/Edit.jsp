<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Editing Notification</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Editing Notification</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{notification.notification.id}" title="Id" />
                    <h:outputText value="Type:"/>
                    <h:inputText id="type" value="#{notification.notification.type}" title="Type" required="true" requiredMessage="The type field is required." />
                    <h:outputText value="Description:"/>
                    <h:inputText id="description" value="#{notification.notification.description}" title="Description" />
                    <h:outputText value="Visualized:"/>
                    <h:inputText id="visualized" value="#{notification.notification.visualized}" title="Visualized" required="true" requiredMessage="The visualized field is required." />
                    <h:outputText value="User:"/>
                    <h:selectOneMenu id="user" value="#{notification.notification.user}" title="User" required="true" requiredMessage="The user field is required." >
                        <f:selectItems value="#{user.userItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                    <h:outputText value="EventID:"/>
                    <h:selectOneMenu id="eventID" value="#{notification.notification.eventID}" title="EventID" >
                        <f:selectItems value="#{event.eventItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{notification.edit}" value="Save">
                    <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification][notification.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{notification.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification][notification.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{notification.listSetup}" value="Show All Notification Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
