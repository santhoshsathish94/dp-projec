<%
response.setCharacterEncoding("UTF-8");
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page import="java.util.Calendar" %>
 
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
  
 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
 <c:set var="lang" scope="request" value="${requestScope.locale.language}"/> 
 
 
 <html xmlns="http://www.w3.org/1999/xhtml"> 
 
 
     <head>
     
     
        	 	<meta charset="utf-8">
    			<title><s:message code="label.storeadministration" text="Store administration" /></title>
    			<meta name="viewport" content="width=device-width, initial-scale=1.0">
    			<meta name="description" content="">
    			<meta name="author" content="">
    			
    			<script src="<c:url value="/resources/js/bootstrap/jquery/jquery-1.10.2.js" />"></script>
    			<script src="<c:url value="/resources/js/jquery.friendurl.min.js" />"></script>
 
  
                <jsp:include page="/common/adminLinks.jsp" />
                
                


 	
 	
 	</head>
 
 	<body class="body">

     <p>&nbsp;</p>

<div class="sm">
	<div class="container">

		<div class="row">

  			<div class="span4"><a class="brand" href="#"><img src="<c:url value="/resources/img/shopizer_small.jpg" />"/></a></div>

  			<div class="span4 offset4">
  			
  			

  			
  			
  			
  			

					<div class="btn-group pull-right">
						<div class="nav-collapse">
							<ul class="nav pull-right" style="z-index:500000;position:relative">
								<li class="dropdown">
									
									<a data-toggle="dropdown" class="dropdown-toggle" href="#">
										<i class="icon-user"></i> 
										<sec:authentication property="principal.username" />
										<b class="caret"></b>
									</a>
									
									<ul class="dropdown-menu">
										<li><a href="<c:url value="/admin/users/displayUser.html" />"><s:message code="label.my.profile" text="My profile" /></a></li>
										<!-- <li><a href="javascript:;">TODO //Language</a></li> -->
										<li class="divider"></li>
										<li>
											<c:url value="/admin/j_spring_security_logout" var="logoutUrl"/>
											<a href="${logoutUrl}"><s:message code="button.label.logout" text="Logout" /></a>
										</li>
									</ul>
									
								</li>
						</ul>
			

				
				</div><!--/.nav-collapse -->	

			</div>

  			</div>


   
		
		</div>
		
	</div>

	
	<div class="row">&nbsp;</div>



	<div class="container"> 
		<div class="row">	
			
			<div class="span3">
				<ul class="nav nav-list">
					  <c:forEach items="${requestScope.MENULIST}" var="menu">
					  			<sec:authorize access="hasRole('${menu.role}') and fullyAuthenticated">
					  			<li <c:if test="${activeMenus[menu.code]!=null}"> class="active"</c:if>>
									<a href="<c:url value="${menu.url}" />">
										<i class="${menu.icon}"></i>
											<s:message code="menu.${menu.code}" text="${menu.code}"/>
									</a>
					  			</li>
					  			</sec:authorize>
					  </c:forEach>
				</ul>
			</div><!-- end span 3 -->
			
			

			<div class="span9">

				<tiles:insertAttribute name="body"/>

			</div>


		</div>



  
		<hr> 
  
  
		<footer> 
 			<p>&copy; iShop <%=Calendar.getInstance().get(Calendar.YEAR)%></p> 
		</footer> 
  
  
	</div> <!-- /container --> 
	

  
</div>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-button.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-modal.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-tab.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-transition.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-alert.js" />"></script>
    
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-dropdown.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-scrollspy.js" />"></script>
    
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-tooltip.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-popover.js" />"></script>
    
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-collapse.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-carousel.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-typeahead.js" />"></script>
     

    
     	
     	
     <script>
	
		$(document).ready(function(){ 

			$("#catalogue-products-create-link").click(function() {
				window.location='<c:url value="/admin/products/createProduct.html" />';
			});
			$("#catalogue-categories-list-link").click(function() {
				window.location='<c:url value="/admin/categories/categories.html" />';
			});
			$("#catalogue-products-categories-link").click(function() {
				window.location='<c:url value="/admin/products/product-categories.html" />';
			});
			$("#catalogue-link").click(function() {
				window.location='<c:url value="/admin/products/products.html" />';
			});
			$("#catalogue-categories-hierarchy-link").click(function() {
				window.location='<c:url value="/admin/categories/hierarchy.html" />';
			});
			$("#catalogue-categories-create-link").click(function() {
  				window.location='<c:url value="/admin/categories/createCategory.html" />';
			});
			$("#catalogue-options-list-link").click(function() {
  				window.location='<c:url value="/admin/options/options.html" />';
			});
			$("#catalogue-options-create-link").click(function() {
  				window.location='<c:url value="/admin/options/createOption.html" />';
			});
			$("#catalogue-optionsvalues-list-link").click(function() {
  				window.location='<c:url value="/admin/options/optionvalues.html" />';
			});
			$("#catalogue-optionsvalues-create-link").click(function() {
  				window.location='<c:url value="/admin/options/createOptionValue.html" />';
			});
			$("#catalogue-featured-link").click(function() {
  				window.location='<c:url value="/admin/catalogue/featured/list.html" />';
			});
			$("#catalogue-products-custom-group-link").click(function() {
  				window.location='<c:url value="/admin/products/groups/list.html" />';
			});
			$("#manufacturer-list-link").click(function() {
  				window.location='<c:url value="/admin/catalogue/manufacturer/list.html" />';
			});
			$("#manufacturer-create-link").click(function() {
  				window.location='<c:url value="/admin/catalogue/manufacturer/create.html" />';
			});
			$("#myprofile-link").click(function() {
  				window.location='<c:url value="/admin/users/displayUser.html" />';
			});
			$("#user-link").click(function() {
  				window.location='<c:url value="/admin/users/displayUser.html" />';
			});
			$("#change-password-link").click(function() {
  				window.location='<c:url value="/admin/users/password.html" />';
			});
			$("#users-link").click(function() {
  				window.location='<c:url value="/admin/users/list.html" />';
			});
			$("#create-user-link").click(function() {
  				window.location='<c:url value="/admin/users/createUser.html" />';
			});
			$("#security-permissions-link").click(function() {
  				window.location='<c:url value="/admin/user/permissions.html" />';
			});
			$("#security-groups-link").click(function() {
  				window.location='<c:url value="/admin/user/groups.html" />';
			});
			$("#customer-list-link").click(function() {
  				window.location='<c:url value="/admin/customers/list.html" />';
			});
			$("#customer-create-link").click(function() {
  				window.location='<c:url value="/admin/customers/customer.html" />';
			});
			$("#customer-options-list-link").click(function() {
  				window.location='<c:url value="/admin/customers/options/list.html" />';
			});
			$("#customer-options-create-link").click(function() {
  				window.location='<c:url value="/admin/customers/options/create.html" />';
			});
			$("#customer-options-values-list-link").click(function() {
  				window.location='<c:url value="/admin/customers/options/values/list.html" />';
			});
			$("#customer-options-values-create-link").click(function() {
  				window.location='<c:url value="/admin/customers/options/values/create.html" />';
			});
			$("#customer-options-set-link").click(function() {
  				window.location='<c:url value="/admin/customers/optionsset/list.html" />';
			});
			$("#order-list-link").click(function() {
  				window.location='<c:url value="/admin/orders/list.html" />';
			});
			$("#storeDetails-link").click(function() {
  				window.location='<c:url value="/admin/store/store.html" />';
			});
			$("#create-store-link").click(function() {
  				window.location='<c:url value="/admin/store/storeCreate.html" />';
			});
			$("#store-list-link").click(function() {
  				window.location='<c:url value="/admin/store/list.html" />';
			});
			$("#storeBranding-link").click(function() {
  				window.location='<c:url value="/admin/store/storeBranding.html" />';
			});
			$("#storeLanding-link").click(function() {
  				window.location='<c:url value="/admin/store/storeLanding.html" />';
			});
			$("#content-link").click(function() {
  				window.location='<c:url value="/admin/content/contentImages.html" />';
			});
			$("#content-images-create-link").click(function() {
  				window.location='<c:url value="/admin/content/createContentImages.html" />';
			});
			$("#content-pages-link").click(function() {
  				window.location='<c:url value="/admin/content/pages/list.html" />';
			});
			$("#content-pages-create-link").click(function() {
  				window.location='<c:url value="/admin/content/pages/create.html" />';
			});
			$("#content-boxes-link").click(function() {
  				window.location='<c:url value="/admin/content/boxes/list.html" />';
			});
			$("#content-boxes-create-link").click(function() {
  				window.location='<c:url value="/admin/content/boxes/create.html" />';
			});
			$("#content-files-link").click(function() {
  				window.location='<c:url value="/admin/content/static/contentFiles.html" />';
			});
			$("#content-images-link").click(function() {
  				window.location='<c:url value="/admin/content/contentImages.html" />';
			});
			$("#shipping-configs-link").click(function() {
  				window.location='<c:url value="/admin/shipping/shippingConfigs.html" />';
			});
			$("#shipping-methods-link").click(function() {
  				window.location='<c:url value="/admin/shipping/shippingMethods.html" />';
			});
			$("#shipping-options-link").click(function() {
  				window.location='<c:url value="/admin/shipping/shippingOptions.html" />';
			});
			$("#shipping-packages-link").click(function() {
  				window.location='<c:url value="/admin/shipping/shippingPackaging.html" />';
			});
			$("#accounts-conf-link").click(function() {
  				window.location='<c:url value="/admin/configuration/accounts.html" />';
			});
			$("#email-conf-link").click(function() {
  				window.location='<c:url value="/admin/configuration/email.html" />';
			});
			$("#system-configurations-link").click(function() {
  				window.location='<c:url value="/admin/configuration/system.html" />';
			});
			$("#taxclass-link").click(function() {
  				window.location='<c:url value="/admin/tax/taxclass/list.html" />';
			});
			$("#taxconfiguration-link").click(function() {
  				window.location='<c:url value="/admin/tax/taxconfiguration/edit.html" />';
			});
			$("#taxrates-link").click(function() {
  				window.location='<c:url value="/admin/tax/taxrates/list.html" />';
			});
			
			$("#company-info-link").click(function() {
				window.location='<c:url value="/admin/company.html" />';
			});
			$("#company-accounting-period-list-link").click(function() {
				window.location='<c:url value="/admin/company/acountingPeriods.html" />';
			});
			$("#company-accounting-period-create-link").click(function() {
				window.location='<c:url value="/admin/company/createAccountingPeriod.html" />';
			});
			$("#company-currencies-list-link").click(function() {
				window.location='<c:url value="/admin/company/currencies.html" />';
			});
			$("#company-currencies-create-link").click(function() {
				window.location='<c:url value="/admin/company/createCurrencies.html" />';
			});
			$("#company-general-link").click(function() {
				window.location='<c:url value="/admin/company/generalInfo.html" />';
			});

			$("#supplier-list-link").click(function() {
				window.location='<c:url value="/admin/supplier/suppliers.html" />';
			});
			$("#supplier-create-link").click(function() {
				window.location='<c:url value="/admin/supplier/createsupplier.html" />';
			});
			
			$("#sales-channel-default-margin-list-link").click(function() {
				window.location='<c:url value="/admin/supplier/saleschanneldefaultmargins.html" />';
			});
			$("#sales-channel-default-margin-create-link").click(function() {
				window.location='<c:url value="/admin/supplier/createsaleschanneldefaultmargin.html" />';
			});
			
			///  Inventory Management

			$("#openingStock").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/createstock.html" />';
			});
			$("#stock-list-link").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/stocks.html" />';
			});
			$("#stock-create-link").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/createstock.html" />';
			});
			$("#purchase-entry-list-link").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/purchaseEntrylist.html" />';
			});
			$("#purchase-entry-create-link").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/createPurchaseEntry.html" />';
			});
			$("#purchase-return-debit-note-list-link").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/purchasereturndnlist.html" />';
			});
			$("#purchase-return-debit-note-create-link").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/createpurchasereturndn.html" />';
			});
			$("#debit-note-other-list-link").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/debitnoteotherlist.html" />';
			});
			$("#debit-note-other-create-link").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/createdebitnoteother.html" />';
			});
			$("#branch-transfer-list-link").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/branchtransferlist.html" />';
			});
			$("#branch-transfer-create-link").click(function() {
				window.location='<c:url value="/admin/inventoryManagement/createbranchtransfer.html" />';
			});
			
			/*$("#pack-size-list-link").click(function() {
				window.location='<c:url value="/admin/catalogue/master/listpacksize.html" />';
			});
			$("#pack-size-create-link").click(function() {
				window.location='<c:url value="/admin/catalogue/master/createpacksize.html" />';
			});
			$("#shades-list-link").click(function() {
				window.location='<c:url value="/admin/catalogue/master/listshade.html" />';
			});
			$("#shades-create-link").click(function() {
				window.location='<c:url value="/admin/catalogue/master/createshade.html" />';
			});*/

			///  Account Management

			$("#account_Management").click(function() {
				window.location='<c:url value="/admin/account/createReceipt.html" />';
			});
			$("#create-receipt-link").click(function() {
				window.location='<c:url value="/admin/account/createReceipt.html" />';
			});			
			$("#receipt-list-link").click(function() {
				window.location='<c:url value="/admin/account/receipts.html" />';
			});
			$("#create-payment-link").click(function() {
				window.location='<c:url value="/admin/account/createPayment.html" />';
			});
			$("#payment-list-link").click(function() {
				window.location='<c:url value="/admin/account/payments.html" />';
			});
			$("#expense-entry-link").click(function() {
				window.location='<c:url value="/admin/account/createExpense.html" />';
			});
			$("#expense-list-link").click(function() {
				window.location='<c:url value="/admin/account/expenses.html" />';
			});
			$("#create-journal-link").click(function() {
				window.location='<c:url value="/admin/account/createJournal.html" />';
			});
			$("#journal-list-link").click(function() {
				window.location='<c:url value="/admin/account/journals.html" />';
			});
			
			
			$("#sales-invoice-setting-link").click(function() {
				window.location='<c:url value="/admin/billing/saleinvoicesetting.html" />';
			});
			$("#sales-invoice-list-link").click(function() {
				window.location='<c:url value="/admin/billing/invoide-list.html" />';
			});
			$("#sales-invoice-create-link").click(function() {
				window.location='<c:url value="/admin/billing/invoicecreate.html" />';
			});
			$("#credit-note-list-link").click(function() {
				window.location='<c:url value="/admin/billing/creditnote-list.html" />';
			});
			$("#credit-note-create-link").click(function() {
				window.location='<c:url value="/admin/billing/createcreditnote.html" />';
			});
			
			$("#order-list-link").click(function() {
				window.location='<c:url value="/admin/orders/list.html" />';
			});
			$("#sale-order-booking-create-link").click(function() {
				window.location='<c:url value="/admin/orders/createsaleorderbooking.html" />';
			});


			///  inventory sharing
			
			$("#product-sharing-link").click(function() {
				window.location='<c:url value="/admin/catalogue/sharing/byProduct.html" />';
			});
			$("#manufacturer-sharing-link").click(function() {
				window.location='<c:url value="/admin/catalogue/sharing/byManufacturer.html" />';
			});
			$("#category-sharing-link").click(function() {
				window.location='<c:url value="/admin/catalogue/sharing/byCategory.html" />';
			});			
			
		}); 
		
		
		function checkCode(code, id, url) {
			

			
			$.ajax({
					type: 'POST',
					dataType: "json",
					url: url,
					data: "code="+ code + "&id=" + id,
					success: function(response) { 
						var msg = isc.XMLTools.selectObjects(response, "/response/statusMessage");
						var status = isc.XMLTools.selectObjects(response, "/response/status");
						
						callBackCheckCode(msg,status);

						
					},
					error: function(jqXHR,textStatus,errorThrown) { 
						alert(jqXHR + "-" + textStatus + "-" + errorThrown);
					}
					
			});
			
			
			
		}
	
</script>	
     	
         
 
 	</body>
 
 </html>
 
