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
	<b><spring:message code="entry.teacher" /></b>:
	<jstl:out value="${teacher.name}" /><br>
	<b><spring:message code="entry.teacher.school" /></b>:
	<jstl:out value="${teacher.school.nameSchool}" /><br/><br/>

	<display:table pagesize="5" class="table table-striped table-bordered" name="entries"
		requestURI="${uri}" id="rowEntry">
	
		<spring:message code="entry.Date.format" var="entryDateFormat" />
		
		<display:column property="moment"  titleKey="entry.moment" format ="{0,date,${entryDateFormat}}"/>
		
		<display:column titleKey="entry.title">
			<jstl:out value="${rowEntry.title}" />
		</display:column>
		
		<jstl:if test="${myList}">
			<display:column titleKey="event.edit">
				<a href="entry/teacher/edit.do?entryId=${rowEntry.id }">
					<spring:message code="entry.edit" />
				</a>
			</display:column>
		</jstl:if>
		<display:column titleKey="event.display">
			<a href="entry/teacher/display.do?entryId=${rowEntry.id }">
				<spring:message code="entry.display" />
			</a>
		</display:column>
	
	</display:table>

	<jstl:if test="${myList}">
		<div>
			<a href="entry/teacher/edit.do">
				<spring:message code="entry.create" />
			</a>
		</div>
	</jstl:if>

</div>