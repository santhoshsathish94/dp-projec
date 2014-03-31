

function displayVaraint() {
	if($('#productHaveVariant').is(':checked')) {
		$('#product_variant').show();
	} else {
		$('#product_variant').hide();
	}
}

function removeImage(imageId) {
	$("#store.error").show();
	$.ajax({
	  type: 'POST',
	  url: '/ishop/admin/products/product/removeImage.html',
	  data: 'imageId=' + imageId,
	  dataType: 'json',
	  success: function(response){
			var status = isc.XMLTools.selectObjects(response, "/response/status");
			if(status==0 || status ==9999) {
				//remove delete
				$("#imageControlRemove").html('');
				//add field
				$("#imageControl").html('<input class=\"input-file\" id=\"image\" name=\"image\" type=\"file\">');
				$(".alert-success").show();
			} else {
				//display message
				$(".alert-error").show();
			}
	  },
	  error: function(xhr, textStatus, errorThrown) {
	  	alert('error ' + errorThrown);
	  }
	});
}

function addMoreVariant() {
	counter = parseInt(counter) + 1;
	var vv_d = $('<div>').attr('id', 'variant_div'+counter).attr('class', 'variant_div');
	var sv_in = $('<input>').attr('type', 'text').attr('id', 'shades_variant'+counter).attr('class', 'shades_variant').attr('value', '');
	var hiddenId = $('<input>').attr('type', 'hidden').attr('id', 'shadeId'+counter).attr('value', '');
	var v_div = $('<div>').attr('class', 'sm input').attr('style', 'float: left; height: 28px; margin-left: 10px; width: 275px;').attr('id', 'packId'+counter);
	var v_in = $('<input>').attr('type', 'text').attr('id', 'pack_size_variant'+counter).attr('class', 'pack_size_variant');
	var r_s = $('<span>').attr('class', 'remove_variants').attr('onclick', 'removeVariant(this);').html('&#x2716;');
	
	$(v_div).append(v_in);
	$(vv_d).append($(sv_in)).append($(hiddenId)).append($(v_div)).append($(r_s));
	$('#variant_master').append($(vv_d));
	
	setupShadeAutoComplete('shades_variant'+counter);
	setupPackSize('pack_size_variant'+counter);
}

function removeVariant(variantDiv) {
	$(variantDiv).parent().remove();
	var masterDiv = $.trim($('#variant_master').html());
	if(masterDiv == '') {
		addMoreVariant();
	}		
}

function setupShadeAutoComplete(labelId) {
	
	var length = labelId.length;
	var rowValue = labelId.charAt(length - 1);
	
	$( "#"+labelId ).autocomplete({
		source: function( request, response ) {
			$.ajax({
				type: 'POST',
				url: '/ishop/admin/options/loadShadeData.html',
				data: {fieldValue: $( "#"+labelId ).val()},
				dataType: 'json',
				success: function(data) {
					response($.map(data, function(item) {
						return {
							label: item.optionName,
							val: item.id
						};
					}));
					
				}
			});
		},
		select: function (e, i) {
            $("#shadeId"+rowValue).val(i.item.val);
            $(this).val(i.item.label);
        },
		minLength: 2
	});
}

function setupPackSize(packId) {
    $("#"+packId).tokenInput(variantList, {
        theme: "facebook"
    });
}

function setupShadeVariant() {
	
	var objectArray = new Array();
	var object;
	for(var i = 0; i <=counter; i++) {
		
		if($.trim($('#packId'+i).children("input[type=text]").val()) != '') {
			var packId = $('#packId'+i).children("input[type=text]").val();
			var packIdArray = packId.split(',');
			
			object = new Object();
			object.shade = $('#shadeId'+i).val();
			var packArray = new Array();
			for(var j = 0; j < packIdArray.length; j++) {
				packArray.push(packIdArray[j]);
			}
			object.variant = packArray;
			objectArray.push(object);
		}
	}
	
	$('#productVariants').val(JSON.stringify(objectArray));
}

function displayVariantOnLoad() {
	
	if($('.productId').val() != '' && $.trim(variants) != '') {
		
		$('#variant_master').html('');
		var dataObj = eval(variants);

		for(var i = 0;  i < dataObj.length; i++) {
		    
		    populateVariantMasterDiv(parseInt(i)+1);
		    prePopulateShade(counter, dataObj[i].shade);
		    prePopulateVariant(counter, dataObj[i].variant);
		}
	} else {
		setupShadeAutoComplete('shades_variant1');
		setupPackSize('pack_size_variant1');
	}
}

function populateVariantMasterDiv(count) {
	
	var vv_d = $('<div>').attr('id', 'variant_div'+count).attr('class', 'variant_div');
	var sv_in = $('<input>').attr('type', 'text').attr('id', 'shades_variant'+count).attr('class', 'shades_variant').attr('value', '');
	var hiddenId = $('<input>').attr('type', 'hidden').attr('id', 'shadeId'+count).attr('value', '');
	var v_div = $('<div>').attr('class', 'sm input').attr('style', 'float: left; height: 28px; margin-left: 10px; width: 275px;').attr('id', 'packId'+count);
	var v_in = $('<input>').attr('type', 'text').attr('id', 'pack_size_variant'+count).attr('class', 'pack_size_variant');
	var r_s = $('<span>').attr('class', 'remove_variants').attr('onclick', 'removeVariant(this);').html('&#x2716;');
	
	$(v_div).append(v_in);
	$(vv_d).append($(sv_in)).append($(hiddenId)).append($(v_div)).append($(r_s));
	$('#variant_master').append($(vv_d));
	
	counter = parseInt(count);
}

function prePopulateShade(count, shadeObj) {
	setupShadeAutoComplete('shades_variant'+count);
	
	$('#shades_variant'+count).val(shadeObj.name);
	$('#shadeId'+count).val(shadeObj.id);
}

function prePopulateVariant(count, variantObj) {
	$("#pack_size_variant"+count).tokenInput(variantList, {
        theme: "facebook",
        prePopulate: variantObj
    });
}