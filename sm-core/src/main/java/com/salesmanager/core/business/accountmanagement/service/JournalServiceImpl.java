package com.salesmanager.core.business.accountmanagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.accountmanagement.dao.JournalDao;
import com.salesmanager.core.business.accountmanagement.model.Journal;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.utils.CacheUtils;

@Service("journalService")
public class JournalServiceImpl extends SalesManagerEntityServiceImpl<Long, Journal> implements JournalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JournalServiceImpl.class);

	private JournalDao journalDao;

	@Autowired
	private CacheUtils cache;

	@Autowired
	public JournalServiceImpl(JournalDao journalDao) {
		super(journalDao);
		this.journalDao = journalDao;
	}

	@Override
	public Journal getById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Journal> getJournal(MerchantStore store) throws ServiceException {
		return journalDao.listJournal(store);
	}

	@Override
	public void addJournal(Journal journal) {
		System.out.println("==============public void journal(Journal journal)===================>>" + journal.toString());
		journalDao.saveOrUpdate(journal);

	}

}
