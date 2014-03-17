package com.salesmanager.core.business.supplier.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.business.reference.currency.model.Currency;
import com.salesmanager.core.business.reference.zone.model.Zone;

@Entity
@Table(name="SUPPLIER")
public class Supplier extends SalesManagerEntity<Integer, Supplier> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8100481134840437020L;
	
	
	@Id
	@Column(name="SUPPLIER_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "SUPPLIER_NAME", length=100, nullable=false)
	private String supplierName;
	
	@Column(name = "OPENING_BANALCE", length=0, precision=1)
	private Double openingBalance;
	
	@Column(name = "COUNTRY", length=100)
	private String country;
	
	@Column(name = "ADDRESS_LABEL", length=100, nullable=false)
	private String addressLabel;
	
	@Column(name = "MOBILE_NUMBER")
	private Integer mobileNumber;
	
	@Column(name = "WORK_NUMBER", length=100)
	private String workNumber;
	
	@Column(name = "HOME_NUMBER", length=100)
	private String homeNumber;
	
	@Column(name = "EMAIL", length=100)
	private String email;
	
	@Column(name = "ALTERNATE_EMAIL", length=100)
	private String alternateEmail;
	
	@Column(name = "PAN_NUMBER", length=100)
	private String panNumber;
	
	@Column(name = "TIN_NUMBER", length=100)
	private String tinNumber;
	
	@Column(name = "SERVICE_TAX_NUMBER", length=100)
	private String serviceTaxNumber;
	
	@Column(name = "ADDRESS", length=255)
	private String address;
	
	@Column(name = "CITY", length=100)
	private String city;
	
	@Column(name = "POSTAL_CODE", length=100)
	private String postalCode;
	
	@Column(name = "UPDATED")
	private Date updated;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Currency.class)
	@JoinColumn(name = "CURRENCY_ID", nullable=true)
	private Currency currency;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="STATE_ID", nullable=true, updatable=true)
	private Zone zone;
	
	
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * @return the openingBalance
	 */
	public Double getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * @param openingBalance the openingBalance to set
	 */
	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the addressLabel
	 */
	public String getAddressLabel() {
		return addressLabel;
	}

	/**
	 * @param addressLabel the addressLabel to set
	 */
	public void setAddressLabel(String addressLabel) {
		this.addressLabel = addressLabel;
	}

	/**
	 * @return the mobileNumber
	 */
	public Integer getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(Integer mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the workNumber
	 */
	public String getWorkNumber() {
		return workNumber;
	}

	/**
	 * @param workNumber the workNumber to set
	 */
	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	/**
	 * @return the homeNumber
	 */
	public String getHomeNumber() {
		return homeNumber;
	}

	/**
	 * @param homeNumber the homeNumber to set
	 */
	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the alternateEmail
	 */
	public String getAlternateEmail() {
		return alternateEmail;
	}

	/**
	 * @param alternateEmail the alternateEmail to set
	 */
	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	/**
	 * @return the panNumber
	 */
	public String getPanNumber() {
		return panNumber;
	}

	/**
	 * @param panNumber the panNumber to set
	 */
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	/**
	 * @return the tinNumber
	 */
	public String getTinNumber() {
		return tinNumber;
	}

	/**
	 * @param tinNumber the tinNumber to set
	 */
	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}

	/**
	 * @return the serviceTaxNumber
	 */
	public String getServiceTaxNumber() {
		return serviceTaxNumber;
	}

	/**
	 * @param serviceTaxNumber the serviceTaxNumber to set
	 */
	public void setServiceTaxNumber(String serviceTaxNumber) {
		this.serviceTaxNumber = serviceTaxNumber;
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
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * @return the zone
	 */
	public Zone getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(Zone zone) {
		this.zone = zone;
	}

}
