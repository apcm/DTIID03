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



<display:table name="positions" id="row" requestURI="${requestURI}" pagesize="5" class ="displaytag">
<security:authorize access="hasRole('COMPANY')">


<display:column property="deadline" titleKey="position.deadline"/>
<display:column property="isCancelled" titleKey="position.isCancelled"/>

</security:authorize>
<display:column property="title" titleKey="position.title"/>
<display:column property="finalMode" titleKey="position.finalMode"/>
<display:column property="deadline" titleKey="position.deadline"/>
<display:column property="ticker" titleKey="position.ticker"/>
<security:authorize access="hasRole('COMPANY')">
<display:column titleKey="position.problems">
	<jstl:forEach var = "result" items="${row.problems}">
		<jstl:out value="${result.title}"/>
		<br/>
	</jstl:forEach>
</display:column>
</security:authorize>


<security:authorize access="hasRole('COMPANY')">

<display:column>
	<a href="position/company/display.do?positionId=${row.id}">
		<spring:message code="position.display"/>
	</a>
</display:column>

<display:column>
	<jstl:if test="${row.isCancelled==false && row.finalMode==true}">
		<a href="position/company/cancel.do?positionId=${row.id}">
			<spring:message code="position.cancel"/>
		</a>
	</jstl:if>
</display:column>


	<display:column>
		<jstl:if test="${row.finalMode==false}">
		<a href="position/company/edit.do?positionId=${row.id}">
			<spring:message code="position.edit"/>
		</a>
		</jstl:if>
	</display:column>



</security:authorize>
<security:authorize access="hasRole('HACKER')">
 		<display:column>

			<a href="application/hacker/create.do?positionId=${row.id}">
			<spring:message code="position.application.create" />
			</a>
	</display:column>
 	
 	</security:authorize>
 	
</display:table>

<security:authorize access="hasRole('COMPANY')">

<a href="position/company/create.do">
<spring:message code="position.create"/>
</a>
</security:authorize>

	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="application.creation.error"/>
		</div>
	</jstl:if>







