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
<h3>
<spring:message code="parents.admin" />
</h3>
</br>
<display:table pagesize="5" class="displaytag" name="parentsAdmin"
	requestURI="parent/parent/list.do" id="rowParentAdmin"> 

	<display:column titleKey="actor.name" sortable="false">
		<jstl:out value="${rowParentAdmin.name}" />
	</display:column>
	<display:column titleKey="actor.surname" sortable="false">
		<jstl:out value="${rowParentAdmin.surname}" />
	</display:column>
	<display:column>
		<div>
			  <a href="parent/parent/deleteAdmin.do?actorId=${rowParentAdmin.id}&&parentsGroupId=${parentsGroupId}"> <spring:message
					code="parent.deleteAdmin" />
			</a>
		</div>
	</display:column>
	<display:column>
		<div>
			 <a href="parent/parent/removeAdmin.do?actorId=${rowParentAdmin.id}&&parentsGroupId=${parentsGroupId}"> <spring:message
					code="parent.deleteOfGroup" />
			</a>
		</div>
	</display:column>
</display:table>
</br>

<h3>
<spring:message code="parents.member" />
</h3>
</br>
<display:table pagesize="5" class="displaytag" name="parentsMember"
	 requestURI="parent/parent/list.do" id="rowParentMember"> 

	<display:column titleKey="actor.name" sortable="false">
		<jstl:out value="${rowParentMember.name}" />
	</display:column>
	<display:column titleKey="actor.surname" sortable="false">
		<jstl:out value="${rowParentMember.surname}" />
	</display:column>
	<display:column>
		<div>
			 <a href="parent/parent/addAdmin.do?actorId=${rowParentMember.id}&&parentsGroupId=${parentsGroupId}"> <spring:message
					code="parent.AddAdmin" />
			</a>
		</div>
	</display:column>
	<display:column>
		<div>
			 <a href="parent/parent/removeMember.do?actorId=${rowParentMember.id}&&parentsGroupId=${parentsGroupId}"> <spring:message
					code="parent.deleteOfGroup" />
			</a>
		</div>
	</display:column>
</display:table>






	

	


