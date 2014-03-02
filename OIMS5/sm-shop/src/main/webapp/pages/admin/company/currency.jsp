<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<div class="tabbable">
	<jsp:include page="/common/adminTabs.jsp" />
	
	<h3>
		<c:choose>
			<c:when test="${currency.id == null}">
				<s:message code="label.company.currency.title" text="Create Currency" />
			</c:when>
			<c:otherwise>
				<s:message code="label.company.currency.title_Update" text="Update Currency" />
			</c:otherwise>
		</c:choose>
	
	</h3>	
	<br/>
	
	<c:url var="saveAccPeriod" value="/admin/company/saveCurrency.html"/>
	
	<form:form method="POST" commandName="currency" action="${saveAccPeriod}">
		<form:errors path="*" cssClass="alert alert-error" element="div" />
		<div id="currency.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>
		
		<div id="info">
			<div style="width: 100%; float: left;">
					<div class="control-group" style="margin-top: 20px; float: left; margin-bottom: 0px; width: 100%;">
						<div style="float: left; width: 160px;">
	                        <label style="margin-top: 5px;"><s:message code="label.currency.symbol" text="Currency Symbol"/></label>
						</div>
                        <div style="float: left;" class="controls">
                        	<input id="symbl" name="symbl" value="${currency.symbl}" style="width: 100px;" type="text" class="input-large highlight" /> 
							<span class="help-inline"><form:errors path="symbl" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="margin-top: 20px; float: left; margin-bottom: 0px; width: 100%;">
						<div style="float: left; width: 160px;">
	                        <label style="margin-top: 5px;"><s:message code="label.currency.code" text="Currency Code"/></label>
						</div>
                        <div style="float: left;" class="controls">
                        	<input id="code" name="code" value="${currency.code}" style="width: 100px;" type="text" class="input-large highlight" /> 
							<span class="help-inline"><form:errors path="code" cssClass="error" /></span>
                        </div>
					</div>
					
					<div class="control-group" style="margin-top: 20px; float: left; margin-bottom: 0px; width: 100%;">
						<div style="float: left; width: 160px;">
	                        <label style="margin-top: 5px;"><s:message code="label.currency.decimal.place" text="Decimal Places"/></label>
						</div>
                        <div style="float: left;" class="controls">
                        	<input type="text" name="decimalPosition" id = "decimalPosition" value="${currency.decimalPosition}" style="width: 100px;" class="input-large highlight" />
                        </div>
					</div>
					
					<div class="control-group" style="margin-top: 20px; float: left; margin-bottom: 0px; width: 100%;">
						<div style="float: left; width: 160px;">
	                        <label style="margin-top: 5px;"><s:message code="label.currency.exchange.rate" text="Exchange Rate (in INR)"/></label>
						</div>
                        <div style="float: left;" class="controls">
                        	<input type="text" name="exchangeRate" id="exchangeRate" value="${currency.exchangeRate}" style="width: 100px;" class="input-large highlight" />
                        </div>
					</div>
			</div>
		</div>
		
		<input type="hidden" name="id" id="id" value="${currency.id }" />
		
		<div style="margin-top: 10px; float: left;">
       		<div class="pull-left">
       			<button type="submit" class="btn btn-success"><s:message code="button.label.submit2" text="Save or Update"/></button>
       		</div>
  	 	</div>
		
	</form:form>
	
</div>