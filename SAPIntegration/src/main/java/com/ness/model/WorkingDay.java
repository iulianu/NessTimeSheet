package com.ness.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkingDay {

		
	Integer dayInMonth;
	Integer status;
	
	public WorkingDay()
	{} 
	
	
	public Integer getDayInMonth() {
		return dayInMonth;
	}
	public void setDayInMonth(Integer dayInMonth) {
		this.dayInMonth = dayInMonth;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
