package com.ness.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkingDayWithActivities extends WorkingDay {

	List<LoggedWork> loggedWorkItems;
	
	public List<LoggedWork> getLoggedWorkItems() {
		return loggedWorkItems;
	}
	public void setLoggedWorkItems(List<LoggedWork> loggedWorkItems) {
		this.loggedWorkItems = loggedWorkItems;
	}
	
}
