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
import com.salesmanager.core.business.inventory.dao.StockDao;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.country.dao.CountryDao;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.country.model.CountryDescription;
import com.salesmanager.core.business.reference.country.model.Country_;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.utils.CacheUtils;

@Service("stockService")
public class StockServiceImpl extends SalesManagerEntityServiceImpl<Long, Stock> implements StockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

	private StockDao stockDao;

	@Autowired
	private CacheUtils cache;

	@Autowired
	public StockServiceImpl(StockDao stockDao) {
		super(stockDao);
		this.stockDao = stockDao;
	}

	@Override
	public Stock getById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> getStocks(MerchantStore store) throws ServiceException {
		return stockDao.listStock(store);
	}

	@Override
	public void addStosk(Stock stock) {
		System.out.println("==============public void addStosk(Stock stock)===================>>" + stock.toString());
		stockDao.saveOrUpdate(stock);

	}

}
