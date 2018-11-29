
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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form action="${uri}" method="get">
	<h3 style="margin-bottom:0px;"><spring:message code="searcher.select" /></h3>
	
	<label for="radioParentsGroup"><spring:message code="searcher.parentsGroups" /></label>
	<input id="radioParentsGroup" type="radio" name="search" value="parentsGroup" <jstl:if test="${!(search eq 'school')}">checked</jstl:if>>
	
	<label for="radioSchool"><spring:message code="searcher.schools" /></label>
	<input id="radioSchool" type="radio" name="search" value="school" <jstl:if test="${search eq 'school'}">checked</jstl:if>><br>

	<h3 style="margin-bottom:0px;"><spring:message code="searcher.keyWord" /></h3>
	<em><spring:message code="searcher.keyWord.note" /></em><br/>
	<input type="text" name="keyWord" value="${keyWord}">

	<acme:submit name="doSearch" code="searcher.search"/>

	<input type="button" name="clear"
		value="<spring:message code="searcher.clear" />"
		onclick="javascript: relativeRedir('/searcher/display.do');" />
	<br />

</form>

<jstl:if test="${search eq 'parentsGroup'}">
	<h2 style="margin-bottom:0px;"><spring:message code="searcher.parentsGroups.found" /></h2>
	<display:table pagesize="5" class="displaytag" keepStatus="true"
		name="parentsGroups" requestURI="${uri}" id="rowSearchParentsGroup">
	
		<display:column titleKey="searcher.parentsGroup.name" >
			<a href="parentsGroup/display.do?parentsGroupId=${rowSearchParentsGroup.id}">
				<jstl:out value="${rowSearchParentsGroup.name }"/>
			</a>
		</display:column>
		<display:column titleKey="searcher.parentsGroup.description" >
			<jstl:out value="${rowSearchParentsGroup.description }"/>
		</display:column>
		
		<display:column titleKey="searcher.parentsGroup.creator">
			<a href="actor/display.do?actorId=${rowSearchParentsGroup.creator.id}">
				<jstl:out value="${rowSearchParentsGroup.creator.userAccount.username }"/>
			</a>
		</display:column>
	</display:table>
</jstl:if>
<jstl:if test="${search eq 'school'}">
	<h2 style="margin-bottom:0px;"><spring:message code="searcher.schools.found" /></h2>
	<display:table pagesize="5" class="displaytag" keepStatus="true"
		name="schools" requestURI="${uri}" id="rowSearchSchool">
	
		<display:column titleKey="searcher.school.nameSchool">
			<a href="school/display.do?schoolId=${rowSearchSchool.id}">
				<jstl:out value="${rowSearchSchool.nameSchool}" />
			</a>
		</display:column>
		
		<display:column titleKey="searcher.school.emailSchool">
			<jstl:out value="${rowSearchSchool.emailSchool}" />				
		</display:column>
		<display:column titleKey="searcher.school.teachers">
			${fn:length(rowSearchSchool.teachers)}
		</display:column>
	
	</display:table>
</jstl:if>



