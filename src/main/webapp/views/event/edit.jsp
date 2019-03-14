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
	<form:form action="event/parent/edit.do"
		modelAttribute="event">
		
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="parentsGroup"/>
		
		<acme:textbox code="event.moment" path="moment" placeholder="MM/dd/yyyy HH:mm" />
		<acme:textbox code="event.location.northCoordinate" path="location.northCoordinate" placeholder="12.123456" />
		<acme:textbox code="event.location.eastCoordinate" path="location.eastCoordinate" placeholder="-123.123456" />  
		<acme:textbox code="event.description" path="description" />
		
		<acme:submit name="save" code="event.save" />
		<acme:cancel code="event.cancel" url="${uri}" />&nbsp; 
	
	
	</form:form>
	<h4><em><spring:message code="event.location.warning" /></em></h4>
	<h4><em><spring:message code="event.location.warning.latitud" /></em></h4>
	<h4><em><spring:message code="event.location.warning.longitud" /></em></h4>
</div>