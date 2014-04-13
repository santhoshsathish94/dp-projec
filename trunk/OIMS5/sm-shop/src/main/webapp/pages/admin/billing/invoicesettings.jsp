<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>				

<div class="tabbable">
	<div class="tab-content" style="float: left;" >
		<div class="tab-pane active" id="catalogue-section">
			<div class="sm-ui-component" style="float: left;" >
				<c:if test="${listType == 1}">
					<c:set var="componentTitleKey" value="label.billing.sales.invoice.grid.sales.title" scope="request"/>
				</c:if>
				<c:if test="${listType == 2}">
					<c:set var="componentTitleKey" value="label.billing.sales.invoice.grid.cash.title" scope="request"/>
				</c:if>
				<br/>
				<!-- Listing grid include -->
				<c:set value="/admin/billing/paging.html" var="pagingUrl" scope="request"/>
				
				<c:set var="componentTitleKey" value="label.available.sales.invoice.title" scope="request"/>
				
				<c:set var="gridHeader" value="/pages/admin/billing/invoicesettings-gridHeader.jsp" scope="request"/>
				<c:set var="canRemoveEntry" value="false" scope="request"/>
				<c:set var="groupByEntity" value="invoiceType" scope="request"/>
				
				<jsp:include page="/pages/admin/components/list.jsp" flush="false"></jsp:include> 
				<!-- End listing grid include -->
			  			     
			</div>
		</div>
	</div>
</div>