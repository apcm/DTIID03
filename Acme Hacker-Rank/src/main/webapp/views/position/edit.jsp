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





<form:form modelAttribute="position" action="position/company/edit.do">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="ticker"/>
		<form:hidden path="company"/>
		<form:hidden path="problems"/>
		<form:hidden path="isCancelled"/>

		<form:label path="title">
			<spring:message code="position.title" />:
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />	
		
		<form:label path="description">
			<spring:message code="position.description" />:
		</form:label>
		<form:input path="description" />
		<form:errors cssClass="error" path="description" />
		<br />	

		<form:label path="deadline">
			<spring:message code="position.deadline" />:
		</form:label>
		<form:input path="deadline" />
		<form:errors cssClass="error" path="deadline" />
		<br />
		
		<form:label path="skills">
			<spring:message code="position.skills" />:
		</form:label>
		<form:input path="skills" />
		<form:errors cssClass="error" path="skills" />
		<br />
		
		<form:label path="technologies">
			<spring:message code="position.technologies" />:
		</form:label>
		<form:input path="technologies" />
		<form:errors cssClass="error" path="technologies" />
		<br />
		
		<form:label path="profile">
			<spring:message code="position.profile" />:
		</form:label>
		<form:input path="profile" />
		<form:errors cssClass="error" path="profile" />
		<br />
		
		
		<form:label path="salary">
			<spring:message code="position.salary" />:
		</form:label>
		<form:input path="salary" />
		<form:errors cssClass="error" path="salary" />
		<br />
		
		<form:label path="finalMode">
		<spring:message code="position.finalMode" />:
	    </form:label>
	    <form:select path="finalMode">
		<form:option value="true"></form:option>
		<form:option value="false"></form:option>
		</form:select>
		
		
			
	<input type ="submit" name="save" value="<spring:message code="position.save"/>" />

	<input type="button" name="cancel" value="<spring:message code="position.cancel" />" onclick="javascript:relativeRedir('position/company/list.do');" />


</form:form>





