package com.salesmanager.core.business.reference.inventory.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.inventory.dao.PurchaseReturnDebitNoteDao;
import com.salesmanager.core.business.inventory.model.PurchaseReturnDebitNote;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.utils.CacheUtils;

@Service("purchaseReturnDebitNoteService")
public class PurchaseReturnDebitNoteServiceImpl extends SalesManagerEntityServiceImpl<Long, PurchaseReturnDebitNote> implements PurchaseReturnDebitNoteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseReturnDebitNoteServiceImpl.class);

	private PurchaseReturnDebitNoteDao purchaseReturnDebitNoteDao;

	@Autowired
	private CacheUtils cache;

	@Autowired
	public PurchaseReturnDebitNoteServiceImpl(PurchaseReturnDebitNoteDao purchaseReturnDebitNoteDao) {
		super(purchaseReturnDebitNoteDao);
		this.purchaseReturnDebitNoteDao = purchaseReturnDebitNoteDao;
	}

	@Override
	public PurchaseReturnDebitNote getById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseReturnDebitNote> getPurchaseReturnDebitNote(MerchantStore store) throws ServiceException {
		return purchaseReturnDebitNoteDao.listPurchaseReturnDebitNote(store);
	}

	@Override
	public void addPurchaseReturnDebitNote(PurchaseReturnDebitNote purchaseReturnDebitNote) {
		System.out.println("==============public void purchaseReturnDebitNote(PurchaseReturnDebitNote purchaseReturnDebitNote)===================>>" + purchaseReturnDebitNote.toString());
		purchaseReturnDebitNoteDao.saveOrUpdate(purchaseReturnDebitNote);

	}

}
