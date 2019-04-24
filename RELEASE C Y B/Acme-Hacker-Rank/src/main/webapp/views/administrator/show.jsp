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
		<spring:message code="administrator.edit.label.name" />:
	</h4>
	<jstl:out value="${administrator.name}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.edit.label.surname" />:
	</h4>
	<jstl:out value="${administrator.surname}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.edit.label.address" />:
	</h4>
	<jstl:out value="${administrator.address}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.edit.label.vat" />:
	</h4>
	<jstl:out value="${administrator.vat}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.edit.label.email" />:
	</h4>
	<jstl:out value="${administrator.email}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.edit.label.phoneNumber" />:
	</h4>
	<jstl:out value="${administrator.phoneNumber}"></jstl:out>

	<h4>
		<spring:message code="administrator.edit.label.username" />:
	</h4>
	<jstl:out value="${administrator.userAccount.username}"></jstl:out>
	

	<h4>
		<spring:message code="administrator.holderName" />:
	</h4>
	<jstl:out value="${administrator.holderName}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.makeName" />:
	</h4>
	<jstl:out value="${administrator.makeName}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.number" />:
	</h4>
	<jstl:out value="${administrator.number}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.expirationYear" />:
	</h4>
	<jstl:out value="${administrator.expirationYear}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.expirationMonth" />:
	</h4>
	<jstl:out value="${administrator.expirationMonth}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.cvv" />:
	</h4>
	<jstl:out value="${administrator.cvv}"></jstl:out>
	
	<h4>
		<spring:message code="administrator.socialprofile" />:
	</h4>
	<jstl:forEach items="${administrator.socialProfiles}" var="sp">
		<jstl:out value="${sp.nick}"></jstl:out>
	</jstl:forEach>

<br/><br/>

	<spring:message code="administrator.export.explanation" var="exportExplanation"/>
	<jstl:out value="${exportExplanation}"/>
	
	<a href="administrator/administrator/edit.do"> Link </a>
	
	
	<br/>

