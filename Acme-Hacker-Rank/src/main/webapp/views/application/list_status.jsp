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



<display:table name="applicationsP" id="row" requestURI="${requestURI}" pagesize="5" class ="displaytag">

<display:column property="moment" titleKey="application.moment"/>
<display:column property="status" titleKey="application.status"/>

<display:column>
	<a href="application/company/edit.do?applicationId=${row.id}">
		<spring:message code="application.edit"/>
	</a>
</display:column>


<display:column>
	<a href="application/company/display.do?applicationId=${row.id}">
		<spring:message code="application.display"/>
	</a>
</display:column>
</display:table>



<display:table name="applicationsA" id="row" requestURI="${requestURI}" pagesize="5" class ="displaytag">

<display:column property="moment" titleKey="application.moment"/>
<display:column property="status" titleKey="application.status"/>

<display:column>
	<a href="application/company/edit.do?applicationId=${row.id}">
		<spring:message code="application.edit"/>
	</a>
</display:column>


<display:column>
	<a href="application/company/display.do?applicationId=${row.id}">
		<spring:message code="application.display"/>
	</a>
</display:column>
</display:table>


<display:table name="applicationsR" id="row" requestURI="${requestURI}" pagesize="5" class ="displaytag">

<display:column property="moment" titleKey="application.moment"/>
<display:column property="status" titleKey="application.status"/>

<display:column>
	<a href="application/company/edit.do?applicationId=${row.id}">
		<spring:message code="application.edit"/>
	</a>
</display:column>


<display:column>
	<a href="application/company/display.do?applicationId=${row.id}">
		<spring:message code="application.display"/>
	</a>
</display:column>
</display:table>

