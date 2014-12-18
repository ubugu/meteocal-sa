<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>New Badconditions</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>New Badconditions</h1>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{badconditions.validateCreate}" value="value"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:inputText id="id" value="#{badconditions.badconditions.id}" title="Id" required="true" requiredMessage="The id field is required." />
                    <h:outputText value="Layer:"/>
                    <h:inputText id="layer" value="#{badconditions.badconditions.layer}" title="Layer" required="true" requiredMessage="The layer field is required." />
                    <h:outputText value="Precipitations:"/>
                    <h:inputText id="precipitations" value="#{badconditions.badconditions.precipitations}" title="Precipitations" />
                    <h:outputText value="Temperature:"/>
                    <h:inputText id="temperature" value="#{badconditions.badconditions.temperature}" title="Temperature" />
                    <h:outputText value="EventID:"/>
                    <h:selectOneMenu id="eventID" value="#{badconditions.badconditions.eventID}" title="EventID" required="true" requiredMessage="The eventID field is required." >
                        <f:selectItems value="#{event.eventItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{badconditions.create}" value="Create"/>
                <br />
                <br />
                <h:commandLink action="#{badconditions.listSetup}" value="Show All Badconditions Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
