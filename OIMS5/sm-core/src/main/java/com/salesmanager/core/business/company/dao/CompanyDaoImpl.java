package com.salesmanager.core.business.company.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.company.model.AccountingPeriod;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.company.model.CompanyCurrencies;
import com.salesmanager.core.business.company.model.QAccountingPeriod;
import com.salesmanager.core.business.company.model.QCompany;
import com.salesmanager.core.business.company.model.QCompanyCurrencies;
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
			.leftJoin(qCompany.companyCurrency).fetch()
			.leftJoin(qCompany.companyCountry).fetch()
			.leftJoin(qCompany.companyZone).fetch()
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
		query.from(qAccountingPeriod).where(qAccountingPeriod.id.eq(id));
		
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
		if(accountingPeriod.getId() == null) {
			super.save(accountingPeriod);
		} else {
			super.update(accountingPeriod);
		}
	}

	@Override
	public CompanyCurrencies getByCompanyCurrenciesId(int id) {
		QCompanyCurrencies qCompanyCurrencies = QCompanyCurrencies.companyCurrencies;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qCompanyCurrencies).where(qCompanyCurrencies.id.eq(id));
		
		return query.uniqueResult(qCompanyCurrencies);
	}

	@Override
	public List<CompanyCurrencies> listCompanyCurrencies() {
		QCompanyCurrencies qCompanyCurrencies = QCompanyCurrencies.companyCurrencies;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qCompanyCurrencies);
		
		return query.list(qCompanyCurrencies);
	}

	@Override
	public void saveOrUpdate(CompanyCurrencies companyCurrencies) {
		if(companyCurrencies.getId() == null) {
			super.save(companyCurrencies);
		} else {
			super.update(companyCurrencies);
		}
	}

	@Override
	public Company getCompanyByCompanyDisplayName(String displayName) {
		QCompany qCompany = QCompany.company;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qCompany).where(qCompany.companyDisplayName.eq(displayName));
		
		return query.uniqueResult(qCompany);
	}

	@Override
	public List<String> getCompanyNameListByDisplayName(String displayName) {
		
		StringBuilder qs = new StringBuilder();
		qs.append("select c.companyDisplayName from Company as c ");
		qs.append("where c.companyDisplayName like :displayName ");
		
		String hql = qs.toString();
		Query q = super.getEntityManager().createQuery(hql);
		
		q.setParameter("displayName", displayName+"%");
		
		List<String> displayNameList = q.getResultList(); 
		
		return displayNameList;
	}

	
}
