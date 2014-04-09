package com.salesmanager.core.business.accountmanagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.accountmanagement.dao.ReceiptDao;
import com.salesmanager.core.business.accountmanagement.model.Receipt;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.utils.CacheUtils;

@Service("receiptService")
public class ReceiptServiceImpl extends SalesManagerEntityServiceImpl<Long, Receipt> implements ReceiptService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptServiceImpl.class);

	private ReceiptDao receiptDao;

	@Autowired
	private CacheUtils cache;

	@Autowired
	public ReceiptServiceImpl(ReceiptDao receiptDao) {
		super(receiptDao);
		this.receiptDao = receiptDao;
	}

	@Override
	public Receipt getById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Receipt> getReceipt(MerchantStore store) throws ServiceException {
		return receiptDao.listReceipt(store);
	}

	@Override
	public void addReceipt(Receipt receipt) {
		System.out.println("==============public void receipt(Receipt receipt)===================>>" + receipt.toString());
		receiptDao.saveOrUpdate(receipt);

	}

}
