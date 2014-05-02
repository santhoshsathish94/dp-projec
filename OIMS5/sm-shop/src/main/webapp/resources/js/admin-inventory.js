function onLoadCreateStock() {
	if($('#id').val() == '') {
		setupProductAutoComplete('productName1');
	} else {
		onLoadProductInfo();
	}
}

function onLoadPurchaseEntry() {
	if($('#id').val() == '') {
		setupProductAutoComplete('productName1');
	} else {
		onLoadProductInfo();
	}
}

function addNewProduct() {
	productCount = parseInt(productCount) + 1;
	
	var prod_data_div = $('<div>').attr('style', 'float: left; width: 100%; margin-top: 5px;').attr('id', 'product_row_data'+productCount);
		
	$(prod_data_div).append(createProductDiv(productCount));
	$(prod_data_div).append(createProductDescDiv(productCount));
	$(prod_data_div).append(createProductQtyDiv(productCount));
	$(prod_data_div).append(createProductUOMDiv(productCount));
	$(prod_data_div).append(createProductUnitPriceDiv(productCount));
	$(prod_data_div).append(createProductTaxDiv(productCount));
	$(prod_data_div).append(createProductAmountDiv(productCount));
	
	$(prod_data_div).append($('<span class="" style="float: left; margin-left: 10px; cursor: pointer;" onclick="removeProduct(this);">&#x2716;</span>'));
	
	$('#productData').append(prod_data_div);
	
	setupProductAutoComplete('productName'+productCount);
	
}

function createProductDiv(productCount) {
	
	var main_div = $('<div>').attr('style', 'width: 175px; float: left;');
	var control_group_div = $('<div>').attr('class', 'control-group').attr('style', 'float: left;');
	var control_div = $('<div>').attr('class', 'controls').attr('style', 'float: left;');
	var prod_name_in = $('<input>').attr('class', 'input-large').attr('id', 'productName'+productCount).attr('style', 'width: 165px;');
	var prod_id_in = $('<input>').attr('class', 'input-large').attr('id', 'productId'+productCount).attr('type', 'hidden');
	var prod_variant_id_in = $('<input>').attr('class', 'input-large').attr('id', 'variant'+productCount).attr('type', 'hidden');
	
	$(control_div).append(prod_name_in).append(prod_id_in).append(prod_variant_id_in);
	$(control_group_div).append(control_div);
	$(main_div).append(control_group_div);
	
	return main_div;
}

function createProductDescDiv(productCount) {
	
	var main_div = $('<div>').attr('style', 'width: 175px; float: left; margin-left: 25px;');
	var control_group_div = $('<div>').attr('class', 'control-group').attr('style', 'float: left;');
	var control_div = $('<div>').attr('class', 'controls').attr('style', 'float: left;');
	var inputDiv = $('<textarea>').attr('class', 'input-large').attr('id', 'prod_description'+productCount).attr('style', 'width: 175px; height: 40px;');

	$(control_group_div).append(inputDiv);
	$(control_group_div).append(control_div);
	$(main_div).append(control_group_div);
	
	return main_div;
}

function createProductQtyDiv(productCount) {
	
	var main_div = $('<div>').attr('style', 'width: 45px; float: left; margin-left: 25px;');
	var control_group_div = $('<div>').attr('class', 'control-group').attr('style', 'float: left;');
	var control_div = $('<div>').attr('class', 'controls').attr('style', 'float: left;');
	var inputDiv = $('<input>').attr('class', 'input-large').attr('id', 'prod_qty'+productCount).attr('style', 'width: 35px;').attr('onkeyup', 'calculateAmount(this);');
	
	$(control_group_div).append(inputDiv);
	$(control_group_div).append(control_div);
	$(main_div).append(control_group_div);
	
	return main_div;
}

function createProductUOMDiv(productCount) {
	
	var main_div = $('<div>').attr('style', 'width: 50px; float: left; margin-left: 25px;');
	var control_group_div = $('<div>').attr('class', 'control-group').attr('style', 'float: left;');
	var control_div = $('<div>').attr('class', 'controls').attr('style', 'float: left;');
	var inputDiv = $('<input>').attr('class', 'input-large').attr('id', 'prod_uom'+productCount).attr('style', 'width: 40px;');
	
	$(control_group_div).append(inputDiv);
	$(control_group_div).append(control_div);
	$(main_div).append(control_group_div);
	
	return main_div;
}

function createProductUnitPriceDiv(productCount) {
	
	var main_div = $('<div>').attr('style', 'width: 70px; float: left; margin-left: 25px;');
	var control_group_div = $('<div>').attr('class', 'control-group').attr('style', 'float: left;');
	var control_div = $('<div>').attr('class', 'controls').attr('style', 'float: left;');
	var inputDiv = $('<input>').attr('class', 'input-large').attr('id', 'prod_unit_price'+productCount).attr('style', 'width: 60px;').attr('onkeyup', 'calculateAmount(this);');
	
	$(control_group_div).append(inputDiv);
	$(control_group_div).append(control_div);
	$(main_div).append(control_group_div);
	
	return main_div;
}

function createProductTaxDiv(productCount) {
	
	var main_div = $('<div>').attr('style', 'width: 90px; float: left; margin-left: 25px;');
	var control_group_div = $('<div>').attr('class', 'control-group').attr('style', 'float: left;');
	var control_div = $('<div>').attr('class', 'controls').attr('style', 'float: left;');
	var inputDiv = $('<select>').attr('class', 'input-large').attr('id', 'prod_tax'+productCount).attr('style', 'width: 90px;').attr('onchange', 'getMapValue($(this).val(), "tax_amount'+productCount+'");');
	
	var hiddenInput = $('<input>').attr('type', 'hidden').attr('id', 'tax_amount'+productCount).attr('value', '');
	
	$(control_group_div).append(inputDiv);
	$(control_group_div).append(hiddenInput);
	$(control_group_div).append(control_div);
	$(main_div).append(control_group_div);
	
	return main_div;
}

function createProductAmountDiv(productCount) {
	
	var main_div = $('<div>').attr('style', 'width: 90px; float: left; margin-left: 25px;');
	var control_group_div = $('<div>').attr('class', 'control-group').attr('style', 'float: left;');
	var control_div = $('<div>').attr('class', 'controls').attr('style', 'float: left;');
	var inputDiv = $('<input>').attr('class', 'input-large').attr('id', 'prod_amount'+productCount).attr('style', 'width: 80px;').attr('onkeyup', 'calculateTotal();');
	
	$(control_group_div).append(inputDiv);
	$(control_group_div).append(control_div);
	$(main_div).append(control_group_div);
	
	return main_div;
}

function removeProduct(productSpanDiv) {
	$(productSpanDiv).parent().remove();
	var masterDiv = $.trim($('#productData').html());
	if(masterDiv == '') {
		addNewProduct();
	}
	calculateTotal();
}

function setupCustomerPhoneticAutoComplete(labelId) {
	
	$( "#"+labelId ).autocomplete({
		source: function( request, response ) {
			$.ajax({
				type: 'POST',
				url: ctx+'/admin/ajax/loadCustomer.html',
				data: {fieldValue: $( "#"+labelId ).val()},
				dataType: 'json',
				success: function(data) {
					response($.map(data, function(item) {
						return {
							label: item.nick+":"+item.name+":"+item.company,
							val: item.id
						};
					}));
					
				}
			});
		},
		select: function (e, i) {
            $("#customerId").val(i.item.val);
            $(this).val(i.item.label);
        },
		minLength: 2
	});
}

function setupProductAutoComplete(labelId) {
	
	var length = labelId.length;
	var rowValue = labelId.charAt(length - 1);
	
	$( "#"+labelId ).autocomplete({
		source: function( request, response ) {
			$.ajax({
				type: 'POST',
				url: ctx+'/admin/ajax/loadProductInfo.html',
				data: {fieldValue: $( "#"+labelId ).val(), searchType: ''},
				dataType: 'json',
				success: function(data) {
					response($.map(data, function(item) {
						return {
							label: item.productName,
							val: item
						};
					}));
					
				}
			});
		},
		select: function (e, i) {
            $("#productId"+rowValue).val(i.item.val.productId);
            $("#variant"+rowValue).val(i.item.val.variantId);
            $("#prod_description"+rowValue).val(i.item.val.description);
            $("#prod_qty"+rowValue).val(i.item.val.quantity);
            $("#prod_uom"+rowValue).val(i.item.val.uom);
            
            var unitPrice = parseFloat(i.item.val.unitPrice);
            $("#prod_unit_price"+rowValue).val(unitPrice.toFixed(2));
            
            $("#prod_tax"+rowValue).append($('#hiddenTaxClass option').clone());
            $("#prod_tax"+rowValue).val(i.item.val.taxClassId);
            
            var amount = parseFloat(i.item.val.amount);
            $("#prod_amount"+rowValue).val(amount.toFixed(2));
            
            $(this).val(i.item.label);
            
            getMapValue(i.item.val.taxClassId, 'tax_amount'+rowValue);
            
            calculateTotal();
        },
		minLength: 2
	});
}

function calculateTotal() {
	var subTotal = 0.00;
	for(var i = 0; i <= productCount; i++) {
	    if($.trim($('#prod_amount'+i).val()) != '') {
	    	subTotal += parseFloat($('#prod_amount'+i).val());
	    }
	}
	
	if($('#subTotalValue').length > 0) {
		$('#subTotalValue').html('');
		$('#subTotalValue').html(subTotal.toFixed(2));
	}
	
	if($('#totalValue').length > 0) {
		$('#totalValue').html('');
		$('#totalValue').html(subTotal.toFixed(2));
	}
}

function calculateAmount(calledDiv) {
	
	var fieldId = $(calledDiv).attr('id');
	
	var length = fieldId.length;
	var rowValue = fieldId.charAt(length - 1);
	var amount = 0.00;
	
	var qty = parseFloat($("#prod_qty"+rowValue).val());
	var unitPrice = parseFloat($("#prod_unit_price"+rowValue).val());
	
	if(isNaN(unitPrice)) {
		unitPrice = 0.00;
	}
	
	if(isNaN(qty)) {
		qty = 0;
	}
	
	amount = parseFloat(qty * unitPrice);
	
	var taxAmount = 0.00;
	if($('#tax_amount'+rowValue).length > 0) {
		var taxAmt = parseFloat($('#tax_amount'+rowValue).val());
		if(!isNaN(taxAmt)) {
			taxAmount = parseFloat((taxAmt * amount)/100);
		}
	}
	var total = parseFloat(amount + taxAmount);
	
	if($('#prod_amount'+rowValue).length > 0) 
		$('#prod_amount'+rowValue).val(total.toFixed(2));
	
	calculateTotal();
}

function getMapValue(taxMapKey, taxamountInput) {
	if(taxClassMap != '') {
		
		var taxAmount = parseFloat(taxClassMap[parseInt(taxMapKey)]);
		if(isNaN(taxAmount)) {
			$('#'+taxamountInput).val(parseFloat(0).toFixed(2));
		} else {
			$('#'+taxamountInput).val(taxAmount.toFixed(2));
		}
		
		calculateAmount($('#'+taxamountInput));
	}
}

function createProductJsonBeforeSave() {

	var objectArray = new Array();
	var object;
	
	for(var rowValue = 1; rowValue <= productCount; rowValue++) {
		
		if($("#product_row_data"+rowValue).length > 0) {
			
			object = new Object();
			
			object.productId = parseInt($("#productId"+rowValue).val());
			object.productName = $("#productName"+rowValue).val();
			object.variantId = parseInt($("#variant"+rowValue).val());
			object.description = $("#prod_description"+rowValue).val();
			object.quantity = parseInt($("#prod_qty"+rowValue).val());
			object.uom = $("#prod_uom"+rowValue).val();
			object.unitPrice = parseFloat($("#prod_unit_price"+rowValue).val());
			
			if($("#prod_tax"+rowValue).val() != '') {
				object.taxClassId = $("#prod_tax"+rowValue).val();
			}
			
			object.amount = parseFloat($('#prod_amount'+rowValue).val());
			
			objectArray.push(object);
		} else {
			$('#productJson').val('');
		}
	}
	
	$('#productJson').val(JSON.stringify(objectArray));
		

	return true;
}

function onLoadProductInfo() {
	
	if($.trim($('#productJson').val()) != '') {
		
		$('#productData').html('');
		var dataObj = eval($('#productJson').val());
		
		for(var rowValue = 0;  rowValue < dataObj.length; rowValue++) {
			addNewProduct();
			
			$('#productName'+productCount).val(dataObj[rowValue].productName);
			$("#productId"+productCount).val(dataObj[rowValue].productId);
            $("#variant"+productCount).val(dataObj[rowValue].variantId);
            $("#prod_description"+productCount).val(dataObj[rowValue].description);
            $("#prod_qty"+productCount).val(dataObj[rowValue].quantity);
            $("#prod_uom"+productCount).val(dataObj[rowValue].uom);
            
            var unitPrice = parseFloat(dataObj[rowValue].unitPrice);
            $("#prod_unit_price"+productCount).val(unitPrice.toFixed(2));
            
            $("#prod_tax"+rowValue).append($('#hiddenTaxClass option').clone());
            $("#prod_tax"+productCount).val(dataObj[rowValue].taxClassId);
            
            var taxAmt = parseFloat(dataObj[rowValue].taxAmount);
            $("#tax_amount"+productCount).val(taxAmt.toFixed(2));
            
            var amount = parseFloat(dataObj[rowValue].amount);
            $("#prod_amount"+productCount).val(amount.toFixed(2));
            
            getMapValue(dataObj[rowValue].taxClassId, 'tax_amount'+productCount);
            
            calculateTotal();
            
            setupProductAutoComplete('productName'+productCount);
		}
	}
}