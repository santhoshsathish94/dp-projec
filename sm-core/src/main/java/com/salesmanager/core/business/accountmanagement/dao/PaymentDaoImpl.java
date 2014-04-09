package com.salesmanager.core.business.accountmanagement.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.accountmanagement.model.Payment;
import com.salesmanager.core.business.accountmanagement.model.QPayment;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.merchant.model.MerchantStore;

@Repository("paymentDao")
public class PaymentDaoImpl extends SalesManagerEntityDaoImpl<Long, Payment> implements PaymentDao {

	@PersistenceContext
	private EntityManager entityManager;

	public PaymentDaoImpl() {
		super();
	}

	@Override
	public Payment getPayment(Long purchaseid) {
		QPayment qPayment = QPayment.payment;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qPayment).where(qPayment.id.eq(purchaseid));

		return query.uniqueResult(qPayment);
	}

	@Override
	public List<Payment> listPayment(MerchantStore store) {
		QPayment qPayment = QPayment.payment;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qPayment).leftJoin(qPayment.merchantStore).fetch().where((qPayment.merchantStore.id.eq(store.getId())));

		return query.list(qPayment);
	}

	@Override
	public void saveOrUpdate(Payment payment) {
		if (payment.getId() == null) {
			super.save(payment);
		} else {
			super.update(payment);
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
