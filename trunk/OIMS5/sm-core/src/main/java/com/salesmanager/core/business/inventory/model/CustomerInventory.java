/**
 * 
 */
package com.salesmanager.core.business.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.business.merchant.model.MerchantStore;

/**
 * @author iShop
 * 
 */

@Entity
@Table(name = "CUSTOMER_INVENTORY")
public class CustomerInventory extends SalesManagerEntity<Long, CustomerInventory> {

	private static final long serialVersionUID = 2676237056070958550L;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private MerchantStore merchantStore;

	@Column(name = "CUSTOMER_ID")
	private Long customerid;

	@Column(name = "INVENTORY_ID")
	private Long inventoryid;

	@Column(name = "INVENTORY_TYPE")
	private String inventory_type;

	@Transient
	private String jsonArray;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public void reset() {

	}

	public Long getPurchaseid() {
		return customerid;
	}

	public void setPurchaseid(Long purchaseid) {
		this.customerid = purchaseid;
	}

	public String getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(String jsonArray) {
		this.jsonArray = jsonArray;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public Long getInventoryid() {
		return inventoryid;
	}

	public void setInventoryid(Long inventoryid) {
		this.inventoryid = inventoryid;
	}

	public String getInventory_type() {
		return inventory_type;
	}

	public void setInventory_type(String inventory_type) {
		this.inventory_type = inventory_type;
	}

}
