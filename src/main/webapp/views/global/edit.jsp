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

<form:form action="global/administrator/edit.do" modelAttribute="global">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<div>
		<form:label path="spamWords">
			<spring:message code="global.spamWords" />:
		</form:label>
		<form:input path="spamWords" />
		<form:errors cssClass="error" path="spamWords" />
	</div>

	<div>
		<form:label path="dangerousWords">
			<spring:message code="global.dangerousWords" />:
		</form:label>
		<form:input path="dangerousWords" />
		<form:errors cssClass="error" path="dangerousWords" />
	</div>

	<div>
		<form:label path="wordsLimit">
			<spring:message code="global.wordsLimit" />:
		</form:label>
		<form:input path="wordsLimit" />
		<form:errors cssClass="error" path="wordsLimit" />
	</div>

	<div>
		<form:label path="price">
			<spring:message code="global.price" />:
		</form:label>
		<form:input path="price" />
		<form:errors cssClass="error" path="price" />
	</div>

	<div>
		<form:label path="payPalEmail">
			<spring:message code="global.payPalEmail" />:
		</form:label>
		<form:input path="payPalEmail" />
		<form:errors cssClass="error" path="payPalEmail" />
	</div>

	<br />

	<hr>

	<input type="submit" name="save"
		onclick="return confirm('Are you sure you want to continue?')"
		value="<spring:message code="global.save" />" />&nbsp; 
	<br />

</form:form>