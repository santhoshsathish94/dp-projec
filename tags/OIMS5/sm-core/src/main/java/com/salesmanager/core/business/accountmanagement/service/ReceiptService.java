package com.salesmanager.core.business.accountmanagement.service;

import java.util.List;

import com.salesmanager.core.business.accountmanagement.model.Receipt;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface ReceiptService extends SalesManagerEntityService<Long, Receipt> {

	public Receipt getById(String id) throws ServiceException;

	public List<Receipt> getReceipt(MerchantStore store) throws ServiceException;

	public void addReceipt(Receipt receipt);

}
