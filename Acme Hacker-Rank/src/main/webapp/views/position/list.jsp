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

<display:column property="ticker" titleKey="position.ticker"/>
<display:column property="title" titleKey="position.title"/>
<display:column property="deadline" titleKey="position.deadline"/>
<display:column property="finalMode" titleKey="position.finalMode"/>
<display:column property="isCancelled" titleKey="position.isCancelled"/>


<display:column>
	<a href="position/company/cancel.do?positionId=${row.id}">
		<spring:message code="position.cancel"/>
	</a>
</display:column>

<display:column>
	<a href="position/company/edit.do?positionId=${row.id}">
		<spring:message code="position.edit"/>
	</a>
</display:column>


<display:column>
	<a href="position/company/display.do?positionId=${row.id}">
		<spring:message code="position.display"/>
	</a>
</display:column>
</display:table>


<a href="position/company/create.do">
<spring:message code="position.create"/>
</a>








