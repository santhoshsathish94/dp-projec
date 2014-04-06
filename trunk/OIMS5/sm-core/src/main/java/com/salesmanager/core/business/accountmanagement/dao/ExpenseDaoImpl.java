package com.salesmanager.core.business.accountmanagement.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.accountmanagement.model.Expense;
import com.salesmanager.core.business.accountmanagement.model.QExpense;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.merchant.model.MerchantStore;

@Repository("expenseDao")
public class ExpenseDaoImpl extends SalesManagerEntityDaoImpl<Long, Expense> implements ExpenseDao {

	@PersistenceContext
	private EntityManager entityManager;

	public ExpenseDaoImpl() {
		super();
	}

	@Override
	public Expense getExpense(Long expenseid) {
		QExpense qExpense = QExpense.expense1;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qExpense).where(qExpense.id.eq(expenseid));

		return query.uniqueResult(qExpense);
	}

	@Override
	public List<Expense> listExpense(MerchantStore store) {
		QExpense qExpense = QExpense.expense1;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qExpense).leftJoin(qExpense.merchantStore).fetch().where((qExpense.merchantStore.id.eq(store.getId())));

		return query.list(qExpense);
	}

	@Override
	public void saveOrUpdate(Expense expense) {
		if (expense.getId() == null) {
			super.save(expense);
		} else {
			super.update(expense);
		}
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager
	 *            the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
