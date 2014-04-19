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
@Table(name = "PAYMENT")
public class Payment extends SalesManagerEntity<Long, Payment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PAYMENT_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long payment_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private MerchantStore merchantStore;

	@Column(name = "PAYMENT_TO")
	private String payment_to;

	@Column(name = "PAYMENT_DATE")
	private Date payment_date;
	@Transient
	private String payment_Sdate;

	@Column(name = "PAYMENT_AMMOUNT")
	private BigDecimal payment_ammount;

	@Column(name = "PAYMENT_MODE")
	private String payment_mode;

	@Column(name = "PAYMENT_TRANSACTION_NO")
	private String payment_transaction_no;

	@Column(name = "PAYMENT_COMMENT")
	private String payment_comment;

	@Column(name = "ENTERED_BY")
	private String entered_by;

	@Column(name = "ENTRY_DATE")
	private Date entry_date;

	public Long getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(Long payment_id) {
		this.payment_id = payment_id;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public String getPayment_to() {
		return payment_to;
	}

	public void setPayment_to(String payment_to) {
		this.payment_to = payment_to;
	}

	public Date getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}

	public String getPayment_Sdate() {
		return payment_Sdate;
	}

	public void setPayment_Sdate(String payment_Sdate) {
		this.payment_Sdate = payment_Sdate;
	}

	public BigDecimal getPayment_ammount() {
		return payment_ammount;
	}

	public void setPayment_ammount(BigDecimal payment_ammount) {
		this.payment_ammount = payment_ammount;
	}

	public String getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}

	public String getPayment_transaction_no() {
		return payment_transaction_no;
	}

	public void setPayment_transaction_no(String payment_transaction_no) {
		this.payment_transaction_no = payment_transaction_no;
	}

	public String getPayment_comment() {
		return payment_comment;
	}

	public void setPayment_comment(String payment_comment) {
		this.payment_comment = payment_comment;
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
		return this.payment_id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.payment_id = id;

	}

}
