package com.salesmanager.core.business.billing.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.salesmanager.core.business.tax.model.taxclass.TaxClass;

public class CustomTaxClass {

	public Long id;
	
	public String name;
	
	public BigDecimal taxRate;
	
	public List<CustomTaxClass> getCustomTaxClassFromOriginal(List<TaxClass> taxClassList) {
		
		List<CustomTaxClass> customTaxClassList = new ArrayList<CustomTaxClass>();
		
		CustomTaxClass customtaxClass = null;
		
		for(TaxClass taxClass: taxClassList) {
			
			customtaxClass = new CustomTaxClass();
			
			customtaxClass.setId(taxClass.getId());
			customtaxClass.setName(taxClass.getCode());
			if(taxClass.getTaxRates() != null && taxClass.getTaxRates().size() > 0) {
				customtaxClass.setTaxRate(taxClass.getTaxRates().iterator().next().getTaxRate());
			} else {
				customtaxClass.setTaxRate(new BigDecimal(0.00));
			}
			
			customTaxClassList.add(customtaxClass);
		}
		
		return customTaxClassList;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the taxRate
	 */
	public BigDecimal getTaxRate() {
		return taxRate;
	}

	/**
	 * @param taxRate the taxRate to set
	 */
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
}

