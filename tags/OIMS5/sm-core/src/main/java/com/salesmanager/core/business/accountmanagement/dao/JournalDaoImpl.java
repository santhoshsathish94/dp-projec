package com.salesmanager.core.business.accountmanagement.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.accountmanagement.model.Journal;
import com.salesmanager.core.business.accountmanagement.model.QJournal;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.merchant.model.MerchantStore;

@Repository("journalDao")
public class JournalDaoImpl extends SalesManagerEntityDaoImpl<Long, Journal> implements JournalDao {

	@PersistenceContext
	private EntityManager entityManager;

	public JournalDaoImpl() {
		super();
	}

	@Override
	public Journal getJournal(Long purchaseid) {
		QJournal qJournal = QJournal.journal;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qJournal).where(qJournal.id.eq(purchaseid));

		return query.uniqueResult(qJournal);
	}

	@Override
	public List<Journal> listJournal(MerchantStore store) {
		QJournal qJournal = QJournal.journal;
		JPQLQuery query = new JPAQuery(getEntityManager());
		query.from(qJournal).leftJoin(qJournal.merchantStore).fetch().where((qJournal.merchantStore.id.eq(store.getId())));

		return query.list(qJournal);
	}

	@Override
	public void saveOrUpdate(Journal journal) {
		if (journal.getId() == null) {
			super.save(journal);
		} else {
			super.update(journal);
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
