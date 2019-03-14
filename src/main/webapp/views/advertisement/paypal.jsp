<%--
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">
<form:form name='formTpv' method='post'
	action='https://www.sandbox.paypal.com/cgi-bin/webscr'
	modelAttribute="advertisement">
	<security:authorize access="hasRole('AGENT')">
		<jstl:if test="${payPalEmail!=null}">
			<spring:message code="advertisement.agent.paypal" />
			<br />
			<br />
			<input name="cmd" type="hidden" value="_cart">
			<input name="upload" type="hidden" value="1">
			<input name="business" type="hidden" value="${payPalEmail}">
			<input name="shopping_url" type="hidden"
				value="https://localhost:8443/Acme-School">
			<input name="currency_code" type="hidden" value="EUR">
			<input name="return" type="hidden"
				value="https://www.acme.com:8443/advertisement/agent/success.do?advertisementId=${advertisement.id}">
			<input type='hidden' name='cancel_return'
				value='https://www.acme.com:8443/advertisement/agent/failure.do'>
			<input name="rm" type="hidden" value="2">

			<input name="item_number_1" type="hidden" value="1">
			<input name="item_name_1" type="hidden"
				value="${advertisement.agent.name}">
			<input name="amount_1" type="hidden" value="${amount}">
			<input name="quantity_1" type="hidden" value="1">
			
				
			
			<button class="btn btn-primary btn-orange uppercase" name="save">
				<spring:message code="advertisement.paypal"></spring:message>
			</button>
		</jstl:if>
				
			
	&nbsp;
		<a href="<spring:url value='advertisement/agent/list.do' />"><input
			type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="advertisement.cancel"/>" /></a>

	</security:authorize>
</form:form>

<form:form action="advertisement/agent/pay.do" modelAttribute="advertisement">



</form:form>
</div>