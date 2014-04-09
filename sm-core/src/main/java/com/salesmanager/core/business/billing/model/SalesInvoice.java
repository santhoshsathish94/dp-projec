package com.salesmanager.core.business.billing.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.order.model.Order;

@Entity
@Table(name="SALES_INVOICE")
public class SalesInvoice extends SalesManagerEntity<Long, SalesInvoice> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5549817981689841863L;

	@Id
	@Column(name="INVOICE_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
	@JoinColumn(name = "CUSTOMER_ID", nullable=true)
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Order.class)
	@JoinColumn(name = "ORDER_ID", nullable=true)
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = MerchantStore.class)
	@JoinColumn(name = "MERCHANT_ID", nullable=true)
	private MerchantStore merchantStore;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = InvoiceSetting.class)
	@JoinColumn(name = "SALES_INVOICE_SETTING_ID", nullable=true)
	private InvoiceSetting invoiceSetting;
	
	@Column(name = "SALES_INVOICE_SERIES", length = 50)
	private String invoiceSeries;
	
	@Column(name = "CASH_ADDRESS")
	private String address;
	
	@Column(name = "CASH_RECEIPT_MODE", length = 100)
	private String receiptMode;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "INVOICE_DATE")
	private Date date;
	
	@Column(name = "INVOICE_DUE_DATE")
	private Date dueDate;
	
	@Column(name = "INVOICE_COMMENT", length=100)
	private String commant;
	
	@Column(name = "UPDATED")
	private Date updated;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "salesInvoice")
	private Set<SalesInvoiceProduct> invoiceProducts = new HashSet<SalesInvoiceProduct>();
	
	@Transient
	private String productJson;
	
	@Transient
	private String invoiceDate;
	
	@Transient
	private String tempDueDate;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the commant
	 */
	public String getCommant() {
		return commant;
	}

	/**
	 * @param commant the commant to set
	 */
	public void setCommant(String commant) {
		this.commant = commant;
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
	 * @return the invoiceProducts
	 */
	public Set<SalesInvoiceProduct> getInvoiceProducts() {
		return invoiceProducts;
	}

	/**
	 * @param invoiceProducts the invoiceProducts to set
	 */
	public void setInvoiceProducts(Set<SalesInvoiceProduct> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the receiptMode
	 */
	public String getReceiptMode() {
		return receiptMode;
	}

	/**
	 * @param receiptMode the receiptMode to set
	 */
	public void setReceiptMode(String receiptMode) {
		this.receiptMode = receiptMode;
	}

	/**
	 * @return the invoiceSetting
	 */
	public InvoiceSetting getInvoiceSetting() {
		return invoiceSetting;
	}

	/**
	 * @param invoiceSetting the invoiceSetting to set
	 */
	public void setInvoiceSetting(InvoiceSetting invoiceSetting) {
		this.invoiceSetting = invoiceSetting;
	}

	/**
	 * @return the invoiceDate
	 */
	public String getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return the tempDueDate
	 */
	public String getTempDueDate() {
		return tempDueDate;
	}

	/**
	 * @param tempDueDate the tempDueDate to set
	 */
	public void setTempDueDate(String tempDueDate) {
		this.tempDueDate = tempDueDate;
	}

	/**
	 * @return the invoiceSeries
	 */
	public String getInvoiceSeries() {
		return invoiceSeries;
	}

	/**
	 * @param invoiceSeries the invoiceSeries to set
	 */
	public void setInvoiceSeries(String invoiceSeries) {
		this.invoiceSeries = invoiceSeries;
	}

}
