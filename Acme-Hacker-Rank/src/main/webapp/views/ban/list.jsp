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

<security:authorize access="hasRole('ADMIN')">
<h2><spring:message code="actors.notBanned"/></h2>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="lH" requestURI="${requestURI}" id="row">

	<display:column property="name" titleKey="actor.name"  />
	<display:column property="surname" titleKey="actor.surname"  />
	<display:column property="email" titleKey="actor.email"  />
	<display:column property="phoneNumber" titleKey="actor.phoneNumber"  />
	<jstl:if test="${row.computed==true }">
	<display:column property="flagSpam" titleKey="actor.flagSpam"  />
	</jstl:if>
	<jstl:if test="${row.computed==false }">
	<display:column titleKey="actor.flagSpam" value="N/A"  />
	</jstl:if>
	<display:column property="isBanned" titleKey="actor.isBanned"  />
	
<%-- 	<display:column>
			<a href="ban/administrator/show.do?actorId=${row.id}">
			<spring:message code="actor.show" />
			</a>
	</display:column> --%>
	
	<display:column>
	<jstl:if test="${row.flagSpam }">
	<a href="ban/administrator/ban.do?actorId=${row.id}">
			<spring:message code="actor.ban" />
			</a>
	</jstl:if>
	</display:column>

	
		
</display:table>

<h2><spring:message code="actors.banned"/></h2>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="lH2" requestURI="${requestURI}" id="row2">

	<display:column property="name" titleKey="actor.name"  />
	<display:column property="surname" titleKey="actor.surname"  />
	<display:column property="email" titleKey="actor.email"  />
	<display:column property="phoneNumber" titleKey="actor.phoneNumber"  />
	<display:column property="flagSpam" titleKey="actor.flagSpam"  />
	<display:column property="isBanned" titleKey="actor.isBanned"  />
	
<%-- 	<display:column>
			<a href="ban/administrator/show.do?actorId=${row.id}">
			<spring:message code="actor.show" />
			</a>
	</display:column> --%>
	
	<display:column>
	<a href="ban/administrator/unban.do?actorId=${row2.id}">
			<spring:message code="actor.unban" />
			</a>
	</display:column>

	
		
</display:table>

<a href="ban/administrator/spamProcess.do">
			<spring:message code="admin.spamProcess" />
			</a>
</security:authorize>