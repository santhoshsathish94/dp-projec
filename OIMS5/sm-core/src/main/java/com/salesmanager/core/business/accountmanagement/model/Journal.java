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
@Table(name = "JOURNAL")
public class Journal extends SalesManagerEntity<Long, Journal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "JOURNAL_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long journal_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private MerchantStore merchantStore;

	@Column(name = "JOURNAL_REF_NO")
	private String journal_ref_no;

	@Column(name = "JOURNAL_DATE")
	private Date journal_date;
	@Transient
	private String journal_Sdate;

	@Column(name = "JOURNAL_DEBIT")
	private String journal_debit;

	@Column(name = "JOURNAL_DEBIT_AMMOUNT")
	private BigDecimal journal_debit_ammount;

	@Column(name = "JOURNAL_CREDIT")
	private String journal_credit;

	@Column(name = "JOURNAL_CREDIT_AMMOUNT")
	private BigDecimal journal_credit_ammount;

	@Column(name = "JOURNAL_NARRATION")
	private String journal_narration;

	@Column(name = "ENTERED_BY")
	private String entered_by;

	@Column(name = "ENTRY_DATE")
	private Date entry_date;

	@Transient
	private String jsonArray;

	@Transient
	private String jsonArray1;

	public Long getJournal_id() {
		return journal_id;
	}

	public void setJournal_id(Long journal_id) {
		this.journal_id = journal_id;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public String getJournal_ref_no() {
		return journal_ref_no;
	}

	public void setJournal_ref_no(String journal_ref_no) {
		this.journal_ref_no = journal_ref_no;
	}

	public Date getJournal_date() {
		return journal_date;
	}

	public void setJournal_date(Date journal_date) {
		this.journal_date = journal_date;
	}

	public String getJournal_Sdate() {
		return journal_Sdate;
	}

	public void setJournal_Sdate(String journal_Sdate) {
		this.journal_Sdate = journal_Sdate;
	}

	public String getJournal_debit() {
		return journal_debit;
	}

	public void setJournal_debit(String journal_debit) {
		this.journal_debit = journal_debit;
	}

	public BigDecimal getJournal_debit_ammount() {
		return journal_debit_ammount;
	}

	public void setJournal_debit_ammount(BigDecimal journal_debit_ammount) {
		this.journal_debit_ammount = journal_debit_ammount;
	}

	public String getJournal_credit() {
		return journal_credit;
	}

	public void setJournal_credit(String journal_credit) {
		this.journal_credit = journal_credit;
	}

	public BigDecimal getJournal_credit_ammount() {
		return journal_credit_ammount;
	}

	public void setJournal_credit_ammount(BigDecimal journal_credit_ammount) {
		this.journal_credit_ammount = journal_credit_ammount;
	}

	public String getJournal_narration() {
		return journal_narration;
	}

	public void setJournal_narration(String journal_narration) {
		this.journal_narration = journal_narration;
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
		return jsonArray;
	}

	public void setJsonArray(String jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getJsonArray1() {
		return jsonArray1;
	}

	public void setJsonArray1(String jsonArray1) {
		this.jsonArray1 = jsonArray1;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.journal_id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.journal_id = id;

	}

}
