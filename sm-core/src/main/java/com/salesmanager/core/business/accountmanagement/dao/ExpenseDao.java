package com.salesmanager.core.business.accountmanagement.dao;

import java.util.List;

import com.salesmanager.core.business.accountmanagement.model.Expense;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface ExpenseDao extends SalesManagerEntityDao<Long, Expense> {

	public Expense getExpense(Long transfer_id);

	List<Expense> listExpense(MerchantStore store);

	void saveOrUpdate(Expense expense);

}
