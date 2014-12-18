<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Listing Calendar Items</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Calendar Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Calendar Items Found)<br />" rendered="#{calendar.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{calendar.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{calendar.pagingInfo.firstItem + 1}..#{calendar.pagingInfo.lastItem} of #{calendar.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{calendar.prev}" value="Previous #{calendar.pagingInfo.batchSize}" rendered="#{calendar.pagingInfo.firstItem >= calendar.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{calendar.next}" value="Next #{calendar.pagingInfo.batchSize}" rendered="#{calendar.pagingInfo.lastItem + calendar.pagingInfo.batchSize <= calendar.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{calendar.next}" value="Remaining #{calendar.pagingInfo.itemCount - calendar.pagingInfo.lastItem}"
                                   rendered="#{calendar.pagingInfo.lastItem < calendar.pagingInfo.itemCount && calendar.pagingInfo.lastItem + calendar.pagingInfo.batchSize > calendar.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{calendar.calendarItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
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
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][calendar.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{calendar.editSetup}">
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][calendar.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{calendar.remove}">
                                <f:param name="jsfcrud.currentCalendar" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][calendar.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>

                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{calendar.createSetup}" value="New Calendar"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />


            </h:form>
        </body>
    </html>
</f:view>
