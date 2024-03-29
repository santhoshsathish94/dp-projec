<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@ page session="false" %>






<div class="tabbable">

		<jsp:include page="/common/adminTabs.jsp" />
		<c:url var="userSave" value="/admin/setUserStore.html"/>


		<form:form id="envselection" method="POST" commandName="user" action="${userSave}">
			<div id="messages" class="alert alert-info" style="display:none">
			</div>
			
			<div class="box" style="margin-bottom: 10px;">
				<span class="box-title">
				<p><s:message code="label.company.dashboard.title" text="Comppany information" /></p>
				</span>
				
				<p>
				<address>
					<strong><c:out value="${company.companyDisplayName}"/></strong><br/>
					<c:if test="${not empty company.companyAddress}">
						<c:out value="${company.companyAddress}"/><br/>
					</c:if>
					<c:if test="${not empty company.companyCity}">
						<c:out value="${company.companyCity}"/>,
					</c:if>
					<c:choose>
					<c:when test="${not empty company.companyZone}">
						<c:out value="${company.companyZone.code}"/>,
					</c:when>
					<c:otherwise>
						<c:if test="${not empty company.companyStateProvince}">
							<c:out value="${company.companyStateProvince}"/>,
						</c:if>
					</c:otherwise>
					</c:choose>
					<c:if test="${not empty company.companyPostalCode}">
						<c:out value="${company.companyPostalCode}"/>
					</c:if>
					<br/><c:out value="${country.name}"/>
					<c:if test="${not empty company.companyMobileNumber}">
						<br/><c:out value="${company.companyMobileNumber}"/>
					</c:if>
				</address>
				</p>
			</div>
			
			
			<div class="box">
				<span class="box-title">
				<p><s:message code="label.store.information" text="Store information" /></p>
				</span>
				<c:forEach var="merchantStore1" items="${merchantStores}">
				<div class="box">
						<p>
						<address>
							<strong><c:out value="${merchantStore1.storename}"/> &nbsp;&nbsp; <form:radiobutton path="merchantStore.id" value="${merchantStore1.id}" onclick="submitForm(this)"/></strong><br/>
							<c:if test="${not empty merchantStore1.storeaddress}">
								<c:out value="${merchantStore1.storeaddress}"/><br/>
							</c:if>
							<c:if test="${not empty merchantStore1.storecity}">
								<c:out value="${merchantStore1.storecity}"/>,
							</c:if>
							<c:choose>
							<c:when test="${not empty merchantStore1.zone}">
								<c:out value="${merchantStore1.zone.code}"/>,
							</c:when>
							<c:otherwise>
								<c:if test="${not empty merchantStore1.storestateprovince}">
									<c:out value="${merchantStore1.storestateprovince}"/>,
								</c:if>
							</c:otherwise>
							</c:choose>
							<c:if test="${not empty merchantStore1.storepostalcode}">
								<c:out value="${merchantStore1.storepostalcode}"/>
							</c:if>
							<br/><c:out value="${countries.get(merchantStore1.country.isoCode).name}"/>
							<c:if test="${not empty merchantStore1.storephone}">
								<br/><c:out value="${merchantStore1.storephone}"/>
							</c:if>
						</address>
	
						
						</p>
						
					
					</div>
				</c:forEach>.
			</div>
			
			<p>
				<i class="icon-user"></i> 
				<sec:authentication property="principal.username" /><br/>
				<i class="icon-calendar"></i> <s:message code="label.profile.lastaccess" text="Last access"/>: <fmt:formatDate type="both" dateStyle="long" value="${user.lastAccess}" />
			</p>
			
			<%-- <br/>
			<h3><s:message code="label.order.recent" text="Recent orders"/></h3>
			<br/><br/>
			
		 <!-- Listing grid include -->
		 <c:set value="/admin/orders/paging.html?_endRow=10" var="pagingUrl" scope="request"/>
		 <c:set value="/admin/orders/remove.html" var="removeUrl" scope="request"/>
		 <c:set value="/admin/orders/editOrder.html" var="editUrl" scope="request"/>
		 <c:set value="/admin/orders/list.html" var="afterRemoveUrl" scope="request"/>
		 <c:set var="entityId" value="orderId" scope="request"/>
		 <c:set var="componentTitleKey" value="label.order.title" scope="request"/>
		 <c:set var="gridHeader" value="/pages/admin/orders/orders-gridHeader.jsp" scope="request"/>
		 <c:set var="canRemoveEntry" value="false" scope="request"/>

          	 <jsp:include page="/pages/admin/components/list.jsp"></jsp:include> 
		 <!-- End listing grid include --> --%>
		 </form:form>

</div>

<script>
function submitForm(obj) {
    //alert(obj.value);
    document.getElementById("envselection").submit();
}
</script>