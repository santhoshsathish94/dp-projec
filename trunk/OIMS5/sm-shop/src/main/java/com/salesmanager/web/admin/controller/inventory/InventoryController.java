package com.salesmanager.web.admin.controller.inventory;

import java.math.BigDecimal;
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

import com.salesmanager.core.business.common.model.ProductJSONEntity;
import com.salesmanager.core.business.inventory.model.BranchTransfer;
import com.salesmanager.core.business.inventory.model.DebitNoteOther;
import com.salesmanager.core.business.inventory.model.Purchase;
import com.salesmanager.core.business.inventory.model.PurchaseReturnDebitNote;
import com.salesmanager.core.business.inventory.model.Stock;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.inventory.service.BranchTransferService;
import com.salesmanager.core.business.reference.inventory.service.DebitNoteOtherService;
import com.salesmanager.core.business.reference.inventory.service.PurchaseReturnDebitNoteService;
import com.salesmanager.core.business.reference.inventory.service.PurchaseService;
import com.salesmanager.core.business.reference.inventory.service.StockService;
import com.salesmanager.core.business.service.utils.LogicUtils;
import com.salesmanager.core.business.service.utils.ProductJSONEntityService;
import com.salesmanager.core.business.tax.model.taxclass.TaxClass;
import com.salesmanager.core.business.tax.service.TaxClassService;
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
	PurchaseReturnDebitNoteService purchaseReturnDebitNoteService;

	@Autowired
	DebitNoteOtherService debitNoteOtherService;

	@Autowired
	BranchTransferService branchTransferService;
	
	@Autowired
	private TaxClassService taxClassService;

	@Autowired
	LabelUtils messages;

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createstock.html", method = RequestMethod.GET)
	public String createStockPage(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "openingStock");
		
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		List<TaxClass> taxClasses = taxClassService.listByStore(store);

		Map<Long, BigDecimal> taxRateMap = new HashMap<Long, BigDecimal>();
		for (TaxClass tc : taxClasses) {
			if (tc.getTaxRates() != null && tc.getTaxRates().size() > 0) {
				taxRateMap.put(tc.getId(), tc.getTaxRates().iterator().next().getTaxRate());
			} else {
				taxRateMap.put(tc.getId(), new BigDecimal(0));
			}
		}

		model.addAttribute("taxClasses", taxClasses);
		model.addAttribute("taxRateMap", LogicUtils.getJSONStringFromMap(taxRateMap));
		
		return "admin-inventory";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createPurchaseEntry.html", method = RequestMethod.GET)
	public String PerchaseEntery(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Purchase purchase = new Purchase();
		model.addAttribute("purchase", purchase);
		setMenu(model, request, "purchase");
		
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		List<TaxClass> taxClasses = taxClassService.listByStore(store);

		Map<Long, BigDecimal> taxRateMap = new HashMap<Long, BigDecimal>();
		for (TaxClass tc : taxClasses) {
			if (tc.getTaxRates() != null && tc.getTaxRates().size() > 0) {
				taxRateMap.put(tc.getId(), tc.getTaxRates().iterator().next().getTaxRate());
			} else {
				taxRateMap.put(tc.getId(), new BigDecimal(0));
			}
		}

		model.addAttribute("taxClasses", taxClasses);
		model.addAttribute("taxRateMap", LogicUtils.getJSONStringFromMap(taxRateMap));
		
		return "admin-perchase";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createpurchasereturndn.html", method = RequestMethod.GET)
	public String purchasereturndnEntery(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		PurchaseReturnDebitNote purchaseReturnDebitNote = new PurchaseReturnDebitNote();
		model.addAttribute("purchaseReturnDebitNote", purchaseReturnDebitNote);
		setMenu(model, request, "purchase");
		
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		List<TaxClass> taxClasses = taxClassService.listByStore(store);

		Map<Long, BigDecimal> taxRateMap = new HashMap<Long, BigDecimal>();
		for (TaxClass tc : taxClasses) {
			if (tc.getTaxRates() != null && tc.getTaxRates().size() > 0) {
				taxRateMap.put(tc.getId(), tc.getTaxRates().iterator().next().getTaxRate());
			} else {
				taxRateMap.put(tc.getId(), new BigDecimal(0));
			}
		}

		model.addAttribute("taxClasses", taxClasses);
		model.addAttribute("taxRateMap", LogicUtils.getJSONStringFromMap(taxRateMap));
		
		return "admin-perchase-returned";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createdebitnoteother.html", method = RequestMethod.GET)
	public String purchaseReturndnDebitnoteOtherEntery(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		DebitNoteOther debitNoteOther = new DebitNoteOther();
		model.addAttribute("debitNoteOther", debitNoteOther);
		setMenu(model, request, "purchase");
		return "admin-perchase-returned-other";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createbranchtransfer.html", method = RequestMethod.GET)
	public String branchTransferEntery(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		BranchTransfer branchTransfer = new BranchTransfer();
		model.addAttribute("branchTransfer", branchTransfer);
		setMenu(model, request, "branchtransfer");
		return "admin-branch-transfer";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createstock.html", method = RequestMethod.POST)
	public String createStock(@Valid @ModelAttribute("stock") Stock stock, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		Date date = null;
		/*JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(stock.getOpeningStocks());*/
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
		
		List<ProductJSONEntity> pjeList = ProductJSONEntityService.getProductJSONEntityListFromJsonString(stock.getProductJson());
		
		for(ProductJSONEntity pje : pjeList) {
			Stock stock1 = new Stock();
			stock1.setStockSKU(pje.productName);
			stock1.setStockQuantity(pje.quantity);
			stock1.setStockUOM(pje.uom);
			stock1.setStockUnitPrice(pje.unitPrice);
			stock1.setStockAmount(pje.amount);
			stock1.setStockDate(stock.getStockDate());
			stock1.setStockComment(stock.getStockComment());
			stock1.setCreatedBy(sCurrentUser);
			stock1.setMerchantStore(store);

			stockService.addStosk(stock1);
		}
		
		/*Iterator i = jsonArray.iterator();

		while (i.hasNext()) {
			Stock stock1 = new Stock();
			JSONObject slide = (JSONObject) i.next();
			stock1.setStockSKU((String) slide.get("stockSKU"));
			stock1.setStockQuantity(Integer.parseInt((String) slide.get("stockQuantity")));
			stock1.setStockUOM((String) slide.get("stockUOM"));
			stock1.setStockUnitPrice(new BigDecimal(slide.get("stockUnitPrice").toString()));
			stock1.setStockAmount(new BigDecimal(slide.get("stockAmount").toString()));
			stock1.setStockDate(stock.getStockDate());
			stock1.setStockComment(stock.getStockComment());
			stock1.setCreatedBy(sCurrentUser);
			stock1.setMerchantStore(store);

			stockService.addStosk(stock1);
		}*/

		stock.reset();
		model.addAttribute("stock", stock);
		setMenu(model, request, "openingStock");
		return "admin-inventory";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createPurchaseEntry.html", method = RequestMethod.POST)
	public String createPerchaseEntery(@Valid @ModelAttribute("purchase") Purchase purchase, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale)
			throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		Date date = null;

		List<ProductJSONEntity> pjeList = ProductJSONEntityService.getProductJSONEntityListFromJsonString(purchase.getProductJson());
		
		/*JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(purchase.getJsonArray());*/

		if (!StringUtils.isBlank(purchase.getPurchase_Sdate())) {
			try {
				date = DateUtil.getDate(purchase.getPurchase_Sdate());
				purchase.setPurchase_date(date);
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
		
		for(ProductJSONEntity pje: pjeList) {
			Purchase purchase1 = new Purchase();

			purchase1.setCreated_by(sCurrentUser);
			purchase1.setMerchantStore(store);
			purchase1.setPurchase_sku(pje.productName);
			purchase1.setPurchase_description(pje.description);
			purchase1.setPurchase_quantity(pje.quantity);
			purchase1.setPurchase_uom(pje.uom);
			purchase1.setPurchase_unit_price(pje.unitPrice);
			purchase1.setPurchase_tax(pje.taxClassId.toString());
			purchase1.setPurchase_amount(pje.amount);

			purchase1.setPurchase_supplier(purchase.getPurchase_supplier());
			purchase1.setPurchase_due_date(purchase.getPurchase_due_date());
			purchase1.setPurchase_date(purchase.getPurchase_date());
			purchase1.setPurchase_ref_number(purchase.getPurchase_ref_number());
			purchase1.setPurchase_comment(purchase.getPurchase_comment());
			purchase1.setEntry_date(purchase.getEntry_date());

			purchaseService.addPurchase(purchase1);
		}
		
		/*Iterator i = jsonArray.iterator();
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

			purchase1.setPurchase_supplier(purchase.getPurchase_supplier());
			purchase1.setPurchase_due_date(purchase.getPurchase_due_date());
			purchase1.setPurchase_date(purchase.getPurchase_date());
			purchase1.setPurchase_ref_number(purchase.getPurchase_ref_number());
			purchase1.setPurchase_comment(purchase.getPurchase_comment());
			purchase1.setEntry_date(purchase.getEntry_date());

			purchaseService.addPurchase(purchase1);
		}*/

		purchase.reset();
		model.addAttribute("purchase", purchase);
		setMenu(model, request, "openingStock");
		return "admin-perchase";

	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createpurchasereturndn.html", method = RequestMethod.POST)
	public String createpurchasereturndnEntery(@Valid @ModelAttribute("purchaseReturnDebitNote") PurchaseReturnDebitNote purchaseReturnDebitNote, BindingResult result, Model model,
			HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		Date date = null;

		/*JSONParser parser = new JSONParser();
		System.out.println("============Json Starts====================>>" + purchaseReturnDebitNote.getJsonArray());
		JSONArray jsonArray = (JSONArray) parser.parse(purchaseReturnDebitNote.getJsonArray());
		System.out.println("============Json JSONParser completes====================");
		System.out.println(jsonArray.toJSONString());*/

		List<ProductJSONEntity> pjeList = ProductJSONEntityService.getProductJSONEntityListFromJsonString(purchaseReturnDebitNote.getProductJson());
		
		if (!StringUtils.isBlank(purchaseReturnDebitNote.getDebit_Sdate())) {
			try {
				date = DateUtil.getDate(purchaseReturnDebitNote.getDebit_Sdate());
				System.out.println("============setDebit_date==========befor==========>>" + purchaseReturnDebitNote.getDebit_Sdate());
				purchaseReturnDebitNote.setDebit_date(date);
			} catch (Exception e) {
				ObjectError error = new ObjectError("purchase_Sdate", messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
		}

		purchaseReturnDebitNote.setEntry_date(new Date());

		purchaseReturnDebitNote.setCreated_by(sCurrentUser);
		purchaseReturnDebitNote.setMerchantStore(store);
		
		for(ProductJSONEntity pje : pjeList) {
			
			PurchaseReturnDebitNote purchaseReturnDebitNote1 = new PurchaseReturnDebitNote();

			purchaseReturnDebitNote1.setCreated_by(sCurrentUser);
			purchaseReturnDebitNote1.setMerchantStore(store);

			purchaseReturnDebitNote1.setDebit_sku(pje.productName);
			purchaseReturnDebitNote1.setDebit_description(pje.description);
			purchaseReturnDebitNote1.setDebit_quantity(pje.quantity);
			purchaseReturnDebitNote1.setDebit_unit_price(pje.unitPrice);
			purchaseReturnDebitNote1.setDebit_tax(pje.taxClassId.toString());
			purchaseReturnDebitNote1.setDebit_tax_amount(pje.taxAmount);
			purchaseReturnDebitNote1.setDebit_amount(pje.amount);
			purchaseReturnDebitNote1.setDebit_invoice_number(pje.invoiceNumber.toString());

			System.out.println("=========purchaseReturnDebitNote1.getDebit_sku===>>>" + purchaseReturnDebitNote1.getDebit_sku());
			purchaseReturnDebitNote1.setDebit_supplier(purchaseReturnDebitNote.getDebit_supplier());
			purchaseReturnDebitNote1.setDebit_date(purchaseReturnDebitNote.getDebit_date());
			purchaseReturnDebitNote1.setDebit_ref_number(purchaseReturnDebitNote.getDebit_ref_number());
			purchaseReturnDebitNote1.setEntry_date(purchaseReturnDebitNote.getEntry_date());
			purchaseReturnDebitNoteService.addPurchaseReturnDebitNote(purchaseReturnDebitNote1);
		}
		
		/*Iterator<JSONObject> i = jsonArray.iterator();
		System.out.println("============Basic Settings completes=========Going to Iterate JsonArray===========");
		while (i.hasNext()) {
			PurchaseReturnDebitNote purchaseReturnDebitNote1 = new PurchaseReturnDebitNote();
			JSONObject slide = (JSONObject) i.next();

			purchaseReturnDebitNote1.setCreated_by(sCurrentUser);
			purchaseReturnDebitNote1.setMerchantStore(store);

			purchaseReturnDebitNote1.setDebit_sku((String) slide.get("product_name"));
			purchaseReturnDebitNote1.setDebit_description((String) slide.get("description"));
			purchaseReturnDebitNote1.setDebit_quantity(Integer.parseInt((String) slide.get("quantity")));
			purchaseReturnDebitNote1.setDebit_unit_price(new BigDecimal(slide.get("unit_price").toString()));
			purchaseReturnDebitNote1.setDebit_tax((String) slide.get("tax"));
			purchaseReturnDebitNote1.setDebit_tax_amount(new BigDecimal(slide.get("tax_amount").toString()));
			purchaseReturnDebitNote1.setDebit_amount(new BigDecimal(slide.get("amount").toString()));
			purchaseReturnDebitNote1.setDebit_invoice_number((String) slide.get("invoice_no"));

			System.out.println("=========purchaseReturnDebitNote1.getDebit_sku===>>>" + purchaseReturnDebitNote1.getDebit_sku());
			purchaseReturnDebitNote1.setDebit_supplier(purchaseReturnDebitNote.getDebit_supplier());
			purchaseReturnDebitNote1.setDebit_date(purchaseReturnDebitNote.getDebit_date());
			purchaseReturnDebitNote1.setDebit_ref_number(purchaseReturnDebitNote.getDebit_ref_number());
			purchaseReturnDebitNote1.setEntry_date(purchaseReturnDebitNote.getEntry_date());
			purchaseReturnDebitNoteService.addPurchaseReturnDebitNote(purchaseReturnDebitNote1);

		}*/

		purchaseReturnDebitNote = new PurchaseReturnDebitNote();
		model.addAttribute("purchaseReturnDebitNote", purchaseReturnDebitNote);
		setMenu(model, request, "purchase");
		return "admin-perchase-returned";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createdebitnoteother.html", method = RequestMethod.POST)
	public String createPurchasereturndnDebitnoteOtherEntery(@Valid @ModelAttribute("debitNoteOther") DebitNoteOther debitNoteOther, BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		debitNoteOther.setDebit_date(convertDate(debitNoteOther.getDebit_Sdate(), "debit_Sdate", result, locale));
		debitNoteOther.setEntry_date(new Date());
		debitNoteOther.setCreated_by(sCurrentUser);
		debitNoteOther.setMerchantStore(store);
		debitNoteOtherService.addDebitNoteOther(debitNoteOther);
		debitNoteOther = new DebitNoteOther();
		model.addAttribute("debitNoteOther", debitNoteOther);
		setMenu(model, request, "purchase");
		return "admin-perchase-returned-other";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/createbranchtransfer.html", method = RequestMethod.POST)
	public String branchTransferEntery(@Valid @ModelAttribute("branchTransfer") BranchTransfer branchTransfer, BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		Date date = null;

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(branchTransfer.getJsonArray());
		branchTransfer.setTransfer_date(convertDate(branchTransfer.getTransfer_Sdate(), "transfer_Sdate", result, locale));

		branchTransfer.setEntry_date(new Date());

		branchTransfer.setEntered_by(sCurrentUser);
		branchTransfer.setMerchantStore(store);
		Iterator i = jsonArray.iterator();

		while (i.hasNext()) {
			BranchTransfer branchTransfer1 = new BranchTransfer();
			JSONObject slide = (JSONObject) i.next();

			branchTransfer1.setEntered_by(sCurrentUser);
			branchTransfer1.setMerchantStore(store);
			branchTransfer1.setEntered_by(branchTransfer.getEntered_by());
			branchTransfer1.setEntry_date(branchTransfer.getEntry_date());

			branchTransfer1.setSku((String) slide.get("SKU"));
			branchTransfer1.setAvailable_quantity(Integer.parseInt((String) slide.get("availability")));
			branchTransfer1.setTransfer_quantity(Integer.parseInt((String) slide.get("quantity")));

			branchTransfer1.setStore_from(branchTransfer.getStore_from());
			branchTransfer1.setStore_to(branchTransfer.getStore_to());
			branchTransfer1.setTransfer_date(branchTransfer.getTransfer_date());
			branchTransfer1.setTransfer_comment(branchTransfer.getTransfer_comment());
			branchTransferService.addBranchTransfer(branchTransfer1);
		}

		branchTransfer = new BranchTransfer();
		model.addAttribute("branchTransfer", branchTransfer);
		setMenu(model, request, "branchTransfer");
		return "admin-branch-transfer";

	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/stocks.html", method = RequestMethod.GET)
	public String displayStocks(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		setMenu(model, request, "openingStock");
		return "admin-stock-list";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/purchasereturndnlist.html", method = RequestMethod.GET)
	public String displayPurchaseReturns(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		setMenu(model, request, "purchase");
		return "admin-perchase-returned-list";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/purchaseEntrylist.html", method = RequestMethod.GET)
	public String displayPurchase(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		setMenu(model, request, "purchase");
		return "admin-perchase-list";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/debitnoteotherlist.html", method = RequestMethod.GET)
	public String displayDebitOther(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		setMenu(model, request, "purchase");
		return "admin-perchase-returned-list-other";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/branchtransferlist.html", method = RequestMethod.GET)
	public String displayBranchTransfer(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		setMenu(model, request, "branchTransfer");
		return "admin-branch-transfer-list";
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

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/purchaseReturn/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String purchaseReturnPaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();

		String sCurrentUser = request.getRemoteUser();

		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
			List<PurchaseReturnDebitNote> purchaseReturnDebitNotes = null;

			if (UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN)) {
				purchaseReturnDebitNotes = purchaseReturnDebitNoteService.getPurchaseReturnDebitNote(store);
				for (PurchaseReturnDebitNote purchaseReturnDebitNote : purchaseReturnDebitNotes) {
					Map entry = new HashMap();
					entry.put("Id", purchaseReturnDebitNote.getId());
					entry.put("Supplier", purchaseReturnDebitNote.getDebit_supplier());
					entry.put("SKU", purchaseReturnDebitNote.getDebit_sku());
					entry.put("Quantity", purchaseReturnDebitNote.getDebit_quantity());
					entry.put("UnitPrice", purchaseReturnDebitNote.getDebit_unit_price());
					entry.put("Amount", purchaseReturnDebitNote.getDebit_amount());
					entry.put("SDate", DateUtil.formatDate(purchaseReturnDebitNote.getDebit_date()));

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

	@Secured("AUTH")
	@RequestMapping(value = "/admin/inventoryManagement/purchaseReturnOther/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String DebitReturnOtherPaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();
		String sCurrentUser = request.getRemoteUser();

		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
			List<DebitNoteOther> debitNoteOthers = null;

			if (UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN)) {
				debitNoteOthers = debitNoteOtherService.getDebitNoteOther(store);
				for (DebitNoteOther debitNoteOther : debitNoteOthers) {
					Map entry = new HashMap();
					entry.put("Id", debitNoteOther.getId());
					entry.put("Debit_supplier", debitNoteOther.getDebit_supplier());
					entry.put("SDate", DateUtil.formatDate(debitNoteOther.getDebit_date()));
					entry.put("Debit_ref_number", debitNoteOther.getDebit_ref_number());
					entry.put("Amount", debitNoteOther.getDebit_amount());
					entry.put("Debit_tax", debitNoteOther.getDebit_tax());
					entry.put("Debit_total_amount", debitNoteOther.getDebit_total_amount());
					entry.put("Debit_credit_to", debitNoteOther.getDebit_credit_to());
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
	@RequestMapping(value = "/admin/inventoryManagement/branchTransfer/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String branchTransferPaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();
		String sCurrentUser = request.getRemoteUser();

		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
			List<BranchTransfer> branchTransfers = null;

			if (UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN)) {
				branchTransfers = branchTransferService.getBranchTransfer(store);
				for (BranchTransfer branchTransfer : branchTransfers) {
					Map entry = new HashMap();
					entry.put("Id", branchTransfer.getId());
					entry.put("store_from", branchTransfer.getStore_from());
					entry.put("store_to", branchTransfer.getStore_to());
					entry.put("SDate", DateUtil.formatDate(branchTransfer.getTransfer_date()));
					entry.put("comment", branchTransfer.getTransfer_comment());
					entry.put("sku", branchTransfer.getSku());
					entry.put("availability", branchTransfer.getAvailable_quantity());
					entry.put("quantity", branchTransfer.getTransfer_quantity());
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
