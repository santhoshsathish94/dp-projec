package com.salesmanager.core.business.adapter;

public class CustomCustomer {

	public Long id;

	public String name;

	public String nick;

	public String company;

	public CustomCustomer() {
	}

	public CustomCustomer(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public CustomCustomer(Long id, String name, String nick, String company) {
		this.id = id;
		this.name = name;
		this.name = nick;
		this.name = company;
	}
}
