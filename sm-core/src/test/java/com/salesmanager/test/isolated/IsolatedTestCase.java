package com.salesmanager.test.isolated;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.salesmanager.core.business.catalog.category.model.Category;
import com.salesmanager.core.business.catalog.category.model.CategoryDescription;
import com.salesmanager.core.business.catalog.category.service.CategoryService;
import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.ProductCriteria;
import com.salesmanager.core.business.catalog.product.model.ProductList;
import com.salesmanager.core.business.catalog.product.model.availability.ProductAvailability;
import com.salesmanager.core.business.catalog.product.model.description.ProductDescription;
import com.salesmanager.core.business.catalog.product.model.image.ProductImage;
import com.salesmanager.core.business.catalog.product.model.image.ProductImageDescription;
import com.salesmanager.core.business.catalog.product.model.manufacturer.Manufacturer;
import com.salesmanager.core.business.catalog.product.model.manufacturer.ManufacturerDescription;
import com.salesmanager.core.business.catalog.product.model.price.ProductPrice;
import com.salesmanager.core.business.catalog.product.model.price.ProductPriceDescription;
import com.salesmanager.core.business.catalog.product.model.type.ProductType;
import com.salesmanager.core.business.catalog.product.service.ProductService;
import com.salesmanager.core.business.catalog.product.service.attribute.ProductAttributeService;
import com.salesmanager.core.business.catalog.product.service.attribute.ProductOptionService;
import com.salesmanager.core.business.catalog.product.service.attribute.ProductOptionValueService;
import com.salesmanager.core.business.catalog.product.service.availability.ProductAvailabilityService;
import com.salesmanager.core.business.catalog.product.service.image.ProductImageService;
import com.salesmanager.core.business.catalog.product.service.manufacturer.ManufacturerService;
import com.salesmanager.core.business.catalog.product.service.price.ProductPriceService;
import com.salesmanager.core.business.catalog.product.service.type.ProductTypeService;
import com.salesmanager.core.business.content.service.ContentService;
import com.salesmanager.core.business.customer.service.CustomerService;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.util.EntityManagerUtils;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.merchant.service.MerchantStoreService;
import com.salesmanager.core.business.order.service.OrderService;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.country.service.CountryService;
import com.salesmanager.core.business.reference.currency.model.Currency;
import com.salesmanager.core.business.reference.currency.service.CurrencyService;
import com.salesmanager.core.business.reference.init.service.InitializationDatabase;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.reference.language.service.LanguageService;
import com.salesmanager.core.business.reference.zone.service.ZoneService;
import com.salesmanager.core.business.system.model.IntegrationConfiguration;
import com.salesmanager.core.business.system.model.IntegrationModule;
import com.salesmanager.core.business.system.service.ModuleConfigurationService;
import com.salesmanager.core.business.user.model.Group;
import com.salesmanager.core.business.user.model.GroupType;
import com.salesmanager.core.business.user.model.Permission;
import com.salesmanager.core.business.user.service.GroupService;
import com.salesmanager.core.business.user.service.PermissionService;
import com.salesmanager.core.business.user.service.UserService;
import com.salesmanager.core.utils.reference.ConfigurationModulesLoader;
import com.salesmanager.test.core.SalesManagerCoreTestExecutionListener;

@ContextConfiguration(locations = { "classpath:spring/test-spring-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, SalesManagerCoreTestExecutionListener.class })
public class IsolatedTestCase {

    private static final Logger log = Logger.getLogger(IsolatedTestCase.class);

    private static final Date date = new Date(System.currentTimeMillis());

    @Autowired
    private EntityManagerUtils entityManagerUtils;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ProductPriceService productPriceService;

    @Autowired
    protected ProductAttributeService productAttributeService;

    @Autowired
    protected ProductOptionService productOptionService;

    @Autowired
    protected ProductOptionValueService productOptionValueService;

    @Autowired
    protected ProductAvailabilityService productAvailabilityService;

    @Autowired
    protected ProductImageService productImageService;

    @Autowired
    protected ContentService contentService;

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected MerchantStoreService merchantService;

    @Autowired
    protected ProductTypeService productTypeService;

    @Autowired
    protected LanguageService languageService;

    @Autowired
    protected CountryService countryService;

    @Autowired
    protected ZoneService zoneService;

    @Autowired
    protected CustomerService customerService;

    @Autowired
    protected ManufacturerService manufacturerService;

    @Autowired
    protected CurrencyService currencyService;

    @Autowired
    protected OrderService orderService;

    @Autowired
    protected GroupService groupService;

    @Autowired
    protected PermissionService permissionService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected InitializationDatabase initializationDatabase;

    @Autowired
    protected ModuleConfigurationService moduleConfigurationService;

    // @Autowired
    protected TestSupportFactory testSupportFactory;

    @Test
    public void creatAdminUser() throws ServiceException {
	// MerchantStore store =
	// merchantStoreService.getMerchantStore(MerchantStore.DEFAULT_STORE);
	MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
	org.springframework.security.authentication.encoding.ShaPasswordEncoder passwordEncoder = new org.springframework.security.authentication.encoding.ShaPasswordEncoder();
	String password = passwordEncoder.encodePassword("password", null);

	List<Group> groups = groupService.listGroup(GroupType.ADMIN);

	// creation of the super admin admin:password)
	com.salesmanager.core.business.user.model.User user = new com.salesmanager.core.business.user.model.User("admin", password, "admin@shopizer.com");
	user.setFirstName("Administrator");
	user.setLastName("User");

	for (Group group : groups) {
	    if (group.getGroupName().equals("SUPERADMIN") || group.getGroupName().equals("ADMIN")) {
		user.getGroups().add(group);
	    }
	}

	user.setMerchantStore(store);
	userService.create(user);
    }

}
