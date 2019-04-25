<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="problem/company/edit.do"
	modelAttribute="problem">
	<form:hidden path="id" />
	
	<jstl:if test="${problem.id==0}">
	<form:hidden path="version" />
	<form:hidden path="company" />
	</jstl:if>
	
	<fieldset>
		<legend align="left">
			<spring:message code="edit.problem" />
		</legend>

		<form:label path="title">
			<spring:message code="problem.title" />* :
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br /> <br />

		<form:label path="statement">
			<spring:message code="problem.statement" />* :
		</form:label>
		<form:input path="statement" />
		<form:errors cssClass="error" path="statement" />
		<br /> <br />

		<form:label path="hint">
			<spring:message code="problem.hint" /> :
		</form:label>
		<form:input path="hint" />
		<form:errors cssClass="error" path="hint" />
		<br /> <br />

		<form:label path="attachments">
			<spring:message code="problem.attachments" /> :
		</form:label>
		<form:textarea path="attachments" />
		<form:errors cssClass="error" path="attachments" />
		<br /> <br />

		<form:label path="finalMode">
			<spring:message code="problem.finalMode" />*:
		</form:label>
		<form:select path="finalMode" >
		<form:option value="true"><spring:message code="problem.finalMode.true"/></form:option>
		<form:option value="false"><spring:message code="problem.finalMode.false"/></form:option>
		</form:select>
		<form:errors cssClass="error" path="finalMode" />
		<br /> <br />

	</fieldset>
	<br />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="problem.save" />" />&nbsp;
		<jstl:if test="${problem.id!=0}">
	<input type="submit" name="delete"
		value="<spring:message code="problem.delete" />"
		onclick="return confirm('<spring:message code="problem.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('problem/company/list.do')"
		value="<spring:message code="problem.edit.cancel" />" />

</form:form>