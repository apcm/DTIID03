<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




	<h4>
		<spring:message code="problem.title" />:
	</h4>
	<jstl:out value="${problem.title}"></jstl:out>
	
	<h4>
		<spring:message code="problem.statement" />:
	</h4>
	<jstl:out value="${problem.statement}"></jstl:out>
	
	<h4>
		<spring:message code="problem.hint" />:
	</h4>
	<jstl:out value="${problem.hint}"></jstl:out>
	
	<h4>
		<spring:message code="problem.attachments" />:
	</h4>
	<jstl:forEach items="${problem.attachments}" var="attachment">
	<jstl:out value="attachment"/>
	</jstl:forEach>
	
	<h4>
		<spring:message code="problem.finalMode" />:
	</h4>
	<jstl:out value="${problem.finalMode}"></jstl:out>
	
	
	<br/>
	<br/>
	<br/>
<security:authorize access="hasRole('COMPANY')">
	
	<form:form action="problem/company/edit.do" modelAttribute="problem">
	<form:hidden path="id"/>

	<jstl:if test="${!problem.finalMode}">
	<input type="submit" name="delete"
			value="<spring:message code="problem.delete" />"
			onclick="return confirm('<spring:message code="problem.confirm.delete" />')" />&nbsp;
	</jstl:if>
	</form:form>

	
	<input type="button" name="back" onclick="javascript: window.location.replace('problem/company/list.do')"
		value="<spring:message code="problem.back" />" />
	</security:authorize>