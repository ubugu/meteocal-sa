<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Calendar Detail</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Calendar Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{calendar.calendar.id}" title="Id" />
                    <h:outputText value="Privacy:"/>
                    <h:outputText value="#{calendar.calendar.privacy}" title="Privacy" />
                    <h:outputText value="Owner:"/>
                    <h:panelGroup>
                        <h:outputText value="#{calendar.calendar.owner}"/>
                        <h:panelGroup rendered="#{calendar.calendar.owner != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{user.detailSetup}">
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar.owner][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="calendar"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.CalendarController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{user.editSetup}">
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar.owner][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="calendar"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.CalendarController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{user.destroy}">
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar.owner][user.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="calendar"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.CalendarController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>

                    <h:outputText value="UserCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty calendar.calendar.userCollection}" value="(No Items)"/>
                        <h:dataTable value="#{calendar.calendar.userCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty calendar.calendar.userCollection}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Username"/>
                                </f:facet>
                                <h:outputText value="#{item.username}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Password"/>
                                </f:facet>
                                <h:outputText value="#{item.password}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Email"/>
                                </f:facet>
                                <h:outputText value="#{item.email}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Calendar"/>
                                </f:facet>
                                <h:outputText value="#{item.calendar}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{user.detailSetup}">
                                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="calendar" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.CalendarController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{user.editSetup}">
                                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="calendar" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.CalendarController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{user.destroy}">
                                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][user.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="calendar" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.CalendarController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                    <h:outputText value="EventCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty calendar.calendar.eventCollection}" value="(No Items)"/>
                        <h:dataTable value="#{calendar.calendar.eventCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty calendar.calendar.eventCollection}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Id"/>
                                </f:facet>
                                <h:outputText value="#{item.id}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Title"/>
                                </f:facet>
                                <h:outputText value="#{item.title}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="StartingTime"/>
                                </f:facet>
                                <h:outputText value="#{item.startingTime}">
                                    <f:convertDateTime pattern="HH:mm:ss" />
                                </h:outputText>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="EndingTime"/>
                                </f:facet>
                                <h:outputText value="#{item.endingTime}">
                                    <f:convertDateTime pattern="HH:mm:ss" />
                                </h:outputText>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Location"/>
                                </f:facet>
                                <h:outputText value="#{item.location}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Description"/>
                                </f:facet>
                                <h:outputText value="#{item.description}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Color"/>
                                </f:facet>
                                <h:outputText value="#{item.color}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Privacy"/>
                                </f:facet>
                                <h:outputText value="#{item.privacy}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Calendar"/>
                                </f:facet>
                                <h:outputText value="#{item.calendar}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Weather"/>
                                </f:facet>
                                <h:outputText value="#{item.weather}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{event.detailSetup}">
                                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="calendar" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.CalendarController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{event.editSetup}">
                                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="calendar" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.CalendarController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{event.destroy}">
                                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="calendar" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.CalendarController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{calendar.remove}" value="Destroy">
                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{calendar.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][calendar.calendar][calendar.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{calendar.createSetup}" value="New Calendar" />
                <br />
                <h:commandLink action="#{calendar.listSetup}" value="Show All Calendar Items"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
