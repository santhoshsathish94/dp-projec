<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>

<html>
<head>
	<script src="<c:url value="/resources/js/jquery-1.10.2.min.js"/>"></script>
	<link href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />" rel="stylesheet"></link>
	<script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>
</head>

<div class="tabbable">
	<jsp:include page="/common/adminTabs.jsp" />
	<h3>
		<s:message code="" text="New Payment" />
	</h3>
	<br />
</div>
<c:url var="saveURL" value="/admin/account/createPayment.html" />
<form:form method="POST" commandName="stock" action="${saveURL}">

	<div style="float: left; width: 100%;">
		
		<div style="float: left; width: 290px;">
			<label style="margin-top: 0px;"><s:message	code="" text="To"/></label>
		</div>		
	
		<div style="float: left; width: 250px; ">
			<label style="margin-top: 0px;"><s:message	code="" text="Date"/></label>
		</div>		

	</div>
	
	<div style="float: left; width: 100%; margin-top: 0px;">
	
		<div style="float: left; width: 290px;" class="controls" >
			<form:input cssClass="input-large" path=""	cssStyle="width: 250px;" />
			<span class="help-inline"><form:errors path=""	cssClass="error" /></span>
		</div>
	
		<div style="float: left;"	class="controls">
			<form:input style="width: 80px;" class="small" type="text"
				data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>"
				data-datepicker="datepicker" path="" />
			<span class="help-inline"><form:errors path=""	cssClass="error" /></span>
		</div>

	</div>
	
	<div style="float: left; width: 100%;">
		
		<div style="float: left; width: 400px;">
			<label style="margin-top: 0px;font-size:10px;"><font color="blue"><s:message code="" text="Start typing 2-3 letters of the name to show the available names."/></font></label>
		</div>				

	</div>
	
	<div style="float: left; width: 100%;">
		
		<div style="float: left; width: 290px;">
			<label style="margin-top: 0px;"><s:message	code="" text="Amount"/></label>
		</div>		
	
		<div style="float: left; width: 250px; ">
			<label style="margin-top: 0px;"><s:message	code="" text="Mode of Payment"/></label>
		</div>		

	</div>
	
	<div style="float: left; width: 100%; margin-top: 0px;">
	
		<div style="float: left; width: 290px;" class="controls" >
			<form:input cssClass="input-large" path=""	cssStyle="width: 250px;" />
			<span class="help-inline"><form:errors path=""	cssClass="error" /></span>
		</div>
	
		<div style="float: left;"	class="controls">
			<form:select cssClass="country-list highlight" path="">
				<form:option value="Cash">Cash</form:option>
				<form:option value="Bank">Bank</form:option>
			</form:select>
			<span class="help-inline"><form:errors path=""	cssClass="error" /></span>
		</div>

	</div>
	
	<div style="float: left; width: 100%;">
		
		<div style="float: left; width: 290px;">
			<label style="margin-top: 0px;"><s:message	code="" text="Transaction No."/></label>
		</div>		
	
		<div style="float: left; width: 250px; ">
			<label style="margin-top: 0px;"><s:message	code="" text="Comments"/></label>
		</div>		

	</div>
	
	<div style="float: left; width: 100%; margin-top: 0px;">
	
		<div style="float: left; width: 290px;" class="controls" >
			<form:input cssClass="input-large" path=""	cssStyle="width: 250px;" />
			<span class="help-inline"><form:errors path=""	cssClass="error" /></span>
		</div>
	
		<div style="float: left;" class="controls">
			<form:textarea cssClass="input-large" path="" cssStyle="width: 250px;" />
			<span class="help-inline"><form:errors path=""	cssClass="error" /></span>
		</div>

	</div>
		
	<div class="control-group" style="float: left; width: 100%;">
		<div class="pull-left" style="width: 100%; margin-top: 10px;">
			<button type="submit" class="btn btn-success" onclick="return createStockEntryJson();">
				<s:message code="button.label.submit2" text="Save" />
			</button>
		</div>
	</div>
		
</form:form>

			
<script>
	
		
</script>