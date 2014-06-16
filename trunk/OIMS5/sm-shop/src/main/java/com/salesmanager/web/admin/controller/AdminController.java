package com.salesmanager.web.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.salesmanager.core.business.common.model.CriteriaOrderBy;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.company.service.CompanyService;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.merchant.service.MerchantStoreService;
import com.salesmanager.core.business.order.model.OrderCriteria;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.country.service.CountryService;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.user.model.Group;
import com.salesmanager.core.business.user.model.GroupType;
import com.salesmanager.core.business.user.model.User;
import com.salesmanager.core.business.user.service.UserService;
import com.salesmanager.web.admin.security.SecurityQuestion;
import com.salesmanager.web.constants.Constants;

@Controller
public class AdminController {
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	private MerchantStoreService merchantService;


	
	@RequestMapping(value={"/admin/home.html","/admin/","/admin"}, method=RequestMethod.GET)
	public String displayDashboard(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Language language = (Language)request.getAttribute("LANGUAGE");
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("home", "home");
		
		model.addAttribute("activeMenus",activeMenus);
		
		//get store information
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		Map<String,Country> countries = countryService.getCountriesMap(language);
		
		Country storeCountry = store.getCountry();
		Country country = countries.get(storeCountry.getIsoCode());
		
		String sCurrentUser = request.getRemoteUser();
		User currentUser = userService.getByUserName(sCurrentUser);
		
		model.addAttribute("store", store);
		model.addAttribute("country", country);
		model.addAttribute("countries", countries);
		model.addAttribute("user", currentUser);
		model.addAttribute("merchantStores", currentUser.getMerchantStores());
		
		//get last 10 orders
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.setMaxCount(10);
		orderCriteria.setOrderBy(CriteriaOrderBy.DESC);
		
		Company company = companyService.getByCode(Company.DEFAULT_ADMIN);
		
		model.addAttribute("company", company);
		
		return ControllerConstants.Tiles.adminDashboard;
	}
	
	/**
	 * From user profile
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	@Secured("AUTH")
	@RequestMapping(value = "/admin/setUserStore.html", method = RequestMethod.POST)
	public String displayUserEdit(@ModelAttribute("user") User user,Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		System.out.println("=============================getAdminName>>>>>>>>>"+user.getAdminName()+"------"+	user.getMerchantStore().getId());
		String userName = request.getRemoteUser();
		User user1 = userService.getByUserName(userName);
		
		MerchantStore store = merchantService.getById(user.getMerchantStore().getId());
		user.setMerchantStore(store);
		user1.setMerchantStore(store);
		userService.saveOrUpdate(user1);
		request.getSession().setAttribute(Constants.ADMIN_STORE, store);
		return "redirect:/admin/home.html";
		//return ControllerConstants.Tiles.adminDashboard;

	}
	


}
