package com.salesmanager.core.business.accountmanagement.dao;

import java.util.List;

import com.salesmanager.core.business.accountmanagement.model.Payment;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface PaymentDao extends SalesManagerEntityDao<Long, Payment> {

	public Payment getPayment(Long transfer_id);

	List<Payment> listPayment(MerchantStore store);

	void saveOrUpdate(Payment payment);

}
