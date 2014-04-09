package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.inventory.model.QStock;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;

@Repository("stockDao")
public class StockDaoImpl extends SalesManagerEntityDaoImpl<Long, Stock> implements StockDao {

	@PersistenceContext
	private EntityManager entityManager;

	public StockDaoImpl() {
		super();
	}

	@Override
	public Stock getStock(Long stockId) {
		QStock qStock = QStock.stock;

		JPQLQuery query = new JPAQuery(getEntityManager());

		query.from(qStock).where(qStock.id.eq(stockId));

		return query.uniqueResult(qStock);
	}

	@Override
	public List<Stock> listStock(MerchantStore store) {
		QStock qStock = QStock.stock;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qStock).leftJoin(qStock.merchantStore).fetch().where((qStock.merchantStore.id.eq(store.getId())));

		return query.list(qStock);
	}

	@Override
	public void saveOrUpdate(Stock stock) {
		if (stock.getId() == null) {
			super.save(stock);
		} else {
			super.update(stock);
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
