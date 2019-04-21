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
		<spring:message code="company.edit.label.name" />:
	</h4>
	<jstl:out value="${company.name}"></jstl:out>
	
	<h4>
		<spring:message code="company.edit.label.surname" />:
	</h4>
	<jstl:out value="${company.surname}"></jstl:out>
	
	<h4>
		<spring:message code="company.edit.label.address" />:
	</h4>
	<jstl:out value="${company.address}"></jstl:out>
	
	<h4>
		<spring:message code="company.edit.label.vat" />:
	</h4>
	<jstl:out value="${company.vat}"></jstl:out>
	
	<h4>
		<spring:message code="company.edit.label.email" />:
	</h4>
	<jstl:out value="${company.email}"></jstl:out>
	
	<h4>
		<spring:message code="company.edit.label.phoneNumber" />:
	</h4>
	<jstl:out value="${company.phoneNumber}"></jstl:out>

	<h4>
		<spring:message code="company.edit.label.username" />:
	</h4>
	<jstl:out value="${company.userAccount.username}"></jstl:out>
	
	<h4>
		<spring:message code="company.companyName" />:
	</h4>
	<jstl:out value="${company.companyName}"></jstl:out>
	

<br/><br/>

	<spring:message code="company.export.explanation" var="exportExplanation"/>
	<jstl:out value="${exportExplanation}"/>
	
	<a href="company/company/edit.do"> Link </a>
	
	
	<br/>
<security:authorize access="hasRole('MEMBER')">
	<jstl:if test="${enrolement.status=='APPROVED'}">
	<form:form action="company/member/show.do" modelAttribute="company">
	<form:hidden path="id"/>
	<input type="submit" name="delete"
			value="<spring:message code="company.delete" />"
			onclick="return confirm('<spring:message code="message.confirm.delete" />')" />&nbsp;
	</form:form>
	</jstl:if>
	
	<input type="button" name="back" onclick="javascript: window.location.replace('enrolements/member/list.do')"
		value="<spring:message code="member.back" />" />
	</security:authorize>