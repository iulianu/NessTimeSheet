package com.ness.mobility.timesheet.domain;

import java.util.HashMap;





public class  WorkingMonth {

	String userCode;
	HashMap<Integer, WorkingDay> workingDays;
	
	public WorkingMonth()
	{
		
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public HashMap<Integer, WorkingDay> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(HashMap<Integer, WorkingDay> workingDays) {
		this.workingDays = workingDays;
	}
	
	public void addDay(WorkingDay wd) {
		if (workingDays == null) {
			workingDays = new HashMap<Integer, WorkingDay>();
		}
		workingDays.put(wd.getDayOfMonth(),wd);
	}
}


