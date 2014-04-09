package com.salesmanager.core.business.catalog.product.service.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.catalog.product.dao.master.ProductMasterDao;
import com.salesmanager.core.business.catalog.product.model.master.Shades;
import com.salesmanager.core.business.catalog.product.model.master.Variants;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;

@Service("ProductMasterService")
public class ProductMasterServiceImpl extends SalesManagerEntityServiceImpl<Long, Variants> implements ProductMasterService {

	
	private ProductMasterDao productMasterDao;
	
	@Autowired
	public ProductMasterServiceImpl(ProductMasterDao productMasterDao) {
		super(productMasterDao);
		this.productMasterDao = productMasterDao;
	}

	@Override
	public Variants getVariantsById(Long id) {
		return productMasterDao.getVariantsById(id);
	}

	@Override
	public List<Variants> getVariantsList() {
		return productMasterDao.getVariantsList();
	}

	@Override
	public Variants getVariantsByName(String variantName) {
		return productMasterDao.getVariantsByName(variantName);
	}

	@Override
	public void saveOrUpdate(Variants variants) {
		productMasterDao.saveOrUpdate(variants);
	}

	@Override
	public Shades getShadesById(Long id) {
		return productMasterDao.getShadesById(id);
	}

	@Override
	public Shades getShadesByShortCode(String shortCode) {
		return productMasterDao.getShadesByShortCode(shortCode);
	}

	@Override
	public List<Shades> getShadesList() {
		return productMasterDao.getShadesList();
	}

	@Override
	public void saveOrUpdate(Shades shades) {
		productMasterDao.saveOrUpdate(shades);
	}

	@Override
	public List<String> getVariantNameList() {
		return productMasterDao.getVariantNameList();
	}

	@Override
	public List<String> getShadeNameList() {
		return productMasterDao.getShadeNameList();
	}
	

}
