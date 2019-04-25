<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<b><spring:message code="mes.sender"/></b>
<jstl:out value="${messageInfo.sender.email}"/>
<br/>

<b><spring:message code="mes.recipients"/></b>
<jstl:out value="${messageInfo.recipient.email}"/>
<br/>

<b><spring:message code="mes.subject"/></b>
<jstl:out value="${messageInfo.subject}"/>
<br/>



<b><spring:message code="mes.body"/></b>
<jstl:out value="${messageInfo.body}"/>
<br/>


	<a href="messages/delete.do?messageId=${messageInfo.id}">
			<spring:message code="mes.edit"/>
	</a>
	<br>

	


