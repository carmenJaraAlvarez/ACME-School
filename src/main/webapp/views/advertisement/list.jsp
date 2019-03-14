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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">

<display:table pagesize="3" class="table table-striped table-bordered" keepStatus="true"
	name="advertisements" requestURI="${uri}" id="rowAdvertisement">
	<display:column titleKey="advertisement.banner">
		<img src="${rowAdvertisement.banner}" width="80px" height="80px" />
	</display:column>

	<display:column titleKey="advertisement.title">
		<jstl:out value="${rowAdvertisement.title }" />
	</display:column>
	<display:column titleKey="advertisement.targetPage">
		<div>
			<a href="${rowAdvertisement.web}"> <jstl:out
					value="${rowAdvertisement.web}" />
			</a>
		</div>
	</display:column>
	<display:column titleKey="advertisement.paid">
	
	<jstl:out value="${rowAdvertisement.paid}"></jstl:out>
	
	</display:column>
	
	<display:column titleKey="advertisement.pay.actions">
	<jstl:if test="${!rowAdvertisement.paid}">
		<a href="advertisement/agent/paidMethod.do?advertisementId=${rowAdvertisement.id}"> <spring:message
				code="advertisement.pay.actions" />
		</a>
	</jstl:if>
	</display:column>
	

<%-- 	<display:column > --%>
<%-- 	<a href="advertisement/agent/display.do"> <spring:message --%>
<%-- 				code="advertisement.display" /> --%>
<!-- 		</a> -->
<%-- 	</display:column> --%>

	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="advertisement.delete">
			<jstl:if test="${fromTabooList}">
				<a
					href="advertisement/administrator/taboo/delete.do?advertisementId=${rowAdvertisement.id}"
					onclick="return confirm('<spring:message code="advertisement.confirm.cancel" />')">
					<spring:message code="advertisement.delete" />
				</a>
			</jstl:if>
			<jstl:if test="${!fromTabooList}">
				<a
					href="advertisement/administrator/delete.do?advertisementId=${rowAdvertisement.id}"
					onclick="return confirm('<spring:message code="advertisement.confirm.cancel" />')">
					<spring:message code="advertisement.delete" />
				</a>
			</jstl:if>
		</display:column>

	</security:authorize>
	<security:authorize access="hasRole('AGENT')" >
	<display:column titleKey="advertisement.amount" >
	 <jstl:out value="${amount}" /> &euro;
	</display:column>

		</security:authorize>
</display:table>
<security:authorize access="hasRole('AGENT')">

	<div>
		<a href="advertisement/agent/create.do"> <spring:message
				code="advertisement.register" />
		</a>
	</div>

</security:authorize>

</div>