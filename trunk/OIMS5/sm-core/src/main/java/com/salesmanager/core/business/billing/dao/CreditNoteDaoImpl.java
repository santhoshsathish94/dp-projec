package com.salesmanager.core.business.billing.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.billing.model.CreditNote;
import com.salesmanager.core.business.billing.model.CreditNoteProduct;
import com.salesmanager.core.business.billing.model.QCreditNote;
import com.salesmanager.core.business.billing.model.QCreditNoteProduct;
import com.salesmanager.core.business.billing.model.QSalesInvoice;
import com.salesmanager.core.business.catalog.product.model.QProduct;
import com.salesmanager.core.business.customer.model.QCustomer;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.merchant.model.QMerchantStore;
import com.salesmanager.core.business.order.model.QOrder;
import com.salesmanager.core.business.tax.model.taxclass.QTaxClass;

@Repository("CreditNoteDao")
public class CreditNoteDaoImpl extends SalesManagerEntityDaoImpl<Long, CreditNote> implements CreditNoteDao {

	@Override
	public CreditNote getCreditNoteById(Long id) throws ServiceException {
		
		QCreditNote qCreditNote = QCreditNote.creditNote;
		QCustomer qCustomer = QCustomer.customer;
		QOrder qOrder = QOrder.order;
		QMerchantStore qMerchantStore = QMerchantStore.merchantStore;
		QTaxClass qTaxClass = QTaxClass.taxClass;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qCreditNote)
		.leftJoin(qCreditNote.merchantStore, qMerchantStore).fetch()
		.leftJoin(qCreditNote.creditCustomer, qCustomer).fetch()
		.leftJoin(qCreditNote.debitCustomer, qCustomer).fetch()
		.leftJoin(qCreditNote.order, qOrder).fetch()
		.leftJoin(qCreditNote.taxClassOther, qTaxClass).fetch()
		.where(qCreditNote.id.eq(id));
		
		return query.uniqueResult(qCreditNote);
	}

	@Override
	public List<CreditNote> creditNoteList() throws ServiceException {
		
		QCreditNote qCreditNote = QCreditNote.creditNote;
		QCustomer qCustomer = QCustomer.customer;
		QOrder qOrder = QOrder.order;
		QMerchantStore qMerchantStore = QMerchantStore.merchantStore;
		QTaxClass qTaxClass = QTaxClass.taxClass;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qCreditNote)
		.leftJoin(qCreditNote.merchantStore, qMerchantStore).fetch()
		.leftJoin(qCreditNote.creditCustomer, qCustomer).fetch()
		.leftJoin(qCreditNote.debitCustomer, qCustomer).fetch()
		.leftJoin(qCreditNote.order, qOrder).fetch()
		.leftJoin(qCreditNote.taxClassOther, qTaxClass).fetch();
		
		return query.list(qCreditNote);
	}

	@Override
	public void SaveOrUpdate(CreditNote creditNote) throws ServiceException {
		if(creditNote.getId() == null) {
			super.save(creditNote);
		} else {
			super.update(creditNote);
		}
	}

	@Override
	public CreditNoteProduct getCreditNoteProductById(Long id) throws ServiceException {
		
		QCreditNoteProduct qCreditNoteProduct = QCreditNoteProduct.creditNoteProduct;
		QCreditNote qCreditNote = QCreditNote.creditNote;
		QProduct qProduct = QProduct.product;
		QSalesInvoice qSalesInvoice = QSalesInvoice.salesInvoice;
		QTaxClass qTaxClass = QTaxClass.taxClass;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qCreditNoteProduct)
		.leftJoin(qCreditNoteProduct.creditNote, qCreditNote).fetch()
		.leftJoin(qCreditNoteProduct.product, qProduct).fetch()
		.leftJoin(qCreditNoteProduct.salesInvoice, qSalesInvoice).fetch()
		.leftJoin(qCreditNoteProduct.taxClass, qTaxClass).fetch()
		.where(qCreditNoteProduct.id.eq(id));
		
		return query.uniqueResult(qCreditNoteProduct);
	}

	@Override
	public List<CreditNoteProduct> creditNoteProductList() throws ServiceException {
		
		QCreditNoteProduct qCreditNoteProduct = QCreditNoteProduct.creditNoteProduct;
		QCreditNote qCreditNote = QCreditNote.creditNote;
		QProduct qProduct = QProduct.product;
		QSalesInvoice qSalesInvoice = QSalesInvoice.salesInvoice;
		QTaxClass qTaxClass = QTaxClass.taxClass;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qCreditNoteProduct)
		.leftJoin(qCreditNoteProduct.creditNote, qCreditNote).fetch()
		.leftJoin(qCreditNoteProduct.product, qProduct).fetch()
		.leftJoin(qCreditNoteProduct.salesInvoice, qSalesInvoice).fetch()
		.leftJoin(qCreditNoteProduct.taxClass, qTaxClass).fetch();
		
		return query.list(qCreditNoteProduct);
	}

	@Override
	public List<CreditNoteProduct> creditNoteProductByCreditNoteId(Long Id) throws ServiceException {
		
		QCreditNoteProduct qCreditNoteProduct = QCreditNoteProduct.creditNoteProduct;
		QCreditNote qCreditNote = QCreditNote.creditNote;
		QProduct qProduct = QProduct.product;
		QSalesInvoice qSalesInvoice = QSalesInvoice.salesInvoice;
		QTaxClass qTaxClass = QTaxClass.taxClass;
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qCreditNoteProduct)
		.leftJoin(qCreditNoteProduct.creditNote, qCreditNote).fetch()
		.leftJoin(qCreditNoteProduct.product, qProduct).fetch()
		.leftJoin(qCreditNoteProduct.salesInvoice, qSalesInvoice).fetch()
		.leftJoin(qCreditNoteProduct.taxClass, qTaxClass).fetch()
		.where(qCreditNoteProduct.creditNote.id.eq(Id));
		
		return query.list(qCreditNoteProduct);
	}

	@Override
	public void SaveOrUpdate(CreditNoteProduct creditNoteProduct) throws ServiceException {
		if(creditNoteProduct.getId() == null) {
			super.save(creditNoteProduct);
		} else {
			super.update(creditNoteProduct);
		}
		
	}
}
