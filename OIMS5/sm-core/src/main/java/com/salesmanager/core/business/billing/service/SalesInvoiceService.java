package com.salesmanager.core.business.billing.service;

import java.util.List;

import com.salesmanager.core.business.billing.model.CreditNoteProductEntity;
import com.salesmanager.core.business.billing.model.SalesInvoice;
import com.salesmanager.core.business.billing.model.SalesInvoiceProduct;
import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;

public interface SalesInvoiceService extends SalesManagerEntityService<Long, SalesInvoice> {

	SalesInvoice getSalesInvoiceById(Long id) throws ServiceException;
	
	List<SalesInvoice> salesInvoiceList() throws ServiceException;
	
	void SaveOrUpdate(SalesInvoice salesInvoice) throws ServiceException;
	
	SalesInvoiceProduct getSalesInvoiceProductById(Long id) throws ServiceException;
	
	List<SalesInvoiceProduct> salesInvoiceProductList() throws ServiceException;
	
	List<SalesInvoiceProduct> salesInvoiceProductListBySalesInvoiceId(Long Id) throws ServiceException;
	
	void SaveOrUpdate(SalesInvoiceProduct salesInvoiceProduct) throws ServiceException;
	
	public List<CreditNoteProductEntity> getcustomProdListForCreditNote(List<Product> productList, String fieldValue) throws ServiceException;
	
	public void saveSalesInvoiceProductInfo(String productInfoJson, SalesInvoice invoice) throws ServiceException;
}
