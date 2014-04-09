package com.salesmanager.core.business.reference.inventory.service;

import java.util.List;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.inventory.model.DebitNoteOther;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface DebitNoteOtherService extends SalesManagerEntityService<Long, DebitNoteOther> {

	public DebitNoteOther getById(String id) throws ServiceException;

	public List<DebitNoteOther> getDebitNoteOther(MerchantStore store) throws ServiceException;

	public void addDebitNoteOther(DebitNoteOther debitNoteOther);

}
