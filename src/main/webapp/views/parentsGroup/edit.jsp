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

<form:form modelAttribute="parentsGroup">

	<acme:textbox code="parentsGroup.name" path="name" />
	<acme:textbox code="parentsGroup.image" path="image" />
	<acme:textbox code="parentsGroup.description" path="description" />
	<br />

	<acme:submit name="save" code="parentsGroup.save" />

<!-- 
	<jstl:if test="">
		<acme:submit name="delete" code="parentsGroup.delete" />
	</jstl:if>
 -->

	<acme:cancel code="parentsGroup.cancel" url="#" />



</form:form>