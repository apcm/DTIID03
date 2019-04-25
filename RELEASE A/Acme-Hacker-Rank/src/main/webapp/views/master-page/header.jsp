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
					<li><a href="administrator/administrator/create.do"><spring:message code="master.page.administrator.create" /></a></li>
					<li><a href="administrator/administrator/edit.do"><spring:message code="master.page.administrator.edit" /></a></li>
					<li><a href="dashboard/administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="customisation/administrator/edit.do"><spring:message code="master.page.administrator.customisation" /></a></li>
					<li><a href="ban/administrator/list.do"><spring:message code="master.page.administrator.ban" /></a></li>

				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="company/company/edit.do"><spring:message code="master.page.edit.company" /></a></li>
					<li><a href="problem/company/list.do"><spring:message code="master.page.problem" /></a></li>	
					<li><a href="position/company/list.do"><spring:message code="master.page.list.position" /></a></li>
					<li><a href="application/company/list.do"><spring:message code="master.page.list.application" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HACKER')">
			<li><a class="fNiv"><spring:message	code="master.page.hacker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="hacker/hacker/edit.do"><spring:message code="master.page.edit.hacker" /></a></li>
					<li><a href="finder/hacker/show.do"><spring:message code="master.page.finder" /></a></li>	
					<li><a href="curricula/hacker/list.do"><spring:message code="master.page.curricula.list"/></a></li>
					<li><a href="position/hacker/list.do"><spring:message code="master.page.hacker.list"/></a></li>
					<li><a href="application/hacker/list.do"><spring:message code="master.page.hacker.applications"/></a></li>
					
				</ul>
			</li>
		</security:authorize>

		
		<security:authorize access="isAuthenticated()">
		<li>
				<a class="fNiv"> 
					<spring:message code="master.page.socialprofile" /> 
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="socialprofile/list.do"><spring:message code="master.page.socialprofile.list" /> </a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.messages.header" /> 
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="boxes/list.do"><spring:message code="master.page.messages.link" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="position/list.do"><spring:message code="master.page.position" /></a></li>
			<li><a class="fNiv" href="company/list.do"><spring:message code="master.page.company" /></a></li>
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
		<security:authorize access="permitAll">
			<li><a class="fNiv"><spring:message	code="master.page.actions" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="search/search.do"><spring:message code="master.page.search" /></a></li>			
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>


<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

