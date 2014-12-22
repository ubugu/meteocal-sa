<%@page contentType="text/html"%>
<%@page pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
            <title>Editing Participant</title>
            <link rel="stylesheet" type="text/css" href="/MeteoCalProject/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Editing Participant</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Organiser:"/>
                    <h:inputText id="organiser" value="#{participant.participant.organiser}" title="Organiser" required="true" requiredMessage="The organiser field is required." />
                    <h:outputText value="Participant:"/>
                    <h:inputText id="participant" value="#{participant.participant.participant}" title="Participant" required="true" requiredMessage="The participant field is required." />
                    <h:outputText value="User1:"/>
                    <h:outputText value=" #{participant.participant.user1}" title="User1" />
                    <h:outputText value="Event1:"/>
                    <h:outputText value=" #{participant.participant.event1}" title="Event1" />

                </h:panelGrid>
                <br />
                <h:commandLink action="#{participant.edit}" value="Save">
                    <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant][participant.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{participant.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentParticipant" value="#{jsfcrud_class['jsf.classes.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][participant.participant][participant.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{participant.listSetup}" value="Show All Participant Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
