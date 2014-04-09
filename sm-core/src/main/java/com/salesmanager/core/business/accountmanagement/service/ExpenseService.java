package com.salesmanager.core.business.accountmanagement.service;

import java.util.List;

import com.salesmanager.core.business.accountmanagement.model.Expense;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface ExpenseService extends SalesManagerEntityService<Long, Expense> {

	public Expense getById(String id) throws ServiceException;

	public List<Expense> getExpense(MerchantStore store) throws ServiceException;

	public void addExpense(Expense expense);

}
