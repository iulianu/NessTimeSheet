package com.ness.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderList implements Serializable {

	private List<Order> items;

    public OrderList() {
        this.items = new ArrayList<Order>();
    }

    public OrderList(List<Order> items) {
        this.items = items;
    }

    public List<Order> getItems() {
        return items;
    }

    public void setItems(List<Order> items) {
        this.items = items;
    }
    
    public void addOrder(Order ord) {
    	items.add(ord);
    } 
   
    
    public Order hasOrder(String ordNo) {
    	for (Order ord : items) {
    		if (ord.getNoOrder().equals(ordNo))
    			return ord;
    	}
    	return null;
    }
	
}
