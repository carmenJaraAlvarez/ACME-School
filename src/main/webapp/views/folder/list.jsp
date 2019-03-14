<%--
 * list.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<link rel="stylesheet" href="styles/privateMessages.css" type="text/css">
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="container-fluid">
	<div class="container2">
		<jstl:choose>
			<jstl:when test="${newNotification}">
				<div class="verde">
					<spring:message code="folder.message.newNotification" />
				</div>
			</jstl:when>
			<jstl:when test="${delete}">
				<div class="verde">
					<spring:message code="folder.message.delete.complete" />
				</div>
			</jstl:when>
			<jstl:when test="${toTrashBox}">
				<div class="rojo">
					<spring:message code="folder.message.delete.toTrashBox" />
				</div>
			</jstl:when>
			<jstl:when test="${moved}">
				<div class="verde">
					<spring:message code="folder.message.moved" />
				</div>
			</jstl:when>
			<jstl:when test="${stayed}">
				<div class="rojo">
					<spring:message code="folder.message.stayed" />
				</div>
			</jstl:when>
			<jstl:when test="${folderDeleted}">
				<div class="verde">
					<spring:message code="folder.message.folderDeleted" />
				</div>
			</jstl:when>
			<jstl:when test="${folderError}">
				<div class="rojo">
					<spring:message code="folder.commit.error" />
				</div>
			</jstl:when>
		</jstl:choose>
		<div class="submenu">
			<ul class="nav nav-tabs">
			<jstl:forEach items="${fathers}" var="primFather">
				<jstl:if test="${primFather.id==folder.id}">
					<li class="active">
						<a href="folder/actor/list.do?folderId=${primFather.id}">
							<jstl:out value="${primFather.name}"/> <span class="badge"><jstl:out value="${fn:length(primFather.privateMessages)}"/></span>
						</a>
					</li>
				</jstl:if>
				<jstl:if test="${primFather.id!=folder.id}">
					<jstl:if test="${primFather.id==primaryFolder.id}">
						<li class="active">
							<a href="folder/actor/list.do?folderId=${primFather.id}">
								<jstl:out value="${primFather.name}"/> <span class="badge"><jstl:out value="${fn:length(primFather.privateMessages)}"/></span>
							</a>
						</li>
					</jstl:if>
					<jstl:if test="${primFather.id!=primaryFolder.id}">
						<li>
							<a href="folder/actor/list.do?folderId=${primFather.id}">
								<jstl:out value="${primFather.name}"/> <span class="badge"><jstl:out value="${fn:length(primFather.privateMessages)}"/> </span>
							</a>
						</li>
					</jstl:if>
				</jstl:if>
			</jstl:forEach>
			</ul>
		</div>
		<div class="resto">
			<div class="sublista">
				<div class="list-group">
					<jstl:forEach items="${foldersList}" var="folderOfList">
						<jstl:if test="${folderOfList.id==folder.id}">
							<a href="folder/actor/list.do?folderId=${folderOfList.id}" class="list-group-item active">
								<jstl:out value="${folderOfList.name}"/> <span class="badge"><jstl:out value="${fn:length(folderOfList.privateMessages)}"/></span>
							</a>
							<hr/>
						</jstl:if>
						<jstl:if test="${folderOfList.id!=folder.id}">
							<a href="folder/actor/list.do?folderId=${folderOfList.id}" class="list-group-item">
								<jstl:out value="${folderOfList.name}"/> <span class="badge"><jstl:out value="${fn:length(folderOfList.privateMessages)}"/></span>
							</a>
							<hr/>
						</jstl:if>
					</jstl:forEach>
					<jstl:forEach items="${children}" var="childFolder">
						<jstl:if test="${childFolder.id==folder.id}">
							<a href="folder/actor/list.do?folderId=${childFolder.id}" class="list-group-item active">
								<jstl:out value="${childFolder.name}"/> <span class="badge"><jstl:out value="${fn:length(childFolder.privateMessages)}"/></span>
							</a>
						</jstl:if>
						<jstl:if test="${childFolder.id!=folder.id}">
							<a href="folder/actor/list.do?folderId=${childFolder.id}" class="list-group-item">
								<jstl:out value="${childFolder.name}"/> <span class="badge"><jstl:out value="${fn:length(childFolder.privateMessages)}"/></span>
							</a>
						</jstl:if>
					</jstl:forEach>
				</div>
				<div>
					<jstl:if test="${!folder.ofTheSystem}">
						<button class="boton" type="button" onclick="javascript: relativeRedir('folder/actor/edit.do?folderId=${folder.id}')" >
							<spring:message code="folder.edit" />
						</button>
					</jstl:if>
				</div>
				<div>
					<button class="boton" type="button" onclick="javascript: relativeRedir('folder/actor/edit.do?fatherId=${folder.id}')" >
						<spring:message code="folder.create" />
					</button>
				</div>
				<div>
					<button class="boton" type="button" onclick="javascript: relativeRedir('privateMessage/actor/create.do?folderId=${folder.id}')" >
						<spring:message code="folder.message.new" />
					</button>
				</div>
				<security:authorize access="hasRole('ADMIN')">
					<div>
						<button class="boton" type="button" onclick="javascript: relativeRedir('privateMessage/administrator/notification.do?folderId=${folder.id}')" >
							<spring:message code="folder.message.notification" />
						</button>
					</div>
				</security:authorize>
			</div>
			
			<div class="containerMessages">
				<jstl:if test="${displayMessage}">
					<div class="nombreCarpeta" style="padding:0px;">
						<jstl:if test="${!isNotificationBox}">
							<a href="privateMessage/actor/move.do?privateMessageId=${privateMessageId}&folderId=${folder.id}&newFolderId=${folder.id}" class="moverBorrar"><img width="30px" height="25px" alt="" src="images/move.png"> <spring:message code="folder.message.move" /></a>
						</jstl:if>
						<a href="privateMessage/actor/delete.do?privateMessageId=${privateMessageId}&&folderId=${folder.id}" class="moverBorrar" onclick="return confirm('<spring:message code="folder.message.confirm.delete" />')"><img width="25px" height="25px" alt="" src="images/deleteMessage.png"> <spring:message code="folder.delete" /></a>
					</div>
				</jstl:if>
				<jstl:if test="${!displayMessage}">
					<div class="nombreCarpeta">
							<spring:message code="folder.messages.box" /><jstl:out value="${folder.name}"/>
					</div>
				</jstl:if>
				<div class="mensajes">
					<jstl:if test="${displayMessage}">
						<jstl:if test="${!isNotificationBox}">
							<div class="list-group-item">
								<spring:message code="parentsGroup.message.dateFormat" var="privateMessageDateFormat" />
								<span class="fecha"><fmt:formatDate pattern="${privateMessageDateFormat}" value="${privateMessage.sendMoment}" /> </span>
								<div class="textBefore"><spring:message code="folder.message.from" /><span class="textAfter"><jstl:out value="${privateMessage.actorSender.name}"/></span></div>
								<div class="textBefore"><spring:message code="folder.message.subject" /><span class="textAfter"><jstl:out value="${privateMessage.subject}"/></span></div>
								<div class="body">${privateMessage.body}</div>
							</div>
						</jstl:if>
						<jstl:if test="${isNotificationBox}">
							<div class="list-group-item">
								<spring:message code="parentsGroup.message.dateFormat" var="privateMessageDateFormat" />
								<span class="fecha"><fmt:formatDate pattern="${privateMessageDateFormat}" value="${privateMessage.sendMoment}" /></span>
								<div class="textBefore textAfter"><spring:message code="folder.message.display.notification" /></div>
								<div class="textBefore"><spring:message code="folder.message.subject" /><span class="textAfter"><jstl:out value="${privateMessage.subject}"/></span></div>
								<div class="body">${privateMessage.body}</div>
							</div>
						</jstl:if>
					</jstl:if>
					<jstl:if test="${!displayMessage}">
						<jstl:if test="${!isNotificationBox}">
							<jstl:forEach items="${privateMessages}" var="privateMessage">
								<jstl:choose>
									<jstl:when test="${privateMessage.priority eq low}">
										<jstl:set var="priority" value="low" />
									</jstl:when>
									<jstl:when test="${privateMessage.priority eq high}">
										<jstl:set var="priority" value="high" />
									</jstl:when>
									<jstl:when test="${privateMessage.priority eq neutral}">
										<jstl:set var="priority" value="neutral" />
									</jstl:when>
								</jstl:choose>
								<a href="privateMessage/actor/display.do?privateMessageId=${privateMessage.id}&folderId=${folder.id}" class="${priority} list-group-item">
									<spring:message code="folder.message.from" /><jstl:out value="${privateMessage.actorSender.name}"/><br/>
									<spring:message code="folder.message.subject" /><jstl:out value="${privateMessage.subject}"/><br/>
									<spring:message code="parentsGroup.message.dateFormat" var="privateMessageDateFormat" />
									<spring:message code="folder.messages.date" />
									<fmt:formatDate pattern="${privateMessageDateFormat}" value="${privateMessage.sendMoment}" /><br/>
								</a>
							</jstl:forEach>
						</jstl:if>
						<jstl:if test="${isNotificationBox}">
							<jstl:forEach items="${privateMessages}" var="privateMessage">
								<jstl:choose>
									<jstl:when test="${privateMessage.priority eq low}">
										<jstl:set var="priority" value="low" />
									</jstl:when>
									<jstl:when test="${privateMessage.priority eq high}">
										<jstl:set var="priority" value="high" />
									</jstl:when>
									<jstl:when test="${privateMessage.priority eq neutral}">
										<jstl:set var="priority" value="neutral" />
									</jstl:when>
								</jstl:choose>
								<a href="privateMessage/actor/display.do?privateMessageId=${privateMessage.id}&folderId=${folder.id}" class="${priority} list-group-item">
									<spring:message code="parentsGroup.message.dateFormat" var="privateMessageDateFormat" />
									<span class="fecha"><fmt:formatDate pattern="${privateMessageDateFormat}" value="${privateMessage.sendMoment}" /></span>
									<span class="textBefore"><b><jstl:out value="${privateMessage.subject}"/></b></span><br/>
								</a>
							</jstl:forEach>
						</jstl:if>
					</jstl:if>
				</div>
			</div>
		</div>
	</div>
	<br/>
	<spring:message code="folder.messages.info.colors" /><br/>
	<spring:message code="folder.messages.info.colors.yellow" /><br/>
	<spring:message code="folder.messages.info.colors.blue" /><br/>
	<spring:message code="folder.messages.info.colors.white" /><br/>

</div>
