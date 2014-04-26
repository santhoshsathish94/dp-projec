package com.salesmanager.core.business.service.utils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LogicUtils {

	public static Gson gson = new Gson();
	static Type collectionListType = new TypeToken<List<?>>(){}.getType();
	
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
			
			jsonString = gson.toJson(objectList);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}
	
	public static String getJSONStringFromMap(Map<?, ?> objectMap) {
		String jsonString = "";
		try {
			
			jsonString = gson.toJson(objectMap);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}
	
	
	public static  List<?> getObjectListFromJson(String jsonString, Type collectionListType) {
		return gson.fromJson(jsonString, collectionListType);
	}
	
	
	/*public static void main(String[] args) {
		
		List<LogicUtils.Employee> empList = new ArrayList<LogicUtils.Employee>();
		
		empList.add(new LogicUtils().new Employee(Long.valueOf("1"), "Abhishek", "Verma"));
		empList.add(new LogicUtils().new Employee(Long.valueOf("2"), "Jyoti", "Verma"));
		empList.add(new LogicUtils().new Employee(Long.valueOf("3"), "Dilip", "Prakash"));
		empList.add(new LogicUtils().new Employee(Long.valueOf("4"), "Ankit", "Rawat"));
		empList.add(new LogicUtils().new Employee(Long.valueOf("5"), "Abhinav", "Verma"));
		
		
		String jsonString = getJSONString(empList);
		
		System.out.println(jsonString);
		
		Type collectionListType = new TypeToken<List<LogicUtils.Employee>>(){}.getType();
		
		@SuppressWarnings("unchecked")
		List<LogicUtils.Employee> details = (List<Employee>)getObjectListFromJson(jsonString, collectionListType);
		
		if(details != null && details.size() > 0) {
			
			for(LogicUtils.Employee emp: empList) {
				
				System.out.println(emp.getId());
				System.out.println(emp.getfName());
				System.out.println(emp.getlName());
				System.out.println("\n");
			}
		}
		
	}
	
	
	class Employee {
		
		private Long id;
		
		private String fName;
		
		private String lName;
		
		
		public Employee(Long id, String fName, String lName) {
			this.id = id;
			this.fName = fName;
			this.lName = lName;
		}

		*//**
		 * @return the id
		 *//*
		public Long getId() {
			return id;
		}

		*//**
		 * @param id the id to set
		 *//*
		public void setId(Long id) {
			this.id = id;
		}

		*//**
		 * @return the fName
		 *//*
		public String getfName() {
			return fName;
		}

		*//**
		 * @param fName the fName to set
		 *//*
		public void setfName(String fName) {
			this.fName = fName;
		}

		*//**
		 * @return the lName
		 *//*
		public String getlName() {
			return lName;
		}

		*//**
		 * @param lName the lName to set
		 *//*
		public void setlName(String lName) {
			this.lName = lName;
		}
		
		
	}*/
	
}
