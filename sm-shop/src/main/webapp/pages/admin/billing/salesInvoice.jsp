<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<h3><s:message code="label.billing.invoice.setting.title" text="Sales Invoice: ${invoiceTypeMap['siNumberSeries']}" /></h3><br/>

<form:form method="POST" commandName="salesInvoice" action="${saveInvoice}">
	
	<form:errors path="*" cssClass="alert alert-error" element="div" />
		
	<div id="salesInvoice.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>">
		<s:message code="message.success" text="Request successfull"/>
	</div>
	
	<div id="info">
		<div style="width: 100%; float: left;">
			<div style="width: 195px; float: left;">
				<div class="control-group" style="float: left; margin-bottom: 0px;">
					<label><s:message code="label.billing.prefix" text="Customer"/></label>
					<div style="float: left;" class="controls">
						<form:input cssClass="input-large highlight" path="customer.company" cssStyle="width: 185px;"/>
					</div>
				</div>
			</div>
			<div style="width: 105px; float: left; margin-left: 25px;">
				<div class="control-group" style="float: left; margin-bottom: 0px;">
					<label><s:message code="label.billing.prefix" text="Date"/></label>
					<div style="float: left;" class="controls">
						<form:input cssClass="input-large highlight" path="invoiceDate" cssStyle="width: 95px;"/>
					</div>
				</div>
			</div>
			<div style="width: 105px; float: left; margin-left: 25px;">
				<div class="control-group" style="float: left; margin-bottom: 0px;">
					<label><s:message code="label.billing.prefix" text="Due Date"/></label>
					<div style="float: left;" class="controls">
						<form:input cssClass="input-large" path="dueDate" cssStyle="width: 95px;"/>
					</div>
				</div>
			</div>
			<div style="width: 165px; float: left; margin-left: 25px;">
				<div class="control-group" style="float: left; margin-bottom: 0px;">
					<label><s:message code="label.billing.prefix" text="Ref. No.:(Optional)"/></label>
					<div style="float: left;" class="controls">
						<form:input cssClass="input-large" path="order.id" cssStyle="width: 155px;"/>
					</div>
				</div>
			</div>
		</div>
		<div style="width: 100%; float: left;">
			<div style="width: 325px; float: left;">
				<div class="control-group" style="float: left; margin-bottom: 0px;">
					<label><s:message code="label.billing.prefix" text="Comments:"/></label>
					<div style="float: left;" class="controls">
						<form:textarea cssClass="input-large" path="commant" cssStyle="width: 315px;"/>
					</div>
				</div>
			</div>
		</div>
		
		<jsp:include page="/pages/admin/billing/productInfo.jsp" />
		
	</div>

</form:form>