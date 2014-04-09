<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>

<html>
<head>
	<script src="<c:url value="/resources/js/jquery-1.10.2.min.js"/>"></script>
	<link href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />" rel="stylesheet"></link>
	<script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>
</head>

<div class="tabbable">
	<jsp:include page="/common/adminTabs.jsp" />
	<h3>
		<s:message code="label.inventoryManagement.perchase" text="New Purchase Entery" />
	</h3>
	<br />
</div>
<c:url var="savePurchaseEntery" value="/admin/inventoryManagement/createPurchaseEntry.html" />
<form:form method="POST" commandName="purchase" action="${savePurchaseEntery}">

	<div class="control-group" style="float: left; width: 100%;">
	
		<div style="float: left; width: 60px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message
					code="" text="Supplier"/></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls" >
			<form:input cssClass="input-large" path="purchase_supplier"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="purchase_supplier"
					cssClass="error" /></span>
		</div>
		
		<div style="float: left; width: 40px; margin-top: 15px;">
			<label><s:message code=""
					text="Date" /></label>
		</div>
		<div style="float: left; margin-top: 10px;"
			class="controls">
			<form:input style="width: 80px;" class="small" type="text"
				data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>"
				data-datepicker="datepicker" path="purchase_Sdate" />
			<span class="help-inline"><form:errors path="purchase_Sdate"
					cssClass="error" /></span>
		</div>
		
		<div style="float: left; width: 60px; margin-top: 15px;">
			<label><s:message code=""
					text="Due Date" /></label>
		</div>
		<div style="float: left; margin-top: 10px;"
			class="controls">
			<form:input style="width: 80px;" class="small" type="text"
				data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>"
				data-datepicker="datepicker" path="purchase_due_Sdate" />
			<span class="help-inline"><form:errors path="purchase_due_Sdate"
					cssClass="error" /></span>
		</div>
		
	</div>
	<div class="control-group" style="float: left; width: 100%;">
	
		<div style="float: left; width: 60px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message
					code="" text="Ref No."/></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls" >
			<form:input cssClass="input-large" path="purchase_ref_number"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="purchase_ref_number"
					cssClass="error" /></span>
		</div>
	
		<div style="float: left; width: 80px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message
					code="" text="Comment"/></label>
		</div>
		<div style="float: left;" class="controls">
			<form:textarea cssClass="input-large" path="purchase_comment"
				cssStyle="width: 250px;" />
			<span class="help-inline"><form:errors path="purchase_comment"
					cssClass="error" /></span>
		</div>

	</div>
	<div class="control-group" style="float: left; width: 100%;">
		<div class="pull-left" style="width: 100%;">
			<div style="float: left; width: 100%;" class="stockLabel">
				<span style="float: left;">Product</span>
				<span id="addNewRow" style="float: left; margin-left: 10px; cursor: pointer; text-decoration: underline; color: blue;">Add New Row</span>
				<span style="float: left; margin-left: 120px;">Description</span>
				<span style="float: left; margin-left: 50px;">Quantity</span>
				<span style="float: left; margin-left: 30px;">UOM</span>
				<span style="float: left; margin-left: 30px;">Unit Price</span>
				<span style="float: left; margin-left: 40px;">Tax</span>
				<span style="float: left; margin-left: 60px;">Amount</span>
				
			</div>
			
			<div style="float: left; width: 100%;margin-top: 10px;" class="master_div" id="master_div">
				
				<div id="value_holder1" style="float: left; width: 100%;">
					<input type="text" style="float: left;width: 185px;" id="product_name1">
					<input type="textarea" style="float: left; margin-left: 32px; width: 100px;" id="description1">
					<input type="text" style="float: left; margin-left: 32px; width: 45px;" id="quantity1">
					<input type="text" style="float: left; margin-left: 23px; width: 25px;" id="uom1">
					<input type="text" style="float: left; margin-left: 25px; width: 60px;" id="unit_price1">
					<input type="text" style="float: left; margin-left: 15px; width: 60px;" id="tax1">
					<input type="text" style="float: left; margin-left: 15px; width: 60px;" id="amount1">
					<span style="float: left; margin: 5px; cursor: pointer;" onclick="deleteRow(this)">&#x2716;</span>
				</div>
				
			</div>
			<input type="hidden" id="jsonArray" name="jsonArray" value=""/>
			
		</div>
		
		<div class="pull-left" style="width: 100%; margin-top: 10px;">
			<button type="submit" class="btn btn-success" onclick="return createStockEntryJson();">
				<s:message code="button.label.submit2" text="Add Stock" />
			</button>
		</div>
	</div>
		
</form:form>

			
<script>
	var rowCount = 1;
	$("#addNewRow").click(function (){
		addNewRow();
	});

	function addNewRow() {
		rowCount = parseInt(rowCount) + 1;
		var holderDiv = $('<div>').attr('style', 'float: left; width: 100%;').attr('id', 'value_holder'+rowCount);
		var pName = $('<input>').attr('type', 'text').attr('style', 'float: left; width: 185px;').attr('id', 'product_name'+rowCount);
		var description = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 32px; width: 100px;').attr('id', 'description'+rowCount);
		var quantity = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 32px; width: 45px;').attr('id', 'quantity'+rowCount);
		var uom = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 23px; width: 25px;').attr('id', 'uom'+rowCount);
		var unitPrice = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 25px; width: 60px;').attr('id', 'unit_price'+rowCount);
		var tax = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 15px; width: 60px;').attr('id', 'tax'+rowCount);
		var amount = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 15px; width: 60px;').attr('id', 'amount'+rowCount);
		var deleteRow = $('<span>').attr('style', 'float: left; margin: 5px; cursor: pointer;').attr('onclick', 'deleteRow(this)').html('&#x2716;');
		
		$(holderDiv).append(pName).append(description).append(quantity).append(uom).append(unitPrice).append(tax).append(amount).append(deleteRow);

		$('#master_div').append(holderDiv);		
	}
	
	
	function deleteRow(removeElement) {
		$(removeElement).parent().remove();
		if($.trim($('#master_div').html()) == '') {
			addNewRow();
		}
	}


	function createStockEntryJson() {
		var objectAray = new Array();
		var object = ""; 
		for(var counter = 0; counter <= parseInt(rowCount); counter++) {
			if($('#value_holder'+counter).length == 1) {
				object = new Object();
				object.purchaseSKU = $('#product_name'+counter).val();
				object.purchaseDescription = $('#description'+counter).val();
				object.purchaseQuantity = $('#quantity'+counter).val();
				object.purchaseUOM = $('#uom'+counter).val();
				object.purchaseUnitPrice = $('#unit_price'+counter).val();
				object.purchaseTax = $('#tax'+counter).val();
				object.purchaseAmount = $('#amount'+counter).val();

				objectAray.push(object);
			}
		}
		//alert(JSON.stringify(objectAray));
		$('#jsonArray').val(JSON.stringify(objectAray));
	}
		
</script>