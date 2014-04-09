<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<div class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />
	
		<h3>
			<c:choose>
			<c:when test="${variant.id == null}">
				<s:message code="label.product.master.variant.setup.title" text="Set-Up New Pack / Sizes" /></h3>
			</c:when>
			<c:otherwise>
				<s:message code="label.product.master.variant.title.update" text="Edit Pack / Sizes" /></h3>
			</c:otherwise>
		</c:choose>
			
	<br/>

	<c:url var="saveVariantDetails" value="/admin/catalogue/master/savepacksize.html"/>
	
	<form:form method="POST" commandName="variant" action="${saveVariantDetails}">
		<form:errors path="*" cssClass="alert alert-error" element="div" />
		<div id="variant.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>
		
		<div id="info" style="margin-top: 20px; float: left;">
			<div style="width: 100%; float: left;">
					
				<div class="control-group" style="float: left; margin-bottom: 0px; width: 100%;">
					<div style="float: left; width: 120px;">
                        <label style="margin-top: 5px;"><s:message code="label.variant.name" text="Pack / Sizes"/></label>
					</div>
                       <div style="float: left;" class="controls">
        					<form:input cssClass="input-large highlight" path="variantName" cssStyle="width: 195px;"/>
                        <span class="help-inline"><form:errors path="variantName" cssClass="error" /></span>
                       </div>
				</div>
				
				<div class="control-group" style="float: left; margin-bottom: 0px; width: 100%;">
					<div style="float: left; width: 120px;">
                        <label style="margin-top: 5px;"><s:message code="label.variant.type" text="Pack Type"/></label>
					</div>
                       <div style="float: left;" class="controls">
        					<form:select cssClass="country-list" path="variantType"  cssStyle="width: 205px;">
								<form:options items="${variantTypes }" itemValue="typeCode" itemLabel="typeValue"/>
   							</form:select>
                        	<span class="help-inline"><form:errors path="variantType" cssClass="error" /></span>
                       </div>
				</div>
					
			</div>
		</div>
		
		<form:hidden path="id" />
		
		<div style="margin-top: 30px; float: left; width: 100%;">
       		<div class="pull-left">
       			<button type="submit" class="btn btn-success"><s:message code="button.label.submit2" text="Save or Update"/></button>
       		</div>
  	 	</div>
		
		
	</form:form>

</div>