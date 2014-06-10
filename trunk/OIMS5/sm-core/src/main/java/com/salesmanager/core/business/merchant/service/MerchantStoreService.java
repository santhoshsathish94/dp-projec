package com.salesmanager.core.business.merchant.service;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.user.model.User;

public interface MerchantStoreService extends SalesManagerEntityService<Integer, MerchantStore>{
	

	//Collection<Product> getProducts(MerchantStore merchantStore) throws ServiceException;
	
	//MerchantStore getMerchantStore(Integer merchantStoreId) throws ServiceException;

	MerchantStore getMerchantStore(String merchantStoreCode)
			throws ServiceException;
	
	MerchantStore getByCode(String code) throws ServiceException ;

	void saveOrUpdate(MerchantStore store) throws ServiceException;
	
	List<MerchantStore> listStoreByIds(ArrayList<Integer> storeIds) throws ServiceException;
}
