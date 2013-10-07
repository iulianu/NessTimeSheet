package com.ness.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoggedWork {

	Integer hours;
	Network network;
	Activity activity;
	
	public LoggedWork(){}
	
	public Integer getHours() {
		return hours;
	}
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	public Network getNetwork() {
		return network;
	}
	public void setNetwork(Network network) {
		this.network = network;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}	
}
