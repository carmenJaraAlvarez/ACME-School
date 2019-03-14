
<!--  * display.jsp -->
<!--  * -->
<!--  * Copyright (C) 2017 Universidad de Sevilla -->
<!--  *  -->
<!--  * The use of this project is hereby constrained to the conditions of the  -->
<!--  * TDG Licence, a copy of which you may download from  -->
<!--  * http://www.tdg-seville.info/License.html -->


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div class="container-fluid">
	<div>
		<div>



			<div class="container-fluid" style="float: left;">
				<br>
				<div>
					<h3>
						<jstl:out value="${school.nameSchool}" />
					</h3>
				</div>
				<div>
					<spring:message code="school.phoneNumber"></spring:message>
					:
					<jstl:out value="${school.phoneNumber}" />
				</div>
				<div>
					<jstl:out value="${school.address}" />
				</div>
				<div>
					<jstl:out value="${school.emailSchool}" />
				</div>
			</div>
			<div class="container-fluid">
				<img src="${school.image}" alt="${school.image}" width="220px"
					style="float: right;">
			</div>
		</div>

		<hr>

		<jstl:forEach items="${levels}" var="level">
			<div>

				<spring:message code="school.classesOf" />
				<jstl:out value="${level.level}"></jstl:out>

			</div>

			<div>
				<display:table class="table table-striped table-bordered"
					name="${level.classGroups}" requestURI="school/display.do"
					id="rowClassGroup">

					<display:column titleKey="classGroup.name">
						<jstl:out value="${rowClassGroup.name}" />
					</display:column>

					<security:authorize access="hasRole('PARENT')">
						<display:column titleKey="parent.signUpStudent">
							<a href="actor/createStudent.do?classGroupId=${rowClassGroup.id}">
								<spring:message code="parent.signUpStudent" />
							</a>
						</display:column>

						<display:column titleKey="classGroup.edit">
							<a
								href="classGroup/parent/edit.do?classGroupId=${rowClassGroup.id}">
								<spring:message code="classGroup.edit" />
							</a>
						</display:column>
					</security:authorize>

					<security:authorize access="hasRole('TEACHER')">
						<%
							Boolean isAdd = false;
						%>
						<jstl:forEach var="x" items="${listClassGroupTeacher}">
							<jstl:if test="${x.id eq rowClassGroup.id}">
								<%
									isAdd = true;
								%>
							</jstl:if>
						</jstl:forEach>
						<display:column titleKey="teacher.add">
							<jstl:if test="${school.id eq schoolLogueadoId}">
							<jstl:if test="<%=!isAdd%>">
								<a
									href="teacher/addClassGroup.do?classGroupId=${rowClassGroup.id}&&schoolId=${school.id}" onclick="return confirm('<spring:message code="school.confirm.add" />')">
									<spring:message code="teacher.add" />
								</a>
							</jstl:if>
							</jstl:if>
						</display:column>
					</security:authorize>
				</display:table>
			</div>
			<div>
				<spring:message code="school.subjects" var="button1" />
				<a href="subject/list.do?levelId=${level.id}"><input type=button
					class="btn" value="${button1 }" /></a>

				<spring:message code="classGroup.create" var="button2" />
				<security:authorize access="hasRole('PARENT')">
					<a href="classGroup/parent/create.do?levelId=${level.id}"> <input
						type=button class="btn" value="${button2 }" />
					</a>
				</security:authorize>
			</div>

			<br>
		</jstl:forEach>

	</div>
</div>
