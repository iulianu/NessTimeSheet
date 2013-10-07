package com.ness.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {
	
	private String noOrder;
	private String customerName;
	
	public Order() 
	{
		
	}
	
	public Order(String nr, String cust)
	{
		noOrder = nr;
		customerName = cust;
	}

	public String getNoOrder() {
		return noOrder;
	}

	public void setNoOrder(String noOrder) {
		this.noOrder = noOrder;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
