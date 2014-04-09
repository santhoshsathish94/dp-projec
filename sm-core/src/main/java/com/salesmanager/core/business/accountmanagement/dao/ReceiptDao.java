package com.salesmanager.core.business.accountmanagement.dao;

import java.util.List;

import com.salesmanager.core.business.accountmanagement.model.Receipt;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface ReceiptDao extends SalesManagerEntityDao<Long, Receipt> {

	public Receipt getReceipt(Long transfer_id);

	List<Receipt> listReceipt(MerchantStore store);

	void saveOrUpdate(Receipt receipt);

}
