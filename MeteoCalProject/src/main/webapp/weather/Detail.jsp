<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Weather Detail</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Weather Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="City:"/>
                    <h:outputText value="#{weather.weather.weatherPK.city}" title="City" />
                    <h:outputText value="Date:"/>
                    <h:outputText value="#{weather.weather.weatherPK.date}" title="Date" >
                        <f:convertDateTime pattern="MM/dd/yyyy" />
                    </h:outputText>
                    <h:outputText value="Precipitations:"/>
                    <h:outputText value="#{weather.weather.precipitations}" title="Precipitations" />
                    <h:outputText value="Temperature:"/>
                    <h:outputText value="#{weather.weather.temperature}" title="Temperature" />
                    <h:outputText value="Wind:"/>
                    <h:outputText value="#{weather.weather.wind}" title="Wind" />
                    <h:outputText value="Pressure:"/>
                    <h:outputText value="#{weather.weather.pressure}" title="Pressure" />
                    <h:outputText value="Humidity:"/>
                    <h:outputText value="#{weather.weather.humidity}" title="Humidity" />
                    <h:outputText value="Clouds:"/>
                    <h:outputText value="#{weather.weather.clouds}" title="Clouds" />

                    <h:outputText value="EventCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty weather.weather.eventCollection}" value="(No Items)"/>
                        <h:dataTable value="#{weather.weather.eventCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty weather.weather.eventCollection}">
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
                                    <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][weather.weather][weather.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="weather" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.WeatherController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{event.editSetup}">
                                    <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][weather.weather][weather.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="weather" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.WeatherController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{event.destroy}">
                                    <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][weather.weather][weather.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][event.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="weather" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.classes.controller.bean.WeatherController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{weather.remove}" value="Destroy">
                    <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][weather.weather][weather.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{weather.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][weather.weather][weather.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{weather.createSetup}" value="New Weather" />
                <br />
                <h:commandLink action="#{weather.listSetup}" value="Show All Weather Items"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
