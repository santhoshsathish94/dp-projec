package com.salesmanager.core.business.reference.inventory.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.inventory.dao.DebitNoteOtherDao;
import com.salesmanager.core.business.inventory.model.DebitNoteOther;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.utils.CacheUtils;

@Service("debitNoteOtherService")
public class DebitNoteOtherServiceImpl extends SalesManagerEntityServiceImpl<Long, DebitNoteOther> implements DebitNoteOtherService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DebitNoteOtherServiceImpl.class);

	private DebitNoteOtherDao debitNoteOtherDao;

	@Autowired
	private CacheUtils cache;

	@Autowired
	public DebitNoteOtherServiceImpl(DebitNoteOtherDao debitNoteOtherDao) {
		super(debitNoteOtherDao);
		this.debitNoteOtherDao = debitNoteOtherDao;
	}

	@Override
	public DebitNoteOther getById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DebitNoteOther> getDebitNoteOther(MerchantStore store) throws ServiceException {
		return debitNoteOtherDao.listDebitNoteOther(store);
	}

	@Override
	public void addDebitNoteOther(DebitNoteOther debitNoteOther) {
		System.out.println("==============public void addDebitNoteOther(DebitNoteOther debitNoteOther)===================>>" + debitNoteOther.toString());
		debitNoteOtherDao.saveOrUpdate(debitNoteOther);

	}

}
