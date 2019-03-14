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
	<spring:message code="homework.Date.format" var="articleDateFormat" />


	<jstl:if test="${showMoreButton}">
		<div>
			<a href="chatRoom/student/list.do"><spring:message
					code="chatRoom.moreChats" /></a>
		</div>
	</jstl:if>

	<display:table pagesize="5" class="table table-striped table-bordered"
		name="chatRooms" requestURI="${uri}" id="rowChatRoom">

		<display:column titleKey="chatRoom.name">
			<jstl:out value="${rowChatRoom.name}" />
		</display:column>

		<display:column titleKey="chatRoom.members">
			<a href="student/student/memberList.do?chatRoomId=${rowChatRoom.id}">
				<spring:message code="chatRoom.members" />
			</a>
		</display:column>

		<jstl:if test="${not showMoreButton}">
			<display:column titleKey="chatRoom.join">
				<a href="chatRoom/student/join.do?chatRoomId=${rowChatRoom.id}" onclick="return confirm('<spring:message code="join.confirm" />')">
					<spring:message code="chatRoom.join" />
				</a>
			</display:column>
		</jstl:if>

		<jstl:if test="${showMoreButton}">
			<display:column titleKey="chatRoom.messages">
				<a href="chatRoom/student/chat.do?chatRoomId=${rowChatRoom.id}">
					<spring:message code="chatRoom.messages" />
				</a>
			</display:column>
			<display:column titleKey="chatRoom.exit">
				<a href="chatRoom/student/exit.do?chatRoomId=${rowChatRoom.id}">
					<spring:message code="chatRoom.exit" />
				</a>
			</display:column>
		</jstl:if>

	</display:table>

	<br />

	<!-- Botón de create -->
	<security:authorize access="hasRole('STUDENT')">
		<a href="chatRoom/student/create.do"> <spring:message
				code="chatRoom.create" />
		</a>
	</security:authorize>
</div>
