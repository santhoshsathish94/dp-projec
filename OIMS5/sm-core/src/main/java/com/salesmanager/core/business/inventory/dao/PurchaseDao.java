package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface PurchaseDao extends SalesManagerEntityDao<Long, Purchase> {

	public Purchase getPurchase(Long purchaseid);

	List<Purchase> listPurchase(MerchantStore store);

	void saveOrUpdate(Purchase purchase);

}
