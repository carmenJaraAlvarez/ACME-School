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
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">

	<div style="float: left;">
		<a href=""><img src="images/acmeschool.jpg"
			alt="Acme-School., Inc." /></a>
	</div>

		<jstl:if test="${bannerAnnouncement != null }">
		<a href="${webAnnouncement }" title="${titleAnnouncement}">
		<img src="${bannerAnnouncement}"  height="120px"  style="max-width:500px !important;width:auto !important;float: right;height: 120px !important;"/></a>
	</jstl:if>

	<div style="float: right;">
		<a href="mailto:?subject=Acme-School&body=https://www.acme.com:8443/"><img
			src="data:image/png;base64,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"
			width="33px" height="33px" /></a> 
				<a
			href="http://twitter.com/home?status=Visita%20esta%20aplicaci�n%20https://localhost:8443/Acme-School/"><img
			src="http://www.socialflow.com/wp-content/uploads/2016/04/twitter1.png"
			width="30px" height="30px" /></a> 
			
			
			<a target="_blank"
			href="http://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Fwww.acme.com%2F&amp;src=sdkpreparse"><img
			src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAaVBMVEX///9HWZM9UY81S4z09fgxSIqkq8Xd4OpAU5CHkbU7T46Zob5EVpG+w9ZBVJBjcKB/ibBOYJjn6fHw8fawt85ebZ/FyttreaZUZJrU1+Tl5+9ZaZ1icKHN0eB0gKqbo8Css8uPmLq4vtOXhZnoAAAEAUlEQVR4nO2d63biIBRGDbmYIkkTNV4a6+39H3Jq2zVja8AwXMJhffu3Bvci4cTDAWYzAAAAAAAAAAAAAAAAAAAAAAAAAAAAgDmZXzzbLddNX+Y+SbaLd3+Wm2PJa5H4pS7TdrH04jc/8Nqz3TeC8YWHflzkE/l9wtq9Y79syyf0+0DwtVvBQzmt4AepU8UABN0qNmxqu0+4s2exSqd2+0K0jkbUzHcIlMIWbgxPYdyjN7iT0J9NrXUHu7gwvE4cCe9x8yQep3yX+U36bl8wCyEU/qV0cJvuAwkVX9Rb+4YhPYY37D+IRTix4kYev6H9iBiYYTqHIQxhaB3xQDyGouRcJLvXXxxKlSMdQ8F4d7nOVwMtLjuFIhXDOu/Ob9Im3xQvijQMRbpVZ1xaeSeSMOTds4wSbUPBi6dtkjYUYkRKkLJh3Q6NnhEZ1rsxgoQNRTvuvw9dQz4yj0TWcMQoSttQ7Ma2SdVwfK6TqGF9HN0mUcOxwwxZQ9GNb5OmITvHbsjl/wcfUMwFhWs4PlTMZhvFREK4hk/mNrPVcvXNck0zT8MreRNV07X/PimUDYZrmEofw3PO6ycpRBqGsus3evOTwRpKB5pCcwI2XEPJ3O1Kd4Y5WMO6Gb76WneGOVhDWYWBdqFHuIan4atrXyhcQ0lFmnYZBDXDDIYwhCEMYQhDGMIQhjCEIQzVCMbvyCWGOX+E0ciX1ruiukdSY3KtHil2FLL6oh9VdDFMtiMwM6MxWTiAYuIiGMPSqKR+lcduOJdnUSMxjP8uXcvbjMTwJF8vF4mhIhMeiWFPIB6aGVKYxzczlIfDgAw1akseILEagZls8PBO4S5lJj+komDITX6IqslgDI368EWxQD4YQ6M+3FL4B2zUh4qAH46hSR9mreLC4RgaRIsliUyUrDJhDMoam2AMk/K/t62qlFV84RgmrL9f3NtJKoa636uAX3t1ewEZ/lykzSRVX7fCS52l3EEZ/kBW16Z9cRjCEIYwhCEMYQhDGMIQhvqGpOtpYAhDGMIQhjDURbKbekSGMVbQwhCGMIQhDGEIQ2eGDvbVP4dlyOwbqjZt8m8oeuuC+vurODWU5eyMUFUoeTfkZweGVo57smVoVnssYW7jQbRk6OKYmZm6VNCzId84Mdxb6EQ7hjq7S2rRmB/5ZMfQwQvN9+9IjIdTK4bc0dF5MxunWtkwLN0MM18Yv9hYMCwPTk/M3aRmN6q5IescHwm8T4yGG2PDtHF+5nHWpAZx0dCQCcXmoPbYb9WbAbgyrHly8nX4+PzU55yxUp9UtqfCk+8xnvLj1WBtvz6rzbl40ae5Dhs26q9diuve39nxAAAAAAAAAAAAAAAAAAAAAAAAAAAAxMsfMtt2/nkFjuAAAAAASUVORK5CYII="
			width="42px" height="42px" /></a>
	</div>
	<div>
		<div class="btn-group btn-group-justified">
			<ul id="jMenu">
				<!-- Do not forget the "fNiv" class for the first level links !! -->
				<security:authorize access="isAnonymous()">
					<!-- Log in -->
					<li><a class="fNiv" href="security/login.do"><spring:message
								code="master.page.login" /></a></li>

					<!-- Registrarse (Boton de Sign Up) -->
					<li><a><spring:message code="master.page.register" /> </a>
						<ul>
							<li class="isParent"></li>
							<li><a href="actor/createParent.do"><spring:message
										code="master.page.signUpParent" /></a></li>
							<li><a href="actor/createAgent.do"><spring:message
										code="master.page.signUpAgent" /></a></li>
							<li><a href="actor/createTeacher.do"><spring:message
										code="master.page.signUpTeacher" /></a></li>
						</ul></li>
				</security:authorize>


				<security:authorize access="isAuthenticated()">
					<li><a class="fNiv" href="actor/myDisplay.do"> <spring:message
								code="master.page.profile" /> (<security:authentication
								property="principal.username" />)
					</a>
						<ul>
							<li><a href="folder/actor/list.do"><spring:message
										code="master.page.message" /> </a></li>

							<!-- Padres -->
							<security:authorize access="hasRole('PARENT')">
								<li><a href="parentsGroup/parent/mylist.do"><spring:message
											code="master.page.parent.myparentsgroups" /></a></li>
							</security:authorize>

							<!-- Profesores -->
							<security:authorize access="hasRole('TEACHER')">
								<li><a href="parentsGroup/teacher/mylist.do"><spring:message
											code="master.page.parent.myparentsgroups" /></a></li>
								<li><a href="classGroup/teacher/list.do"><spring:message
											code="master.page.parent.myclasses" /></a></li>
								<li><a href="entry/teacher/myList.do"><spring:message
											code="master.page.teacher.myentries" /></a></li>
							</security:authorize>



							<!-- Admin -->
							<security:authorize access="hasRole('ADMIN')">

								
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

							<!-- Students -->
							<security:authorize access="hasRole('STUDENT')">
								<li><a href="chatRoom/student/myList.do"><spring:message
											code="master.page.student.chatRooms" /></a></li>
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
							<li><a href="schoolCalendar/list.do"><spring:message
										code="master.page.schoolCalendar" /></a></li>
						</ul></li>
					<li><a href="searcher/display.do"><spring:message
								code="master.page.general.searcher" /></a></li>

				</security:authorize>
			</ul>
		</div>
	</div>
	<div style="float: left;"> 
		<a href="?language=en">en</a> | <a href="?language=es">es</a>
	</div>

</div>