/**
 * 
 */
package com.salesmanager.core.business.inventory.model;

import java.math.BigDecimal;
import java.util.Date;

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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.constants.SchemaConstant;

/**
 * @author iShop
 * 
 */

@Entity
@Table(name = "PURCHASE", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class Purchase extends SalesManagerEntity<Long, Purchase> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2676237056070958550L;

	@Id
	@Column(name = "PURCHASE_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long purchaseid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private MerchantStore merchantStore;

	@Column(name = "PURCHASE_SUPPLIER")
	private String purchase_supplier;

	@Column(name = "PURCHASE_DATE")
	private Date purchase_date;
	@Transient
	private String purchase_Sdate;

	@Column(name = "PURCHASE_DUE_DATE")
	private Date purchase_due_date;
	@Transient
	private String purchase_due_Sdate;

	@Column(name = "PURCHASE_REF_NUMBER")
	private String purchase_ref_number;

	@Column(name = "PURCHASE_COMMENT")
	private String purchase_comment;

	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Column(name = "PURCHASE_SKU")
	private String purchase_sku;

	@Column(name = "PURCHASE_DESCRIPTION")
	private String purchase_description;

	@Column(name = "PURCHASE_QUANTITY")
	private Integer purchase_quantity;

	@Column(name = "PURCHASE_UOM")
	private String purchase_uom;

	@Column(name = "PURCHASE_UNIT_PRICE")
	private BigDecimal purchase_unit_price;

	@Column(name = "PURCHASE_TAX")
	private String purchase_tax;

	@Column(name = "PURCHASE_AMOUNT")
	private BigDecimal purchase_amount;

	@Column(name = "CREATED_BY")
	private String created_by;

	@Transient
	private String jsonArray;

	@Column(name = "ENTRY_DATE")
	private Date entry_date;
	@Transient
	private String entry_Sdate;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.purchaseid;
	}

	@Override
	public void setId(Long id) {
		this.purchaseid = id;

	}

	public void reset() {

	}

	public Long getPurchaseid() {
		return purchaseid;
	}

	public void setPurchaseid(Long purchaseid) {
		this.purchaseid = purchaseid;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public String getPurchase_supplier() {
		return purchase_supplier;
	}

	public void setPurchase_supplier(String purchase_supplier) {
		this.purchase_supplier = purchase_supplier;
	}

	public Date getPurchase_date() {
		return purchase_date;
	}

	public void setPurchase_date(Date purchase_date) {
		this.purchase_date = purchase_date;
	}

	public String getPurchase_Sdate() {
		return purchase_Sdate;
	}

	public void setPurchase_Sdate(String purchase_Sdate) {
		this.purchase_Sdate = purchase_Sdate;
	}

	public Date getPurchase_due_date() {
		return purchase_due_date;
	}

	public void setPurchase_due_date(Date purchase_due_date) {
		this.purchase_due_date = purchase_due_date;
	}

	public String getPurchase_due_Sdate() {
		return purchase_due_Sdate;
	}

	public void setPurchase_due_Sdate(String purchase_due_Sdate) {
		this.purchase_due_Sdate = purchase_due_Sdate;
	}

	public String getPurchase_ref_number() {
		return purchase_ref_number;
	}

	public void setPurchase_ref_number(String purchase_ref_number) {
		this.purchase_ref_number = purchase_ref_number;
	}

	public String getPurchase_comment() {
		return purchase_comment;
	}

	public void setPurchase_comment(String purchase_comment) {
		this.purchase_comment = purchase_comment;
	}

	public String getPurchase_sku() {
		return purchase_sku;
	}

	public void setPurchase_sku(String purchase_sku) {
		this.purchase_sku = purchase_sku;
	}

	public String getPurchase_description() {
		return purchase_description;
	}

	public void setPurchase_description(String purchase_description) {
		this.purchase_description = purchase_description;
	}

	public Integer getPurchase_quantity() {
		return purchase_quantity;
	}

	public void setPurchase_quantity(Integer purchase_quantity) {
		this.purchase_quantity = purchase_quantity;
	}

	public String getPurchase_uom() {
		return purchase_uom;
	}

	public void setPurchase_uom(String purchase_uom) {
		this.purchase_uom = purchase_uom;
	}

	public BigDecimal getPurchase_unit_price() {
		return purchase_unit_price;
	}

	public void setPurchase_unit_price(BigDecimal purchase_unit_price) {
		this.purchase_unit_price = purchase_unit_price;
	}

	public String getPurchase_tax() {
		return purchase_tax;
	}

	public void setPurchase_tax(String purchase_tax) {
		this.purchase_tax = purchase_tax;
	}

	public BigDecimal getPurchase_amount() {
		return purchase_amount;
	}

	public void setPurchase_amount(BigDecimal purchase_amount) {
		this.purchase_amount = purchase_amount;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(String jsonArray) {
		this.jsonArray = jsonArray;
	}

	public Date getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}

	public String getEntry_Sdate() {
		return entry_Sdate;
	}

	public void setEntry_Sdate(String entry_Sdate) {
		this.entry_Sdate = entry_Sdate;
	}

}
