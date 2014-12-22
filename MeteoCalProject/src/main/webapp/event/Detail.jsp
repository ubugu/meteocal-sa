<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Event Detail</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Event Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{event.event.id}" title="Id" />
                    <h:outputText value="Title:"/>
                    <h:outputText value="#{event.event.title}" title="Title" />
                    <h:outputText value="Date:"/>
                    <h:outputText value="#{event.event.date}" title="Date" >
                        <f:convertDateTime pattern="MM/dd/yyyy" />
                    </h:outputText>
                    <h:outputText value="StartingTime:"/>
                    <h:outputText value="#{event.event.startingTime}" title="StartingTime" >
                        <f:convertDateTime pattern="HH:mm:ss" />
                    </h:outputText>
                    <h:outputText value="EndingTime:"/>
                    <h:outputText value="#{event.event.endingTime}" title="EndingTime" >
                        <f:convertDateTime pattern="HH:mm:ss" />
                    </h:outputText>
                    <h:outputText value="Location:"/>
                    <h:outputText value="#{event.event.location}" title="Location" />
                    <h:outputText value="City:"/>
                    <h:outputText value="#{event.event.city}" title="City" />
                    <h:outputText value="Description:"/>
                    <h:outputText value="#{event.event.description}" title="Description" />
                    <h:outputText value="Color:"/>
                    <h:outputText value="#{event.event.color}" title="Color" />
                    <h:outputText value="Privacy:"/>
                    <h:outputText value="#{event.event.privacy}" title="Privacy" />
                    <h:outputText value="Calendar:"/>
                    <h:panelGroup>
                        <h:outputText value="#{event.event.calendar}"/>
                        <h:panelGroup rendered="#{event.event.calendar != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{calendar.detailSetup}">
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event.calendar][calendar.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="event"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{calendar.editSetup}">
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event.calendar][calendar.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="event"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{calendar.destroy}">
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event.calendar][calendar.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="event"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                    <h:outputText value="WeatherID:"/>
                    <h:panelGroup>
                        <h:outputText value="#{event.event.weatherID}"/>
                        <h:panelGroup rendered="#{event.event.weatherID != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{weather.detailSetup}">
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event.weatherID][weather.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="event"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{weather.editSetup}">
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event.weatherID][weather.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="event"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{weather.destroy}">
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event.weatherID][weather.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="event"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>

                    <h:outputText value="NotificationCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty event.event.notificationCollection}" value="(No Items)"/>
                        <h:dataTable value="#{event.event.notificationCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty event.event.notificationCollection}">
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
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][notification.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="event" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{notification.editSetup}">
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][notification.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="event" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{notification.destroy}">
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][notification.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="event" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                    <h:outputText value="ParticipantCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty event.event.participantCollection}" value="(No Items)"/>
                        <h:dataTable value="#{event.event.participantCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty event.event.participantCollection}">
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
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][participant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="event" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{participant.editSetup}">
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][participant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="event" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{participant.destroy}">
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][participant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="event" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                    <h:outputText value="BadconditionsCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty event.event.badconditionsCollection}" value="(No Items)"/>
                        <h:dataTable value="#{event.event.badconditionsCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty event.event.badconditionsCollection}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Id"/>
                                </f:facet>
                                <h:outputText value="#{item.id}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Layer"/>
                                </f:facet>
                                <h:outputText value="#{item.layer}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Precipitations"/>
                                </f:facet>
                                <h:outputText value="#{item.precipitations}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Temperature"/>
                                </f:facet>
                                <h:outputText value="#{item.temperature}"/>
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
                                <h:commandLink value="Show" action="#{badconditions.detailSetup}">
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][badconditions.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="event" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{badconditions.editSetup}">
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][badconditions.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="event" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{badconditions.destroy}">
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][badconditions.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="event" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.EventController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{event.remove}" value="Destroy">
                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{event.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][event.event][event.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{event.createSetup}" value="New Event" />
                <br />
                <h:commandLink action="#{event.listSetup}" value="Show All Event Items"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
