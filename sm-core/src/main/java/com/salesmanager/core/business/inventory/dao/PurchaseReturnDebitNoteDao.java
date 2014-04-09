package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.inventory.model.PurchaseReturnDebitNote;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface PurchaseReturnDebitNoteDao extends SalesManagerEntityDao<Long, PurchaseReturnDebitNote> {

	public PurchaseReturnDebitNote getPurchaseReturnDebitNote(Long debitId);

	List<PurchaseReturnDebitNote> listPurchaseReturnDebitNote(MerchantStore store);

	void saveOrUpdate(PurchaseReturnDebitNote purchaseReturnDebitNote);

}
