package com.salesmanager.test.isolated;

public class PasswordTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		org.springframework.security.authentication.encoding.ShaPasswordEncoder ff = new org.springframework.security.authentication.encoding.ShaPasswordEncoder();
		
		System.out.println(ff.encodePassword("test123#", null));

	}

}
