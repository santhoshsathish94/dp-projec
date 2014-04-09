package com.salesmanager.core.business.reference.inventory.service;

import java.util.List;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface PurchaseService extends SalesManagerEntityService<Long, Purchase> {

	public Purchase getById(String id) throws ServiceException;

	public List<Purchase> getPurchase(MerchantStore store) throws ServiceException;

	public void addPurchase(Purchase purchase);

}
