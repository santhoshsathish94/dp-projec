package com.salesmanager.core.business.reference.inventory.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.inventory.dao.BranchTransferDao;
import com.salesmanager.core.business.inventory.model.BranchTransfer;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.utils.CacheUtils;

@Service("branchTransferService")
public class BranchTransferServiceImpl extends SalesManagerEntityServiceImpl<Long, BranchTransfer> implements BranchTransferService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BranchTransferServiceImpl.class);

	private BranchTransferDao branchTransferDao;

	@Autowired
	private CacheUtils cache;

	@Autowired
	public BranchTransferServiceImpl(BranchTransferDao branchTransferDao) {
		super(branchTransferDao);
		this.branchTransferDao = branchTransferDao;
	}

	@Override
	public BranchTransfer getById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BranchTransfer> getBranchTransfer(MerchantStore store) throws ServiceException {
		return branchTransferDao.listBranchTransfer(store);
	}

	@Override
	public void addBranchTransfer(BranchTransfer branchTransfer) {
		System.out.println("==============public void branchTransfer(BranchTransfer branchTransfer)===================>>" + branchTransfer.toString());
		branchTransferDao.saveOrUpdate(branchTransfer);

	}

}
