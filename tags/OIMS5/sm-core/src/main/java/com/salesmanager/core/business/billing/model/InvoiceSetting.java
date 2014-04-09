package com.salesmanager.core.business.billing.model;

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

import com.salesmanager.core.business.generic.model.SalesManagerEntity;

@Entity
@Table(name="SALES_INVOICE_SETTING")
public class InvoiceSetting extends SalesManagerEntity<Long, InvoiceSetting> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3159720749004720741L;

	@Id
	@Column(name="INVOICE_SETTING_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = InvoiceSettingType.class)
	@JoinColumn(name = "INVOICE_SETTING_TYPE_ID", nullable=true)
	private InvoiceSettingType type;
	
	@Column(name = "PREFIX", length=100, nullable=false)
	private String prefix;
	
	@Column(name = "STARTING_NUMBER", length=0)
	private int startingNumber;
	
	@Column(name = "CURRENT_NUMBER", length=0)
	private int currentNumber;
	
	@Column(name = "CURRENT_SERIES", length=50)
	private String currentSeries;
	
	@Column(name = "UPDATED")
	private Date updated;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public InvoiceSettingType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(InvoiceSettingType type) {
		this.type = type;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the startingNumber
	 */
	public int getStartingNumber() {
		return startingNumber;
	}

	/**
	 * @param startingNumber the startingNumber to set
	 */
	public void setStartingNumber(int startingNumber) {
		this.startingNumber = startingNumber;
	}

	/**
	 * @return the currentNumber
	 */
	public int getCurrentNumber() {
		return currentNumber;
	}

	/**
	 * @param currentNumber the currentNumber to set
	 */
	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}

	/**
	 * @return the currentSeries
	 */
	public String getCurrentSeries() {
		return currentSeries;
	}

	/**
	 * @param currentSeries the currentSeries to set
	 */
	public void setCurrentSeries(String currentSeries) {
		this.currentSeries = currentSeries;
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
