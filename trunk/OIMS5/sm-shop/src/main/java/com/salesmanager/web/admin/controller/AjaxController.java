package com.salesmanager.web.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salesmanager.core.business.adapter.CustomCustomer;
import com.salesmanager.core.business.billing.model.CreditNoteProductEntity;
import com.salesmanager.core.business.billing.service.SalesInvoiceService;
import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.ProductCriteria;
import com.salesmanager.core.business.catalog.product.model.ProductList;
import com.salesmanager.core.business.catalog.product.service.ProductService;
import com.salesmanager.core.business.common.model.ProductJSONEntity;
import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.customer.service.CustomerService;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.service.utils.LogicUtils;
import com.salesmanager.core.business.service.utils.ProductJSONEntityService;
import com.salesmanager.web.constants.Constants;

@Controller
public class AjaxController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	public SalesInvoiceService salesInvoiceService;
	
	@RequestMapping(value={"/admin/ajax/loadCustomer.html"}, method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String loadCustomerData(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		
		String fieldValue = request.getParameter("fieldValue");
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		List<Customer> customerList = customerService.getByNameOrIdOrCompany(store, fieldValue);
		
		List<CustomCustomer> customCustomerList = new ArrayList<CustomCustomer>();
		
		for(Customer customer: customerList) {
			CustomCustomer cc = new CustomCustomer();
			
			cc.id = customer.getId();
			cc.name = customer.getBilling().getFirstName() + " " + customer.getBilling().getLastName();
			cc.nick = customer.getNick();
			cc.company = customer.getBilling().getCompany();
			
			customCustomerList.add(cc);
		}
		
		return LogicUtils.getJSONString(customCustomerList);
	}
	
	@RequestMapping(value={"/admin/ajax/loadProductInfo.html"}, method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String loadProductInfo(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		
		String fieldValue = request.getParameter("fieldValue");
		String searchType = request.getParameter("searchType");
		
		Language language = (Language)request.getAttribute("LANGUAGE");
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		ProductCriteria criteria = new ProductCriteria();
		
		ProductList productList = productService.listByStore(store, language, criteria);
		
		List<Product> pList = productList.getProducts();
		
		/*if(StringUtils.equals("CREDITNOTE", searchType)) {
			
			List<CreditNoteProductEntity> customProdList = salesInvoiceService.getcustomProdListForCreditNote(pList, fieldValue);
			return LogicUtils.getJSONString(customProdList);
		
		} else {*/
		
			List<ProductJSONEntity> customProdList = getcustomProdList(pList, fieldValue);
			return LogicUtils.getJSONString(customProdList);
		
		//}
	}
	
	private List<ProductJSONEntity> getcustomProdList(List<Product> productList, String fieldValue) {
		
		List<ProductJSONEntity> customProdList = ProductJSONEntityService.getProductJSONEntity(productList);
		
		List<ProductJSONEntity> newCustomProdList = new ArrayList<ProductJSONEntity>(); 
		
		for(ProductJSONEntity siProd: customProdList) {
			
			if(StringUtils.containsIgnoreCase(siProd.productName, fieldValue)) {
				newCustomProdList.add(siProd);
			}
		}
		
		return newCustomProdList;
	}
}
