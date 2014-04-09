<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>				
				
<div class="tabbable">
	<jsp:include page="/common/adminTabs.jsp" />
	<div class="tab-content">
		<div class="tab-pane active" id="catalogue-section">
			<div class="sm-ui-component">
				<h3><s:message code="label.default.margin.title" text="Party + Item Wise Default" /></h3>	
				<br/>
				<!-- Listing grid include -->
				<c:set value="/admin/supplier/partyitemdefaultmarginpaging.html" var="pagingUrl" scope="request"/>
				<c:set value="/admin/supplier/deletepartyitemdefaultmargin.html" var="removeUrl" scope="request"/>
				<c:set value="/admin/supplier/editpartyitemdefaultmargin.html" var="editUrl" scope="request"/>
				<c:set value="/admin/supplier/partyitemdefaultmargins.html" var="afterRemoveUrl" scope="request"/>
				<c:set var="entityId" value="partyItemDefaultMarginId" scope="request"/>
				<c:set var="componentTitleKey" value="label.default.margin.title" scope="request"/>
				<c:set var="gridHeader" value="/pages/admin/supplier/defaultMargins-gridHeader.jsp" scope="request"/>
				<c:set var="canRemoveEntry" value="true" scope="request"/>
				
				<jsp:include page="/pages/admin/components/list.jsp"></jsp:include> 
				<!-- End listing grid include -->
			  			     
			</div>
		</div>
	</div>
</div>		      			     