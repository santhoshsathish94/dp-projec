package com.salesmanager.core.business.service.utils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.reflect.TypeToken;
import com.salesmanager.core.business.billing.model.SalesInvoice;
import com.salesmanager.core.business.billing.model.SalesInvoiceProduct;
import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductAttribute;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductOptionValueDescription;
import com.salesmanager.core.business.common.model.ProductJSONEntity;
import com.salesmanager.core.business.tax.model.taxclass.TaxClass;

public class ProductJSONEntityService {

	/**
	 * @Purpose: This method return the productJSONEntity list from product List
	 * 
	 */
	public static List<ProductJSONEntity> getProductJSONEntity(List<Product> productList) {
		
		List<ProductJSONEntity> siProductList = new ArrayList<ProductJSONEntity>();
		
		ProductJSONEntity siProduct =  null;
		
		for(Product prod: productList) {
			
			siProduct = new ProductJSONEntity();
			
			Integer qty = prod.getAvailabilities().iterator().next().getProductQuantity();
			BigDecimal unitPrice = prod.getAvailabilities().iterator().next().getPrices().iterator().next().getProductPriceAmount();
			
			TaxClass txc = prod.getTaxClass();
			Long taxClassId = null;
			BigDecimal prodTaxRate = new BigDecimal(0.00);
			if(txc != null && txc.getTaxRates() != null && txc.getTaxRates().size() > 0) {
				prodTaxRate = txc.getTaxRates().iterator().next().getTaxRate();
				taxClassId = txc.getId();
			}
			
			BigDecimal amount = calculateAmount(prod.getAvailabilities().iterator().next().getPrices().iterator().next().getProductPriceAmount(), prod.getAvailabilities().iterator().next().getProductQuantity());
			amount = amount.add(amount.multiply(prodTaxRate));
			
			if(prod.isProductHaveVariants()) {
				siProductList = getVariantData(siProductList, prod.getAttributes(), siProduct, prod.getProductDescription().getDescription(), qty, unitPrice, amount, taxClassId);
			} else {
				siProduct.productId = prod.getId();
				siProduct.productName = prod.getSku();
				siProduct.productDispalyName = prod.getProductDescription().getName();
				siProduct.description = prod.getProductDescription().getDescription();
				siProduct.quantity = qty;
				siProduct.unitPrice = unitPrice;
				siProduct.taxClassId = taxClassId;
				siProduct.amount = amount;
				
				siProductList.add(siProduct);
			}
		}
		
		return siProductList;
	}
	
	private static List<ProductJSONEntity> getVariantData(List<ProductJSONEntity> siProductList, Set<ProductAttribute> prodAttrList,
			ProductJSONEntity siProduct, String prodDesc, Integer qty, BigDecimal unitPrice, BigDecimal amount, Long taxClassId) {
		
		for(ProductAttribute prodAttr: prodAttrList) {
			
			siProduct = new ProductJSONEntity();
			
			String origProdName = prodAttr.getProduct().getSku();
			String origProdDisplayName = prodAttr.getProduct().getProductDescription().getName();
			String prodOpt = prodAttr.getProductOption().getCode();
			
			if(prodAttr.getProductOptionValue().getDescriptions() != null && prodAttr.getProductOptionValue().getDescriptions().size() > 0) {
				
				for(ProductOptionValueDescription prodOptValDesc: prodAttr.getProductOptionValue().getDescriptions()) {
					
					siProduct.productId = prodAttr.getProduct().getId();
					siProduct.productName = origProdName + " " + prodOpt + " " + prodOptValDesc.getName();
					siProduct.productDispalyName = origProdDisplayName;
					siProduct.variantId = prodAttr.getId();
					siProduct.description = prodDesc;
					siProduct.quantity = qty;
					siProduct.unitPrice = unitPrice;
					siProduct.taxClassId = taxClassId;
					siProduct.amount = amount;					
					siProductList.add(siProduct);
				}
			}
		}
		
		return siProductList;
	}
	
	private static BigDecimal calculateAmount(BigDecimal unitPrice, Integer quantity) {
		BigDecimal totalAmt = unitPrice.multiply(new BigDecimal(quantity));
		return totalAmt;
	}
	
	public static SalesInvoiceProduct getSalesInvoiceProduct(ProductJSONEntity siProductEntity, SalesInvoice invoice) {
		
		SalesInvoiceProduct siProduct = new SalesInvoiceProduct();
		
		Product prod = new Product();
		prod.setId(siProductEntity.productId);
		
		if(siProductEntity.variantId != null) {
			ProductAttribute pa = new ProductAttribute();
			pa.setId(siProductEntity.variantId);
			
			siProduct.setProductAttribute(pa);
		}
		
		
		siProduct.setProduct(prod);
		siProduct.setSalesInvoice(invoice);
		
		siProduct.setDescription(siProductEntity.description);
		siProduct.setQuantiry(siProductEntity.quantity);
		siProduct.setUnitPrice(siProductEntity.unitPrice);
		siProduct.setProductName(siProductEntity.productName);
		
		return siProduct;
	}
	
	public static List<ProductJSONEntity> getProductInfoJson(List<SalesInvoiceProduct> salesInvoiceProductList) {
		
		List<ProductJSONEntity> sipeList = new ArrayList<ProductJSONEntity>();
		
		ProductJSONEntity sipe = null;
		
		for(SalesInvoiceProduct sip: salesInvoiceProductList) {
			sipe = new ProductJSONEntity();
			
			sipe.productId = sip.getProduct().getId();
			sipe.productName = sip.getProductName();
			sipe.description = sip.getDescription();
			if(sip.getProductAttribute() == null) {
				sipe.variantId = null;
			} else {
				sipe.variantId = sip.getProductAttribute().getId();
			}
			
			sipe.quantity = sip.getQuantiry();
			sipe.unitPrice = sip.getUnitPrice();
			sipe.amount = calculateAmount(sip.getUnitPrice(), sip.getQuantiry());
			
			sipeList.add(sipe);
		}
		
		return sipeList;
	}
	
	public static List<ProductJSONEntity> getProductJSONEntityListFromJsonString(String productJson) {
		
		Type collectionListType = new TypeToken<List<ProductJSONEntity>>(){}.getType();
		
		return LogicUtils.gson.fromJson(productJson, collectionListType);
	}
}
