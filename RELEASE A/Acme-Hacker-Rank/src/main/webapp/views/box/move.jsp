<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


	<display:table pagesize = "5" class="displaytag" name="messageBoxes" requestURI="${requestURI}" id="row">

	<spring:message code="messageBox.name" var= "nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />

	
		<display:column>
				<a href="boxes/copyToBox.do?messageBoxId=${row.id}&messageId=${messageId}">
		<spring:message code="messageBox.copyToBox"/>
		</a>
		</display:column>
	
	</display:table>
	
	
	