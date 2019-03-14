<%--
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">
<form:form action="advertisement/agent/pay.do" modelAttribute="advertisement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="agent" />
	<form:hidden path="title" />
	<form:hidden path="banner" />
	<form:hidden path="web" />
		
	<h3>
		<jstl:out value="${card }" />
	</h3>

	<acme:textbox code="advertisement.creditcard.holderName" path="creditCard.holderName" placeholder="John Doe" />

	<acme:textbox code="advertisement.creditCard.num" path="creditCard.number" placeholder="1111222233334444"/>

	<acme:textbox code="advertisement.creditcard.brand" path="creditCard.brand" placeholder="VISA"/>
	
	<acme:textbox code="advertisement.creditCard.cvv" path="creditCard.cvv" placeholder="999" />

	<acme:textbox code="advertisement.creditCard.month" path="creditCard.expirationMonth" placeholder="MM" />

	<spring:message code="advertisement.creditCard.year" var="y"/>
	<acme:textbox code="advertisement.creditCard.year" path="creditCard.expirationYear" placeholder="${y}"/>             

	<br />
	<acme:submit name="save" code="advertisement.save" />&nbsp; 
	
	<acme:cancel  code="advertisement.cancel" url="/advertisement/agent/list.do"/>
</form:form>


</div>
