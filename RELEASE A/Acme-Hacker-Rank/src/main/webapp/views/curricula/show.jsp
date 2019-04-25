<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('HACKER')">

	<h3 style="color:blue;">
		<spring:message code="curricula.name" />:
	</h3>
	<jstl:out value="${curricula.name}"></jstl:out>
	<!-- Personal Data -->
	<h3 style="color:blue;">
		<spring:message code="personalData.part" />:
	</h3>
	
	<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="personalData" requestURI="${requestURI}" id="row">

	<display:column property="fullName" titleKey="personalData.fullName"  />
	<display:column property="statement" titleKey="personalData.statement"  />
	<display:column property="phoneNumber" titleKey="personalData.phoneNumber"  />
	<display:column property="gitProfile" titleKey="personalData.gitProfile"  />
	<display:column property="linkedInProfile" titleKey="personalData.linkedInProfile"  />
	
	<jstl:if test="${!curricula.isCopy }">
	<display:column>
	<a href="personalData/hacker/edit.do?personalDataId=${row.id }">
	<spring:message code="edit.pesonalData"/></a>

	</display:column>
	</jstl:if>
		
</display:table>

	<jstl:if test="${!curricula.isCopy }">
	<a href="personalData/hacker/create.do?curriculaId=${curricula.id }">
	<spring:message code="create.pesonalData"/></a>
	<br/>
	<br/>
	</jstl:if>
	
	<!-- Education Data -->
	<h3 style="color:blue;">
		<spring:message code="educationData.part" />:
	</h3>
	
	<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="educationData" requestURI="${requestURI}" id="row">

	<display:column property="degree" titleKey="educationData.degree"  />
	<display:column property="institution" titleKey="educationData.institution"  />
	<display:column property="mark" titleKey="educationData.mark"  />
	<display:column property="startMoment" titleKey="educationData.startMoment"  />
	<display:column property="endMoment" titleKey="educationData.endMoment"  />
	
	<jstl:if test="${!curricula.isCopy }">
	<display:column>
	<a href="educationData/hacker/edit.do?educationDataId=${row.id }">
	<spring:message code="edit.educationData"/></a>

	</display:column>
	</jstl:if>
		
</display:table>
	
	<jstl:if test="${!curricula.isCopy }">
	<a href="educationData/hacker/create.do?curriculaId=${curricula.id }">
	<spring:message code="create.educationData"/></a>
	
	<br/>
	</jstl:if>
	
	<!-- Position Data -->
	<h3 style="color:blue;">
		<spring:message code="positionData.part" />:
	</h3>
	
	<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="positionData" requestURI="${requestURI}" id="row">

	<display:column property="title" titleKey="positionData.title"  />
	<display:column property="description" titleKey="positionData.description"  />
	<display:column property="startMoment" titleKey="positionData.startMoment"  />
	<display:column property="endMoment" titleKey="positionData.endMoment"  />
	
	<jstl:if test="${!curricula.isCopy }">
	<display:column>
	<a href="positionData/hacker/edit.do?positionDataId=${row.id }">
	<spring:message code="edit.positionData"/></a>

	</display:column>
	</jstl:if>
		
</display:table>
	
	<jstl:if test="${!curricula.isCopy }">
	<a href="positionData/hacker/create.do?curriculaId=${curricula.id }">
	<spring:message code="create.positionData"/></a>
	<br/>
	</jstl:if>
	
	<h3 style="color:blue;">
		<spring:message code="miscellaneousData.part" />:
	</h3>
	
	<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="miscellaneousData" requestURI="${requestURI}" id="row">

	<display:column property="freeText" titleKey="miscellaneousData.freeText"  />
	<display:column property="attachments" titleKey="miscellaneousData.attachments"  />
	
	<jstl:if test="${!curricula.isCopy }">
	<display:column>
	<a href="miscellaneousData/hacker/edit.do?miscellaneousDataId=${row.id }">
	<spring:message code="edit.miscellaneousData"/></a>

	</display:column>
	</jstl:if>
		
</display:table>
	
	<jstl:if test="${!curricula.isCopy }">
	<a href="miscellaneousData/hacker/create.do?curriculaId=${curricula.id }">
	<spring:message code="create.miscellaneousData"/></a>
	</jstl:if>
	
	<br/>
	<br/>

	
	<form:form action="curricula/hacker/edit.do" modelAttribute="curricula">
	<form:hidden path="id"/>
	<jstl:if test="${!curricula.isCopy }">
	<input type="submit" name="delete"
			value="<spring:message code="curricula.delete" />"
			onclick="return confirm('<spring:message code="curricula.confirm.delete" />')" />&nbsp;
	</jstl:if>
	</form:form>
	
	
	<input type="button" name="back" onclick="javascript: window.location.replace('curricula/hacker/list.do')"
		value="<spring:message code="curricula.back" />" />
	</security:authorize>