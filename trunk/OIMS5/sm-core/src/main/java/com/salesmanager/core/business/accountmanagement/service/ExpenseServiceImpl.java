package com.salesmanager.core.business.accountmanagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.accountmanagement.dao.ExpenseDao;
import com.salesmanager.core.business.accountmanagement.model.Expense;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.utils.CacheUtils;

@Service("expenseService")
public class ExpenseServiceImpl extends SalesManagerEntityServiceImpl<Long, Expense> implements ExpenseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseServiceImpl.class);

	private ExpenseDao expenseDao;

	@Autowired
	private CacheUtils cache;

	@Autowired
	public ExpenseServiceImpl(ExpenseDao expenseDao) {
		super(expenseDao);
		this.expenseDao = expenseDao;
	}

	@Override
	public Expense getById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Expense> getExpense(MerchantStore store) throws ServiceException {
		return expenseDao.listExpense(store);
	}

	@Override
	public void addExpense(Expense expense) {
		System.out.println("==============public void expense(Expense expense)===================>>" + expense.toString());
		expenseDao.saveOrUpdate(expense);

	}

}
