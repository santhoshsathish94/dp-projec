package com.salesmanager.core.business.billing.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductAttribute;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductOptionValueDescription;
import com.salesmanager.core.business.tax.model.taxclass.TaxClass;

public class CreditNoteProductEntity {

	public Long creditNoteProductId;
	
	public Long productId;
	
	public String productName;
	
	public Long variantId;
	
	public String description;
	
	public Integer quantity;
	
	public String uom;
	
	public BigDecimal unitPrice;
	
	public Long taxClassId;
	
	public BigDecimal taxAmount;
	
	public BigDecimal amount;
	
	public Long invoiceNumber;
	
	public CreditNoteProductEntity() {}
	
	public CreditNoteProductEntity(Long productId, String productName, Long variantId, String description,
			Integer quantity, String uom, BigDecimal unitPrice, BigDecimal amount) {
		
		this.productId = productId;
		this.productName = productName;
		this.variantId = variantId;
		this.description = description;
		this.quantity = quantity;
		this.uom = uom;
		this.unitPrice = unitPrice;
		this.amount = amount;
	}
	
	public static List<CreditNoteProductEntity> getCreditNoteProductEntity(List<Product> productList, String searchValue) {
		
		List<CreditNoteProductEntity> cnProductList = new ArrayList<CreditNoteProductEntity>();
		
		CreditNoteProductEntity cnProduct =  null;
		
		for(Product prod: productList) {
			
			cnProduct = new CreditNoteProductEntity();
			
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
				cnProductList = getVariantData(cnProductList, prod.getAttributes(), cnProduct, prod.getProductDescription().getDescription(), qty, unitPrice, amount, taxClassId, searchValue, prodTaxRate);
			} else {
				
				if(searchValue != null && !searchValue.equalsIgnoreCase("")) {
				
					if(prod.getSku().toLowerCase().contains(searchValue.toLowerCase())) {
						
						cnProduct.productId = prod.getId();
						cnProduct.productName = prod.getSku();
						cnProduct.description = prod.getProductDescription().getDescription();
						cnProduct.quantity = qty;
						cnProduct.unitPrice = unitPrice;
						cnProduct.amount = amount;
						cnProduct.taxClassId = taxClassId;
						cnProduct.taxAmount = prodTaxRate;
						
						cnProductList.add(cnProduct);
					}
				
				} else {
					
					cnProduct.productId = prod.getId();
					cnProduct.productName = prod.getSku();
					cnProduct.description = prod.getProductDescription().getDescription();
					cnProduct.quantity = qty;
					cnProduct.unitPrice = unitPrice;
					cnProduct.amount = amount;
					cnProduct.taxClassId = taxClassId;
					cnProduct.taxAmount = prodTaxRate;
					
					cnProductList.add(cnProduct);
					
				}
			}
		}
		
		return cnProductList;
	}
	
	private static List<CreditNoteProductEntity> getVariantData(List<CreditNoteProductEntity> siProductList, Set<ProductAttribute> prodAttrList,
			CreditNoteProductEntity cnProduct, String prodDesc, Integer qty, BigDecimal unitPrice, BigDecimal amount, Long taxClassId, String searchValue, BigDecimal prodTaxRate) {
		
		for(ProductAttribute prodAttr: prodAttrList) {
			
			cnProduct = new CreditNoteProductEntity();
			
			String origProdName = prodAttr.getProduct().getSku();
			String prodOpt = prodAttr.getProductOption().getCode();
			
			if(prodAttr.getProductOptionValue().getDescriptions() != null && prodAttr.getProductOptionValue().getDescriptions().size() > 0) {
				
				for(ProductOptionValueDescription prodOptValDesc: prodAttr.getProductOptionValue().getDescriptions()) {
					
					String prodName = origProdName + " " + prodOpt + " " + prodOptValDesc.getName();
					
					if(prodName.contains(searchValue)) {
						
						cnProduct.productId = prodAttr.getProduct().getId();
						cnProduct.productName = prodName;
						cnProduct.variantId = prodAttr.getId();
						cnProduct.description = prodDesc;
						cnProduct.quantity = qty;
						cnProduct.unitPrice = unitPrice;
						cnProduct.amount = amount;
						cnProduct.taxClassId = taxClassId;
						cnProduct.taxAmount = prodTaxRate;
						
						siProductList.add(cnProduct);
					}
					
				}
			}
		}
		
		return siProductList;
	}
	
	private static BigDecimal calculateAmount(BigDecimal unitPrice, Integer quantity) {
		BigDecimal totalAmt = unitPrice.multiply(new BigDecimal(quantity));
		return totalAmt;
	}
	
	public CreditNoteProduct getCreditNoteProduct(CreditNoteProductEntity cnProductEntity, SalesInvoice invoice) {
		
		CreditNoteProduct siProduct = new CreditNoteProduct();
		
		Product prod = new Product();
		prod.setId(cnProductEntity.productId);
		
		if(cnProductEntity.variantId != null) {
			ProductAttribute pa = new ProductAttribute();
			pa.setId(cnProductEntity.variantId);
			
			siProduct.setProductAttribute(pa);
		}
		
		
		siProduct.setProduct(prod);
		siProduct.setSalesInvoice(invoice);
		
		siProduct.setDescription(cnProductEntity.description);
		siProduct.setQuantiry(cnProductEntity.quantity);
		siProduct.setUnitPrice(cnProductEntity.unitPrice);
		siProduct.setProductName(cnProductEntity.productName);
		
		return siProduct;
	}
	
	public static List<CreditNoteProductEntity> getProductJsonFromCustomProducts(List<CreditNoteProduct> creditNoteProductList) {
		
		List<CreditNoteProductEntity> creditNoteProductEntityList = new ArrayList<CreditNoteProductEntity>();
		
		CreditNoteProductEntity creditNoteProductEntity = null;
		
		for(CreditNoteProduct cnp: creditNoteProductList) {
			creditNoteProductEntity = new CreditNoteProductEntity();
			
			creditNoteProductEntity.creditNoteProductId = cnp.getId();
			creditNoteProductEntity.productId = cnp.getProduct().getId();
			creditNoteProductEntity.productName = cnp.getProductName();
			creditNoteProductEntity.description = cnp.getDescription();
			if(cnp.getProductAttribute() == null) {
				creditNoteProductEntity.variantId = null;
			} else {
				creditNoteProductEntity.variantId = cnp.getProductAttribute().getId();
			}
			
			creditNoteProductEntity.quantity = cnp.getQuantiry();
			creditNoteProductEntity.unitPrice = cnp.getUnitPrice();
			creditNoteProductEntity.amount = calculateAmount(cnp.getUnitPrice(), cnp.getQuantiry());
			
			if(cnp.getTaxClass() != null) {
				creditNoteProductEntity.taxClassId = cnp.getTaxClass().getId();
				creditNoteProductEntity.taxAmount = cnp.getTaxAmount();
			} else {
				creditNoteProductEntity.taxAmount = new BigDecimal(0.00);
			}
			
			creditNoteProductEntityList.add(creditNoteProductEntity);
		}
		
		return creditNoteProductEntityList;
	}
	
	public static CreditNoteProduct populateCreditNoteProduct(CreditNoteProductEntity creditNoteProductEntity) {
		
		CreditNoteProduct creditNoteProduct = new CreditNoteProduct();
		
		Product prod = new Product();
		prod.setId(creditNoteProductEntity.productId);
		creditNoteProduct.setProduct(prod);
		
		ProductAttribute prodAttribute = null;
		if(creditNoteProductEntity.variantId != null) {
			prodAttribute = new ProductAttribute();
			prodAttribute.setId(creditNoteProductEntity.variantId);
		}
		creditNoteProduct.setProductAttribute(prodAttribute);
		
		TaxClass taxClass = null;
		if(creditNoteProductEntity.taxClassId != null) {
			taxClass = new TaxClass();
			taxClass.setId(creditNoteProductEntity.taxClassId);
		}
		creditNoteProduct.setTaxClass(taxClass);
		
		SalesInvoice salesInvoice = null;
		if(creditNoteProductEntity.invoiceNumber != null) {
			salesInvoice = new SalesInvoice();
			salesInvoice.setId(creditNoteProductEntity.invoiceNumber);
		}
		
		creditNoteProduct.setId(creditNoteProductEntity.creditNoteProductId);
		creditNoteProduct.setSalesInvoice(salesInvoice);
		creditNoteProduct.setProductName(creditNoteProductEntity.productName);
		creditNoteProduct.setDescription(creditNoteProductEntity.description);
		creditNoteProduct.setQuantiry(creditNoteProductEntity.quantity);
		creditNoteProduct.setTaxAmount(creditNoteProductEntity.taxAmount);
		creditNoteProduct.setUnitPrice(creditNoteProductEntity.unitPrice);
		
		creditNoteProduct.setUpdated(new Date());
		
		return creditNoteProduct;
	}
}
