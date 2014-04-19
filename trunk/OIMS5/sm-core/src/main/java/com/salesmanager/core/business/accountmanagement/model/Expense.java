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
@Table(name = "EXPENSE")
public class Expense extends SalesManagerEntity<Long, Expense> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EXPENSE_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long expense_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private MerchantStore merchantStore;

	@Column(name = "EXPENSE_DATE")
	private Date expense_date;
	@Transient
	private String expense_Sdate;

	@Column(name = "EXPENSE_REF_NO")
	private String expense_ref_no;

	@Column(name = "EXPENSE_COMMENT")
	private String expense_comment;

	@Column(name = "EXPENSE")
	private String expense;

	@Column(name = "EXPENSE_AMMOUNT")
	private BigDecimal expense_ammount;

	@Column(name = "EXPENSE_PAYMENT_MODE")
	private String expense_payment_mode;

	@Column(name = "ENTERED_BY")
	private String entered_by;
	
	@Transient
	private String jsonArray;

	@Column(name = "ENTRY_DATE")
	private Date entry_date;

	public Long getExpense_id() {
		return expense_id;
	}

	public void setExpense_id(Long expense_id) {
		this.expense_id = expense_id;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public Date getExpense_date() {
		return expense_date;
	}

	public void setExpense_date(Date expense_date) {
		this.expense_date = expense_date;
	}

	public String getExpense_Sdate() {
		return expense_Sdate;
	}

	public void setExpense_Sdate(String expense_Sdate) {
		this.expense_Sdate = expense_Sdate;
	}

	public String getExpense_ref_no() {
		return expense_ref_no;
	}

	public void setExpense_ref_no(String expense_ref_no) {
		this.expense_ref_no = expense_ref_no;
	}

	public String getExpense_comment() {
		return expense_comment;
	}

	public void setExpense_comment(String expense_comment) {
		this.expense_comment = expense_comment;
	}

	public String getExpense() {
		return expense;
	}

	public void setExpense(String expense) {
		this.expense = expense;
	}

	public BigDecimal getExpense_ammount() {
		return expense_ammount;
	}

	public void setExpense_ammount(BigDecimal expense_ammount) {
		this.expense_ammount = expense_ammount;
	}

	public String getExpense_payment_mode() {
		return expense_payment_mode;
	}

	public void setExpense_payment_mode(String expense_payment_mode) {
		this.expense_payment_mode = expense_payment_mode;
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

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.expense_id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.expense_id = id;

	}

}
