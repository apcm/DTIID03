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
		<spring:message code="brotherhood.edit.label.name" />:
	</h4>
	<jstl:out value="${brotherhood.name}"></jstl:out>
	
	<h4>
		<spring:message code="brotherhood.edit.label.middleName" />:
	</h4>
	<jstl:out value="${brotherhood.middleName}"></jstl:out>

	<h4>
		<spring:message code="brotherhood.edit.label.surname" />:
	</h4>
	<jstl:out value="${brotherhood.surname}"></jstl:out>
	
	<h4>
		<spring:message code="brotherhood.edit.label.address" />:
	</h4>
	<jstl:out value="${brotherhood.address}"></jstl:out>
	
	<h4>
		<spring:message code="brotherhood.edit.label.email" />:
	</h4>
	<jstl:out value="${brotherhood.email}"></jstl:out>
	
	<h4>
		<spring:message code="brotherhood.edit.label.phoneNumber" />:
	</h4>
	<jstl:out value="${brotherhood.phoneNumber}"></jstl:out>

	<h4>
		<spring:message code="brotherhood.edit.label.username" />:
	</h4>
	<jstl:out value="${brotherhood.userAccount.username}"></jstl:out>
	
		<h4>
		<spring:message code="brotherhood.title" />:
	</h4>
	<jstl:out value="${brotherhood.title}"></jstl:out>

	<h4>
		<spring:message code="brotherhood.stablishment.date" />:
	</h4>
	<jstl:out value="${brotherhood.stablishmentDate}"></jstl:out>

	<h4>
		<spring:message code="brotherhood.photos" />:
	</h4>
	<jstl:out value="${brotherhood.urls}"></jstl:out>
	
<br/><br/>

	<spring:message code="brotherhood.export.explanation" var="exportExplanation"/>
	<jstl:out value="${exportExplanation}"/>
	
	<a href="brotherhood/brotherhood/edit.do"> Link </a>
	
	
	<br/>
<security:authorize access="hasRole('MEMBER')">
	<jstl:if test="${enrolement.status=='APPROVED'}">
	<form:form action="brotherhood/member/show.do" modelAttribute="brotherhood">
	<form:hidden path="id"/>
	<input type="submit" name="delete"
			value="<spring:message code="brotherhood.delete" />"
			onclick="return confirm('<spring:message code="message.confirm.delete" />')" />&nbsp;
	</form:form>
	</jstl:if>
	
	<input type="button" name="back" onclick="javascript: window.location.replace('enrolements/member/list.do')"
		value="<spring:message code="member.back" />" />
	</security:authorize>