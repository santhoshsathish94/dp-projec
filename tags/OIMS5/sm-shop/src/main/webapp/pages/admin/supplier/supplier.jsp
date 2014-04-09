<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<script type="text/javascript">

$(document).ready(function() {
	
	<c:choose>
	<c:when test="${supplier.currency!=null && supplier.currency!=''}">
		$('.currency-list').hide();          
		$('#currency').show(); 
		$('#currency').val('<c:out value="${supplier.currency}"/>');
	</c:when>
	<c:otherwise>
		$('.currency-list').show();           
		$('#currency').hide();
	</c:otherwise>
	</c:choose>
	
	<c:choose>
	<c:when test="${supplier.zone!=null && supplier.zone!=''}">
		$('.zone-list').show();          
		$('#supplierZone').hide(); 
		getZones('<c:out value="${supplier.country.isoCode}" />'); 
		//$('#supplierZone').val('<c:out value="${supplier.zone}"/>');
	</c:when>
	<c:otherwise>
		$('.zone-list').show();           
		$('#supplierZone').hide();
		getZones('<c:out value="${supplier.country.isoCode}" />'); 
	</c:otherwise>
	</c:choose>

	$(".country-list").change(function() {
		getZones($(this).val());
    })

});

$.fn.addItems = function(data) {
    $(".zone-list > option").remove();
        return this.each(function() {
            var list = this;
            $.each(data, function(index, itemData) {
                var option = new Option(itemData.name, itemData.code);
                list.add(option);
            });
     });
};

function getZones(countryCode){
	$.ajax({
	  type: 'POST',
	  url: '<c:url value="/admin/reference/provinces.html"/>',
	  data: 'countryCode=' + countryCode,
	  dataType: 'json',
	  success: function(response){

			var status = isc.XMLTools.selectObjects(response, "/response/status");
			if(status==0 || status ==9999) {
				
				var data = isc.XMLTools.selectObjects(response, "/response/data");
				if(data && data.length>0) {
					
					$('.zone-list').show();  
					$('#supplierZone').hide();
					$(".zone-list").addItems(data);
					<c:if test="${supplier.zone!=null}">
						$('.zone-list').val('<c:out value="${supplier.zone.code}"/>');
						$('#supplierZone').val('');
					</c:if>
				} else {
					$('.zone-list').hide();             
					$('#supplierZone').show();
					<c:if test="${supplier.zone!=null}">
						$('#supplierZone').val('<c:out value="${supplier.zone}"/>');
					</c:if>
				}
			} else {
				$('.zone-list').hide();             
				$('#supplierZone').show();
			}
	  },
	  error: function(xhr, textStatus, errorThrown) {
	  	alert('error ' + errorThrown);
	  }
	  
	});
}

function selectCountry(value) {
	$('#country').val(value);
}

</script>


<div class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />
	
		<h3>
			<c:choose>
			<c:when test="${supplier.id == null}">
				<s:message code="label.supplier.title" text="New Supplier" /></h3>
			</c:when>
			<c:otherwise>
				<s:message code="label.supplier.title.update" text="Edit Supplier" /></h3>
			</c:otherwise>
		</c:choose>
			
	<br/>

	<c:url var="saveSupplierDetails" value="/admin/company/saveSupplier.html"/>
	
	<form:form method="POST" commandName="supplier" action="${saveSupplierDetails}">
		<form:errors path="*" cssClass="alert alert-error" element="div" />
		<div id="supplier.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>
		
		<div id="info">
			<div style="width: 100%; float: left;">
				<div style="width: 50%; float: left;">
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.name" text="Name"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large highlight" path="supplierName" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="supplierName" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.opening.balance" text="Opening Banalce"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="openingBalance" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="openingBalance" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.currency" text="Currency"/></label>
						</div>
                        <div style="float: left;" class="controls">
                        	<form:select items="${currencies}" itemValue="id" itemLabel="code" path="currency.id" style="width: 205px;"/> 
         					<%-- <form:select cssClass="currency-list" path="currency.code" style="width: 205px;"/> --%> 
                        </div>
					</div>
				</div>
				<div style="width: 50%; float: left;">
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.pan.no" text="PAN No:"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="panNumber" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="panNumber" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.tin.number" text="TIN No:"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="tinNumber" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="tinNumber" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.sevice.tax.number" text="Service Tax No:"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="serviceTaxNumber" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="serviceTaxNumber" cssClass="error" /></span>
                        </div>
					</div>
				</div>
			</div>
			
			<div style="width: 100%; float: left; margin-top: 20px;">
				<h4><s:message code="label.supplier.contact.title" text="Contact Address" /></h4>
				<div style="width: 50%; float: left; margin-top: 10px;">
					
					<%-- <div class="control-group" style="float: left; width: 100%; margin-bottom: 10px;">
						<div style="float: left; width: 100%;" class="controls">
						
       						<div style="float: left; width: 49%;">
       							<input type="radio" id="countryIndia" name="selcountry" style="float: left;" checked="checked" onclick="selectCountry('India');"/><span style="float: left; margin-left: 3px;">India</span>
       						</div>
       						<div style="float: left; width: 49%;">
       							<input type="radio" id="countryOther" name="selcountry" style="float: left;" onclick="selectCountry('Other');"/><span style="float: left; margin-left: 3px;">Other</span>
       						</div>
         					
         					<form:input cssClass="input-large highlight" path="country" cssStyle="width: 195px; display: none;" />
                        </div>
					</div> --%>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.country" text="Country"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:select cssClass="country-list" path="country.isoCode"  cssStyle="width: 205px;">
								<form:options items="${countries}" itemValue="isoCode" itemLabel="name"/>
   							</form:select>
                        </div>
					</div>
					
					<%-- <div class="control-group" style="float: left; width: 100%; margin-bottom: 10px;">
	                    <label><s:message code="label.supplier.country" text="Country"/></label>
	                    <div class="controls">
                 			<form:select cssClass="country-list highlight" path="country.isoCode">
								<form:options items="${countries}" itemValue="isoCode" itemLabel="name"/>
   							</form:select>
	                     </div>
	                </div> --%>
					
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.addressLabel" text="Address Label:"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large highlight" path="addressLabel" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="addressLabel" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.mobileNumber" text="Mobile Number"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="mobileNumber" cssStyle="width: 195px;"/>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.workNumber" text="Work Number"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="workNumber" cssStyle="width: 195px;"/>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.homeNumber" text="Home Number"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="homeNumber" cssStyle="width: 195px;"/>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.email" text="Email"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="email" cssStyle="width: 195px;"/>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.alternateEmail" text="Alternate Email"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="alternateEmail" cssStyle="width: 195px;"/>
                        </div>
					</div>
					
				</div>
				
				<div style="width: 50%; float: left; margin-top: 40px;">
					<span style="float: left; margin-bottom: 10px;">
						<h4><s:message code="label.supplier.address.title" text="Postal Address" /></h4>
					</span>
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.address" text="Address:"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="address" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="address" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.city" text="City:"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="city" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="city" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.zone" text="State:"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:select cssClass="zone-list" path="zone.code" style="width: 205px;"/>
         					<input type="text" class="input-large" id="supplierZone" name="supplierZone" value="${supplier.zone}" style="width: 195px;"/>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.supplier.postalcode" text="Postal Code:"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="postalCode" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="postalCode" cssClass="error" /></span>
                        </div>
					</div>
					
				</div>
			</div>
		</div>
		
		<form:hidden path="id" />
		
		<div>
       		<div class="pull-left">
       			<button type="submit" class="btn btn-success"><s:message code="button.label.submit2" text="Save or Update"/></button>
       		</div>
  	 	</div>
		
		
	</form:form>

</div>