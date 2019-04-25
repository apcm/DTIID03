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
	
	
	<spring:message code="finder.moment"/>
	<jstl:out value="${finder.moment}"/>
	<br/>
	
	<spring:message code="finder.keyword"/>
	<jstl:out value="${finder.keyword}"/>
	<br/>
	
	<spring:message code="finder.deadline"/>
	<jstl:out value="${finder.deadline}"/>
	<br/>
	
	<spring:message code="finder.maximumDeadline"/>
	<jstl:out value="${finder.maximumDeadline}"/>
	<br/>
	
	<spring:message code="finder.minimumSalary"/>
	<jstl:out value="${finder.minimumSalary}"/>
	<br/>
	
	<h3><spring:message code="finder.results"/></h3>
	<br/>
	<jstl:forEach var = "result" items="${finder.positions}">
		<jstl:out value="${result.title}"/>
		<a href="position/display.do?positionId=${result.id}">
		<spring:message code="position.display"/>
		</a>
		<br/>
	</jstl:forEach>
	<%-- <display:table pagesize="5" class="displaytag" keepStatus="true"
	name="positions" requestURI="finder/hacker/show.do?finderId=${finder.id}" id="row">
	</display:table> --%>
	<br/>
	
	<a href="finder/hacker/edit.do"><spring:message code="finder.edit"/></a>
</security:authorize>