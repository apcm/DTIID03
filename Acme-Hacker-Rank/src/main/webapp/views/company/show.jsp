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
	

	<h4>
		<spring:message code="company.holderName" />:
	</h4>
	<jstl:out value="${company.holderName}"></jstl:out>
	
	<h4>
		<spring:message code="company.makeName" />:
	</h4>
	<jstl:out value="${company.makeName}"></jstl:out>
	
	<h4>
		<spring:message code="company.number" />:
	</h4>
	<jstl:out value="${company.number}"></jstl:out>
	
	<h4>
		<spring:message code="company.expirationYear" />:
	</h4>
	<jstl:out value="${company.expirationYear}"></jstl:out>
	
	<h4>
		<spring:message code="company.expirationMonth" />:
	</h4>
	<jstl:out value="${company.expirationMonth}"></jstl:out>
	
	<h4>
		<spring:message code="company.cvv" />:
	</h4>
	<jstl:out value="${company.cvv}"></jstl:out>
	
	<h4>
		<spring:message code="company.socialprofile" />:
	</h4>
	<jstl:forEach items="${company.socialProfiles}" var="sp">
		<jstl:out value="${sp.nick}"></jstl:out>
	</jstl:forEach>
	
	


<br/><br/>

	<spring:message code="company.export.explanation" var="exportExplanation"/>
	<jstl:out value="${exportExplanation}"/>
	
	<a href="company/company/edit.do"> Link </a>
	
	
	<br/>
