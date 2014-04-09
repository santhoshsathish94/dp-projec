package com.salesmanager.core.business.billing.dao;

import java.util.List;

import com.salesmanager.core.business.billing.model.SalesInvoice;
import com.salesmanager.core.business.billing.model.SalesInvoiceProduct;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;

public interface SalesInvoiceDao extends SalesManagerEntityDao<Long, SalesInvoice> {

	SalesInvoice getSalesInvoiceById(Long id);
	
	List<SalesInvoice> salesInvoiceList();
	
	void SaveOrUpdate(SalesInvoice salesInvoice);
	
	SalesInvoiceProduct getSalesInvoiceProductById(Long id);
	
	List<SalesInvoiceProduct> salesInvoiceProductList();
	
	List<SalesInvoiceProduct> salesInvoiceProductListBySalesInvoiceId(Long Id);
	
	void SaveOrUpdate(SalesInvoiceProduct salesInvoiceProduct);
}
