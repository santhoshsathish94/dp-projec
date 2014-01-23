package com.salesmanager.core.business.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.company.dao.CompanyDao;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;

@Service("companyService")
public class CompanyServiceImpl extends SalesManagerEntityServiceImpl<Integer, Company> implements CompanyService {

	private CompanyDao companyDao;
	
	@Autowired
	public CompanyServiceImpl(CompanyDao companyDao) {
		super(companyDao);
		this.companyDao = companyDao;
	}

	@Override
	public Company getCompany(Integer companyId) throws ServiceException {
		return companyDao.getCompany(companyId);
	}

	
	
	@Override
	public void saveOrUpdate(Company company) throws ServiceException {
		
		if(company.getId()==null) {
			super.save(company);
		} else {
			super.update(company);
		}
	}

	@Override
	public Company getByCode(String code) throws ServiceException {
		// TODO Auto-generated method stub
		return companyDao.getCompanyDetails(code);
	}

}
