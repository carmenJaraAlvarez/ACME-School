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
<jstl:out value="${createGroupForm.school.nameSchool }"/>
<br>
<jstl:out value="${createGroupForm.level.level }"/>
<br>
<form:form action="${uri }"
	modelAttribute="createGroupForm">

	<form:hidden path="school"/>
	<form:hidden path="level"/>
	<acme:select code="parentsGroup.classgroup" path="classGroup"
		items="${all}" itemLabel="name" />
	<br>
	
	<a href="parentsGroup/parent/createClass.do?levelId=${createGroupForm.level.id }"> <spring:message
		code="classGroup.create" />
</a>
<br>

	<acme:submit name="done" code="parentsGroup.done" />
	<acme:cancel code="parentsGroup.cancel" url="${uriCancel }" />&nbsp; 

</form:form>
<!-- Botón de create -->

</div>
