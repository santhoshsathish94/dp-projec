<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>

<html>
<head>
<script src="<c:url value="/resources/js/jquery-1.10.2.min.js"/>"></script>
<link
	href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />"
	rel="stylesheet"></link>
<script
	src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>
</head>

<div class="tabbable">
	<jsp:include page="/common/adminTabs.jsp" />
	<h3>
		<s:message code="" text="New Debit Note (Againt Others)" />
	</h3>
	<br />
</div>
<c:url var="saveURL"
	value="/admin/inventoryManagement/createdebitnoteother.html" />
<form:form method="POST" commandName="debitNoteOther"
	action="${saveURL}">
	<div class="control-group" style="float: left; width: 100%;">

		<div style="float: left; width: 100px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code=""
					text="Name (Debit To)" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="debit_supplier"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="debit_supplier"
					cssClass="error" /></span>
		</div>

		<div style="float: left; width: 40px; margin-top: 15px;">
			<label><s:message code="" text="Date" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input style="width: 80px;" class="small" type="text"
				data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>"
				data-datepicker="datepicker" path="debit_Sdate" />
			<span class="help-inline"><form:errors path="debit_Sdate"
					cssClass="error" /></span>
		</div>

		<div style="float: left; width: 60px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code=""
					text="Ref No." /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="debit_ref_number"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="debit_ref_number"
					cssClass="error" /></span>
		</div>
	</div>

	<div class="control-group" style="float: left; width: 100%;">

		<div style="float: left; width: 100px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code=""
					text="Ammount" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="debit_amount"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="debit_amount"
					cssClass="error" /></span>
		</div>


		<div style="float: left; width: 60px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code="" text="Tax" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="debit_tax"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="debit_tax"
					cssClass="error" /></span>
		</div>
	</div>
	<div class="control-group" style="float: left; width: 100%;">

		<div style="float: left; width: 100px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code=""
					text="To Credit To" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="debit_credit_to"
				cssStyle="width: 300px;" />
			<span class="help-inline"><form:errors path="debit_credit_to"
					cssClass="error" /></span>
		</div>

	</div>

	<div class="control-group" style="float: left; width: 100%;">

		<div style="float: left; width: 150px; margin-top: 15px; margin-left: 300px;">
			<label style="margin-top: 0px;"><s:message code=""
					text="Total Debit Note Amount" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="debit_total_amount"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors
					path="debit_total_amount" cssClass="error" /></span>
		</div>

	</div>

	<div class="pull-left" style="width: 100%; margin-top: 10px;">
		<button type="submit" class="btn btn-success">
			<s:message code="button.label.submit2" text="Save" />
		</button>
	</div>
</form:form>


<script>
	

	
</script>