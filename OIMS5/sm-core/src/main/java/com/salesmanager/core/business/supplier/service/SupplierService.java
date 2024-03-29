package com.salesmanager.core.business.supplier.service;

import java.util.List;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.supplier.model.PartyItemDefaultMargin;
import com.salesmanager.core.business.supplier.model.Supplier;

public interface SupplierService extends SalesManagerEntityService<Integer, Supplier> {

	Supplier getSupplier(Long supplierId) throws ServiceException;
	
	List<Supplier> getSupplierList() throws ServiceException;
	
	void saveOrUpdate(Supplier supplier) throws ServiceException;
	
	
	PartyItemDefaultMargin getPartyItemDefaultMargin(Long id) throws ServiceException;
	
	List<PartyItemDefaultMargin> getPartyItemDefaultMarginList() throws ServiceException;
	
	void saveOrUpdate(PartyItemDefaultMargin partyItemDefaultMargin) throws ServiceException;
	
}
