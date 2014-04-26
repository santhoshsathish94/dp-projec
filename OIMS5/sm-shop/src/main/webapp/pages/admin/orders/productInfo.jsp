<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<style type="text/css">
	#productHeader label {
		font-weight: bold;
	}
</style>

<div style="width: 100%; float: left; margin-top: 10px;">
	<div id="productHeader" style="float: left; width: 100%; background-color: #D8D8D8; height: 20px;">
		<div style="width: 175px; float: left;">
			<div class="control-group" style="float: left;">
				<label><s:message code="label.billing.prefix" text="Product:"/></label>
			</div>
		</div>
		<div style="width: 175px; float: left; margin-left: 25px;">
			<div class="control-group" style="float: left;">
				<label><s:message code="label.billing.prefix" text="Description:"/></label>
			</div>
		</div>
		<div style="width: 45px; float: left; margin-left: 25px;">
			<div class="control-group" style="float: left;">
				<label><s:message code="label.billing.prefix" text="Qty:"/></label>
			</div>
		</div>
		<div style="width: 50px; float: left; margin-left: 25px;">
			<div class="control-group" style="float: left;">
				<label><s:message code="label.billing.prefix" text="UOM:"/></label>
			</div>
		</div>
		<div style="width: 70px; float: left; margin-left: 25px;">
			<div class="control-group" style="float: left;">
				<label><s:message code="label.billing.prefix" text="Unit Price:"/></label>
			</div>
		</div>
		<div style="width: 90px; float: left; margin-left: 25px;">
			<div class="control-group" style="float: left;">
				<label><s:message code="label.billing.prefix" text="Tax:"/></label>
			</div>
		</div>
		<div style="width: 90px; float: left; margin-left: 25px;">
			<div class="control-group" style="float: left;">
				<label><s:message code="label.billing.prefix" text="Amount:"/></label>
			</div>
		</div>
	</div>
	
	<div id="productData" style="float: left; width: 100%; margin-top: 10px;">
		<div id="product_row_data1" style="float: left; width: 100%; margin-top: 5px;">
			<div style="width: 175px; float: left;">
				<div class="control-group" style="float: left;">
					<div style="float: left;" class="controls">
						<input class="input-large" id="productName1" style="width: 165px;"/>
						<input class="input-large" id="productId1" type="hidden"/>
						<input class="input-large" id="variant1" type="hidden"/>
					</div>
				</div>
			</div>
			<div style="width: 175px; float: left; margin-left: 25px;">
				<div class="control-group" style="float: left;">
					<div style="float: left;" class="controls">
						<textarea class="input-large" id="prod_description1" style="width: 175px; height: 40px;"></textarea>
					</div>
				</div>
			</div>
			<div style="width: 45px; float: left; margin-left: 25px;">
				<div class="control-group" style="float: left;">
					<div style="float: left;" class="controls">
						<input class="input-large" id="prod_qty1" style="width: 35px;" onkeyup="calculateAmount(this);"/>
					</div>
				</div>
			</div>
			<div style="width: 50px; float: left; margin-left: 25px;">
				<div class="control-group" style="float: left;">
					<div style="float: left;" class="controls">
						<input class="input-large" id="prod_uom1" style="width: 40px;"/>
					</div>
				</div>
			</div>
			<div style="width: 70px; float: left; margin-left: 25px;">
				<div class="control-group" style="float: left;">
					<div style="float: left;" class="controls">
						<input class="input-large" id="prod_unit_price1" style="width: 60px;" onkeyup="calculateAmount(this);"/>
					</div>
				</div>
			</div>
			<div style="width: 90px; float: left; margin-left: 25px;">
				<div class="control-group" style="float: left;">
					<div style="float: left;" class="controls">
						<select class="input-large" id="prod_tax1" style="width: 90px;"  onchange="getMapValue($(this).val(), 'tax_amount1');">
							
						</select>
						<input type="hidden" id="tax_amount1" value=""/>
					</div>
				</div>
			</div>
			<div style="width: 90px; float: left; margin-left: 25px;">
				<div class="control-group" style="float: left;">
					<div style="float: left;" class="controls">
						<input class="input-large" id="prod_amount1" style="width: 80px;" onkeyup="calculateTotal();"/>
					</div>
				</div>
			</div>
			<span class="" style="float: left; margin-left: 10px; cursor: pointer;" onclick="removeProduct(this);">&#x2716;</span>
		</div>
	</div>
</div>
