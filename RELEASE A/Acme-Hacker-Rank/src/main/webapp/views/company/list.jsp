
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="companys" id="row" requestURI="${requestURI}" pagesize="5" class ="displaytag">

<display:column property="name" titleKey="company.name"/>
<display:column property="photo" titleKey="company.photo"/>
<display:column property="email" titleKey="company.email"/>
<display:column property="phoneNumber" titleKey="company.phoneNumber"/>
<display:column property="address" titleKey="company.address"/>

<display:column>
	<a href="company/show.do?companyId=${row.id}">
		<spring:message code="company.show"/>
	</a>
</display:column>

</display:table>