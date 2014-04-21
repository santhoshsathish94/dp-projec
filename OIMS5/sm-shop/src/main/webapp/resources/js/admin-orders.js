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
	
	//setupProductAutoComplete('productName'+productCount);
	
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
	var inputDiv = $('<select>').attr('class', 'input-large').attr('id', 'prod_tax'+productCount).attr('style', 'width: 90px;');
	
	$(control_group_div).append(inputDiv);
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
	//calculateTotal();
}