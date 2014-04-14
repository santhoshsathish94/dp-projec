package com.salesmanager.core.business.billing.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductAttribute;
import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.business.tax.model.taxclass.TaxClass;

@Entity
@Table(name="CREDIT_NOTE_PRODUCT")
public class CreditNoteProduct extends SalesManagerEntity<Long, CreditNoteProduct> implements Comparable<CreditNoteProduct> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 132004984117385215L;
	
	@Id
	@Column(name="CN_PRODUCT_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", nullable=true)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = ProductAttribute.class)
	@JoinColumn(name = "PRODUCT_ATTRIBUTE_ID", nullable=true)
	private ProductAttribute productAttribute;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name="TAX_CLASS_ID", nullable=true)
	private TaxClass taxClass;
	
	@ManyToOne
	@JoinColumn(name="CREDIT_NOTE_ID", nullable=false)
	private CreditNote creditNote;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = SalesInvoice.class)
	@JoinColumn(name = "SALES_INVOICE_ID", nullable=true)
	private SalesInvoice salesInvoice;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "QUANTITY")
	private Integer quantiry;
	
	@Column(name = "UNIT_PRICE")
	private BigDecimal unitPrice;
	
	@Column(name = "TAX_AMOUNT")
	private BigDecimal taxAmount;
	
	@Column(name = "UPDATED")
	private Date updated;
	
	
	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the productAttribute
	 */
	public ProductAttribute getProductAttribute() {
		return productAttribute;
	}

	/**
	 * @param productAttribute the productAttribute to set
	 */
	public void setProductAttribute(ProductAttribute productAttribute) {
		this.productAttribute = productAttribute;
	}

	/**
	 * @return the taxClass
	 */
	public TaxClass getTaxClass() {
		return taxClass;
	}

	/**
	 * @param taxClass the taxClass to set
	 */
	public void setTaxClass(TaxClass taxClass) {
		this.taxClass = taxClass;
	}

	/**
	 * @return the creditNote
	 */
	public CreditNote getCreditNote() {
		return creditNote;
	}

	/**
	 * @param creditNote the creditNote to set
	 */
	public void setCreditNote(CreditNote creditNote) {
		this.creditNote = creditNote;
	}

	/**
	 * @return the salesInvoice
	 */
	public SalesInvoice getSalesInvoice() {
		return salesInvoice;
	}

	/**
	 * @param salesInvoice the salesInvoice to set
	 */
	public void setSalesInvoice(SalesInvoice salesInvoice) {
		this.salesInvoice = salesInvoice;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the quantiry
	 */
	public Integer getQuantiry() {
		return quantiry;
	}

	/**
	 * @param quantiry the quantiry to set
	 */
	public void setQuantiry(Integer quantiry) {
		this.quantiry = quantiry;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the taxAmount
	 */
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	
	@Override
	public int compareTo(CreditNoteProduct o) {
		if(this.getId() != null && o.getId() != null) {
			
			if(this.getId() < o.getId()) {
				return -1;
			}
			
			return this.getId() == o.getId() ? 0 : 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean equals(Object object){
		if(((CreditNoteProduct)object).getId() != null) {
			return getId().equals(((CreditNoteProduct)object).getId());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode(){
		if(getId() != null) {
			return getId().hashCode();
		} else {
			return 0;
		}
	}

}
