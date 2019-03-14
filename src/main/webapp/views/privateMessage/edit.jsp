<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="container-fluid">
	<form:form action="${actionUri}" modelAttribute="privateMessageForm">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:choose>
			<jstl:when test="${!esNotificacion}">
				<acme:textbox code="message.receivers" path="sendTo" placeholder="John, Jesus, Marcos"/>
				<jstl:if test="${badNames}">
					<span class="error"><spring:message code="message.badNames" /> <jstl:out value="${nombresIncorrectos}"></jstl:out></span><br/><br/>
				</jstl:if>
			</jstl:when>
		</jstl:choose>
	
		<acme:textbox code="message.subject" path="subject"/>
		
		<form:label path="priority">
			<spring:message code="message.priority" />
		</form:label>
		
		<form:select path="priority" cssClass="form-control" cssStyle="max-width:800px;">
			<form:option label="LOW" value="LOW" />
			<form:option label="HIGH" value="HIGH" />
			<form:option label="NEUTRAL" value="NEUTRAL" />
		</form:select>
		<form:errors cssClass="error" path="priority" /><br/>
		
		
		<acme:textarea code="message.body" path="body"/>
	
		
		
		<%--
			  * botones del formulario
			 --%>
		<acme:submit name="save" code="message.send"/>
		<acme:cancel url="${cancelUri}" code="message.cancel"/>
	
	</form:form>
</div>