<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<div class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />
	
	<h3>
		<s:message code="label.billing.invoice.setting.title" text="Sales Invoice Settings" /></h3>
	<br/>
	<c:url var="saveInvoiceSetting" value="/admin/billing/saveinvoiceSetting.html"/>
	
	<form:form method="POST" commandName="invoiceSetting" action="${saveInvoiceSetting}">
		
		<form:errors path="*" cssClass="alert alert-error" element="div" />
		
		<div id="invoiceSetting.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>">
			<s:message code="message.success" text="Request successfull"/>
		</div>
		
		<div id="info">
			<div style="width: 100%; float: left;">
				<div style="width: 50%; float: left;">
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.billing.prefix" text="Prefix"/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large highlight" path="prefix" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="prefix" cssClass="error" /></span>
                        </div>
					</div>
				</div>
				<div style="width: 50%; float: left;">
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.billing.setting.type" text="Type"/></label>
						</div>
                        <div style="float: left;" class="controls">
                        	<form:select items="${typeList}" itemValue="id" itemLabel="text" path="type.id" style="width: 205px;"/> 
                        </div>
					</div>
				</div>
			</div>
			<div style="width: 100%; float: left;">
				<div style="width: 50%; float: left;">
					<div class="control-group" style="float: left; margin-bottom: 0px;">
						<div style="float: left; width: 120px;">
	                        <label style="margin-top: 5px;"><s:message code="label.billing.starting.number" text="Starting No."/></label>
						</div>
                        <div style="float: left;" class="controls">
         					<form:input cssClass="input-large highlight" path="startingNumber" cssStyle="width: 195px;"/>
	                        <span class="help-inline"><form:errors path="startingNumber" cssClass="error" /></span>
                        </div>
					</div>
				</div>
			</div>
		</div>
		
		<form:hidden path="id" />
		
		<div style="float: left; margin-top: 20px;">
       		<div class="pull-left">
       			<button type="submit" class="btn btn-success"><s:message code="button.label.submit2" text="Save"/></button>
       		</div>
  	 	</div>
		
	</form:form>
</div>
<div id="salse-invoice" class="tabbable" style="float: left; margin-top: 15px; height: 100px;">
	<h3>
		<s:message code="label.billing.invoice.setting.title" text="Available Number Series &#40;Sales Invoice&#41;" /></h3>
	<br/>
	
	<jsp:include page="/pages/admin/billing/invoicesettings.jsp" flush="false">
		<jsp:param name="listType" value="1"/>
	</jsp:include>

</div>

<div id="cash-invoice" class="tabbable" style="float: left; margin-top: 15px; height: 100px;">
	<h3>
		<s:message code="label.billing.invoice.setting.title" text="Available Number Series &#40;Cash Invoice&#41;" /></h3>
	<br/>
	
	<jsp:include page="/pages/admin/billing/invoicesettings.jsp" flush="false">
		<jsp:param name="listType" value="2"/>
	</jsp:include>

</div>

<script type="text/javascript">
$('.sectionStack').attr('style', '-moz-box-sizing: border-box; height: 175px; left: 0; overflow: hidden; padding: 0; position: absolute; top: 0; width: 700px; z-index: 200198;');
</script>	
