package com.salesmanager.core.business.billing.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.billing.model.InvoiceSetting;
import com.salesmanager.core.business.billing.model.InvoiceSettingType;
import com.salesmanager.core.business.billing.model.QInvoiceSetting;
import com.salesmanager.core.business.billing.model.QInvoiceSettingType;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;

@Repository("InvoiceSettingDao")
public class InvoiceSettingDaoImpl extends SalesManagerEntityDaoImpl<Long, InvoiceSetting> implements InvoiceSettingDao {

	@Override
	public InvoiceSettingType getInvoiceSettingType(Long settingTypeId) {
		
		QInvoiceSettingType qInvoiceSettingType = QInvoiceSettingType.invoiceSettingType;
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qInvoiceSettingType).where(qInvoiceSettingType.id.eq(settingTypeId));
		
		return query.uniqueResult(qInvoiceSettingType);
	}

	@Override
	public List<InvoiceSettingType> getInvoiceSettingTypeList() {
		
		QInvoiceSettingType qInvoiceSettingType = QInvoiceSettingType.invoiceSettingType;
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qInvoiceSettingType);
		
		return query.list(qInvoiceSettingType);
	}

	@Override
	public InvoiceSetting getInvoiceSetting(Long settingId) {
		
		QInvoiceSetting qInvoiceSetting = QInvoiceSetting.invoiceSetting;
		JPAQuery query = new JPAQuery(getEntityManager());
		
		query.from(qInvoiceSetting).where(qInvoiceSetting.id.eq(settingId));
		
		return query.uniqueResult(qInvoiceSetting);
	}

	@Override
	public InvoiceSetting getInvoiceSettingByPrefix(String prefix) {
		
		QInvoiceSetting qInvoiceSetting = QInvoiceSetting.invoiceSetting;
		JPAQuery query = new JPAQuery(getEntityManager());
		
		query.from(qInvoiceSetting).where(qInvoiceSetting.prefix.eq(prefix));
		
		return query.uniqueResult(qInvoiceSetting);
	}
	
	@Override
	public List<InvoiceSetting> getInvoiceSettingList() {
		
		QInvoiceSetting qInvoiceSetting = QInvoiceSetting.invoiceSetting;
		JPAQuery query = new JPAQuery(getEntityManager());
		
		query.from(qInvoiceSetting);
		
		return query.list(qInvoiceSetting);
	}
	
	@Override
	public List<InvoiceSetting> getInvoiceSettingBySettingTypeId(Long settingTypeId) {
		
		QInvoiceSetting qInvoiceSetting = QInvoiceSetting.invoiceSetting;
		JPAQuery query = new JPAQuery(getEntityManager());
		
		query.from(qInvoiceSetting).where(qInvoiceSetting.type.id.eq(settingTypeId));
		
		return query.list(qInvoiceSetting);
	}

	@Override
	public void saveOrUpdate(InvoiceSetting invoiceSetting) {
		if(invoiceSetting.getId() == null) {
			super.save(invoiceSetting);
		} else {
			super.update(invoiceSetting);
		}
		
	}

	

}
