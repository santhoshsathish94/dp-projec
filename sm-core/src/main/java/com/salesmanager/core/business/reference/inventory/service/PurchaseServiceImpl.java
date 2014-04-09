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
import com.salesmanager.core.business.inventory.dao.PurchaseDao;
import com.salesmanager.core.business.inventory.dao.StockDao;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.country.dao.CountryDao;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.country.model.CountryDescription;
import com.salesmanager.core.business.reference.country.model.Country_;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.utils.CacheUtils;

@Service("purchaseService")
public class PurchaseServiceImpl extends SalesManagerEntityServiceImpl<Long, Purchase> implements PurchaseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImpl.class);

	private PurchaseDao purchaseDao;

	@Autowired
	private CacheUtils cache;

	@Autowired
	public PurchaseServiceImpl(PurchaseDao purchaseDao) {
		super(purchaseDao);
		this.purchaseDao = purchaseDao;
	}

	@Override
	public Purchase getById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Purchase> getPurchase(MerchantStore store) throws ServiceException {
		return purchaseDao.listPurchase(store);
	}

	@Override
	public void addPurchase(Purchase purchase) {
		System.out.println("==============public void purchase(Purchase purchase)===================>>" + purchase.toString());
		purchaseDao.saveOrUpdate(purchase);

	}

}
