package com.salesmanager.core.business.supplier.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.supplier.model.PartyItemDefaultMargin;
import com.salesmanager.core.business.supplier.model.QPartyItemDefaultMargin;
import com.salesmanager.core.business.supplier.model.QSupplier;
import com.salesmanager.core.business.supplier.model.Supplier;

@Repository("SupplierDao")
public class SupplierDaoImpl extends SalesManagerEntityDaoImpl<Integer, Supplier> implements SupplierDao {

	@Override
	public Supplier getSupplier(Integer id) {
		
		QSupplier qSupplier = QSupplier.supplier;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qSupplier).where(qSupplier.id.eq(id));
		
		return query.uniqueResult(qSupplier);
	}

	@Override
	public List<Supplier> getSupplierList() {
		
		QSupplier qSupplier = QSupplier.supplier;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qSupplier);
		
		return query.list(qSupplier);
	}

	@Override
	public void saveOrUpdate(Supplier supplier) {
		if(supplier.getId() == null) {
			super.save(supplier);
		} else {
			super.update(supplier);
		}
	
	}

	@Override
	public PartyItemDefaultMargin getPartyItemDefaultMargin(Long id) {
		
		QPartyItemDefaultMargin qPartyItemDefaultMargin = QPartyItemDefaultMargin.partyItemDefaultMargin;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qPartyItemDefaultMargin).where(qPartyItemDefaultMargin.id.eq(id));
		
		return query.uniqueResult(qPartyItemDefaultMargin);
	}

	@Override
	public List<PartyItemDefaultMargin> getPartyItemDefaultMarginList() {
		
		QPartyItemDefaultMargin qPartyItemDefaultMargin = QPartyItemDefaultMargin.partyItemDefaultMargin;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qPartyItemDefaultMargin);
		
		return query.list(qPartyItemDefaultMargin);
	}

	@Override
	public void saveOrUpdate(PartyItemDefaultMargin partyItemDefaultMargin) {
		
		if(partyItemDefaultMargin.getId() == null) {
			super.save(partyItemDefaultMargin);
		} else {
			super.update(partyItemDefaultMargin);
		}
		
	}

}
