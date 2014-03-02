package com.salesmanager.web.admin.controller.company;

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

import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.company.model.CompanyCurrencies;
import com.salesmanager.core.business.company.service.CompanyService;
import com.salesmanager.core.business.user.model.User;
import com.salesmanager.core.business.user.service.UserService;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.LabelUtils;
import com.salesmanager.web.utils.UserUtils;

@Controller
public class CurrenciesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrenciesController.class);
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LabelUtils messages;
	
	
	@Secured("SUPERADMIN")
	@RequestMapping(value="/admin/company/currencies.html", method=RequestMethod.GET)
	public String displayAccountingPeriod(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request);
		return "admin-company-currency-list";
	}
	
	
	/**
	 * Displays a list of accountingPeriods that can be managed by superadmin
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Secured("SUPERADMIN")
	@RequestMapping(value = "/admin/currencies/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String pageCurrencies(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();

		String sCurrentUser = request.getRemoteUser();
		
		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			List<CompanyCurrencies> currencies = null;
			
			if(UserUtils.userInGroup(currentUser, Constants.GROUP_SUPERADMIN) ) {
				
				currencies = companyService.listCompanyCurrencies();
				
				for(CompanyCurrencies currency: currencies) {
					@SuppressWarnings("rawtypes")
					Map entry = new HashMap();
					
					entry.put("currencyId", currency.getId());
					entry.put("symbol", currency.getSymbl());
					entry.put("code", currency.getCode());
					entry.put("decimalPlace", currency.getDecimalPosition());
					entry.put("exchangeRate", currency.getExchangeRate());
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
	
	/**
	 * From Currency list
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	@Secured("SUPERADMIN")
	@RequestMapping(value="/admin/company/editCurrency.html", method=RequestMethod.GET)
	public String displayCurrencyEdit(@ModelAttribute("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		return displayUser(id, model, request, response);
	}

	private String displayUser(Long dbCurrencyId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//display menu
		setMenu(model,request);
		
		String returnType = "admin-company-currency";
		
		if(dbCurrencyId != null && dbCurrencyId != 0) {
			CompanyCurrencies dbCurrency = companyService.getByCurrencyId(dbCurrencyId);
			
			if(dbCurrency == null) {
				returnType = "admin-company-currency-list";
			}
			
			model.addAttribute("currency", dbCurrency);
			
		} else {
			returnType = "admin-company-currency";
		}

		return returnType;
	}
	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("company", "company");
		activeMenus.put("company-currencies", "company-currencies");

		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("company");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
	}
	
	@Secured("SUPERADMIN")
	@RequestMapping(value="/admin/company/createCurrencies.html", method=RequestMethod.GET)
	public String displayAccountingPeriodCreate(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return displayUser(Long.getLong("0"), model, request, response);
	}
	
	@Secured("SUPERADMIN")
	@RequestMapping(value="/admin/company/saveCurrency.html", method=RequestMethod.POST)
	public String saveCompanyDetails(@Valid @ModelAttribute("currency") CompanyCurrencies currency, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request);
		CompanyCurrencies newCurrency = new CompanyCurrencies();
		Company sessionCompany = null;
		
		if(currency.getId()!=null) {
			newCurrency = companyService.getByCurrencyId(currency.getId().longValue());
			if(newCurrency == null) {
				return "redirect:/admin/company/currencies.html";
			}
			newCurrency.setUpdated(new Date());
		}
		
		sessionCompany = (Company)request.getAttribute(Constants.ADMIN_COMPANY);
		if(sessionCompany == null) {
			sessionCompany = companyService.getByCode(Company.DEFAULT_ADMIN);
			request.getSession().setAttribute(Constants.ADMIN_COMPANY, sessionCompany);
		}
		
		newCurrency.setCompany(sessionCompany);
		newCurrency.setCode(currency.getCode());
		newCurrency.setSymbl(currency.getSymbl());
		newCurrency.setDecimalPosition(currency.getDecimalPosition());
		newCurrency.setExchangeRate(currency.getExchangeRate());
		
		companyService.saveOrUpdate(newCurrency);
		
		model.addAttribute("success","success");
		model.addAttribute("currency", newCurrency);
		
		return "admin-company-currency";
	}
	
	
}
