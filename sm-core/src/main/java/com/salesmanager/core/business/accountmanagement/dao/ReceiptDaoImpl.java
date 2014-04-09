package com.salesmanager.core.business.accountmanagement.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.accountmanagement.model.Receipt;
import com.salesmanager.core.business.accountmanagement.model.QReceipt;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.merchant.model.MerchantStore;

@Repository("receiptDao")
public class ReceiptDaoImpl extends SalesManagerEntityDaoImpl<Long, Receipt> implements ReceiptDao {

	@PersistenceContext
	private EntityManager entityManager;

	public ReceiptDaoImpl() {
		super();
	}

	@Override
	public Receipt getReceipt(Long purchaseid) {
		QReceipt qReceipt = QReceipt.receipt;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qReceipt).where(qReceipt.id.eq(purchaseid));

		return query.uniqueResult(qReceipt);
	}

	@Override
	public List<Receipt> listReceipt(MerchantStore store) {
		QReceipt qReceipt = QReceipt.receipt;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qReceipt).leftJoin(qReceipt.merchantStore).fetch().where((qReceipt.merchantStore.id.eq(store.getId())));

		return query.list(qReceipt);
	}

	@Override
	public void saveOrUpdate(Receipt receipt) {
		if (receipt.getId() == null) {
			super.save(receipt);
		} else {
			super.update(receipt);
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
