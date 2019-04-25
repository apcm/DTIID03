<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<display:table pagesize = "5" class="displaytag" name="messageBoxes" requestURI="/boxes/list.do" id="row">

	<spring:message code="messageBox.name" var= "nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />

	
	<display:column>
	<a href="messages/list.do?boxId=${row.id}">
	<spring:message code="message.list"/>
	</a>
	</display:column>
		

	

	
	</display:table>
	
	
	<a href="messages/create.do">
				<spring:message	code="mes.create" />
	</a>
	<security:authorize access="hasRole('ADMIN')">
		<br/>
	<a href="messages/breach.do">
				<spring:message	code="mes.breach.create" />
	</a>
		</security:authorize>