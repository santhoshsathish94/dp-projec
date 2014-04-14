package com.salesmanager.core.business.billing.dao;

import java.util.List;

import com.salesmanager.core.business.billing.model.CreditNote;
import com.salesmanager.core.business.billing.model.CreditNoteProduct;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.generic.exception.ServiceException;

public interface CreditNoteDao extends SalesManagerEntityDao<Long, CreditNote> {

	CreditNote getCreditNoteById(Long id) throws ServiceException;
	
	List<CreditNote> creditNoteList() throws ServiceException;
	
	void SaveOrUpdate(CreditNote creditNote) throws ServiceException;
	
	CreditNoteProduct getCreditNoteProductById(Long id) throws ServiceException;
	
	List<CreditNoteProduct> creditNoteProductList() throws ServiceException;
	
	List<CreditNoteProduct> creditNoteProductByCreditNoteId(Long Id) throws ServiceException;
	
	void SaveOrUpdate(CreditNoteProduct creditNoteProduct) throws ServiceException;
}
