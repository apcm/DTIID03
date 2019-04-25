 
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


	<form:form action="boxes/edit.do" modelAttribute="messageBox">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="messages"/>
		<form:hidden path="predefined"/>
		<jstl:if test="${messageBox.id == 0 }">		
		<form:hidden path="descendants" />	
		</jstl:if>
		<jstl:if test="${messageBox.predefined == true }">
			<form:hidden path="name"/>
		</jstl:if>	
		
		<jstl:if test="${messageBox.predefined == false }">
		<form:label path="name">
			<spring:message code="messageBox.name" />:
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br />
				</jstl:if>	
		

		
		<jstl:if test="${messageBox.id != 0 }"> 
		<spring:message code="messageBox.descendants" />:
		<form:select multiple="true" id="descendants" path="descendants">
		    <form:options items="${allBoxes}" itemLabel="name" itemValue="id" />
		    </form:select>
		   <br />
		</jstl:if>
		
		<jstl:if test="${messageBox.predefined == true }"></jstl:if>
		<input type="submit" name = "save" value = "<spring:message code ="messageBox.save" /> " />
		
		<jstl:if test="${messageBox.id != 0 }"> 
		<input type="submit" name="delete" value = "<spring:message code ="messageBox.delete" /> " />
		</jstl:if>
		
		<spring:message code ="messageBox.cancel" var="cancel" />
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('boxes/list.do');" />
		
		
		
	
	
	</form:form>
	
	