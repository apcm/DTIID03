<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<fieldset>
		<legend align="left">
			<spring:message code="application.problem.title" />
		</legend>
		<jstl:out value="${problem.title }" /> <br/>
		<jstl:out value="${problem.statement }" /> <br/>
					<spring:message code="application.problem.hint" />
		
		<jstl:out value="${problem.hint }" />



		</fieldset>
<br/>

<form:form action="application/hacker/solve.do" 
modelAttribute="application">
<form:hidden path="id" />

<fieldset>
		<legend align="left">
			<spring:message code="application.answerExplanation" />
		</legend>

		<form:label path="answerExplanation">
			<spring:message code="application.answerExplanation" />* :
		</form:label>
		<form:textarea path="answerExplanation" />
		<form:errors cssClass="error" path="answerExplanation" />
		<br />
		<form:label path="Link">
			<spring:message code="application.answerLink" />* :
		</form:label>
		<form:input path="Link" />
		<form:errors cssClass="error" path="Link" />
		<br />
</fieldset>	 

	<input type="submit" name="save" value="<spring:message code="application.edit.save" />" />
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('application/hacker/list.do')"
		value="<spring:message code="application.cancel" />" />	
		
		
</form:form>