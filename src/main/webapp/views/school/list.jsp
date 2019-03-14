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

	<display:table class="table table-striped table-bordered" pagesize="5"
		name="schools" requestURI="school/list.do" id="rowSchool">

		<display:column titleKey="school.nameSchool">
			<a href="school/display.do?schoolId=${rowSchool.id}"><jstl:out value="${rowSchool.nameSchool }" /></a>
		</display:column>

		<display:column titleKey="school.address">
			<jstl:out value="${rowSchool.address }" />
		</display:column>

		<jstl:if test="${showParentButtons}">
			<!-- Columna de editar -->
			<security:authorize access="hasRole('PARENT')">
				<security:authorize access="hasRole('PARENT')">
					<display:column titleKey="school.edit">
						<a href="school/parent/edit.do?schoolId=${rowSchool.id}"> <spring:message
								code="school.edit" />
						</a>
					</display:column>
				</security:authorize>
			</security:authorize>
		</jstl:if>

	</display:table>

	<jstl:if test="${showParentButtons}">
		<!-- Botón de create -->
		<security:authorize access="hasRole('PARENT')">
			<a href="school/parent/create.do?createGroup=0"> <spring:message
					code="school.create" />
			</a>
		</security:authorize>
	</jstl:if>
</div>
