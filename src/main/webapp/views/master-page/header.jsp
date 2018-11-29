<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a href=""><img src="images/acmeschool.jpg" alt="Acme-School., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="isAnonymous()">
			<!-- Log in -->
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>

			<!-- Registrarse (Botón de Sign Up) -->
			<li><a><spring:message code="master.page.register" /> </a>
				<ul>
					<li class="isParent"></li>
					<li><a href="actor/createParent.do"><spring:message
								code="master.page.signUpParent" /></a></li>
					<li><a href="actor/createAgent.do"><spring:message
								code="master.page.signUpAgent" /></a></li>
				</ul></li>
		</security:authorize>


		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="actor/myDisplay.do"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="folder/actor/list.do"><spring:message
								code="master.page.message" /> </a></li>

					<!-- Padres -->
					<security:authorize access="hasRole('PARENT')">
	
					
					<li><a href="parentsGroup/parent/mylist.do"><spring:message
								code="master.page.parent.myparentsgroups" /></a></li>
					
	
	
					</security:authorize>

					<!-- Director -->
					<security:authorize access="hasRole('DIRECTOR')">
						<li><a href="actor/createTeacher.do"><spring:message
									code="master.page.director.signUpTeacher" /></a></li>
					</security:authorize>

					<!-- Admin -->
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="actor/createDirector.do"><spring:message
									code="master.page.admin.signUpDirector" /></a></li>
						<li><a><spring:message code="master.page.taboo" /> </a>
							<ul>
								<li class="isParent"></li>
								<li><a href="#"><spring:message
											code="master.page.administrator.free" /></a></li>
								<li><a href="#"><spring:message
											code="master.page.administrator.free" /></a></li>
								<li><a href="#"><spring:message
											code="master.page.administrator.free" /></a></li>
								<li><a href="#"><spring:message
											code="master.page.administrator.free" /></a></li>
							</ul></li>
						<li><a href="global/administrator/edit.do"><spring:message
									code="master.page.admin.globalParams" /></a></li>
						<li><a href="dashboard/administrator/display.do"><spring:message
									code="master.page.administrator.dashboard" /></a></li>
					</security:authorize>

					<!-- Agentes -->
					<security:authorize access="hasRole('AGENT')">
						<li><a href="advertisement/agent/list.do"><spring:message
									code="master.page.agent.advertisements" /></a></li>
					</security:authorize>

					<!-- Desloguearse -->
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>

		<!-- Para todo el mundo -->
		<security:authorize access="permitAll">
			<li><a><spring:message code="master.page.general.list" /></a>
				<ul>
					<li class="isParent"></li>
					<li><a href="parentsGroup/general/list.do"> <spring:message
								code="master.page.general.list.parentsGroup" /></a>
					<li>
					<li><a href="school/list.do"> <spring:message
								code="master.page.general.list.school" /></a>
					<li>
				</ul></li>
			<li><a href="searcher/display.do"><spring:message
						code="master.page.general.searcher" /></a></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

