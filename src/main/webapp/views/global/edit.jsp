<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="styles/messages.css" type="text/css">

<div class="container-fluid">

	
	<jstl:choose>
		<jstl:when test="${saved}">
			<div class="verde">
				<spring:message code="global.save.sucessfull" />
			</div>
			<br/>
		</jstl:when>
	</jstl:choose>
	<form:form action="global/administrator/edit.do"
		modelAttribute="global">

		<form:hidden path="id" />
		<form:hidden path="version" />

		<div>
			<acme:textbox code="global.spamWords" path="spamWords" />
		</div>

		<div>
			<acme:textbox code="global.dangerousWords" path="dangerousWords" />
		</div>

		<div>
			<acme:textbox code="global.wordsLimit" path="wordsLimit" />
		</div>

		<div>
			<acme:textbox code="global.price" path="price" />
		</div>

		<div>
			<acme:textbox code="global.payPalEmail" path="payPalEmail" />
		</div>

		<br />

		<hr>
		<button type="submit" name="save" class="btn btn-primary"
			onclick="return confirm('<spring:message code="global.confirm.continue" />')">
			<spring:message code="global.save" />
		</button>
		<br />

	</form:form>
</div>