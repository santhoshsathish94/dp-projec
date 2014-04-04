package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.inventory.model.BranchTransfer;
import com.salesmanager.core.business.inventory.model.DebitNoteOther;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface DebitNoteOtherDao extends SalesManagerEntityDao<Long, DebitNoteOther> {

	public DebitNoteOther getDebitNoteOther(Long debitId);

	List<DebitNoteOther> listDebitNoteOther(MerchantStore store);

	void saveOrUpdate(DebitNoteOther debitNoteOther);

}
