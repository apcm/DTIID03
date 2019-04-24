<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


	<form:form action="messages/delete.do" modelAttribute="mesInformation">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="moment"/>
		<form:hidden path="sender"/>
		<form:hidden path="recipients"/>

		<spring:message code="mes.priority" />:
		<form:select id="priority" path="priority" disabled="true">
		<form:options items="${priority}" itemLabel="priority" itemValue="id"/>
			<form:option value="LOW" label="LOW"/>
			<form:option value="NEUTRAL" label="NEUTRAL"/>
			<form:option value="HIGH" label="HIGH"/>
		</form:select>
		
		<br/>
		
		<form:label path="subject">
			<spring:message code="mes.subject" />:
		</form:label>
		<form:input path="subject" readonly="true"/>
		<form:errors cssClass="error" path="subject" />
		<br />	
		
		<form:label path="body">
			<spring:message code="mes.body" />:
		</form:label>
		<form:textarea path="body" readonly="true" />
		<form:errors cssClass="error" path="body" />
		<br />
		
		<form:label path="tag">
			<spring:message code="mes.tags" />:
		</form:label>
		<form:input path="tag" readonly="true" />
		<form:errors cssClass="error" path="tag" />
		<br />
		
		<input type="submit" name = "delete" value = "<spring:message code="mes.delete"/> " />
		
		<spring:message code ="mes.cancel" var="cancel" />
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('boxes/list.do');" />
		
		
		
	
	
	</form:form>
	