<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/shopizer-tags.tld" prefix="sm" %>

<%@ page session="false" %>			
<script type="text/javascript">
	var priceFormatMessage = '<s:message code="message.price.cents" text="Wrong format" />';
	var variantList = ${variantList}
	<c:choose>
		<c:when test="${variants != null && variants != ''}" >
			var variants = ${variants}
		</c:when>
		<c:otherwise>
		var variants = '';
		</c:otherwise>
	</c:choose>
</script>

<link href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />" rel="stylesheet"></link>
<script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>
<script src="<c:url value="/resources/js/ckeditor/ckeditor.js" />"></script>
<script src="<c:url value="/resources/js/jquery.formatCurrency-1.4.0.js" />"></script>
<script src="<c:url value="/resources/js/jquery.alphanumeric.pack.js" />"></script>
<script src="<c:url value="/resources/js/adminFunctions.js" />"></script>

<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/jquery.ui.theme.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/jquery.ui.accordion.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/jquery.ui.menu.css" />">

<script type='text/javascript' src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.bgiframe.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.core.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.widget.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.position.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.menu.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.autocomplete.js" />"></script>

<script src="<c:url value="/resources/js/admin-product.js" />"></script>

<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.tokeninput.js" />"></script>

<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/token-input.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/token-input-facebook.css" />">

<style>
.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	max-width: 135px;
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

.token-input-list-facebook {
    clear: left;
    cursor: text;
    font-family: Verdana;
    font-size: 12px;
    height: auto !important;
    list-style-type: none;
    margin: 0;
    min-height: 1px;
    overflow: hidden;
    padding: 0;
    float: left;
}

ul.token-input-list-facebook {
    background-color: #FFFFFF;
    border: 1px solid #CCCCCC;
    clear: left;
    cursor: text;
    font-family: Verdana;
    font-size: 12px;
    height: auto !important;
    list-style-type: none;
    margin: 0;
    min-height: 1px;
    overflow: hidden;
    padding: 0;
    width: 275px;
    z-index: 999;
    border-radius: 3px;
}

ul.token-input-list-facebook li input {
    background-color: #FFFFFF;
    border: 0 none;
    margin: 2px 0;
    padding: 3px 8px;
    width: 100px;
}

div.token-input-dropdown-facebook {
	width: 275px;
}

<!--
#variant_master {
	float: left; 
	width: 100%;
}

#variant_master .variant_div {
	float: left;
	margin-left: 220px;
}

#variant_master .shades_variant {
	float: left;
	width: 130px;
}

#variant_master .pack_size_variant {
	float: left;
	/* width: 265px; */
	outline: medium none;
    width: 30px
}

.remove_variants {
	margin-left: 10px; 
	cursor: pointer;
	float: left;
}
-->
</style>

<script type="text/javascript">

/* var variants = "";//${variantNames}
var shades = ${shades} */
var counter = 1;

	$(function() {
		$('#sku').alphanumeric();
		$('#productPriceAmount').numeric({allow:"."});
		$('#quantity').numeric();
		$('#ordermin').numeric();
		$('#ordermax').numeric();
		$('#order').numeric();
		$('#weight').numeric({allow:"."});
		$('#width').numeric({allow:"."});
		$('#length').numeric({allow:"."});
		$('#hight').numeric({allow:"."});
		<c:forEach items="${product.descriptions}" var="description" varStatus="counter">		
			$("#name${counter.index}").friendurl({id : 'seUrl${counter.index}'});
		</c:forEach>
		
		
		$('#productHaveVariant').click(function() {
			if($('#productHaveVariant').is(":checked")) {
				$('#productHaveVariants').val(true);
			} else {
				$('#productHaveVariants').val(false);
			}
			
			displayVaraint();
		});
	});
	
</script>

<div class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />
	
	<div class="tab-content">
		<div class="tab-pane active" id="catalogue-section">
			<div class="sm-ui-component">
				<c:if test="${product.product.id!=null && product.product.id>0}">
					<c:set value="${product.product.id}" var="productId" scope="request"/>
					<jsp:include page="/pages/admin/products/product-menu.jsp" />
				</c:if>
					
				<h3 style="margin-bottom: 5px;">
					<c:choose>
						<c:when test="${product.product.id!=null && product.product.id>0}">
							<s:message code="label.product.edit" text="Edit product" /> <c:out value="${product.product.sku}"/>
						</c:when>
						<c:otherwise>
							<s:message code="label.product.create" text="Create product" />
						</c:otherwise>
					</c:choose>
				</h3>	
				<%-- <br/>
				<c:if test="${product.product.id!=null && product.product.id>0}">
					<c:forEach items="${product.descriptions}" var="description" varStatus="counter">
						<strong><sm:productUrl productDescription="${description}" /></strong><br/>
					</c:forEach>
				</c:if>
				<br/><br/> --%>
				<c:url var="productSave" value="/admin/products/save.html"/>
				
				<form:form method="POST" enctype="multipart/form-data" commandName="product" action="${productSave}">
					<form:errors path="*" cssClass="alert alert-error" element="div" />
					<div id="store.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>   
					<div id="store.error" class="alert alert-error" style="display:none;"><s:message code="message.error" text="An error occured"/></div>
					
					<div class="control-group">
						<label><s:message code="label.product.sku" text="Sku"/></label>
						<div class="controls">
							<form:input cssClass="input-large highlight" id="sku" path="product.sku"/>
							<span class="help-inline"><s:message code="label.generic.alphanumeric" text="Alphanumeric" /><form:errors path="product.sku" cssClass="error" /></span>
						</div>
					</div>
					
					<form:hidden path="product.id" class="productId"/>
					
					<div class="control-group">
						<label><s:message code="label.product.available" text="Product available"/></label>
						<div class="controls">
							<form:checkbox path="product.available" />
						</div>
					</div>
					<%-- <div class="control-group">
						<label><s:message code="label.product.availabledate" text="Date available"/></label>
						<div class="controls">
							<input id="dateAvailable" name="dateAvailable" value="${product.dateAvailable}" class="small" type="text" data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>" data-datepicker="datepicker"> 
							<script type="text/javascript">
								$('#dateAvailable').datepicker();
							</script>
							<span class="help-inline"><form:errors path="dateAvailable" cssClass="error" /></span>
						</div>
					</div> --%>
					<div class="control-group">
						<label><s:message code="label.product.manufacturer" text="Manufacturer"/></label>
						<div class="controls">
							<form:select items="${manufacturers}" itemValue="id" itemLabel="descriptions[0].name"  path="product.manufacturer.id"/> 
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="control-group">
						<label><s:message code="label.productedit.producttype" text="Product type"/></label>
						<div class="controls">
							<form:select items="${productTypes}" itemValue="id" itemLabel="code"  path="product.type.id"/> 
							<span class="help-inline"></span>
						</div>
					</div>
					
					<c:forEach items="${product.descriptions}" var="description" varStatus="counter">
						<div class="control-group">
							<label class="required"><s:message code="label.productedit.productname" text="Product name"/> (<c:out value="${description.language.code}"/>)</label>
							<div class="controls">
								<form:input cssClass="input-large highlight" id="name${counter.index}" path="descriptions[${counter.index}].name"/>
								<span class="help-inline"><form:errors path="descriptions[${counter.index}].name" cssClass="error" /></span>
							</div>
						</div>
						<div class="control-group">
							<label class="required"><s:message code="label.sefurl" text="Search engine friendly url"/> (<c:out value="${description.language.code}"/>)</label>
							<div class="controls">
								<form:input id="seUrl${counter.index}" cssClass="input-large" path="descriptions[${counter.index}].seUrl"/>
								<span class="help-inline"><form:errors path="descriptions[${counter.index}].seUrl" cssClass="error" /></span>
							</div>
						</div>
						<div class="control-group">
							<label class="required"><s:message code="label.productedit.producthl" text="Product highlight"/> (<c:out value="${description.language.code}"/>)</label>
							<div class="controls">
								<form:input cssClass="input-large" path="descriptions[${counter.index}].productHighlight"/>
								<span class="help-inline"><form:errors path="descriptions[${counter.index}].productHighlight" cssClass="error" /></span>
							</div>
						</div>
						<div class="control-group">
							<label class="required"><s:message code="label.productedit.productdesc" text="Product description"/> (<c:out value="${description.language.code}"/>)</label>
							<div class="controls">
								<textarea cols="30" id="descriptions${counter.index}.description" name="descriptions[${counter.index}].description">
									<c:out value="${product.descriptions[counter.index].description}"/>
								</textarea>
							</div>
							                     
							<script type="text/javascript">
								//<![CDATA[
								
									CKEDITOR.replace('descriptions[${counter.index}].description',
									{
										skin : 'office2003',
										toolbar : 
										[
											['Source','-','Save','NewPage','Preview'], 
											['Cut','Copy','Paste','PasteText','-','Print'], 
											['Undo','Redo','-','Find','-','SelectAll','RemoveFormat'], '/', 
											['Bold','Italic','Underline','Strike','-','Subscript','Superscript'], 
											['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'], 
											['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'], 
											['Link','Unlink','Anchor'], 
											['Image','Flash','Table','HorizontalRule','SpecialChar','PageBreak'], '/', 
											['Styles','Format','Font','FontSize'], ['TextColor','BGColor'], 
											['Maximize', 'ShowBlocks'] 
										],
										
										filebrowserWindowWidth : '720',
										filebrowserWindowHeight : '740',
										filebrowserImageBrowseUrl :    '<c:url value="/admin/content/fileBrowser.html"/>'
									});
								//]]>
							</script>
						</div>
						
						<div class="control-group">
							<label class="required"><s:message code="is.variation" text="This product has multiple variations"/></label>
							<div class="controls">
								<input type="checkbox" id="productHaveVariant"/>
								<form:hidden path="productHaveVariants" vlaue=""/>
							</div>
						</div>
						
						<div class="control-group" id="product_variant" style="display: none;">
							<div id="variant_lable" style="float: left; width: 100%; margin-bottom: 5px;">
								<span style="float: left; width: 220px;"><label class="required"><s:message code="is.variation" text="Product Variations"/></label></span>
								<span style="float: left; width: 150px;"><label class="required"><s:message code="is.variation" text="Type"/></label></span>
								<span style="float: left; width: 275px;"><label class="required"><s:message code="is.variation" text="Value"/></label></span>
								<span style="float: left; cursor: pointer;">
									<label class="required" style="cursor: pointer; color: #0000FF; font: 12px 'Trebuchet MS',icon;" onclick="addMoreVariant();">+ add more</label>
								</span>
							</div>
							<div id="variant_master">
								<div id="variant_div1" class="variant_div">
									<input type="text" id="shades_variant1" class="shades_variant" value=""/>
									<input type="hidden" id="shadeId1" value=""/>
									<div id="packId1" class="sm input" style="float: left; height: 28px; margin-left: 10px; width: 275px; z-index: 1200;">
										<input type="text" id="pack_size_variant1" class="pack_size_variant"/>
									</div>
									<span class="remove_variants" onclick="removeVariant(this);">&#x2716;</span>
								</div>
							</div>
							<form:hidden path="productVariants" vlaue=""/>
							<!-- <input type="hidden" id="productVariants" name="productVariants" value=""/> -->
						</div>
						
						<div class="control-group">
							<label class="required"><s:message code="label.product.title" text="Product title"/> (<c:out value="${description.language.code}"/>)</label>
							<div class="controls">
								<form:input cssClass="input-large" path="descriptions[${counter.index}].metatagTitle"/>
								<span class="help-inline"><form:errors path="descriptions[${counter.index}].metatagTitle" cssClass="error" /></span>
							</div>
						</div>
						<div class="control-group">
							<label class="required"><s:message code="label.metatags.description" text="Metatag description"/> (<c:out value="${description.language.code}"/>)</label>
							<div class="controls">
								<form:input cssClass="input-large" path="descriptions[${counter.index}].metatagDescription"/>
								<span class="help-inline"><form:errors path="descriptions[${counter.index}].metatagDescription" cssClass="error" /></span>
							</div>
						</div>
						
						<form:hidden path="descriptions[${counter.index}].language.id" />
						<form:hidden path="descriptions[${counter.index}].language.code" />
						<form:hidden path="descriptions[${counter.index}].id" />
						<form:hidden path="descriptions[${counter.index}].metatagKeywords" />
						<form:hidden path="descriptions[${counter.index}].productExternalDl" />
					</c:forEach>
					
					
					
					<div class="control-group">
						<label class="required"><s:message code="label.product.price" text="Price"/></label>
						<div class="controls">
							<form:input id="productPriceAmount" cssClass="highlight" path="productPrice"/>
							<span id="help-price" class="help-inline"><form:errors path="productPrice" cssClass="error" /></span>
						</div>
					</div>
					<%-- <div class="control-group">
						<label><s:message code="label.productedit.qtyavailable" text="Quantity available"/></label>
						<div class="controls">
							<form:input id="quantity" cssClass="highlight" path="availability.productQuantity"/>
							<span class="help-inline"><form:errors path="availability.productQuantity" cssClass="error" /></span>
						</div>
					</div>
					<div class="control-group">
						<label><s:message code="label.product.ordermin" text="Quantity order minimum"/></label>
						<div class="controls">
							<form:input id="ordermin" cssClass="highlight" path="availability.productQuantityOrderMin"/>
							<span class="help-inline"><form:errors path="availability.productQuantityOrderMin" cssClass="error" /></span>
						</div>
					</div>
					<div class="control-group">
						<label><s:message code="label.product.ordermax" text="Quantity order maximum"/></label>
						<div class="controls">
							<form:input id="ordermax" cssClass="highlight" path="availability.productQuantityOrderMax"/>
							<span class="help-inline"><form:errors path="availability.productQuantityOrderMax" cssClass="error" /></span>
						</div>
					</div> --%>
					<div class="control-group">
						<label><s:message code="label.product.shipeable" text="Product will be shipped"/></label>
						<div class="controls">
							<form:checkbox path="product.productShipeable" />
						</div>
					</div>
					
					<form:hidden path="availability.region" />
					<form:hidden path="availability.id" />
					<form:hidden path="price.id" />
					
					<%-- <div class="control-group">
						<label><s:message code="label.product.weight" text="Weight"/></label>
						<div class="controls">
							<form:input id="weight" cssClass="" path="product.productWeight"/>
							<span class="help-inline"><form:errors path="product.productWeight" cssClass="error" /></span>
						</div>
					</div>
					<div class="control-group">
						<label><s:message code="label.product.height" text="Height"/></label>
						<div class="controls">
							<form:input id="height" cssClass="" path="product.productHeight"/>
							<span class="help-inline"><form:errors path="product.productHeight" cssClass="error" /></span>
						</div>
					</div>
					<div class="control-group">
						<label><s:message code="label.product.width" text="Width"/></label>
						<div class="controls">
							<form:input id="width" cssClass="" path="product.productWidth"/>
							<span class="help-inline"><form:errors path="product.productWidth" cssClass="error" /></span>
						</div>
					</div>
					<div class="control-group">
						<label><s:message code="label.product.length" text="Length"/></label>
						<div class="controls">
							<form:input id="length" cssClass="" path="product.productLength"/>
							<span class="help-inline"><form:errors path="product.productLength" cssClass="error" /></span>
						</div>
					</div>  --%>         
					<div class="control-group">
						<label><s:message code="label.entity.order" text="Sort order"/></label>
						<div class="controls">
							<form:input id="order" cssClass="" path="product.sortOrder"/>
							<span class="help-inline"><form:errors path="product.sortOrder" cssClass="error" /></span>
						</div>
					</div>                 
					<div class="control-group">
						<label><s:message code="label.product.image" text="Image"/>&nbsp;<c:if test="${product.productImage.productImage!=null && product.productImage.productImage!=''}"><span id="imageControlRemove"> - <a href="#" onClick="removeImage('${product.productImage.id}')"><s:message code="label.generic.remove" text="Remove"/></a></span></c:if></label>
						<div class="controls" id="imageControl">
							<c:choose>
								<c:when test="${product.productImage.productImage==null || product.productImage.productImage==''}">
									<input class="input-file" id="image" name="image" type="file">
								</c:when>
								<c:otherwise>
									<img src="<sm:productImage imageName="${product.productImage.productImage}" product="${product.product}"/>" width="200"/>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<form:hidden path="productImage.productImage" />
					<div class="control-group">
						<label><s:message code="label.taxclass" text="Tax class"/></label>
						<div class="controls">
							<form:select items="${taxClasses}" itemValue="id" itemLabel="code"  path="product.taxClass.id"/> 
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="form-actions">
						<div class="pull-right">
							<button type="submit" class="btn btn-success" onclick="return setupShadeVariant();"><s:message code="button.label.submit2" text="Submit"/></button>
						</div>
					</div>
				</form:form>
				
				<%-- <c:if test="${product.product.id!=null && product.product.id>0}">      
					<c:url var="createSimilar" value="/admin/products/product/duplicate.html"/>
					<form:form method="POST" enctype="multipart/form-data" commandName="product" action="${createSimilar}">
						<input type="hidden" name="productId" value="${product.product.id}" />
						<div class="form-actions">
							<div class="pull-right">
								<button type="submit" class="btn"><s:message code="label.product.createsimilar" text="Create similar product"/></button>
							</div>
						</div>
					</form:form>
				</c:if> --%>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		if($('#productHaveVariants').val() == "true") {
			$('#productHaveVariant').prop('checked', true);
			
			displayVaraint();
			displayVariantOnLoad();
		}
	</script>
</div>
