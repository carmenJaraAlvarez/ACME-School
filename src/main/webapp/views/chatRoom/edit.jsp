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
<form:form action="chatRoom/student/edit.do" modelAttribute="chatRoom">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="counter" />
	<form:hidden path="students" />
	<form:hidden path="studentMessages"/>

	<acme:textbox code="chatRoom.name" path="name" />
	
	<br />

	<acme:submit name="save" code="chatRoom.save" />
	<acme:cancel code="chatRoom.cancel" url="chatRoom/student/list.do" />

</form:form>
</div>