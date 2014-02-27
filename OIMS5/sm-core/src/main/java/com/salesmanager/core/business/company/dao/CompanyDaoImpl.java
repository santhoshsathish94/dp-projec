package com.salesmanager.core.business.company.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.company.model.AccountingPeriod;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.company.model.QAccountingPeriod;
import com.salesmanager.core.business.company.model.QCompany;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.generic.exception.ServiceException;

@Repository("companyDao")
public class CompanyDaoImpl  extends SalesManagerEntityDaoImpl<Integer, Company> implements CompanyDao {

	
	public CompanyDaoImpl() {
		super();
	}
	
	@Override
	public Company getCompany(Integer companyId) {
		
		QCompany qCompany = QCompany.company;

		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qCompany)
			.leftJoin(qCompany.companyCurrency)
			.leftJoin(qCompany.companyCountry)
			.leftJoin(qCompany.companyZone)
			.where(qCompany.id.eq(companyId));
		
		return query.uniqueResult(qCompany);
	}

	@Override
	public Company getCompanyDetails(String code) throws ServiceException {
		
		QCompany qCompany = QCompany.company;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qCompany)
			.leftJoin(qCompany.companyCurrency).fetch()
			.leftJoin(qCompany.companyCountry).fetch()
			.leftJoin(qCompany.companyZone).fetch()
			.where(qCompany.code.eq(code));
		
		return query.uniqueResult(qCompany);
	}

	@Override
	public AccountingPeriod getByAccountingPeriodId(int id) {
		QAccountingPeriod qAccountingPeriod = QAccountingPeriod.accountingPeriod;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qAccountingPeriod).fetchAll();
		
		return query.uniqueResult(qAccountingPeriod);
	}

	@Override
	public List<AccountingPeriod> listAccountingPeriod() {
		QAccountingPeriod qAccountingPeriod = QAccountingPeriod.accountingPeriod;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qAccountingPeriod);
		
		return query.list(qAccountingPeriod);
	}

	@Override
	public List<AccountingPeriod> listAccountingPeriodByCompany(Company company) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdateAccountingPeriod(AccountingPeriod accountingPeriod) {
		
		
	}
}
