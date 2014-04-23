package com.salesmanager.core.modules.integration.shipping.model;

import java.math.BigDecimal;

public abstract class CustomShippingQuoteItem {
	
	private String priceText="Wait Base";
	private BigDecimal price=new BigDecimal(11.20);
	public void setPriceText(String priceText) {
		this.priceText = "Wait Base";
	}
	public String getPriceText() {
		return priceText;
	}
	public void setPrice(BigDecimal price) {
		this.price = new BigDecimal(11.20);
	}
	public BigDecimal getPrice() {
		return price;
	}

}
