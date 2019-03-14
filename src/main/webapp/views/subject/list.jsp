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

<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">

	<spring:message code="${levelSchool}" var="levelSchoolInt" />

	<b><jstl:out value="${level.school.nameSchool}" /><br> <jstl:out
			value="${levelSchoolInt }" /></b>

	<br/><br/>
	<display:table class="table table-striped table-bordered" pagesize="5"
		name="subjects" requestURI="subject/list.do" id="rowSubject">



		<display:column titleKey="subject.name">
			<jstl:out value="${rowSubject.name }" />
		</display:column>







		<!-- 		<!-- Columna de editar --> -->
<%-- 		<security:authorize access="isAuthenticated()" > --%>

		<%-- 				<display:column titleKey="subject.edit"> --%>
		<%-- 					<a href="subject/edit.do?subjectId=${rowSubject.id}"> <spring:message --%>
		<%-- 							code="subject.edit" /> --%>
		<!-- 					</a> -->
		<%-- 				</display:column> --%>

		<%-- 		</security:authorize> --%>


	</display:table>

	<br /> <br />
	<!-- Botón de create -->
	<security:authorize access="hasRole('PARENT')">

		<a href="subject/parent/create.do?levelId=${levelId}"> <spring:message
				code="subject.create" />
		</a>
	</security:authorize>
</div>

