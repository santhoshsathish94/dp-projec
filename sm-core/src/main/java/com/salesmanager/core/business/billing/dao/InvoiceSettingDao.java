package com.salesmanager.core.business.billing.dao;

import java.util.List;

import com.salesmanager.core.business.billing.model.InvoiceSetting;
import com.salesmanager.core.business.billing.model.InvoiceSettingType;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;

public interface InvoiceSettingDao extends SalesManagerEntityDao<Long, InvoiceSetting> {

	InvoiceSettingType getInvoiceSettingType(Long settingTypeId);
	
	List<InvoiceSettingType> getInvoiceSettingTypeList();
	
	InvoiceSetting getInvoiceSetting(Long settingId);
	
	InvoiceSetting getInvoiceSettingByPrefix(String prefix);
	
	List<InvoiceSetting> getInvoiceSettingList();
	
	List<InvoiceSetting> getInvoiceSettingBySettingTypeId(Long settingTypeId);
	
	void saveOrUpdate(InvoiceSetting invoiceSetting);
}
