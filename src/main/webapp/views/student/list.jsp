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

	<display:table class="table table-striped table-bordered" pagesize="4"
		name="students" requestURI="${uri }" id="rowStudent">

		<display:column titleKey="student.name">
			<jstl:out value="${rowStudent.userAccount.username}" />
		</display:column>
		<security:authorize access="hasRole('TEACHER')">
			<display:column titleKey="student.parent">
				<jstl:out value="${rowStudent.parent.name}" />
			</display:column>
			<display:column titleKey="student.mail">
				<jstl:out value="${rowStudent.parent.email}" />
			</display:column>
			<display:column titleKey="student.mark">
				<a href="mark/teacher/list.do?studentId=${rowStudent.id}"> <spring:message
						code="student.mark" />
				</a>
			</display:column>
		</security:authorize>
	</display:table>
	<jstl:if test="${riskList}">
		<spring:message code="student.risk.note" /><br/><br/>
	</jstl:if>
	<br/><br/>
	<acme:cancel url="${uriCancel}" code="student.back" />



</div>
