package com.salesmanager.core.business.common.model;

import java.math.BigDecimal;


public class ProductJSONEntity {

	public Long productId;
	
	public String productName;
	
	public String productDispalyName;
	
	public String productManu;
	
	public Long variantId;
	
	public String description;
	
	public Integer quantity;
	
	public String uom;
	
	public BigDecimal unitPrice;
	
	public Long taxClassId;
	
	public BigDecimal amount;
	
	public ProductJSONEntity() {
		
	}
	
	public ProductJSONEntity(Long productId, String productName, Long variantId, String description,
			Integer quantity, String uom, BigDecimal unitPrice, String taxClass, BigDecimal amount) {
		
		this.productId = productId;
		this.productName = productName;
		this.variantId = variantId;
		this.description = description;
		this.quantity = quantity;
		this.uom = uom;
		this.unitPrice = unitPrice;
		this.amount = amount;
	}
	
	public ProductJSONEntity(Long productId, String productName,String productDispalyName, Long variantId, String description,
			Integer quantity, String uom, BigDecimal unitPrice, String taxClass, BigDecimal amount) {
		
		this.productId = productId;
		this.productName = productName;
		this.productDispalyName=productDispalyName;
		this.variantId = variantId;
		this.description = description;
		this.quantity = quantity;
		this.uom = uom;
		this.unitPrice = unitPrice;
		this.amount = amount;
	}
	
}
