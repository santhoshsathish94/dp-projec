/**
 * 
 */
package com.salesmanager.core.business.inventory.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.constants.SchemaConstant;

/**
 * @author iShop
 * 
 */

@Entity
@Table(name = "STOCK", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class Stock extends SalesManagerEntity<Long, Stock> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2676237056070958550L;

	@Id
	@Column(name = "STOCK_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stockId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MERCHANT_ID", nullable=false)
	private MerchantStore merchantStore;

	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Column(name = "StockSKU")
	private String stockSKU;

	@Column(name = "STOCK_QUANTITY")
	private Integer stockQuantity;

	@Column(name = "STOCK_UOM")
	private String stockUOM;

	@Column(name = "STOCK_UNIT_PRICE")
	private BigDecimal stockUnitPrice;

	@Column(name = "STOCK_AMOUNT")
	private BigDecimal stockAmount;

	@Column(name = "STOCK_DATE")
	private Date stockDate;
	@Transient
	private String stockSDate;

	@Column(name = "STOCK_COMMENT", nullable = true)
	private String stockComment;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;
	@Transient
	private String updatedSDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private String updatedBy;

	@Transient
	private String openingStocks;
	
	public String getOpeningStocks() {
		return openingStocks;
	}

	public void setOpeningStocks(String openingStocks) {
		this.openingStocks = openingStocks;
	}

	public Date getStockDate() {
		return stockDate;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.stockId;
	}

	@Override
	public void setId(Long id) {
		this.stockId = id;

	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getStockSKU() {
		return stockSKU;
	}

	public void setStockSKU(String stockSKU) {
		this.stockSKU = stockSKU;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getStockUOM() {
		return stockUOM;
	}

	public void setStockUOM(String stockUOM) {
		this.stockUOM = stockUOM;
	}

	public BigDecimal getStockUnitPrice() {
		return stockUnitPrice;
	}

	public void setStockUnitPrice(BigDecimal stockUnitPrice) {
		this.stockUnitPrice = stockUnitPrice;
	}

	public BigDecimal getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(BigDecimal stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getStockSDate() {
		return stockSDate;
	}

	public void setStockSDate(String stockSDate) {
		this.stockSDate = stockSDate;
	}

	public String getStockComment() {
		return stockComment;
	}

	public void setStockComment(String stockComment) {
		this.stockComment = stockComment;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedSDate() {
		return updatedSDate;
	}

	public void setUpdatedSDate(String updatedSDate) {
		this.updatedSDate = updatedSDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public void reset() {
		this.createdBy = null;
		this.stockAmount = null;
		this.stockComment = null;
		this.stockDate = null;
		this.stockId = null;
		this.stockQuantity = null;
		this.stockSDate = null;
		this.stockSKU = null;
		this.stockUnitPrice = null;
		this.stockUOM = null;
		this.updatedBy = null;
		this.updatedDate = null;
		this.updatedSDate = null;
	}

}
