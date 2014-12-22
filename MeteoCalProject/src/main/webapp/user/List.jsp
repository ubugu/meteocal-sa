<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Listing User Items</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing User Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No User Items Found)<br />" rendered="#{user.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{user.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{user.pagingInfo.firstItem + 1}..#{user.pagingInfo.lastItem} of #{user.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{user.prev}" value="Previous #{user.pagingInfo.batchSize}" rendered="#{user.pagingInfo.firstItem >= user.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{user.next}" value="Next #{user.pagingInfo.batchSize}" rendered="#{user.pagingInfo.lastItem + user.pagingInfo.batchSize <= user.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{user.next}" value="Remaining #{user.pagingInfo.itemCount - user.pagingInfo.lastItem}"
                                   rendered="#{user.pagingInfo.lastItem < user.pagingInfo.itemCount && user.pagingInfo.lastItem + user.pagingInfo.batchSize > user.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{user.userItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
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
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][user.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{user.editSetup}">
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][user.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{user.remove}">
                                <f:param name="jsfcrud.currentUser" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][user.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>

                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{user.createSetup}" value="New User"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />


            </h:form>
        </body>
    </html>
</f:view>
