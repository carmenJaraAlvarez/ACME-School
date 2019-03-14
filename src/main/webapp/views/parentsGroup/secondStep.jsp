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
<form:form action="${uri }"
	modelAttribute="createGroupForm">
	<form:hidden path="school"/>
	
	<acme:select code="parentsGroup.level" path="level"
		items="${all}" itemLabel="level" />
	<br>
	

<br><br>

	<acme:submit name="done" code="parentsGroup.done" />
	<acme:cancel code="parentsGroup.cancel" url="${uriCancel }" />&nbsp; 

</form:form>
<!-- Aplicacion con niveles de 1 a 6 -->

</div>
