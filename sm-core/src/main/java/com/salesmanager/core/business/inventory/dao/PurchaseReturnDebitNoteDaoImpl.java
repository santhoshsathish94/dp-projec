package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.inventory.model.PurchaseReturnDebitNote;
import com.salesmanager.core.business.inventory.model.QPurchase;
import com.salesmanager.core.business.inventory.model.QPurchaseReturnDebitNote;
import com.salesmanager.core.business.inventory.model.QStock;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;

@Repository("purchaseReturnDebitNoteDaoImpl")
public class PurchaseReturnDebitNoteDaoImpl extends SalesManagerEntityDaoImpl<Long, PurchaseReturnDebitNote> implements PurchaseReturnDebitNoteDao {

	@PersistenceContext
	private EntityManager entityManager;

	public PurchaseReturnDebitNoteDaoImpl() {
		super();
	}

	@Override
	public PurchaseReturnDebitNote getPurchaseReturnDebitNote(Long debitId) {
		// TODO Auto-generated method stub
		QPurchaseReturnDebitNote qPurchaseReturnDebitNote = QPurchaseReturnDebitNote.purchaseReturnDebitNote;

		JPQLQuery query = new JPAQuery(getEntityManager());

		query.from(qPurchaseReturnDebitNote).where(qPurchaseReturnDebitNote.id.eq(debitId));

		return query.uniqueResult(qPurchaseReturnDebitNote);
	}

	@Override
	public List<PurchaseReturnDebitNote> listPurchaseReturnDebitNote(MerchantStore store) {
		QPurchaseReturnDebitNote qPurchaseReturnDebitNote = QPurchaseReturnDebitNote.purchaseReturnDebitNote;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qPurchaseReturnDebitNote).leftJoin(qPurchaseReturnDebitNote.merchantStore).fetch().where((qPurchaseReturnDebitNote.merchantStore.id.eq(store.getId())));

		return query.list(qPurchaseReturnDebitNote);
	}

	@Override
	public void saveOrUpdate(PurchaseReturnDebitNote purchaseReturnDebitNote) {
		if (purchaseReturnDebitNote.getId() == null) {
			super.save(purchaseReturnDebitNote);
		} else {
			super.update(purchaseReturnDebitNote);
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
