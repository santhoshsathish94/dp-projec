package com.salesmanager.web.admin.controller.products;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.salesmanager.core.business.adapter.CustomCustomer;
import com.salesmanager.core.business.billing.model.CreditNoteProductEntity;
import com.salesmanager.core.business.billing.model.SalesInvoice;
import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.ProductCriteria;
import com.salesmanager.core.business.catalog.product.model.ProductList;
import com.salesmanager.core.business.catalog.product.service.ProductService;
import com.salesmanager.core.business.common.model.ProductJSONEntity;
import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.customer.service.CustomerService;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.inventory.model.CustomerInventory;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.inventory.model.PurchaseReturnDebitNote;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.inventory.service.BranchTransferService;
import com.salesmanager.core.business.reference.inventory.service.CustomerInventoryService;
import com.salesmanager.core.business.reference.inventory.service.DebitNoteOtherService;
import com.salesmanager.core.business.reference.inventory.service.PurchaseReturnDebitNoteService;
import com.salesmanager.core.business.reference.inventory.service.PurchaseService;
import com.salesmanager.core.business.reference.inventory.service.StockService;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.service.utils.LogicUtils;
import com.salesmanager.core.business.service.utils.ProductJSONEntityService;
import com.salesmanager.core.business.user.service.UserService;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.DateUtil;
import com.salesmanager.web.utils.LabelUtils;

@Controller
public class InventorySharingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InventorySharingController.class);

	@Autowired
	UserService userService;

	@Autowired
	StockService stockService;

	@Autowired
	PurchaseService purchaseService;

	@Autowired
	PurchaseReturnDebitNoteService purchaseReturnDebitNoteService;

	@Autowired
	DebitNoteOtherService debitNoteOtherService;

	@Autowired
	BranchTransferService branchTransferService;

	@Autowired
	LabelUtils messages;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;
	@Autowired
	private CustomerInventoryService customerInventoryService;

	@Secured("AUTH")
	@RequestMapping(value = "/admin/catalogue/sharing/byProduct.html", method = RequestMethod.GET)
	public String customerProduct(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		CustomerInventory customerInventory = new CustomerInventory();
		model.addAttribute("customerInventory", customerInventory);
		setMenu(model, request, "product-sharing");
		return "admin-product-sharing";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/catalogue/sharing/byManufacturer.html", method = RequestMethod.GET)
	public String customerManufacturer(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		CustomerInventory customerInventory = new CustomerInventory();
		model.addAttribute("customerInventory", customerInventory);
		setMenu(model, request, "manufacturer-sharing");
		return "admin-manufacturer-sharing";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/catalogue/sharing/byCategory.html", method = RequestMethod.GET)
	public String customerCategory(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		CustomerInventory customerInventory = new CustomerInventory();
		model.addAttribute("customerInventory", customerInventory);
		setMenu(model, request, "category-sharing");
		return "admin-category-sharing";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/catalogue/sharing/saveCustomerProductMapping.html", method = RequestMethod.POST)
	public String saveCustomerProductMapping(@Valid @ModelAttribute("customerInventory") CustomerInventory customerInventory, BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(customerInventory.getJsonArray());
		Iterator i = jsonArray.iterator();

		while (i.hasNext()) {
			CustomerInventory customerInventory1 = new CustomerInventory();
			JSONObject slide = (JSONObject) i.next();
			customerInventory1.setCustomerid(Long.parseLong(slide.get("customer").toString()));
			customerInventory1.setInventoryid(Long.parseLong(slide.get("product").toString()));
			customerInventory1.setInventory_type(slide.get("type").toString());
			customerInventory1.setMerchantStore(store);
			customerInventoryService.addCustomerInventory(customerInventory1);
		}

		model.addAttribute("customerInventory", new CustomerInventory());
		setMenu(model, request, "product-sharing");
		return "admin-product-sharing";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/catalogue/sharing/saveCustomerManufacturerMapping.html", method = RequestMethod.POST)
	public String saveCustomerManufacturerMapping(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		CustomerInventory customerInventory = new CustomerInventory();
		model.addAttribute("customerInventory", customerInventory);
		setMenu(model, request, "product-sharing");
		return "admin-manufacturer-sharing";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/catalogue/sharing/saveCustomerCategoryMapping.html", method = RequestMethod.POST)
	public String saveCustomerCategoryMapping(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		CustomerInventory customerInventory = new CustomerInventory();
		model.addAttribute("customerInventory", customerInventory);
		setMenu(model, request, "product-sharing");
		return "admin-category-sharing";
	}

	private void setMenu(Model model, HttpServletRequest request, String setActiveMenus) throws Exception {

		// display menu
		Map<String, String> activeMenus = new HashMap<String, String>();
		activeMenus.put("catalogue", "catalogue");
		activeMenus.put(setActiveMenus, setActiveMenus);

		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>) request.getAttribute("MENUMAP");

		Menu currentMenu = (Menu) menus.get("catalogue");
		model.addAttribute("currentMenu", currentMenu);
		model.addAttribute("activeMenus", activeMenus);
	}

	@RequestMapping(value = { "/admin/catalogue/sharing/loadCustomer.html" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String loadCustomerData(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

		String fieldValue = request.getParameter("fieldValue");

		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);

		List<Customer> customerList = customerService.getByNameOrIdOrCompany(store, fieldValue);

		List<CustomCustomer> customCustomerList = new ArrayList<CustomCustomer>();

		for (Customer customer : customerList) {
			CustomCustomer cc = new CustomCustomer();

			cc.id = customer.getId();
			cc.name = customer.getBilling().getFirstName() + " " + customer.getBilling().getLastName();
			cc.nick = customer.getNick();
			cc.company = customer.getBilling().getCompany();

			customCustomerList.add(cc);
		}

		return LogicUtils.getJSONString(customCustomerList);
	}

	@RequestMapping(value = { "/admin/catalogue/sharing/loadProductInfo.html" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String loadProductInfo(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

		String fieldValue = request.getParameter("fieldValue");
		String searchType = request.getParameter("searchType");

		Language language = (Language) request.getAttribute("LANGUAGE");
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);

		ProductCriteria criteria = new ProductCriteria();

		ProductList productList = productService.listByStore(store, language, criteria);

		List<Product> pList = productList.getProducts();

		List<ProductJSONEntity> customProdList = getcustomProdList(pList, fieldValue);
		return LogicUtils.getJSONString(customProdList);

	}

	private List<ProductJSONEntity> getcustomProdList(List<Product> productList, String fieldValue) {
		fieldValue = fieldValue.toUpperCase();
		List<ProductJSONEntity> customProdList = ProductJSONEntityService.getProductJSONEntity(productList);

		List<ProductJSONEntity> newCustomProdList = new ArrayList<ProductJSONEntity>();

		for (ProductJSONEntity siProd : customProdList) {
			if (siProd.productName.toUpperCase().contains(fieldValue) || siProd.productDispalyName.toUpperCase().contains(fieldValue)) {
				newCustomProdList.add(siProd);
			}
		}

		return newCustomProdList;
	}

	private Date convertDate(String sDate, String dateFieldName, BindingResult result, Locale locale) {

		if (!StringUtils.isBlank(sDate)) {
			try {
				return DateUtil.getDate(sDate);

			} catch (Exception e) {
				ObjectError error = new ObjectError(dateFieldName, messages.getMessage("message.invalid.date", locale));
				result.addError(error);
				return null;
			}
		} else {
			return null;
		}

	}

}
