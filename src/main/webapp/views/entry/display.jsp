
<!--  * display.jsp -->
<!--  * -->
<!--  * Copyright (C) 2017 Universidad de Sevilla -->
<!--  *  -->
<!--  * The use of this project is hereby constrained to the conditions of the  -->
<!--  * TDG Licence, a copy of which you may download from  -->
<!--  * http://www.tdg-seville.info/License.html -->


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
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="styles/entry.css" type="text/css">
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="container-fluid">
<jstl:choose>
	<jstl:when test="${entryNull}">
<%-- 		<p><spring:message code="entry.error.null" /></p> --%>
	</jstl:when>
	<jstl:otherwise>
		<b><spring:message code="entry.teacher" /></b>:
		<jstl:out value="${teacher.name}" /><br>
		<b><spring:message code="entry.teacher.school" /></b>:
		<jstl:out value="${teacher.school.nameSchool}" /><br/><br/>
		<div class="container2">
			<div class="row1">
				<div class="title">
					<p><b><jstl:out value="${entry.title}" /></b></p>
			  	</div>
				<div class="moment">
					<spring:message code="entry.Date.format" var="entryDateFormat" />
					<p><fmt:formatDate pattern="${entryDateFormat}" value="${entry.moment}" /></p>
			  	</div>
			</div>
			<div class="row2">
				<div class="body">
			    	<p><jstl:out value="${entry.body}" /></p>
			  	</div>
			</div>
		</div>
		<br/>
		<acme:cancel code="event.back" url="${uri}" />
	</jstl:otherwise>
</jstl:choose>
</div>
