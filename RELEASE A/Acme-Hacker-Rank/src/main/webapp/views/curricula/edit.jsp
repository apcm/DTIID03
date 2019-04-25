<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="curricula/hacker/edit.do"
	modelAttribute="curricula">
	<form:hidden path="id" />
	<jstl:if test="${curricula.id==0 }">
	<form:hidden path="isCopy"/>

	<form:hidden path="version" />

	<form:hidden path="hacker" />
	</jstl:if>

	<fieldset>
		<legend align="left">
			<spring:message code="edit.curricula" />
		</legend>

		<form:label path="name">
			<spring:message code="curricula.name" />* :
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br /> <br />

	</fieldset>
	<br />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="curricula.save" />" />&nbsp;
		<jstl:if test="${curricula.id!=0 }">
	<input type="submit" name="delete"
		value="<spring:message code="curricula.delete" />"
		onclick="return confirm('<spring:message code="curricula.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('curricula/hacker/list.do')"
		value="<spring:message code="curricula.edit.cancel" />" />

</form:form>