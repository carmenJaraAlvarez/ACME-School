<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
 
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" 
  uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
 
<display:table pagesize="2" class="table table-striped table-bordered"  
  name="all" requestURI="message/parent/files.do" id="rowFile">  
  
  <display:column  titleKey="message.name" sortable="false" >  
  <jstl:out value="${rowFile.name}" /> 
  </display:column> 
  
  <display:column titleKey="message.delete">  
    <div>  
      <a href="message/parent/delete.do?fileUploadId=${rowFile.id}"> 
        <spring:message code="message.delete" />  
      </a>  
    </div>  
  </display:column>  
   <display:column titleKey="message.download">  
    <div>  
      <a href="message/parent/download.do?fileUploadId=${rowFile.id}"> 
        <spring:message code="message.download" />  
      </a>  
    </div>  
  </display:column>  
  </display:table>