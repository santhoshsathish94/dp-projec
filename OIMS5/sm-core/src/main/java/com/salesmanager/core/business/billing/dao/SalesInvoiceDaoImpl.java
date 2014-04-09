package com.salesmanager.core.business.billing.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.billing.model.QInvoiceSetting;
import com.salesmanager.core.business.billing.model.QInvoiceSettingType;
import com.salesmanager.core.business.billing.model.QSalesInvoice;
import com.salesmanager.core.business.billing.model.QSalesInvoiceProduct;
import com.salesmanager.core.business.billing.model.SalesInvoice;
import com.salesmanager.core.business.billing.model.SalesInvoiceProduct;
import com.salesmanager.core.business.catalog.product.model.QProduct;
import com.salesmanager.core.business.catalog.product.model.attribute.QProductAttribute;
import com.salesmanager.core.business.customer.model.QCustomer;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.merchant.model.QMerchantStore;
import com.salesmanager.core.business.order.model.QOrder;
import com.salesmanager.core.business.tax.model.taxclass.QTaxClass;

@Repository("SalesInvoiceDao")
public class SalesInvoiceDaoImpl extends SalesManagerEntityDaoImpl<Long, SalesInvoice> implements SalesInvoiceDao {

	@Override
	public SalesInvoice getSalesInvoiceById(Long id) {
		
		QSalesInvoice qSalesInvoice = QSalesInvoice.salesInvoice;
		QCustomer qCustomer = QCustomer.customer; 
		QOrder qOrder = QOrder.order;
		QMerchantStore qMerchantStore = QMerchantStore.merchantStore;
		QInvoiceSetting qInvoiceSetting = QInvoiceSetting.invoiceSetting;
		QInvoiceSettingType qInvoiceSettingType = QInvoiceSettingType.invoiceSettingType;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		query.from(qSalesInvoice)
		.leftJoin(qSalesInvoice.invoiceSetting, qInvoiceSetting).fetch()
		.leftJoin(qInvoiceSetting.type, qInvoiceSettingType).fetch()
		.leftJoin(qSalesInvoice.customer, qCustomer).fetch()
		.leftJoin(qSalesInvoice.order, qOrder).fetch()
		.leftJoin(qSalesInvoice.merchantStore, qMerchantStore).fetch()
		.where(qSalesInvoice.id.eq(id));
		
		return query.uniqueResult(qSalesInvoice);
	}

	@Override
	public List<SalesInvoice> salesInvoiceList() {

		QSalesInvoice qSalesInvoice = QSalesInvoice.salesInvoice;
		QCustomer qCustomer = QCustomer.customer; 
		QOrder qOrder = QOrder.order;
		QMerchantStore qMerchantStore = QMerchantStore.merchantStore;
		QInvoiceSetting qInvoiceSetting = QInvoiceSetting.invoiceSetting;
		QInvoiceSettingType qInvoiceSettingType = QInvoiceSettingType.invoiceSettingType;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qSalesInvoice)
		.leftJoin(qSalesInvoice.invoiceSetting, qInvoiceSetting).fetch()
		.leftJoin(qInvoiceSetting.type, qInvoiceSettingType).fetch()
		.leftJoin(qSalesInvoice.customer, qCustomer).fetch()
		.leftJoin(qSalesInvoice.order, qOrder).fetch()
		.leftJoin(qSalesInvoice.merchantStore, qMerchantStore).fetch();
		
		return query.list(qSalesInvoice);
	}

	@Override
	public void SaveOrUpdate(SalesInvoice salesInvoice) {
		
		if(salesInvoice.getId() == null) {
			super.save(salesInvoice);
		} else {
			super.update(salesInvoice);
		}
		
	}

	@Override
	public SalesInvoiceProduct getSalesInvoiceProductById(Long id) {
		
		QSalesInvoiceProduct qSalesInvoiceProduct = QSalesInvoiceProduct.salesInvoiceProduct;
		QProduct qProduct = QProduct.product;
		QSalesInvoice qSalesInvoice = QSalesInvoice.salesInvoice;
		QTaxClass qTaxClass = QTaxClass.taxClass;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qSalesInvoiceProduct)
		.leftJoin(qSalesInvoiceProduct.product, qProduct).fetch()
		.leftJoin(qSalesInvoiceProduct.salesInvoice, qSalesInvoice).fetch()
		.leftJoin(qSalesInvoiceProduct.taxClass, qTaxClass).fetch()
		.where(qSalesInvoiceProduct.id.eq(id));
		
		return query.uniqueResult(qSalesInvoiceProduct);
	}

	@Override
	public List<SalesInvoiceProduct> salesInvoiceProductList() {
		
		QSalesInvoiceProduct qSalesInvoiceProduct = QSalesInvoiceProduct.salesInvoiceProduct;
		QProduct qProduct = QProduct.product;
		QSalesInvoice qSalesInvoice = QSalesInvoice.salesInvoice;
		QTaxClass qTaxClass = QTaxClass.taxClass;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qSalesInvoiceProduct)
		.leftJoin(qSalesInvoiceProduct.product, qProduct).fetch()
		.leftJoin(qSalesInvoiceProduct.salesInvoice, qSalesInvoice).fetch()
		.leftJoin(qSalesInvoiceProduct.taxClass, qTaxClass).fetch();
		
		return query.list(qSalesInvoiceProduct);
	}

	@Override
	public List<SalesInvoiceProduct> salesInvoiceProductListBySalesInvoiceId(Long Id) {
		
		QSalesInvoiceProduct qSalesInvoiceProduct = QSalesInvoiceProduct.salesInvoiceProduct;
		QProduct qProduct = QProduct.product;
		QSalesInvoice qSalesInvoice = QSalesInvoice.salesInvoice;
		QTaxClass qTaxClass = QTaxClass.taxClass;
		QProductAttribute qProductAttribute = QProductAttribute.productAttribute;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qSalesInvoiceProduct)
		.leftJoin(qSalesInvoiceProduct.product, qProduct).fetch()
		.leftJoin(qSalesInvoiceProduct.salesInvoice, qSalesInvoice).fetch()
		.leftJoin(qSalesInvoiceProduct.productAttribute, qProductAttribute).fetch()
		.leftJoin(qSalesInvoiceProduct.taxClass, qTaxClass).fetch()
		.where(qSalesInvoiceProduct.salesInvoice.id.eq(Id));
		
		return query.list(qSalesInvoiceProduct);
	}

	@Override
	public void SaveOrUpdate(SalesInvoiceProduct salesInvoiceProduct) {
		
		if(salesInvoiceProduct.getId() == null) {
			super.save(salesInvoiceProduct);
		} else {
			super.update(salesInvoiceProduct);
		}
		
	}

}
