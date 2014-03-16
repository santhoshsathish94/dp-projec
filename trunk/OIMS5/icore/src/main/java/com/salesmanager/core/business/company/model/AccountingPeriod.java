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
import javax.persistence.Transient;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.utils.CloneUtils;

@Entity
@Table(name = "COMPANY_ACCOUNTING_PERIOD")
public class AccountingPeriod extends SalesManagerEntity<Integer, AccountingPeriod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9209655236196700869L;


	@Id
	@Column(name="ACCOUNTING_PERIOD_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne(targetEntity = Company.class)
	@JoinColumn(name="COMPANY_ID", nullable=false, updatable=true)
	private Company company;
	
	@Column(name = "ACC_PERIOD_STATUS")
	private boolean status;
	
	@Column(name = "ACC_PERIOD_ISDEFAULT")
	private boolean setAsDefault;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ACC_PERIOD_FROM_DATE")
	private Date fromDate = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ACC_PERIOD_TO_DATE")
	private Date toDate = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED")
	private Date created = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED")
	private Date updated = new Date();
	
	@Transient
	private String fromSDate;
	
	@Transient
	private String toSDate;
	
	

	/**
	 * @return the companyB
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
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the isDefault
	 */
	public boolean isSetAsDefault() {
		return setAsDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setSetAsDefault(boolean setAsDefault) {
		this.setAsDefault = setAsDefault;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return  CloneUtils.clone(fromDate);
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = CloneUtils.clone(fromDate);
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return  CloneUtils.clone(toDate);
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = CloneUtils.clone(toDate);
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return  CloneUtils.clone(created);
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = CloneUtils.clone(created);
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return  CloneUtils.clone(updated);
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = CloneUtils.clone(updated);
	}

	/**
	 * @return the fromSDate
	 */
	public String getFromSDate() {
		return fromSDate;
	}

	/**
	 * @param fromSDate the fromSDate to set
	 */
	public void setFromSDate(String fromSDate) {
		this.fromSDate = fromSDate;
	}

	/**
	 * @return the toSDate
	 */
	public String getToSDate() {
		return toSDate;
	}

	/**
	 * @param toSDate the toSDate to set
	 */
	public void setToSDate(String toSDate) {
		this.toSDate = toSDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
