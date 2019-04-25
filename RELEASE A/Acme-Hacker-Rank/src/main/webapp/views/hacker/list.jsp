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
	name="brotherhoods" requestURI="${requestURI}" id="row">

	<display:column property="title" titleKey="brotherhood.title"  />
	<display:column property="area.name" titleKey="brotherhood.area"  />
	<display:column property="address" titleKey="brotherhood.address"  />
	<display:column property="stablishmentDate" titleKey="brotherhood.stablishment.date.d"  />
	
	<display:column>
			<a href="parade/list.do?brotherhoodId=${row.id}">
			<spring:message code="parade.list" />
			</a>
	</display:column>
	
	<display:column>
			<a href="member/list.do?brotherhoodId=${row.id}">
			<spring:message code="member.list" />
			</a>
	</display:column>
	
	<display:column>
			<a href="float/list.do?brotherhoodId=${row.id}">
			<spring:message code="float.list" />
			</a>
	</display:column>
	
	<display:column>
			<a href="brotherhood/showRecords.do?brotherhoodId=${row.id}">
			<spring:message code="history.list" />
			</a>
	</display:column>
	
		
</display:table>