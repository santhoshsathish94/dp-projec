package com.salesmanager.web.admin.controller.accountmanagement;

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
import com.salesmanager.core.business.user.model.User;
import com.salesmanager.core.business.user.service.UserService;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.DateUtil;
import com.salesmanager.web.utils.LabelUtils;
import com.salesmanager.web.utils.UserUtils;

@Controller
public class AccountManagementController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagementController.class);
	// managment
	@Autowired
	UserService userService;

	@Autowired
	StockService stockService;

	@Autowired
	LabelUtils messages;

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createReceipt.html", method = RequestMethod.GET)
	public String createReceipt(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "cash-bank-receipt");
		return "admin-account-management-receipt";
	}
	
	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/receipts.html", method = RequestMethod.GET)
	public String listReceipt(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "cash-bank-receipt");
		return "admin-account-management-receipt-list";
	}
	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createPayment.html", method = RequestMethod.GET)
	public String createPayment(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "cash-bank-payment");
		return "admin-account-management-create-payment";
	}
	
	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/payments.html", method = RequestMethod.GET)
	public String payments(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "cash-bank-payment");
		return "admin-account-management-payment-list";
	}
	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createExpense.html", method = RequestMethod.GET)
	public String createExpense(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "expense");
		return "admin-account-management-expense-entry";
	}
	
	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/expenses.html", method = RequestMethod.GET)
	public String expenses(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "expense");
		return "admin-account-management-expense-list";
	}
	
	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createJournal.html", method = RequestMethod.GET)
	public String createJournal(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "journal");
		return "admin-account-management-create-journal";
	}
	
	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/journals.html", method = RequestMethod.GET)
	public String journals(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Stock stock = new Stock();
		model.addAttribute("stock", stock);
		setMenu(model, request, "journal");
		return "admin-account-management-journal-list";
	}
	private void setMenu(Model model, HttpServletRequest request, String setActiveMenus) throws Exception {

		// display menu
		Map<String, String> activeMenus = new HashMap<String, String>();
		activeMenus.put("account_Management", "account_Management");
		activeMenus.put(setActiveMenus, setActiveMenus);

		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>) request.getAttribute("MENUMAP");

		Menu currentMenu = (Menu) menus.get("account_Management");
		model.addAttribute("currentMenu", currentMenu);
		model.addAttribute("activeMenus", activeMenus);
	}

	private Date convertDate(String sDate, String dateFieldName, BindingResult result, Locale locale) {

		if (!StringUtils.isBlank(sDate)) {
			try {
				return DateUtil.getDate(sDate);

			} catch (Exception e) {
				ObjectError error = new ObjectError("purchase_Sdate", messages.getMessage("message.invalid.date", locale));
				result.addError(error);
				return null;
			}
		} else {
			return null;
		}

	}

}
