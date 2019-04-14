<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="personalData/hacker/edit.do"
	modelAttribute="personalData">
	<form:hidden path="id" />
	<jstl:if test="${personalData.id==0 }">
	<form:hidden path="version" />

	<form:hidden path="curricula" />
	</jstl:if>

	<fieldset>
		<legend align="left">
			<spring:message code="edit.personalData" />
		</legend>

		<form:label path="fullName">
			<spring:message code="personalData.fullName" />* :
		</form:label>
		<form:input path="fullName" />
		<form:errors cssClass="error" path="fullName" />
		<br /> <br />
		
		<form:label path="statement">
			<spring:message code="personalData.statement" />* :
		</form:label>
		<form:input path="statement" />
		<form:errors cssClass="error" path="statement" />
		<br /> <br />

		<form:label path="phoneNumber">
			<spring:message code="personalData.phoneNumber" />* :
		</form:label>
		<form:input path="phoneNumber" onchange="check(this)" pattern="^(\d|\(|\)| |\+)+$"/>
		<form:errors cssClass="error" path="phoneNumber" />
		
		<script language='javascript' type='text/javascript'>
		
			var re = /^\+\d{1,3} \(\d{1,3}\) \d{4,}$/;
			var re2 = /^\+\d{1,3} \d{4,}$/;
			var re3 = /^\d{4,}$/;
		
		    function check(input) {
		    	var OK = re.exec(input.value);
		    	var OK2 = re2.exec(input.value);
		    	var OK3 = re3.exec(input.value);
		        if (!(OK || OK2 || OK3)) {
		            alert("<spring:message code="phoneNumber.confirm" />" );
		        }
		    }
		</script>		
		<br /> <br />
		
		<form:label path="gitProfile">
			<spring:message code="personalData.gitProfile" />* :
		</form:label>
		<form:input path="gitProfile" />
		<form:errors cssClass="error" path="gitProfile" />
		<br /> <br />
		
		<form:label path="linkedInProfile">
			<spring:message code="personalData.linkedInProfile" />* :
		</form:label>
		<form:input path="linkedInProfile" />
		<form:errors cssClass="error" path="linkedInProfile" />
		<br /> <br />
	</fieldset>
	<br />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="personalData.save" />" />&nbsp;
		<jstl:if test="${personalData.id!=0 }">
	<input type="submit" name="delete"
		value="<spring:message code="personalData.delete" />"
		onclick="return confirm('<spring:message code="personalData.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<jstl:if test="${personalData.curricula.id!=null }">
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('curricula/hacker/show.do?curriculaId=${personalData.curricula.id}')"
		value="<spring:message code="personalData.edit.cancel" />" />
	</jstl:if>
	<jstl:if test="${personalData.curricula.id==null }">
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('curricula/hacker/list.do')"
		value="<spring:message code="personalData.edit.cancel" />" />
	</jstl:if>
</form:form>