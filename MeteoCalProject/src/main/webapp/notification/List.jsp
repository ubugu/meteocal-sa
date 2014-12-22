<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Listing Notification Items</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Notification Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Notification Items Found)<br />" rendered="#{notification.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{notification.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{notification.pagingInfo.firstItem + 1}..#{notification.pagingInfo.lastItem} of #{notification.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{notification.prev}" value="Previous #{notification.pagingInfo.batchSize}" rendered="#{notification.pagingInfo.firstItem >= notification.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{notification.next}" value="Next #{notification.pagingInfo.batchSize}" rendered="#{notification.pagingInfo.lastItem + notification.pagingInfo.batchSize <= notification.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{notification.next}" value="Remaining #{notification.pagingInfo.itemCount - notification.pagingInfo.lastItem}"
                                   rendered="#{notification.pagingInfo.lastItem < notification.pagingInfo.itemCount && notification.pagingInfo.lastItem + notification.pagingInfo.batchSize > notification.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{notification.notificationItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
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
                                <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][notification.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{notification.editSetup}">
                                <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][notification.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{notification.remove}">
                                <f:param name="jsfcrud.currentNotification" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][notification.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>

                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{notification.createSetup}" value="New Notification"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />


            </h:form>
        </body>
    </html>
</f:view>
