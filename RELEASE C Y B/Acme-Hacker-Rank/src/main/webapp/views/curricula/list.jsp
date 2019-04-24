<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="cList" requestURI="${requestURI}" id="row">

	<display:column property="name" titleKey="curricula.name"  />
	
	<display:column>
			<a href="curricula/hacker/show.do?curriculaId=${row.id}">
			<spring:message code="curricula.show" />
			</a>
	</display:column>
	
	<display:column>

	<jstl:if test="${!row.isCopy }">

	<a href="curricula/hacker/edit.do?curriculaId=${row.id}">
			<spring:message code="curricula.edit.name" />
			</a>
	</jstl:if>
	</display:column>

	
		
</display:table>

<a href="curricula/hacker/create.do">
			<spring:message code="curricula.create" />
			</a>