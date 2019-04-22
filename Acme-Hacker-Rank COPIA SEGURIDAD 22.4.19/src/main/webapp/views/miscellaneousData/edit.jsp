<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="miscellaneousData/hacker/edit.do"
	modelAttribute="miscellaneousData">
	<form:hidden path="id" />
	<jstl:if test="${miscellaneousData.id==0 }">
	<form:hidden path="version" />

	<form:hidden path="curricula" />
	</jstl:if>

	<fieldset>
		<legend align="left">
			<spring:message code="edit.miscellaneousData" />
		</legend>

		<form:label path="freeText">
			<spring:message code="miscellaneousData.freeText" />* :
		</form:label>
		<form:textarea path="freeText" />
		<form:errors cssClass="error" path="freeText" />
		<br /> <br />
		
		<form:label path="attachments">
			<spring:message code="miscellaneousData.attachments" /> :
		</form:label>
		<form:textarea path="attachments" />
		<form:errors cssClass="error" path="attachments" />
		<br /> <br />
	</fieldset>
	<br />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="miscellaneousData.save" />" />&nbsp;
		<jstl:if test="${miscellaneousData.id!=0 }">
	<input type="submit" name="delete"
		value="<spring:message code="miscellaneousData.delete" />"
		onclick="return confirm('<spring:message code="miscellaneousData.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<jstl:if test="${miscellaneousData.curricula.id!=null}">
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('curricula/hacker/show.do?curriculaId=${miscellaneousData.curricula.id}')"
		value="<spring:message code="miscellaneousData.edit.cancel" />" />
	</jstl:if>
	
	<jstl:if test="${miscellaneousData.curricula.id==null}">
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('curricula/hacker/list.do')"
		value="<spring:message code="miscellaneousData.edit.cancel" />" />
	</jstl:if>

</form:form>