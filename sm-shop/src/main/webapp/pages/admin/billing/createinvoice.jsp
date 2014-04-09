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

<script src="<c:url value="/resources/js/admin-common.js" />"></script>

<style>
.ui-autocomplete #customerName {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	max-width: 190px;
}

.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	max-width: 205px;
}
/* IE 6 doesn't support max-height
* we use height instead, but this forces the menu to always be this tall
*/
* html .ui-autocomplete {
	height: 100px;
	width: 205px;
}
.ui-autocomplete-loading {
		background: white url('/ishop/resources/css/bootstrap/themes/base/images/ui-anim_basic_16x16.gif') right center no-repeat;
	}
</style>

<script type="text/javascript">
var productCount = 1;
</script>


<div class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />
	
		<div class="control-group" style="float: left; margin-bottom: 0px;" id="invoiceTypeSelection">
			<div style="float: left; width: 120px;">
				<label style="margin-top: 5px;"><s:message code="label.billing.setting.type" text="Sales Invoice:"/></label>
			</div>
			<div style="float: left;" class="controls">
				<select id="selectInvoiceType" onchange="selectInvoiceType();">
					<option value="SI" selected="selected">Sales Invoice</option>
					<option value="CI">Cash Invoice</option>
				</select> 
			</div>
		</div>
		
	<c:if test="${salesInvoice.id eq null}">
		<div style="float: left; width: 100%; margin-top: 10px;">
			<h3 id="salesInvoiceHeader"><s:message code="label.billing.sales.title" text="Sales Invoice: ${invoiceTypeMap['siNumberSeries']}" /></h3>
			<input type="hidden" id="salesInvoiceId" value="${invoiceTypeMap['siInvoiceSettingId']}">
			<input type="hidden" id="salesInvoiceSeries" value="${invoiceTypeMap['siNumberSeries']}">
			<h3 id="cashInvoiceHeader"><s:message code="label.billing.cash.title" text="Cash Invoice: ${invoiceTypeMap['ciNumberSeries']}" /></h3>
			<input type="hidden" id="cashInvoiceId" value="${invoiceTypeMap['ciInvoiceSettingId']}">
			<input type="hidden" id="cashInvoiceSeries" value="${invoiceTypeMap['ciNumberSeries']}">
		</div>
		
	</c:if>
	
	<c:if test="${salesInvoice.id ne null}">
		<div style="float: left; width: 100%; margin-top: 10px;">
			<c:if test="${salesInvoice.invoiceSetting.type.code ne null and salesInvoice.invoiceSetting.type.code eq 'SI'}">
				<h3 id="salesInvoiceHeader"><s:message code="label.billing.sales.title" text="Sales Invoice: ${salesInvoice.invoiceSeries}" /></h3>
				<input type="hidden" id="salesInvoiceId" value="${salesInvoice.invoiceSetting.id}">
				<input type="hidden" id="salesInvoiceSeries" value="${salesInvoice.invoiceSeries}">
			</c:if>
			<c:if test="${salesInvoice.invoiceSetting.type.code ne null and salesInvoice.invoiceSetting.type.code eq 'CI'}">
				<h3 id="cashInvoiceHeader"><s:message code="label.billing.cash.title" text="Cash Invoice: ${salesInvoice.invoiceSeries}" /></h3>
				<input type="hidden" id="cashInvoiceId" value="${salesInvoice.invoiceSetting.id}">
				<input type="hidden" id="cashInvoiceSeries" value="${salesInvoice.invoiceSeries}">
			</c:if>
		</div>
	</c:if>
	
	<br/>
	
	<c:url var="saveInvoiceHTML" value="/admin/billing/saveinvoice.html"/>
	
	<div style="float: left; width: 100%; margin-top: 10px;">
		<form:form method="POST" commandName="salesInvoice" action="${saveInvoiceHTML}">
			
			<form:errors path="*" cssClass="alert alert-error" element="div" />
			
			<form:hidden path="invoiceSetting.id" id="invoiceSetting"/>
			<form:hidden path="invoiceSeries"/>
			
			<div id="salesInvoice.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>">
				<s:message code="message.success" text="Request successfull"/>
			</div>
			
			<div id="info">
				<div style="width: 100%; float: left;">
					<div style="width: 195px; float: left;" id="customerDiv">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.prefix" text="Customer"/></label>
							<div style="float: left;" class="controls">
								<input class="input-large highlight" id="customerName" style="width: 185px;" value="${salesInvoice.customer.company}"/>
								<form:input type="hidden" path="customer.id" id="customer" value=""/>
							</div>
						</div>
					</div>
					<div style="width: 325px; float: left;" id="addressDiv">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.prefix" text="To, Address:"/></label>
							<div style="float: left;" class="controls">
								<form:textarea cssClass="input-large highlight" path="address" cssStyle="width: 315px;"/>
							</div>
						</div>
					</div>
					<div style="width: 165px; float: left; margin-left: 25px;" id="receiptModeDiv">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.prefix" text="Receipt Mode"/></label>
							<div style="float: left;" class="controls">
								<select id="receiptMode" name="receiptMode" class="input-large highlight" style="width: 165px;">
									<option value="Cash">Cash</option>
									<option value="Credit">Credit</option>
									<option value="Credit Card">Credit Card</option>
									<option value="Multi_Payment">Multi Payment</option>
								</select>
								<%-- <form:select cssClass="input-large highlight" path="receiptMode" cssStyle="width: 165px;"/> --%>
							</div>
						</div>
					</div>
					<div style="width: 105px; float: left; margin-left: 25px;">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.prefix" text="Date"/></label>
							<div style="float: left;" class="controls">
								<form:input cssClass="input-large highlight" path="invoiceDate" cssStyle="width: 95px;" data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>" data-datepicker="datepicker"/>
								<script type="text/javascript">
									$('#invoiceDate').datepicker();
								</script>
							</div>
						</div>
					</div>
					<div style="width: 105px; float: left; margin-left: 25px;" id="dueDateDiv">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.prefix" text="Due Date"/></label>
							<div style="float: left;" class="controls">
								<form:input cssClass="input-large" path="tempDueDate" cssStyle="width: 95px;" data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>" data-datepicker="datepicker"/>
								<script type="text/javascript">
									$('#tempDueDate').datepicker();
								</script>
							</div>
						</div>
					</div>
					<div style="width: 165px; float: left; margin-left: 25px;" id="refNoDiv">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.prefix" text="Ref. No.:(Optional)"/></label>
							<div style="float: left;" class="controls">
								<form:input cssClass="input-large" path="order.id" cssStyle="width: 155px;"/>
							</div>
						</div>
					</div>
					
					<div style="width: 190px; float: left; margin-left: 25px;">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.prefix" text="Comments:"/></label>
							<div style="float: left;" class="controls">
								<form:textarea cssClass="input-large" path="commant" cssStyle="width: 190px"/>
							</div>
						</div>
					</div>
					
				</div>
				<div style="width: 100%; float: left;">
					<span style="float: right; cursor: pointer; font-weight: bold; text-decoration: underline; color: #0431B4;" onclick="addNewProduct();">Add New List</span>
				</div>
				<jsp:include page="/pages/admin/billing/productInfo.jsp" />
				
			</div>
		
			<form:hidden path="id"/>
			<form:hidden path="productJson"/>
			
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
	       			<button type="submit" class="btn btn-success" onclick="return getProductJSONBeforeSave();"><s:message code="button.label.submit2" text="Save"/></button>
	       		</div>
	  	 	</div>
			
		</form:form>
	</div>
</div>

<script type="text/javascript">

	if(${salesInvoice.id ne null}) {
		
		$('#invoiceTypeSelection').hide();
		
		if(${salesInvoice.invoiceSetting.type.code ne null and salesInvoice.invoiceSetting.type.code eq 'SI'}) {
			$('#selectInvoiceType').val('SI');	
		} else {
			$('#selectInvoiceType').val('CI');
		}
	}

	selectInvoiceType();
	onLoadSalesInvoice();
</script>