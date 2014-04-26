<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/jquery.ui.theme.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/jquery.ui.accordion.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/jquery.ui.menu.css" />">

<script type='text/javascript' src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.bgiframe.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.core.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.widget.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.position.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.menu.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.autocomplete.js" />"></script>

<link href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />" rel="stylesheet"></link>
<script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>

<script src="<c:url value="/resources/js/admin-orders.js" />"></script>
<script>
var productCount = 1;
var ctx = "${pageContext.request.contextPath}";
var taxClassMap = '';
<c:if test="${taxRateMap ne null}">
	taxClassMap = ${taxRateMap};
</c:if>
</script>
<style>
.ui-autocomplete #customerName {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	max-width: 350px;
}

.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	max-width: 350px;
}
/* IE 6 doesn't support max-height
* we use height instead, but this forces the menu to always be this tall
*/
* html .ui-autocomplete {
	height: 100px;
	width: 205px;
}
.ui-autocomplete-loading {
		background: white url('<c:out value="${pageContext.request.contextPath}"/>/resources/css/bootstrap/themes/base/images/ui-anim_basic_16x16.gif') right center no-repeat;
	}
</style>

<div class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />

	<h3><s:message code="label.sales.order.booking.title" text="Sales Order Booking: " /></h3><br/>
	
	<c:url var="saveSalesOrderBookingUrl" value="/admin/orders/savesalesorderbooking.html" />
	
	<div style="float: left; width: 100%; margin-top: 10px;">
	
		<form:form method="POST" commandName="salesOrderBooking" action="${saveSalesOrderBookingUrl}">
			
			<form:errors path="*" cssClass="alert alert-error" element="div" />
				
			<div id="salesInvoice.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>">
				<s:message code="message.success" text="Request successfull"/>
			</div>
			
			<div id="info">
				<div style="width: 100%; float: left;">
					<div style="width: 195px; float: left;">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.prefix" text="Customer"/></label>
							<div style="float: left;" class="controls">
								
								<c:choose>
									<c:when test="${salesOrderBooking.customerId ne null}">
										<input class="input-large highlight" id="customerName" style="width: 185px;" value="${customerName}"/>
									</c:when>
									<c:otherwise>
										<input class="input-large highlight" id="customerName" style="width: 185px;" value=""/>
									</c:otherwise>
								</c:choose>
								<form:input type="hidden" path="customerId" id="customerId" value=""/>
								<%-- <form:input cssClass="input-large highlight" path="customer.company" cssStyle="width: 185px;"/> --%>
							</div>
						</div>
					</div>
					<div style="width: 105px; float: left; margin-left: 25px;">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.prefix" text="Date"/></label>
							<div style="float: left;" class="controls">
								<form:input cssClass="input-large highlight" path="bookingDate" cssStyle="width: 95px;" data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>" data-datepicker="datepicker"/>
								<script type="text/javascript">
									$('#bookingDate').datepicker();
								</script>
							</div>
						</div>
					</div>
					
					<div style="float: left; margin-left: 25px; width: 370px;">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.prefix" text="Comments:"/></label>
							<div style="float: left;" class="controls">
								<form:textarea cssClass="input-large" path="comment" cssStyle="width: 315px;"/>
							</div>
						</div>
					</div>
				</div>
				
				<div style="width: 100%; float: left;">
					<span style="float: right; cursor: pointer; font-weight: bold; text-decoration: underline; color: #0431B4;" onclick="addNewProduct();">Add New Product</span>
				</div>
				
				<jsp:include page="/pages/admin/orders/productInfo.jsp" />
				
			</div>
			
			<form:hidden path="productJson"/>
			<select id="hiddenTaxClass" style="display: none; width: 100px;">
				<option value=""></option>
				<c:forEach items="${taxClasses }" var="taxClass">
					<option value="${taxClass.id}">${taxClass.code}</option>
				</c:forEach>
			</select>
			
			<div style="float: left; width: 100%; margin-top: 10px; font-weight: bold;">
				<span style="float: left; width: 100%; height: 25px;">
					<hr>
				</span>
				<div style="float: left; width: 100%;">
					<span style="float: left; margin-left: 520px;">Sub Total:</span>
					<span style="float: right;" id="subTotalValue">0.00</span>
				</div>
				<span style="float: left; width: 100%; height: 25px;">
					<hr>
				</span>
				<div style="float: left; width: 100%;">
					<span style="float: left; margin-left: 550px;">Total:</span>
					<span style="float: right;" id="totalValue">0.00</span>
				</div>
				<span style="float: left; width: 100%;">
					<hr style="border-color: #000;">
				</span>
			</div>
			
			<div style="float: left; margin-top: 20px;">
	       		<div class="pull-left">
	       			<button type="submit" class="btn btn-success" onclick="return createProductJsonBeforeSave();"><s:message code="button.label.submit2" text="Save"/></button>
	       		</div>
	  	 	</div>
		
		</form:form>
		
	</div>
</div>
<script>
	onLoadSalesOrderBooking();
</script>