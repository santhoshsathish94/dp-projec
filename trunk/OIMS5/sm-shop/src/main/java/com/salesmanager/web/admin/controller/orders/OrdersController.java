package com.salesmanager.web.admin.controller.orders;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductAttribute;
import com.salesmanager.core.business.catalog.product.service.ProductService;
import com.salesmanager.core.business.catalog.product.service.attribute.ProductAttributeService;
import com.salesmanager.core.business.common.model.ProductJSONEntity;
import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.customer.service.CustomerService;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.order.model.Order;
import com.salesmanager.core.business.order.model.OrderCriteria;
import com.salesmanager.core.business.order.model.OrderList;
import com.salesmanager.core.business.order.model.OrderTotal;
import com.salesmanager.core.business.order.model.SalesOrderBooking;
import com.salesmanager.core.business.order.model.orderproduct.OrderProduct;
import com.salesmanager.core.business.order.model.orderproduct.OrderProductPrice;
import com.salesmanager.core.business.order.model.orderstatus.OrderStatus;
import com.salesmanager.core.business.order.model.orderstatus.OrderStatusHistory;
import com.salesmanager.core.business.order.service.OrderService;
import com.salesmanager.core.business.payments.model.PaymentType;
import com.salesmanager.core.business.reference.currency.service.CurrencyService;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.service.utils.LogicUtils;
import com.salesmanager.core.business.service.utils.ProductJSONEntityService;
import com.salesmanager.core.business.system.model.IntegrationModule;
import com.salesmanager.core.business.system.service.ModuleConfigurationService;
import com.salesmanager.core.business.tax.model.taxclass.TaxClass;
import com.salesmanager.core.business.tax.service.TaxClassService;
import com.salesmanager.core.utils.ProductPriceUtils;
import com.salesmanager.core.utils.ajax.AjaxPageableResponse;
import com.salesmanager.core.utils.ajax.AjaxResponse;
import com.salesmanager.web.admin.controller.ControllerConstants;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.DateUtil;
import com.salesmanager.web.utils.LabelUtils;

/**
 * Manage order list Manage search order
 * 
 * @author csamson
 * 
 */
@Controller
public class OrdersController {

	@Autowired
	OrderService orderService;

	@Autowired
	LabelUtils messages;

	@Autowired
	private ProductPriceUtils priceUtil;

	@Autowired
	protected ModuleConfigurationService moduleConfigurationService;

	@Autowired
	private TaxClassService taxClassService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	protected CurrencyService currencyService;

	@Autowired
	protected ProductService productService;

	@Autowired
	protected ProductAttributeService productAttributeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderControler.class);

	@Secured("ORDER")
	@RequestMapping(value = "/admin/orders/list.html", method = RequestMethod.GET)
	public String displayOrders(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		setMenu(model, request, "order");

		// the list of orders is from page method

		return ControllerConstants.Tiles.Order.orders;

	}

	@Secured("ORDER")
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/admin/orders/paging.html", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String pageOrders(HttpServletRequest request, HttpServletResponse response, Locale locale) {

		AjaxPageableResponse resp = new AjaxPageableResponse();

		try {

			int startRow = Integer.parseInt(request.getParameter("_startRow"));
			int endRow = Integer.parseInt(request.getParameter("_endRow"));
			String paymentModule = request.getParameter("paymentModule");
			String customerName = request.getParameter("customer");

			OrderCriteria criteria = new OrderCriteria();
			criteria.setStartIndex(startRow);
			criteria.setMaxCount(endRow);
			if (!StringUtils.isBlank(paymentModule)) {
				criteria.setPaymentMethod(paymentModule);
			}

			if (!StringUtils.isBlank(customerName)) {
				criteria.setCustomerName(customerName);
			}

			Language language = (Language) request.getAttribute("LANGUAGE");
			MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);
			List<IntegrationModule> paymentModules = moduleConfigurationService.getIntegrationModules("PAYMENT");

			OrderList orderList = orderService.listByStore(store, criteria);
			// List<Order> orders = orderService.listByStore(store);

			if (orderList.getOrders() != null) {

				for (Order order : orderList.getOrders()) {

					@SuppressWarnings("rawtypes")
					Map entry = new HashMap();
					entry.put("orderId", order.getId());
					entry.put("customer", order.getBilling().getFirstName() + " " + order.getBilling().getLastName());
					entry.put("amount", priceUtil.getAdminFormatedAmountWithCurrency(store, order.getTotal()));// todo
																												// format
																												// total
					entry.put("date", DateUtil.formatDate(order.getDatePurchased()));
					entry.put("status", order.getStatus().name());

					if (paymentModules != null && paymentModules.size() > 0) {
						for (int index = 0; index < paymentModules.size(); index++) {
							if (paymentModules.get(index).getCode().equalsIgnoreCase(order.getPaymentModuleCode())) {
								paymentModule = paymentModules.get(index).getCode();
								break;
							}
						}

					} else {

						paymentModule = PaymentType.COD.toString();
					}

					entry.put("paymentModule", paymentModule);
					resp.addDataEntry(entry);

				}
			}

			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);

		} catch (Exception e) {
			LOGGER.error("Error while paging orders", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}

		String returnString = resp.toJSONString();

		return returnString;
	}

	private void setMenu(Model model, HttpServletRequest request, String selectedMenu) throws Exception {

		// display menu
		Map<String, String> activeMenus = new HashMap<String, String>();
		activeMenus.put("order", "order");
		activeMenus.put(selectedMenu, selectedMenu);

		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>) request.getAttribute("MENUMAP");

		Menu currentMenu = (Menu) menus.get("order");
		model.addAttribute("currentMenu", currentMenu);
		model.addAttribute("activeMenus", activeMenus);
		//
	}

	@Secured("ORDER")
	@RequestMapping(value = "/admin/orders/createsaleorderbooking.html", method = RequestMethod.GET)
	public String salesOrderBooking(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		SalesOrderBooking salesOrderBooking = new SalesOrderBooking();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		salesOrderBooking.setBookingDate(format.format(new Date()));

		return displaySalesOrderBooking(salesOrderBooking, model, request, response, locale);
	}

	private String displaySalesOrderBooking(SalesOrderBooking salesOrderBooking, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		setMenu(model, request, "sale-order-booking");

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

		model.addAttribute("salesOrderBooking", salesOrderBooking);

		return "admin-create-sales-order-booking";
	}

	@Secured("ORDER")
	@RequestMapping(value = "/admin/orders/savesalesorderbooking.html", method = RequestMethod.POST)
	public String saveCreditNote(@Valid @ModelAttribute("salesOrderBooking") SalesOrderBooking salesOrderBooking, BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response, Locale locale) throws Exception {

		MerchantStore store = (MerchantStore) request.getAttribute(Constants.ADMIN_STORE);

		Customer adminOrderBookingCustomer = customerService.getById(salesOrderBooking.getCustomerId());

		// Currency currency = currencyService.getByCode("CAD");

		Date datePurchased = new Date();
		if (!StringUtils.isBlank(salesOrderBooking.getBookingDate())) {
			try {
				datePurchased = DateUtil.getDate(salesOrderBooking.getBookingDate());
			} catch (Exception e) {
				ObjectError error = new ObjectError("datePurchased", messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}

		}

		Order order = new Order();

		order.setDatePurchased(datePurchased);
		order.setCurrency(store.getCurrency());
		order.setLastModified(new Date());
		order.setBilling(adminOrderBookingCustomer.getBilling());

		order.setCustomerId(salesOrderBooking.getCustomerId());
		order.setDelivery(adminOrderBookingCustomer.getDelivery());
		order.setCustomerEmailAddress(adminOrderBookingCustomer.getEmailAddress());
		order.setIpAddress(request.getRemoteAddr());
		order.setMerchant(store);
		order.setOrderDateFinished(new Date());

		OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
		orderStatusHistory.setComments(salesOrderBooking.getComment());
		orderStatusHistory.setCustomerNotified(1);
		orderStatusHistory.setStatus(OrderStatus.ORDERED);
		orderStatusHistory.setDateAdded(new Date());
		orderStatusHistory.setOrder(order);

		order.getOrderHistory().add(orderStatusHistory);

		order.setPaymentType(PaymentType.COD);
		order.setPaymentModuleCode(PaymentType.COD.toString());
		order.setStatus(OrderStatus.ORDERED);
		// order.setTotal(calculateTotalOrderAmount(salesOrderBooking.productJson));

		order = calculateOrderProductTotal(order, salesOrderBooking.productJson);

		orderService.saveOrUpdate(order);

		return displayOrders(model, request, response);
	}

	private Order calculateOrderProductTotal(Order order, String productJson) {

		List<ProductJSONEntity> pjeList = ProductJSONEntityService.getProductJSONEntityListFromJsonString(productJson);

		Product prod = null;
		BigDecimal totalBSaseAmount = new BigDecimal(0);
		BigDecimal totalTaxAmount = new BigDecimal(0);
		BigDecimal totalAmount = new BigDecimal(0);

		for (ProductJSONEntity pje : pjeList) {
			if (pje.variantId == null) {
				prod = productService.getById(pje.productId);
			} else {
				ProductAttribute poa = productAttributeService.getById(pje.variantId);
				prod = productService.getById(poa.getProduct().getId());
			}

			// OrderProductPrice
			OrderProductPrice oproductprice = new OrderProductPrice();
			oproductprice.setDefaultPrice(true);
			oproductprice.setProductPrice(prod.getAvailabilities().iterator().next().getPrices().iterator().next().getProductPriceAmount());
			oproductprice.setProductPriceCode("baseprice");
			oproductprice.setProductPriceName("Base Price");
			// oproductprice.setProductPriceSpecialAmount(new BigDecimal(13.99)
			// );

			// OrderProduct
			OrderProduct oproduct = new OrderProduct();
			// oproduct.getDownloads().add(orderProductDownload);
			int quantity = pje.quantity;
			BigDecimal taxAmount = null;
			BigDecimal unitPrice = new BigDecimal(0);

			if (pje.variantId == null) {
				prod = productService.getById(pje.productId);
			} else {
				ProductAttribute poa = productAttributeService.getById(pje.variantId);
				prod = productService.getById(poa.getProduct().getId());
			}

			unitPrice = oproductprice.getProductPrice();

			if (pje.taxClassId != null) {
				taxAmount = taxClassService.getById(pje.taxClassId).getTaxRates().iterator().next().getTaxRate();
			}

			totalBSaseAmount = totalBSaseAmount.add(unitPrice.multiply(new BigDecimal(quantity)));
			if (taxAmount == null) {
				totalAmount = totalAmount.add(unitPrice.multiply(new BigDecimal(quantity)));
			} else {
				BigDecimal taxableAmount = unitPrice.multiply(new BigDecimal(quantity)).multiply(taxAmount).divide(new BigDecimal(100));
				totalTaxAmount = totalTaxAmount.add(taxableAmount);
				totalAmount = totalAmount.add(unitPrice.multiply(new BigDecimal(quantity)).add(taxableAmount));
			}

			oproduct.setOneTimeCharge(oproductprice.getProductPrice());
			
			oproduct.setOrder(order);
			oproduct.setProductName(prod.getDescriptions().iterator().next().getName());
			oproduct.setProductQuantity(pje.quantity);
			oproduct.setSku(prod.getSku());
			oproduct.getPrices().add(oproductprice);
			oproduct.setPrices(oproduct.getPrices());

			oproductprice.setOrderProduct(oproduct);
			oproductprice.setOrderProduct(oproduct);
			// orderProductDownload.setOrderProduct(oproduct);
			order.getOrderProducts().add(oproduct);

		}
		// OrderTotal
		order.setTotal(totalAmount);
		OrderTotal subtotal = new OrderTotal();
		subtotal.setModule("summary");
		subtotal.setSortOrder(0);
		subtotal.setText("Summary");
		subtotal.setTitle("Summary");
		subtotal.setOrderTotalCode("subtotal");
		subtotal.setValue(totalBSaseAmount);
		subtotal.setOrder(order);

		order.getOrderTotal().add(subtotal);

		OrderTotal tax = new OrderTotal();
		tax.setModule("tax");
		tax.setSortOrder(1);
		tax.setText("Tax");
		tax.setTitle("Tax");
		tax.setOrderTotalCode("tax");
		tax.setValue(totalTaxAmount);
		tax.setOrder(order);

		order.getOrderTotal().add(tax);

		OrderTotal total = new OrderTotal();
		total.setModule("total");
		total.setSortOrder(2);
		total.setText("Total");
		total.setTitle("Total");
		total.setOrderTotalCode("total");
		total.setValue(totalAmount);
		total.setOrder(order);

		order.getOrderTotal().add(total);

		return order;
	}

	private BigDecimal calculateTotalOrderAmount(String productJson) {

		BigDecimal totalBSaseAmount = new BigDecimal(0);
		BigDecimal totalTaxAmount = new BigDecimal(0);
		BigDecimal totalAmount = new BigDecimal(0);

		List<ProductJSONEntity> pjeList = ProductJSONEntityService.getProductJSONEntityListFromJsonString(productJson);

		for (ProductJSONEntity pje : pjeList) {

			int quantity = pje.quantity;
			BigDecimal taxAmount = null;
			BigDecimal unitPrice = new BigDecimal(0);

			Product prod = null;

			if (pje.variantId == null) {
				prod = productService.getById(pje.productId);
			} else {
				ProductAttribute poa = productAttributeService.getById(pje.variantId);
				prod = productService.getById(poa.getProduct().getId());
			}

			unitPrice = prod.getAvailabilities().iterator().next().getPrices().iterator().next().getProductPriceAmount();

			if (pje.taxClassId != null) {
				taxAmount = taxClassService.getById(pje.taxClassId).getTaxRates().iterator().next().getTaxRate();
			}

			totalBSaseAmount = totalBSaseAmount.add(totalAmount.add(unitPrice.multiply(new BigDecimal(quantity))));
			if (taxAmount == null) {
				totalAmount = totalAmount.add(unitPrice.multiply(new BigDecimal(quantity)));
			} else {
				BigDecimal taxableAmount = unitPrice.multiply(new BigDecimal(quantity)).multiply(taxAmount).divide(new BigDecimal(100));
				totalTaxAmount = totalTaxAmount.add(taxableAmount);
				totalAmount = totalAmount.add(unitPrice.multiply(new BigDecimal(quantity)).add(taxableAmount));
			}
		}

		return totalAmount;
	}

}
