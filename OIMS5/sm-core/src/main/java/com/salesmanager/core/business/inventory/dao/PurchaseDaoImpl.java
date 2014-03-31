package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.inventory.model.QPurchase;
import com.salesmanager.core.business.inventory.model.QStock;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;

@Repository("purchaseDao")
public class PurchaseDaoImpl extends SalesManagerEntityDaoImpl<Long, Purchase> implements PurchaseDao {

	@PersistenceContext
	private EntityManager entityManager;

	public PurchaseDaoImpl() {
		super();
	}

	@Override
	public Purchase getPurchase(Long purchaseid) {
		QPurchase qPurchase = QPurchase.purchase;

		JPQLQuery query = new JPAQuery(getEntityManager());

		query.from(qPurchase).where(qPurchase.id.eq(purchaseid));

		return query.uniqueResult(qPurchase);
	}

	@Override
	public List<Purchase> listPurchase(MerchantStore store) {
		QPurchase qPurchase = QPurchase.purchase;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qPurchase).leftJoin(qPurchase.merchantStore).fetch().where((qPurchase.merchantStore.id.eq(store.getId())));

		return query.list(qPurchase);
	}

	@Override
	public void saveOrUpdate(Purchase purchase) {
		if (purchase.getId() == null) {
			super.save(purchase);
		} else {
			super.update(purchase);
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
