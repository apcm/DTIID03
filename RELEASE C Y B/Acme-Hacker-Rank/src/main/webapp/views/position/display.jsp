<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>



<b><spring:message code="position.title"/></b>
<jstl:out value="${position.title}"/>
<br/>

<b><spring:message code="position.description"/></b>
<jstl:out value="${position.description}"/>
<br/>

<b><spring:message code="position.deadline"/></b>
<jstl:out value="${position.deadline}"/>
<br/>

<b><spring:message code="position.skills"/></b>
<jstl:out value="${position.skills}"/>
<br/>

<b><spring:message code="position.finalMode"/></b>
<jstl:out value="${position.finalMode}"/>
<br/>

<b><spring:message code="position.salary"/></b>
<jstl:out value="${position.salary}"/>
<br/>

<b><spring:message code="position.company"/></b>
<jstl:out value="${position.company.companyName}"/>
<br/>

<security:authorize access="hasRole('COMPANY')">
<b><spring:message code="position.problems"/></b>
<br/>
	<jstl:forEach var = "result" items="${position.problems}">
		<jstl:out value="${result.title}"/>
		<br/>
	</jstl:forEach>
	
</security:authorize>