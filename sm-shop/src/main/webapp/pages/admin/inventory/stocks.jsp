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
			<h3><s:message code="label.inventoryManagement.stock.title" text="Stocks" /></h3>	
			<br/>
			<div class="sm-ui-component">
				<!-- Listing grid include -->
				<c:set value="/admin/inventoryManagement/paging.html" var="pagingUrl" scope="request" />
				<c:set value="/admin/inventoryManagement/remove.html" var="removeUrl" scope="request" />
				<c:set value="/admin/inventoryManagement/createstock.html" var="refreshUrl" scope="request" />
				<c:set var="entityId" value="stockId" scope="request"/>
				<c:set var="componentTitleKey" value="label.inventoryManagement.stock.title" scope="request" />
				<c:set var="canRemoveEntry" value="true" scope="request" />
				<c:set var="gridHeader" value="/pages/admin/inventory/stock-gridHeader.jsp" scope="request"/>
				<jsp:include page="/pages/admin/components/list.jsp"></jsp:include>
				<!-- End listing grid include -->
			</div>
		</div>
		</div>
	</div>
</div>		      			     