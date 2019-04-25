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
	name="applications" requestURI="${requestURI}" id="row">

	<display:column property="moment" titleKey="application.moment"  />
	<display:column property="position.title" titleKey="position.title"  />
	<display:column property="status" titleKey="application.status"  />
	<display:column property="position.ticker" titleKey="position.ticker"  />
	

	<display:column>
		<jstl:if test="${row.status == 'PENDING'}">
			<a href="application/hacker/solve.do?applicationId=${row.id }">
			<spring:message code="application.solve" />
			</a>
				</jstl:if>		
	</display:column>
	
		<display:column>
			<a href="application/hacker/show.do?applicationId=${row.id }">
			<spring:message code="application.show" />
			</a>
	</display:column>

</display:table>