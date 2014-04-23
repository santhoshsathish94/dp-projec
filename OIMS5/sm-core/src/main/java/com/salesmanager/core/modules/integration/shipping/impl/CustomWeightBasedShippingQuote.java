package com.salesmanager.core.modules.integration.shipping.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.salesmanager.core.business.common.model.Delivery;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.shipping.model.PackageDetails;
import com.salesmanager.core.business.shipping.model.ShippingBasisType;
import com.salesmanager.core.business.shipping.model.ShippingConfiguration;
import com.salesmanager.core.business.shipping.model.ShippingOption;
import com.salesmanager.core.business.system.model.CustomIntegrationConfiguration;
import com.salesmanager.core.business.system.model.IntegrationConfiguration;
import com.salesmanager.core.business.system.model.IntegrationModule;
import com.salesmanager.core.business.system.model.MerchantConfiguration;
import com.salesmanager.core.business.system.service.MerchantConfigurationService;
import com.salesmanager.core.modules.integration.IntegrationException;
import com.salesmanager.core.modules.integration.shipping.model.CustomShippingQuoteWeightItem;
import com.salesmanager.core.modules.integration.shipping.model.CustomShippingQuotesConfiguration;
import com.salesmanager.core.modules.integration.shipping.model.CustomShippingQuotesRegion;
import com.salesmanager.core.modules.integration.shipping.model.ShippingQuoteModule;
import com.salesmanager.core.utils.ProductPriceUtils;

public class CustomWeightBasedShippingQuote implements ShippingQuoteModule {

	public final static String MODULE_CODE = "weightBased";
	private final static String CUSTOM_WEIGHT = "CUSTOM_WEIGHT";

	@Autowired
	private MerchantConfigurationService merchantConfigurationService;

	@Autowired
	private ProductPriceUtils productPriceUtils;

	@Override
	public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException {

		// not used, it has its own controller with complex validators

	}

	@Override
	public CustomIntegrationConfiguration getCustomModuleConfiguration(MerchantStore store) throws IntegrationException {

		try {

			MerchantConfiguration configuration = merchantConfigurationService.getMerchantConfiguration(MODULE_CODE, store);

			if (configuration != null) {
				String value = configuration.getValue();
				ObjectMapper mapper = new ObjectMapper();
				try {
					CustomShippingQuotesConfiguration config = mapper.readValue(value, CustomShippingQuotesConfiguration.class);
					return config;
				} catch (Exception e) {
					throw new ServiceException("Cannot parse json string " + value);
				}

			} else {
				CustomShippingQuotesConfiguration custom = new CustomShippingQuotesConfiguration();
				custom.setModuleCode(MODULE_CODE);
				return custom;
			}

		} catch (Exception e) {
			throw new IntegrationException(e);
		}

	}

	@Override
	public List<ShippingOption> getShippingQuotes(List<PackageDetails> packages, BigDecimal orderTotal, Delivery delivery, MerchantStore store, IntegrationConfiguration configuration,
			IntegrationModule module, ShippingConfiguration shippingConfiguration, Locale locale) throws IntegrationException {
		System.out.println("====================In CustomWeightBasedShippingQuote================");
		// get configuration
		CustomShippingQuotesConfiguration customConfiguration = (CustomShippingQuotesConfiguration) this.getCustomModuleConfiguration(store);

		List<CustomShippingQuotesRegion> regions = customConfiguration.getRegions();
		System.out.println("====================In regions length================" + regions.size());
		System.out.println("====================In regions================" + regions.toString());

		ShippingBasisType shippingType = shippingConfiguration.getShippingBasisType();
		ShippingOption shippingOption = null;
		System.out.println("====================In shippingType================" + shippingType.name());
		try {

			for (CustomShippingQuotesRegion region : customConfiguration.getRegions()) {

				for (String countryCode : region.getCountries()) {
					System.out.println("countryCode>>>"+countryCode+"====="+delivery.getCountry().getIsoCode());
					if (countryCode.equals(delivery.getCountry().getIsoCode())) {

						// determine shipping weight
						double weight = 0;
						for (PackageDetails packageDetail : packages) {
							weight = weight + packageDetail.getShippingWeight();
						}
						System.out.println("weight>>>"+weight);
						// see the price associated with the width
						List<CustomShippingQuoteWeightItem> quoteItems = region.getQuoteItems();
						System.out.println("quoteItems size>>>"+quoteItems.size());
						System.out.println("quoteItems>>>"+quoteItems.toString());
						for (CustomShippingQuoteWeightItem quoteItem : quoteItems) {
							System.out.println("weight>>>"+weight+"=====quoteItem.getMaximumWeight()>>"+quoteItem.getMaximumWeight());
							if (weight <= quoteItem.getMaximumWeight()) {
								shippingOption = new ShippingOption();
								shippingOption.setOptionCode(new StringBuilder().append(CUSTOM_WEIGHT).toString());
								shippingOption.setOptionId(new StringBuilder().append(CUSTOM_WEIGHT).append("_").append(region.getCustomRegionName()).toString());
								shippingOption.setOptionPrice(quoteItem.getPrice());
								shippingOption.setOptionPriceText(productPriceUtils.getStoreFormatedAmountWithCurrency(store, quoteItem.getPrice()));
								break;
							}
						}

					}

				}

			}

			if (shippingOption != null) {
				List<ShippingOption> options = new ArrayList<ShippingOption>();
				options.add(shippingOption);
				return options;
			}

			return null;

		} catch (Exception e) {
			throw new IntegrationException(e);
		}

	}

}
