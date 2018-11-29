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

<display:table pagesize="5" class="displaytag" name="classGroups"
	requestURI="classGroup/parent/list.do" id="rowClassGroup">

	<display:column titleKey="classGroup.level">
		<jstl:out value="${rowClassGroup.level.level}" />
	</display:column>

	<display:column titleKey="classGroup.name">
		<jstl:out value="${rowClassGroup.name}" />
	</display:column>

	<display:column>
		<a href="actor/createStudent.do?classGroupId=${rowClassGroup.id}">
			<spring:message code="parent.signUpStudent" />
		</a>
	</display:column>

</display:table>

