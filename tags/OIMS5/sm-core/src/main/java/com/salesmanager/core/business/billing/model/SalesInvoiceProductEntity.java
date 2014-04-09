package com.salesmanager.core.business.billing.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductAttribute;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductOptionValueDescription;


public class SalesInvoiceProductEntity {

	public Long productId;
	
	public String productName;
	
	public Long variantId;
	
	public String description;
	
	public Integer quantity;
	
	public String uom;
	
	public BigDecimal unitPrice;
	
	public String taxClass;
	
	public BigDecimal amount;
	
	public SalesInvoiceProductEntity(){}
	
	public SalesInvoiceProductEntity(Long productId, String productName, Long variantId, String description,
			Integer quantity, String uom, BigDecimal unitPrice, String taxClass, BigDecimal amount) {
		
		this.productId = productId;
		this.productName = productName;
		this.variantId = variantId;
		this.description = description;
		this.quantity = quantity;
		this.uom = uom;
		this.unitPrice = unitPrice;
		this.taxClass = taxClass;
		this.amount = amount;
	}
	
	public static List<SalesInvoiceProductEntity> getSalesInvoiceProductEntity(List<Product> productList) {
		
		List<SalesInvoiceProductEntity> siProductList = new ArrayList<SalesInvoiceProductEntity>();
		
		SalesInvoiceProductEntity siProduct =  null;
		
		for(Product prod: productList) {
			
			siProduct = new SalesInvoiceProductEntity();
			
			Integer qty = prod.getAvailabilities().iterator().next().getProductQuantity();
			BigDecimal unitPrice = prod.getAvailabilities().iterator().next().getPrices().iterator().next().getProductPriceAmount();
			
			BigDecimal amount = calculateAmount(prod.getAvailabilities().iterator().next().getPrices().iterator().next().getProductPriceAmount(), prod.getAvailabilities().iterator().next().getProductQuantity());
			
			
			if(prod.isProductHaveVariants()) {
				siProductList = getVariantData(siProductList, prod.getAttributes(), siProduct, prod.getProductDescription().getDescription(), qty, unitPrice, amount);
			} else {
				siProduct.productId = prod.getId();
				siProduct.productName = prod.getSku();
				siProduct.description = prod.getProductDescription().getDescription();
				siProduct.quantity = qty;
				siProduct.unitPrice = unitPrice;
				siProduct.amount = amount;
				
				siProductList.add(siProduct);
			}
		}
		
		return siProductList;
	}
	
	private static List<SalesInvoiceProductEntity> getVariantData(List<SalesInvoiceProductEntity> siProductList, Set<ProductAttribute> prodAttrList,
			SalesInvoiceProductEntity siProduct, String prodDesc, Integer qty, BigDecimal unitPrice, BigDecimal amount) {
		
		for(ProductAttribute prodAttr: prodAttrList) {
			
			siProduct = new SalesInvoiceProductEntity();
			
			String origProdName = prodAttr.getProduct().getSku();
			String prodOpt = prodAttr.getProductOption().getCode();
			
			if(prodAttr.getProductOptionValue().getDescriptions() != null && prodAttr.getProductOptionValue().getDescriptions().size() > 0) {
				
				for(ProductOptionValueDescription prodOptValDesc: prodAttr.getProductOptionValue().getDescriptions()) {
					
					siProduct.productId = prodAttr.getProduct().getId();
					siProduct.productName = origProdName + " " + prodOpt + " " + prodOptValDesc.getName();
					siProduct.variantId = prodAttr.getId();
					siProduct.description = prodDesc;
					siProduct.quantity = qty;
					siProduct.unitPrice = unitPrice;
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
	
	public SalesInvoiceProduct getSalesInvoiceProduct(SalesInvoiceProductEntity siProductEntity, SalesInvoice invoice) {
		
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
	
	public static List<SalesInvoiceProductEntity> getProductInfoJson(List<SalesInvoiceProduct> salesInvoiceProductList) {
		
		List<SalesInvoiceProductEntity> sipeList = new ArrayList<SalesInvoiceProductEntity>();
		
		SalesInvoiceProductEntity sipe = null;
		
		for(SalesInvoiceProduct sip: salesInvoiceProductList) {
			sipe = new SalesInvoiceProductEntity();
			
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
}
