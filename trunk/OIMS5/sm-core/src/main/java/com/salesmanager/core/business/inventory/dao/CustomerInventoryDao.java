package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.inventory.model.CustomerInventory;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface CustomerInventoryDao extends SalesManagerEntityDao<Long, CustomerInventory> {

	public List<CustomerInventory> getCustomerInventoryByCustomerId(Long customerId);

	public List<CustomerInventory> getCustomerInventoryByInventoryId(Long inventoryId);

	List<CustomerInventory> listCustomerInventory(MerchantStore store);

	void saveOrUpdate(CustomerInventory customerInventory);

}
