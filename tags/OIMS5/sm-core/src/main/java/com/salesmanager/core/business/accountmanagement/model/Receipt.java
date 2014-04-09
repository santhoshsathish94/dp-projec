package com.salesmanager.core.business.accountmanagement.model;

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

import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.constants.SchemaConstant;

@Entity
@Table(name = "RECEIPT", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class Receipt extends SalesManagerEntity<Long, Receipt> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RECEIPT_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long receipt_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private MerchantStore merchantStore;

	@Column(name = "RECEIPT_FROM")
	private String receipt_from;

	@Column(name = "RECEIPT_DATE")
	private Date receipt_date;
	@Transient
	private String receipt_Sdate;

	@Column(name = "RECEIPT_AMMOUNT")
	private BigDecimal receipt_ammount;

	@Column(name = "RECEIPT_MODE")
	private String receipt_mode;

	@Column(name = "RECEIPT_TRANSACTION_NO")
	private String receipt_transaction_no;

	@Column(name = "RECEIPT_COMMENT")
	private String receipt_comment;

	@Column(name = "ENTERED_BY")
	private String entered_by;

	@Column(name = "ENTRY_DATE")
	private Date entry_date;

	public Long getReceipt_id() {
		return receipt_id;
	}

	public void setReceipt_id(Long receipt_id) {
		this.receipt_id = receipt_id;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public String getReceipt_from() {
		return receipt_from;
	}

	public void setReceipt_from(String receipt_from) {
		this.receipt_from = receipt_from;
	}

	

	public Date getReceipt_date() {
		return receipt_date;
	}

	public void setReceipt_date(Date receipt_date) {
		this.receipt_date = receipt_date;
	}

	public String getReceipt_Sdate() {
		return receipt_Sdate;
	}

	public void setReceipt_Sdate(String receipt_Sdate) {
		this.receipt_Sdate = receipt_Sdate;
	}

	public BigDecimal getReceipt_ammount() {
		return receipt_ammount;
	}

	public void setReceipt_ammount(BigDecimal receipt_ammount) {
		this.receipt_ammount = receipt_ammount;
	}

	public String getReceipt_mode() {
		return receipt_mode;
	}

	public void setReceipt_mode(String receipt_mode) {
		this.receipt_mode = receipt_mode;
	}

	public String getReceipt_transaction_no() {
		return receipt_transaction_no;
	}

	public void setReceipt_transaction_no(String receipt_transaction_no) {
		this.receipt_transaction_no = receipt_transaction_no;
	}

	public String getReceipt_comment() {
		return receipt_comment;
	}

	public void setReceipt_comment(String receipt_comment) {
		this.receipt_comment = receipt_comment;
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

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.receipt_id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.receipt_id = id;

	}

}
