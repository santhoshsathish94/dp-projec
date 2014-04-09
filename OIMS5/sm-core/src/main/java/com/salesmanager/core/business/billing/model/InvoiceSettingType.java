package com.salesmanager.core.business.billing.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;

@Entity
@Table(name="SALES_INVOICE_SETTING_TYPE")
public class InvoiceSettingType extends SalesManagerEntity<Long, InvoiceSettingType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 781887523535418723L;

	@Id
	@Column(name="SETTING_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CODE", length=50, nullable=false)
	private String code;
	
	@Column(name = "TYPE_TEXT", length=100, nullable=false)
	private String text;
	
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
