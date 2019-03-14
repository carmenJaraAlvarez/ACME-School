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
	<jstl:if test="${notAuthorized}">
		<div class="error">
			<spring:message code="mark.notAvailable" />
		</div>
	</jstl:if>
	<jstl:if test="${authorized}">

		<h3><jstl:out value="${student.name}" /></h3>

		<display:table class="table table-striped table-bordered" pagesize="5"
			name="marks" requestURI="${uri}" id="rowMark">


			<display:column titleKey="mark.comment">
				<jstl:out value="${rowMark.comment }" />
			</display:column>

			<display:column titleKey="mark.subject">
				<jstl:out value="${rowMark.subject.name }" />
			</display:column>

			<security:authorize access="hasRole('PARENT')">
				<display:column titleKey="mark.teacher">
					<jstl:out value="${rowMark.teacher.name }" />
				</display:column>
			</security:authorize>

			<display:column titleKey="mark.value">
				<jstl:out value="${rowMark.value }" />
			</display:column>

			<spring:message code="mark.Date.format" var="markDateFormat" />

			<display:column property="date" titleKey="mark.date"
				format="{0,date,${markDateFormat}}" />



			<security:authorize access="hasRole('TEACHER')">
				<display:column titleKey="mark.edit">
					<a href="mark/teacher/edit.do?markId=${rowMark.id}"> <spring:message
							code="mark.edit" />
					</a>
				</display:column>
			</security:authorize>


		</display:table>
		<br/>

		<security:authorize access="hasRole('TEACHER')">
			<a href="mark/teacher/create.do?studentId=${studentId}"> <spring:message
					code="mark.create" />
			</a>
		</security:authorize>
	</jstl:if>
</div>
