package com.salesmanager.core.business.accountmanagement.dao;

import java.util.List;

import com.salesmanager.core.business.accountmanagement.model.Journal;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface JournalDao extends SalesManagerEntityDao<Long, Journal> {

	public Journal getJournal(Long transfer_id);

	List<Journal> listJournal(MerchantStore store);

	void saveOrUpdate(Journal journal);

}
