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
	<form:form action="school/parent/editForCreateGroup.do" modelAttribute="school">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="teachers" />
		<form:hidden path="levels" />

		<acme:textbox code="school.nameSchool" path="nameSchool" />
		<acme:textbox code="school.address" path="address" />
		<acme:textbox code="school.phoneNumber" path="phoneNumber" />
		<acme:textbox code="school.emailSchool" path="emailSchool" />
		<acme:textbox code="school.image" path="image" />
		<br />

		<acme:submit name="save" code="school.save" />
		<acme:cancel code="school.cancel" url="parentsGroup/parent/create.do" />
	</form:form>
</div>