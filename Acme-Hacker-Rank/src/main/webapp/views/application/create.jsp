<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="application/hacker/create.do" modelAttribute="curricula">
	<form:hidden path="application"/>

	<fieldset>
		<legend align="left">
			<spring:message code="select.curricula" />
		</legend>

		<form:select path="id">
		<form:options  items="${lC}" itemValue="id" itemLabel="name"/>
		</form:select>
		<br /> <br />

	</fieldset>
	<br />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="curricula.save" />" />&nbsp;
		
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('application/hacker/list.do')"
		value="<spring:message code="curricula.edit.cancel" />" />

</form:form>