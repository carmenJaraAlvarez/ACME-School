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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">

	<display:table pagesize="5" class="table table-striped table-bordered"
		name="classGroups" requestURI="${requestURI}"
		id="rowClassGroup">

		<display:column titleKey="classGroup.level">
			<jstl:out value="${rowClassGroup.level.level}" />
		</display:column>

		<display:column titleKey="classGroup.name">
			<jstl:out value="${rowClassGroup.name}" />
		</display:column>
		<security:authorize access="hasRole('PARENT')">
			<display:column titleKey="parent.signUpStudent">
				<a href="actor/createStudent.do?classGroupId=${rowClassGroup.id}">
					<spring:message code="parent.signUpStudent" />
				</a>
			</display:column>

			<display:column titleKey="parent.listSubjects">
				<a href="subject/parent/list.do?classGroupId=${rowClassGroup.id}">
					<spring:message code="parent.listSubjects" />
				</a>
			</display:column>

		</security:authorize>
		<security:authorize access="hasRole('TEACHER')">
			<display:column titleKey="classGroup.student">
				<a href="student/teacher/list.do?classGroupId=${rowClassGroup.id}">
					<spring:message code="classGroup.listStudents" />
				</a>
			</display:column>

		</security:authorize>
	</display:table>
	<security:authorize access="hasRole('PARENT')">
		<div>
			<a href="classGroup/parent/create.do?schoolId=${schoolId}"><spring:message
					code="classGroup.create" /></a>
		</div>
	</security:authorize>

	<security:authorize access="hasRole('TEACHER')">
		<spring:message code="student.riskSituations" var="button" />
		<a href="student/teacher/riskList.do"> <input type=button
			class="btn" value="${button }" />
		</a>
	</security:authorize>
</div>