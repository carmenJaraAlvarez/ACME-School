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
	<form:form action="extracurricularActivity/parent/edit.do"
		modelAttribute="extracurricularActivity">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="parentsGroup" />

		<acme:textbox code="extracurrAct.title" path="title" />
		<acme:textbox code="extracurrAct.description" path="description" />

		<br />

		<acme:submit name="save" code="extracurrAct.save" />
		<jstl:if test="${extracurricularActivity.id != 0}">
			<input type="submit" name="delete" class="btn btn-primary"
				value="<spring:message code="extracurrAct.delete" />"
				onclick="return confirm('<spring:message code="extracurrAct.confirm.delete" />')" />
		</jstl:if>
		<acme:cancel code="extracurrAct.cancel"
			url="extracurricularActivity/parent/list.do?parentsGroupId=${parentsGroupId}" />

	</form:form>
</div>