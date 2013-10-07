package com.ness.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Activities implements Serializable{

	private List<Activity> items;

    public Activities() {
        this.items = new ArrayList<Activity>();
    }

    public  Activities(List<Activity> items) {
        this.items = items;
    }

    public List<Activity> getItems() {
        return items;
    }

    public void setItems(List<Activity> items) {
        this.items = items;
    }
}
