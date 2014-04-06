package com.salesmanager.core.business.accountmanagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.accountmanagement.dao.PaymentDao;
import com.salesmanager.core.business.accountmanagement.model.Payment;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.utils.CacheUtils;

@Service("accountmanagement_paymentService")
public class PaymentServiceImpl extends SalesManagerEntityServiceImpl<Long, Payment> implements PaymentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

	private PaymentDao paymentDao;

	@Autowired
	private CacheUtils cache;

	@Autowired
	public PaymentServiceImpl(PaymentDao paymentDao) {
		super(paymentDao);
		this.paymentDao = paymentDao;
	}

	@Override
	public Payment getById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getPayment(MerchantStore store) throws ServiceException {
		return paymentDao.listPayment(store);
	}

	@Override
	public void addPayment(Payment payment) {
		System.out.println("==============public void payment(Payment payment)===================>>" + payment.toString());
		paymentDao.saveOrUpdate(payment);

	}

}
