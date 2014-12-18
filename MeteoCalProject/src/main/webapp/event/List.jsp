<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Listing Event Items</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Event Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Event Items Found)<br />" rendered="#{event.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{event.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{event.pagingInfo.firstItem + 1}..#{event.pagingInfo.lastItem} of #{event.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{event.prev}" value="Previous #{event.pagingInfo.batchSize}" rendered="#{event.pagingInfo.firstItem >= event.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{event.next}" value="Next #{event.pagingInfo.batchSize}" rendered="#{event.pagingInfo.lastItem + event.pagingInfo.batchSize <= event.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{event.next}" value="Remaining #{event.pagingInfo.itemCount - event.pagingInfo.lastItem}"
                                   rendered="#{event.pagingInfo.lastItem < event.pagingInfo.itemCount && event.pagingInfo.lastItem + event.pagingInfo.batchSize > event.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{event.eventItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
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
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][event.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{event.editSetup}">
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][event.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{event.remove}">
                                <f:param name="jsfcrud.currentEvent" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][event.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>

                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{event.createSetup}" value="New Event"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />


            </h:form>
        </body>
    </html>
</f:view>
