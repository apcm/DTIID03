
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="positions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">


	<display:column property="title" titleKey="position.title" />
	<display:column property="description" titleKey="position.description" />
	<display:column property="deadline" titleKey="position.deadline" />
	<display:column property="skills" titleKey="position.skills" />
	<display:column property="technologies"
		titleKey="position.technologies" />
	<display:column property="profile" titleKey="position.profile" />
	<display:column property="salary" titleKey="position.salary" />
	<display:column property="ticker" titleKey="position.ticker" />
	<display:column property="finalMode" titleKey="position.finalmode" />

</display:table>

<input type="button" name="companies"
		value="<spring:message code="position.companies" />"
		onclick="javascript: relativeRedir('company/list.do');" />