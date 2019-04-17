<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	<spring:message code="dashboard.avgPositions"/>
	<jstl:out value="${avgPositions}"/>
	<br/>
	<spring:message code="dashboard.minPositions"/>
	<jstl:out value="${minPositions}"/>
	<br/>
	<spring:message code="dashboard.maxPositions"/>
	<jstl:out value="${maxPositions}"/>
	<br/>
	<spring:message code="dashboard.stddevPositions"/>
	<jstl:out value="${stddevPositions}"/>
	<br/>
	
	<spring:message code="dashboard.companiesWithMorePositions"/>
	<jstl:out value="${companiesWithMorePositions}"/>
	<br/>
	<spring:message code="dashboard.hackersWithMoreApplications"/>
	<jstl:out value="${hackersWithMoreApplications}"/>
	<br/>
	
	<spring:message code="dashboard.avgApplications"/>
	<jstl:out value="${avgApplications}"/>
	<br/>
	<spring:message code="dashboard.minApplications"/>
	<jstl:out value="${minApplications}"/>
	<br/>
	<spring:message code="dashboard.maxApplications"/>
	<jstl:out value="${maxApplications}"/>
	<br/>
	<spring:message code="dashboard.stddevApplications"/>
	<jstl:out value="${stddevApplications}"/>
	<br/>
	
	<spring:message code="dashboard.avgSalary"/>
	<jstl:out value="${avgSalary}"/>
	<br/>
	<spring:message code="dashboard.minSalary"/>
	<jstl:out value="${minSalary}"/>
	<br/>
	<spring:message code="dashboard.maxSalary"/>
	<jstl:out value="${maxSalary}"/>
	<br/>
	<spring:message code="dashboard.stddevSalary"/>
	<jstl:out value="${stddevSalary}"/>
	<br/>
	
	<spring:message code="dashboard.highestSalaryPosition"/>
	<jstl:out value="${highestSalaryPosition}"/>
	<br/>
	<spring:message code="dashboard.lowestSalaryPosition"/>
	<jstl:out value="${lowestSalaryPosition}"/>
	<br/>
	
	<spring:message code="dashboard.minCurricula"/>
	<jstl:out value="${minCurricula}"/>
	<br/>
	<spring:message code="dashboard.maxCurricula"/>
	<jstl:out value="${maxCurricula}"/>
	<br/>
	<spring:message code="dashboard.avgCurricula"/>
	<jstl:out value="${avgCurricula}"/>
	<br/>
	<spring:message code="dashboard.stddevCurricula"/>
	<jstl:out value="${stddevCurricula}"/>
	<br/>
	<spring:message code="dashboard.minResults"/>
	<jstl:out value="${minResults}"/>
	<br/>
	<spring:message code="dashboard.maxResults"/>
	<jstl:out value="${maxResults}"/>
	<br/>
	<spring:message code="dashboard.avgResults"/>
	<jstl:out value="${avgResults}"/>
	<br/>
	<spring:message code="dashboard.stddevResults"/>
	<jstl:out value="${stddevResults}"/>
	<br/>
	<spring:message code="dashboard.ratioFinders"/>
	<jstl:out value="${ratioFinders}"/>
	<br/>
	
</security:authorize>