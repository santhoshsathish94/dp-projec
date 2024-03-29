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
		<s:message code="" text="Branch Transfer" />
	</h3>
	<br />
</div>
<c:url var="saveURL" value="/admin/inventoryManagement/createbranchtransfer.html" />
<form:form method="POST" commandName="branchTransfer" action="${saveURL}">

	<div class="control-group" style="float: left; width: 100%;">
	
		<div style="float: left; width: 60px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message
					code="" text="From"/></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls" >
			<form:input cssClass="input-large" path="store_from"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="store_from"
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
				data-datepicker="datepicker" path="transfer_Sdate" />
			<span class="help-inline"><form:errors path="transfer_Sdate"
					cssClass="error" /></span>
		</div>
		
	</div>
	<div class="control-group" style="float: left; width: 100%;">
	
		<div style="float: left; width: 60px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message
					code="" text="To"/></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls" >
			<form:input cssClass="input-large" path="store_to"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="store_to"
					cssClass="error" /></span>
		</div>
	
		<div style="float: left; width: 80px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message
					code="" text="Comments"/></label>
		</div>
		<div style="float: left;" class="controls">
			<form:textarea cssClass="input-large" path="transfer_comment"
				cssStyle="width: 250px;" />
			<span class="help-inline"><form:errors path="transfer_comment"
					cssClass="error" /></span>
		</div>

	</div>
	<div class="control-group" style="float: left; width: 100%;">
		<div class="pull-left" style="width: 100%;">
			<div style="float: left; width: 100%;" class="stockLabel">
				<span style="float: left;">Product</span>
				<span id="addNewRow" style="float: left; margin-left: 10px; cursor: pointer; text-decoration: underline; color: blue;">Add New Row</span>
				<span style="float: left; margin-left: 120px;">Availability</span>
				<span style="float: left; margin-left: 50px;">Quantity</span>
								
			</div>
			
			<div style="float: left; width: 100%;margin-top: 10px;" class="master_div" id="master_div">
				
				<div id="value_holder1" style="float: left; width: 100%;">
					<input type="text" style="float: left;width: 185px;" id="product_name1">
					<input type="textarea" style="float: left; margin-left: 32px; width: 100px;" id="availability1">
					<input type="text" style="float: left; margin-left: 32px; width: 45px;" id="quantity1">
					
					<span style="float: left; margin: 5px; cursor: pointer;" onclick="deleteRow(this)">&#x2716;</span>
				</div>
				
			</div>
			<input type="hidden" id="jsonArray" name="jsonArray" value=""/>
			
		</div>
		
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
		var pName = $('<input>').attr('type', 'text').attr('style', 'float: left; width: 185px;').attr('id', 'product_name'+rowCount);
		var availability = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 32px; width: 100px;').attr('id', 'availability'+rowCount);
		var quantity = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 32px; width: 45px;').attr('id', 'quantity'+rowCount);
		var deleteRow = $('<span>').attr('style', 'float: left; margin: 5px; cursor: pointer;').attr('onclick', 'deleteRow(this)').html('&#x2716;');
		
		$(holderDiv).append(pName).append(availability).append(quantity).append(deleteRow);

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
				object.SKU = $('#product_name'+counter).val();
				object.availability = $('#availability'+counter).val();
				object.quantity = $('#quantity'+counter).val();
				
				objectAray.push(object);
			}
		}
		//alert(JSON.stringify(objectAray));
		$('#jsonArray').val(JSON.stringify(objectAray));
	}
		
</script>