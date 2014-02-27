package com.salesmanager.core.business.company.service;

import java.util.List;

import com.salesmanager.core.business.company.model.AccountingPeriod;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;

public interface CompanyService extends SalesManagerEntityService<Integer, Company> {

	
	Company getCompany(Integer companyId) throws ServiceException;
	
	Company getByCode(String code) throws ServiceException ;
	
	void saveOrUpdate(Company company) throws ServiceException;
	
	AccountingPeriod getByAccountingPeriodId(Long id) throws ServiceException;
	
	List<AccountingPeriod> listAccountingPeriod() throws ServiceException;
	
	List<AccountingPeriod> listAccountingPeriodByCompany(Company company) throws ServiceException;
	
	void saveOrUpdate(Company company, AccountingPeriod accountingPeriod) throws ServiceException;
	
}
