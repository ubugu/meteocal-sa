<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Listing Participant Items</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Participant Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Participant Items Found)<br />" rendered="#{participant.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{participant.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{participant.pagingInfo.firstItem + 1}..#{participant.pagingInfo.lastItem} of #{participant.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{participant.prev}" value="Previous #{participant.pagingInfo.batchSize}" rendered="#{participant.pagingInfo.firstItem >= participant.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{participant.next}" value="Next #{participant.pagingInfo.batchSize}" rendered="#{participant.pagingInfo.lastItem + participant.pagingInfo.batchSize <= participant.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{participant.next}" value="Remaining #{participant.pagingInfo.itemCount - participant.pagingInfo.lastItem}"
                                   rendered="#{participant.pagingInfo.lastItem < participant.pagingInfo.itemCount && participant.pagingInfo.lastItem + participant.pagingInfo.batchSize > participant.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{participant.participantItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
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
                                <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][participant.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{participant.editSetup}">
                                <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][participant.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{participant.remove}">
                                <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][participant.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>

                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{participant.createSetup}" value="New Participant"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />


            </h:form>
        </body>
    </html>
</f:view>
