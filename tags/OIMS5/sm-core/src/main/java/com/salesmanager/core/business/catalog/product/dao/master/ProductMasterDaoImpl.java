package com.salesmanager.core.business.catalog.product.dao.master;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.catalog.product.model.master.QShades;
import com.salesmanager.core.business.catalog.product.model.master.QVariants;
import com.salesmanager.core.business.catalog.product.model.master.Shades;
import com.salesmanager.core.business.catalog.product.model.master.Variants;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;

@Repository("ProductMasterDao")
public class ProductMasterDaoImpl extends SalesManagerEntityDaoImpl<Long, Variants> implements ProductMasterDao {

	@Override
	public Variants getVariantsById(Long id) {
		
		QVariants qVaraints = QVariants.variants;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qVaraints).where(qVaraints.id.eq(id));
		
		return query.uniqueResult(qVaraints);
	}

	@Override
	public Variants getVariantsByName(String variantName) {
		
		QVariants qVaraints = QVariants.variants;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qVaraints).where(qVaraints.variantName.eq(variantName));
		
		return query.uniqueResult(qVaraints);
	}

	@Override
	public List<Variants> getVariantsList() {
		
		QVariants qVaraints = QVariants.variants;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qVaraints);
		
		return query.list(qVaraints);
	}

	@Override
	public void saveOrUpdate(Variants variants) {
		if(variants.getId() == null) {
			super.save(variants);
		} else {
			super.update(variants);
		}
	}

	@Override
	public Shades getShadesById(Long id) {
		
		QShades qShades = QShades.shades;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qShades).where(qShades.id.eq(id));
		
		return query.uniqueResult(qShades);
	}

	@Override
	public Shades getShadesByShortCode(String shortCode) {
		
		QShades qShades = QShades.shades;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qShades).where(qShades.shortName.eq(shortCode));
		
		return query.uniqueResult(qShades);
	}

	@Override
	public List<Shades> getShadesList() {

		QShades qShades = QShades.shades;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qShades);
		
		return query.list(qShades);
	}

	@Override
	public void saveOrUpdate(Shades shades) {
		if(shades.getId() == null) {
			super.save(shades);
		} else {
			super.update(shades);
		}
		
	}

	@Override
	public List<String> getVariantNameList() {
		
		StringBuilder qs = new StringBuilder();
		qs.append("select v.variantName from Variants as v ");
		
		String hql = qs.toString();
		Query q = super.getEntityManager().createQuery(hql);
		
		List<String> variantName = q.getResultList();
		
		return variantName;
	}
	
	@Override
	public List<String> getShadeNameList() {
		
		StringBuilder qs = new StringBuilder();
		qs.append("select s.shadesName from Shades as s ");
		
		String hql = qs.toString();
		Query q = super.getEntityManager().createQuery(hql);
		
		List<String> shadesName = q.getResultList();
		
		return shadesName;
	}

}
