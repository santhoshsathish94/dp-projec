package com.salesmanager.test.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.salesmanager.core.business.catalog.category.model.Category;
import com.salesmanager.core.business.catalog.category.model.CategoryDescription;
import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.availability.ProductAvailability;
import com.salesmanager.core.business.catalog.product.model.description.ProductDescription;
import com.salesmanager.core.business.catalog.product.model.manufacturer.Manufacturer;
import com.salesmanager.core.business.catalog.product.model.manufacturer.ManufacturerDescription;
import com.salesmanager.core.business.catalog.product.model.price.ProductPrice;
import com.salesmanager.core.business.catalog.product.model.price.ProductPriceDescription;
import com.salesmanager.core.business.catalog.product.model.type.ProductType;
import com.salesmanager.core.business.common.model.Billing;
import com.salesmanager.core.business.common.model.Delivery;
import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.customer.model.CustomerGender;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.order.model.Order;
import com.salesmanager.core.business.order.model.OrderCriteria;
import com.salesmanager.core.business.order.model.OrderList;
import com.salesmanager.core.business.order.model.OrderTotal;
import com.salesmanager.core.business.order.model.orderproduct.OrderProduct;
import com.salesmanager.core.business.order.model.orderproduct.OrderProductDownload;
import com.salesmanager.core.business.order.model.orderproduct.OrderProductPrice;
import com.salesmanager.core.business.order.model.orderstatus.OrderStatus;
import com.salesmanager.core.business.order.model.orderstatus.OrderStatusHistory;
import com.salesmanager.core.business.order.model.payment.CreditCard;
import com.salesmanager.core.business.payments.model.CreditCardType;
import com.salesmanager.core.business.payments.model.PaymentType;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.currency.model.Currency;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.reference.zone.model.Zone;
import com.salesmanager.core.business.user.model.Group;
import com.salesmanager.core.business.user.model.GroupType;
import com.salesmanager.core.business.user.service.GroupService;
import com.salesmanager.test.core.AbstractSalesManagerCoreTestCase;

public class OrderSalesManagerTestCase extends AbstractSalesManagerCoreTestCase {

	// @Ignore

	@Test
	public void createOrderwithExisingProductAndCostomer() throws ServiceException {

		Date date = new Date(System.currentTimeMillis());

		Language en = languageService.getByCode("en");
		Language fr = languageService.getByCode("fr");

		Country canada = countryService.getByCode("CA");
		Zone zone = zoneService.getByCode("QC");

		// create a merchant
		MerchantStore store = merchantService.getMerchantStore(MerchantStore.DEFAULT_STORE);
		ProductType generalType = productTypeService.getProductType(ProductType.GENERAL_TYPE);

		

		

		
		


		

		
		// Create a customer (user name[nick] : shopizer password : password)

		Customer customer = new Customer();
		customer.setMerchantStore(store);
		customer.setEmailAddress("test@shopizer.com");
		customer.setGender(CustomerGender.M);
		customer.setAnonymous(false);
		customer.setCompany("CSTI Consulting");
		customer.setDateOfBirth(new Date());

		customer.setDefaultLanguage(en);
		customer.setNick("shopizer");

		// String password = passwordEncoder.encodePassword("password", null);
		String password = "password";
		customer.setPassword(password);

		List<Group> groups = groupService.listGroup(GroupType.CUSTOMER);

		for (Group group : groups) {
			if (group.getGroupName().equals("CUSTOMER")) {
				customer.getGroups().add(group);
			}
		}

		Delivery delivery = new Delivery();
		delivery.setAddress("358 Du Languadoc");
		delivery.setCity("Boucherville");
		delivery.setCountry(canada);
		// delivery.setCountryCode(canada.getIsoCode());
		delivery.setFirstName("Leonardo");
		delivery.setLastName("DiCaprio");
		delivery.setPostalCode("J4B-8J9");
		delivery.setZone(zone);

		Billing billing = new Billing();
		billing.setAddress("358 Du Languadoc");
		billing.setCity("Boucherville");
		billing.setCompany("CSTI Consulting");
		billing.setCountry(canada);
		// billing.setCountryCode(canada.getIsoCode());
		billing.setFirstName("Leonardo");
		billing.setLastName("DiCaprio");
		billing.setPostalCode("J4B-8J9");
		billing.setZone(zone);

		customer.setBilling(billing);
		customer.setDelivery(delivery);
		customerService.create(customer);

		Currency currency = currencyService.getByCode("CAD");

		OrderStatusHistory orderStatusHistory = new OrderStatusHistory();

		// create an order

		Order order = new Order();
		order.setDatePurchased(new Date());
		order.setCurrency(currency);
		order.setLastModified(new Date());
		order.setBilling(billing);

		order.setCurrencyValue(new BigDecimal(0.98));// compared to based
														// currency (not
														// necessary)
		order.setCustomerId(customer.getId());
		order.setBilling(billing);
		order.setDelivery(delivery);
		order.setCustomerEmailAddress("leo@shopizer.com");
		order.setDelivery(delivery);
		order.setIpAddress("ipAddress");
		order.setMerchant(store);
		order.setOrderDateFinished(new Date());// committed date

		orderStatusHistory.setComments("We received your order");
		orderStatusHistory.setCustomerNotified(1);
		orderStatusHistory.setStatus(OrderStatus.ORDERED);
		orderStatusHistory.setDateAdded(new Date());
		orderStatusHistory.setOrder(order);
		order.getOrderHistory().add(orderStatusHistory);

		order.setPaymentType(PaymentType.PAYPAL);
		order.setPaymentModuleCode("paypal");
		order.setStatus(OrderStatus.DELIVERED);
		order.setTotal(new BigDecimal(23.99));

		// OrderProductDownload - Digital download
		OrderProductDownload orderProductDownload = new OrderProductDownload();
		orderProductDownload.setDownloadCount(1);
		orderProductDownload.setMaxdays(31);
		orderProductDownload.setOrderProductFilename("Your digital file name");

		// OrderProductPrice
		OrderProductPrice oproductprice = new OrderProductPrice();
		oproductprice.setDefaultPrice(true);
		oproductprice.setProductPrice(new BigDecimal(19.99));
		oproductprice.setProductPriceCode("baseprice");
		oproductprice.setProductPriceName("Base Price");
		// oproductprice.setProductPriceSpecialAmount(new BigDecimal(13.99) );

		// OrderProduct
		OrderProduct oproduct = new OrderProduct();
		oproduct.getDownloads().add(orderProductDownload);
		oproduct.setOneTimeCharge(new BigDecimal(19.99));
		oproduct.setOrder(order);
		oproduct.setProductName("Product name");
		oproduct.setProductQuantity(1);
		oproduct.setSku("TB12345");
		oproduct.getPrices().add(oproductprice);

		oproductprice.setOrderProduct(oproduct);
		orderProductDownload.setOrderProduct(oproduct);
		order.getOrderProducts().add(oproduct);

		// OrderTotal
		OrderTotal subtotal = new OrderTotal();
		subtotal.setModule("summary");
		subtotal.setSortOrder(0);
		subtotal.setText("Summary");
		subtotal.setTitle("Summary");
		subtotal.setOrderTotalCode("subtotal");
		subtotal.setValue(new BigDecimal(19.99));
		subtotal.setOrder(order);

		order.getOrderTotal().add(subtotal);

		OrderTotal tax = new OrderTotal();
		tax.setModule("tax");
		tax.setSortOrder(1);
		tax.setText("Tax");
		tax.setTitle("Tax");
		tax.setOrderTotalCode("tax");
		tax.setValue(new BigDecimal(4));
		tax.setOrder(order);

		order.getOrderTotal().add(tax);

		OrderTotal total = new OrderTotal();
		total.setModule("total");
		total.setSortOrder(2);
		total.setText("Total");
		total.setTitle("Total");
		total.setOrderTotalCode("total");
		total.setValue(new BigDecimal(23.99));
		total.setOrder(order);

		order.getOrderTotal().add(total);

		orderService.saveOrUpdate(order);

		Assert.assertTrue(orderService.count() == 1);
	}

	@Test
	public void createOrder1() throws ServiceException {

		Date date = new Date(System.currentTimeMillis());

		Language en = languageService.getByCode("en");
		Language fr = languageService.getByCode("fr");

		Country canada = countryService.getByCode("CA");
		Zone zone = zoneService.getByCode("QC");

		// create a merchant
		MerchantStore store = merchantService.getMerchantStore(MerchantStore.DEFAULT_STORE);
		ProductType generalType = productTypeService.getProductType(ProductType.GENERAL_TYPE);

		Category book = new Category();
		book.setMerchantStore(store);
		book.setCode("book");
		book.setVisible(true);

		CategoryDescription bookEnglishDescription = new CategoryDescription();
		bookEnglishDescription.setName("Book");
		bookEnglishDescription.setCategory(book);
		bookEnglishDescription.setLanguage(en);
		bookEnglishDescription.setSeUrl("book");

		CategoryDescription bookFrenchDescription = new CategoryDescription();
		bookFrenchDescription.setName("Livre");
		bookFrenchDescription.setCategory(book);
		bookFrenchDescription.setLanguage(fr);
		bookFrenchDescription.setSeUrl("livre");

		List<CategoryDescription> descriptions = new ArrayList<CategoryDescription>();
		descriptions.add(bookEnglishDescription);
		descriptions.add(bookFrenchDescription);

		book.setDescriptions(descriptions);

		categoryService.create(book);

		Category music = new Category();
		music.setMerchantStore(store);
		music.setCode("music");
		music.setVisible(true);

		CategoryDescription musicEnglishDescription = new CategoryDescription();
		musicEnglishDescription.setName("Music");
		musicEnglishDescription.setCategory(music);
		musicEnglishDescription.setLanguage(en);
		musicEnglishDescription.setSeUrl("music");

		CategoryDescription musicFrenchDescription = new CategoryDescription();
		musicFrenchDescription.setName("Musique");
		musicFrenchDescription.setCategory(music);
		musicFrenchDescription.setLanguage(fr);
		musicFrenchDescription.setSeUrl("musique");

		List<CategoryDescription> descriptions2 = new ArrayList<CategoryDescription>();
		descriptions2.add(musicEnglishDescription);
		descriptions2.add(musicFrenchDescription);

		music.setDescriptions(descriptions2);

		categoryService.create(music);

		Category novell = new Category();
		novell.setMerchantStore(store);
		novell.setCode("novell");
		novell.setVisible(true);

		CategoryDescription novellEnglishDescription = new CategoryDescription();
		novellEnglishDescription.setName("Novell");
		novellEnglishDescription.setCategory(novell);
		novellEnglishDescription.setLanguage(en);
		novellEnglishDescription.setSeUrl("novell");

		CategoryDescription novellFrenchDescription = new CategoryDescription();
		novellFrenchDescription.setName("Roman");
		novellFrenchDescription.setCategory(novell);
		novellFrenchDescription.setLanguage(fr);
		novellFrenchDescription.setSeUrl("roman");

		List<CategoryDescription> descriptions3 = new ArrayList<CategoryDescription>();
		descriptions3.add(novellEnglishDescription);
		descriptions3.add(novellFrenchDescription);

		novell.setDescriptions(descriptions3);

		novell.setParent(book);

		categoryService.create(novell);
		categoryService.addChild(book, novell);

		Category tech = new Category();
		tech.setMerchantStore(store);
		tech.setCode("tech");

		CategoryDescription techEnglishDescription = new CategoryDescription();
		techEnglishDescription.setName("Technology");
		techEnglishDescription.setCategory(tech);
		techEnglishDescription.setLanguage(en);
		techEnglishDescription.setSeUrl("techno");

		CategoryDescription techFrenchDescription = new CategoryDescription();
		techFrenchDescription.setName("Technologie");
		techFrenchDescription.setCategory(tech);
		techFrenchDescription.setLanguage(fr);
		techFrenchDescription.setSeUrl("techno");

		List<CategoryDescription> descriptions4 = new ArrayList<CategoryDescription>();
		descriptions4.add(techEnglishDescription);
		descriptions4.add(techFrenchDescription);

		tech.setDescriptions(descriptions4);

		tech.setParent(book);

		categoryService.create(tech);
		categoryService.addChild(book, tech);

		Category fiction = new Category();
		fiction.setMerchantStore(store);
		fiction.setCode("fiction");
		fiction.setVisible(true);

		CategoryDescription fictionEnglishDescription = new CategoryDescription();
		fictionEnglishDescription.setName("Fiction");
		fictionEnglishDescription.setCategory(fiction);
		fictionEnglishDescription.setLanguage(en);
		fictionEnglishDescription.setSeUrl("fiction");

		CategoryDescription fictionFrenchDescription = new CategoryDescription();
		fictionFrenchDescription.setName("Sc Fiction");
		fictionFrenchDescription.setCategory(fiction);
		fictionFrenchDescription.setLanguage(fr);
		fictionFrenchDescription.setSeUrl("fiction");

		List<CategoryDescription> fictiondescriptions = new ArrayList<CategoryDescription>();
		fictiondescriptions.add(fictionEnglishDescription);
		fictiondescriptions.add(fictionFrenchDescription);

		fiction.setDescriptions(fictiondescriptions);

		fiction.setParent(novell);

		categoryService.create(fiction);
		categoryService.addChild(book, fiction);

		// Add products
		// ProductType generalType = productTypeService.

		Manufacturer oreilley = new Manufacturer();
		oreilley.setMerchantStore(store);

		ManufacturerDescription oreilleyd = new ManufacturerDescription();
		oreilleyd.setLanguage(en);
		oreilleyd.setName("O\'reilley");
		oreilleyd.setManufacturer(oreilley);
		oreilley.getDescriptions().add(oreilleyd);

		manufacturerService.create(oreilley);

		Manufacturer packed = new Manufacturer();
		packed.setMerchantStore(store);

		ManufacturerDescription packedd = new ManufacturerDescription();
		packedd.setLanguage(en);
		packedd.setManufacturer(packed);
		packedd.setName("Packed publishing");
		packed.getDescriptions().add(packedd);

		manufacturerService.create(packed);

		Manufacturer novells = new Manufacturer();
		novells.setMerchantStore(store);

		ManufacturerDescription novellsd = new ManufacturerDescription();
		novellsd.setLanguage(en);
		novellsd.setManufacturer(novells);
		novellsd.setName("Novells publishing");
		novells.getDescriptions().add(novellsd);

		manufacturerService.create(novells);

		// PRODUCT 1

		Product product = new Product();
		product.setProductHeight(new BigDecimal(4));
		product.setProductLength(new BigDecimal(3));
		product.setProductWidth(new BigDecimal(1));
		product.setSku("TB12345");
		product.setManufacturer(oreilley);
		product.setType(generalType);
		product.setMerchantStore(store);
		product.setProductShipeable(true);

		// Product description
		ProductDescription description = new ProductDescription();
		description.setName("Spring in Action");
		description.setLanguage(en);
		description.setSeUrl("Spring-in-Action");
		description.setProduct(product);

		product.getDescriptions().add(description);

		product.getCategories().add(tech);
		product.getCategories().add(novell);
		product.getCategories().add(fiction);

		productService.create(product);

		// Availability
		ProductAvailability availability = new ProductAvailability();
		availability.setProductDateAvailable(date);
		availability.setProductQuantity(100);
		availability.setRegion("*");
		availability.setProduct(product);// associate with product

		productAvailabilityService.create(availability);

		ProductPrice dprice = new ProductPrice();
		dprice.setDefaultPrice(true);
		dprice.setProductPriceAmount(new BigDecimal(29.99));
		dprice.setProductAvailability(availability);

		ProductPriceDescription dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice);
		dpd.setLanguage(en);

		dprice.getDescriptions().add(dpd);

		productPriceService.create(dprice);

		// PRODUCT 2

		Product product2 = new Product();
		product2.setProductHeight(new BigDecimal(4));
		product2.setProductLength(new BigDecimal(3));
		product2.setProductWidth(new BigDecimal(1));
		product2.setSku("TB2468");
		product2.setManufacturer(packed);
		product2.setType(generalType);
		product2.setMerchantStore(store);
		product2.setProductShipeable(true);

		// Product description
		description = new ProductDescription();
		description.setName("This is Node.js");
		description.setLanguage(en);
		description.setProduct(product2);
		description.setSeUrl("This-is-Node-js");

		product2.getDescriptions().add(description);

		product2.getCategories().add(tech);
		productService.create(product2);

		// Availability
		ProductAvailability availability2 = new ProductAvailability();
		availability2.setProductDateAvailable(date);
		availability2.setProductQuantity(100);
		availability2.setRegion("*");
		availability2.setProduct(product2);// associate with product

		productAvailabilityService.create(availability2);

		ProductPrice dprice2 = new ProductPrice();
		dprice2.setDefaultPrice(true);
		dprice2.setProductPriceAmount(new BigDecimal(39.99));
		dprice2.setProductAvailability(availability2);

		dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice2);
		dpd.setLanguage(en);

		dprice2.getDescriptions().add(dpd);

		productPriceService.create(dprice2);

		// PRODUCT 3

		Product product3 = new Product();
		product3.setProductHeight(new BigDecimal(4));
		product3.setProductLength(new BigDecimal(3));
		product3.setProductWidth(new BigDecimal(1));
		product3.setSku("NB1111");
		product3.setManufacturer(packed);
		product3.setType(generalType);
		product3.setMerchantStore(store);
		product3.setProductShipeable(true);

		// Product description
		description = new ProductDescription();
		description.setName("A nice book for you");
		description.setLanguage(en);
		description.setProduct(product3);
		description.setSeUrl("A-nice-book-for-you");

		product3.getDescriptions().add(description);

		product3.getCategories().add(novell);
		productService.create(product3);

		// Availability
		ProductAvailability availability3 = new ProductAvailability();
		availability3.setProductDateAvailable(date);
		availability3.setProductQuantity(100);
		availability3.setRegion("*");
		availability3.setProduct(product3);// associate with product

		productAvailabilityService.create(availability3);

		ProductPrice dprice3 = new ProductPrice();
		dprice3.setDefaultPrice(true);
		dprice3.setProductPriceAmount(new BigDecimal(19.99));
		dprice3.setProductAvailability(availability3);

		dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice3);
		dpd.setLanguage(en);

		dprice3.getDescriptions().add(dpd);

		productPriceService.create(dprice3);

		// PRODUCT 4

		Product product4 = new Product();
		product4.setProductHeight(new BigDecimal(4));
		product4.setProductLength(new BigDecimal(3));
		product4.setProductWidth(new BigDecimal(1));
		product4.setSku("SF333345");
		product4.setManufacturer(packed);
		product4.setType(generalType);
		product4.setMerchantStore(store);
		product4.setProductShipeable(true);

		// Product description
		description = new ProductDescription();
		description.setName("Battle of the worlds");
		description.setLanguage(en);
		description.setProduct(product4);
		description.setSeUrl("Battle-of-the-worlds");

		product4.getDescriptions().add(description);

		product4.getCategories().add(fiction);
		productService.create(product4);

		// Availability
		ProductAvailability availability4 = new ProductAvailability();
		availability4.setProductDateAvailable(date);
		availability4.setProductQuantity(100);
		availability4.setRegion("*");
		availability4.setProduct(product4);// associate with product

		productAvailabilityService.create(availability4);

		ProductPrice dprice4 = new ProductPrice();
		dprice4.setDefaultPrice(true);
		dprice4.setProductPriceAmount(new BigDecimal(18.99));
		dprice4.setProductAvailability(availability4);

		dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice4);
		dpd.setLanguage(en);

		dprice4.getDescriptions().add(dpd);

		productPriceService.create(dprice4);

		// PRODUCT 5

		Product product5 = new Product();
		product5.setProductHeight(new BigDecimal(4));
		product5.setProductLength(new BigDecimal(3));
		product5.setProductWidth(new BigDecimal(1));
		product5.setSku("SF333346");
		product5.setManufacturer(packed);
		product5.setType(generalType);
		product5.setMerchantStore(store);
		product5.setProductShipeable(true);

		// Product description
		description = new ProductDescription();
		description.setName("Battle of the worlds 2");
		description.setLanguage(en);
		description.setProduct(product5);
		description.setSeUrl("Battle-of-the-worlds-2");

		product5.getDescriptions().add(description);

		product5.getCategories().add(fiction);
		productService.create(product5);

		// Availability
		ProductAvailability availability5 = new ProductAvailability();
		availability5.setProductDateAvailable(date);
		availability5.setProductQuantity(100);
		availability5.setRegion("*");
		availability5.setProduct(product5);// associate with product

		productAvailabilityService.create(availability5);

		ProductPrice dprice5 = new ProductPrice();
		dprice5.setDefaultPrice(true);
		dprice5.setProductPriceAmount(new BigDecimal(18.99));
		dprice5.setProductAvailability(availability5);

		dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice5);
		dpd.setLanguage(en);

		dprice5.getDescriptions().add(dpd);

		productPriceService.create(dprice5);

		// PRODUCT 6

		Product product6 = new Product();
		product6.setProductHeight(new BigDecimal(4));
		product6.setProductLength(new BigDecimal(3));
		product6.setProductWidth(new BigDecimal(1));
		product6.setSku("LL333444");
		product6.setManufacturer(packed);
		product6.setType(generalType);
		product6.setMerchantStore(store);
		product6.setProductShipeable(true);

		// Product description
		description = new ProductDescription();
		description.setName("Life book");
		description.setLanguage(en);
		description.setProduct(product6);
		description.setSeUrl("Life-book");

		product6.getDescriptions().add(description);

		product6.getCategories().add(novell);
		productService.create(product6);

		// Availability
		ProductAvailability availability6 = new ProductAvailability();
		availability6.setProductDateAvailable(date);
		availability6.setProductQuantity(100);
		availability6.setRegion("*");
		availability6.setProduct(product6);// associate with product

		productAvailabilityService.create(availability6);

		ProductPrice dprice6 = new ProductPrice();
		dprice6.setDefaultPrice(true);
		dprice6.setProductPriceAmount(new BigDecimal(18.99));
		dprice6.setProductAvailability(availability6);

		dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice6);
		dpd.setLanguage(en);

		dprice6.getDescriptions().add(dpd);

		productPriceService.create(dprice6);

		// Create a customer (user name[nick] : shopizer password : password)

		Customer customer = new Customer();
		customer.setMerchantStore(store);
		customer.setEmailAddress("test@shopizer.com");
		customer.setGender(CustomerGender.M);
		customer.setAnonymous(false);
		customer.setCompany("CSTI Consulting");
		customer.setDateOfBirth(new Date());

		customer.setDefaultLanguage(en);
		customer.setNick("shopizer");

		// String password = passwordEncoder.encodePassword("password", null);
		String password = "password";
		customer.setPassword(password);

		List<Group> groups = groupService.listGroup(GroupType.CUSTOMER);

		for (Group group : groups) {
			if (group.getGroupName().equals("CUSTOMER")) {
				customer.getGroups().add(group);
			}
		}

		Delivery delivery = new Delivery();
		delivery.setAddress("358 Du Languadoc");
		delivery.setCity("Boucherville");
		delivery.setCountry(canada);
		// delivery.setCountryCode(canada.getIsoCode());
		delivery.setFirstName("Leonardo");
		delivery.setLastName("DiCaprio");
		delivery.setPostalCode("J4B-8J9");
		delivery.setZone(zone);

		Billing billing = new Billing();
		billing.setAddress("358 Du Languadoc");
		billing.setCity("Boucherville");
		billing.setCompany("CSTI Consulting");
		billing.setCountry(canada);
		// billing.setCountryCode(canada.getIsoCode());
		billing.setFirstName("Leonardo");
		billing.setLastName("DiCaprio");
		billing.setPostalCode("J4B-8J9");
		billing.setZone(zone);

		customer.setBilling(billing);
		customer.setDelivery(delivery);
		customerService.create(customer);

		Currency currency = currencyService.getByCode("CAD");

		OrderStatusHistory orderStatusHistory = new OrderStatusHistory();

		// create an order

		Order order = new Order();
		order.setDatePurchased(new Date());
		order.setCurrency(currency);
		order.setLastModified(new Date());
		order.setBilling(billing);

		order.setCurrencyValue(new BigDecimal(0.98));// compared to based
														// currency (not
														// necessary)
		order.setCustomerId(customer.getId());
		order.setBilling(billing);
		order.setDelivery(delivery);
		order.setCustomerEmailAddress("leo@shopizer.com");
		order.setDelivery(delivery);
		order.setIpAddress("ipAddress");
		order.setMerchant(store);
		order.setOrderDateFinished(new Date());// committed date

		orderStatusHistory.setComments("We received your order");
		orderStatusHistory.setCustomerNotified(1);
		orderStatusHistory.setStatus(OrderStatus.ORDERED);
		orderStatusHistory.setDateAdded(new Date());
		orderStatusHistory.setOrder(order);
		order.getOrderHistory().add(orderStatusHistory);

		order.setPaymentType(PaymentType.PAYPAL);
		order.setPaymentModuleCode("paypal");
		order.setStatus(OrderStatus.DELIVERED);
		order.setTotal(new BigDecimal(23.99));

		// OrderProductDownload - Digital download
		OrderProductDownload orderProductDownload = new OrderProductDownload();
		orderProductDownload.setDownloadCount(1);
		orderProductDownload.setMaxdays(31);
		orderProductDownload.setOrderProductFilename("Your digital file name");

		// OrderProductPrice
		OrderProductPrice oproductprice = new OrderProductPrice();
		oproductprice.setDefaultPrice(true);
		oproductprice.setProductPrice(new BigDecimal(19.99));
		oproductprice.setProductPriceCode("baseprice");
		oproductprice.setProductPriceName("Base Price");
		// oproductprice.setProductPriceSpecialAmount(new BigDecimal(13.99) );

		// OrderProduct
		OrderProduct oproduct = new OrderProduct();
		oproduct.getDownloads().add(orderProductDownload);
		oproduct.setOneTimeCharge(new BigDecimal(19.99));
		oproduct.setOrder(order);
		oproduct.setProductName("Product name");
		oproduct.setProductQuantity(1);
		oproduct.setSku("TB12345");
		oproduct.getPrices().add(oproductprice);

		oproductprice.setOrderProduct(oproduct);
		orderProductDownload.setOrderProduct(oproduct);
		order.getOrderProducts().add(oproduct);

		// OrderTotal
		OrderTotal subtotal = new OrderTotal();
		subtotal.setModule("summary");
		subtotal.setSortOrder(0);
		subtotal.setText("Summary");
		subtotal.setTitle("Summary");
		subtotal.setOrderTotalCode("subtotal");
		subtotal.setValue(new BigDecimal(19.99));
		subtotal.setOrder(order);

		order.getOrderTotal().add(subtotal);

		OrderTotal tax = new OrderTotal();
		tax.setModule("tax");
		tax.setSortOrder(1);
		tax.setText("Tax");
		tax.setTitle("Tax");
		tax.setOrderTotalCode("tax");
		tax.setValue(new BigDecimal(4));
		tax.setOrder(order);

		order.getOrderTotal().add(tax);

		OrderTotal total = new OrderTotal();
		total.setModule("total");
		total.setSortOrder(2);
		total.setText("Total");
		total.setTitle("Total");
		total.setOrderTotalCode("total");
		total.setValue(new BigDecimal(23.99));
		total.setOrder(order);

		order.getOrderTotal().add(total);

		orderService.saveOrUpdate(order);

		Assert.assertTrue(orderService.count() == 1);
	}

	public void createOrder() throws ServiceException {

		MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);

		// create a product
		ProductType generalType = productTypeService.getProductType(ProductType.GENERAL_TYPE);

		Language en = languageService.getByCode("en");

		Product product = new Product();
		product.setProductHeight(new BigDecimal(4));
		product.setProductLength(new BigDecimal(3));
		product.setProductWidth(new BigDecimal(5));
		product.setProductWeight(new BigDecimal(8));
		product.setSku("TESTSKU");
		product.setType(generalType);
		product.setMerchantStore(store);

		// Product description
		ProductDescription description = new ProductDescription();
		description.setName("Product 1");
		description.setLanguage(en);
		description.setProduct(product);

		product.getDescriptions().add(description);

		// Availability
		ProductAvailability availability = new ProductAvailability();
		availability.setProductDateAvailable(new Date());
		availability.setProductQuantity(100);
		availability.setRegion("*");
		availability.setProduct(product);// associate with product

		product.getAvailabilities().add(availability);

		ProductPrice dprice = new ProductPrice();
		dprice.setDefaultPrice(true);
		dprice.setProductPriceAmount(new BigDecimal(29.99));
		dprice.setProductAvailability(availability);

		availability.getPrices().add(dprice);

		ProductPriceDescription dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice);
		dpd.setLanguage(en);

		dprice.getDescriptions().add(dpd);

		productService.saveOrUpdate(product);

		// create a Customer
		Country country = countryService.getByCode("CA");
		Zone zone = zoneService.getByCode("QC");

		Customer customer = new Customer();
		customer.setMerchantStore(store);
		customer.setEmailAddress("test@test.com");
		customer.setGender(CustomerGender.M);
		customer.setAnonymous(true);
		customer.setCompany("ifactory");
		customer.setDateOfBirth(new Date());
		customer.setNick("My nick");
		customer.setDefaultLanguage(en);

		Delivery delivery = new Delivery();
		delivery.setAddress("358 Du Languadoc");
		delivery.setCity("Boucherville");
		delivery.setCountry(country);
		// delivery.setCountryCode(CA_COUNTRY_CODE);
		delivery.setPostalCode("J4B-8J9");
		delivery.setFirstName("Carl");
		delivery.setLastName("Samson");
		delivery.setZone(zone);

		Billing billing = new Billing();
		billing.setAddress("358 Du Languadoc");
		billing.setCity("Boucherville");
		billing.setCompany("CSTI Consulting");
		billing.setCountry(country);
		// billing.setCountryCode(CA_COUNTRY_CODE);
		billing.setFirstName("Carl");
		billing.setLastName("Samson");
		billing.setPostalCode("J4B-8J9");
		billing.setZone(zone);

		customer.setBilling(billing);
		customer.setDelivery(delivery);
		customerService.create(customer);

		Currency currency = currencyService.getByCode(CAD_CURRENCY_CODE);

		OrderStatusHistory orderStatusHistory = new OrderStatusHistory();

		Order order = new Order();
		order.setDatePurchased(new Date());
		order.setCurrency(currency);
		order.setLastModified(new Date());
		order.setBilling(billing);

		order.setCurrencyValue(new BigDecimal(0.98));// compared to based
														// currency (not
														// necessary)
		order.setCustomerId(customer.getId());
		order.setDelivery(delivery);
		order.setIpAddress("ipAddress");
		order.setMerchant(store);
		order.setCustomerEmailAddress(customer.getEmailAddress());

		order.setOrderDateFinished(new Date());// committed date

		orderStatusHistory.setComments("We received your order");
		orderStatusHistory.setCustomerNotified(1);
		orderStatusHistory.setStatus(OrderStatus.ORDERED);
		orderStatusHistory.setDateAdded(new Date());
		orderStatusHistory.setOrder(order);
		order.getOrderHistory().add(orderStatusHistory);

		order.setPaymentType(PaymentType.PAYPAL);
		order.setPaymentModuleCode("paypal");
		order.setStatus(OrderStatus.DELIVERED);
		order.setTotal(new BigDecimal(23.99));

		// OrderProductDownload - Digital download
		OrderProductDownload orderProductDownload = new OrderProductDownload();
		orderProductDownload.setDownloadCount(1);
		orderProductDownload.setMaxdays(31);
		orderProductDownload.setOrderProductFilename("Your digital file name");

		// OrderProductPrice
		OrderProductPrice oproductprice = new OrderProductPrice();
		oproductprice.setDefaultPrice(true);
		oproductprice.setProductPrice(new BigDecimal(19.99));
		oproductprice.setProductPriceCode("baseprice");
		oproductprice.setProductPriceName("Base Price");

		// OrderProduct
		OrderProduct oproduct = new OrderProduct();
		oproduct.getDownloads().add(orderProductDownload);
		oproduct.setOneTimeCharge(new BigDecimal(19.99));
		oproduct.setOrder(order);
		oproduct.setProductName("Product name");
		oproduct.setProductQuantity(1);
		oproduct.setSku("TB12345");
		oproduct.getPrices().add(oproductprice);

		oproductprice.setOrderProduct(oproduct);
		orderProductDownload.setOrderProduct(oproduct);
		order.getOrderProducts().add(oproduct);

		// requires
		// OrderProduct
		// OrderProductPrice
		// OrderTotal

		// OrderTotal
		OrderTotal subtotal = new OrderTotal();
		subtotal.setModule("summary");
		subtotal.setSortOrder(0);
		subtotal.setText("Summary");
		subtotal.setTitle("Summary");
		subtotal.setOrderTotalCode("summary");
		subtotal.setValue(new BigDecimal(19.99));
		subtotal.setOrder(order);

		order.getOrderTotal().add(subtotal);

		OrderTotal tax = new OrderTotal();
		tax.setModule("tax");
		tax.setSortOrder(1);
		tax.setText("Tax");
		tax.setTitle("Tax");
		tax.setOrderTotalCode("tax");
		tax.setValue(new BigDecimal(4));
		tax.setOrder(order);

		order.getOrderTotal().add(tax);

		OrderTotal total = new OrderTotal();
		total.setModule("total");
		total.setSortOrder(2);
		total.setText("Total");
		total.setTitle("Total");
		total.setOrderTotalCode("total");
		total.setValue(new BigDecimal(23.99));
		total.setOrder(order);

		order.getOrderTotal().add(total);

		orderService.saveOrUpdate(order);
		Assert.assertTrue(orderService.count() == 1);
	}

	@Ignore
	@Test
	public void getMerchantOrders() throws ServiceException {

		List<Order> merchantOrders = new ArrayList<Order>();

		Language language = languageService.getByCode(ENGLISH_LANGUAGE_CODE);
		Currency currency = currencyService.getByCode(EURO_CURRENCY_CODE);
		Country country = countryService.getByCode(FR_COUNTRY_CODE);
		Zone zone = zoneService.getByCode("VT");

		MerchantStore merchant = new MerchantStore();
		merchant.setCurrency(currency);
		merchant.setStorename("Test Store");
		merchant.setCountry(country);
		merchant.setDefaultLanguage(language);
		merchant.setStorecity("Test Store City");
		merchant.setCode(merchantService.count() + "");
		Language en = languageService.getByCode("en");
		Language fr = languageService.getByCode("fr");
		List<Language> supportedLanguages = new ArrayList<Language>();
		supportedLanguages.add(en);
		supportedLanguages.add(fr);
		merchant.setLanguages(supportedLanguages);
		merchant.setStoreEmailAddress("store_email@email.com");
		merchant.setStorephone("Merchant Store Phone");
		merchant.setStorepostalcode("12061");
		merchantService.create(merchant);

		Customer customer = new Customer();
		customer.setMerchantStore(merchant);
		customer.setEmailAddress("email@email.com");
		customer.setPassword("-1999");
		customer.setNick("My New nick");
		customer.setCompany(" Apple");
		customer.setGender(CustomerGender.M);
		customer.setDateOfBirth(new Date());

		Billing billing = new Billing();
		billing.setAddress("Billing address");
		billing.setCity("Billing city");
		billing.setCompany("Billing company");
		billing.setCountry(country);
		// billing.setCountryCode(CA_COUNTRY_CODE);
		billing.setFirstName("Carl");
		billing.setLastName("Samson");
		billing.setPostalCode("Billing postal code");
		billing.setState("Billing state");
		billing.setZone(zone);

		Delivery delivery = new Delivery();
		delivery.setAddress("Shipping address");
		delivery.setCountry(country);
		delivery.setZone(zone);

		customer.setBilling(billing);
		customer.setDelivery(delivery);

		customerService.create(customer);

		OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
		Order order = new Order();
		order.setDatePurchased(new Date());
		order.setCurrency(currency);
		order.setMerchant(merchant);
		order.setLastModified(new Date());

		CreditCard creditCard = new CreditCard();
		creditCard.setCardType(CreditCardType.VISA);

		creditCard.setCcCvv("123");
		creditCard.setCcExpires("12/30/2020");
		creditCard.setCcNumber("123456789");
		creditCard.setCcOwner("ccOwner");

		order.setCreditCard(creditCard);

		order.setCurrencyValue(new BigDecimal(19.99));
		order.setCustomerId(new Long(1));
		order.setDelivery(delivery);
		order.setIpAddress("ipAddress");
		order.setMerchant(merchant);
		order.setOrderDateFinished(new Date());
		orderStatusHistory.setDateAdded(new Date());
		orderStatusHistory.setOrder(order);
		order.setPaymentType(PaymentType.CREDITCARD);
		order.setPaymentModuleCode("payment Module Code");
		order.setShippingModuleCode("UPS");
		order.setStatus(OrderStatus.ORDERED);
		order.setTotal(new BigDecimal(23.99));

		// OrderProduct
		OrderProduct oproduct = new OrderProduct();
		oproduct.setDownloads(null);
		oproduct.setOneTimeCharge(new BigDecimal(16.99));
		oproduct.setOrder(order);
		oproduct.setProductName("Order Product Name");
		oproduct.setProductQuantity(5);
		oproduct.setSku("Order Product sku");

		orderService.create(order);

		merchantOrders = orderService.listByStore(merchant);

		Assert.assertTrue("Merchant Orders are null.", merchantOrders != null);
		Assert.assertTrue("Merchant Orders count is not one.", (merchantOrders != null && merchantOrders.size() == 1));
	}

	public void testSearchOrders() throws ServiceException {

		MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
		Country country = countryService.getByCode("CA");
		Zone zone = zoneService.getByCode("VT");

		// create 3 customers
		Customer firstCustomer = new Customer();
		firstCustomer.setMerchantStore(store);
		firstCustomer.setEmailAddress("test@test.com");
		firstCustomer.setGender(CustomerGender.M);
		firstCustomer.setAnonymous(true);
		firstCustomer.setCompany("ifactory");
		firstCustomer.setDateOfBirth(new Date());
		firstCustomer.setNick("My nick");
		firstCustomer.setPassword("123456");

		Delivery delivery = new Delivery();
		delivery.setAddress("Shipping address");
		delivery.setCountry(country);
		delivery.setZone(zone);

		Billing billing = new Billing();
		billing.setAddress("Billing address");
		billing.setCountry(country);
		billing.setZone(zone);

		firstCustomer.setBilling(billing);
		firstCustomer.setDelivery(delivery);

		customerService.create(firstCustomer);

		Customer secondCustomer = new Customer();
		secondCustomer.setMerchantStore(store);
		secondCustomer.setEmailAddress("test@test.com");
		secondCustomer.setGender(CustomerGender.M);
		secondCustomer.setDateOfBirth(new Date());
		secondCustomer.setPassword("123456");

		secondCustomer.setBilling(billing);
		secondCustomer.setDelivery(delivery);

		customerService.create(secondCustomer);

		Customer thirdCustomer = new Customer();
		thirdCustomer.setMerchantStore(store);
		thirdCustomer.setEmailAddress("test@test.com");
		thirdCustomer.setGender(CustomerGender.M);
		thirdCustomer.setDateOfBirth(new Date());
		thirdCustomer.setPassword("123456");

		thirdCustomer.setBilling(billing);
		thirdCustomer.setDelivery(delivery);

		customerService.create(thirdCustomer);

		// create a few orders
		Order order = new Order();
		order.setDatePurchased(new Date());
		order.setCurrency(store.getCurrency());
		order.setMerchant(store);
		order.setLastModified(new Date());

		CreditCard creditCard = new CreditCard();
		creditCard.setCardType(CreditCardType.VISA);

		creditCard.setCcCvv("123");
		creditCard.setCcExpires("12/30/2020");
		creditCard.setCcNumber("123456789");
		creditCard.setCcOwner("ccOwner");

		order.setCreditCard(creditCard);
		order.setCurrencyValue(new BigDecimal(19.99));
		order.setCustomerId(new Long(1));
		order.setDelivery(delivery);
		order.setIpAddress("ipAddress");

		order.setPaymentType(PaymentType.CREDITCARD);
		order.setPaymentModuleCode("beanstream");
		order.setShippingModuleCode("ups");
		order.setStatus(OrderStatus.ORDERED);
		order.setTotal(new BigDecimal(23.99));

		OrderProductPrice oproductprice = new OrderProductPrice();
		oproductprice.setDefaultPrice(true);
		oproductprice.setProductPrice(new BigDecimal(19.99));
		oproductprice.setProductPriceCode("baseprice");
		oproductprice.setProductPriceName("Base Price");

		// OrderProduct
		OrderProduct oproduct = new OrderProduct();
		oproduct.setOneTimeCharge(new BigDecimal(19.99));
		oproduct.setOrder(order);
		oproduct.setProductName("Product name");
		oproduct.setProductQuantity(1);
		oproduct.setSku("TB12345");
		oproduct.getPrices().add(oproductprice);

		oproductprice.setOrderProduct(oproduct);
		order.getOrderProducts().add(oproduct);

		OrderTotal orderTotal = new OrderTotal();
		orderTotal.setModule("total");
		orderTotal.setOrder(order);
		orderTotal.setText("Total");
		orderTotal.setTitle("total");
		orderTotal.setValue(new BigDecimal(23.99));

		order.getOrderTotal().add(orderTotal);

		orderService.create(order);

		Order secondOrder = new Order();
		secondOrder.setDatePurchased(new Date());
		secondOrder.setCurrency(store.getCurrency());
		secondOrder.setMerchant(store);
		secondOrder.setLastModified(new Date());

		creditCard = new CreditCard();
		creditCard.setCardType(CreditCardType.VISA);

		creditCard.setCcCvv("123");
		creditCard.setCcExpires("12/30/2020");
		creditCard.setCcNumber("123456789");
		creditCard.setCcOwner("ccOwner");

		order.setCreditCard(creditCard);
		secondOrder.setCurrencyValue(new BigDecimal(19.99));
		secondOrder.setCustomerId(secondCustomer.getId());
		secondOrder.setDelivery(delivery);
		secondOrder.setIpAddress("ipAddress");
		order.setPaymentType(PaymentType.CREDITCARD);
		order.setPaymentModuleCode("beanstream");
		order.setShippingModuleCode("ups");
		secondOrder.setShippingModuleCode("ups");
		secondOrder.setStatus(OrderStatus.ORDERED);
		secondOrder.setTotal(new BigDecimal(23.99));

		oproductprice = new OrderProductPrice();
		oproductprice.setDefaultPrice(true);
		oproductprice.setProductPrice(new BigDecimal(19.99));
		oproductprice.setProductPriceCode("baseprice");
		oproductprice.setProductPriceName("Base Price");

		// OrderProduct
		oproduct = new OrderProduct();
		oproduct.setOneTimeCharge(new BigDecimal(19.99));
		oproduct.setOrder(secondOrder);
		oproduct.setProductName("Product name");
		oproduct.setProductQuantity(1);
		oproduct.setSku("TB12345");
		oproduct.getPrices().add(oproductprice);

		oproductprice.setOrderProduct(oproduct);
		secondOrder.getOrderProducts().add(oproduct);

		orderTotal = new OrderTotal();
		orderTotal.setModule("total");
		orderTotal.setOrder(secondOrder);
		orderTotal.setText("Total");
		orderTotal.setTitle("total");
		orderTotal.setValue(new BigDecimal(23.99));

		order.getOrderTotal().add(orderTotal);

		orderService.create(secondOrder);

		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.setCustomerName("Cruise");
		orderCriteria.setStartIndex(0);
		orderCriteria.setMaxCount(5);

		OrderList orderList = orderService.listByStore(store, orderCriteria);

		Assert.assertNotNull(orderList);

		System.out.println("Total count " + orderList.getTotalCount());

	}

}