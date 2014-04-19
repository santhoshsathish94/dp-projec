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

import org.elasticsearch.common.Required;
import org.hibernate.validator.constraints.NotEmpty;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.constants.SchemaConstant;

/**
 * @author iShop
 * 
 */

@Entity
@Table(name = "PURCHASE_RETURN_DEBIT_NOTE")
public class PurchaseReturnDebitNote extends SalesManagerEntity<Long, PurchaseReturnDebitNote> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2676237056070958550L;

	@Id
	@Column(name = "DEBIT_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long debit_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private MerchantStore merchantStore;

	@Column(name = "DEBIT_SUPPLIER")
	private String debit_supplier;

	@Column(name = "DEBIT_DATE")
	private Date debit_date;
	@Transient
	private String debit_Sdate;

	@Column(name = "DEBIT_REF_NUMBER")
	private String debit_ref_number;

	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Column(name = "DEBIT_SKU")
	private String debit_sku;

	@Column(name = "DEBIT_DESCRIPTION")
	private String debit_description;

	@Column(name = "DEBIT_QUANTITY")
	private Integer debit_quantity;

	@Column(name = "DEBIT_UNIT_PRICE")
	private BigDecimal debit_unit_price;

	@Column(name = "DEBIT_TAX")
	private String debit_tax;

	@Column(name = "DEBIT_TAX_AMOUNT")
	private BigDecimal debit_tax_amount;
	@Required
	@Column(name = "DEBIT_AMOUNT")
	private BigDecimal debit_amount;

	@Column(name = "DEBIT_INVOICE_NUMBER")
	private String debit_invoice_number;

	@Column(name = "DEBIT_CREDIT_TO")
	private String debit_credit_to;

	@Column(name = "CREATED_BY")
	private String created_by;

	public String getDebit_credit_to() {
		return debit_credit_to;
	}

	public void setDebit_credit_to(String debit_credit_to) {
		this.debit_credit_to = debit_credit_to;
	}

	public String getDebit_invoice_number() {
		return debit_invoice_number;
	}

	public void setDebit_invoice_number(String debit_invoice_number) {
		this.debit_invoice_number = debit_invoice_number;
	}

	@Transient
	private String jsonArray;

	@Column(name = "ENTRY_DATE")
	private Date entry_date;
	@Transient
	private String entry_Sdate;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.debit_id;
	}

	@Override
	public void setId(Long id) {
		this.debit_id = id;

	}

	public void reset() {

	}

	public Long getDebit_id() {
		return debit_id;
	}

	public void setDebit_id(Long debit_id) {
		this.debit_id = debit_id;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public String getDebit_supplier() {
		return debit_supplier;
	}

	public void setDebit_supplier(String debit_supplier) {
		this.debit_supplier = debit_supplier;
	}

	public Date getDebit_date() {
		return debit_date;
	}

	public void setDebit_date(Date debit_date) {
		this.debit_date = debit_date;
	}

	public String getDebit_Sdate() {
		return debit_Sdate;
	}

	public void setDebit_Sdate(String debit_Sdate) {
		this.debit_Sdate = debit_Sdate;
	}

	public String getDebit_ref_number() {
		return debit_ref_number;
	}

	public void setDebit_ref_number(String debit_ref_number) {
		this.debit_ref_number = debit_ref_number;
	}

	public String getDebit_sku() {
		return debit_sku;
	}

	public void setDebit_sku(String debit_sku) {
		this.debit_sku = debit_sku;
	}

	public String getDebit_description() {
		return debit_description;
	}

	public void setDebit_description(String debit_description) {
		this.debit_description = debit_description;
	}

	public Integer getDebit_quantity() {
		return debit_quantity;
	}

	public void setDebit_quantity(Integer debit_quantity) {
		this.debit_quantity = debit_quantity;
	}

	public BigDecimal getDebit_unit_price() {
		return debit_unit_price;
	}

	public void setDebit_unit_price(BigDecimal debit_unit_price) {
		this.debit_unit_price = debit_unit_price;
	}

	public String getDebit_tax() {
		return debit_tax;
	}

	public void setDebit_tax(String debit_tax) {
		this.debit_tax = debit_tax;
	}

	public BigDecimal getDebit_tax_amount() {
		return debit_tax_amount;
	}

	public void setDebit_tax_amount(BigDecimal debit_tax_amount) {
		this.debit_tax_amount = debit_tax_amount;
	}

	public BigDecimal getDebit_amount() {
		return debit_amount;
	}

	public void setDebit_amount(BigDecimal debit_amount) {
		this.debit_amount = debit_amount;
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
