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
<form:form action="schoolCalendar/administrator/edit.do" modelAttribute="schoolCalendar">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<spring:message code="shoolCalendar.patternCourse" var="patternCourse" />
	<spring:message code="shoolCalendar.patternImage" var="patternImage" />
	
	<acme:textbox code="schoolCalendar.course" path="course" placeholder="${patternCourse}"/>
	<acme:textbox code="schoolCalendar.country" path="country" />
	<acme:textbox code="schoolCalendar.image" path="image" placeholder="${patternImage}"/>
	<acme:textbox code="schoolCalendar.region" path="region" />
	<br />

	<acme:submit name="save" code="schoolCalendar.save" />

	<acme:cancel code="schoolCalendar.cancel" url="/schoolCalendar/list.do" />&nbsp; 



</form:form>
</div>


