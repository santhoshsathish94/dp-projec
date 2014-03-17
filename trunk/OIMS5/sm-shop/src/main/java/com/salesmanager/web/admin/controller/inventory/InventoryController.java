package com.salesmanager.web.admin.controller.inventory;

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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salesmanager.core.business.company.model.AccountingPeriod;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.company.service.CompanyService;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.country.service.CountryService;
import com.salesmanager.core.business.reference.currency.model.Currency;
import com.salesmanager.core.business.reference.currency.service.CurrencyService;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.reference.language.service.LanguageService;
import com.salesmanager.core.business.reference.zone.model.Zone;
import com.salesmanager.core.business.reference.zone.service.ZoneService;
import com.salesmanager.core.business.system.service.EmailService;
import com.salesmanager.core.business.user.model.User;
import com.salesmanager.core.business.user.service.UserService;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.DateUtil;
import com.salesmanager.web.utils.LabelUtils;
import com.salesmanager.web.utils.UserUtils;

@Controller
public class InventoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	CompanyService companyService;

	@Autowired
	CountryService countryService;

	@Autowired
	ZoneService zoneService;

	@Autowired
	LanguageService languageService;

	@Autowired
	CurrencyService currencyService;

	@Autowired
	UserService userService;

	@Autowired
	LabelUtils messages;

	@Autowired
	EmailService emailService;

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createstock.html", method = RequestMethod.GET)
	public String createStock(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//display menu
		setMenu(model,request, "openingStock");
		return "admin-inventory";
	}
	
	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/stocks.html", method = RequestMethod.GET)
	public String displayStocks(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		//display menu
		setMenu(model,request, "openingStock");
		return "admin-inventory";
	}
	
	

	private void setMenu(Model model, HttpServletRequest request, String setActiveMenus) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("inventoryManagement", "inventoryManagement");
		activeMenus.put("stock-list", "company");
		activeMenus.put("purchase-entry-list", "purchase-entry-list");
		activeMenus.put("purchase-entry-create", "purchase-entry-create");
		activeMenus.put("purchase-return-debit-note-list", "purchase-return-debit-note-list");
		activeMenus.put("purchase-return-debit-note-create", "purchase-return-debit-note-create");
		activeMenus.put("debit-note-other-list", "debit-note-other-list");
		activeMenus.put("debit-note-other-create", "debit-note-other-create");
		activeMenus.put("branch-transfer-list", "branch-transfer-list");
		activeMenus.put("branch-transfer-create", "branch-transfer-create");
		activeMenus.put(setActiveMenus, setActiveMenus);

		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("inventoryManagement");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
	}
	
}
