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
			    <h3><s:message code="" text="Debit Return Other" /></h3> 
			    <br/>
			    <!-- Listing grid include -->
					<c:set value="/admin/inventoryManagement/purchaseReturnOther/paging.html" var="pagingUrl" scope="request" />
					<c:set value="/admin/inventoryManagement/createdebitnoteother.html" var="refreshUrl" scope="request" />
					<c:set var="entityId" value="purchaseId" scope="request"/>
					<c:set var="componentTitleKey" value="label.inventoryManagement.perchase.return.other.list" scope="request" />
					<c:set var="canRemoveEntry" value="true" scope="request" />
					<c:set var="gridHeader" value="/pages/admin/inventory/perchase-other-returned-gridHeader.jsp" scope="request"/>
					<jsp:include page="/pages/admin/components/list.jsp"></jsp:include>
			    <!-- End listing grid include -->
			      
		   </div>
		</div>
	</div>
</div>		      			     