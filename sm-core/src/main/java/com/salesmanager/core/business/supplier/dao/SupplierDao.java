package com.salesmanager.core.business.supplier.dao;

import java.util.List;

import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.supplier.model.PartyItemDefaultMargin;
import com.salesmanager.core.business.supplier.model.Supplier;

public interface SupplierDao extends SalesManagerEntityDao<Integer, Supplier> {

	Supplier getSupplier(Integer id);
	
	List<Supplier> getSupplierList();
	
	void saveOrUpdate(Supplier supplier);
	
	PartyItemDefaultMargin getPartyItemDefaultMargin(Long id);
	
	List<PartyItemDefaultMargin> getPartyItemDefaultMarginList();
	
	void saveOrUpdate(PartyItemDefaultMargin partyItemDefaultMargin);
	
}
