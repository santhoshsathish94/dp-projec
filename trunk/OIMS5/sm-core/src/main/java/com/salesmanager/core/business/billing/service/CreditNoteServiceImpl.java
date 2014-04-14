package com.salesmanager.core.business.billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.billing.dao.CreditNoteDao;
import com.salesmanager.core.business.billing.model.CreditNote;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.billing.model.CreditNoteProduct;
import com.salesmanager.core.business.generic.exception.ServiceException;

@Service("CreditNoteService")
public class CreditNoteServiceImpl extends SalesManagerEntityServiceImpl<Long, CreditNote> implements CreditNoteService {

	private CreditNoteDao creditNoteDao;
	
	@Autowired
	public CreditNoteServiceImpl(CreditNoteDao creditNoteDao) {
		super(creditNoteDao);
		this.creditNoteDao = creditNoteDao;
	}

	@Override
	public CreditNote getCreditNoteById(Long id) throws ServiceException {
		return creditNoteDao.getCreditNoteById(id);
	}

	@Override
	public List<CreditNote> creditNoteList() throws ServiceException {
		return creditNoteDao.creditNoteList();
	}

	@Override
	public void SaveOrUpdate(CreditNote creditNote) throws ServiceException {
		creditNoteDao.SaveOrUpdate(creditNote);
	}

	@Override
	public CreditNoteProduct getCreditNoteProductById(Long id) throws ServiceException {
		return creditNoteDao.getCreditNoteProductById(id);
	}

	@Override
	public List<CreditNoteProduct> creditNoteProductList() throws ServiceException {
		return creditNoteDao.creditNoteProductList();
	}

	@Override
	public List<CreditNoteProduct> creditNoteProductByCreditNoteId(Long Id) throws ServiceException {
		return creditNoteDao.creditNoteProductByCreditNoteId(Id);
	}

	@Override
	public void SaveOrUpdate(CreditNoteProduct creditNoteProduct) throws ServiceException {
		creditNoteDao.SaveOrUpdate(creditNoteProduct);
	}
}
