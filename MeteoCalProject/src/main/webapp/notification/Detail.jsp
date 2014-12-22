<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Notification Detail</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Notification Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{notification.notification.id}" title="Id" />
                    <h:outputText value="Type:"/>
                    <h:outputText value="#{notification.notification.type}" title="Type" />
                    <h:outputText value="Description:"/>
                    <h:outputText value="#{notification.notification.description}" title="Description" />
                    <h:outputText value="Visualized:"/>
                    <h:outputText value="#{notification.notification.visualized}" title="Visualized" />
                    <h:outputText value="User:"/>
                    <h:panelGroup>
                        <h:outputText value="#{notification.notification.user}"/>
                        <h:panelGroup rendered="#{notification.notification.user != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{user.detailSetup}">
                                <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification][notification.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification.user][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="notification"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.NotificationController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{user.editSetup}">
                                <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification][notification.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification.user][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="notification"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.NotificationController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{user.destroy}">
                                <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification][notification.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification.user][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="notification"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.NotificationController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                    <h:outputText value="EventID:"/>
                    <h:panelGroup>
                        <h:outputText value="#{notification.notification.eventID}"/>
                        <h:panelGroup rendered="#{notification.notification.eventID != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{event.detailSetup}">
                                <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification][notification.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification.eventID][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="notification"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.NotificationController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{event.editSetup}">
                                <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification][notification.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification.eventID][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="notification"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.NotificationController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{event.destroy}">
                                <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification][notification.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification.eventID][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="notification"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.NotificationController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>


                </h:panelGrid>
                <br />
                <h:commandLink action="#{notification.remove}" value="Destroy">
                    <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification][notification.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{notification.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][notification.notification][notification.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{notification.createSetup}" value="New Notification" />
                <br />
                <h:commandLink action="#{notification.listSetup}" value="Show All Notification Items"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
