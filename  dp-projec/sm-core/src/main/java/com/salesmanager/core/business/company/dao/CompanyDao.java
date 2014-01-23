package com.salesmanager.core.business.company.dao;

import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.generic.exception.ServiceException;

public interface CompanyDao  extends SalesManagerEntityDao<Integer, Company> {

	
	public Company getCompany(Integer companyId);
	
	public Company getCompanyDetails(String code) throws ServiceException;

}
