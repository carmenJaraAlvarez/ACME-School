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

 
<%--
 * Edit folder
 
 Recibe folder que tiene los siguientes atributos
 
 	private String				name;
	private boolean				ofTheSystem;
	private Collection<Message>	messages;
	private Collection<Folder>	childFolders;
	private Folder				fatherFolder;
	private Actor				actor;
 --%>



<div class="container-fluid">
	<form:form action="${uri}" modelAttribute="folder">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		
	
		<jstl:if test="${folder.id != 0}">	
			<acme:select items="${all}" itemLabel="name" code="folder.father" path="fatherFolder"/>
		</jstl:if>
	
		<acme:textbox code="folder.name" path="name"/>
		
		<acme:submit name="save" code="folder.save"/>
		
		<jstl:if test="${folder.id != 0}">		
			<input type="submit" name="delete" class="btn btn-primary"
					value="<spring:message code="folder.delete" />"
					onclick="return confirm('<spring:message code="folder.confirm.delete" />')" />&nbsp;
		</jstl:if>
		
		<acme:cancel url="${cancelUri}" code="folder.cancel"/>
	
	</form:form>
</div>