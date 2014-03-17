package com.salesmanager.core.business.supplier.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
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

}
