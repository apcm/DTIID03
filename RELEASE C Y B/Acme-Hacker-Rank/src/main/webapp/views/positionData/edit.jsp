<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="positionData/hacker/edit.do"
	modelAttribute="positionData">
	<form:hidden path="id" />
	<jstl:if test="${positionData.id==0 }">
	<form:hidden path="version" />

	<form:hidden path="curricula" />
	</jstl:if>

	<fieldset>
		<legend align="left">
			<spring:message code="edit.positionData" />
		</legend>

		<form:label path="title">
			<spring:message code="positionData.title" />* :
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br /> <br />
		
		<form:label path="description">
			<spring:message code="positionData.description" />* :
		</form:label>
		<form:input path="description" />
		<form:errors cssClass="error" path="description" />
		<br /> <br />

		<form:label path="startMoment">
			<spring:message code="positionData.startMoment" />* (yyyy-MM-dd):
		</form:label>
		<form:input path="startMoment"/>
		<form:errors cssClass="error" path="startMoment" />
		<br /> <br />
		
		<form:label path="endMoment">
			<spring:message code="positionData.endMoment" /> (yyyy-MM-dd):
		</form:label>
		<form:input path="endMoment" />
		<form:errors cssClass="error" path="endMoment" />
		<br /> <br />
	
	</fieldset>
	<br />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="positionData.save" />" />&nbsp;
		<jstl:if test="${positionData.id!=0 }">
	<input type="submit" name="delete"
		value="<spring:message code="positionData.delete" />"
		onclick="return confirm('<spring:message code="positionData.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<jstl:if test="${positionData.curricula.id!=null }">
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('curricula/hacker/show.do?curriculaId=${positionData.curricula.id}')"
		value="<spring:message code="positionData.edit.cancel" />" />
	</jstl:if>
	<jstl:if test="${positionData.curricula.id==null }">
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('curricula/hacker/list.do')"
		value="<spring:message code="positionData.edit.cancel" />" />
	</jstl:if>
</form:form>