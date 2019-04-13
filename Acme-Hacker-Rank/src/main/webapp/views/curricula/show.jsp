<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('HACKER')">

	<h3 style="color:blue;">
		<spring:message code="curricula.name" />:
	</h3>
	<jstl:out value="${curricula.name}"></jstl:out>
	
	<jstl:forEach items="${personalData}" var="personalData">
	
	<h3 style="color:blue;">
		<spring:message code="personalData.part" />:
	</h3>
	
	<h4>
		<spring:message code="personalData.fullName" />:
	</h4>
	<jstl:out value="${personalData.fullName}"></jstl:out>
	
	<h4>
		<spring:message code="personalData.statement" />:
	</h4>
	<jstl:out value="${personalData.statement}"></jstl:out>
	
	<h4>
		<spring:message code="personalData.phoneNumber" />:
	</h4>
	<jstl:out value="${personalData.phoneNumber}"></jstl:out>
	
	<h4>
		<spring:message code="personalData.gitProfile" />:
	</h4>
	<jstl:out value="${personalData.gitProfile}"></jstl:out>
	
	<h4>
		<spring:message code="personalData.linkedInProfile" />:
	</h4>
	<jstl:out value="${personalData.linkedInProfile}"></jstl:out>
	
	<br/>
	<br/>
	
	<a href="personalData/hacker/edit.do?personalDataId=${personalData.id }">
	<spring:message code="edit.pesonalData"/></a>
	
	<br/>
	
	</jstl:forEach>
	
	<a href="personalData/hacker/create.do?curriculaId=${curricula.id }">
	<spring:message code="create.pesonalData"/></a>
	<br/>
	
	<jstl:forEach items="${educationData}" var="educationData">
	<h3 style="color:blue;">
		<spring:message code="educationData.part" />:
	</h3>
	
	<h4>
		<spring:message code="educationData.degree" />:
	</h4>
	<jstl:out value="${educationData.degree}"></jstl:out>
	
	<h4>
		<spring:message code="educationData.institution" />:
	</h4>
	<jstl:out value="${educationData.institution}"></jstl:out>
	
	<h4>
		<spring:message code="educationData.mark" />:
	</h4>
	<jstl:out value="${educationData.mark}"></jstl:out>
	
	<h4>
		<spring:message code="educationData.startMoment" />:
	</h4>
	<jstl:out value="${educationData.startMoment}"></jstl:out>
	
	<h4>
		<spring:message code="educationData.endMoment" />:
	</h4>
	<jstl:out value="${educationData.endMoment}"></jstl:out>
	
	<br/>
	<br/>
	
	<a href="educationData/hacker/edit.do?educationDataId=${educationData.id }">
	<spring:message code="edit.educationData"/></a>
	
	<br/>
	
	</jstl:forEach>
	
	<a href="educationData/hacker/create.do?curriculaId=${curricula.id }">
	<spring:message code="create.educationData"/></a>
	
	<br/>
	
	<jstl:forEach items="${positionData}" var="positionData">
	<h3 style="color:blue;">
		<spring:message code="positionData.part" />:
	</h3>
	
	<h4>
		<spring:message code="positionData.title" />:
	</h4>
	<jstl:out value="${positionData.title}"></jstl:out>
	
	<h4>
		<spring:message code="positionData.description" />:
	</h4>
	<jstl:out value="${positionData.description}"></jstl:out>
	
	<h4>
		<spring:message code="positionData.startMoment" />:
	</h4>
	<jstl:out value="${positionData.startMoment}"></jstl:out>
	
	<h4>
		<spring:message code="positionData.endMoment" />:
	</h4>
	<jstl:out value="${positionData.endMoment}"></jstl:out>
	
	<br/>
	<br/>
	
	<a href="positionData/hacker/edit.do?positionDataId=${positionData.id }">
	<spring:message code="edit.positionData"/></a>
	
	<br/>
	
	</jstl:forEach>
	
	<a href="positionData/hacker/create.do?curriculaId=${curricula.id }">
	<spring:message code="create.positionData"/></a>
	<br/>
	
	<jstl:forEach items="${miscellaneousData}" var="miscellaneousData">
	<h3 style="color:blue;">
		<spring:message code="miscellaneousData.part" />:
	</h3>
	
	<h4>
		<spring:message code="miscellaneousData.freeText" />:
	</h4>
	<jstl:out value="${miscellaneousData.freeText}"></jstl:out>
	
	<h4>
		<spring:message code="miscellaneousData.attachments" />:
	</h4>
	<jstl:out value="${miscellaneousData.attachments}"></jstl:out>
	
	<br/>
	<br/>
	
	<a href="miscellaneousData/hacker/edit.do?miscellaneousDataId=${miscellaneousData.id }">
	<spring:message code="edit.miscellaneousData"/></a>
	
	<br/>
	
	</jstl:forEach>
	
	<a href="miscellaneousData/hacker/create.do?curriculaId=${curricula.id }">
	<spring:message code="create.miscellaneousData"/></a>
	
	
	<br/>
	<br/>
	<br/>

	
	<form:form action="curricula/hacker/edit.do" modelAttribute="curricula">
	<form:hidden path="id"/>

	<input type="submit" name="delete"
			value="<spring:message code="curricula.delete" />"
			onclick="return confirm('<spring:message code="curricula.confirm.delete" />')" />&nbsp;
	</form:form>
	
	
	<input type="button" name="back" onclick="javascript: window.location.replace('curricula/hacker/list.do')"
		value="<spring:message code="curricula.back" />" />
	</security:authorize>