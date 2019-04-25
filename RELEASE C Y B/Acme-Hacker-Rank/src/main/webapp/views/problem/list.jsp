<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="pList" requestURI="${requestURI}" id="row">

	<display:column property="title" titleKey="problem.title"  />
	<display:column property="statement" titleKey="problem.statement"  />
	<display:column property="hint" titleKey="problem.hint"  />
	<display:column property="attachments" titleKey="problem.attachments"  />
	<display:column property="finalMode" titleKey="problem.finalMode"  />
	
	<display:column>
			<a href="problem/company/show.do?problemId=${row.id}">
			<spring:message code="problem.show" />
			</a>
	</display:column>
	
	<display:column>
	<jstl:if test="${row.finalMode==false}">
			<a href="problem/company/edit.do?problemId=${row.id}">
			<spring:message code="problem.edit" />
			</a>
	</jstl:if>
	</display:column>

	
		
</display:table>

<a href="problem/company/create.do">
			<spring:message code="problem.create" />
			</a>