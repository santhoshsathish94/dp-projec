package com.salesmanager.core.business.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.salesmanager.core.business.tax.model.taxclass.TaxClass;

public class SalesOrderBooking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5287202556021148489L;

	
	public Long customerId;
	
	public String bookingDate;
	
	public String comment;
	
	public String productJson;
	
	


	/**
	 * @return the productJson
	 */
	public String getProductJson() {
		return productJson;
	}


	/**
	 * @param productJson the productJson to set
	 */
	public void setProductJson(String productJson) {
		this.productJson = productJson;
	}


	/**
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}


	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the date
	 */
	public String getBookingDate() {
		return bookingDate;
	}


	/**
	 * @param date the date to set
	 */
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}


	/**
	 * @return the commant
	 */
	public String getComment() {
		return comment;
	}


	/**
	 * @param commant the commant to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	public class SalesOrderBookingProducts {
		
		public Long productId;
		
		public String productName;
		
		public Long varientId;
		
		public String description;
		
		public int quantity;
		
		public String uom;
		
		public BigDecimal untiPrice;
		
		public TaxClass taxClass;

		
		
		/**
		 * @return the productId
		 */
		public Long getProductId() {
			return productId;
		}

		/**
		 * @param productId the productId to set
		 */
		public void setProductId(Long productId) {
			this.productId = productId;
		}

		/**
		 * @return the productName
		 */
		public String getProductName() {
			return productName;
		}

		/**
		 * @param productName the productName to set
		 */
		public void setProductName(String productName) {
			this.productName = productName;
		}

		/**
		 * @return the varientId
		 */
		public Long getVarientId() {
			return varientId;
		}

		/**
		 * @param varientId the varientId to set
		 */
		public void setVarientId(Long varientId) {
			this.varientId = varientId;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * @return the quantity
		 */
		public int getQuantity() {
			return quantity;
		}

		/**
		 * @param quantity the quantity to set
		 */
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		/**
		 * @return the uom
		 */
		public String getUom() {
			return uom;
		}

		/**
		 * @param uom the uom to set
		 */
		public void setUom(String uom) {
			this.uom = uom;
		}

		/**
		 * @return the untiPrice
		 */
		public BigDecimal getUntiPrice() {
			return untiPrice;
		}

		/**
		 * @param untiPrice the untiPrice to set
		 */
		public void setUntiPrice(BigDecimal untiPrice) {
			this.untiPrice = untiPrice;
		}

		/**
		 * @return the taxClass
		 */
		public TaxClass getTaxClass() {
			return taxClass;
		}

		/**
		 * @param taxClass the taxClass to set
		 */
		public void setTaxClass(TaxClass taxClass) {
			this.taxClass = taxClass;
		}
		
		
		
	}



}
