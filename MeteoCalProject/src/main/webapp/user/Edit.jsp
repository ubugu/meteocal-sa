<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Editing User</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Editing User</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Username:"/>
                    <h:outputText value="#{user.user.username}" title="Username" />
                    <h:outputText value="Password:"/>
                    <h:inputText id="password" value="#{user.user.password}" title="Password" required="true" requiredMessage="The password field is required." />
                    <h:outputText value="Email:"/>
                    <h:inputText id="email" value="#{user.user.email}" title="Email" required="true" requiredMessage="The email field is required." />
                    <h:outputText value="CalendarCollection:"/>
                    <h:selectManyListbox id="calendarCollection" value="#{user.user.jsfcrud_transform[jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method.arrayToList].calendarCollection}" title="CalendarCollection" size="6" converter="#{calendar.converter}" >
                        <f:selectItems value="#{calendar.calendarItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="Calendar:"/>
                    <h:selectOneMenu id="calendar" value="#{user.user.calendar}" title="Calendar" >
                        <f:selectItems value="#{calendar.calendarItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                    <h:outputText value="NotificationCollection:"/>
                    <h:selectManyListbox id="notificationCollection" value="#{user.user.jsfcrud_transform[jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method.arrayToList].notificationCollection}" title="NotificationCollection" size="6" converter="#{notification.converter}" >
                        <f:selectItems value="#{notification.notificationItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="ParticipantCollection:"/>
                    <h:outputText escape="false" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getCollectionAsString'][user.user.participantCollection == null ? jsfcrud_null : user.user.participantCollection].jsfcrud_invoke}" title="ParticipantCollection" />

                </h:panelGrid>
                <br />
                <h:commandLink action="#{user.edit}" value="Save">
                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{user.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{user.listSetup}" value="Show All User Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
