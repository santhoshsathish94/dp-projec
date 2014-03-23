package com.salesmanager.web.admin.controller.products;

import java.util.ArrayList;
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

import com.salesmanager.core.business.catalog.product.model.master.Shades;
import com.salesmanager.core.business.catalog.product.model.master.Variants;
import com.salesmanager.core.business.catalog.product.service.master.ProductMasterService;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.entity.web.Menu;

@Controller
public class ProductMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductMasterController.class);
	
	@Autowired
	ProductMasterService productMasterService;
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/catalogue/master/listpacksize.html", method=RequestMethod.GET)
	public String variantsList(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		setMenu(model,request, "master-entry");
		return "admin-product-variant-list";
	}
	

	@SuppressWarnings("unchecked")
	@Secured("PRODUCTS")
	@RequestMapping(value = "/admin/catalogue/master/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String variantsPaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();

		try {
			List<Variants> variantsList = productMasterService.getVariantsList();
			
			int counter = 1;
			for(Variants variant: variantsList) {

				@SuppressWarnings("rawtypes")
				Map entry = new HashMap();
				
				entry.put("counter", counter);
				entry.put("variantId", variant.getId());
				entry.put("variant", variant.getVariantName());
				entry.put("variantType", variant.getVariantType());
				entry.put("edit", "Edit");
				resp.addDataEntry(entry);
				
				counter++;
			}
			 
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);

		} catch (Exception e) {
			LOGGER.error("Error while paging products", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}

		String returnString = resp.toJSONString();

		return returnString;
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/catalogue/master/createpacksize.html", method=RequestMethod.GET)
	public String createVariant(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Variants newVariant = new Variants();
		
		return displayVariants(newVariant, model, request, response);
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/catalogue/master/editpacksize.html", method=RequestMethod.GET)
	public String editVariant(@ModelAttribute("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		Variants dbVariant = productMasterService.getVariantsById(id);
		
		if(dbVariant == null) {
			return "admin-product-variant-list";
		}
		
		return displayVariants(dbVariant, model, request, response);
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/catalogue/master/savepacksize.html", method=RequestMethod.POST)
	public String savePackSize(@Valid @ModelAttribute("variant") Variants variant, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		Variants newVariant = new Variants();
		
		if(variant.getId() != null) {
			newVariant = productMasterService.getVariantsById(variant.getId());
			
			if(newVariant == null) {
				return "admin-product-variant-list";
			}
			
			newVariant.setUpdated(new Date());
		}
		
		newVariant.setVariantName(variant.getVariantName());
		newVariant.setVariantType(variant.getVariantType());
		
		productMasterService.saveOrUpdate(newVariant);
		
		Variants savedVariant = productMasterService.getVariantsById(newVariant.getId());
		
		model.addAttribute("success","success");
		
		return displayVariants(savedVariant, model, request, response);
	}
	
	private String displayVariants(Variants variant, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//display menu
		setMenu(model,request, "master-entry");
		
		model.addAttribute("variantTypes", variant.new VariantType().getTypeList());
		
		model.addAttribute("variant", variant);
		
		return "admin-product-variant";
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/catalogue/master/listshade.html", method=RequestMethod.GET)
	public String shadesList(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		setMenu(model,request, "master-entry");
		return "admin-product-shade-list";
	}
	

	@SuppressWarnings("unchecked")
	@Secured("PRODUCTS")
	@RequestMapping(value = "/admin/catalogue/master/shadespaging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String shadesPaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();

		try {
			List<Shades> shadeList = productMasterService.getShadesList();
			
			int counter = 1;
			for(Shades shade: shadeList) {

				@SuppressWarnings("rawtypes")
				Map entry = new HashMap();
				
				entry.put("counter", counter);
				entry.put("shadeId", shade.getId());
				entry.put("shade", shade.getShadesName());
				entry.put("shadeShortName", shade.getShortName());
				entry.put("edit", "Edit");
				resp.addDataEntry(entry);
				
				counter++;
			}
			 
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);

		} catch (Exception e) {
			LOGGER.error("Error while paging products", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}

		String returnString = resp.toJSONString();

		return returnString;
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/catalogue/master/createshade.html", method=RequestMethod.GET)
	public String createShade(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Shades newShade = new Shades();
		
		return displayShades(newShade, model, request, response);
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/catalogue/master/editshade.html", method=RequestMethod.GET)
	public String editShades(@ModelAttribute("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		Shades dbShade = productMasterService.getShadesById(id);
		
		if(dbShade == null) {
			return "admin-product-shade-list";
		}
		
		return displayShades(dbShade, model, request, response);
	}
	
	@Secured("PRODUCTS")
	@RequestMapping(value="/admin/catalogue/master/saveShade.html", method=RequestMethod.POST)
	public String saveShades(@Valid @ModelAttribute("shade") Shades shade, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		Shades newShade = new Shades();
		
		if(shade.getId() != null) {
			newShade = productMasterService.getShadesById(shade.getId());
			
			if(newShade == null) {
				return "admin-product-variant-list";
			}
			
			newShade.setUpdated(new Date());
		}
		
		newShade.setShadesName(shade.getShadesName());
		newShade.setShortName(shade.getShortName());
		
		productMasterService.saveOrUpdate(newShade);
		
		Shades savedShade = productMasterService.getShadesById(newShade.getId());
		
		model.addAttribute("success","success");
		
		return displayShades(savedShade, model, request, response);
	}
	
	private String displayShades(Shades shade, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//display menu
		setMenu(model,request, "master-entry");
		
		model.addAttribute("shade", shade);
		
		return "admin-product-shade";
	}
	
	private void setMenu(Model model, HttpServletRequest request, String selectedMenu) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("catalogue", "catalogue");
		activeMenus.put(selectedMenu, selectedMenu);

		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("catalogue");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
	}
}
