package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.inventory.model.BranchTransfer;
import com.salesmanager.core.business.inventory.model.QBranchTransfer;
import com.salesmanager.core.business.merchant.model.MerchantStore;

@Repository("branchTransferDao")
public class BranchTransferDaoImpl extends SalesManagerEntityDaoImpl<Long, BranchTransfer> implements BranchTransferDao {

	@PersistenceContext
	private EntityManager entityManager;

	public BranchTransferDaoImpl() {
		super();
	}

	@Override
	public BranchTransfer getBranchTransfer(Long purchaseid) {
		QBranchTransfer qBranchTransfer = QBranchTransfer.branchTransfer;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qBranchTransfer).where(qBranchTransfer.id.eq(purchaseid));

		return query.uniqueResult(qBranchTransfer);
	}

	@Override
	public List<BranchTransfer> listBranchTransfer(MerchantStore store) {
		QBranchTransfer qBranchTransfer = QBranchTransfer.branchTransfer;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qBranchTransfer).leftJoin(qBranchTransfer.merchantStore).fetch().where((qBranchTransfer.merchantStore.id.eq(store.getId())));

		return query.list(qBranchTransfer);
	}

	@Override
	public void saveOrUpdate(BranchTransfer branchTransfer) {
		if (branchTransfer.getId() == null) {
			super.save(branchTransfer);
		} else {
			super.update(branchTransfer);
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
