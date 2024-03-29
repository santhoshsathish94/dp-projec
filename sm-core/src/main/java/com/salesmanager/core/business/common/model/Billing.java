package com.salesmanager.core.business.common.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.zone.model.Zone;

@Embeddable
public class Billing {
	
	

	
	@Column (name ="BILLING_NAME", length=64)
	private String name;
	
	@Column (name ="BILLING_COMPANY", length=100)
	private String company;
	
	@Column (name ="BILLING_STREET_ADDRESS", length=256)
	private String address;
	
	
	@Column (name ="BILLING_CITY", length=100)
	private String city;
	
	@Column (name ="BILLING_POSTCODE", length=20)
	private String postalCode;
	
	@Column(name="BILLING_TELEPHONE", length=32)
	private String telephone;
	
	@Column (name ="BILLING_STATE", length=100)
	private String state;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="BILLING_COUNTRY_ID", nullable=false)
	private Country country;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="BILLING_ZONE_ID", nullable=true)
	private Zone zone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephone() {
		return telephone;
	}
	
}
