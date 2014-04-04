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
@Table(name = "BRANCH_TRANSFER", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class BranchTransfer extends SalesManagerEntity<Long, BranchTransfer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2676237056070958550L;

	@Id
	@Column(name = "TRANSFER_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transfer_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private MerchantStore merchantStore;

	@Column(name = "STORE_FROM")
	private String store_from;

	@Column(name = "STORE_TO")
	private String store_to;

	@Column(name = "TRANSFER_DATE")
	private Date transfer_date;
	@Transient
	private String transfer_Sdate;

	@Column(name = "TRANSFER_COMMENT")
	private String transfer_comment;

	@Column(name = "SKU")
	private String sku;

	@Column(name = "AVAILABLE_QUANTITY")
	private int available_quantity;

	@Column(name = "TRANSFER_QUANTITY")
	private int transfer_quantity;

	@Column(name = "ENTERED_BY")
	private String entered_by;

	@Column(name = "ENTRY_DATE")
	private Date entry_date;

	@Transient
	private String JsonArray;

	public Long getTransfer_id() {
		return transfer_id;
	}

	public void setTransfer_id(Long transfer_id) {
		this.transfer_id = transfer_id;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public String getStore_from() {
		return store_from;
	}

	public void setStore_from(String store_from) {
		this.store_from = store_from;
	}

	public String getStore_to() {
		return store_to;
	}

	public void setStore_to(String store_to) {
		this.store_to = store_to;
	}

	public Date getTransfer_date() {
		return transfer_date;
	}

	public void setTransfer_date(Date transfer_date) {
		this.transfer_date = transfer_date;
	}

	public String getTransfer_Sdate() {
		return transfer_Sdate;
	}

	public void setTransfer_Sdate(String transfer_Sdate) {
		this.transfer_Sdate = transfer_Sdate;
	}

	public String getTransfer_comment() {
		return transfer_comment;
	}

	public void setTransfer_comment(String transfer_comment) {
		this.transfer_comment = transfer_comment;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getAvailable_quantity() {
		return available_quantity;
	}

	public void setAvailable_quantity(int available_quantity) {
		this.available_quantity = available_quantity;
	}

	public int getTransfer_quantity() {
		return transfer_quantity;
	}

	public void setTransfer_quantity(int transfer_quantity) {
		this.transfer_quantity = transfer_quantity;
	}

	public String getEntered_by() {
		return entered_by;
	}

	public void setEntered_by(String entered_by) {
		this.entered_by = entered_by;
	}

	public Date getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}

	public String getJsonArray() {
		return JsonArray;
	}

	public void setJsonArray(String jsonArray) {
		JsonArray = jsonArray;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.transfer_id;
	}

	@Override
	public void setId(Long id) {
		this.transfer_id = id;

	}

	public void reset() {

	}

}
