<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Editing Calendar</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Editing Calendar</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{calendar.calendar.id}" title="Id" />
                    <h:outputText value="Privacy:"/>
                    <h:inputText id="privacy" value="#{calendar.calendar.privacy}" title="Privacy" required="true" requiredMessage="The privacy field is required." />
                    <h:outputText value="UserCollection:"/>
                    <h:selectManyListbox id="userCollection" value="#{calendar.calendar.jsfcrud_transform[jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method.arrayToList].userCollection}" title="UserCollection" size="6" converter="#{user.converter}" >
                        <f:selectItems value="#{user.userItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="Owner:"/>
                    <h:selectOneMenu id="owner" value="#{calendar.calendar.owner}" title="Owner" required="true" requiredMessage="The owner field is required." >
                        <f:selectItems value="#{user.userItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                    <h:outputText value="EventCollection:"/>
                    <h:selectManyListbox id="eventCollection" value="#{calendar.calendar.jsfcrud_transform[jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method.arrayToList].eventCollection}" title="EventCollection" size="6" converter="#{event.converter}" >
                        <f:selectItems value="#{event.eventItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{calendar.edit}" value="Save">
                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{calendar.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{calendar.listSetup}" value="Show All Calendar Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
