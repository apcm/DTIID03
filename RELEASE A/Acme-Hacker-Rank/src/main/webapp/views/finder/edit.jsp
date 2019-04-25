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
	
	<form:form action="finder/hacker/edit.do" modelAttribute="finder">
		<form:hidden path="id"/>		
		
		<form:label path="keyword">
			<spring:message code="finder.keyword"/>
		</form:label>
		<form:input path="keyword"/>
		<form:errors cssClass ="error" path ="keyword"/>
		<br />
		
		<form:label path="deadline">
			<spring:message code="finder.deadline"/>
		</form:label>
		<form:input path="deadline"/>
		<form:errors cssClass ="error" path ="deadline"/>
		<br/>
		
		<form:label path="maximumDeadline">
			<spring:message code="finder.maximumDeadline"/>
		</form:label>
		<form:input path="maximumDeadline"/>
		<form:errors cssClass ="error" path ="maximumDeadline"/>
		<br/>
		
		<form:label path="minimumSalary">
			<spring:message code="finder.minimumSalary"/>
		</form:label>
		<form:input path="minimumSalary"/>
		<form:errors cssClass ="error" path ="minimumSalary"/>
		<br/>
		
		<input type="button" name="cancel" value="<spring:message code="finder.cancel"/>" onclick ="javascript: relativeRedir('finder/hacker/show.do');"/>
		<input type="submit" name="save" value="<spring:message code="finder.save"/>"/>
		<input type="submit" name="clear" value="<spring:message code="finder.clear"/>"/>
	</form:form>
	
	
</security:authorize>