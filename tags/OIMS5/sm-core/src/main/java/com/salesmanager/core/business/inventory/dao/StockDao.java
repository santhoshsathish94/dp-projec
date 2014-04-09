package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import com.salesmanager.core.business.company.model.AccountingPeriod;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.company.model.CompanyCurrencies;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface StockDao extends SalesManagerEntityDao<Long, Stock> {

	public Stock getStock(Long stockId);

	List<Stock> listStock(MerchantStore store);

	void saveOrUpdate(Stock stock);

}
