package com.salesmanager.core.business.catalog.product.model.master;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;

@Entity
@Table(name="VARIANT_MASTER")
public class Variants extends SalesManagerEntity<Long, Variants> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 856643231251250016L;
	
	@Id
	@Column(name="VARIANT_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "VARIANT", length=100, nullable=false)
	private String variantName;
	
	@Column(name = "VARIANT_TYPE", length=100, nullable=false)
	private String variantType;
	
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
	 * @return the variantName
	 */
	public String getVariantName() {
		return variantName;
	}

	/**
	 * @param variantName the variantName to set
	 */
	public void setVariantName(String variantName) {
		this.variantName = variantName;
	}

	/**
	 * @return the variantType
	 */
	public String getVariantType() {
		return variantType;
	}

	/**
	 * @param variantType the variantType to set
	 */
	public void setVariantType(String variantType) {
		this.variantType = variantType;
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
	
	
	public class VariantType {
		
		private String typeCode;
		
		private String typeValue;
		
		private List<VariantType> typeList = new ArrayList<VariantType>();
		
		public VariantType() {}
		
		private VariantType(String typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		
		/**
		 * @return the typeCode
		 */
		public String getTypeCode() {
			return typeCode;
		}


		/**
		 * @return the typeValue
		 */
		public String getTypeValue() {
			return typeValue;
		}


		/**
		 * @return the typeList
		 */
		public  List<VariantType> getTypeList() {
			
			if(typeList.size() <= 2) {
				typeList.add(new VariantType("REGULAR", "REGULAR"));
				typeList.add(new VariantType("IRREGULAR", "IRREGULAR"));
			}
			
			return typeList;
		}


		
	}

}
