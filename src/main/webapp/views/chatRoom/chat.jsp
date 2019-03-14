<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="styles/messages.css" type="text/css">
<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">

	<jstl:if test="${!errorOccurred}">

		<jstl:choose>
			<jstl:when test="${notificated}">
				<div class="verde">
					<spring:message code="chatRoom.notificated" />
				</div>
				<br/>
			</jstl:when>
		</jstl:choose>
		<div class=container>
			<p class="chatTitle">
				<spring:message code="parentsGroup.chat" />
			</p>
			<jstl:if test="${moreMessages}">
				<a class="moreMessages"
					href="${requestURI}&messagesNumber=${messagesNumber}"> <spring:message
						code="parentsGroup.moreMessages" />
				</a>
			</jstl:if>
			<jstl:forEach items="${messages}" var="message">
				<jstl:if test="${!(message.student eq studentLogged) }">
					<div class="groupMessage1">
						<div class="itemMessage">
							<div class="m_item1">
								<p class="parentName">
									<jstl:out value="${message.student.name}" />
								</p>
							</div>
							<div class="m_item2">
								<p class="messageMoment">
									<spring:message code="parentsGroup.message.dateFormat"
										var="messageDateFormat" />
									<fmt:formatDate pattern="${messageDateFormat}"
										value="${message.moment}" />
								</p>
							</div>
						</div>
						<div class="itemMessage">
							<div class="m_item4">
								<p class="messageBody">
									<jstl:out value="${message.body}" />
								</p>
							</div>
						</div>
					</div>
				</jstl:if>
				<jstl:if test="${message.student eq studentLogged }">
					<div class="groupMessage2">
						<div class="itemMessage">
							<div class="m_item5">
								<p class="parentName">
									<jstl:out value="${message.student.name}" />
								</p>
							</div>
							<div class="m_item6">
								<p class="messageMoment">
									<spring:message code="parentsGroup.message.dateFormat"
										var="messageDateFormat" />
									<fmt:formatDate pattern="${messageDateFormat}"
										value="${message.moment}" />
								</p>
							</div>
						</div>
						<div class="itemMessage">
							<div class="m_item7">
								<p class="messageBody">
									<jstl:out value="${message.body}" />
								</p>
							</div>
						</div>
					</div>
				</jstl:if>
			</jstl:forEach>
		</div>

		<form:form cssClass="container2" id="FormularioChat"
			action="${requestURI}&messagesNumber=${messagesNumber-incremento}#FormularioChat"
			modelAttribute="newMessage">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<div class=container3>
				<form:input cssClass="addMessage" path="body" />
				<acme:submit cssClass="send" name="save" code="parentsGroup.send" />
			</div>
			<div>
				<form:errors cssClass="error" path="body" />
			</div>
		</form:form>

		<a href="chatRoom/student/help.do?chatRoomId=${chatRoom.id}&english=<spring:message code="chatRoom.english" />"> <img src="data:image/png;base64,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"
		width="100px" height="50px"/>
		</a>


	</jstl:if>
	<jstl:if test="${errorOccurred}">
		<p>
			<b><spring:message code="parentsGroup.errorOccurred" /></b>
		</p>
	</jstl:if>
</div>
