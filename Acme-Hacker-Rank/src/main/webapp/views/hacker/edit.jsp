<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="hacker/hacker/edit.do"
	modelAttribute="hacker">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount.authorities" />

	<fieldset>
		<legend align="left">
			<spring:message code="hacker.edit.contact" />
		</legend>

		<form:label path="name">
			<spring:message code="hacker.edit.label.name" />* :
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br /> <br />

		<form:label path="surname">
			<spring:message code="hacker.edit.label.surname" />* :
		</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
		<br /> <br />

		<form:label path="address">
			<spring:message code="hacker.edit.label.address" /> :
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
		<br /> <br />

		<form:label path="vat">
			<spring:message code="hacker.edit.label.vat" />* :
		</form:label>
		<form:input path="vat" />
		<form:errors cssClass="error" path="vat" />
		<br /> <br />

		<form:label path="email">
			<spring:message code="hacker.edit.label.email" />*:
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		<br /> <br />

		<form:label path="phoneNumber">
			<spring:message code="hacker.edit.label.phoneNumber" />:
		</form:label>
		<form:input path="phoneNumber" onchange="check(this)"
			pattern="^(\d|\(|\)| |\+)+$" />
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
					alert("<spring:message code="hacker.confirm" />");
				}
			}
		</script>
		<br /> <br />

		<form:label path="photo">
			<spring:message code="hacker.edit.label.photo" />:
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		<br /> <br />
	</fieldset>
	<br />

	<fieldset>
		<legend align="left">
			<spring:message code="hacker.creditCard" />
		</legend>
		
		<form:label path="holderName">
			<spring:message code="hacker.holderName" />* :
		</form:label>
		<form:input path="holderName" />
		<form:errors cssClass="error" path="holderName" />
		<br />
		
		<form:label path="makeName">
			<spring:message code="hacker.makeName" />* :
		</form:label>
		<form:input path="makeName" />
		<form:errors cssClass="error" path="makeName" />
		<br />
				
		<form:label path="number">
			<spring:message code="hacker.number" />* :
		</form:label>
		<form:input path="number" />
		<form:errors cssClass="error" path="number" />
		<br />
		
		<form:label path="expirationYear">
			<spring:message code="hacker.expirationYear" />* :
		</form:label>
		<form:input path="expirationYear" />
		<form:errors cssClass="error" path="expirationYear" />
		<br />	
					
		<form:label path="expirationMonth">
			<spring:message code="hacker.expirationMonth" />* :
		</form:label>
		<form:input path="expirationMonth" />
		<form:errors cssClass="error" path="expirationMonth" />
		<br />	
				
		<form:label path="cvv">
			<spring:message code="hacker.cvv" />* :
		</form:label>
		<form:input path="cvv" />
		<form:errors cssClass="error" path="cvv" />
		<br />			
	</fieldset>

	<br />
	<spring:message code="hacker.leave.explanation"
		var="leaveExplanation" />
	<jstl:out value="${leaveExplanation}" />

	<a href="hacker/hacker/show.do"> Link </a>

	<br />
	<input type="submit" name="save"
		value="<spring:message code="hacker.edit.save.save" />" />&nbsp;
	<input type="submit" name="leave"
		value="<spring:message code="hacker.edit.leave" />"
		onclick="return confirm('<spring:message code="hacker.confirm.delete" />')" />&nbsp;
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('welcome/index.do')"
		value="<spring:message code="hacker.edit.cancel" />" />

</form:form>