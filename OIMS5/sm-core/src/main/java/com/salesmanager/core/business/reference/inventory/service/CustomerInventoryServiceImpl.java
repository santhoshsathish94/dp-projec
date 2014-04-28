package com.salesmanager.core.business.reference.inventory.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.inventory.dao.CustomerInventoryDao;
import com.salesmanager.core.business.inventory.model.CustomerInventory;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.utils.CacheUtils;

@Service("customerInventoryService")
public class CustomerInventoryServiceImpl extends SalesManagerEntityServiceImpl<Long, CustomerInventory> implements CustomerInventoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerInventoryServiceImpl.class);

	private CustomerInventoryDao customerInventoryDao;

	@Autowired
	private CacheUtils cache;

	@Autowired
	public CustomerInventoryServiceImpl(CustomerInventoryDao customerInventoryDao) {
		super(customerInventoryDao);
		this.customerInventoryDao = customerInventoryDao;
	}

	@Override
	public List<CustomerInventory> getCustomerInventory(MerchantStore store) throws ServiceException {
		return customerInventoryDao.listCustomerInventory(store);
	}

	@Override
	public void addCustomerInventory(CustomerInventory customerInventory) {
		System.out.println("==============public void customerInventory(CustomerInventory customerInventory)===================>>" + customerInventory.toString());
		customerInventoryDao.saveOrUpdate(customerInventory);

	}

	@Override
	public List<CustomerInventory> getCustomerInventoryByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return customerInventoryDao.getCustomerInventoryByCustomerId(customerId);
	}

	@Override
	public List<CustomerInventory> getCustomerInventoryByInventoryId(Long inventoryId) {
		// TODO Auto-generated method stub
		return customerInventoryDao.getCustomerInventoryByInventoryId(inventoryId);
	}

}
