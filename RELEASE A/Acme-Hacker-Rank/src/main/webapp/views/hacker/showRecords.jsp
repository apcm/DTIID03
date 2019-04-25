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
	name="legalRecords" requestURI="${requestURI}" id="row">

	<display:column property="title" titleKey="brotherhood.title"  />
	<display:column property="description" titleKey="brotherhood.description"  />
	<display:column property="legalName" titleKey="brotherhood.legalName"  />
	<display:column property="applicableLaws" titleKey="brotherhood.applicableLaws"  />
	<display:column property="vatNumber" titleKey="brotherhood.vatNumber"  />
	<display:column>
		<security:authorize access="hasRole('BROTHERHOOD')">
			<a href="legalrecord/brotherhood/edit.do?legalRecordId=${row.id}">
			<spring:message code="brotherhood.edit" />
			</a>
		</security:authorize>
	</display:column>
</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">
<input type="submit" name="create"
	value="<spring:message code="brotherhood.legalRecord.create" />"
	onclick="javascript: relativeRedir('legalrecord/brotherhood/create.do');" />
</security:authorize>

<br/><br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="periodRecords" requestURI="${requestURI}" id="row">

	<display:column property="title" titleKey="brotherhood.title"  />
	<display:column property="description" titleKey="brotherhood.description"  />
	<display:column property="startYear" titleKey="brotherhood.startYear"  />
	<display:column property="endYear" titleKey="brotherhood.endYear"  />
	<display:column property="photos" titleKey="brotherhood.photos"  />

	<display:column>
		<security:authorize access="hasRole('BROTHERHOOD')">
			<a href="periodrecord/brotherhood/edit.do?periodRecordId=${row.id}">
			<spring:message code="brotherhood.edit" />
			</a>
		</security:authorize>
	</display:column>

</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">
<input type="submit" name="create"
	value="<spring:message code="brotherhood.periodRecord.create" />"
	onclick="javascript: relativeRedir('periodrecord/brotherhood/create.do');" />
</security:authorize>
<br/><br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="inceptionRecord" requestURI="${requestURI}" id="row">

	<display:column property="title" titleKey="brotherhood.title"  />
	<display:column property="description" titleKey="brotherhood.description"  />
	<display:column property="photos" titleKey="brotherhood.photos"  />

	<display:column>
		<security:authorize access="hasRole('BROTHERHOOD')">
			<a href="inceptionrecord/brotherhood/edit.do?inceptionRecordId=${row.id}">
			<spring:message code="brotherhood.edit" />
			</a>
		</security:authorize>
	</display:column>
</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">
<input type="submit" name="create"
	value="<spring:message code="brotherhood.inceptionRecord.create" />"
	onclick="javascript: relativeRedir('inceptionrecord/brotherhood/create.do');" />
</security:authorize>

<br/><br/>
	
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="linkRecords" requestURI="${requestURI}" id="row">

	<display:column property="title" titleKey="brotherhood.title"  />
	<display:column property="description" titleKey="brotherhood.description"  />
	<display:column property="link" titleKey="brotherhood.link"  />

	<display:column>
		<security:authorize access="hasRole('BROTHERHOOD')">
			<a href="linkrecord/brotherhood/edit.do?linkRecordId=${row.id}">
			<spring:message code="brotherhood.edit" />
			</a>
		</security:authorize>
	</display:column>

</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">
<input type="submit" name="create"
	value="<spring:message code="brotherhood.linkRecord.create" />"
	onclick="javascript: relativeRedir('linkrecord/brotherhood/create.do');" />
</security:authorize>


<br/><br/>
