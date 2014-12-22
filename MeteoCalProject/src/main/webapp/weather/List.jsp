<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Listing Weather Items</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Weather Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Weather Items Found)<br />" rendered="#{weather.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{weather.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{weather.pagingInfo.firstItem + 1}..#{weather.pagingInfo.lastItem} of #{weather.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{weather.prev}" value="Previous #{weather.pagingInfo.batchSize}" rendered="#{weather.pagingInfo.firstItem >= weather.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{weather.next}" value="Next #{weather.pagingInfo.batchSize}" rendered="#{weather.pagingInfo.lastItem + weather.pagingInfo.batchSize <= weather.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{weather.next}" value="Remaining #{weather.pagingInfo.itemCount - weather.pagingInfo.lastItem}"
                                   rendered="#{weather.pagingInfo.lastItem < weather.pagingInfo.itemCount && weather.pagingInfo.lastItem + weather.pagingInfo.batchSize > weather.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{weather.weatherItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="City"/>
                            </f:facet>
                            <h:outputText value="#{item.city}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Date"/>
                            </f:facet>
                            <h:outputText value="#{item.date}">
                                <f:convertDateTime pattern="MM/dd/yyyy" />
                            </h:outputText>
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
                                <h:outputText value="Wind"/>
                            </f:facet>
                            <h:outputText value="#{item.wind}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Pressure"/>
                            </f:facet>
                            <h:outputText value="#{item.pressure}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Humidity"/>
                            </f:facet>
                            <h:outputText value="#{item.humidity}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Clouds"/>
                            </f:facet>
                            <h:outputText value="#{item.clouds}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Id"/>
                            </f:facet>
                            <h:outputText value="#{item.id}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText escape="false" value="&nbsp;"/>
                            </f:facet>
                            <h:commandLink value="Show" action="#{weather.detailSetup}">
                                <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][weather.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{weather.editSetup}">
                                <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][weather.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{weather.remove}">
                                <f:param name="jsfcrud.currentWeather" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][weather.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>

                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{weather.createSetup}" value="New Weather"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />


            </h:form>
        </body>
    </html>
</f:view>
