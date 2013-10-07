package com.ness.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Networks implements Serializable {

	private List<Network> items;

    public Networks() {
        this.items = new ArrayList< Network>();
    }

    public  Networks(List< Network> items) {
        this.items = items;
    }

    public List< Network> getItems() {
        return items;
    }

    public void setItems(List< Network> items) {
        this.items = items;
    }
}
