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

<jstl:if test="${!accessErrror}">
<div class="container-fluid">
	<form:form action="${uri}" modelAttribute="entry">
		
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		<acme:textbox code="entry.title" path="title" />
		<acme:textbox code="entry.body"  path="body" />
		
		<acme:submit name="save" code="entry.save" />
		<acme:cancel code="entry.cancel" url="${cancelUri}" />&nbsp; 
	
	
	</form:form>
</div>
</jstl:if>
<jstl:if test="${accessErrror}">
	<h4><spring:message code="entry.error.access" /></h4>
</jstl:if>