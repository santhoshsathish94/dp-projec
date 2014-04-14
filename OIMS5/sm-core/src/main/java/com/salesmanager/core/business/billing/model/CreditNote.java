package com.salesmanager.core.business.billing.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.order.model.Order;
import com.salesmanager.core.business.tax.model.taxclass.TaxClass;

@Entity
@Table(name="CREDIT_NOTE")
public class CreditNote extends SalesManagerEntity<Long, CreditNote> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8364727332138561470L;
	
	@Id
	@Column(name="CREDIT_NOTE_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
	@JoinColumn(name = "CREDIT_TO_CUSTOMER_ID", nullable=false)
	private Customer creditCustomer;
	
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Order.class)
	@JoinColumn(name = "ORDER_ID", nullable=true)
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name="OTHER_TAX_CLASS_ID", nullable=true)
	private TaxClass taxClassOther;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
	@JoinColumn(name = "DEBIT_TO_CUSTOMER_ID", nullable=true)
	private Customer debitCustomer;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = MerchantStore.class)
	@JoinColumn(name = "MERCHANT_ID", nullable=true)
	private MerchantStore merchantStore;
	
	/*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "creditNote")
	private Set<CreditNoteProduct> creditNoteProduct = new TreeSet<CreditNoteProduct>();*/
	
	@Column(name = "CREDIT_NOTE_DATE")
	private Date noteDate;
	
	@Column(name = "OTHER_AMOUNT", nullable = true)
	private BigDecimal otherAmount;
	
	@Column(name = "CREDIT_NOTE_TYPE", length = 50)
	private String creditNoteType;
	
	@Column(name = "UPDATED")
	private Date updated;
	
	@Transient
	private String productJson;
	
	@Transient
	private String tempNoteDate;
	
	
	
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
	 * @return the creditCustomer
	 */
	public Customer getCreditCustomer() {
		return creditCustomer;
	}

	/**
	 * @param creditCustomer the creditCustomer to set
	 */
	public void setCreditCustomer(Customer creditCustomer) {
		this.creditCustomer = creditCustomer;
	}

	/**
	 * @return the dueDate
	 */
	public Date getNoteDate() {
		return noteDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * @return the creditNoteType
	 */
	public String getCreditNoteType() {
		return creditNoteType;
	}

	/**
	 * @param creditNoteType the creditNoteType to set
	 */
	public void setCreditNoteType(String creditNoteType) {
		this.creditNoteType = creditNoteType;
	}

	/**
	 * @return the orderAmount
	 */
	public BigDecimal getOtherAmount() {
		return otherAmount;
	}

	/**
	 * @param orderAmount the orderAmount to set
	 */
	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	/**
	 * @return the taxClassOther
	 */
	public TaxClass getTaxClassOther() {
		return taxClassOther;
	}

	/**
	 * @param taxClassOther the taxClassOther to set
	 */
	public void setTaxClassOther(TaxClass taxClassOther) {
		this.taxClassOther = taxClassOther;
	}

	/**
	 * @return the debitCustomer
	 */
	public Customer getDebitCustomer() {
		return debitCustomer;
	}

	/**
	 * @param debitCustomer the debitCustomer to set
	 */
	public void setDebitCustomer(Customer debitCustomer) {
		this.debitCustomer = debitCustomer;
	}

	/**
	 * @return the merchantStore
	 */
	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	/**
	 * @param merchantStore the merchantStore to set
	 */
	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
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
	 * @return the productJson
	 */
	public String getProductJson() {
		return productJson;
	}

	/**
	 * @param productJson the productJson to set
	 */
	public void setProductJson(String productJson) {
		this.productJson = productJson;
	}

	/**
	 * @return the tempDueDate
	 */
	public String getTempNoteDate() {
		return tempNoteDate;
	}

	/**
	 * @param tempDueDate the tempDueDate to set
	 */
	public void setTempNoteDate(String tempNoteDate) {
		this.tempNoteDate = tempNoteDate;
	}

	/**
	 * @return the creditNoteProduct
	 *//*
	public Set<CreditNoteProduct> getCreditNoteProduct() {
		return creditNoteProduct;
	}

	*//**
	 * @param creditNoteProduct the creditNoteProduct to set
	 *//*
	public void setCreditNoteProduct(Set<CreditNoteProduct> creditNoteProduct) {
		this.creditNoteProduct = creditNoteProduct;
	}*/
}
