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

<script src="<c:url value="/resources/js/admin-creditNote.js" />"></script>

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
var taxClassMap = '';
<c:if test="${taxRateMap ne null}">
	taxClassMap = ${taxRateMap};
</c:if>

</script>


<div class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />
	
	<c:if test="${creditNote.id ne null}">
		<div style="float: left; width: 100%; margin-top: 10px;">
			<h3><s:message code="label.billing.credit.note.edit.title" text="Edit Credit Note" /></h3>
		</div>
	</c:if>
	<c:if test="${creditNote.id eq null}">
		<div style="float: left; width: 100%; margin-top: 10px;">
			<h3><s:message code="label.billing.credit.note.new.title" text="New Credit Note" /></h3>
		</div>
	</c:if>
	<br/>
	
	<c:url var="saveCreditNoteAction" value="/admin/billing/savecreditnote.html"/>
	
	<div style="float: left; width: 100%; margin-top: 10px;">
		<form:form method="POST" commandName="creditNote" action="${saveCreditNoteAction}">
			
			<form:errors path="*" cssClass="alert alert-error" element="div" />
			<div id="creditNote.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>">
				<s:message code="message.success" text="Request successfull"/>
			</div>
			
			<div id="info">
				<div style="width: 100%; float: left;">
					<div style="width: 210px; float: left;">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.credit.note.credit.customer" text="Name: (Credit To)"/></label>
							<div style="float: left;" class="controls">
								<input class="input-large highlight" id="creditCustomerName" style="width: 200px;" value="${creditNote.creditCustomer.billing.company}"/>
								<form:input type="hidden" path="creditCustomer.id" id="creditCustomer" value="${creditCustomer.id}"/>
								<%-- <input class="input-large highlight" id="creditCustomerName" style="width: 200px;" value="${creditCustomer.company}"/> --%>
								<%-- <form:input path="creditCustomer.company"/> --%>
							</div>
						</div>
					</div>
					<div style="width: 105px; float: left; margin-left: 25px;" id="dueDateDiv">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.credit.note.Date" text="Date"/></label>
							<div style="float: left;" class="controls">
								<form:input cssClass="input-large" path="tempNoteDate" cssStyle="width: 95px;" data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>" data-datepicker="datepicker"/>
								<script type="text/javascript">
									$('#tempNoteDate').datepicker();
								</script>
							</div>
						</div>
					</div>
					<div style="width: 165px; float: left; margin-left: 25px;" id="refNoDiv">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.credit.note.order" text="Ref. No.:(Optional)"/></label>
							<div style="float: left;" class="controls">
								<form:input cssClass="input-large" path="order.id" cssStyle="width: 155px;"/>
							</div>
						</div>
					</div>
				</div>
				
				<div style="width: 100%; float: left; margin-top: 20px;">
					<div class="control-group" style="float: left; margin-bottom: 0px;">
                        <div style="float: left;" class="controls">
         					<input type="radio" id="againstProduct" name="creditNoteTypeRadio" checked="checked" value="PRODUCT" onclick="setCreditNoteType('PRODUCT');" style="float: Left;">
                        </div>
                        <div style="float: left; width: 120px; margin-top: -5px; margin-left: 10px;">
	                        <label style="margin-top: 5px;"><s:message code="label.billing.credit.note.type.product" text="Against Product"/></label>
						</div>
					</div>
					<div class="control-group" style="float: left; margin-bottom: 0px;">
                        <div style="float: left;" class="controls">
         					<input type="radio" id="againstOthers" name="creditNoteTypeRadio" value="OTHERS" onclick="setCreditNoteType('OTHERS');" style="float: Left;">
                        </div>
                        <div style="float: left; width: 120px; margin-top: -5px; margin-left: 10px;">
	                        <label style="margin-top: 5px;"><s:message code="label.billing.credit.note.type.other" text="Against Others"/></label>
						</div>
					</div>
					
					<form:hidden path="creditNoteType" />
				</div>
				
				
				
				<div style="width: 100%; float: left; margin-top: 5px; display: none;" id="showOthers">
					<div style="width: 100%; float: left; margin-top: 20px;">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
	                        <div style="float: left; width: 60px; margin-top: -5px; margin-left: 10px;">
		                        <label style="margin-top: 5px;"><s:message code="label.billing.credit.note.type.other.amount" text="Amount"/></label>
							</div>
	                        <div style="float: left;" class="controls">
	         					<form:input path="otherAmount" class="input-large" onchange="calTotalAmountForOthers()" onkeyup="calTotalAmountForOthers()"/>
	                        </div>
						</div>
						<div class="control-group" style="float: left; margin-bottom: 0px; margin-left: 50px;">
	                        <div style="float: left; width: 60px; margin-top: -5px; margin-left: 10px;">
		                        <label style="margin-top: 5px;"><s:message code="label.billing.credit.note.type.other.tax" text="Tax:"/></label>
							</div>
	                        <div style="float: left;" class="controls">
	                        	<form:select class="input-large" items="${taxClasses}" itemLabel="code" itemValue="id" path="taxClassOther.id" id="taxClassOther" onchange="changeOtherTaxAmount(this)"/>
	                        	<input type="hidden" id="otherTaxAmount" value="">
	                        </div>
						</div>
						
					</div>
				</div>
				
				<div style="width: 100%; float: left;" id="showProduct">
					<div style="width: 100%; float: left;">
						<span style="float: right; cursor: pointer; font-weight: bold; text-decoration: underline; color: #0431B4;" onclick="addNewProduct();">Add New Product</span>
					</div>
				
					<jsp:include page="/pages/admin/billing/creditNoteProduct.jsp" />
				</div>
				<div style="width: 100%; float: left;" id="creditTo">
					<div style="width: 210px; float: left;">
						<div class="control-group" style="float: left; margin-bottom: 0px;">
							<label><s:message code="label.billing.credit.note.debit.customer" text="To: (Debit To)"/></label>
							<div style="float: left;" class="controls">
								<input class="input-large highlight" id="debitCustomerName" style="width: 200px;" value="${creditNote.debitCustomer.billing.company}"/>
								<form:input type="hidden" path="debitCustomer.id" id="debitCustomer" value="${debitCustomer.id}"/>
								<%-- <input class="input-large highlight" id="debitCustomerName" style="width: 200px;" value="${debitCustomer.company}"/> --%>
								<%-- <form:input path="debitCustomer.company" value=""/> --%>
							</div>
						</div>
					</div>
				</div>
			</div>
		
			<form:hidden path="id"/>
			<form:hidden path="productJson"/>
			
			<select id="hiddenTaxClass" style="display: none; width: 100px;">
				<option value=""></option>
				<c:forEach items="${taxClasses }" var="taxClass">
					<option value="${taxClass.id}">${taxClass.code}</option>
				</c:forEach>
			</select>
			
			<div style="float: left; width: 100%; margin-top: 20px; font-weight: bold;">
				<div style="float: left; width: 100%;">
					<span style="float: left; margin-left: 550px;"><label><s:message code="label.billing.credit.note.debit.Total" text="Total Credit Note Amount:"/></label></span>
					<span style="float: right; margin-right: 110px;" id="totalValue">0.00</span>
				</div>
			</div>
			
			<div style="float: left; margin-top: 20px;">
	       		<div class="pull-left">
	       			<button type="submit" class="btn btn-success" onclick="return createProductJsonBeforeSave();"><s:message code="button.label.submit2" text="Save"/></button>
	       		</div>
	  	 	</div>
			
		</form:form>
	</div>
</div>

<script type="text/javascript">

	/* if(${salesInvoice.id ne null}) {
		
		$('#invoiceTypeSelection').hide();
		
		if(${salesInvoice.invoiceSetting.type.code ne null and salesInvoice.invoiceSetting.type.code eq 'SI'}) {
			$('#selectInvoiceType').val('SI');	
		} else {
			$('#selectInvoiceType').val('CI');
		}
	}

	selectInvoiceType(); */
	onLoadCreditNote();
</script>