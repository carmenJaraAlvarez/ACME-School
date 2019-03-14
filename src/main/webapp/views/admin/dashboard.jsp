<%--
 * action-1.jsp
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

	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.ammd" />
		<spring:message code="administrator.dashboard.tutorsGroup" />
	</h3>

	<table style="max-width: 800px" class="table table-hover table-bordered">
		<tr>
			<th><spring:message code="administrator.dashboard.min" /></th>
			<th><jstl:out value="${minTutorsGroup}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.max" /></th>
			<th><jstl:out value="${maxTutorsGroup}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.avg" /></th>
			<th><jstl:out value="${avgTutorsGroup}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.stdev" /></th>
			<th><jstl:out value="${stdevTutorsGroup}" /></th>
		</tr>
	</table>

	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.ammd" />
		<spring:message code="administrator.dashboard.eventsGroup" />
	</h3>

	<table style="max-width: 800px" class="table table-hover table-bordered">
		<tr>
			<th><spring:message code="administrator.dashboard.min" /></th>
			<th><jstl:out value="${minEventsGroup}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.max" /></th>
			<th><jstl:out value="${maxEventsGroup}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.avg" /></th>
			<th><jstl:out value="${avgEventsGroup}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.stdev" /></th>
			<th><jstl:out value="${stdevEventsGroup}" /></th>
		</tr>
	</table>

	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.ammd" />
		<spring:message code="administrator.dashboard.homeSubject" />
	</h3>

	<table style="max-width: 800px" class="table table-hover table-bordered">
		<tr>
			<th><spring:message code="administrator.dashboard.min" /></th>
			<th><jstl:out value="${minHomeSubject}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.max" /></th>
			<th><jstl:out value="${maxHomeSubject}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.avg" /></th>
			<th><jstl:out value="${avgHomeSubject}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.stdev" /></th>
			<th><jstl:out value="${stdevHomeSubject}" /></th>
		</tr>
	</table>

	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.mostTutorsGroup" />
	</h3>

	<display:table style="max-width: 800px" pagesize="3" class="table table-striped table-bordered"
		keepStatus="true" name="groupMostTutors"
		requestURI="${requestURI}#groupTutors" id="rowGrTutors">

		<display:column property="name" titleKey="parentsGroup.name" />
		<display:column property="description"
			titleKey="parentsGroup.description" />
	</display:table>

	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.tutorsMostMsgs1Week" />
	</h3>

	<display:table style="max-width: 800px" pagesize="3" class="table table-striped table-bordered"
		keepStatus="true" name="prntsMostMsgsLastWeek"
		requestURI="${requestURI}#prntsLastWeek" id="rowLastWeek">

		<display:column property="name" titleKey="actor.name" />
		<display:column property="surname" titleKey="actor.surname" />
		<display:column property="address" titleKey="actor.address" />
	</display:table>


	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.tutorsMostMsgs2Week" />
	</h3>

	<display:table style="max-width: 800px" pagesize="3" class="table table-striped table-bordered"
		keepStatus="true" name="prntsMostMsgsLast2Week"
		requestURI="${requestURI}#prntsLast2Week" id="rowLast2Week">

		<display:column property="name" titleKey="actor.name" />
		<display:column property="surname" titleKey="actor.surname" />
		<display:column property="address" titleKey="actor.address" />
	</display:table>

	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.adsInLastMonth" />
	</h3>
	<table style="max-width: 800px" class="table table-hover table-bordered">
		<tr>
			<th><jstl:out value="${nAdsLastMonth}%" /></th>
		</tr>
	</table>

	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.passedMarksYear" />
	</h3>
	<table style="max-width: 800px" class="table table-hover table-bordered">
		<tr>
			<th><jstl:out value="${nMarksLastYear}" /></th>
		</tr>
	</table>

	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.ast" />
		<spring:message code="administrator.dashboard.teachersSchool" />
	</h3>
	<table style="max-width: 800px" class="table table-hover table-bordered">
		<tr>
			<th><spring:message code="administrator.dashboard.avg" /></th>
			<th><jstl:out value="${avgTeachersSchool}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.stdev" /></th>
			<th><jstl:out value="${stdevTeachersSchool}" /></th>
		</tr>
	</table>


	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.ast" />
		<spring:message code="administrator.dashboard.studentsClass" />
	</h3>
	<table style="max-width: 800px" class="table table-hover table-bordered">
		<tr>
			<th><spring:message code="administrator.dashboard.avg" /></th>
			<th><jstl:out value="${avgStudentsClass}" /></th>
		</tr>
		<tr>
			<th><spring:message code="administrator.dashboard.stdev" /></th>
			<th><jstl:out value="${stdevStudentsClass}" /></th>
		</tr>
	</table>


	<h3 style="text-decoration: underline;">
		<spring:message code="administrator.dashboard.schoolMostTeachers" />
	</h3>

	<display:table style="max-width: 800px" pagesize="3" class="table table-striped table-bordered"
		keepStatus="true" name="schoolMostTeach"
		requestURI="${requestURI}#schoolMostTeach" id="rowSchoolTeach">

		<display:column property="nameSchool" titleKey="school.nameSchool" />
		<display:column property="address" titleKey="school.address" />
	</display:table>

</div>