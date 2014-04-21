package com.salesmanager.core.business.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.salesmanager.core.business.customer.model.Customer;
import com.salesmanager.core.business.tax.model.taxclass.TaxClass;

public class SalesOrderBooking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5287202556021148489L;

	
	public Customer customer;
	
	public String bookingDate;
	
	public String commant;
	
	
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}


	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	public String getCommant() {
		return commant;
	}


	/**
	 * @param commant the commant to set
	 */
	public void setCommant(String commant) {
		this.commant = commant;
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
