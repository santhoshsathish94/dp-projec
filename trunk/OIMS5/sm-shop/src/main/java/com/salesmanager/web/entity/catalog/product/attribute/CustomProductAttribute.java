package com.salesmanager.web.entity.catalog.product.attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.salesmanager.core.business.catalog.product.model.attribute.ProductAttribute;
import com.salesmanager.core.business.service.utils.LogicUtils;

public class CustomProductAttribute {

	public CustomProductAttribute.Shade shade;
	
	public List<CustomProductAttribute.Variant> variant;
	
	public String getProductAttributeAsJson(List<ProductAttribute> productAttributes) {
		
		List<CustomProductAttribute> customProductAttributes = new ArrayList<CustomProductAttribute>();
		
		Map<Long, List<CustomProductAttribute.Variant>> shadeVariantMap1 = new HashMap<Long, List<CustomProductAttribute.Variant>>();
		
		for(ProductAttribute productAttribute: productAttributes) {
			
			List<CustomProductAttribute.Variant> variantList = null;
			
			if(shadeVariantMap1.containsKey(productAttribute.getProductOption().getId())) {
				variantList = shadeVariantMap1.get(productAttribute.getProductOption().getId());
			} else {
				variantList = new ArrayList<CustomProductAttribute.Variant>();
			}
			CustomProductAttribute.Variant variant = new CustomProductAttribute().new Variant();
			variant.id = productAttribute.getProductOptionValue().getId();
			variant.name = productAttribute.getProductOptionValue().getDescriptionsSettoList().get(0).getName();
			
			variantList.add(variant);
			shadeVariantMap1.put(productAttribute.getProductOption().getId(), variantList);
		}
		
		Map<Long, CustomProductAttribute.Shade> shadeMap1 = new HashMap<Long, CustomProductAttribute.Shade>();
		for(ProductAttribute productAttribute: productAttributes) {
			if(!shadeMap1.containsKey(productAttribute.getProductOption().getId())) {
				CustomProductAttribute.Shade shade = new CustomProductAttribute().new Shade();
				shade.id = productAttribute.getProductOption().getId();
				shade.name = productAttribute.getProductOption().getDescriptionsSettoList().get(0).getName();
				
				shadeMap1.put(productAttribute.getProductOption().getId(), shade);
			}
		}
		
		for (Map.Entry<Long, CustomProductAttribute.Shade> entry : shadeMap1.entrySet()) {
			
			CustomProductAttribute customProductAttribute = new CustomProductAttribute();
			
			customProductAttribute.shade = entry.getValue();
			customProductAttribute.variant = shadeVariantMap1.get(entry.getKey());
			
			customProductAttributes.add(customProductAttribute);
		}
		
		return LogicUtils.getJSONString(customProductAttributes);
	}
	
	public class Shade {
		
		public Long id;
		
		public String name;
	}
	
	
	public class Variant {
		
		Long id;
		
		String name;
	}
	
	/*Map<Long, List<Long>> variantMap = new HashMap<Long, List<Long>>();
	
	for(ProductAttribute productAttribute: productAttributes) {
		List<Long> variantList = null;
		if(variantMap.containsKey(productAttribute.getProductOption().getId())) {
			variantList = variantMap.get(productAttribute.getProductOption().getId()); 
			variantList.add(productAttribute.getProductOptionValue().getId());
		} else {
			variantList = new ArrayList<Long>();
			variantList.add(productAttribute.getProductOptionValue().getId());
		}
		
		variantMap.put(productAttribute.getProductOption().getId(), variantList);
	}
	
	for (Map.Entry<Long, List<Long>> entry : variantMap.entrySet()) {
		CustomProductAttribute customProductAttribute = new CustomProductAttribute();
		customProductAttribute.shade = entry.getKey();
		customProductAttribute.variant = entry.getValue();
		
		customProductAttributes.add(customProductAttribute);
	}*/
	
}
