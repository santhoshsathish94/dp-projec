package com.salesmanager.web.populator.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;

import com.salesmanager.core.business.catalog.product.service.ProductService;
import com.salesmanager.core.business.catalog.product.service.attribute.ProductAttributeService;
import com.salesmanager.core.business.catalog.product.service.file.DigitalProductService;
import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.customer.service.CustomerService;
import com.salesmanager.core.business.generic.exception.ConversionException;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.order.model.Order;
import com.salesmanager.core.business.order.model.orderproduct.OrderProduct;
import com.salesmanager.core.business.order.model.orderstatus.OrderStatus;
import com.salesmanager.core.business.order.model.orderstatus.OrderStatusHistory;
import com.salesmanager.core.business.order.model.payment.CreditCard;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.country.service.CountryService;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.reference.zone.model.Zone;
import com.salesmanager.core.business.reference.zone.service.ZoneService;
import com.salesmanager.core.utils.AbstractDataPopulator;
import com.salesmanager.core.utils.CreditCardUtils;
import com.salesmanager.web.entity.customer.PersistableCustomer;
import com.salesmanager.web.entity.order.OrderTotal;
import com.salesmanager.web.entity.order.PersistableOrder;
import com.salesmanager.web.entity.order.PersistableOrderProduct;

public class PersistableOrderPopulator extends
		AbstractDataPopulator<PersistableOrder, Order> {
	
	private CustomerService customerService;
	private CountryService countryService;
	private ZoneService zoneService;
	private ProductService productService;
	private DigitalProductService digitalProductService;
	private ProductAttributeService productAttributeService;

	@Override
	public Order populate(PersistableOrder source, Order target,
			MerchantStore store, Language language) throws ConversionException {
		
		
		Validate.notNull(productService,"productService must be set");
		Validate.notNull(digitalProductService,"digitalProductService must be set");
		Validate.notNull(productAttributeService,"productAttributeService must be set");
		Validate.notNull(customerService,"customerService must be set");
		Validate.notNull(countryService,"countryService must be set");
		Validate.notNull(zoneService,"zoneService must be set");

		try {
			

			Map<String,Country> countriesMap = countryService.getCountriesMap(language);
			Map<String,Zone> zonesMap = zoneService.getZones(language);
			/** customer **/
			PersistableCustomer customer = source.getCustomer();
			if(customer!=null) {
				if(customer.getId()!=null && customer.getId()>0) {
					Customer modelCustomer = customerService.getById(customer.getId());
					if(modelCustomer==null) {
						throw new ConversionException("Customer id " + customer.getId() + " does not exists");
					}
					if(modelCustomer.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
						throw new ConversionException("Customer id " + customer.getId() + " does not exists for store " + store.getCode());
					}
					target.setCustomerId(modelCustomer.getId());
					target.setBilling(modelCustomer.getBilling());
					target.setDelivery(modelCustomer.getDelivery());
					target.setCustomerCity(modelCustomer.getCity());
					target.setCustomerFirstName(modelCustomer.getFirstname());
					target.setCustomerFirstName(modelCustomer.getLastname());
					target.setCustomerPostCode(modelCustomer.getPostalCode());
					target.setCustomerStreetAddress(modelCustomer.getStreetAddress());
					target.setCustomerTelephone(modelCustomer.getTelephone());
					target.setCustomerEmailAddress(source.getCustomer().getEmailAddress());
					if(modelCustomer.getCountry()==null) {
						throw new ConversionException("Customer country is null");
					}
					Country c = countriesMap.get(modelCustomer.getCountry().getIsoCode());
					if(c==null) {
						throw new ConversionException("Country code " + modelCustomer.getCountry().getIsoCode() + " is invalid");
					}
					target.setCustomerCountry(c.getName());
					if(modelCustomer.getZone()!=null) {
						Zone z = zonesMap.get(modelCustomer.getZone().getCode());
						if(z==null) {
							throw new ConversionException("Country zone code " + modelCustomer.getZone().getCode() + " is invalid");
						}
						target.setCustomerState(z.getName());
					}  else {
						target.setCustomerState(modelCustomer.getState());
					}
					
				} 
			}
			
			CreditCard creditCard = source.getCreditCard();
			if(creditCard!=null) {
				String maskedNumber = CreditCardUtils.maskCardNumber(creditCard.getCcNumber());
				creditCard.setCcNumber(maskedNumber);
				target.setCreditCard(creditCard);
			}
			
			target.setDatePurchased(source.getDatePurchased());
			target.setCurrency(store.getCurrency());
			target.setCurrencyValue(new BigDecimal(0));
			target.setMerchant(store);
			target.setStatus(source.getOrderStatus());
			target.setPaymentModuleCode(source.getPaymentModule());
			target.setPaymentType(source.getPaymentType());
			target.setShippingModuleCode(source.getShippingModule());
			if(source.getPreviousOrderStatus()!=null) {
				List<OrderStatus> orderStatusList = source.getPreviousOrderStatus();
				for(OrderStatus status : orderStatusList) {
					OrderStatusHistory statusHistory = new OrderStatusHistory();
					statusHistory.setStatus(status);
					statusHistory.setOrder(target);
					target.getOrderHistory().add(statusHistory);
				}
			}
			
			List<PersistableOrderProduct> products = source.getOrderProductItems();
			if(CollectionUtils.isEmpty(products)) {
				throw new ConversionException("Requires at least 1 PersistableOrderProduct");
			}
			PersistableOrderProductPopulator orderProductPopulator = new PersistableOrderProductPopulator();
			orderProductPopulator.setProductAttributeService(productAttributeService);
			orderProductPopulator.setProductService(productService);
			orderProductPopulator.setDigitalProductService(digitalProductService);
			
			for(PersistableOrderProduct orderProduct : products) {
				OrderProduct modelOrderProduct = new OrderProduct();
				orderProductPopulator.populate(orderProduct, modelOrderProduct, store, language);
				target.getOrderProducts().add(modelOrderProduct);
			}
			
			List<OrderTotal> orderTotals = source.getTotals();
			if(CollectionUtils.isNotEmpty(orderTotals)) {
				for(OrderTotal total : orderTotals) {
					com.salesmanager.core.business.order.model.OrderTotal totalModel = new com.salesmanager.core.business.order.model.OrderTotal();
					totalModel.setModule(total.getModule());
					totalModel.setOrder(target);
					totalModel.setOrderTotalCode(total.getCode());
					totalModel.setTitle(total.getTitle());
					totalModel.setValue(total.getValue());
					target.getOrderTotal().add(totalModel);
				}
			}
			
		} catch (Exception e) {
			throw new ConversionException(e);
		}
		
		
		return target;
	}

	@Override
	protected Order createTarget() {
		return null;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setDigitalProductService(DigitalProductService digitalProductService) {
		this.digitalProductService = digitalProductService;
	}

	public DigitalProductService getDigitalProductService() {
		return digitalProductService;
	}

	public void setProductAttributeService(ProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}

	public ProductAttributeService getProductAttributeService() {
		return productAttributeService;
	}

}
