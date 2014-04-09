package com.salesmanager.core.business.company.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;

@Entity
@Table(name = "COMPANY_CURRENCIES")
public class CompanyCurrencies  extends SalesManagerEntity<Integer, CompanyCurrencies>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9209655236196774629L;
	
	@Id
	@Column(name="CURRENCY_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(targetEntity = Company.class)
	@JoinColumn(name="COMPANY_ID", nullable=false, updatable=true)
	private Company company;
	
	@Column(name = "CURRENCY_SYMBOL", length=50)
	private String symbl;
	
	@Column(name = "CURRENCY_CODE", length=50)
	private String code;
	
	@Column(name = "CURRENCY_DECIMAL_POSITION")
	private int decimalPosition;
	
	@Column(name = "CURRENCY_EXCHANGE_RATE")
	private double exchangeRate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED")
	private Date updated = new Date();
	
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the currencySymbl
	 */
	public String getSymbl() {
		return symbl;
	}

	/**
	 * @param currencySymbl the currencySymbl to set
	 */
	public void setSymbl(String symbl) {
		this.symbl = symbl;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the decimalPosition
	 */
	public int getDecimalPosition() {
		return decimalPosition;
	}

	/**
	 * @param decimalPosition the decimalPosition to set
	 */
	public void setDecimalPosition(int decimalPosition) {
		this.decimalPosition = decimalPosition;
	}

	/**
	 * @return the exchangeRate
	 */
	public double getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param exchangeRate the exchangeRate to set
	 */
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
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

}
