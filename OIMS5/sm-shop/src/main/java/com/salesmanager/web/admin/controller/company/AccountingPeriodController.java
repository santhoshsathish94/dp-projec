package com.salesmanager.web.admin.controller.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.salesmanager.core.business.reference.currency.model.Currency;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.reference.zone.model.Zone;
import com.salesmanager.core.business.user.model.User;
import com.salesmanager.core.business.user.service.UserService;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.DateUtil;
import com.salesmanager.web.utils.LabelUtils;
import com.salesmanager.web.utils.UserUtils;



@Controller
public class AccountingPeriodController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountingPeriodController.class);
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LabelUtils messages;
	
	@Secured("SUPERADMIN")
	@RequestMapping(value="/admin/company/acountingPeriods.html", method=RequestMethod.GET)
	public String displayAccountingPeriod(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request);
		return "admin-company-accounting-period-list";
	}
	
	
	/**
	 * Displays a list of accountingPeriods that can be managed by superadmin
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Secured("SUPERADMIN")
	@RequestMapping(value = "/admin/acountingPeriods/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String pageAccountingPeriod(HttpServletRequest request,
			HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();

		String sCurrentUser = request.getRemoteUser();
		
		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			List<AccountingPeriod> accountingPeriods = null;
			
			if(UserUtils.userInGroup(currentUser, Constants.GROUP_SUPERADMIN) ) {
				
				accountingPeriods = companyService.listAccountingPeriod();
				
				SimpleDateFormat sf = new SimpleDateFormat("MMM yyyy");
				
				for(AccountingPeriod accPeriod: accountingPeriods) {
					@SuppressWarnings("rawtypes")
					Map entry = new HashMap();
					
					entry.put("accountingPeriodId", accPeriod.getId());
					
					entry.put("accPeriod", sf.format(accPeriod.getFromDate()) +" - " + sf.format(accPeriod.getToDate()));
					if(accPeriod.isStatus())
						entry.put("status", "This period is open");
					else 
						entry.put("status", "This period is closed.");
					if(!accPeriod.isSetAsDefault())
						entry.put("default", "Set as default");
					else
						entry.put("default", "Default set");
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
	
	/**
	 * From Accounting Period list
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	@Secured("SUPERADMIN")
	@RequestMapping(value="/admin/company/editAccountingPeriod.html", method=RequestMethod.GET)
	public String displayAccountingPeriodEdit(@ModelAttribute("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		return displayUser(id, model, request, response);
	}

	private String displayUser(Long dbAccountingPeriodId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//display menu
		setMenu(model,request);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		
		String returnType = "admin-company-accounting-period";
		
		if(dbAccountingPeriodId != null && dbAccountingPeriodId != 0) {
			AccountingPeriod dbAccountingPeriod = companyService.getByAccountingPeriodId(dbAccountingPeriodId);
			
			if(dbAccountingPeriod == null) {
				returnType = "admin-company-accounting-period-list";
			}
			
			dbAccountingPeriod.setFromSDate(dateFormat.format(dbAccountingPeriod.getFromDate()));
			
			dbAccountingPeriod.setToSDate(dateFormat.format(dbAccountingPeriod.getToDate()));
			
			model.addAttribute("accountingPeriod", dbAccountingPeriod);
			
		} else {
			returnType = "admin-company-accounting-period";
		}

		return returnType;
	}
	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("company", "company");
		activeMenus.put("company-accounting", "company-accounting");

		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("company");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
	}
	
	@Secured("SUPERADMIN")
	@RequestMapping(value="/admin/company/createAccountingPeriod.html", method=RequestMethod.GET)
	public String displayAccountingPeriodCreate(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return displayUser(Long.getLong("0"), model, request, response);
	}
	
	@Secured("SUPERADMIN")
	@RequestMapping(value="/admin/company/saveAccountingPeriod.html", method=RequestMethod.POST)
	public String saveCompanyDetails(@Valid @ModelAttribute("accountingPeriod") AccountingPeriod accountingPeriod, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request);
		boolean ispresent = false;
		AccountingPeriod accPeriod = new AccountingPeriod();
		Company sessionCompany = null;
		
		if(accountingPeriod.getId()!=null) {
			accPeriod = companyService.getByAccountingPeriodId(accountingPeriod.getId().longValue());
			if(accPeriod == null) {
				return "redirect:/admin/company/acountingPeriods.html";
			}
			ispresent = true;
		} else {
			sessionCompany = (Company)request.getAttribute(Constants.ADMIN_COMPANY);
			if(sessionCompany == null) {
				sessionCompany = companyService.getByCode(Company.DEFAULT_ADMIN);
				request.getSession().setAttribute(Constants.ADMIN_COMPANY, sessionCompany);
			}
		}
		
		Date date = new Date();
		if(!StringUtils.isBlank(accountingPeriod.getFromSDate())) {
			try {
				date = DateUtil.getDate(accountingPeriod.getFromSDate());
				accPeriod.setFromDate(date);
			} catch (Exception e) {
				ObjectError error = new ObjectError("fromDate",messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
		}
		
		date = new Date();
		if(!StringUtils.isBlank(accountingPeriod.getToSDate())) {
			try {
				date = DateUtil.getDate(accountingPeriod.getToSDate());
				accPeriod.setToDate(date);
			} catch (Exception e) {
				ObjectError error = new ObjectError("toDate",messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
		}
		
		accPeriod.setStatus(accountingPeriod.isStatus());

		accPeriod.setSetAsDefault(accountingPeriod.isSetAsDefault());
		
		if(ispresent) {
			accPeriod.setUpdated(new Date());
		}
		
		companyService.saveOrUpdate(sessionCompany, accPeriod);
		
		model.addAttribute("success","success");
		model.addAttribute("accountingPeriod", accPeriod);
		
		return "admin-company-accounting-period";
	}
}
