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
		<spring:message code="hacker.edit.label.name" />:
	</h4>
	<jstl:out value="${hacker.name}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.edit.label.surname" />:
	</h4>
	<jstl:out value="${hacker.surname}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.edit.label.address" />:
	</h4>
	<jstl:out value="${hacker.address}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.edit.label.vat" />:
	</h4>
	<jstl:out value="${hacker.vat}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.edit.label.email" />:
	</h4>
	<jstl:out value="${hacker.email}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.edit.label.phoneNumber" />:
	</h4>
	<jstl:out value="${hacker.phoneNumber}"></jstl:out>

	<h4>
		<spring:message code="hacker.edit.label.username" />:
	</h4>
	<jstl:out value="${hacker.userAccount.username}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.holderName" />:
	</h4>
	<jstl:out value="${hacker.holderName}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.makeName" />:
	</h4>
	<jstl:out value="${hacker.makeName}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.number" />:
	</h4>
	<jstl:out value="${hacker.number}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.expirationYear" />:
	</h4>
	<jstl:out value="${hacker.expirationYear}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.expirationMonth" />:
	</h4>
	<jstl:out value="${hacker.expirationMonth}"></jstl:out>
	
	<h4>
		<spring:message code="hacker.cvv" />:
	</h4>
	<jstl:out value="${hacker.cvv}"></jstl:out>
	

<br/><br/>

	<spring:message code="hacker.export.explanation" var="exportExplanation"/>
	<jstl:out value="${exportExplanation}"/>
	
	<a href="hacker/hacker/edit.do"> Link </a>
	
	
	<br/>
