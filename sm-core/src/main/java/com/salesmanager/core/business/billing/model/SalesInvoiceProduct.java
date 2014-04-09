package com.salesmanager.core.business.billing.model;

import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name="SALES_INVOICE_PRODUCT_INFO")
public class SalesInvoiceProduct extends SalesManagerEntity<Long, SalesInvoiceProduct> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8671495159493662296L;
	
	@Id
	@Column(name="PRODUCT_INFO_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", nullable=true)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = ProductAttribute.class)
	@JoinColumn(name = "PRODUCT_ATTRIBUTE_ID", nullable=true)
	private ProductAttribute productAttribute;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = SalesInvoice.class)
	@JoinColumn(name = "SALES_INVOICE_ID", nullable=true)
	private SalesInvoice salesInvoice;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name="TAX_CLASS_ID", nullable=true)
	private TaxClass taxClass;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "QUANTITY")
	private Integer quantiry;
	
	@Column(name = "UOM", length = 10)
	private String uom;

	@Column(name = "UNIT_PRICE")
	private BigDecimal unitPrice;
	
	@Column(name = "UPDATED")
	private Date updated;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	
	@Override
	public Long getId() {
		return id;
	}

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
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
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

}
