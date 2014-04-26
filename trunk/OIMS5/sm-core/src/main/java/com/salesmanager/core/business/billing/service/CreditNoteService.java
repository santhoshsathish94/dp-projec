package com.salesmanager.core.business.billing.service;

import java.util.List;

import com.salesmanager.core.business.billing.model.CreditNote;
import com.salesmanager.core.business.billing.model.CreditNoteProduct;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;

public interface CreditNoteService  extends SalesManagerEntityService<Long, CreditNote> {

	CreditNote getCreditNoteById(Long id) throws ServiceException;
	
	List<CreditNote> creditNoteList() throws ServiceException;
	
	void SaveOrUpdate(CreditNote creditNote) throws ServiceException;
	
	CreditNoteProduct getCreditNoteProductById(Long id) throws ServiceException;
	
	List<CreditNoteProduct> creditNoteProductList() throws ServiceException;
	
	List<CreditNoteProduct> creditNoteProductByCreditNoteId(Long Id) throws ServiceException;
	
	void SaveOrUpdate(CreditNoteProduct creditNoteProduct) throws ServiceException;
	
	void getCreditNoteProductFromJSON(String creditNoteProductJson, CreditNote creditNote) throws ServiceException;
}
