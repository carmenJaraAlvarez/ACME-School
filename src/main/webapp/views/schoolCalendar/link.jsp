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
<div>
<form:form action="schoolCalendar/parent/link.do" modelAttribute="linkForm">
 	<form:hidden path="idCalendar" />
 	
	<form:select name="idGroup" path="idGroup">
		<jstl:forEach items="${parentsGroups}" var="parentsGroup">
		  <option value="${parentsGroup.id}">${parentsGroup.name} | ${parentsGroup.classGroup.level.school.nameSchool}</option>
		</jstl:forEach>
	</form:select>
	
	<br />
<br />
<br />
	<acme:submit name="save" code="schoolCalendar.save" />&nbsp; 
	<acme:cancel code="schoolCalendar.cancel" url="/schoolCalendar/list.do" />
	
	</form:form>

		
</div>

</div>
