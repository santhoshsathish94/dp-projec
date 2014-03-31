package com.salesmanager.web.admin.controller.inventory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import com.salesmanager.core.business.catalog.category.model.Category;
import com.salesmanager.core.business.catalog.category.model.CategoryDescription;
import com.salesmanager.core.business.company.model.AccountingPeriod;
import com.salesmanager.core.business.company.model.Company;
import com.salesmanager.core.business.company.model.CompanyCurrencies;
import com.salesmanager.core.business.company.service.CompanyService;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.country.service.CountryService;
import com.salesmanager.core.business.reference.currency.model.Currency;
import com.salesmanager.core.business.reference.currency.service.CurrencyService;
import com.salesmanager.core.business.reference.inventory.service.PurchaseService;
import com.salesmanager.core.business.reference.inventory.service.StockService;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.reference.language.service.LanguageService;
import com.salesmanager.core.business.reference.zone.model.Zone;
import com.salesmanager.core.business.reference.zone.service.ZoneService;
import com.salesmanager.core.business.supplier.model.Supplier;
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
	UserService userService;

	@Autowired
	StockService stockService;

	@Autowired
	PurchaseService purchaseService;

	@Autowired
	LabelUtils messages;

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createstock.html", method = RequestMethod.GET)
	public String createStockPage(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "openingStock");
		return "admin-inventory";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createPurchaseEntry.html", method = RequestMethod.GET)
	public String PerchaseEntery(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Purchase purchase = new Purchase();
		model.addAttribute("purchase", purchase);
		setMenu(model, request, "purchase");
		return "admin-perchase";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createPurchaseEntry.html", method = RequestMethod.POST)
	public String createPerchaseEntery(@Valid @ModelAttribute("purchase") Purchase purchase, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale)
			throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		Date date = null;

		JSONParser parser = new JSONParser();
		System.out.println("============Json Starts====================>>" + purchase.getJsonArray());
		JSONArray jsonArray = (JSONArray) parser.parse(purchase.getJsonArray());
		System.out.println("============Json JSONParser completes====================");
		System.out.println(jsonArray.toJSONString());

		if (!StringUtils.isBlank(purchase.getPurchase_Sdate())) {
			try {
				date = DateUtil.getDate(purchase.getPurchase_Sdate());
				System.out.println("============setPurchase_date==========befor==========>>" + purchase.getPurchase_Sdate());
				purchase.setPurchase_date(date);
				System.out.println("============Json JSONParser completes====================");
			} catch (Exception e) {
				ObjectError error = new ObjectError("purchase_Sdate", messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
		}
		if (!StringUtils.isBlank(purchase.getPurchase_due_Sdate())) {
			try {
				date = DateUtil.getDate(purchase.getPurchase_due_Sdate());
				purchase.setPurchase_due_date(date);
			} catch (Exception e) {
				ObjectError error = new ObjectError("purchase_due_Sdate", messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
		}
		purchase.setEntry_date(new Date());

		purchase.setCreated_by(sCurrentUser);
		purchase.setMerchantStore(store);
		Iterator i = jsonArray.iterator();
		System.out.println("============Basic Settings completes=========Going to Iterate JsonArray===========");
		while (i.hasNext()) {
			Purchase purchase1 = new Purchase();
			JSONObject slide = (JSONObject) i.next();

			purchase1.setCreated_by(sCurrentUser);
			purchase1.setMerchantStore(store);
			purchase1.setPurchase_sku((String) slide.get("purchaseSKU"));
			purchase1.setPurchase_description((String) slide.get("purchaseDescription"));
			purchase1.setPurchase_quantity(Integer.parseInt((String) slide.get("purchaseQuantity")));
			purchase1.setPurchase_uom((String) slide.get("purchaseUOM"));
			purchase1.setPurchase_unit_price(new BigDecimal(slide.get("purchaseUnitPrice").toString()));
			purchase1.setPurchase_tax((String) slide.get("purchaseTax"));
			purchase1.setPurchase_amount(new BigDecimal(slide.get("purchaseAmount").toString()));

			System.out.println("=========purchase1.getPurchase_sku===>>>" + purchase1.getPurchase_sku());
			purchase1.setPurchase_supplier(purchase.getPurchase_supplier());
			purchase1.setPurchase_due_date(purchase.getPurchase_due_date());
			purchase1.setPurchase_date(purchase.getPurchase_date());
			purchase1.setPurchase_ref_number(purchase.getPurchase_ref_number());
			purchase1.setPurchase_comment(purchase.getPurchase_comment());
			purchase1.setEntry_date(purchase.getEntry_date());

			purchaseService.addPurchase(purchase1);
		}

		purchase.reset();
		model.addAttribute("purchase", purchase);
		setMenu(model, request, "openingStock");
		return "admin-perchase";

	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createpurchasereturndn.html", method = RequestMethod.GET)
	public String purchasereturndnEntery(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "openingStock");
		return "admin-perchase-returned";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createpurchasereturndn.html", method = RequestMethod.POST)
	public String createpurchasereturndnEntery(@Valid @ModelAttribute("stock") Stock stock, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale)
			throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		return sCurrentUser;
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createdebitnoteother.html", method = RequestMethod.GET)
	public String purchaseReturndnDebitnoteOtherEntery(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "openingStock");
		return "admin-perchase-returned-other";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createdebitnoteother.html", method = RequestMethod.POST)
	public String createPurchasereturndnDebitnoteOtherEntery(@Valid @ModelAttribute("stock") Stock stock, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response,
			Locale locale) throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		return sCurrentUser;
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createstock.html", method = RequestMethod.POST)
	public String createStock(@Valid @ModelAttribute("stock") Stock stock, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		Date date = null;
		JSONParser parser = new JSONParser();
		// JSONObject json = (JSONObject)new
		// JSONParser().parse("{\"name\":\"MyNode\", \"width\":200, \"height\":100}");
		System.out.println("============Json Starts====================>>" + stock.getOpeningStocks());
		JSONArray jsonArray = (JSONArray) parser.parse(stock.getOpeningStocks());
		System.out.println("============Json JSONParser completes====================");
		// JSONArray openingStocks = (JSONArray) json.get("openingStocks");
		System.out.println(jsonArray.toJSONString());
		if (!StringUtils.isBlank(stock.getStockSDate())) {
			try {
				date = DateUtil.getDate(stock.getStockSDate());
				stock.setStockDate(date);
			} catch (Exception e) {
				ObjectError error = new ObjectError("stockSDate", messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
		}
		if (!StringUtils.isBlank(stock.getUpdatedSDate())) {
			try {
				date = DateUtil.getDate(stock.getUpdatedSDate());
				stock.setUpdatedDate(date);
			} catch (Exception e) {
				ObjectError error = new ObjectError("updatedSDate", messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
		}
		stock.setCreatedBy(sCurrentUser);
		stock.setMerchantStore(store);
		Iterator i = jsonArray.iterator();

		while (i.hasNext()) {
			Stock stock1 = new Stock();
			JSONObject slide = (JSONObject) i.next();
			stock1.setStockSKU((String) slide.get("stockSKU"));
			stock1.setStockQuantity(Integer.parseInt((String) slide.get("stockQuantity")));
			stock1.setStockUOM((String) slide.get("stockUOM"));
			stock1.setStockQuantity(Integer.parseInt((String) slide.get("stockQuantity")));
			stock1.setStockUnitPrice(new BigDecimal(slide.get("stockUnitPrice").toString()));
			stock1.setStockAmount(new BigDecimal(slide.get("stockAmount").toString()));
			stock1.setStockDate(stock.getStockDate());
			stock1.setStockComment(stock.getStockComment());
			stock1.setCreatedBy(sCurrentUser);
			stock1.setMerchantStore(store);

			System.out.println("=========stock.getStockSKU===>>>" + stock.getStockSKU());

			stockService.addStosk(stock1);
		}

		stock.reset();
		model.addAttribute("stock", stock);
		setMenu(model, request, "openingStock");
		return "admin-inventory";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/stocks.html", method = RequestMethod.GET)
	public String displayStocks(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		setMenu(model, request, "openingStock");
		return "admin-stock-list";
	}
	

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String supplierPaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();

		String sCurrentUser = request.getRemoteUser();

		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
			List<Stock> stocks = null;

			if (UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN)) {
				stocks = stockService.getStocks(store);
				for (Stock stock : stocks) {
					Map entry = new HashMap();
					entry.put("stockId", stock.getId());
					entry.put("stockSKU", stock.getStockSKU());
					entry.put("stockQuantity", stock.getStockQuantity());
					entry.put("stockUOM", stock.getStockUOM());
					entry.put("stockUnitPrice", stock.getStockUnitPrice());
					entry.put("stockAmount", stock.getStockAmount());
					entry.put("stockSDate", DateUtil.formatDate(stock.getStockDate()));

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
		System.out.println("============Before====resp.toJSONString======================>>");
		String returnString = resp.toJSONString();
		System.out.println("============After====resp.returnString======================>>" + returnString);

		return returnString;
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/purchaseEntrylist.html", method = RequestMethod.GET)
	public String displayPurchase(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		setMenu(model, request, "purchase");
		return "admin-perchase-list";
	}
	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/purchase/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String purchasePaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();

		String sCurrentUser = request.getRemoteUser();

		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
			List<Purchase> purchases = null;

			if (UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN)) {
				purchases = purchaseService.getPurchase(store);
				for (Purchase purchase : purchases) {
					Map entry = new HashMap();
					entry.put("Id", purchase.getId());
					entry.put("Supplier", purchase.getPurchase_supplier());
					entry.put("SKU", purchase.getPurchase_sku());
					entry.put("Quantity", purchase.getPurchase_quantity());
					entry.put("UnitPrice", purchase.getPurchase_unit_price());
					entry.put("Amount", purchase.getPurchase_amount());
					entry.put("SDate", DateUtil.formatDate(purchase.getPurchase_date()));
									

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
		System.out.println("============Before====resp.toJSONString======================>>");
		String returnString = resp.toJSONString();
		System.out.println("============After====resp.returnString======================>>" + returnString);

		return returnString;
	}

	private void setMenu(Model model, HttpServletRequest request, String setActiveMenus) throws Exception {

		// display menu
		Map<String, String> activeMenus = new HashMap<String, String>();
		activeMenus.put("inventoryManagement", "inventoryManagement");
		activeMenus.put(setActiveMenus, setActiveMenus);

		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>) request.getAttribute("MENUMAP");

		Menu currentMenu = (Menu) menus.get("inventoryManagement");
		model.addAttribute("currentMenu", currentMenu);
		model.addAttribute("activeMenus", activeMenus);
	}

}
