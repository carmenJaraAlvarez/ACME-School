<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">
	<jsp:useBean id="date" class="java.util.Date" />

	<hr />

	<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" />
		Acme-School., Inc.
	</b>
	<div>
		<a href="legal/terms.do"><spring:message
				code="master.page.legal.terms" /></a> <a href="legal/cookies.do"><spring:message
				code="master.page.legal.cookies" /></a>
	</div>
</div>