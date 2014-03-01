<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<link href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />" rel="stylesheet"></link>
<script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>

<div class="tabbable">
	<jsp:include page="/common/adminTabs.jsp" />
	
	<h3>
		<c:choose>
			<c:when test="${accountingPeriod.id == 0}">
				<s:message code="label.company.accountingPeriod.title" text="Create Accounting Period" />
			</c:when>
			<c:otherwise>
				<s:message code="label.company.accountingPeriod.title_Update" text="Update Accounting Period" />
			</c:otherwise>
		</c:choose>
	
	</h3>	
	<br/>
	
	<c:url var="saveAccPeriod" value="/admin/company/saveAccountingPeriod.html"/>
	
	<form:form method="POST" commandName="accountingPeriod" action="${saveAccPeriod}">
		<form:errors path="*" cssClass="alert alert-error" element="div" />
		<div id="accountingPeriod.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>
		
		<div id="info">
			<div style="width: 100%; float: left;">
				<h4><s:message code="label.accountingPeriod.title" text="Time Period" /></h4>
				
				<div style="float: left; margin-top: 10px;  width: 100%;">
					
					<div class="control-group" style="float: left;">
						<div style="float: left; width: 100px;">
	                        <label><s:message code="label.accountingeriod.fromDate" text="From Date"/></label>
						</div>
						<div style="float: left; width: 200px;"  class="controls">
							<input id="fromSDate" name="fromSDate" value="${accountingPeriod.fromSDate}" style="width: 150px;" class="small" type="text" data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>" data-datepicker="datepicker"> 
							<span class="help-inline"><form:errors path="fromSDate" cssClass="error" /></span>
						</div>
					</div>
				</div>
				<div style="float: left; margin-top: 10px;  width: 100%;">
					<div class="control-group" style="float: left;">
						<div style="float: left; width: 100px;">
	                        <label><s:message code="label.accountingeriod.toDate" text="To Date"/></label>
						</div>
						
						<div style="float: left; width: 200px;" class="controls">
							<input id="toSDate" name="toSDate" value="${accountingPeriod.toSDate}" style="width: 150px;" class="small" type="text" data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>" data-datepicker="datepicker"> 
							<span class="help-inline"><form:errors path="toSDate" cssClass="error" /></span>
						</div>
					</div>
				</div>
				<div style="float: left; margin-top: 10px;  width: 100%;">
					<div class="control-group" style="float: left;">
						<div style="float: left; width: 100px;">
	                        <label><s:message code="label.accountingeriod.toDate" text="Status"/></label>
						</div>
						
						<div style="float: left; width: 200px;" class="controls">
							<input type="checkbox" name="status" id = "status" value="${accountingPeriod.status}" <c:if test="${accountingPeriod.status == true}"> checked="checked" </c:if>  onclick="changeCheckBoxValue('status');">
						</div>
					</div>
				</div>
				<div style="float: left; margin-top: 10px;  width: 100%;">
					<div class="control-group" style="float: left;">
						<div style="float: left; width: 100px;">
	                        <label><s:message code="label.accountingeriod.toDate" text="Set As Default"/></label>
						</div>
						
						<div style="float: left; width: 200px;" class="controls">
							<input type="checkbox" name="setAsDefault" id="setAsDefault" value="${accountingPeriod.setAsDefault}" <c:if test="${accountingPeriod.setAsDefault == true}"> checked="checked" </c:if> onclick="changeCheckBoxValue('setAsDefault');">
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<input type="hidden" name="id" id="id" value="${accountingPeriod.id }" />
		
		<div style="margin-top: 10px; float: left;">
       		<div class="pull-left">
       			<button type="submit" class="btn btn-success"><s:message code="button.label.submit2" text="Save or Update"/></button>
       		</div>
  	 	</div>
		
	</form:form>

<script type="text/javascript">
	function changeCheckBoxValue(field) {
		var isChecked = $('#'+field).prop('checked');
		if(isChecked) {
			$('#'+field).val(true);
		} else {
			$('#'+field).val(false);
		}
	}
</script>	
</div>