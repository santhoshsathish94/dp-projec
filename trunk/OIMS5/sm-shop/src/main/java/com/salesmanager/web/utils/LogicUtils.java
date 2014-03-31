package com.salesmanager.web.utils;

import java.util.List;

import com.google.gson.Gson;

public class LogicUtils {

	public static String getJsonString(List<String> stringList) {
		StringBuffer sb = new StringBuffer("[");
		for(int i=0; i < stringList.size(); i++) {
			if(i < (stringList.size() - 1)) {
				sb.append("\"" + stringList.get(i) + "\"").append(",");
			} else {
				sb.append("\"" + stringList.get(i) + "\"");
			}
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	public static String getJSONString(List<?> objectList) {
		
		String jsonString = "";
		try {
			
			jsonString = new Gson().toJson(objectList);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}
}
