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

<form:form action="parentsGroup/parent/edit.do" modelAttribute="parentsGroupEditRawForm">
	<form:hidden path="id" />
	<form:hidden path="version" />


	
	<acme:textbox code="parentsGroup.name" path="name" />
	<acme:textbox code="parentsGroup.image" path="url" />
	<acme:textbox code="parentsGroup.description" path="description" />

	<br>


	<acme:submit name="save" code="parentsGroup.save" />
	<acme:cancel code="parentsGroup.cancel" url="parentsGroup/parent/mylist.do" />&nbsp; 





</form:form>
</div>