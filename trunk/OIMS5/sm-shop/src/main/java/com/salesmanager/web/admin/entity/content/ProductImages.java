package com.salesmanager.web.admin.entity.content;

public class ProductImages extends ContentFiles {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7732719188032287938L;
	private long productId;
	private Long image_product_option;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public Long getImage_product_option() {
		return image_product_option;
	}

	public void setImage_product_option(Long image_product_option) {
		this.image_product_option = image_product_option;
	}

}
