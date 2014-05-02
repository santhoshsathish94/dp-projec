<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>

<html>
<%-- <head>
	<script src="<c:url value="/resources/js/jquery-1.10.2.min.js"/>"></script>
	<link href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />" rel="stylesheet"></link>
	<script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>
</head> --%>

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

<script src="<c:url value="/resources/js/admin-inventory.js" />"></script>

<script>
var productCount = 1;
var ctx = "${pageContext.request.contextPath}";
var taxClassMap = '';
<c:if test="${taxRateMap ne null}">
	taxClassMap = ${taxRateMap};
</c:if>
</script>
<style>
.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	max-width: 305px;
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
	<h3>
		<s:message code="label.inventoryManagement.title" text="Add Stock" />
	</h3>
	<br />
</div>
<c:url var="saveStock" value="/admin/inventoryManagement/createstock.html" />
<form:form method="POST" commandName="stock" action="${saveStock}">

	<div class="control-group" style="float: left; width: 100%;">
		<div style="float: left; width: 100px; margin-top: 15px;">
			<label><s:message code="label.inventoryManagement.stock.stockSDate"
					text="Date" /></label>
		</div>
		<div style="float: left; width: 200px; margin-top: 10px;"
			class="controls">
			<form:input style="width: 150px;" class="small" type="text"
				data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>"
				data-datepicker="datepicker" path="stockSDate" />
			<span class="help-inline"><form:errors path="stockSDate"
					cssClass="error" /></span>
		</div>

		<div style="float: left; width: 120px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message
					code="label.inventoryManagement.stock.stockComment" text="Comment"/></label>
		</div>
		<div style="float: left;" class="controls">
			<form:textarea cssClass="input-large" path="stockComment"
				cssStyle="width: 250px;" />
			<span class="help-inline"><form:errors path="stockComment"
					cssClass="error" /></span>
		</div>

	</div>
	<div style="width: 100%; float: left;">
		<span style="float: right; cursor: pointer; font-weight: bold; text-decoration: underline; color: #0431B4;" onclick="addNewProduct();">Add New List</span>
	</div>
	<jsp:include page="/pages/admin/inventory/productInfo.jsp" />
	<div class="control-group" style="float: left; width: 100%;">
		<%-- <div class="pull-left" style="width: 100%;">
			<div style="float: left; width: 100%;" class="stockLabel">
				<span style="float: left;">Product</span>
				<span style="float: left; margin-left: 280px;">Quantity</span>
				<span style="float: left; margin-left: 30px;">UOM</span>
				<span style="float: left; margin-left: 30px;">Unit Price</span>
				<span style="float: left; margin-left: 30px;">Amount</span>
				
				<span id="addNewRow" style="float: left; margin-left: 50px; cursor: pointer; text-decoration: underline; color: blue;">Add New Row</span>
			</div>
			
			<div style="float: left; width: 100%;margin-top: 10px;" class="master_div" id="master_div">
				
				<div id="value_holder1" style="float: left; width: 100%;">
					<input type="text" style="float: left;width: 285px;" id="product_name1">
					<input type="text" style="float: left; margin-left: 32px; width: 45px;" id="quantity1">
					<input type="text" style="float: left; margin-left: 23px; width: 25px;" id="uom1">
					<input type="text" style="float: left; margin-left: 25px; width: 60px;" id="unit_price1">
					<input type="text" style="float: left; margin-left: 15px; width: 60px;" id="amount1">
					<span style="float: left; margin: 5px; cursor: pointer;" onclick="deleteRow(this)">&#x2716;</span>
				</div>
				
			</div>
			<input type="hidden" id="openingStocks" name="openingStocks" value=""/>
			
		</div> --%>
		<form:hidden path="id"/>
		<form:hidden path="productJson"/>
		<select id="hiddenTaxClass" style="display: none; width: 100px;">
			<option value=""></option>
			<c:forEach items="${taxClasses }" var="taxClass">
				<option value="${taxClass.id}">${taxClass.code}</option>
			</c:forEach>
		</select>
		
		<div class="pull-left" style="width: 100%; margin-top: 10px;">
			<button type="submit" class="btn btn-success" onclick="return createProductJsonBeforeSave();">
				<s:message code="button.label.submit2" text="Add Stock" />
			</button>
		</div>
	</div>
<%-- 	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
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
	</div> --%>
	
</form:form>

			
<script>
	/* var rowCount = 1;
	$("#addNewRow").click(function (){
		addNewRow();
	});

	function addNewRow() {
		rowCount = parseInt(rowCount) + 1;
		var holderDiv = $('<div>').attr('style', 'float: left; width: 100%;').attr('id', 'value_holder'+rowCount);
		var pName = $('<input>').attr('type', 'text').attr('style', 'float: left; width: 285px;').attr('id', 'product_name'+rowCount);
		var quantity = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 32px; width: 45px;').attr('id', 'quantity'+rowCount);
		var uom = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 23px; width: 25px;').attr('id', 'uom'+rowCount);
		var unitPrice = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 25px; width: 60px;').attr('id', 'unit_price'+rowCount);
		var amount = $('<input>').attr('type', 'text').attr('style', 'float: left; margin-left: 15px; width: 60px;').attr('id', 'amount'+rowCount);
		var deleteRow = $('<span>').attr('style', 'float: left; margin: 5px; cursor: pointer;').attr('onclick', 'deleteRow(this)').html('&#x2716;');
		
		$(holderDiv).append(pName).append(quantity).append(uom).append(unitPrice).append(amount).append(deleteRow);

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
				object.stockSKU = $('#product_name'+counter).val();
				object.stockQuantity = $('#quantity'+counter).val();
				object.stockUOM = $('#uom'+counter).val();
				object.stockUnitPrice = $('#unit_price'+counter).val();
				object.stockAmount = $('#amount'+counter).val();

				objectAray.push(object);
			}
		}

		$('#openingStocks').val(JSON.stringify(objectAray));
	} */
	onLoadCreateStock();
</script>