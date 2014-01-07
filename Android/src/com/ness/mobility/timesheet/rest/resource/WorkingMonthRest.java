package com.ness.mobility.timesheet.rest.resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ness.mobility.timesheet.domain.WorkingDay;
import com.ness.mobility.timesheet.domain.WorkingMonth;

public class WorkingMonthRest implements Resource {
	
	private WorkingMonth workingMonth;
	private JSONObject data;
	
	
	public WorkingMonthRest(JSONObject json) {
		data = json;
	}
	
	public WorkingMonth getDays() {
		workingMonth = new WorkingMonth();
		try {
			JSONArray wd = data.getJSONArray("workingDays");
			for (int i = 0; i < wd.length();i++)  {
				JSONObject d = wd.getJSONObject(i);
				int sts = d.getInt("status");
				int day = d.getInt("dayInMonth");
				workingMonth.addDay(new WorkingDay(day, sts));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		return workingMonth;
	}

}
