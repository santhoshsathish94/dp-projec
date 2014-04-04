package com.salesmanager.core.business.reference.inventory.service;

import java.util.List;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.inventory.model.BranchTransfer;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface BranchTransferService extends SalesManagerEntityService<Long, BranchTransfer> {

	public BranchTransfer getById(String id) throws ServiceException;

	public List<BranchTransfer> getBranchTransfer(MerchantStore store) throws ServiceException;

	public void addBranchTransfer(BranchTransfer branchTransfer);

}
