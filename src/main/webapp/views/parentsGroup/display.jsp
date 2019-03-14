<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="styles/messages.css" type="text/css">
<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<div class="container-fluid">

	<jstl:if test="${!errorOccurred}">
	
		<div style="float: right;">
			<a
				href="mailto:?subject=Acme-School&body=El%20codigo%20es%20${parentsGroup.code}%20https://www.acme.com:8443/inscription/parent/createInscription.do?parentsGroupId=${parentsGroup.id}"><img
				src="data:image/png;base64,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"
				width="40px" height="40px" /> <spring:message code="share.parent" /><br/> 
			</a>
			<a
				href="mailto:?subject=Acme-School&body=El%20codigo%20es%20${parentsGroup.code}%20https://www.acme.com:8443/inscription/teacher/createInscription.do?parentsGroupId=${parentsGroup.id}"><img
				src="data:image/png;base64,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"
				width="40px" height="40px" /> <spring:message code="share.teacher" /><br/> 
	
			</a>
			<a
				href="parentsGroup/parent/invite/teacher.do?parentsGroupId=${parentsGroup.id}&english=<spring:message code="parentsGroup.language.english" />"><img
				src="images/move.png" style="margin:3px; margin-right:0px;"
				width="37px" height="33px" /> <spring:message code="share.teacher.registered" /><br/> 
			</a>
		</div>
		
		<div>
			<br/>
			<p><b><spring:message code="parentsGroup.name" /></b>:
			<jstl:out value="${parentsGroup.name}" /></p>
			<p><b><spring:message code="parentsGroup.description" /></b>:
			<jstl:out value="${parentsGroup.description}" /></p>
			<p><b><spring:message code="parentsGroup.code.code" /></b>:
			<jstl:out value="${parentsGroup.code}" /></p>
		</div>

		<br/>
		<jstl:choose>
			<jstl:when test="${invited}">
				<div class="verde">
					<spring:message code="parentsGroup.invited.teacher" />
				</div>
			</jstl:when>
		</jstl:choose>
		<br/>
	
		<ul class="nav nav-tabs">
			<li class="active"><a href="parentsGroup/parent/display.do?parentsGroupId=${parentsGroup.id}"><spring:message code="parentsGroup.chat" /></a></li>
			<li><a href="event/parent/list.do?parentsGroupId=${parentsGroup.id}"><spring:message code="parentsGroup.events" /></a></li>
			<li><a href="parent/parent/list.do?parentsGroupId=${parentsGroup.id}"><spring:message code="parentsOfGroup.list" /></a></li>
			<li><a href="extracurricularActivity/parent/list.do?parentsGroupId=${parentsGroup.id}"><spring:message code="parentsGroup.extracurricular" /></a></li>
			<li><a href="homework/parent/list.do?parentsGroupId=${parentsGroup.id}"><spring:message code="parentsGroup.homeworks" /></a></li>
			<li><a href="schoolCalendar/parent/display.do?parentsGroupId=${parentsGroup.id}"><spring:message code="parentsGroup.calendar" /></a></li>
			<li><a href="classTime/parent/list.do?classGroupId=${parentsGroup.classGroup.id}&&parentsGroupId=${parentsGroup.id}"><spring:message code="parentsGroup.schedule" /></a></li>
		</ul>
			
	
	
			<br />
			<div class=container>
				<p class="chatTitle" id="startChat">
					<spring:message code="parentsGroup.chat" />
				</p>
				<jstl:if test="${moreMessages}">
					<a class="moreMessages"
						href="${requestURI}&messagesNumber=${messagesNumber}#startChat"> <spring:message
							code="parentsGroup.moreMessages" />
					</a>
				</jstl:if>
				<jstl:forEach items="${messages}" var="message">
					<jstl:if test="${!(message.parent eq parentLogged) }">
						<jstl:if test="${message.parent == null}">
							<div class="groupMessage3">
								<div class="itemMessage">
									<div class="m_item1">
										<p class="parentName">
											<jstl:forEach varStatus="i" var="splt" items="${fn:split(message.body,',')}">
												<jstl:if test="${i.index == 0}">
													<jstl:set var="bodyTeacherId" value="${splt}" />
												</jstl:if>
												<jstl:if test="${i.index == 1}">
													<jstl:set var="bodyTeacherName" value="${splt}" />
												</jstl:if>
												<jstl:if test="${i.index == 2}">
													<jstl:set var="bodyEntryId" value="${splt}" />
												</jstl:if>
											</jstl:forEach>
											<spring:message code="parentsGroup.message.teacher" /> 
											<a href="actor/display.do?actorId=<jstl:out value='${bodyTeacherId}'/>"> <em><jstl:out value="${bodyTeacherName}"/> </em></a>
											<spring:message code="parentsGroup.message.entry" />
											<a href="entry/display.do?entryId=<jstl:out value='${bodyEntryId}'/>"><spring:message code="parentsGroup.message.here" /></a>
											
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
							</div>
						</jstl:if>
						<jstl:if test="${message.parent != null}">
							<div class="groupMessage1">
								<div class="itemMessage">
									<div class="m_item1">
										<p class="parentName">
											<jstl:out value="${message.parent.name}" />
										</p>
									</div>
									<div class="m_item2">
										<p class="messageMoment">
											<spring:message code="parentsGroup.message.dateFormat" var="messageDateFormat" />
											<fmt:formatDate pattern="${messageDateFormat}" value="${message.moment}" />
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
								<jstl:if test="${message.attachment != null}">
									<div class="itemMessage">
										<div class="m_item3">
											<p class="messageBody">
										    	<a href="parentsGroup/parent/download.do?messageId=${message.id}"> 
		        									<spring:message code="parentsGroup.attachment.download" /> <jstl:out value="${message.attachment.name}"/> 
		      									</a>  
			      							</p>
										</div>
									</div>
								</jstl:if>
							</div>
						</jstl:if>
					</jstl:if>
					<jstl:if test="${message.parent eq parentLogged }">
						<div class="groupMessage2">
							<div class="itemMessage">
								<div class="m_item5">
									<p class="parentName">
										<jstl:out value="${message.parent.name}" />
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
							<jstl:if test="${message.attachment != null}">
								<div class="itemMessage">
									<div class="m_item8">
										<p class="messageBody">
										    	<a href="parentsGroup/parent/download.do?messageId=${message.id}&parentsGroupId=${parentsGroup.id}"> 
		        									<spring:message code="parentsGroup.attachment.download" /> <jstl:out value="${message.attachment.name}"/> 
		      									</a>  
		      							</p>
									</div>
								</div>
							</jstl:if>
						</div>
					</jstl:if>
				</jstl:forEach>
			</div>
	
			<form:form id="FormularioChat" cssClass="container2" accept-charset="UTF-8" action="${requestURI}&messagesNumber=${messagesNumber-incremento}#FormularioChat" enctype="multipart/form-data" modelAttribute="messageForm">
	
				<form:hidden path="messageId" />
				<form:hidden path="messageVersion" />
				<div class=container3>
					<form:input cssClass="addMessage" path="body" />
					<acme:submit cssClass="send" name="save" code="parentsGroup.send" />
				</div>
				<div class=container3>
	            	<form:input cssClass="inputFile" type="file" path="file" id="messageFile"/>
	            </div>
	            <span style="padding-left:10px;"><spring:message code="maximum.size"></spring:message></span>
	<!--             <div class="has-error"> -->
	<%--                 <form:errors path="file" class="help-inline"/> --%>
	<!--             </div> -->
				<div>
					<form:errors cssClass="error" path="body" />
				</div>
			</form:form>

	</jstl:if>
	<jstl:if test="${errorOccurred}">
		<p>
			<b><spring:message code="parentsGroup.errorOccurred" /></b>
		</p>
	</jstl:if>
</div>
