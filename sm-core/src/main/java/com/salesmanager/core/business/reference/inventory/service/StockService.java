package com.salesmanager.core.business.reference.inventory.service;

import java.util.List;
import java.util.Map;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.country.model.CountryDescription;
import com.salesmanager.core.business.reference.language.model.Language;

public interface StockService extends SalesManagerEntityService<Long, Stock> {

	public Stock getById(String id) throws ServiceException;

	public List<Stock> getStocks(MerchantStore store) throws ServiceException;

	public void addStosk(Stock stock);

}
