<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="educationData/hacker/edit.do"
	modelAttribute="educationData">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="curricula" />

	<fieldset>
		<legend align="left">
			<spring:message code="edit.educationData" />
		</legend>

		<form:label path="degree">
			<spring:message code="educationData.degree" />* :
		</form:label>
		<form:input path="degree" />
		<form:errors cssClass="error" path="degree" />
		<br /> <br />
		
		<form:label path="institution">
			<spring:message code="educationData.institution" />* :
		</form:label>
		<form:input path="institution" />
		<form:errors cssClass="error" path="institution" />
		<br /> <br />

		<form:label path="mark">
			<spring:message code="educationData.mark" />* :
		</form:label>
		<form:input path="mark" />
		<form:errors cssClass="error" path="mark" />
		<br /> <br />
		
		<form:label path="startMoment">
			<spring:message code="educationData.startMoment" />* :
		</form:label>
		<form:input path="startMoment" />
		<form:errors cssClass="error" path="startMoment" />
		<br /> <br />
		
		<form:label path="endMoment">
			<spring:message code="educationData.endMoment" />* :
		</form:label>
		<form:input path="endMoment" />
		<form:errors cssClass="error" path="endMoment" />
		<br /> <br />
	</fieldset>
	<br />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="educationData.save" />" />&nbsp;
		<jstl:if test="${problem.id!=0 }">
	<input type="submit" name="delete"
		value="<spring:message code="educationData.delete" />"
		onclick="return confirm('<spring:message code="educationData.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('curricula/hacker/show.do?curriculaId=${educationData.curricula.id}')"
		value="<spring:message code="educationData.edit.cancel" />" />

</form:form>