package com.salesmanager.core.business.catalog.product.dao.master;

import java.util.List;

import com.salesmanager.core.business.catalog.product.model.master.Shades;
import com.salesmanager.core.business.catalog.product.model.master.Variants;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;

public interface ProductMasterDao extends SalesManagerEntityDao<Long, Variants> {

	Variants getVariantsById(Long id);
	
	Variants getVariantsByName(String variantName);
	
	List<Variants> getVariantsList();
	
	List<String> getVariantNameList();
	
	void saveOrUpdate(Variants variants);
	
	Shades getShadesById(Long id);
	
	Shades getShadesByShortCode(String shortCode);
	
	List<Shades> getShadesList();
	
	List<String> getShadeNameList();
	
	void saveOrUpdate(Shades shades);
}
