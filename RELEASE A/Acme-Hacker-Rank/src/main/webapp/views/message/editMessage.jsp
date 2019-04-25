
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


	<form:form action="messages/edit.do" modelAttribute="mesInformation">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="moment"/>
		<form:hidden path="sender"/>
		
		<spring:message code="mes.recipients" />:*
		<form:select multiple="false" id="recipient" path="recipient">
		    <form:options items="${recipient}" itemLabel="email" itemValue="id" />
		    </form:select>
		   <br />
		
		
		<form:label path="subject">
			<spring:message code="mes.subject" />:*
		</form:label>
		<form:input path="subject" />
		<form:errors cssClass="error" path="subject" />
		<br />	
		
		<form:label path="body">
			<spring:message code="mes.body" />:*
		</form:label>
		<form:textarea path="body" />
		<form:errors cssClass="error" path="body" />
		<br />
		
		<form:label path="tag">
			<spring:message code="mes.tags" />:
		</form:label>
		<form:input path="tag" />
		<form:errors cssClass="error" path="tag" />
		<br />
		
		<input type="submit" name = "send" value = "<spring:message code ="mes.send" /> " />
		<spring:message code ="mes.cancel" var="cancel" />
		
		<security:authorize access="hasRole('ADMIN')">
		<input type="submit" name = "sendToAll" value = "<spring:message code ="mes.sendToAll" /> " />
		</security:authorize>
		
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('boxes/list.do');" />
		
		
		
	
	
	</form:form>
	
	