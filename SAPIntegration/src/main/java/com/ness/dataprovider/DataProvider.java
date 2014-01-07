package com.ness.dataprovider;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import com.ness.model.WorkingDay;
import com.ness.model.WorkingMonth;


interface WDStatus {
    int UNCOMPLETE = 0;
    int COMPLETE_SAVED = 1;
    int COMPLETE_APPROVWED = 2;
    int HOLIDAY = 3;
    int WEEKEND = 4;
    int OTHER_MONTH = 5;
}

public class DataProvider {

	public static WorkingMonth getMonth(String monthYear) {
		WorkingMonth wm = new WorkingMonth();
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		int month = Integer.parseInt(monthYear.substring(0, 1));
		int year = Integer.parseInt(monthYear.substring(monthYear.length() - 4));
		if (monthYear.length() == 6) 
			month = Integer.parseInt(monthYear.substring(0, 2));
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		
		Random rand = new Random();
		for (int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH);i++)
		{
			WorkingDay wd = new WorkingDay();
			wd.setDayInMonth(i + 1);
			wd.setStatus(rand.nextInt(4));
			
			wm.addWorkingDay(wd);
		}
		
		return wm;
	}
}
