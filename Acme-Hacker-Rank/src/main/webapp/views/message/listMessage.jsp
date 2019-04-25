<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h4><spring:message code="message.table.messages" /></h4>

<display:table pagesize = "5" class="displaytag" name="messages" requestURI="${requestURI}" id="row">

	<spring:message code="mes.sender" var= "senderHeader" />
	<display:column property="sender.userAccount.username" title="${senderHeader}" sortable="false" />

	<spring:message code="mes.subject" var= "subjectHeader" />
	<display:column property="subject" title="${subjectHeader}" sortable="false" />
	
	<spring:message code="mes.body" var= "bodyHeader" />
	<display:column property="body" title="${bodyHeader}" sortable="false" />
	
	<spring:message code="mes.moment" var= "momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="false" format="{0,date,yyyy/MM/dd HH:mm}" />
	
	<spring:message code="mes.tags" var= "tagHeader" />
	<display:column property="tag" title="${tagHeader}" sortable="true" />
	
	
		<display:column>
			<a href="messages/display.do?messageId=${row.id}">
			<spring:message code="mes.display"/>
			</a>
		</display:column>

	
	</display:table>
	
	<input type="button" name="Back" value="<spring:message code="messageBox.list" />"
		onclick="javascript:relativeRedir('boxes/list.do');"/>
	
	
	