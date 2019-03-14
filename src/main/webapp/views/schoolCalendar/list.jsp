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

<display:table pagesize="5" class="table table-striped table-bordered"  name="schoolCalendars"
	requestURI="schoolCalendar/list.do" id="rowSchoolCalendar">

	<display:column titleKey="schoolCalendar.image">
		<img src="${rowSchoolCalendar.image}" alt="${rowSchoolCalendar.image}"
			height="60px" width="60px" />
	</display:column>

	<display:column titleKey="schoolCalendar.course">
		<jstl:out value="${rowSchoolCalendar.course }" />
	</display:column>

	<display:column titleKey="schoolCalendar.country">
		<jstl:out value="${rowSchoolCalendar.country }" />
	</display:column>

	<display:column titleKey="schoolCalendar.region">
		<jstl:out value="${rowSchoolCalendar.region }" />
	</display:column>
		<display:column titleKey="schoolCalendar.display">
			<a
					href="schoolCalendar/display.do?schoolCalendarId=${rowSchoolCalendar.id}">
					<spring:message code="schoolCalendar.display" />
				</a>
	</display:column>
<jstl:if test="${hasGroup}">
	<security:authorize access="hasRole('PARENT')">
		<display:column titleKey="schoolCalendar.link">
			<div>
				<a
					href="schoolCalendar/parent/link.do?schoolCalendarId=${rowSchoolCalendar.id}">
					<spring:message code="schoolCalendar.link" />
				</a>
			</div>
		</display:column>
	</security:authorize>
	</jstl:if>


</display:table>
<div>

<!-- Botón de create -->
<security:authorize access="hasRole('ADMIN')">
	<a href="schoolCalendar/administrator/create.do"> <spring:message
			code="schoolCalendar.create" />
	</a>
</security:authorize>
</div>

</div>