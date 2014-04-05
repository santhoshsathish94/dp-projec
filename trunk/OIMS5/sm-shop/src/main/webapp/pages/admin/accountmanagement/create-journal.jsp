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
		<s:message code="" text="New Journal Entry" />
	</h3>
	<br />
</div>
<c:url var="saveURL" value="/admin/account/createExpense.html" />
<form:form method="POST" commandName="stock" action="${saveURL}">

	<div style="float: left; width: 100%;">
		
		<div style="float: left; width: 200px;">
			<label style="margin-top: 0px;"><s:message	code="" text="Date"/></label>
		</div>		
	
		<div style="float: left; width: 200px; ">
			<label style="margin-top: 0px;"><s:message	code="" text="Reference No."/></label>
		</div>		
		
		<div style="float: left; width: 200px; ">
			<label style="margin-top: 0px;"><s:message	code="" text="Comment"/></label>
		</div>	

	</div>
	
	<div style="float: left; width: 100%; margin-top: 0px;">
	
		
	
		<div style="float: left;"	class="controls">
			<form:input style="width: 180px;" class="small" type="text"
				data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>"
				data-datepicker="datepicker" path="" />
			<span class="help-inline"><form:errors path=""	cssClass="error" /></span>
		</div>
		
		<div style="float: left;" class="controls" >
			<form:input cssClass="input-large" path=""	cssStyle="width: 180px;" />
			<span class="help-inline"><form:errors path=""	cssClass="error" /></span>
		</div>
		
		<div style="float: left;" class="controls">
			<form:textarea cssClass="input-large" path="" cssStyle="width: 180px;" />
			<span class="help-inline"><form:errors path=""	cssClass="error" /></span>
		</div>
		

	</div>
		
	
	<div style="float: left; width: 100%;">
		
		<div style="float: left; width: 290px;">
			<label style="margin-top: 0px;"><s:message	code="" text="Pay By"/></label>
		</div>		
	
	</div>
	
	<div style="float: left; width: 100%; margin-top: 0px;">
		
		<div style="float: left;"	class="controls">
			<form:select cssClass="country-list highlight" path="">
				<form:option value="Cash">Cash</form:option>
				<form:option value="Bank">Bank</form:option>
			</form:select>
			<span class="help-inline"><form:errors path=""	cssClass="error" /></span>
		</div>

	</div>
	<div class="control-group" style="float: left; width: 100%;">
		<div class="pull-left" style="width: 100%;">
			<div style="float: left; width: 100%;" class="stockLabel">
				<span style="float: left;">Debit</span>
							
				<span id="addNewRow" style="float: left; margin-left: 50px; cursor: pointer; text-decoration: underline; color: blue;">Add New Row</span>
				
				<span style="float: left; margin-left: 205px;">Amount</span>	
			</div>

			
			<div style="float: left; width: 100%;margin-top: 10px;" class="master_div" id="master_div">
				
				<div id="value_holder1" style="float: left; width: 100%;">
					<input type="text" style="float: left;width: 285px;" id="debit1">
					<input type="text" style="float: left; margin-left: 30px; width: 100px;" id="debit_amount1">
					<span style="float: left; margin: 5px; cursor: pointer;" onclick="deleteRow(this)">&#x2716;</span>
				</div>
				
			</div>
			<div style="float: left; width: 100%;">		
				<div style="float: left; width: 400px;">
					<label style="margin-top: 0px;font-size:10px;"><font color="blue"><s:message code="" text="Start typing 2-3 letters of the name to show the available names."/></font></label>
				</div>				
			</div>
			<input type="hidden" id="jsonArray" name="jsonArray" value=""/>
			
		</div>
				
	</div>
	
	<div class="control-group" style="float: left; width: 100%;">
		<div class="pull-left" style="width: 100%;">
			<div style="float: left; width: 100%;" class="stockLabel">
				<span style="float: left;">Credit</span>
							
				<span id="addNewRow1" style="float: left; margin-left: 50px; cursor: pointer; text-decoration: underline; color: blue;">Add New Row</span>
				
				<span style="float: left; margin-left: 205px;">Amount</span>	
			</div>

			
			<div style="float: left; width: 100%;margin-top: 10px;" class="master_div1" id="master_div1">
				
				<div id="value_holder11" style="float: left; width: 100%;">
					<input type="text" style="float: left;width: 285px;" id="credit1">
					<input type="text" style="float: left; margin-left: 30px; width: 100px;" id="credit_amount1">
					<span style="float: left; margin: 5px; cursor: pointer;" onclick="deleteRow(this)">&#x2716;</span>
				</div>
				
			</div>
			<div style="float: left; width: 100%;">		
				<div style="float: left; width: 400px;">
					<label style="margin-top: 0px;font-size:10px;"><font color="blue"><s:message code="" text="Start typing 2-3 letters of the name to show the available names."/></font></label>
				</div>				
			</div>
			<input type="hidden" id="jsonArray1" name="jsonArray1" value=""/>
			
		</div>
				
	</div>
			
	<div class="control-group" style="float: left; width: 100%;">
		<div class="pull-left" style="width: 100%; margin-top: 10px;">
			<button type="submit" class="btn btn-success" onclick="return createStockEntryJson();">
				<s:message code="button.label.submit2" text="Save" />
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
	var pName = $('<input>').attr('type', 'text').attr('style', 'float: left; width: 285px;').attr('id', 'debit'+rowCount);
	var amount = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 30px; width: 100px;').attr('id', 'debit_amount'+rowCount);
	var deleteRow = $('<span>').attr('style', 'float: left; margin: 5px; cursor: pointer;').attr('onclick', 'deleteRow(this)').html('&#x2716;');
	
	$(holderDiv).append(pName).append(amount).append(deleteRow);

	$('#master_div').append(holderDiv);		
}

var rowCount1 = 1;
$("#addNewRow1").click(function (){
	addNewRow1();
});

function addNewRow1() {
	rowCount1 = parseInt(rowCount1) + 1;
	var holderDiv = $('<div>').attr('style', 'float: left; width: 100%;').attr('id', 'value_holder1'+rowCount1);
	var pName = $('<input>').attr('type', 'text').attr('style', 'float: left; width: 285px;').attr('id', 'credit'+rowCount1);
	var amount = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 30px; width: 100px;').attr('id', 'credit_amount'+rowCount1);
	var deleteRow = $('<span>').attr('style', 'float: left; margin: 5px; cursor: pointer;').attr('onclick', 'deleteRow(this)').html('&#x2716;');
	
	$(holderDiv).append(pName).append(amount).append(deleteRow);

	$('#master_div1').append(holderDiv);		
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
			object.stockSKU = $('#debit'+counter).val();
			object.stockAmount = $('#debit_amount'+counter).val();

			objectAray.push(object);
		}
	}
	alert(JSON.stringify(objectAray));
	$('#jsonArray').val(JSON.stringify(objectAray));
	createStockEntryJson1();
}	

function createStockEntryJson1() {
	var objectAray = new Array();
	var object = ""; 
	for(var counter = 0; counter <= parseInt(rowCount); counter++) {
		if($('#value_holder'+counter).length == 1) {
			object = new Object();
			object.stockSKU = $('#credit'+counter).val();
			object.stockAmount = $('#credit_amount'+counter).val();

			objectAray.push(object);
		}
	}
	alert(JSON.stringify(objectAray));
	$('#jsonArray1').val(JSON.stringify(objectAray));
}	
		
</script>