package com.salesmanager.core.business.catalog.product.model.master;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;

@Entity
@Table(name="SHADES_MASTER")
public class Shades extends SalesManagerEntity<Long, Shades> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8801348286508965956L;

	@Id
	@Column(name="SHADES_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "SHADES_NAME", length=100, nullable=false)
	private String shadesName;

	@Column(name = "SHADES_SHORT_NAME", length=5, nullable=false)
	private String shortName;
	
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
	 * @return the shadesName
	 */
	public String getShadesName() {
		return shadesName;
	}

	/**
	 * @param shadesName the shadesName to set
	 */
	public void setShadesName(String shadesName) {
		this.shadesName = shadesName;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
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
