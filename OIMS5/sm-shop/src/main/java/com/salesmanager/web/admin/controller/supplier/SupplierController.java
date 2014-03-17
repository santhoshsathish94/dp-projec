package com.salesmanager.web.admin.controller.supplier;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import com.salesmanager.core.business.reference.country.service.CountryService;
import com.salesmanager.core.business.reference.currency.model.Currency;
import com.salesmanager.core.business.reference.currency.service.CurrencyService;
import com.salesmanager.core.business.reference.zone.model.Zone;
import com.salesmanager.core.business.reference.zone.service.ZoneService;
import com.salesmanager.core.business.supplier.model.Supplier;
import com.salesmanager.core.business.supplier.service.SupplierService;
import com.salesmanager.core.business.user.model.User;
import com.salesmanager.core.business.user.service.UserService;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
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
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/supplier/suppliers.html", method=RequestMethod.GET)
	public String suppliersList(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		setMenu(model,request);
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
		return displaySupplier(Long.valueOf("0"), model, request, response);
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/supplier/editsupplier.html", method=RequestMethod.GET)
	public String editSupplier(@ModelAttribute("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		return displaySupplier(id, model, request, response);
	}
	
	private String displaySupplier(Long dbSupplierId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//display menu
		setMenu(model,request);
		String returnType = "admin-supplier";
		Supplier dbSupplier = null;
		if(dbSupplierId != null && dbSupplierId != 0) {
			dbSupplier = supplierService.getSupplier(dbSupplierId);
			
			if(dbSupplier == null) {
				returnType = "admin-supplier-list";
			}
			
		} else {
			returnType = "admin-supplier";
			dbSupplier = new Supplier();
		}
		
		List<Currency> currencies = currencyService.list();
		model.addAttribute("currencies",currencies);
		
		model.addAttribute("supplier", dbSupplier);
		
		return returnType;
	}
	
	@Secured("AUTH")
	@RequestMapping(value="/admin/company/saveSupplier.html", method=RequestMethod.POST)
	public String saveAccountingPeriod(@Valid @ModelAttribute("supplier") Supplier supplier, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request);
		
		Supplier newSupplier = new Supplier();
		
		if(supplier.getId() != null) {
			newSupplier = supplierService.getById(supplier.getId());
			
			if(newSupplier == null) {
				return "admin-supplier-list";
			}
		}
		
		Zone zone = supplier.getZone();
		if(zone != null) {
			zone = zoneService.getByCode(zone.getCode());
		}
		
		Currency currency = supplier.getCurrency();
		if(currency != null) {
			currency = currencyService.getById(currency.getId());
		}
		
		
		supplier.setZone(zone);
		supplier.setCurrency(currency);
		supplier.setUpdated(new Date());
		
		supplierService.saveOrUpdate(supplier);
		
		model.addAttribute("success","success");
		model.addAttribute("supplier", supplier);
		
		return "admin-supplier";
	}
	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("supplier", "supplier");
		activeMenus.put("supplier", "supplier");

		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("supplier");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
	}
}
