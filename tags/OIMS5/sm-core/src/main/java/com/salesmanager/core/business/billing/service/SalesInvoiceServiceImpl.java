package com.salesmanager.core.business.billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.billing.dao.SalesInvoiceDao;
import com.salesmanager.core.business.billing.model.SalesInvoice;
import com.salesmanager.core.business.billing.model.SalesInvoiceProduct;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;

@Service("SalesInvoiceService")
public class SalesInvoiceServiceImpl extends SalesManagerEntityServiceImpl<Long, SalesInvoice> implements SalesInvoiceService {

	private SalesInvoiceDao salesInvoiceDao;
	
	@Autowired
	public SalesInvoiceServiceImpl(SalesInvoiceDao salesInvoiceDao) {
		super(salesInvoiceDao);
		this.salesInvoiceDao = salesInvoiceDao;
	}

	@Override
	public SalesInvoice getSalesInvoiceById(Long id) throws ServiceException {
		return salesInvoiceDao.getSalesInvoiceById(id);
	}

	@Override
	public List<SalesInvoice> salesInvoiceList() throws ServiceException {
		return salesInvoiceDao.salesInvoiceList();
	}

	@Override
	public void SaveOrUpdate(SalesInvoice salesInvoice) throws ServiceException {
		
		salesInvoiceDao.SaveOrUpdate(salesInvoice);
	}

	@Override
	public SalesInvoiceProduct getSalesInvoiceProductById(Long id) throws ServiceException {
		return salesInvoiceDao.getSalesInvoiceProductById(id);
	}

	@Override
	public List<SalesInvoiceProduct> salesInvoiceProductList() throws ServiceException {
		return salesInvoiceDao.salesInvoiceProductList();
	}

	@Override
	public List<SalesInvoiceProduct> salesInvoiceProductListBySalesInvoiceId(Long Id) throws ServiceException {
		return salesInvoiceDao.salesInvoiceProductListBySalesInvoiceId(Id);
	}

	@Override
	public void SaveOrUpdate(SalesInvoiceProduct salesInvoiceProduct) throws ServiceException {
		salesInvoiceDao.SaveOrUpdate(salesInvoiceProduct);
	}

}
