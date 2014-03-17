package com.salesmanager.core.business.supplier.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.supplier.dao.SupplierDao;
import com.salesmanager.core.business.supplier.model.Supplier;

@Service("SupplierService")
public class SupplierServiceImpl extends SalesManagerEntityServiceImpl<Integer, Supplier> implements SupplierService {

	private SupplierDao supplierDao;
	
	@Autowired
	public SupplierServiceImpl(SupplierDao supplierDao) {
		super(supplierDao);
		this.supplierDao = supplierDao;
	}

	@Override
	public Supplier getSupplier(Long supplierId) throws ServiceException {
		return supplierDao.getSupplier(Integer.parseInt(Long.toString(supplierId)));
	}

	@Override
	public List<Supplier> getSupplierList() throws ServiceException {
		return supplierDao.getSupplierList();
	}

	@Override
	public void saveOrUpdate(Supplier supplier) throws ServiceException {
		supplierDao.saveOrUpdate(supplier);
	}

}
