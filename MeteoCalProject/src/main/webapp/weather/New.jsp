<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>New Weather</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>New Weather</h1>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{weather.validateCreate}" value="value"/>
                <h:panelGrid columns="2">
                    <h:outputText value="City:"/>
                    <h:inputText id="city" value="#{weather.weather.city}" title="City" required="true" requiredMessage="The city field is required." />
                    <h:outputText value="Date (MM/dd/yyyy):"/>
                    <h:inputText id="date" value="#{weather.weather.date}" title="Date" required="true" requiredMessage="The date field is required." >
                        <f:convertDateTime pattern="MM/dd/yyyy" />
                    </h:inputText>
                    <h:outputText value="Precipitations:"/>
                    <h:inputText id="precipitations" value="#{weather.weather.precipitations}" title="Precipitations" />
                    <h:outputText value="Temperature:"/>
                    <h:inputText id="temperature" value="#{weather.weather.temperature}" title="Temperature" />
                    <h:outputText value="Wind:"/>
                    <h:inputText id="wind" value="#{weather.weather.wind}" title="Wind" />
                    <h:outputText value="Pressure:"/>
                    <h:inputText id="pressure" value="#{weather.weather.pressure}" title="Pressure" />
                    <h:outputText value="Humidity:"/>
                    <h:inputText id="humidity" value="#{weather.weather.humidity}" title="Humidity" />
                    <h:outputText value="Clouds:"/>
                    <h:inputText id="clouds" value="#{weather.weather.clouds}" title="Clouds" />
                    <h:outputText value="Id:"/>
                    <h:inputText id="id" value="#{weather.weather.id}" title="Id" required="true" requiredMessage="The id field is required." />
                    <h:outputText value="EventCollection:"/>
                    <h:selectManyListbox id="eventCollection" value="#{weather.weather.jsfcrud_transform[jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method.arrayToList].eventCollection}" title="EventCollection" size="6" converter="#{event.converter}" >
                        <f:selectItems value="#{event.eventItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{weather.create}" value="Create"/>
                <br />
                <br />
                <h:commandLink action="#{weather.listSetup}" value="Show All Weather Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
