<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<link href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />" rel="stylesheet"></link>
<script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>

<script>

$(document).ready(function() {
	
	if($("#code").val()=="") {
		$('.btn').addClass('disabled');
	}
	
	<c:choose>
	<c:when test="${company.companyStateProvince!=null && company.companyStateProvince!=''}">
		$('.zone-list').hide();          
		$('#companyStateProvince').show(); 
		$('#companyStateProvince').val('<c:out value="${company.companyStateProvince}"/>');
		getZones('IN');
	</c:when>
	<c:otherwise>
		$('.zone-list').show();           
		$('#companyStateProvince').hide();
		getZones('IN'); 
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
					$('#companyStateProvince').hide();
					$(".zone-list").addItems(data);
					<c:if test="${company.companyZone!=null}">
						$('.zone-list').val('<c:out value="${company.companyZone.code}"/>');
						$('#companyStateProvince').val('');
					</c:if>
				} else {
					$('.zone-list').hide();             
					$('#companyStateProvince').show();
					<c:if test="${company.companyStateProvince!=null}">
						$('#companyStateProvince').val('<c:out value="${company.companyStateProvince}"/>');
					</c:if>
				}
			} else {
				$('.zone-list').hide();             
				$('#companyStateProvince').show();
			}
	  },
	  error: function(xhr, textStatus, errorThrown) {
	  	alert('error ' + errorThrown);
	  }
	  
	});
}

function validateCode() {
	$('#checkCodeStatus').html('<img src="<c:url value="/resources/img/ajax-loader.gif" />');
	$('#checkCodeStatus').show();
	var storeCode = $("#code").val();
	var id = $("#id").val();
	//checkCode(storeCode,id,'<c:url value="/admin/store/checkStoreCode.html" />');
}

function callBackCheckCode(msg,code) {
	
	if(code==0) {
		$('.btn').removeClass('disabled');
	}
}


</script>


<div class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />
	
	<h3><s:message code="label.company.title" text="Company Details" /></h3>	
	<br/>

	<c:url var="saveCompanyDetails" value="/admin/company/save.html"/>
	
	<form:form method="POST" commandName="company" action="${saveCompanyDetails}">
		<form:errors path="*" cssClass="alert alert-error" element="div" />
		<div id="company.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>
		
		<div id="info">
			<div style="width: 100%; float: left;">
				<div style="width: 50%; float: left;">
					<h4><s:message code="label.company.address.title" text="Company Address" /></h4>
					
					<div class="control-group" style="margin-top: 20px; float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companyzone" text="State or Province"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:select cssClass="zone-list highlight" path="companyZone.code" style="width: 205px;"/>
         					<input type="text" class="input-large highlight" id="companyStateProvince" name="companyStateProvince" value="${company.companyStateProvince}" style="width: 195px;"/> 
                   			<span class="help-inline"><form:errors path="companyZone.code" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px; margin-top: 20px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companyaddress" text="Address"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:textarea cssClass="input-large" path="companyAddress" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyAddress" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companycity" text="City"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companyCity" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyCity" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companypostalcode" text="Postal Code"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companyPostalCode" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyPostalCode" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companyworknumber" text="Work No."/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companyWorkNumber" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyWorkNumber" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companymobilenumber" text="Mobile No."/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companyMobileNumber" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyMobileNumber" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companyfaxnumber" text="Fax No."/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companyFaxNumber" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyFaxNumber" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companyemailaddress" text="Email"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large highlight" path="companyEmailAddress" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyEmailAddress" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companyalternateemailaddress" text="Alternate Email"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companyAlternateEmailAddress" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyAlternateEmailAddress" cssClass="error" /></span>
                        </div>
					</div>
				</div>
				<div style="width: 50%; float: left; margin-top: 35px;">
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companydisplayname" text="Display Name"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large highlight" path="companyDisplayName" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyDisplayName" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companytradingname" text="Legal / Trading Name"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large highlight" path="companyTradingName" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyTradingName" cssClass="error" /></span>
                        </div>
					</div>
					
					<label style="margin-top: 20px; float: left;">
						<h4><s:message code="label.company.registration.title" text="Registration Number" /></h4>
					</label>
					
					<div class="control-group" style="float: left; margin-bottom: 0px; margin-top: 5px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companyroc" text="ROC"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companyROC" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyROC" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px; margin-top: 5px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companypan" text="PAN"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companyPAN" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyPAN" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px; margin-top: 5px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companytan" text="TAN"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companyTAN" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyTAN" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px; margin-top: 5px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companytin" text="TIN"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companyTIN" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyTIN" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px; margin-top: 5px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companystnumber" text="ST No."/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large" path="companySTNumber" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companySTNumber" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="float: left; margin-bottom: 0px; margin-top: 5px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.companytype" text="Company Type"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large highlight" path="companyType" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="companyType" cssClass="error" /></span>
                        </div>
					</div>
					
					<!-- Hidden Files Data : Start -->
					
					<div class="control-group" style="display: none;">
	                    <label><s:message code="label.storecountry" text="Store Country"/></label>
	                    <div class="controls">
                 			<form:select cssClass="country-list highlight" path="companyCountry.isoCode">
							<form:options items="${countries}" itemValue="isoCode" itemLabel="name"/>
   							</form:select>
	                     </div>
	                </div>
					
					<!-- Hidden Files Data : End -->
					
				</div>
			</div>
		</div>
		
		<form:hidden path="id" />
		<form:hidden path="code"/>
		
		<div>
       		<div class="pull-left">
       			<button type="submit" class="btn btn-success"><s:message code="button.label.submit2" text="Save or Update"/></button>
       		</div>
  	 	</div>
		
		
	</form:form>

</div>