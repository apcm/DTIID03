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
		<spring:message code="application.moment" />:
	</h4>
	<jstl:out value="${application.moment}"></jstl:out>
	
		<h4>
		<spring:message code="application.position.name" />:
	</h4>
	<jstl:out value="${application.position.title}"></jstl:out>
	
		<h4>
		<spring:message code="application.status" />:
	</h4>
	<jstl:out value="${application.status}"></jstl:out>
	
		<h4>
		<spring:message code="application.problem.title2" />:
	</h4>
	<jstl:out value="${application.problem.title}"></jstl:out>
	
	
		<h4>
		<spring:message code="application.problem.hint" />:
	</h4>
	<jstl:out value="${application.problem.hint}"></jstl:out>
	
		<h4>
		<spring:message code="application.answerExplanation" />:
	</h4>
	<jstl:out value="${application.answerExplanation}"></jstl:out>
	
	
		<h4>
		<spring:message code="application.answerLink" />:
	</h4>
	<jstl:out value="${application.link}"></jstl:out>
	
	
	
	
	