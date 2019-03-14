
<!--  * display.jsp -->
<!--  * -->
<!--  * Copyright (C) 2017 Universidad de Sevilla -->
<!--  *  -->
<!--  * The use of this project is hereby constrained to the conditions of the  -->
<!--  * TDG Licence, a copy of which you may download from  -->
<!--  * http://www.tdg-seville.info/License.html -->


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="styles/entry.css" type="text/css">
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="container-fluid">
<jstl:choose>
	<jstl:when test="${actorNull}">
		<p><spring:message code="actor.error.null" /></p>
	</jstl:when>
	<jstl:when test="${entryNull}">
		<p><spring:message code="actor.entry.null" /></p>
	</jstl:when>
	<jstl:otherwise>
		<table class="">
			<tr>
				<th><spring:message code="actor.name" /></th>
				<td><jstl:out value="${actor.name}" /></td>
			</tr>
		
			<tr>
				<th><spring:message code="actor.surname" /></th>
				<td><jstl:out value="${actor.surname}" /></td>
			</tr>
			<tr>
				<th><spring:message code="actor.address" /></th>
				<td><jstl:out value="${actor.address}" /></td>
			</tr>
			<tr>
				<th><spring:message code="actor.email" /></th>
				<td><jstl:out value="${actor.email}" /></td>
			</tr>
			<tr>
				<th><spring:message code="actor.phoneNumber" /></th>
				<td><jstl:out value="${actor.phoneNumber}" /></td>
			</tr>
			<jstl:if test="${actorType eq 'student'}">
				<tr>
					<th><spring:message code="student.comment" /></th>
					<td><jstl:out value="${actor.comment}" /></td>
				</tr>
				<tr>
					<th><spring:message code="student.classGroup" /></th>
					<td><jstl:out value="${actor.classGroup.name}" /></td>
				</tr>
			</jstl:if>
			
			<jstl:if test="${actorType eq 'teacher'}">
				<tr>
					<th><spring:message code="teacher.school" /></th>
					<td><jstl:out value="${actor.school.nameSchool}" /></td>
				</tr>
			</jstl:if>
			<jstl:if test="${isMyProfile}">
				<jstl:if test="${actorType eq 'parent'}">
		
					<display:table pagesize="5" class="table table-striped table-bordered"
						name="${actor.students}" requestURI="actor/myDisplay.do"
						id="rowParentStudent">
		
						<display:column titleKey="actor.name" sortable="false">
							<jstl:out value="${rowParentStudent.name}" />
						</display:column>
						<display:column titleKey="actor.surname" sortable="false">
							<jstl:out value="${rowParentStudent.surname}" />
						</display:column>
						<display:column titleKey="student.edit" sortable="false">
							<a href="student/edit.do?actorId=${rowParentStudent.id}"> <spring:message
									code="student.edit" />
							</a>
						</display:column>
						<display:column titleKey="parents.marks" sortable="false">
							<a href="mark/parent/list.do?studentId=${rowParentStudent.id}"> <spring:message
									code="parents.marks" />
							</a>
						</display:column>
		
					</display:table>
				</jstl:if>
			</jstl:if>
		
			<jstl:if test="${actorType eq 'agent'}">
				<tr>
					<th><spring:message code="agent.taxCode" /></th>
					<td><jstl:out value="${actor.taxCode}" /></td>
				</tr>
			</jstl:if>
		</table>
		
		
		<security:authorize access="isAuthenticated()">
			<jstl:if test="${isMyProfile}">
				<jstl:if test="${actorType eq 'agent' || actorType eq 'teacher'|| actorType eq 'admin'|| actorType eq 'parent'}">
					<div>
						<a href="${actorType}/edit.do"> <spring:message
								code="actor.edit" />
						</a>
					</div>
					<br/>
				</jstl:if>
			</jstl:if>
		</security:authorize>
		
		
		<jstl:if test="${actorType eq 'teacher'}">
			<jstl:choose>
				<jstl:when test="${display}">
					<ul class="nav nav-tabs">
						<li><a href="${uriList}"><spring:message code="teacher.list.entry" /></a></li>
						<li class="active"><a href="${uriEntry}"><spring:message code="teacher.display.entry" /></a></li>
					</ul>	
					<br/>
					<br/>
					<div class="container2">
						<div class="row1">
							<div class="title">
								<p><b><jstl:out value="${entry.title}" /></b></p>
						  	</div>
							<div class="moment">
								<spring:message code="entry.Date.format" var="entryDateFormat" />
								<p><fmt:formatDate pattern="${entryDateFormat}" value="${entry.moment}" /></p>
						  	</div>
						</div>
						<div class="row2">
							<div class="body">
						    	<p><jstl:out value="${entry.body}" /></p>
						  	</div>
						</div>
					</div>
					<br/>
				
				</jstl:when>
				<jstl:otherwise>
					<ul class="nav nav-tabs">
						<li class="active"><a href="${uriList}"><spring:message code="teacher.list.entry" /></a></li>
					</ul>
					<display:table pagesize="4" class="table table-striped table-bordered" name="entries"
						requestURI="${uriList}" id="rowEntry">
					
						<spring:message code="entry.Date.format" var="entryDateFormat" />
						
						<display:column property="moment"  titleKey="entry.moment" format ="{0,date,${entryDateFormat}}"/>
						
						<display:column titleKey="entry.title">
							<jstl:out value="${rowEntry.title}" />
						</display:column>
						
						<display:column titleKey="entry.display">
							<a href="${uriEntryDisplay}${rowEntry.id }">
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
				</jstl:otherwise>
			</jstl:choose>
		
		</jstl:if>
	
	</jstl:otherwise>
</jstl:choose>
</div>