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

	
<%-- 		<jstl:out value="${advertisement.wish.pay}"></jstl:out> --%>


<spring:message	code="advertisement.pay" var="v1" />
<a href="advertisement/agent/pay.do?advertisementId=${advertisementId}"> <input
						type=button class="btn" value="${v1 }" style="width: 100%;"/></a>
							<br>
							<br>
	<spring:message		code="advertisement.paypal" var="v2"/>

<a href="advertisement/agent/paypal.do?advertisementId=${advertisementId}">
					<input type=button class="btn" value="${v2 }"  style="width: 100%;"/></a>




</div>
