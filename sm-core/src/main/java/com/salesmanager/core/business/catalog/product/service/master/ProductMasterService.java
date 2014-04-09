package com.salesmanager.core.business.catalog.product.service.master;

import java.util.List;

import com.salesmanager.core.business.catalog.product.model.master.Shades;
import com.salesmanager.core.business.catalog.product.model.master.Variants;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;

public interface ProductMasterService extends SalesManagerEntityService<Long, Variants> {

	Variants getVariantsById(Long id);
	
	List<Variants> getVariantsList();
	
	List<String> getVariantNameList();
	
	Variants getVariantsByName(String variantName);
	
	void saveOrUpdate(Variants variants);
	
	Shades getShadesById(Long id);
	
	Shades getShadesByShortCode(String shortCode);
	
	List<Shades> getShadesList();
	
	List<String> getShadeNameList();
	
	void saveOrUpdate(Shades shades);
}
