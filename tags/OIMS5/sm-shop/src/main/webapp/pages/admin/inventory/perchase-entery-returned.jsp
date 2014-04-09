<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>

<html>
<head>
<script src="<c:url value="/resources/js/jquery-1.10.2.min.js"/>"></script>
<link
	href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />"
	rel="stylesheet"></link>
<script
	src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>
</head>

<div class="tabbable">
	<jsp:include page="/common/adminTabs.jsp" />
	<h3>
		<s:message code="" text="New Debit Note" />
	</h3>
	<br />
</div>
<c:url var="saveURL"
	value="/admin/inventoryManagement/createpurchasereturndn.html" />
<form:form method="POST" commandName="purchaseReturnDebitNote"	action="${saveURL}">

	<div class="control-group" style="float: left; width: 100%;">

		<div style="float: left; width: 100px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code=""	text="Name (Debit To)" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="debit_supplier"	cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="debit_supplier" cssClass="error" /></span>
		</div>

		<div style="float: left; width: 40px; margin-top: 15px;">
			<label><s:message code="" text="Date" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input style="width: 80px;" class="small" type="text"
				data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>"
				data-datepicker="datepicker" path="debit_Sdate" />
			<span class="help-inline"><form:errors path="debit_Sdate"
					cssClass="error" /></span>
		</div>

		<div style="float: left; width: 60px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code=""
					text="Ref No." /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="debit_ref_number"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="debit_ref_number"
					cssClass="error" /></span>
		</div>
	</div>

	<div class="control-group" style="float: left; width: 100%;">
		<div class="pull-left" style="width: 100%;">
			<div style="float: left; width: 100%;" class="stockLabel">
				<span style="float: left;">Product</span> <span id="addNewRow"
					style="float: left; margin-left: 10px; cursor: pointer; text-decoration: underline; color: blue;">Add
					New Row</span> <span style="float: left; margin-left: 60px;">Description</span>
				<span style="float: left; margin-left: 45px;">Quantity</span> <span
					style="float: left; margin-left: 25px;">Unit Price</span> <span
					style="float: left; margin-left: 40px;">Tax</span> <span
					style="float: left; margin-left: 50px;">Tax Amount</span> <span
					style="float: left; margin-left: 30px;">Amount</span> <span
					style="float: left; margin-left: 40px;">Invoice No.</span>

			</div>

			<div style="float: left; width: 100%; margin-top: 10px;"
				class="master_div" id="master_div">

				<div id="value_holder1" style="float: left; width: 100%;">
					<input type="text" style="float: left; width: 155px;" id="product_name1"> 
					<input type="textarea" style="float: left; margin-left: 15px; width: 100px;" id="description1">
					<input type="text"	style="float: left; margin-left: 15px; width: 45px;" id="quantity1">
					<input type="text"	style="float: left; margin-left: 15px; width: 60px;" id="unit_price1">
					<input type="text"	style="float: left; margin-left: 15px; width: 60px;" id="tax1">
					<input type="text"	style="float: left; margin-left: 15px; width: 60px;" id="tax_amount1">
					<input type="text"	style="float: left; margin-left: 15px; width: 60px;" id="amount1">
					<input type="text"	style="float: left; margin-left: 15px; width: 80px;" id="invoice_no1">
					<span tyle="float: left; margin: 5px; cursor: pointer;"	onclick="deleteRow(this)">&#x2716;</span>
				</div>

			</div>
			<input type="hidden" id="jsonArray" name="jsonArray" value="" />

		</div>

		<div class="pull-left" style="width: 100%; margin-top: 10px;">
			<button type="submit" class="btn btn-success"
				onclick="return createJson();">
				<s:message code="button.label.submit2" text="Add Stock" />
			</button>
		</div>
	</div>
	
</form:form>


<script>
	var rowCount = 1;
	$("#addNewRow").click(function() {
		addNewRow();
	});

	function addNewRow() {
		rowCount = parseInt(rowCount) + 1;
		var holderDiv = $('<div>').attr('style', 'float: left; width: 100%;').attr('id', 'value_holder' + rowCount);
		var pName = $('<input>').attr('type', 'text').attr('style','float: left; width: 155px;').attr('id',	'product_name' + rowCount);
		var description = $('<input>').attr('type', 'text').attr('style','float: left; margin-left: 15px; width: 100px;').attr('id','description' + rowCount);
		var quantity = $('<input>').attr('type', 'text').attr('style','float: left; margin-left: 15px; width: 45px;').attr('id','quantity' + rowCount);
		var unitPrice = $('<input>').attr('type', 'text').attr('style',	'float: left; margin-left: 15px; width: 60px;').attr('id','unit_price' + rowCount);
		var tax = $('<input>').attr('type', 'text').attr('style','float: left; margin-left: 15px; width: 60px;').attr('id',	'tax' + rowCount);
		var tax_amount = $('<input>').attr('type', 'text').attr('style','float: left; margin-left: 15px; width: 60px;').attr('id','tax_amount' + rowCount);
		var amount = $('<input>').attr('type', 'text').attr('style','float: left; margin-left: 15px; width: 60px;').attr('id','amount' + rowCount);
		var invoice_no = $('<input>').attr('type', 'text').attr('style','float: left; margin-left: 15px; width: 80px;').attr('id','invoice_no' + rowCount);
		var deleteRow = $('<span>').attr('style','float: left; margin: 5px; cursor: pointer;').attr('onclick','deleteRow(this)').html('&#x2716;');

		$(holderDiv).append(pName).append(description).append(quantity).append(unitPrice).append(tax).append(tax_amount).append(amount).append(invoice_no).append(deleteRow);

		$('#master_div').append(holderDiv);
	}

	function deleteRow(removeElement) {
		$(removeElement).parent().remove();
		if ($.trim($('#master_div').html()) == '') {
			addNewRow();
		}
	}

	function createJson() {
		var objectAray = new Array();
		var object = "";
		for (var counter = 0; counter <= parseInt(rowCount); counter++) {
			if ($('#value_holder' + counter).length == 1) {
				object = new Object();
				object.product_name = $('#product_name' + counter).val();
				object.description = $('#description' + counter).val();
				object.quantity = $('#quantity' + counter).val();
				object.unit_price = $('#unit_price' + counter).val();
				object.tax = $('#tax' + counter).val();
				object.tax_amount = $('#tax_amount' + counter).val();
				object.amount = $('#amount' + counter).val();
				object.invoice_no = $('#invoice_no' + counter).val();

				objectAray.push(object);
			}
		}
		alert(JSON.stringify(objectAray));

		$('#jsonArray').val(JSON.stringify(objectAray));
	}

	
</script>