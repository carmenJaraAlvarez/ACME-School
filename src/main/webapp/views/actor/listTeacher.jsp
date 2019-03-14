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
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">

	<br>
	<display:table pagesize="5" class="table table-striped table-bordered" name="teachers" requestURI="teachers/list.do" id="rowTeachersPublic"> 
	
		<display:column titleKey="actor.name" sortable="false">
			<jstl:out value="${rowTeachersPublic.name}" />
		</display:column>
		<display:column titleKey="actor.surname" sortable="false">
			<jstl:out value="${rowTeachersPublic.surname}" />
		</display:column>
		<display:column titleKey="teacher.display">
			<div>
				  <a href="actor/display.do?actorId=${rowTeachersPublic.id}"> <spring:message
						code="teacher.display" />
				</a>
			</div>
		</display:column>
	</display:table>

</div>

	

	


