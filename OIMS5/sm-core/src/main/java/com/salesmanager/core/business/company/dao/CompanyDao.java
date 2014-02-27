package com.salesmanager.core.business.company.dao;

import java.util.List;

import com.salesmanager.core.business.company.model.AccountingPeriod;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.generic.exception.ServiceException;

public interface CompanyDao  extends SalesManagerEntityDao<Integer, Company> {

	
	public Company getCompany(Integer companyId);
	
	public Company getCompanyDetails(String code) throws ServiceException;
	
	AccountingPeriod getByAccountingPeriodId(int id);
	
	List<AccountingPeriod> listAccountingPeriod();
	
	List<AccountingPeriod> listAccountingPeriodByCompany(Company company);

	void saveOrUpdateAccountingPeriod(AccountingPeriod accountingPeriod);
}
