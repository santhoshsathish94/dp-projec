package com.salesmanager.core.business.accountmanagement.service;

import java.util.List;

import com.salesmanager.core.business.accountmanagement.model.Payment;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface PaymentService extends SalesManagerEntityService<Long, Payment> {

	public Payment getById(String id) throws ServiceException;

	public List<Payment> getPayment(MerchantStore store) throws ServiceException;

	public void addPayment(Payment payment);

}
