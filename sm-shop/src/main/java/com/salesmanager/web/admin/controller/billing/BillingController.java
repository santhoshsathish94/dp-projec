package com.salesmanager.web.admin.controller.billing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.salesmanager.core.business.adapter.CustomCustomer;
import com.salesmanager.core.business.billing.model.InvoiceSetting;
import com.salesmanager.core.business.billing.model.InvoiceSettingType;
import com.salesmanager.core.business.billing.model.SalesInvoice;
import com.salesmanager.core.business.billing.model.SalesInvoiceProduct;
import com.salesmanager.core.business.billing.model.SalesInvoiceProductEntity;
import com.salesmanager.core.business.billing.service.InvoiceSettingService;
import com.salesmanager.core.business.billing.service.SalesInvoiceService;
import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.ProductCriteria;
import com.salesmanager.core.business.catalog.product.model.ProductList;
import com.salesmanager.core.business.catalog.product.service.ProductService;
import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.customer.service.CustomerService;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.DateUtil;
import com.salesmanager.web.utils.LogicUtils;

@Controller
public class BillingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BillingController.class);
	
	@Autowired
	public InvoiceSettingService invoiceSettingService;
	
	@Autowired
	public SalesInvoiceService salesInvoiceService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/billing/saleinvoicesetting.html", method=RequestMethod.GET)
	public String salesInvoiceSettings(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		return displayInvoiceSetting(model, request, response, locale);
	}
	
	private void setMenu(Model model, HttpServletRequest request, String selectedMenu) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("billing-setting", "billing-setting");
		activeMenus.put(selectedMenu, selectedMenu);

		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("billing-setting");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
	}
	
	private String displayInvoiceSetting(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request, "sales-invoice-setting");
		
		List<InvoiceSettingType> invoiceSettingTypeList = invoiceSettingService.getInvoiceSettingTypeList(); 
		InvoiceSetting invoiceSetting = new InvoiceSetting();

		model.addAttribute("typeList", invoiceSettingTypeList);
		model.addAttribute("invoiceSetting", invoiceSetting);
		
		return "admin-invoice-settings";
	}
	
	@Secured("AUTH")
	@RequestMapping(value = "/admin/billing/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String partyItemDefaultMarginPaging(HttpServletRequest request, HttpServletResponse response) {
		AjaxResponse resp = new AjaxResponse();
		
		try {

			String listType = request.getParameter("listType");
			
			List<InvoiceSetting> invoiceSettingList = invoiceSettingService.getInvoiceSettingBySettingTypeId(Long.valueOf(listType));
			
			for(InvoiceSetting is: invoiceSettingList) {
				
				Map<String, String> entry = new HashMap<String, String>();
				
				entry.put("numberSeries", ""+is.getPrefix()+"-"+is.getStartingNumber());
				entry.put("currentNumber", ""+is.getCurrentNumber());
				entry.put("currentSeries", "");
				
				resp.addDataEntry(entry);
			}
			
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);

		} catch (Exception e) {
			LOGGER.error("Error while paging InvoiceSetting", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}

		String returnString = resp.toJSONString();
		
		return returnString;
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/billing/saveinvoiceSetting.html", method=RequestMethod.POST)
	public String saveSalesInvoiceSetting(@Valid @ModelAttribute("invoiceSetting") InvoiceSetting invoiceSetting, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		
		InvoiceSetting dbInvoiceSetting = invoiceSettingService.getInvoiceSettingByPrefix(invoiceSetting.getPrefix());
		
		if(dbInvoiceSetting != null) {
			invoiceSetting = dbInvoiceSetting;
		} else {
			invoiceSetting.setCurrentNumber(invoiceSetting.getStartingNumber());
		}
		
		invoiceSetting.setUpdated(new Date());
		
		invoiceSettingService.saveOrUpdate(invoiceSetting);
		
		return displayInvoiceSetting(model, request, response, locale);
	}
	
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/billing/invoide-list.html", method=RequestMethod.GET)
	public String SalesInvoiceList(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		setMenu(model,request, "sales-invoice");
		return "admin-sales-invoice-listing";
	}
	
	@SuppressWarnings("unchecked")
	@Secured("AUTH")
	@RequestMapping(value = "/admin/billing/invoicepaging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String salesInvoicePaging(HttpServletRequest request, HttpServletResponse response) {
		AjaxResponse resp = new AjaxResponse();
		
		try {
			
			List<SalesInvoice> salesInvoiceList = salesInvoiceService.salesInvoiceList();
			
			int counter = 1;
			
			for(SalesInvoice si: salesInvoiceList) {
				
				@SuppressWarnings("rawtypes")
				Map entry = new HashMap();
				
				entry.put("serialNumber", counter);
				entry.put("invoiceId", si.getId());
				entry.put("invoiceType", si.getInvoiceSeries());
				entry.put("invoiceDate", si.getDate()+"");
				if(si.getCustomer() != null) {
					entry.put("customer", si.getCustomer().getCompany());
				} else {
					entry.put("customer", "");
				}
				entry.put("edit", "Edit");
				resp.addDataEntry(entry);
				
				counter++;
			}
			
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);
			
		} catch(Exception e) {
			LOGGER.error("Error while paging Sales Invoice", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}
		
		String returnString = resp.toJSONString();
		return returnString;
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/billing/invoicecreate.html", method=RequestMethod.GET)
	public String createSalesInvoice(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		SalesInvoice salesInvoice = new SalesInvoice(); 
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		salesInvoice.setInvoiceDate(format.format(new Date()));
		
		return displayInvoice(salesInvoice, model, request, response, locale);
	}
	
	private String displayInvoice(SalesInvoice salesInvoice, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request, "sales-invoice");
		
		Map<String, String> invoiceTypeMap = new HashMap<String, String>();
		List<InvoiceSetting> invoiceSettingList = invoiceSettingService.getInvoiceSettingList();
		for(InvoiceSetting invoiceSetting: invoiceSettingList) {
			if(invoiceSetting.getType().getCode().equals("SI")) {
				invoiceTypeMap.put("siNumberSeries", invoiceSetting.getPrefix()+"-"+invoiceSetting.getCurrentNumber());
				invoiceTypeMap.put("siInvoiceSettingId", invoiceSetting.getId().toString());
			}
			if(invoiceSetting.getType().getCode().equals("CI")) {
				invoiceTypeMap.put("ciNumberSeries", invoiceSetting.getPrefix()+"-"+invoiceSetting.getCurrentNumber());
				invoiceTypeMap.put("ciInvoiceSettingId", invoiceSetting.getId().toString());
			}
		}
		
		if(salesInvoice.getId() != null) {
			
			salesInvoice.setInvoiceDate(DateUtil.formatDate(salesInvoice.getDate())); 
			
			if(salesInvoice.getDueDate() != null) {
				salesInvoice.setTempDueDate(DateUtil.formatDate(salesInvoice.getDueDate()));
			}
			
			List<SalesInvoiceProduct> salesInvoiceProduct = salesInvoiceService.salesInvoiceProductListBySalesInvoiceId(salesInvoice.getId());
			List<SalesInvoiceProductEntity> sipe = SalesInvoiceProductEntity.getProductInfoJson(salesInvoiceProduct);
			salesInvoice.setProductJson(LogicUtils.getJSONString(sipe));
		}
		
		
		model.addAttribute("invoiceTypeMap", invoiceTypeMap);
		model.addAttribute("salesInvoice", salesInvoice);
		
		return "admin-sales-invoice";
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/billing/saveinvoice.html", method=RequestMethod.POST)
	public String saveSalesInvoice(@Valid @ModelAttribute("salesInvoice") SalesInvoice salesInvoice, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request, "sales-invoice");
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		SalesInvoice newSalesInvoice = new SalesInvoice();
		
		boolean saveProductInfo = false;
		
		if(salesInvoice.getId() != null) {
			newSalesInvoice = salesInvoiceService.getSalesInvoiceById(salesInvoice.getId());
			if(newSalesInvoice == null) {
				return "admin-sales-invoice-listing";
			}
			
			
			
		} else {
			newSalesInvoice = salesInvoice;
			saveProductInfo = true;
		}
		
		if(salesInvoice.getAddress() != "") {
			newSalesInvoice.setAddress(salesInvoice.getAddress());
		}
		newSalesInvoice.setCommant(salesInvoice.getCommant()); 
		
		
		
		int currentNumer = 0;
		if(salesInvoice.getId() == null) {
			currentNumer = Integer.parseInt(salesInvoice.getInvoiceSeries().substring(salesInvoice.getInvoiceSeries().length() - 1));
		}
		
		InvoiceSetting invoiceSetting = invoiceSettingService.getInvoiceSetting(salesInvoice.getInvoiceSetting().getId());
		
		if(invoiceSetting.getType().getCode().equals("SI")) {
			if(salesInvoice.getOrder().getId() == null) {
				newSalesInvoice.setOrder(null);
			}
			
			newSalesInvoice.setReceiptMode(null);
			
			Date date = new Date();
			if(!StringUtils.isBlank(salesInvoice.getTempDueDate())) {
				try {
					date = DateUtil.getDate(salesInvoice.getTempDueDate());
					newSalesInvoice.setDueDate(date);
				} catch (Exception e) {
					/*ObjectError error = new ObjectError("dateBusinessSince",messages.getMessage("message.invalid.date", locale));
					result.addError(error);*/
				}
			}
			
		} else {
			
			newSalesInvoice.setReceiptMode(salesInvoice.getReceiptMode());
			
			newSalesInvoice.setCustomer(null);
			newSalesInvoice.setOrder(null);
			newSalesInvoice.setDueDate(null);
		}
		
		invoiceSetting.setCurrentNumber(currentNumer+1);
		
		Date date = new Date();
		if(!StringUtils.isBlank(salesInvoice.getInvoiceDate())) {
			try {
				date = DateUtil.getDate(salesInvoice.getInvoiceDate());
				newSalesInvoice.setDate(date);
			} catch (Exception e) {
				/*ObjectError error = new ObjectError("dateBusinessSince",messages.getMessage("message.invalid.date", locale));
				result.addError(error);*/
			}
		}
		
		newSalesInvoice.setMerchantStore(store);
		newSalesInvoice.setUpdated(new Date());
		try {
			salesInvoiceService.SaveOrUpdate(newSalesInvoice);
			
			if(saveProductInfo) {
				saveSalesInvoiceProductInfo(salesInvoice.getProductJson(), newSalesInvoice);
				invoiceSettingService.saveOrUpdate(invoiceSetting);
			}
			
			model.addAttribute("success","success");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		SalesInvoice dbSalesInvoice = salesInvoiceService.getSalesInvoiceById(newSalesInvoice.getId());
		
		/*dbSalesInvoice.setInvoiceDate(DateUtil.formatDate(dbSalesInvoice.getDate())); 
		
		if(dbSalesInvoice.getDueDate() != null) {
			dbSalesInvoice.setTempDueDate(DateUtil.formatDate(dbSalesInvoice.getDueDate()));
		}
		
		List<SalesInvoiceProduct> salesInvoiceProduct = salesInvoiceService.salesInvoiceProductListBySalesInvoiceId(newSalesInvoice.getId());
		List<SalesInvoiceProductEntity> sipe = SalesInvoiceProductEntity.getProductInfoJson(salesInvoiceProduct);
		dbSalesInvoice.setProductJson(LogicUtils.getJSONString(sipe));*/
		
		return displayInvoice(dbSalesInvoice, model, request, response, locale);
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/billing/editinvoice.html", method=RequestMethod.GET)
	public String editInvoice(@ModelAttribute("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		SalesInvoice dbSalesInvoice = salesInvoiceService.getSalesInvoiceById(id);
		if(dbSalesInvoice == null) {
			return "admin-sales-invoice-listing";
		}
		
		/*dbSalesInvoice.setInvoiceDate(DateUtil.formatDate(dbSalesInvoice.getDate())); 
		
		if(dbSalesInvoice.getDueDate() != null) {
			dbSalesInvoice.setTempDueDate(DateUtil.formatDate(dbSalesInvoice.getDueDate()));
		}
		
		List<SalesInvoiceProduct> salesInvoiceProduct = salesInvoiceService.salesInvoiceProductListBySalesInvoiceId(dbSalesInvoice.getId());
		List<SalesInvoiceProductEntity> sipe = SalesInvoiceProductEntity.getProductInfoJson(salesInvoiceProduct);
		dbSalesInvoice.setProductJson(LogicUtils.getJSONString(sipe));*/
		
		return displayInvoice(dbSalesInvoice, model, request, response, locale);
	}
	
	@RequestMapping(value={"/admin/billing/loadCustomer.html"}, method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String loadCustomerData(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		
		String fieldValue = request.getParameter("fieldValue");
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		List<Customer> customerList = customerService.getCustomerListByCustomerCompany(store, fieldValue);
		
		List<CustomCustomer> customCustomerList = new ArrayList<CustomCustomer>();
		
		for(Customer customer: customerList) {
			CustomCustomer cc = new CustomCustomer();
			
			cc.id = customer.getId();
			cc.name = customer.getCompany();
			
			customCustomerList.add(cc);
		}
		
		return LogicUtils.getJSONString(customCustomerList);
	}
	
	@RequestMapping(value={"/admin/billing/loadProductInfo.html"}, method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String loadProductInfo(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		
		String fieldValue = request.getParameter("fieldValue");
		Language language = (Language)request.getAttribute("LANGUAGE");
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		ProductCriteria criteria = new ProductCriteria();
		
		ProductList productList = productService.listByStore(store, language, criteria);
		
		List<Product> pList = productList.getProducts();
		
		List<SalesInvoiceProductEntity> customProdList = getcustomProdList(pList, fieldValue);
		
		return LogicUtils.getJSONString(customProdList);
	}
	
	private List<SalesInvoiceProductEntity> getcustomProdList(List<Product> productList, String fieldValue) {
		
		List<SalesInvoiceProductEntity> customProdList = SalesInvoiceProductEntity.getSalesInvoiceProductEntity(productList);
		
		List<SalesInvoiceProductEntity> newCustomProdList = new ArrayList<SalesInvoiceProductEntity>(); 
		
		for(SalesInvoiceProductEntity siProd: customProdList) {
			if(siProd.productName.contains(fieldValue)) {
				newCustomProdList.add(siProd);
			}
		}
		
		return newCustomProdList;
	}
	
	private void saveSalesInvoiceProductInfo(String productInfoJson, SalesInvoice invoice) {
		List<SalesInvoiceProductEntity> salesInvoiceProductEntityList = new ArrayList<SalesInvoiceProductEntity>();
		
		SalesInvoiceProductEntity salesInvoiceProductEntity = null;
		
		JsonArray jsonArr = new JsonParser().parse(productInfoJson).getAsJsonArray();
		
		if(jsonArr.size() > 0) {
			for(int i = 0; i < jsonArr.size(); i++) {
				JsonObject combineArrayrObject = jsonArr.get(i).getAsJsonObject();
				if(combineArrayrObject != null) {
					
					salesInvoiceProductEntity = new Gson().fromJson(combineArrayrObject, SalesInvoiceProductEntity.class);
					
					salesInvoiceProductEntityList.add(salesInvoiceProductEntity);
				}
			}
		}
		
		if(salesInvoiceProductEntityList.size() > 0) {
			for(SalesInvoiceProductEntity siProductEntity: salesInvoiceProductEntityList) {
				
				try {
					salesInvoiceService.SaveOrUpdate(siProductEntity.getSalesInvoiceProduct(siProductEntity, invoice));
					
				} catch (ServiceException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
