package com.salesmanager.core.business.reference.inventory.service;

import java.util.List;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.inventory.model.PurchaseReturnDebitNote;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface PurchaseReturnDebitNoteService extends SalesManagerEntityService<Long, PurchaseReturnDebitNote> {

	public PurchaseReturnDebitNote getById(String id) throws ServiceException;

	public List<PurchaseReturnDebitNote> getPurchaseReturnDebitNote(MerchantStore store) throws ServiceException;

	public void addPurchaseReturnDebitNote(PurchaseReturnDebitNote purchaseReturnDebitNote);

}
