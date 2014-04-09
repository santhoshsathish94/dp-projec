package com.salesmanager.core.business.supplier.model;

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

import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.generic.model.SalesManagerEntity;

@Entity
@Table(name="PARTY_ITEM_DEFAULT_MARGIN")
public class PartyItemDefaultMargin extends SalesManagerEntity<Long, PartyItemDefaultMargin> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5908048883830681437L;
	
	@Id
	@Column(name="DEFAULT_MARGIN_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Customer.class)
	@JoinColumn(name = "CUSTOMER_ID", nullable=false)
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", nullable=false)
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Company.class)
	@JoinColumn(name = "COMPANY_ID", nullable=false)
	private Company company;
	
	@Column(name = "CD_FIELD")
	private String cdField;
	
	@Column(name = "ADD_FIELD")
	private String addField;
	
	@Column(name = "TD_FIELD")
	private String tdField;
	
	@Column(name = "RATE_FIELD")
	private String rateField;
	
	@Column(name = "UPDATED")
	private Date updated;
	
	@Transient
	private String companyName;
	
	@Transient
	private String productName;
	
	@Transient
	private String customerAccountName;
	
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
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the cdField
	 */
	public String getCdField() {
		return cdField;
	}

	/**
	 * @param cdField the cdField to set
	 */
	public void setCdField(String cdField) {
		this.cdField = cdField;
	}

	/**
	 * @return the addField
	 */
	public String getAddField() {
		return addField;
	}

	/**
	 * @param addField the addField to set
	 */
	public void setAddField(String addField) {
		this.addField = addField;
	}

	/**
	 * @return the tdField
	 */
	public String getTdField() {
		return tdField;
	}

	/**
	 * @param tdField the tdField to set
	 */
	public void setTdField(String tdField) {
		this.tdField = tdField;
	}

	/**
	 * @return the rateField
	 */
	public String getRateField() {
		return rateField;
	}

	/**
	 * @param rateField the rateField to set
	 */
	public void setRateField(String rateField) {
		this.rateField = rateField;
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
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	 * @return the customerAccountName
	 */
	public String getCustomerAccountName() {
		return customerAccountName;
	}

	/**
	 * @param customerAccountName the customerAccountName to set
	 */
	public void setCustomerAccountName(String customerAccountName) {
		this.customerAccountName = customerAccountName;
	}


}
