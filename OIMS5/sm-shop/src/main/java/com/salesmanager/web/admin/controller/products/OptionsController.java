package com.salesmanager.web.admin.controller.products;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salesmanager.core.business.catalog.product.model.attribute.ProductOption;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductOptionDescription;
import com.salesmanager.core.business.catalog.product.service.attribute.ProductOptionService;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.reference.language.service.LanguageService;
import com.salesmanager.core.business.service.utils.LogicUtils;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.LabelUtils;

@Controller
public class OptionsController {
	
	@Autowired
	LanguageService languageService;
	
	@Autowired
	ProductOptionService productOptionService;
	
	@Autowired
	LabelUtils messages;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OptionsController.class);
	
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/options/options.html", method=RequestMethod.GET)
	public String displayOptions(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		setMenu(model,request);



		
		return "catalogue-options-list";
		
		
		
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/options/editOption.html", method=RequestMethod.GET)
	public String displayOptionEdit(@RequestParam("id") long optionId, HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayOption(optionId,request,response,model,locale);
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/options/createOption.html", method=RequestMethod.GET)
	public String displayOption(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayOption(null,request,response,model,locale);
	}
	
	private String displayOption(Long optionId, HttpServletRequest request, HttpServletResponse response,Model model,Locale locale) throws Exception {

		
		this.setMenu(model, request);
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		List<Language> languages = store.getLanguages();

		Set<ProductOptionDescription> descriptions = new HashSet<ProductOptionDescription>();
		
		ProductOption option = new ProductOption();
		
		if(optionId!=null && optionId!=0) {//edit mode
			
			
			option = productOptionService.getById(optionId);
			
			
			if(option==null) {
				return "redirect:/admin/options/options.html";
			}
			
			Set<ProductOptionDescription> optionDescriptions = option.getDescriptions();
			
			
			
			for(Language l : languages) {
			
				ProductOptionDescription optionDescription = null;
				
				if(optionDescriptions!=null) {
					
					for(ProductOptionDescription description : optionDescriptions) {
						
						String code = description.getLanguage().getCode();
						if(code.equals(l.getCode())) {
							optionDescription = description;
						}
						
					}
					
				}
				
				if(optionDescription==null) {
					optionDescription = new ProductOptionDescription();
					optionDescription.setLanguage(l);
				}
				
				descriptions.add(optionDescription);
			
			}

		} else {
			
			for(Language l : languages) {
				
				ProductOptionDescription desc = new ProductOptionDescription();
				desc.setLanguage(l);
				descriptions.add(desc);
				
			}
			
		}
		

		option.setDescriptions(descriptions);
		model.addAttribute("option", option);
		return "catalogue-options-details";
		
		
	}
		
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/options/save.html", method=RequestMethod.POST)
	public String saveOption(@Valid @ModelAttribute("option") ProductOption option, BindingResult result, Model model, HttpServletRequest request, Locale locale) throws Exception {
		

		//display menu
		setMenu(model,request);
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		ProductOption dbEntity =	null;	

		if(option.getId() != null && option.getId() >0) { //edit entry
			//get from DB
			dbEntity = productOptionService.getById(option.getId());
			
			if(dbEntity==null) {
				return "redirect:/admin/options/options.html";
			}
		}
		
		//validate if it contains an existing code
		ProductOption byCode = productOptionService.getByCode(store, option.getCode());
		if(byCode!=null) {
			ObjectError error = new ObjectError("code",messages.getMessage("message.code.exist", locale));
			result.addError(error);
		}

			
		Map<String,Language> langs = languageService.getLanguagesMap();
			

		List<ProductOptionDescription> descriptions = option.getDescriptionsList();
		
		if(descriptions!=null) {
				
				for(ProductOptionDescription description : descriptions) {
					
					String code = description.getLanguage().getCode();
					Language l = langs.get(code);
					description.setLanguage(l);
					description.setProductOption(option);
	
				}
				
		}
			
		option.setDescriptions(new HashSet<ProductOptionDescription>(descriptions));
		option.setMerchantStore(store);

		
		if (result.hasErrors()) {
			return "catalogue-options-details";
		}
		

		
		
		productOptionService.saveOrUpdate(option);


		

		model.addAttribute("success","success");
		return "catalogue-options-details";
	}

	
	
	@SuppressWarnings("unchecked")
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/options/paging.html", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String pageOptions(HttpServletRequest request, HttpServletResponse response) {
		
		String optionName = request.getParameter("name");


		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			
			Language language = (Language)request.getAttribute("LANGUAGE");	
		
			MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
			
			List<ProductOption> options = null;
					
			if(!StringUtils.isBlank(optionName)) {
				
				options = productOptionService.getByName(store, optionName, language);
				
			} else {
				
				options = productOptionService.listByStore(store, language);
				
			}
					
					

			for(ProductOption option : options) {
				
				@SuppressWarnings("rawtypes")
				Map entry = new HashMap();
				entry.put("optionId", option.getId());
				entry.put("display", option.isReadOnly());
				ProductOptionDescription description = option.getDescriptions().iterator().next();
				
				entry.put("name", description.getName());
				entry.put("type", option.getProductOptionType());//TODO resolve with option type label
				resp.addDataEntry(entry);
				
				
			}
			
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);
			

		
		} catch (Exception e) {
			LOGGER.error("Error while paging options", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}
		
		String returnString = resp.toJSONString();
		
		return returnString;
		
		
	}
	
	

	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("catalogue", "catalogue");
		activeMenus.put("catalogue-options", "catalogue-options");
		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("catalogue");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
		//
		
	}
	
	@RequestMapping(value="/admin/options/remove.html", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String deleteOption(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String sid = request.getParameter("optionId");

		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			Long id = Long.parseLong(sid);
			
			ProductOption entity = productOptionService.getById(id);

			if(entity==null || entity.getMerchantStore().getId().intValue()!=store.getId().intValue()) {

				resp.setStatusMessage(messages.getMessage("message.unauthorized", locale));
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);			
				
			} else {
				
				productOptionService.delete(entity);
				resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);
				
			}
		
		
		} catch (Exception e) {
			LOGGER.error("Error while deleting option", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		
		return returnString;
	}
	
	@RequestMapping(value={"/admin/options/loadShadeData.html"}, method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String loadAjaxData(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		
		String fieldValue = request.getParameter("fieldValue");
		
		Language language = (Language)request.getAttribute("LANGUAGE");	
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		List<ProductOption> productOptions = productOptionService.getByNameShadeOnly(store, fieldValue, language);
		
		List<ProductOption.CustomProductOption> customOptions = new ProductOption().new CustomProductOption().getCustomProductOptions(productOptions);
				
		return LogicUtils.getJSONString(customOptions);
	}

}
