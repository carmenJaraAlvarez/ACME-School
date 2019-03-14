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
		name="parentsGroups" requestURI="${uri}" id="rowParentsGroup">

		<display:column titleKey="parentsGroup.image">
			<img src="${rowParentsGroup.image}" alt="${rowParentsGroup.image}"
				height="60px" width="60px" />
		</display:column>

		<display:column titleKey="parentsGroup.name">
			<jstl:out value="${rowParentsGroup.name }" />
		</display:column>

		<display:column titleKey="parentsGroup.description">
			<jstl:out value="${rowParentsGroup.description }" />
		</display:column>


		<!-- Columna de administrar administradores del grupo -->

		<security:authorize access="hasRole('PARENT')">



			<%-- 		<jstl:forEach var="x" items="${rowParentsGroup.members}"> --%>
			<%-- 			<jstl:if test="${x.id eq logueadoId}"> --%>
			<%-- 				<% --%>
			<!--  					NoMember = false; -->
			<%-- 				%> --%>
			<%-- 			</jstl:if> --%>
			<%-- 		</jstl:forEach> --%>

			<%-- 		<jstl:if test="${rowParentsGroup.creator.id eq logueadoId}"> --%>
			<%-- 			<% --%>
			<!--  				NoMember = false; -->
			<%-- 			%> --%>
			<%-- 		</jstl:if> --%>

			<%-- 		<display:column titleKey="parentsOfGroup.list"> --%>
			<%-- 			<jstl:if test="<%=!NoMember%>"> --%>
			<%-- 				<a href="parent/parent/list.do?parentsGroupId=${rowParentsGroup.id}"> --%>
			<%-- 					<spring:message code="parentsOfGroup.list" /> --%>
			<!-- 				</a> -->
			<%-- 			</jstl:if> --%>
			<%-- 		</display:column> --%>

			<!-- Columna de editar -->

			<!-- Columna para apuntarse -->

			<%-- 		<display:column titleKey="parentsGroup.add"> --%>
			<%-- 			<jstl:if test="<%=NoMember%>"> --%>
			<!-- 				<a -->
			<%-- 					href="inscription/parent/createInscription.do?parentsGroupId=${rowParentsGroup.id}"> --%>
			<%-- 					<spring:message code="parentsGroup.add" /> --%>
			<!-- 				</a> -->
			<%-- 			</jstl:if> --%>
			<%-- 		</display:column> --%>


			<!-- Columna para mostrar el grupo -->

			<display:column titleKey="parentsGroup.access">
				<a
					href="parentsGroup/parent/display.do?parentsGroupId=${rowParentsGroup.id}">
					<spring:message code="parentsGroup.access" />
				</a>
			</display:column>

			<!-- Boton de abandonar grupo -->


			<jstl:if test="${myList}">
				<display:column titleKey="parentsGroup.delete.member">
					<a
						href="inscription/parent/deleteInscription.do?parentsGroupId=${rowParentsGroup.id}">
						<spring:message code="parentsGroup.delete.member" />
					</a>
				</display:column>
				
				<!-- Boton de editar -->
				
				<display:column titleKey="parentsGroup.edit">
					<a href="parentsGroup/parent/edit.do?parentsGroupId=${rowParentsGroup.id}"> <spring:message
							code="parentsGroup.edit" />
					</a>
				</display:column>
				
				<!-- Boton de asignaturas -->
				
				<display:column titleKey="parentsGroup.subjects">
					<a href="subject/list.do?levelId=${rowParentsGroup.classGroup.level.id}"> <spring:message
							code="parentsGroup.subjects" />
					</a>
				</display:column>
			</jstl:if>
			
		</security:authorize>

		<security:authorize access="hasRole('TEACHER')">

			<%
				Boolean NoMember = true;
			%>

			<jstl:forEach var="x" items="${rowParentsGroup.teachers}">
				<jstl:if test="${x.id eq logueadoId}">
					<%
						NoMember = false;
					%>
				</jstl:if>
			</jstl:forEach>

			<jstl:if test="${myList}">
				<display:column titleKey="parentsGroup.delete.member">
					<a
						href="inscription/teacher/deleteInscription.do?parentsGroupId=${rowParentsGroup.id}">
						<spring:message code="parentsGroup.delete.member" />
					</a>
				</display:column>
				
			</jstl:if>
			<display:column titleKey="parentsGroup.add">
				<jstl:if test="<%=NoMember%>">

					<a
						href="inscription/teacher/createInscription.do?parentsGroupId=${rowParentsGroup.id}">
						<spring:message code="parentsGroup.add" />
					</a>

				</jstl:if>
			</display:column>

		</security:authorize>

		<!-- Bot�n de create -->

	</display:table>
	<div>
		<security:authorize access="hasRole('PARENT')">

			<a href="parentsGroup/parent/create.do"> <spring:message
					code="parentsGroup.create" />
			</a>
		</security:authorize>
	</div>
</div>