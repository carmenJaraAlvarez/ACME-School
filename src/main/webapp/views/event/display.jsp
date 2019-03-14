
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="styles/map.css" type="text/css">
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${isPrincipal}">
<div class="container-fluid">
	<h2><jstl:out value="${event.description}" /></h2>
	<spring:message code="parentsGroup.message.dateFormat" var="messageDateFormat" />
	<h4><fmt:formatDate pattern="${messageDateFormat}" value="${event.moment}" /></h4>
	<jstl:if test="${location}"><div id="map"></div></jstl:if>
    <script>
      function initMap() {
        var uluru = {lat: <jstl:out value="${event.location.northCoordinate}" />, lng: <jstl:out value="${event.location.eastCoordinate}" />};
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 18,
          center: uluru
        });
        var marker = new google.maps.Marker({
          position: uluru,
          map: map
        });
      }
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAs0XW0PuxunXgIwhrEMGjIOeAm7w_QpRI&callback=initMap">
    </script>
    <br/>
	<acme:cancel code="event.back" url="event/parent/list.do?parentsGroupId=${event.parentsGroup.id}" />

</div>
</jstl:if>
<jstl:if test="${!isPrincipal}">
	<h4><spring:message code="event.error.access" /></h4>
</jstl:if>
