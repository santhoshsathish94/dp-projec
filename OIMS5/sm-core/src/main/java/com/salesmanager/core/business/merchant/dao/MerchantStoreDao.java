package com.salesmanager.core.business.merchant.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.user.model.User;

public interface MerchantStoreDao extends SalesManagerEntityDao<Integer, MerchantStore> {
	
	public Collection<Product> getProducts(MerchantStore merchantStore) throws ServiceException;
	
	public MerchantStore getMerchantStore(Integer merchantStoreId);

	public MerchantStore getMerchantStore(String code) throws ServiceException;

	public List<MerchantStore> listStoreByIds(ArrayList<Integer> storeIds);
}
