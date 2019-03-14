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
	<form:form action="mark/teacher/edit.do" modelAttribute="mark">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="student" />

		<acme:textbox code="mark.value" path="value" />
		<acme:textbox code="mark.comment" path="comment" />		
		<acme:select items="${subjects}" itemLabel="name" id="subject" code="mark.subject" path="subject"/>
			
	<br>

		<br />

		<acme:submit name="save" code="mark.save" />

		<!-- 
	<jstl:if test="">
		<acme:submit name="delete" code="mark.delete" />
	</jstl:if>
 -->

		<acme:cancel code="mark.cancel" url="mark/teacher/list.do?studentId=${mark.student.id}" />



	</form:form>
</div>