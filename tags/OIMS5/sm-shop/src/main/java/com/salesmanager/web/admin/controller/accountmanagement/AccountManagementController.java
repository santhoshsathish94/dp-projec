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

import com.salesmanager.core.business.accountmanagement.model.Expense;
import com.salesmanager.core.business.accountmanagement.model.Journal;
import com.salesmanager.core.business.accountmanagement.model.Payment;
import com.salesmanager.core.business.accountmanagement.model.Receipt;
import com.salesmanager.core.business.accountmanagement.service.ExpenseService;
import com.salesmanager.core.business.accountmanagement.service.JournalService;
import com.salesmanager.core.business.accountmanagement.service.PaymentService;
import com.salesmanager.core.business.accountmanagement.service.ReceiptService;
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
	ReceiptService receiptService;

	@Autowired
	PaymentService accountmanagement_paymentService;

	@Autowired
	JournalService journalService;

	@Autowired
	ExpenseService expenseService;

	@Autowired
	LabelUtils messages;

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createReceipt.html", method = RequestMethod.GET)
	public String createReceipt(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Receipt receipt = new Receipt();
		model.addAttribute("receipt", receipt);
		setMenu(model, request, "cash-bank-receipt");
		return "admin-account-management-receipt";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/receipts.html", method = RequestMethod.GET)
	public String listReceipt(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Receipt receipt = new Receipt();
		model.addAttribute("receipt", receipt);
		setMenu(model, request, "cash-bank-receipt");
		return "admin-account-management-receipt-list";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createPayment.html", method = RequestMethod.GET)
	public String createPayment(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Payment payment = new Payment();
		model.addAttribute("payment", payment);
		setMenu(model, request, "cash-bank-payment");
		return "admin-account-management-create-payment";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/payments.html", method = RequestMethod.GET)
	public String payments(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Payment payment = new Payment();
		model.addAttribute("payment", payment);
		setMenu(model, request, "cash-bank-payment");
		return "admin-account-management-payment-list";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createExpense.html", method = RequestMethod.GET)
	public String createExpense(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Expense expense = new Expense();
		model.addAttribute("Expense", expense);
		setMenu(model, request, "expense");
		return "admin-account-management-expense-entry";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/expenses.html", method = RequestMethod.GET)
	public String expenses(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Expense expense = new Expense();
		model.addAttribute("Expense", expense);
		setMenu(model, request, "expense");
		return "admin-account-management-expense-list";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createJournal.html", method = RequestMethod.GET)
	public String createJournal(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Journal journal = new Journal();
		model.addAttribute("journal", journal);
		setMenu(model, request, "journal");
		return "admin-account-management-create-journal";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/journals.html", method = RequestMethod.GET)
	public String journals(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		// display menu
		Journal journal = new Journal();
		model.addAttribute("journal", journal);
		setMenu(model, request, "journal");
		return "admin-account-management-journal-list";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createReceipt.html", method = RequestMethod.POST)
	public String createReceipt(@Valid @ModelAttribute("receipt") Receipt receipt, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale)
			throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);

		receipt.setEntered_by(sCurrentUser);
		receipt.setMerchantStore(store);
		receipt.setEntry_date(new Date());

		receipt.setReceipt_date(convertDate(receipt.getReceipt_Sdate(), "receipt_Sdate", result, locale));

		receiptService.addReceipt(receipt);

		model.addAttribute("receipt", new Receipt());
		setMenu(model, request, "cash-bank-receipt");
		return "admin-account-management-receipt";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createPayment.html", method = RequestMethod.POST)
	public String createPayment(@Valid @ModelAttribute("payment") Payment payment, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale)
			throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);

		payment.setEntered_by(sCurrentUser);
		payment.setMerchantStore(store);
		payment.setEntry_date(new Date());

		payment.setPayment_date(convertDate(payment.getPayment_Sdate(), "payment_Sdate", result, locale));

		accountmanagement_paymentService.addPayment(payment);

		model.addAttribute("payment", new Payment());
		setMenu(model, request, "cash-bank-payment");
		return "admin-account-management-create-payment";
	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createJournal.html", method = RequestMethod.POST)
	public String createJournal(@Valid @ModelAttribute("journal") Journal journal, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale)
			throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		Date date = null;

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(journal.getJsonArray());
		JSONArray jsonArray1 = (JSONArray) parser.parse(journal.getJsonArray1());

		journal.setEntry_date(new Date());
		journal.setEntered_by(sCurrentUser);
		journal.setMerchantStore(store);

		journal.setJournal_date(convertDate(journal.getJournal_Sdate(), "journal_Sdate", result, locale));

		Iterator i = jsonArray.iterator();
		while (i.hasNext()) {
			Journal journal1 = new Journal();
			JSONObject slide = (JSONObject) i.next();

			journal1.setEntered_by(sCurrentUser);
			journal1.setMerchantStore(store);
			journal1.setEntry_date(journal.getEntry_date());

			journal1.setJournal_ref_no(journal.getJournal_ref_no());
			journal1.setJournal_date(journal.getJournal_date());
			journal1.setJournal_narration(journal.getJournal_narration());

			journal1.setJournal_debit((String) slide.get("journal_debit"));
			journal1.setJournal_debit_ammount(new BigDecimal(slide.get("journal_debit_ammount").toString()));

			journalService.addJournal(journal1);
		}

		i = jsonArray1.iterator();
		while (i.hasNext()) {
			Journal journal1 = new Journal();
			JSONObject slide = (JSONObject) i.next();

			journal1.setEntered_by(sCurrentUser);
			journal1.setMerchantStore(store);
			journal1.setEntry_date(journal.getEntry_date());

			journal1.setJournal_ref_no(journal.getJournal_ref_no());
			journal1.setJournal_date(journal.getJournal_date());
			journal1.setJournal_narration(journal.getJournal_narration());

			journal1.setJournal_credit((String) slide.get("journal_credit"));
			journal1.setJournal_credit_ammount(new BigDecimal(slide.get("journal_credit_ammount").toString()));

			journalService.addJournal(journal1);
		}

		model.addAttribute("journal", new Journal());
		setMenu(model, request, "journal");
		return "admin-account-management-create-journal";

	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/account/createExpense.html", method = RequestMethod.POST)
	public String createExpense(@Valid @ModelAttribute("expense") Expense expense, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale)
			throws Exception {
		// display menu
		String sCurrentUser = request.getRemoteUser();
		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
		Date date = null;

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(expense.getJsonArray());

		expense.setEntry_date(new Date());
		expense.setEntered_by(sCurrentUser);
		expense.setMerchantStore(store);

		expense.setExpense_date(convertDate(expense.getExpense_Sdate(), "expense_Sdate", result, locale));

		Iterator i = jsonArray.iterator();

		while (i.hasNext()) {
			Expense expense1 = new Expense();
			JSONObject slide = (JSONObject) i.next();

			expense1.setEntered_by(sCurrentUser);
			expense1.setMerchantStore(store);
			expense1.setEntry_date(expense.getEntry_date());

			expense1.setExpense_date(expense.getExpense_date());
			expense1.setExpense_ref_no(expense.getExpense_ref_no());
			expense1.setExpense_comment(expense.getExpense_comment());
			expense1.setExpense_payment_mode(expense.getExpense_payment_mode());

			expense1.setExpense((String) slide.get("expense_debit"));
			expense1.setExpense_ammount(new BigDecimal(slide.get("expense_debit_ammount").toString()));

			expenseService.addExpense(expense1);
		}

		model.addAttribute("expense", new Expense());
		setMenu(model, request, "expense");
		return "admin-account-management-create-expense";

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
				ObjectError error = new ObjectError(dateFieldName, messages.getMessage("message.invalid.date", locale));
				result.addError(error);
				return null;
			}
		} else {
			return null;
		}

	}

	@Secured("AUTH")
	@RequestMapping(value = "/admin/accountManagement/payment/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String paymentPaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();
		String sCurrentUser = request.getRemoteUser();

		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
			List<Payment> payments = null;

			if (UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN)) {
				payments = accountmanagement_paymentService.getPayment(store);
				for (Payment payment : payments) {
					Map entry = new HashMap();
					entry.put("Id", payment.getId());
					entry.put("To", payment.getPayment_to());
					entry.put("Date", DateUtil.formatDate(payment.getPayment_date()));
					entry.put("Amount", payment.getPayment_ammount());
					entry.put("Mode", payment.getPayment_mode());
					entry.put("Tran-No", payment.getPayment_transaction_no());
					entry.put("Comment", payment.getPayment_comment());

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
	@RequestMapping(value = "/admin/accountManagement/receipt/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String receiptPaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();
		String sCurrentUser = request.getRemoteUser();

		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
			List<Receipt> receipts = null;

			if (UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN)) {
				receipts = receiptService.getReceipt(store);
				for (Receipt receipt : receipts) {
					Map entry = new HashMap();
					entry.put("Id", receipt.getId());
					entry.put("From", receipt.getReceipt_from());
					entry.put("Date", DateUtil.formatDate(receipt.getReceipt_date()));
					entry.put("Amount", receipt.getReceipt_ammount());
					entry.put("Mode", receipt.getReceipt_mode());
					entry.put("Tran-No", receipt.getReceipt_transaction_no());
					entry.put("Comment", receipt.getReceipt_comment());

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
	@RequestMapping(value = "/admin/accountManagement/expense/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String expensePaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();
		String sCurrentUser = request.getRemoteUser();

		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
			List<Expense> expenses = null;

			if (UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN)) {
				expenses = expenseService.getExpense(store);
				for (Expense expense : expenses) {
					Map entry = new HashMap();
					entry.put("Id", expense.getId());
					entry.put("Expense", expense.getExpense());
					entry.put("Date", DateUtil.formatDate(expense.getExpense_date()));
					entry.put("Amount", expense.getExpense_ammount());
					entry.put("Mode", expense.getExpense_payment_mode());
					entry.put("Ref-No", expense.getExpense_ref_no());
					entry.put("Comment", expense.getExpense_comment());

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
	@RequestMapping(value = "/admin/accountManagement/journal/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String journalPaging(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();
		String sCurrentUser = request.getRemoteUser();

		try {

			User currentUser = userService.getByUserName(sCurrentUser);
			MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
			List<Journal> journals = null;

			if (UserUtils.userInGroup(currentUser, Constants.GROUP_ADMIN)) {
				journals = journalService.getJournal(store);
				for (Journal journal : journals) {
					Map entry = new HashMap();
					entry.put("Id", journal.getId());
					entry.put("Ref-No", journal.getJournal_ref_no());
					entry.put("Date", DateUtil.formatDate(journal.getJournal_date()));
					entry.put("Debit", journal.getJournal_debit());
					entry.put("Debit-Amount", journal.getJournal_debit_ammount());
					entry.put("Credit", journal.getJournal_credit());
					entry.put("Credit-Amount", journal.getJournal_credit_ammount());
					entry.put("Narration", journal.getJournal_narration());

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

}
