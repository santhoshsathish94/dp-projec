package com.salesmanager.web.admin.controller.company;

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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.DateUtil;
import com.salesmanager.web.utils.LabelUtils;


@Controller
public class CompanyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);
	
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
	
	@Secured("SUPERADMIN")
	@RequestMapping(value="/admin/company.html", method=RequestMethod.GET)
	public String displayCompany(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request);
		Company sessionCompany = (Company)request.getAttribute(Constants.ADMIN_COMPANY);
		
		Company company = null;
		
		if(sessionCompany == null) {
			company = companyService.getByCode(Company.DEFAULT_ADMIN);
			request.getSession().setAttribute(Constants.ADMIN_COMPANY, company);
		}
		request.setAttribute(Constants.ADMIN_COMPANY, company);
		
		if(company == null) {
			company = new Company();
			
			company.setCompanyCountry(countryService.getByCode("IN"));
			company.setCompanyCurrency(currencyService.getByCode("INR"));
			company.setCompanyZone(zoneService.getByCode("MAH"));
			company.setCode("SUPERADMIN");
			/*company.setLanguages(languageService.getLanguages());
			company.setDefaultCompanyLanguage(userService.getByUserName(request.getRemoteUser()).getDefaultLanguage());*/
		}
		
		return displayCompanyDetails(company, model, request, response, locale);
	}
	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("company", "company");
		activeMenus.put("company-info", "company-info");

		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("company");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
	}

	
	private String displayCompanyDetails(Company company, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request);
		Language language = (Language)request.getAttribute("LANGUAGE");
		List<Language> languages = languageService.getLanguages();
		List<Currency> currencies = currencyService.list();
		Date dt = company.getCompanyInBusinessSince();
		if(dt!=null) {
			company.setDateBusinessSince(DateUtil.formatDate(dt));
		} else {
			company.setDateBusinessSince(DateUtil.formatDate(new Date()));
		}
		
		//get countries
		List<Country> countries = countryService.getCountries(language);
		
		//display menu
		model.addAttribute("countries", countries);
		model.addAttribute("languages",languages);
		model.addAttribute("currencies",currencies);
		
		model.addAttribute("company", company);
		
		return "admin-company";
	}
	
	@Secured("SUPERADMIN")
	@RequestMapping(value="/admin/company/save.html", method=RequestMethod.POST)
	public String saveCompanyDetails(@Valid @ModelAttribute("company") Company company, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		setMenu(model,request);
		Company sessionCompany = (Company)request.getSession().getAttribute(Constants.ADMIN_COMPANY);

		if(company.getId()!=null) {
			if(company.getId().intValue() != sessionCompany.getId().intValue()) {
				return "redirect:/admin/company.html";
			}
		}
		
		Date date = new Date();
		if(!StringUtils.isBlank(company.getDateBusinessSince())) {
			try {
				date = DateUtil.getDate(company.getDateBusinessSince());
				company.setCompanyInBusinessSince(date);
			} catch (Exception e) {
				ObjectError error = new ObjectError("dateBusinessSince",messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
		}
		
		List<Currency> currencies = currencyService.list();
		
		
		Language language = (Language)request.getAttribute("LANGUAGE");
		List<Language> languages = languageService.getLanguages();
		
		//get countries
		List<Country> countries = countryService.getCountries(language);
		
		model.addAttribute("countries", countries);
		model.addAttribute("languages",languages);
		model.addAttribute("currencies",currencies);
		
		
		Country c = company.getCompanyCountry();
		List<Zone> zonesList = zoneService.getZones(c, language);
		
		if((zonesList==null || zonesList.size()==0) && StringUtils.isBlank(company.getCompanyStateProvince())) {
			
			ObjectError error = new ObjectError("zone.code",messages.getMessage("company.zone.invalid", locale));
			result.addError(error);//company.zone.invalid
			
		}

		if (result.hasErrors()) {
			return "admin-company";
		}
		
		//get country
		Country country = company.getCompanyCountry();
		country = countryService.getByCode(country.getIsoCode());
		Zone zone = company.getCompanyZone();
		if(zone!=null) {
			zone = zoneService.getByCode(zone.getCode());
		}
		Currency currency = company.getCompanyCurrency();
		if(currency != null)
			currency = currencyService.getById(currency.getId());

		/*List<Language> supportedLanguages = company.getLanguages();
		List<Language> supportedLanguagesList = new ArrayList<Language>();
		Map<String,Language> languagesMap = languageService.getLanguagesMap();
		for(Language lang : supportedLanguages) {
			
			Language l = languagesMap.get(lang.getCode());
			if(l!=null) {
				supportedLanguagesList.add(l);
			}
			
		}*/
		
		/*Language defaultLanguage = company.getDefaultCompanyLanguage();
		defaultLanguage = languageService.getById(defaultLanguage.getId());
		if(defaultLanguage!=null) {
			company.setDefaultCompanyLanguage(defaultLanguage);
		}*/
		
		company.setCompanyCountry(country);
		company.setCompanyZone(zone);
		company.setCompanyCurrency(currency);
		/*company.setDefaultCompanyLanguage(defaultLanguage);
		company.setLanguages(supportedLanguagesList);
		company.setLanguages(supportedLanguagesList);*/

		
		companyService.saveOrUpdate(company);
		
		/*if(!company.getCode().equals(sessionCompany.getCode())) {//create store
			//send email
			
			try {


				Map<String, String> templateTokens = EmailUtils.createEmailObjectsMap(request, company, messages, storeLocale);
				templateTokens.put(EmailConstants.EMAIL_NEW_STORE_TEXT, messages.getMessage("email.newstore.text", storeLocale));
				templateTokens.put(EmailConstants.EMAIL_STORE_NAME, messages.getMessage("email.newstore.name",new String[]{company.getStorename()},storeLocale));
				templateTokens.put(EmailConstants.EMAIL_ADMIN_STORE_INFO_LABEL, messages.getMessage("email.newstore.info",storeLocale));

				templateTokens.put(EmailConstants.EMAIL_ADMIN_URL_LABEL, messages.getMessage("label.adminurl",storeLocale));
				templateTokens.put(EmailConstants.EMAIL_ADMIN_URL, FilePathUtils.buildAdminUri(company, request));
	
				
				Email email = new Email();
				email.setFrom(company.getStorename());
				email.setFromEmail(company.getStoreEmailAddress());
				email.setSubject(messages.getMessage("email.newstore.title",storeLocale));
				email.setTo(company.getStoreEmailAddress());
				email.setTemplateName(NEW_STORE_TMPL);
				email.setTemplateTokens(templateTokens);
	
	
				
				emailService.sendHtmlEmail(company, email);
			
			} catch (Exception e) {
				LOGGER.error("Cannot send email to user",e);
			}
			
		}*/

		sessionCompany = companyService.getCompany(company.getId());
		
		
		//update session store
		request.getSession().setAttribute(Constants.ADMIN_COMPANY, sessionCompany);


		model.addAttribute("success","success");
		model.addAttribute("company", company);

		
		return "admin-company";
	}
	
}
