<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>New Notification</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>New Notification</h1>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{notification.validateCreate}" value="value"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:inputText id="id" value="#{notification.notification.id}" title="Id" required="true" requiredMessage="The id field is required." />
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
                <h:commandLink action="#{notification.create}" value="Create"/>
                <br />
                <br />
                <h:commandLink action="#{notification.listSetup}" value="Show All Notification Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
