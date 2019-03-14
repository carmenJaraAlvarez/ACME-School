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
<form:form action="classTime/parent/edit.do" modelAttribute="classTimeForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="classGroup"/>
	<form:hidden path="parentsGroupId"/>
	

	

	<form:label path="day"><spring:message code="classTime.day" /></form:label>
<form:select path="day" class="form-control" cssStyle="max-width:800px;" >
		
 <option value="">---</option> 
	<jstl:if test="${classTimeForm.day==1}">	<jstl:set value="selected" var="s1" /></jstl:if>
	<jstl:if test="${classTimeForm.day==2}">	<jstl:set value="selected" var="s2" /></jstl:if>
	<jstl:if test="${classTimeForm.day==3}">	<jstl:set value="selected" var="s3" /></jstl:if>
	<jstl:if test="${classTimeForm.day==4}">	<jstl:set value="selected" var="s4" /></jstl:if>
	<jstl:if test="${classTimeForm.day==5}">	<jstl:set value="selected" var="s5" /></jstl:if>
	
  <option value="1" ${s1 }><spring:message code="classTime.day.1"/></option>
  <option value="2" ${s2 }><spring:message code="classTime.day.2"/></option>
  <option value="3" ${s3 }><spring:message code="classTime.day.3"/></option>
  <option value="4" ${s4 }><spring:message code="classTime.day.4"/></option>
   <option value="5"${s5 }><spring:message code="classTime.day.5"/></option>
</form:select>
<form:errors cssClass="error" path="day" />
	<br>
	<acme:textbox code="classTime.hour" path="hour" placeholder="HH:mm-HH:mm"/>
	<acme:select code="classTime.subject" path="subject"
		items="${subjects}" itemLabel="name" />
	<br />

	<acme:submit name="save" code="classTime.save" />
	<jstl:if test="${classTimeForm.id!=0 }">
	<acme:submit name="delete" code="classTime.delete" />
	</jstl:if>
	<acme:cancel code="classTime.cancel" url="/classTime/parent/list.do?classGroupId=${classTimeForm.classGroup.id}&&parentsGroupId=${classTimeForm.parentsGroupId }" />

</form:form>
</div>