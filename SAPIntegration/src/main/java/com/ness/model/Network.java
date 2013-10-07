package com.ness.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Network {

	private String code;
	private String name;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
