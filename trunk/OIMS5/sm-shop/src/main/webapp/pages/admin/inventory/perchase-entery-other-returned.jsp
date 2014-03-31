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
<c:url var="savePurchaseEntery"
	value="/admin/inventoryManagement/createPurchaseEntry.html" />
<form:form method="POST" commandName="stock" action="${savePurchaseEntery}">
	<div class="control-group" style="float: left; width: 100%;">

		<div style="float: left; width: 100px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code=""
					text="Name (Debit To)" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="stockComment"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="stockComment"
					cssClass="error" /></span>
		</div>

		<div style="float: left; width: 40px; margin-top: 15px;">
			<label><s:message code="" text="Date" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input style="width: 80px;" class="small" type="text"
				data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>"
				data-datepicker="datepicker" path="stockSDate" />
			<span class="help-inline"><form:errors path="stockSDate"
					cssClass="error" /></span>
		</div>

		<div style="float: left; width: 60px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code=""
					text="Ref No." /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="stockComment"
				cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="stockComment"
					cssClass="error" /></span>
		</div>
	</div>
	
	<div class="control-group" style="float: left; width: 100%;">

		<div style="float: left; width: 100px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code=""	text="Ammount" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="stockComment"	cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="stockComment" cssClass="error" /></span>
		</div>

		
		<div style="float: left; width: 60px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code="" text="Tax" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="stockComment" cssStyle="width: 200px;" />
			<span class="help-inline"><form:errors path="stockComment" cssClass="error" /></span>
		</div>
	</div>
	<div class="control-group" style="float: left; width: 100%;">

		<div style="float: left; width: 100px; margin-top: 15px;">
			<label style="margin-top: 0px;"><s:message code=""	text="To Credit To" /></label>
		</div>
		<div style="float: left; margin-top: 10px;" class="controls">
			<form:input cssClass="input-large" path="stockComment"	cssStyle="width: 300px;" />
			<span class="help-inline"><form:errors path="stockComment" cssClass="error" /></span>
		</div>

	</div>
</form:form>


<script>
	
</script>