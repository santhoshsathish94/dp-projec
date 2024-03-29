<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<script>

function getContextPath() {
   return "${pageContext.request.contextPath}";
}

function getMerchantStore() {
   return "${requestScope.MERCHANT_STORE.id}";
}

function getMerchantStoreCode() {
   return "${requestScope.MERCHANT_STORE.code}";
}

function getLanguageCode() {
   return "${requestScope.LANGUAGE.code}";
}

function getItemLabel(quantity) {
	var labelItem = '<s:message code="label.generic.item" text="item" />';
	if (quantity > 1) {
		labelItem = '<s:message code="label.generic.items" text="items" />';
	}
	return labelItem;
}

function getLoginErrorLabel() {
	return '<s:message code="message.username.password" text="Login Failed. Username or Password is incorrect."/>';
}

function emptyCartLabel(){
	$("#cartMessage").html('<s:message code="cart.empty" text="Your Shopping cart is empty" />');
	var labelItem = '<s:message code="label.generic.item" text="item" />';
	$("#cartinfo").html('<span id="cartqty">(' + 0 + ' ' + labelItem + ')</span>&nbsp;<span id="cartprice"></span>');
	 $('#shoppingcart').hide();
	 $('#cartMessage').show();
}

function cartInfoLabel(cart){
	 var labelItem = getItemLabel(cart.quantity);
	 $("#cartinfo").html('<span id="cartqty">(' + cart.quantity + ' ' + labelItem + ')</span>&nbsp;<span id="cartprice">' + cart.total + '</span>');	
}

function cartSubTotal(cart) {
	return '<div class="pull-right"><font class="total-box-label"><s:message code="label.subtotal" text="Sub-total" /> : <font class="total-box-price"><strong><span id="checkout-total">' + cart.subTotal + '</span></strong></font></font></div>';
}

function validateEmail($email) {
	  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	  if ( $email.length > 0 && emailReg.test($email)) {
	    return true;
	  } else {
	    return false;
	  }
}

function emptyString($value) {
	return !$value || !/[^\s]+/.test($value);
}


function loadProducts(url,divProductsContainer, ref) {
		$(divProductsContainer).showLoading();

		$.ajax({
				type: 'POST',
				dataType: "json",
				url: url,
				success: function(productList) {

					buildProductsList(productList,divProductsContainer, ref);
					callBackLoadProducts(productList);


				},
				error: function(jqXHR,textStatus,errorThrown) { 
					$(divProductsContainer).hideLoading();
					alert('Error ' + jqXHR + "-" + textStatus + "-" + errorThrown);
				}
				
		});
		
		
		
}


function searchProducts(url,divProductsContainer,q,filter) {
		
		$(divProductsContainer).showLoading();
		
		if(q==null || q=='') {
			return;
		}
	
	    //category facets
		var facets = '\"facets\" : { \"categories\" : { \"terms\" : {\"field\" : \"categories\"}}}';
	    var highlights = null;
		var queryStart = '{';

		var query = '\"query\":{\"query_string\" : {\"fields\" : [\"name^3\", \"description\", \"tags\"], \"query\" : \"*' + q + '*", \"use_dis_max\" : true }}';
		if(filter!=null && filter!='') {
			//query = '\"query\":{\"filtered\":{\"query\":{\"text\":{\"_all\":\"' + q + '\"}},' + filter + '}}';
			query = query + ',' + filter + '}}';
		}

		if(facets!=null && facets!='') {
			query = query + ',' + facets;
		}

		var queryEnd = '}';
		
		query = queryStart + query + queryEnd;
	
		//alert(query);

		$.ajax({
	  			cache: false,
	  			type:"POST",
	  			dataType:"json",
	  			url:url,
	  			data:query,
	  			contentType:"application/json;charset=UTF-8",
				success: function(productList) {

					buildProductsList(productList,divProductsContainer, null);
					//TODO manage total entries found
					callBackSearchProducts(productList);


				},
				error: function(jqXHR,textStatus,errorThrown) { 
					$(divProductsContainer).hideLoading();
					alert('Error ' + jqXHR + "-" + textStatus + "-" + errorThrown);
				}
				
		});
		
		
		
}

/**
* Builds the product container div from the product list
**/
function buildProductsList(productList, divProductsContainer, ref) {


		for (var i = 0; i < productList.products.length; i++) {
			var productHtml = '<li itemscope itemtype="http://schema.org/Enumeration" class="item listing-item" data-id="' + productList.products[i].id  + '" item-price="' +  productList.products[i].price +'" item-name="' +  productList.products[i].description.name +'" item-order="' +  productList.products[i].sortOrder +'">';
			productHtml = productHtml + '<div class="product-box"><a href="<c:url value="/shop/product/" />' + productList.products[i].description.friendlyUrl + '.html">';
			productHtml = productHtml + '<h4 class="name" itemprop="name">' + productList.products[i].description.name +'</h4></a>';
			if(productList.products[i].discounted) {
					   productHtml = productHtml + '<h3><del>' + productList.products[i].originalPrice +'</del>&nbsp;<span class="specialPrice">' + productList.products[i].finalPrice + '</span></h3>';
			} else {
					    productHtml = productHtml + '<h3>' + productList.products[i].finalPrice +'</h3>';
			}
			var productUrl = '<c:url value="/shop/product/" />' + productList.products[i].description.friendlyUrl + '.html';
			if(ref!=null) {
				productUrl = productUrl + '/ref=' + ref;
			}
			if(productList.products[i].image!=null) {
					    productHtml = productHtml + '<a href="' + productUrl + '"><img src="<c:url value="/"/>' + productList.products[i].image.imageUrl +'" itemprop="image"></a>';
			}
			productHtml = productHtml + '<div>test</div><br><br>';
			productHtml = productHtml + '<div class="bottom"><a href="' + productUrl + '" class="view"><s:message code="button.label.view" text="View" /></a> /<select style="width:50px"><option>test<option/></select>/ <a productid="' + productList.products[i].id + '" href="#" class="addToCart"><s:message code="button.label.addToCart" text="Add to cart" /></a></div>';
			alert("ok");
			
			productHtml = productHtml + '</div>'
			
			productHtml = productHtml + '</li>'
			$(divProductsContainer).append(productHtml);

		}
		
		initBindings();

}

</script>