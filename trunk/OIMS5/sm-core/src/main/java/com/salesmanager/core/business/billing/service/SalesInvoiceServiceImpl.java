package com.salesmanager.core.business.billing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.salesmanager.core.business.billing.dao.SalesInvoiceDao;
import com.salesmanager.core.business.billing.model.CreditNoteProductEntity;
import com.salesmanager.core.business.billing.model.SalesInvoice;
import com.salesmanager.core.business.billing.model.SalesInvoiceProduct;
import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.common.model.ProductJSONEntity;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.service.utils.ProductJSONEntityService;

@Service("SalesInvoiceService")
public class SalesInvoiceServiceImpl extends SalesManagerEntityServiceImpl<Long, SalesInvoice> implements SalesInvoiceService {

	private SalesInvoiceDao salesInvoiceDao;
	
	@Autowired
	public SalesInvoiceServiceImpl(SalesInvoiceDao salesInvoiceDao) {
		super(salesInvoiceDao);
		this.salesInvoiceDao = salesInvoiceDao;
	}

	@Override
	public SalesInvoice getSalesInvoiceById(Long id) throws ServiceException {
		return salesInvoiceDao.getSalesInvoiceById(id);
	}

	@Override
	public List<SalesInvoice> salesInvoiceList() throws ServiceException {
		return salesInvoiceDao.salesInvoiceList();
	}

	@Override
	public void SaveOrUpdate(SalesInvoice salesInvoice) throws ServiceException {
		
		salesInvoiceDao.SaveOrUpdate(salesInvoice);
	}

	@Override
	public SalesInvoiceProduct getSalesInvoiceProductById(Long id) throws ServiceException {
		return salesInvoiceDao.getSalesInvoiceProductById(id);
	}

	@Override
	public List<SalesInvoiceProduct> salesInvoiceProductList() throws ServiceException {
		return salesInvoiceDao.salesInvoiceProductList();
	}

	@Override
	public List<SalesInvoiceProduct> salesInvoiceProductListBySalesInvoiceId(Long Id) throws ServiceException {
		return salesInvoiceDao.salesInvoiceProductListBySalesInvoiceId(Id);
	}

	@Override
	public void SaveOrUpdate(SalesInvoiceProduct salesInvoiceProduct) throws ServiceException {
		salesInvoiceDao.SaveOrUpdate(salesInvoiceProduct);
	}

	@Override
	public List<CreditNoteProductEntity> getcustomProdListForCreditNote(List<Product> productList, String fieldValue) throws ServiceException {
		
		List<CreditNoteProductEntity> customProdList = CreditNoteProductEntity.getCreditNoteProductEntity(productList, fieldValue);
		
		return customProdList;
	}
	
	@Override
	public void saveSalesInvoiceProductInfo(String productInfoJson, SalesInvoice invoice) {
		List<ProductJSONEntity> salesInvoiceProductEntityList = new ArrayList<ProductJSONEntity>();
		
		ProductJSONEntity salesInvoiceProductEntity = null;
		
		JsonArray jsonArr = new JsonParser().parse(productInfoJson).getAsJsonArray();
		
		if(jsonArr.size() > 0) {
			for(int i = 0; i < jsonArr.size(); i++) {
				JsonObject combineArrayrObject = jsonArr.get(i).getAsJsonObject();
				if(combineArrayrObject != null) {
					
					salesInvoiceProductEntity = new Gson().fromJson(combineArrayrObject, ProductJSONEntity.class);
					
					salesInvoiceProductEntityList.add(salesInvoiceProductEntity);
				}
			}
		}
		
		if(salesInvoiceProductEntityList.size() > 0) {
			for(ProductJSONEntity siProductEntity: salesInvoiceProductEntityList) {
				
				try {
					SaveOrUpdate(ProductJSONEntityService.getSalesInvoiceProduct(siProductEntity, invoice));
					
				} catch (ServiceException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
