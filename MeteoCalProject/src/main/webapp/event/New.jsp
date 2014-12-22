<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>New Event</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>New Event</h1>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{event.validateCreate}" value="value"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:inputText id="id" value="#{event.event.id}" title="Id" required="true" requiredMessage="The id field is required." />
                    <h:outputText value="Title:"/>
                    <h:inputText id="title" value="#{event.event.title}" title="Title" required="true" requiredMessage="The title field is required." />
                    <h:outputText value="Date (MM/dd/yyyy):"/>
                    <h:inputText id="date" value="#{event.event.date}" title="Date" required="true" requiredMessage="The date field is required." >
                        <f:convertDateTime pattern="MM/dd/yyyy" />
                    </h:inputText>
                    <h:outputText value="StartingTime (HH:mm:ss):"/>
                    <h:inputText id="startingTime" value="#{event.event.startingTime}" title="StartingTime" required="true" requiredMessage="The startingTime field is required." >
                        <f:convertDateTime pattern="HH:mm:ss" />
                    </h:inputText>
                    <h:outputText value="EndingTime (HH:mm:ss):"/>
                    <h:inputText id="endingTime" value="#{event.event.endingTime}" title="EndingTime" required="true" requiredMessage="The endingTime field is required." >
                        <f:convertDateTime pattern="HH:mm:ss" />
                    </h:inputText>
                    <h:outputText value="Location:"/>
                    <h:inputText id="location" value="#{event.event.location}" title="Location" required="true" requiredMessage="The location field is required." />
                    <h:outputText value="City:"/>
                    <h:inputText id="city" value="#{event.event.city}" title="City" required="true" requiredMessage="The city field is required." />
                    <h:outputText value="Description:"/>
                    <h:inputText id="description" value="#{event.event.description}" title="Description" />
                    <h:outputText value="Color:"/>
                    <h:inputText id="color" value="#{event.event.color}" title="Color" required="true" requiredMessage="The color field is required." />
                    <h:outputText value="Privacy:"/>
                    <h:inputText id="privacy" value="#{event.event.privacy}" title="Privacy" required="true" requiredMessage="The privacy field is required." />
                    <h:outputText value="NotificationCollection:"/>
                    <h:selectManyListbox id="notificationCollection" value="#{event.event.jsfcrud_transform[jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method.arrayToList].notificationCollection}" title="NotificationCollection" size="6" converter="#{notification.converter}" >
                        <f:selectItems value="#{notification.notificationItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="Calendar:"/>
                    <h:selectOneMenu id="calendar" value="#{event.event.calendar}" title="Calendar" required="true" requiredMessage="The calendar field is required." >
                        <f:selectItems value="#{calendar.calendarItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                    <h:outputText value="WeatherID:"/>
                    <h:selectOneMenu id="weatherID" value="#{event.event.weatherID}" title="WeatherID" >
                        <f:selectItems value="#{weather.weatherItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                    <h:outputText value="ParticipantCollection:"/>
                    <h:outputText escape="false" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getCollectionAsString'][event.event.participantCollection == null ? jsfcrud_null : event.event.participantCollection].jsfcrud_invoke}" title="ParticipantCollection" />
                    <h:outputText value="BadconditionsCollection:"/>
                    <h:selectManyListbox id="badconditionsCollection" value="#{event.event.jsfcrud_transform[jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method.arrayToList].badconditionsCollection}" title="BadconditionsCollection" size="6" converter="#{badconditions.converter}" >
                        <f:selectItems value="#{badconditions.badconditionsItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{event.create}" value="Create"/>
                <br />
                <br />
                <h:commandLink action="#{event.listSetup}" value="Show All Event Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
