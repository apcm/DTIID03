<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>





<form:form modelAttribute="application" action="application/company/edit.do">
		
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="moment"/>
		<form:hidden path="answerExplanation"/>
		<form:hidden path="link"/>
		<form:hidden path="position"/>
		<form:hidden path="hacker"/>
		<form:hidden path="problem"/>

		
		
		<form:label path="status">
		<spring:message code="application.status" />:
	    </form:label>
	    <form:select path="status">

		<form:option value="ACCEPTED"></form:option>
		<form:option value="REJECTED"></form:option>
		</form:select>
		
		
			
	<input type ="submit" name="save" value="<spring:message code="application.save"/>" />

	<input type="button" name="cancel" value="<spring:message code="application.cancel" />" onclick="javascript:relativeRedir('application/company/list.do');" />

</form:form>





