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

<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">

<br />
<br />

<div class="dn-breadcrumbs">
  <div class="dn-breadcrumbs-background-services"></div>
  <div class="container">
    <div class="row">
      <div class="span16">
        <ul class="breadcrumb">
			<spring:message code="advertisement.failure" />
		</ul>
      </div>
      <a href="<spring:url value='advertisement/agent/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="advertisement.back"/>" /></a>
    </div>
  </div>
</div>
<br />
</div>