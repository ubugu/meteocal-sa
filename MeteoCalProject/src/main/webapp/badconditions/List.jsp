<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Listing Badconditions Items</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Badconditions Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Badconditions Items Found)<br />" rendered="#{badconditions.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{badconditions.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{badconditions.pagingInfo.firstItem + 1}..#{badconditions.pagingInfo.lastItem} of #{badconditions.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{badconditions.prev}" value="Previous #{badconditions.pagingInfo.batchSize}" rendered="#{badconditions.pagingInfo.firstItem >= badconditions.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{badconditions.next}" value="Next #{badconditions.pagingInfo.batchSize}" rendered="#{badconditions.pagingInfo.lastItem + badconditions.pagingInfo.batchSize <= badconditions.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{badconditions.next}" value="Remaining #{badconditions.pagingInfo.itemCount - badconditions.pagingInfo.lastItem}"
                                   rendered="#{badconditions.pagingInfo.lastItem < badconditions.pagingInfo.itemCount && badconditions.pagingInfo.lastItem + badconditions.pagingInfo.batchSize > badconditions.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{badconditions.badconditionsItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
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
                                <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][badconditions.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{badconditions.editSetup}">
                                <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][badconditions.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{badconditions.remove}">
                                <f:param name="jsfcrud.currentBadconditions" value="#{jsfcrud_class['jsf.classes.controller.bean.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][badconditions.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>

                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{badconditions.createSetup}" value="New Badconditions"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />


            </h:form>
        </body>
    </html>
</f:view>
