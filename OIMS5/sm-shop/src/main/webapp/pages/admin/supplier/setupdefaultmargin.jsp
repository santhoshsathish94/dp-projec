<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/jquery.ui.theme.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/jquery.ui.accordion.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/themes/base/jquery.ui.menu.css" />">

<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.core.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.widget.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.position.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.menu.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap/jquery/ui/jquery.ui.autocomplete.js" />"></script>


<style>
.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	max-width: 205px;
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
</style>

<script type="text/javascript">
$(function() {
	$( "#customerAccountName" ).autocomplete({
		source: function( request, response ) {
			$.ajax({
				type: 'POST',
				url: '<c:url value="/admin/supplier/loadAjaxData.html"/>',
				data: {fieldValue: $('#customerAccountName').val(), searchFor: 'customerAccount'},
				dataType: 'json',
				success: function(data) {
					response($.map(data, function(item) {
						return {
							value: item
						}
					}));
					
				}
			})
		},
		minLength: 2
	});
	$( "#productName" ).autocomplete({
		source: function( request, response ) {
			$.ajax({
				type: 'POST',
				url: '<c:url value="/admin/supplier/loadAjaxData.html"/>',
				data: {fieldValue: $('#productName').val(), searchFor: 'item'},
				dataType: 'json',
				success: function(data) {
					response($.map(data, function(item) {
						return {
							value: item
						}
					}));
					
				}
			})
		},
		minLength: 2
	});
	$( "#companyName" ).autocomplete({
		source: function( request, response ) {
			$.ajax({
				type: 'POST',
				url: '<c:url value="/admin/supplier/loadAjaxData.html"/>',
				data: {fieldValue: $('#companyName').val(), searchFor: 'company'},
				dataType: 'json',
				success: function(data) {
					response($.map(data, function(item) {
						return {
							value: item
						}
					}));
					
				}
			})
		},
		minLength: 2
	});
});
</script>


<div class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />
	
		<h3>
			<c:choose>
			<c:when test="${partyItemDefaultMargin.id == null}">
				<s:message code="label.sales.channel.default.margin.title" text="Set-up New Default Margin" /></h3>
			</c:when>
			<c:otherwise>
				<s:message code="label.sales.channel.default.margin.title.update" text="Edit Default Margin" /></h3>
			</c:otherwise>
		</c:choose>
			
	<br/>

	<c:url var="saveSupplierDetails" value="/admin/supplier/savesaleschanneldefaultmargin.html"/>
	
	<form:form method="POST" commandName="partyItemDefaultMargin" action="${saveSupplierDetails}">
		<form:errors path="*" cssClass="alert alert-error" element="div" />
		<div id="supplier.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>
		
		<div id="info">
			<div style="width: 100%; float: left;">
				<div style="width: 50%; float: left;">
				
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.sales.channel.default.margin.name" text="Party Name"/></label>
						</div>
                        <div style="float: left; width: 205px;" class="controls">
                        	<%-- <input type="text" class="input-large highlight" id="customerName" value="${partyItemDefaultMargin.customer.company}" style="float: left; width: 195px;" onkeyup="searchCustomerName();"/>
                        	<input id="customerId" name="customerId" value="${partyItemDefaultMargin.customer.id}" type="hidden"> --%>
         					<form:input cssClass="input-large highlight" path="customerAccountName" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="customerAccountName" cssClass="error" /></span>
                        </div>
					</div>
				
				</div>
				
				<div style="width: 50%; float: left;">
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.sales.channel.default.margin.item.name" text="Item Name"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<%-- <input type="text" class="input-large highlight" id="itemName" value="${partyItemDefaultMargin.product.sku }" style="float: left; width: 195px;" />
         					<input id="productId" name="productId" value="${partyItemDefaultMargin.product.id}" type="hidden"> --%>
         					<form:input cssClass="input-large highlight" path="productName" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="productName" cssClass="error" /></span>
                        </div>
					</div>					
				</div>
			</div>
			
			<div style="width: 100%; float: left;">
				<div style="width: 50%; float: left;">
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.sales.channel.default.margin.company.name" text="Company Name"/></label>
						</div>
                        <div style="float: left;" class="controls">
                        	<%-- <input type="text" class="input-large highlight" id="itemcompanyName" value="${partyItemDefaultMargin.company.companyDisplayName }" style="float: left; width: 195px;" />
                        	<input type="hidden" class="input-large highlight" id="companyId" name="companyId" value="${partyItemDefaultMargin.company.id }"> --%>
                        	<form:input cssClass="input-large highlight" path="companyName" cssStyle="float: left; width: 195px;"/>
                        	<span class="help-inline"><form:errors path="companyName" cssClass="error" /></span>
                        	<%-- <form:select items="${currencies}" itemValue="id" itemLabel="code" path="currency.id" style="width: 205px;"/> --%> 
         					<%-- <form:select cssClass="currency-list" path="currency.code" style="width: 205px;"/> --%> 
                        </div>
					</div>
				</div>
				
				<div style="width: 50%; float: left;">
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.sales.channel.default.margin.cd" text="C.D. (&#37;)"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="cdField" cssStyle="width: 195px;"/>
                        </div>
					</div>				
				</div>
			</div>
			
			<div style="width: 100%; float: left;">
				<div style="width: 50%; float: left;">
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.sales.channel.default.margin.add" text="ADD. (&#37;)"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="addField" cssStyle="width: 195px;"/>
                        </div>
					</div>
				</div>
				
				<div style="width: 50%; float: left;">
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.sales.channel.default.margin.td" text="T.D. (&#37;)"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="tdField" cssStyle="width: 195px;"/>
                        </div>
					</div>				
				</div>
			</div>
			
			<div style="width: 100%; float: left;">
				<div style="width: 50%; float: left;">
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.sales.channel.default.margin.rate" text="Rate"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="rateField" cssStyle="width: 195px;"/>
                        </div>
					</div>
				</div>
			</div>
			
		</div>
		
		<form:hidden path="id" />
		
		<div style="float: left; margin-top: 20px;">
       		<div class="pull-left">
       			<button type="submit" class="btn btn-success"><s:message code="button.label.submit2" text="Save or Update"/></button>
       		</div>
  	 	</div>
		
		
	</form:form>

</div>