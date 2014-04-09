package com.salesmanager.core.business.accountmanagement.service;

import java.util.List;

import com.salesmanager.core.business.accountmanagement.model.Journal;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface JournalService extends SalesManagerEntityService<Long, Journal> {

	public Journal getById(String id) throws ServiceException;

	public List<Journal> getJournal(MerchantStore store) throws ServiceException;

	public void addJournal(Journal journal);

}
