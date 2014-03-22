package com.salesmanager.web.admin.controller.supplier;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.service.ProductService;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.company.service.CompanyService;
import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.customer.service.CustomerService;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.country.service.CountryService;
import com.salesmanager.core.business.reference.currency.model.Currency;
import com.salesmanager.core.business.reference.currency.service.CurrencyService;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.reference.language.service.LanguageService;
import com.salesmanager.core.business.reference.zone.model.Zone;
import com.salesmanager.core.business.reference.zone.service.ZoneService;
import com.salesmanager.core.business.supplier.model.PartyItemDefaultMargin;
import com.salesmanager.core.business.supplier.model.Supplier;
import com.salesmanager.core.business.supplier.service.SupplierService;
import com.salesmanager.core.business.user.model.User;
import com.salesmanager.core.business.user.service.UserService;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.LabelUtils;
import com.salesmanager.web.utils.UserUtils;

@Controller
public class SupplierController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	ZoneService zoneService;
	
	@Autowired
	CurrencyService currencyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SupplierService supplierService;

	@Autowired
	CustomerService customerDetailsService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	LabelUtils messages;

	@Autowired
	LanguageService languageService;
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/supplier/suppliers.html", method=RequestMethod.GET)
	public String suppliersList(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		setMenu(model,request, "supplier-info");
		return "admin-supplier-list";
	}
	

	@SuppressWarnings("unchecked")
	@Secured("AUTH")
	@RequestMapping(value = "/admin/supplier/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String supplierPaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();

		String sCurrentUser = request.getRemoteUser();
		
		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			
			List<Supplier> suppliers = null;
			
			if(UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN) ) {
				
				suppliers = supplierService.getSupplierList();
				
				for(Supplier supplier: suppliers) {

					@SuppressWarnings("rawtypes")
					Map entry = new HashMap();
					
					entry.put("supplierId", supplier.getId());
					entry.put("supplierName", supplier.getSupplierName());
					entry.put("openingBalance", supplier.getOpeningBalance());
					entry.put("edit", "Edit");
					resp.addDataEntry(entry);
				}
				
			} else {
				LOGGER.error("User not found.");
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			}
			 
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);

		} catch (Exception e) {
			LOGGER.error("Error while paging products", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}

		String returnString = resp.toJSONString();

		return returnString;
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/supplier/createsupplier.html", method=RequestMethod.GET)
	public String createSupplier(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Supplier newSupplier = new Supplier();
		newSupplier.setCountry(countryService.getByCode("IN"));
		newSupplier.setCurrency(currencyService.getByCode("INR"));
		newSupplier.setZone(zoneService.getByCode("MAH"));
		
		return displaySupplier(newSupplier, model, request, response);
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/supplier/editsupplier.html", method=RequestMethod.GET)
	public String editSupplier(@ModelAttribute("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		Supplier dbSupplier = supplierService.getSupplier(id);
		
		if(dbSupplier == null) {
			return "admin-supplier-list";
		}
		
		return displaySupplier(dbSupplier, model, request, response);
	}
	
	private String displaySupplier(Supplier supplier, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//display menu
		setMenu(model,request, "supplier-info");
		
		Language language = languageService.getByCode("en");
		
		//get countries
		List<Country> countries = countryService.getCountries(language);
		model.addAttribute("countries", countries);
		
		List<Currency> currencies = currencyService.list();
		model.addAttribute("currencies",currencies);
		
		model.addAttribute("supplier", supplier);
		
		return "admin-supplier";
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/company/saveSupplier.html", method=RequestMethod.POST)
	public String saveSupplier(@Valid @ModelAttribute("supplier") Supplier supplier, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request, "supplier-info");
		
		Supplier newSupplier = new Supplier();
		
		if(supplier.getId() != null) {
			newSupplier = supplierService.getById(supplier.getId());
			
			if(newSupplier == null) {
				return "admin-supplier-list";
			}
		}
		
		Currency currency = supplier.getCurrency();
		if(currency != null) {
			currency = currencyService.getById(currency.getId());
		}
		
		Country country = supplier.getCountry();
		country = countryService.getByCode(country.getIsoCode());
		
		Zone zone = supplier.getZone();
		if(zone != null) {
			zone = zoneService.getByCode(zone.getCode());
		}
		
		
		supplier.setCountry(country);
		supplier.setZone(zone);
		supplier.setCurrency(currency);
		supplier.setUpdated(new Date());
		
		supplierService.saveOrUpdate(supplier);
		
		Supplier savedSupplier = supplierService.getById(supplier.getId());
		
		model.addAttribute("success","success");
		model.addAttribute("supplier", savedSupplier);
		
		return displaySupplier(savedSupplier, model, request, response);
	}
	
	private void setMenu(Model model, HttpServletRequest request, String selectedMenu) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("supplier", "supplier");
		activeMenus.put(selectedMenu, selectedMenu);

		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("supplier");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
	}
	
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/supplier/saleschanneldefaultmargins.html", method=RequestMethod.GET)
	public String partyItemDefaultMarginList(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		setMenu(model,request, "sales-channel-default-margin");
		return "admin-pt-default-margin-list";
	}
	
	@SuppressWarnings("unchecked")
	@Secured("AUTH")
	@RequestMapping(value = "/admin/supplier/partyitemdefaultmarginpaging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String partyItemDefaultMarginPaging(HttpServletRequest request, HttpServletResponse response) {
		AjaxResponse resp = new AjaxResponse();
		String sCurrentUser = request.getRemoteUser();
		
		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			List<PartyItemDefaultMargin> partyItemDefaultMarginList = null;

			if(UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN) ) {
				
				partyItemDefaultMarginList = supplierService.getPartyItemDefaultMarginList();
				int serialNumber = 0;
				for(PartyItemDefaultMargin piDefaultMargin: partyItemDefaultMarginList) {
					
					serialNumber++;
					@SuppressWarnings("rawtypes")
					Map entry = new HashMap();
					
					entry.put("partyItemDefaultMarginId", piDefaultMargin.getId());
					entry.put("serialNumber", serialNumber);
					entry.put("partyName", piDefaultMargin.getCustomer().getCompany());
					entry.put("itemName", piDefaultMargin.getProduct().getSku());
					entry.put("companyName", piDefaultMargin.getCompany().getCompanyDisplayName());
					entry.put("cdField", piDefaultMargin.getCdField());
					entry.put("addField", piDefaultMargin.getAddField());
					entry.put("tdField", piDefaultMargin.getTdField());
					entry.put("rateField", piDefaultMargin.getRateField());
					entry.put("edit", "Edit");
					resp.addDataEntry(entry);
				}
				
			} else {
				LOGGER.error("User not found.");
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			}
			 
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);

		} catch (Exception e) {
			LOGGER.error("Error while paging products", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}

		String returnString = resp.toJSONString();
		
		return returnString;
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/supplier/createsaleschanneldefaultmargin.html", method=RequestMethod.GET)
	public String createSalesChannedDefaultMargin(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return displaySalesChannedDefaultMargin(Long.valueOf("0"), model, request, response);
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/supplier/editpartyitemdefaultmargin.html", method=RequestMethod.GET)
	public String editSalesChannedDefaultMargin(@ModelAttribute("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		return displaySalesChannedDefaultMargin(id, model, request, response);
	}
	
	private String displaySalesChannedDefaultMargin(Long dbSalesChannedDefaultMargin, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//display menu
		setMenu(model,request, "sales-channel-default-margin");
		String returnType = "admin-pt-default-margin";
		PartyItemDefaultMargin dbPartyItemDefaultMargin = null;
		if(dbSalesChannedDefaultMargin != null && dbSalesChannedDefaultMargin != 0) {
			dbPartyItemDefaultMargin = supplierService.getPartyItemDefaultMargin(dbSalesChannedDefaultMargin);
			
			if(dbPartyItemDefaultMargin == null) {
				returnType = "admin-pt-default-margin-list";
			}
			
			dbPartyItemDefaultMargin.setCompanyName(dbPartyItemDefaultMargin.getCompany().getCompanyDisplayName());
			dbPartyItemDefaultMargin.setProductName(dbPartyItemDefaultMargin.getProduct().getSku());
			dbPartyItemDefaultMargin.setCustomerAccountName(dbPartyItemDefaultMargin.getCustomer().getCompany());
		} else {
			returnType = "admin-pt-default-margin";
			dbPartyItemDefaultMargin = new PartyItemDefaultMargin();
		}
		
		
		model.addAttribute("partyItemDefaultMargin", dbPartyItemDefaultMargin);
		
		return returnType;
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/supplier/savesaleschanneldefaultmargin.html", method=RequestMethod.POST)
	public String saveSalesChannedDefaultMargin(@Valid @ModelAttribute("partyItemDefaultMargin") PartyItemDefaultMargin partyItemDefaultMargin, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request, "sales-channel-default-margin");
		
		if(StringUtils.isBlank(partyItemDefaultMargin.getCustomerAccountName())) {
			ObjectError error = new ObjectError("customerAccountName",messages.getMessage("validation.supplier.defaultmargin.customer.account.message", locale));
			result.addError(error);
		}
		
		if(StringUtils.isBlank(partyItemDefaultMargin.getProductName())) {
			ObjectError error = new ObjectError("productName",messages.getMessage("validation.supplier.defaultmargin.item.name.message", locale));
			result.addError(error);
		}
		
		if(StringUtils.isBlank(partyItemDefaultMargin.getCompanyName())) {
			ObjectError error = new ObjectError("companyName",messages.getMessage("validation.supplier.defaultmargin.company.message", locale));
			result.addError(error);
		}
		
		if (result.hasErrors()) {
			return "admin-pt-default-margin";
		}
		
		PartyItemDefaultMargin newpartyItemDefaultMargin = new PartyItemDefaultMargin();
		
		if(partyItemDefaultMargin.getId() != null) {
			newpartyItemDefaultMargin = supplierService.getPartyItemDefaultMargin(partyItemDefaultMargin.getId());
			
			if(newpartyItemDefaultMargin == null) {
				return "admin-pt-default-margin-list";
			}
			
			newpartyItemDefaultMargin.setCdField(partyItemDefaultMargin.getCdField());
			newpartyItemDefaultMargin.setAddField(partyItemDefaultMargin.getAddField());
			newpartyItemDefaultMargin.setTdField(partyItemDefaultMargin.getTdField());
			newpartyItemDefaultMargin.setRateField(partyItemDefaultMargin.getRateField());
			
			newpartyItemDefaultMargin.setUpdated(new Date());
		}
		
		Customer customer = null;
		if(!StringUtils.isBlank(partyItemDefaultMargin.getCustomerAccountName())) {
			customer = customerDetailsService.getByCustomerCompany(partyItemDefaultMargin.getCustomerAccountName());
		}
		
		Product product = null;
		if(!StringUtils.isBlank(partyItemDefaultMargin.getProductName())) {
			product = productService.getProductBySKU(partyItemDefaultMargin.getProductName());
		}
		
		Company company = null;
		if(!StringUtils.isBlank(partyItemDefaultMargin.getCompanyName())) {
			company = companyService.getCompanyByCompanyDisplayName(partyItemDefaultMargin.getCompanyName());
		}
		
		newpartyItemDefaultMargin.setCustomer(customer);
		newpartyItemDefaultMargin.setProduct(product);
		newpartyItemDefaultMargin.setCompany(company);
		
		supplierService.saveOrUpdate(newpartyItemDefaultMargin);
		
		model.addAttribute("success","success");
		model.addAttribute("supplier", newpartyItemDefaultMargin);
		
		return "admin-pt-default-margin";
	}
	
	@RequestMapping(value={"/admin/supplier/loadAjaxData.html"}, method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String loadAjaxData(HttpServletRequest request, HttpServletResponse response) {
		
		String fieldValue = request.getParameter("fieldValue");
		String searchFor = request.getParameter("searchFor");
		List<String> dataList = null;
		String returnString = "";
		try {
			if(StringUtils.equals("customerAccount", searchFor))
				dataList = customerDetailsService.getCustomerListByCustomerCompany(fieldValue);
				
			if(StringUtils.endsWithIgnoreCase("item", searchFor))
				dataList = productService.getProductListBySKU(fieldValue);
			
			if(StringUtils.endsWithIgnoreCase("company", searchFor))
				dataList = companyService.getCompanyNameListByDisplayName(fieldValue);
			
			returnString = getJsonString(dataList);
			
		} catch (Exception e) {
			LOGGER.error("loadAjaxData()", e);
		}
		
		return returnString;
	}
	
	private String getJsonString(List<String> stringList) {
		StringBuffer sb = new StringBuffer("[");
		for(int i=0; i < stringList.size(); i++) {
			if(i < (stringList.size() - 1)) {
				sb.append("\"" + stringList.get(i) + "\"").append(",");
			} else {
				sb.append("\"" + stringList.get(i) + "\"");
			}
		}
		sb.append("]");
		
		return sb.toString();
	}
	
}
