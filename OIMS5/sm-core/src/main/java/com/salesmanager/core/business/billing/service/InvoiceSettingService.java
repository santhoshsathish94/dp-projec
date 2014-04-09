package com.salesmanager.core.business.billing.service;

import java.util.List;

import com.salesmanager.core.business.billing.model.InvoiceSetting;
import com.salesmanager.core.business.billing.model.InvoiceSettingType;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;

public interface InvoiceSettingService extends SalesManagerEntityService<Long, InvoiceSetting> {

	InvoiceSettingType getInvoiceSettingType(Long settingTypeId) throws ServiceException;
	
	List<InvoiceSettingType> getInvoiceSettingTypeList() throws ServiceException;
	
	InvoiceSetting getInvoiceSetting(Long settingId) throws ServiceException;
	
	InvoiceSetting getInvoiceSettingByPrefix(String prefix) throws ServiceException;
	
	List<InvoiceSetting> getInvoiceSettingList() throws ServiceException;
	
	List<InvoiceSetting> getInvoiceSettingBySettingTypeId(Long settingTypeId) throws ServiceException;
	
	void saveOrUpdate(InvoiceSetting invoiceSetting) throws ServiceException;
	
}
