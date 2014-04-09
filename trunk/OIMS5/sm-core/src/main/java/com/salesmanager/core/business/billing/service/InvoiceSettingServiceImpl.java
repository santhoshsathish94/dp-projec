package com.salesmanager.core.business.billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.billing.dao.InvoiceSettingDao;
import com.salesmanager.core.business.billing.model.InvoiceSetting;
import com.salesmanager.core.business.billing.model.InvoiceSettingType;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;


@Service("InvoiceSettingService")
public class InvoiceSettingServiceImpl extends SalesManagerEntityServiceImpl<Long, InvoiceSetting> implements InvoiceSettingService {

	
	private InvoiceSettingDao invoiceSettingDao;
	
	@Autowired
	public InvoiceSettingServiceImpl(InvoiceSettingDao invoiceSettingDao) {
		super(invoiceSettingDao);
		this.invoiceSettingDao = invoiceSettingDao;
	}
	
	
	@Override
	public InvoiceSettingType getInvoiceSettingType(Long settingTypeId) throws ServiceException {
		return invoiceSettingDao.getInvoiceSettingType(settingTypeId);
	}

	@Override
	public List<InvoiceSettingType> getInvoiceSettingTypeList() throws ServiceException {
		return invoiceSettingDao.getInvoiceSettingTypeList();
	}

	@Override
	public InvoiceSetting getInvoiceSetting(Long settingId) throws ServiceException {
		return invoiceSettingDao.getInvoiceSetting(settingId);
	}

	@Override
	public InvoiceSetting getInvoiceSettingByPrefix(String prefix) throws ServiceException {
		return invoiceSettingDao.getInvoiceSettingByPrefix(prefix);
	}
	
	@Override
	public List<InvoiceSetting> getInvoiceSettingList() throws ServiceException {
		return invoiceSettingDao.getInvoiceSettingList();
	}

	@Override
	public List<InvoiceSetting> getInvoiceSettingBySettingTypeId(Long settingTypeId) throws ServiceException {
		return invoiceSettingDao.getInvoiceSettingBySettingTypeId(settingTypeId);
	}

	@Override
	public void saveOrUpdate(InvoiceSetting invoiceSetting) throws ServiceException {
		invoiceSettingDao.saveOrUpdate(invoiceSetting);
	}

}
