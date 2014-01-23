package com.salesmanager.core.business.company.service;

import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;

public interface CompanyService extends SalesManagerEntityService<Integer, Company> {

	
	Company getCompany(Integer companyId) throws ServiceException;
	
	Company getByCode(String code) throws ServiceException ;
	
	void saveOrUpdate(Company company) throws ServiceException;
}
