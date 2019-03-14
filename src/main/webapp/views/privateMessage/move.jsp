<%--
 * edit.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="styles/privateMessages.css" type="text/css">

<div class="container-fluid">
	<div class="container2">
		<div>
			<h3>
				<spring:message code="message.how" />
			</h3>
		</div>
		<div class="submenuMove">
			<ul class="nav nav-pills">
				<jstl:forEach items="${fathers}" var="primFather">
					<jstl:if test="${primFather.id==newFolder.id}">
						<li class="active" style="border: 1px solid white; border-radius: 4px;">
							<a href="privateMessage/actor/move.do?privateMessageId=${privateMessageToEdit.id}&folderId=${currentFolder.id}&newFolderId=${primFather.id}">
								<jstl:out value="${primFather.name}"/>
							</a>
						</li>
					</jstl:if>
					<jstl:if test="${primFather.id!=newFolder.id}">
						<jstl:if test="${primFather.id==primaryFolder.id}">
							<li style="border: 1px solid black; border-radius: 4px;">
								<a href="privateMessage/actor/move.do?privateMessageId=${privateMessageToEdit.id}&folderId=${currentFolder.id}&newFolderId=${primFather.id}">
									<jstl:out value="${primFather.name}"/>
								</a>
							</li>
						</jstl:if>
						<jstl:if test="${primFather.id!=primaryFolder.id}">
							<li style="border: 1px solid white; border-radius: 4px;">
								<a href="privateMessage/actor/move.do?privateMessageId=${privateMessageToEdit.id}&folderId=${currentFolder.id}&newFolderId=${primFather.id}">
									<jstl:out value="${primFather.name}"/>
								</a>
							</li>
						</jstl:if>
					</jstl:if>
				</jstl:forEach>
			</ul>
		</div>
		<div class="restoMove">
			<div class="sublistaMove list-group">
				<jstl:forEach items="${foldersList}" var="folderOfList">
					<jstl:if test="${folderOfList.id==newFolder.id}">
						<a href="privateMessage/actor/move.do?privateMessageId=${privateMessageToEdit.id}&folderId=${currentFolder.id}&newFolderId=${folderOfList.id}" class="list-group-item active">
							<jstl:out value="${folderOfList.name}"/>
						</a>
						<hr/>
					</jstl:if>
					<jstl:if test="${folderOfList.id!=newFolder.id}">
						<a href="privateMessage/actor/move.do?privateMessageId=${privateMessageToEdit.id}&folderId=${currentFolder.id}&newFolderId=${folderOfList.id}" class="list-group-item">
							<jstl:out value="${folderOfList.name}"/>
						</a>
						<hr/>
					</jstl:if>
				</jstl:forEach>
				<jstl:forEach items="${childFolders}" var="childFolder">
					<jstl:if test="${childFolder.id==newFolder.id}">
						<a href="privateMessage/actor/move.do?privateMessageId=${privateMessageToEdit.id}&folderId=${currentFolder.id}&newFolderId=${childFolder.id}" class="list-group-item active">
							<jstl:out value="${childFolder.name}"/>
						</a>
					</jstl:if>
					<jstl:if test="${childFolder.id!=newFolder.id}">
						<a href="privateMessage/actor/move.do?privateMessageId=${privateMessageToEdit.id}&folderId=${currentFolder.id}&newFolderId=${childFolder.id}" class="list-group-item">
							<jstl:out value="${childFolder.name}"/>
						</a>
					</jstl:if>
				</jstl:forEach>
			</div>
			<div class="moveMessage">
				<jstl:if test="${!isNotificationNewBox}">
					<spring:message code="message.moveToFolder" /><b><em>"<jstl:out value="${newFolder.name}"/></em>"</b>
					<br/><br/>
					<form:form action="${actionUri}" modelAttribute="movePrivateMessageForm">
					
						<form:hidden path="privateMessage" />
						<form:hidden path="currentFolder" />
						<form:hidden path="newFolder" />
					
						<acme:submit name="move" code="folder.message.move"/>
						<acme:cancel url="folder/actor/list.do?folderId=${currentFolder.id}" code="message.cancel"/>
					
					</form:form>
				</jstl:if>
				<jstl:if test="${isNotificationNewBox}">
					<b><spring:message code="message.no.moveToFolder" /></b>
				</jstl:if>
			</div>
		</div>
	</div>
</div>
