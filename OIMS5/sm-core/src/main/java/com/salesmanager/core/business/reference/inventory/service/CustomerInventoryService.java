package com.salesmanager.core.business.reference.inventory.service;

import java.util.List;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.inventory.model.CustomerInventory;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface CustomerInventoryService extends SalesManagerEntityService<Long, CustomerInventory> {

	public List<CustomerInventory> getCustomerInventoryByCustomerId(Long customerId);

	public List<CustomerInventory> getCustomerInventoryByInventoryId(Long inventoryId);

	public List<CustomerInventory> getCustomerInventory(MerchantStore store) throws ServiceException;

	public void addCustomerInventory(CustomerInventory customerInventory);

}
