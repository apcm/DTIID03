<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


	<form:form modelAttribute="customisation" action="customisation/administrator/edit.do" >
		<security:authorize access="hasRole('ADMIN')">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:label path="systemName">
			<spring:message code="customisation.systemName" />:
		</form:label>
		<form:input path="systemName" />
		<form:errors cssClass="error" path="systemName" />
		<br />	
		
		<form:label path="bannerUrl">
			<spring:message code="customisation.bannerUrl" />:
		</form:label>
		<form:input path="bannerUrl" />
		<form:errors cssClass="error" path="bannerUrl" />
		<br />	
		<form:label path="finderDuration">
			<spring:message code="customisation.finderDuration" />:
		</form:label>
		<form:input path="finderDuration" />
		<form:errors cssClass="error" path="finderDuration" />
		<br />
		
		<form:label path="resultsNumber">
			<spring:message code="customisation.resultsNumber" />:
		</form:label>
		<form:input path="resultsNumber" />
		<form:errors cssClass="error" path="resultsNumber" />
		<br />
		
		<form:label path="welcomeMessageEng">
			<spring:message code="customisation.welcomeMessageEng" />:
		</form:label>
		<form:textarea path="welcomeMessageEng" />
		<form:errors cssClass="error" path="welcomeMessageEng" />
		<br />	
		<form:label path="welcomeMessageEsp">
			<spring:message code="customisation.welcomeMessageEsp" />:
		</form:label>
		<form:textarea path="welcomeMessageEsp" />
		<form:errors cssClass="error" path="welcomeMessageEsp" />
		<br />	
		
		<form:label path="spamWords">
			<spring:message code="customisation.spamWords" />:
		</form:label>
		<form:textarea path="spamWords" />
		<form:errors cssClass="error" path="spamWords" />
		<br />	
		
		<form:label path="phoneNumberCode">
			<spring:message code="customisation.phoneNumberCode" />:
		</form:label>
		<form:input path="phoneNumberCode" />
		<form:errors cssClass="error" path="phoneNumberCode" />
		<br />			
		
		<input type="submit" name="save" value = "<spring:message code ="customisation.save" /> " />
		
		<input type="button" name="cancel" value="<spring:message code ="customisation.cancel" />" onclick="javascript:relativeRedir('welcome/index.do');" />
	
	</security:authorize>
	
	</form:form>