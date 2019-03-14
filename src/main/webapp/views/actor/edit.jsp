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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">
<form:form action="${direction}" modelAttribute="actorForm">
	<div class="form-group">
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<acme:textbox path="name" code="actor.name" placeholder="John" />
		<br />
		<acme:textbox path="surname" code="actor.surname" placeholder="Due Due" />
		<br />
		<acme:textbox path="email" code="actor.email" placeholder="email@client.domain" />
		<acme:textbox path="phoneNumber" code="actor.phoneNumber" placeholder="+34123456789"/>
		<acme:textbox path="address" code="actor.address" placeholder="S/Street nºX"/>
		
		<jstl:if test="${actorType eq 'agent'}">
		<acme:textbox path="taxCode" code="agent.taxCode" />
		</jstl:if>
		
		<jstl:if test="${actorType eq 'teacher'}">
		<acme:select items="${schools}" itemLabel="nameSchool" code="teacher.school" path="school"/>
		</jstl:if>
		
		<jstl:if test="${actorType eq 'student'}">
		<acme:select items="${classGroups}" itemLabel="name" code="student.classGroup" path="classGroup"/>
		<acme:textbox path="comment" code="student.comment" />
		</jstl:if>
		
		<acme:submit name="save" code="actor.save"/>
	
		<input type="button" name="cancel" class="btn btn-primary btn-md"
			value="<spring:message code="actor.cancel" />"
			onclick="javascript: relativeRedir('/actor/myDisplay.do');" />
		<br />
	</div>
</form:form>
</div>