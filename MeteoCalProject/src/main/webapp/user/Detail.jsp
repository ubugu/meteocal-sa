<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>User Detail</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>User Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Username:"/>
                    <h:outputText value="#{user.user.username}" title="Username" />
                    <h:outputText value="Password:"/>
                    <h:outputText value="#{user.user.password}" title="Password" />
                    <h:outputText value="Email:"/>
                    <h:outputText value="#{user.user.email}" title="Email" />
                    <h:outputText value="Calendar:"/>
                    <h:panelGroup>
                        <h:outputText value="#{user.user.calendar}"/>
                        <h:panelGroup rendered="#{user.user.calendar != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{calendar.detailSetup}">
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user.calendar][calendar.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="user"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{calendar.editSetup}">
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user.calendar][calendar.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="user"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{calendar.destroy}">
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user.calendar][calendar.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="user"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>

                    <h:outputText value="CalendarCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty user.user.calendarCollection}" value="(No Items)"/>
                        <h:dataTable value="#{user.user.calendarCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty user.user.calendarCollection}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Id"/>
                                </f:facet>
                                <h:outputText value="#{item.id}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Privacy"/>
                                </f:facet>
                                <h:outputText value="#{item.privacy}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Owner"/>
                                </f:facet>
                                <h:outputText value="#{item.owner}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{calendar.detailSetup}">
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][calendar.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="user" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{calendar.editSetup}">
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][calendar.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="user" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{calendar.destroy}">
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][calendar.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="user" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                    <h:outputText value="NotificationCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty user.user.notificationCollection}" value="(No Items)"/>
                        <h:dataTable value="#{user.user.notificationCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty user.user.notificationCollection}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Id"/>
                                </f:facet>
                                <h:outputText value="#{item.id}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Type"/>
                                </f:facet>
                                <h:outputText value="#{item.type}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Description"/>
                                </f:facet>
                                <h:outputText value="#{item.description}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Visualized"/>
                                </f:facet>
                                <h:outputText value="#{item.visualized}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="User"/>
                                </f:facet>
                                <h:outputText value="#{item.user}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="EventID"/>
                                </f:facet>
                                <h:outputText value="#{item.eventID}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{notification.detailSetup}">
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][notification.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="user" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{notification.editSetup}">
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][notification.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="user" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{notification.destroy}">
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][notification.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="user" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                    <h:outputText value="ParticipantCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty user.user.participantCollection}" value="(No Items)"/>
                        <h:dataTable value="#{user.user.participantCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty user.user.participantCollection}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Organiser"/>
                                </f:facet>
                                <h:outputText value="#{item.organiser}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Participant"/>
                                </f:facet>
                                <h:outputText value="#{item.participant}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="User1"/>
                                </f:facet>
                                <h:outputText value="#{item.user1}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Event1"/>
                                </f:facet>
                                <h:outputText value="#{item.event1}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{participant.detailSetup}">
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][participant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="user" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{participant.editSetup}">
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][participant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="user" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{participant.destroy}">
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][participant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="user" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.UserController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{user.remove}" value="Destroy">
                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{user.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][user.user][user.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{user.createSetup}" value="New User" />
                <br />
                <h:commandLink action="#{user.listSetup}" value="Show All User Items"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
