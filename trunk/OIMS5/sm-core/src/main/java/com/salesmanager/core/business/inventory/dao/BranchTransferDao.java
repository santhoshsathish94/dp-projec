package com.salesmanager.core.business.inventory.dao;

import java.util.List;

import com.salesmanager.core.business.generic.dao.SalesManagerEntityDao;
import com.salesmanager.core.business.inventory.model.BranchTransfer;
import com.salesmanager.core.business.merchant.model.MerchantStore;

public interface BranchTransferDao extends SalesManagerEntityDao<Long, BranchTransfer> {

	public BranchTransfer getBranchTransfer(Long transfer_id);

	List<BranchTransfer> listBranchTransfer(MerchantStore store);

	void saveOrUpdate(BranchTransfer branchTransfer);

}
