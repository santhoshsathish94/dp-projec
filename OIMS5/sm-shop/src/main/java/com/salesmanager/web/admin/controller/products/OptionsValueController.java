package com.salesmanager.web.admin.controller.products;

import java.io.InputStream;
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

import com.salesmanager.core.business.catalog.product.model.attribute.ProductOptionValue;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductOptionValueDescription;
import com.salesmanager.core.business.catalog.product.service.attribute.ProductOptionValueService;
import com.salesmanager.core.business.content.model.FileContentType;
import com.salesmanager.core.business.content.model.InputContentFile;
import com.salesmanager.core.business.content.service.ContentService;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.reference.language.service.LanguageService;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.LabelUtils;

@Controller
public class OptionsValueController {
	
	@Autowired
	LanguageService languageService;
	

	@Autowired
	ProductOptionValueService productOptionValueService;
	
	@Autowired
	LabelUtils messages;
	
	@Autowired
	private ContentService contentService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OptionsValueController.class);
	
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/options/optionvalues.html", method=RequestMethod.GET)
	public String displayOptions(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		setMenu(model,request);
		//subsequent ajax call
		return "catalogue-optionsvalues-list";
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/options/editOptionValue.html", method=RequestMethod.GET)
	public String displayOptionEdit(@RequestParam("id") long optionId, HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayOption(optionId,request,response,model,locale);
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/options/createOptionValue.html", method=RequestMethod.GET)
	public String displayOption(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayOption(null,request,response,model,locale);
	}
	
	private String displayOption(Long optionId, HttpServletRequest request, HttpServletResponse response,Model model,Locale locale) throws Exception {
		
		this.setMenu(model, request);
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		List<Language> languages = store.getLanguages();
		Set<ProductOptionValueDescription> descriptions = new HashSet<ProductOptionValueDescription>();
		ProductOptionValue option = new ProductOptionValue();
		
		if(optionId!=null && optionId!=0) {//edit mode
			option = productOptionValueService.getById(store, optionId);
			if(option==null) {
				return "redirect:/admin/options/optionsvalues.html";
			}
			Set<ProductOptionValueDescription> optionDescriptions = option.getDescriptions();

			for(Language l : languages) {
			
				ProductOptionValueDescription optionDescription = null;
				
				if(optionDescriptions!=null) {
					
					for(ProductOptionValueDescription description : optionDescriptions) {
						
						String code = description.getLanguage().getCode();
						if(code.equals(l.getCode())) {
							optionDescription = description;
						}
					}
				}
				
				if(optionDescription==null) {
					optionDescription = new ProductOptionValueDescription();
					optionDescription.setLanguage(l);
				}
				
				descriptions.add(optionDescription);
			}
		} else {
			
			for(Language l : languages) {
				ProductOptionValueDescription desc = new ProductOptionValueDescription();
				desc.setLanguage(l);
				descriptions.add(desc);
			}
			option.setDescriptions(descriptions);
		}
		
		model.addAttribute("optionValue", option);
		return "catalogue-optionsvalues-details";
	}
		
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/options/saveOptionValue.html", method=RequestMethod.POST)
	public String saveOption(@Valid @ModelAttribute("optionValue") ProductOptionValue optionValue, BindingResult result, Model model, HttpServletRequest request, Locale locale) throws Exception {
		//display menu
		setMenu(model,request);
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		ProductOptionValue dbEntity =	null;	

		if(optionValue.getId() != null && optionValue.getId() >0) { //edit entry
			//get from DB
			dbEntity = productOptionValueService.getById(store,optionValue.getId());
			
			if(dbEntity==null) {
				return "redirect:/admin/options/optionsvalues.html";
			}
		}
		
		//validate if it contains an existing code
		ProductOptionValue byCode = productOptionValueService.getByCode(store, optionValue.getCode());
		if(byCode!=null) {
			ObjectError error = new ObjectError("code",messages.getMessage("message.code.exist", locale));
			result.addError(error);
		}
			
		Map<String,Language> langs = languageService.getLanguagesMap();

		List<ProductOptionValueDescription> descriptions = optionValue.getDescriptionsList();
		if(descriptions!=null && descriptions.size()>0) {
				Set<ProductOptionValueDescription> descs = new HashSet<ProductOptionValueDescription>();
				//if(descs==null || descs.size()==0) {			

				//} else {
					optionValue.setDescriptions(descs);
					for(ProductOptionValueDescription description : descriptions) {
						
						if(StringUtils.isBlank(description.getName())) {
							ObjectError error = new ObjectError("name",messages.getMessage("message.name.required", locale));
							result.addError(error);
						} else {
							String code = description.getLanguage().getCode();
							Language l = langs.get(code);
							description.setLanguage(l);
							description.setProductOptionValue(optionValue);
							descs.add(description);
						}
					}
		} else {
			ObjectError error = new ObjectError("name",messages.getMessage("message.name.required", locale));
			result.addError(error);
		}

		optionValue.setMerchantStore(store);
		
		if (result.hasErrors()) {
			return "catalogue-optionsvalues-details";
		}

		if(optionValue.getImage()!=null && !optionValue.getImage().isEmpty()) {

			String imageName = optionValue.getImage().getOriginalFilename();
            InputStream inputStream = optionValue.getImage().getInputStream();
            InputContentFile cmsContentImage = new InputContentFile();
            cmsContentImage.setFileName(imageName);
            cmsContentImage.setMimeType( optionValue.getImage().getContentType() );
            cmsContentImage.setFile( inputStream );
            contentService.addOptionImage(store.getCode(), cmsContentImage);
            
            optionValue.setProductOptionValueImage(imageName);

		}
		
		productOptionValueService.saveOrUpdate(optionValue);

		model.addAttribute("success","success");
		return "catalogue-optionsvalues-details";
	}
	
	
	@SuppressWarnings("unchecked")
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/optionsvalues/paging.html", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String pageOptions(HttpServletRequest request, HttpServletResponse response) {
		
		String optionName = request.getParameter("name");
		AjaxResponse resp = new AjaxResponse();
		
		try {
			
			Language language = (Language)request.getAttribute("LANGUAGE");	
			MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
			List<ProductOptionValue> options = null;
					
			if(!StringUtils.isBlank(optionName)) {
				//productOptionValueService.getByName(store, optionName, language);
			} else {
				options = productOptionValueService.listByStore(store, language);
			}
			
			for(ProductOptionValue option : options) {
				
				@SuppressWarnings("rawtypes")
				Map entry = new HashMap();
				entry.put("optionValueId", option.getId());
				
				ProductOptionValueDescription description = option.getDescriptions().iterator().next();
				
				entry.put("name", description.getName());
				entry.put("image", new StringBuilder().append(store.getCode()).append("/").append(FileContentType.PROPERTY.name()).append("/").append(option.getProductOptionValueImage()).toString());
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
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/optionsvalues/remove.html", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String deleteOptionValue(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String sid = request.getParameter("optionValueId");
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		AjaxResponse resp = new AjaxResponse();

		try {
			
			Long id = Long.parseLong(sid);
			ProductOptionValue entity = productOptionValueService.getById(store, id);

			if(entity==null || entity.getMerchantStore().getId().intValue()!=store.getId().intValue()) {

				resp.setStatusMessage(messages.getMessage("message.unauthorized", locale));
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);			
				
			} else {
				
				productOptionValueService.delete(entity);
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
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/optionsvalues/removeImage.html", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String removeImage(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String optionValueId = request.getParameter("optionId");

		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		AjaxResponse resp = new AjaxResponse();
		
		try {
			
			Long id = Long.parseLong(optionValueId);
			
			ProductOptionValue optionValue = productOptionValueService.getById(store, id);
			contentService.removeFile(store.getCode(), FileContentType.PROPERTY, optionValue.getProductOptionValueImage());
			
			store.setStoreLogo(null);
			optionValue.setProductOptionValueImage(null);
			productOptionValueService.update(optionValue);

		} catch (Exception e) {
			LOGGER.error("Error while deleting product", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
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

}
