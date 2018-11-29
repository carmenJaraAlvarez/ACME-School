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

<display:table pagesize="5" class="displaytag" name="parentsGroup"
	requestURI="${uri}" id="rowParentsGroup">

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

	<jstl:if test="${rowParentsGroup.creator.id eq logueadoId}">

		<!-- Columna de administrar administradores del grupo -->
		<display:column>
			<a href="parent/parent/list.do?parentsGroupId=${rowParentsGroup.id}"> <spring:message
					code="parentsOfGroup.list" />
			</a>
		</display:column>
			</jstl:if>
		<!-- Columna de editar -->

		<!-- Columna para apuntarse -->

		<!-- Botón de create -->
	


</display:table>
<div>
		<security:authorize access="hasRole('PARENT')">

			<a href="parentsGroup/parent/create.do"> <spring:message
					code="parentsGroup.create" />
			</a>
		</security:authorize>
</div>
