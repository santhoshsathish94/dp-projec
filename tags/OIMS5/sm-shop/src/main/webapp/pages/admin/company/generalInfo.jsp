<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<div class="tabbable">
	<jsp:include page="/common/adminTabs.jsp" />
	
	<h3><s:message code="label.company.generalinfo.title" text="Update General Information" /></h3>	
	<br/>
	
	<c:url var="saveCompanyGeneralInfoDetails" value="/admin/company/saveGeneralInfo.html"/>
	
	<form:form method="POST" commandName="company" action="${saveCompanyGeneralInfoDetails}">
		<form:errors path="*" cssClass="alert alert-error" element="div" />
		<div id="company.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>
		
		<div id="info">
			<div style="width: 100%; float: left;">
				
				<div style="float: left; margin-top: 10px;  width: 100%;">
					<div class="control-group" style="float: left;">
						<div style="float: left; width: 50px;" class="controls">
							<input type="checkbox" name="negativeStock" id = "negativeStock" value="${company.negativeStock}" <c:if test="${company.negativeStock == true}"> checked="checked" </c:if>  onclick="changeCheckBoxValue('negativeStock');">
						</div>
						<div style="float: left; width: 300px;">
	                        <label><s:message code="label.general.negative.stock" text="Allow -ve Stock"/></label>
						</div>
					</div>
				</div>
				
				<div style="float: left; margin-top: 10px;  width: 100%;">
					<div class="control-group" style="float: left;">
						<div style="float: left; width: 50px;" class="controls">
							<input type="checkbox" name="reportHeader" id = "reportHeader" value="${company.reportHeader}" <c:if test="${company.reportHeader == true}"> checked="checked" </c:if>  onclick="changeCheckBoxValue('reportHeader');">
						</div>
						<div style="float: left; width: 300px;">
	                        <label><s:message code="label.general.report.header" text="Print Branch Name with Company Name In Report"/></label>
						</div>
					</div>
				</div>
				
				<div style="float: left; margin-top: 10px;  width: 100%;">
					<div class="control-group" style="float: left;">
						<div style="float: left; width: 50px;" class="controls">
							<input type="checkbox" name="createBranch" id = "createBranch" value="${company.createBranch}" <c:if test="${company.createBranch == true}"> checked="checked" </c:if>  onclick="changeCheckBoxValue('createBranch');">
						</div>
						<div style="float: left; width: 300px;">
	                        <label><s:message code="label.general.create.branch" text="Allow New Branch Creation"/></label>
						</div>
					</div>
				</div>
				
				<div style="float: left; margin-top: 10px;  width: 100%;">
					<div class="control-group" style="float: left;">
						<div style="float: left; width: 50px;" class="controls">
							<input type="checkbox" name="emailOnMasterUpdate" id = "emailOnMasterUpdate" value="${company.emailOnMasterUpdate}" <c:if test="${company.emailOnMasterUpdate == true}"> checked="checked" </c:if>  onclick="changeCheckBoxValue('emailOnMasterUpdate');">
						</div>
						<div style="float: left; width: 300px;">
	                        <label><s:message code="label.general.email.on.master.update" text="Set E mail Prompt for Master Modification"/></label>
						</div>
					</div>
				</div>
				
			</div>
		</div>
		
		<input type="hidden" name="id" id="id" value="${company.id }" />
		
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