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

<!-- Botón de create -->
<form:form action="${uri }"
	modelAttribute="createGroupForm">


	<acme:select code="parentsGroup.School" path="school" items="${all}" itemLabel="nameSchool" />
	<br>
	

<br><br>

	<a href="school/parent/create.do?createGroup=1"> <spring:message
		code="school.create" /></a><br>
	<acme:submit name="done" code="parentsGroup.done" />
	<acme:cancel code="parentsGroup.cancel" url="${uriCancel }"/>&nbsp; 

</form:form>

</div>
