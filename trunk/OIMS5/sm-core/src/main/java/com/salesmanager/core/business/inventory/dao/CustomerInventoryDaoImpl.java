package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.inventory.model.CustomerInventory;
import com.salesmanager.core.business.inventory.model.QCustomerInventory;
import com.salesmanager.core.business.merchant.model.MerchantStore;

@Repository("customerInventoryDao")
public class CustomerInventoryDaoImpl extends SalesManagerEntityDaoImpl<Long, CustomerInventory> implements CustomerInventoryDao {

	@PersistenceContext
	private EntityManager entityManager;

	public CustomerInventoryDaoImpl() {
		super();
	}

	@Override
	public List<CustomerInventory> getCustomerInventoryByCustomerId(Long customerId) {
		QCustomerInventory qCustomerInventory = QCustomerInventory.customerInventory;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qCustomerInventory).where(qCustomerInventory.id.eq(customerId));

		return query.list(qCustomerInventory);
	}

	@Override
	public List<CustomerInventory> getCustomerInventoryByInventoryId(Long inventoryId) {
		QCustomerInventory qCustomerInventory = QCustomerInventory.customerInventory;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qCustomerInventory).where(qCustomerInventory.inventoryid.eq(inventoryId));

		return query.list(qCustomerInventory);
	}

	@Override
	public List<CustomerInventory> listCustomerInventory(MerchantStore store) {
		QCustomerInventory qCustomerInventory = QCustomerInventory.customerInventory;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qCustomerInventory).leftJoin(qCustomerInventory.merchantStore).fetch().where((qCustomerInventory.merchantStore.id.eq(store.getId())));

		return query.list(qCustomerInventory);
	}

	@Override
	public void saveOrUpdate(CustomerInventory customerInventory) {
		if (customerInventory.getId() == null) {
			super.save(customerInventory);
		} else {
			super.update(customerInventory);
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
