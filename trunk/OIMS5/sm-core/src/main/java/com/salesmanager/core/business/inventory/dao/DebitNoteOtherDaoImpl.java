package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.inventory.model.BranchTransfer;
import com.salesmanager.core.business.inventory.model.DebitNoteOther;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.inventory.model.QDebitNoteOther;
import com.salesmanager.core.business.inventory.model.QPurchase;
import com.salesmanager.core.business.inventory.model.QStock;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;

@Repository("debitNoteOtherDao")
public class DebitNoteOtherDaoImpl extends SalesManagerEntityDaoImpl<Long, DebitNoteOther> implements DebitNoteOtherDao {

	@PersistenceContext
	private EntityManager entityManager;

	public DebitNoteOtherDaoImpl() {
		super();
	}

	@Override
	public DebitNoteOther getDebitNoteOther(Long debitId) {

		QDebitNoteOther qDebitNoteOther = QDebitNoteOther.debitNoteOther;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qDebitNoteOther).where(qDebitNoteOther.id.eq(debitId));
		return query.uniqueResult(qDebitNoteOther);
	}

	@Override
	public List<DebitNoteOther> listDebitNoteOther(MerchantStore store) {

		QDebitNoteOther qDebitNoteOther = QDebitNoteOther.debitNoteOther;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qDebitNoteOther).leftJoin(qDebitNoteOther.merchantStore).fetch().where((qDebitNoteOther.merchantStore.id.eq(store.getId())));
		return query.list(qDebitNoteOther);
	}

	@Override
	public void saveOrUpdate(DebitNoteOther debitNoteOther) {

		if (debitNoteOther.getId() == null) {
			super.save(debitNoteOther);
		} else {
			super.update(debitNoteOther);
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
