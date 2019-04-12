<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${customisation.bannerUrl}" alt="${customisation.systemName}" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		
		
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="dashboard/administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="customisation/administrator/edit.do"><spring:message code="master.page.administrator.customisation" /></a></li>
					
					<li><a href="administrator/administrator/create.do"><spring:message code="master.page.administrator.create" /></a></li>
					<li><a href="administrator/administrator/edit.do"><spring:message code="master.page.administrator.edit" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message code="master.page.customer.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="company/company/edit.do"><spring:message code="master.page.edit.company" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HACKER')">
			<li><a class="fNiv"><spring:message	code="master.page.hacker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="hacker/hacker/edit.do"><spring:message code="master.page.edit.hacker" /></a></li>
				</ul>
			</li>
		</security:authorize>

		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/action-1.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/action-2.do"><spring:message code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/action-3.do"><spring:message code="master.page.profile.action.3" /></a></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="permitAll">
			<li><a class="fNiv"><spring:message	code="master.page.actions" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="search/search.do"><spring:message code="master.page.search" /></a></li>			
				</ul>
		<security:authorize access="isAnonymous()">
			<li>
			<a class="fNiv" href="company/register.do"><spring:message code="master.page.register.company" /></a>
			</li>
			<li>
			<a class="fNiv" href="hacker/register.do"><spring:message code="master.page.register.hacker" /></a>
			</li>
			<li>
			<a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a>
			</li>
		</security:authorize>
	</ul>
</div>


<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

