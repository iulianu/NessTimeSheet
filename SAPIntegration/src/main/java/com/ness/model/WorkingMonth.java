package com.ness.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkingMonth {

	String userCode;
	List<WorkingDay> workingDays;
	
	public WorkingMonth()
	{
		
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<WorkingDay> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(List<WorkingDay> workingDays) {
		this.workingDays = workingDays;
	}
	
	public void addWorkingDay(WorkingDay wd){
		if (workingDays == null) {
			workingDays = new ArrayList<WorkingDay>();
		}
		workingDays.add(wd);
	}
	
	
}
