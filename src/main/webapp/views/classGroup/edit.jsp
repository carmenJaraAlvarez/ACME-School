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
	<form:form action="classGroup/parent/edit.do"
		modelAttribute="classGroup">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="teachers" />
		<form:hidden path="students" />
		<form:hidden path="parentsGroups" />
		<form:hidden path="classTimes" />
		<form:hidden path="level" />

		<acme:textbox code="classGroup.name" path="name" />
		<br />

		<acme:submit name="save" code="classGroup.save" />
		<acme:cancel code="classGroup.cancel"
			url="school/display.do?schoolId=${classGroup.level.school.id}" />



	</form:form>
</div>