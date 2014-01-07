package com.ness.mobility.timesheet.datasource.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.ness.mobility.timesheet.constants.Constants;
import com.ness.mobility.timesheet.datasource.TimesheetConstants;
import com.ness.mobility.timesheet.datasource.TimesheetConstants.MonthTimesheetTable;
import com.ness.mobility.timesheet.datasource.TimesheetConstants.MonthWorkingDaysTable;
import com.ness.mobility.timesheet.domain.WorkingDay;
import com.ness.mobility.timesheet.domain.WorkingMonth;

public class TimesheetDAO {

	public static boolean isMonthDataFetched(Context context, String monthyear) {
		String[] mSelectionArgs = new String[3];
    	String mSelectionClause = "";
    	
    	if (TextUtils.isEmpty(monthyear)) {
    	    return false;

    	} else {
    	    // Constructs a selection clause that matches the word that the user entered.
    	    mSelectionClause = MonthTimesheetTable.YEARMONTH + " = ?";
    	    mSelectionClause += " AND " + MonthTimesheetTable._RESULT + " = ?";
    	    mSelectionClause += " AND " + MonthTimesheetTable._STATUS + " = ?";
    	    // Moves the user's input string to the selection arguments.
    	    mSelectionArgs[0] = monthyear;
    	    mSelectionArgs[1] = String.valueOf(Constants.Results.SUCCESS);
    	    mSelectionArgs[2] = String.valueOf(Constants.Status.DONE);

    	}
    	
        Cursor cursor = context.getContentResolver().query(TimesheetConstants.CONTENT_URI_MONTHTMS,
        		null, mSelectionClause, mSelectionArgs, null);
        if (cursor.moveToFirst()) {
               return true;
        }
        cursor.close();
        return false;
	}
	
	public static boolean insertMonth(Context context, String monthyear, WorkingMonth wm) {
		String[] mSelectionArgs = new String[1];
    	String mSelectionClause = "";
    	
    	if (TextUtils.isEmpty(monthyear)) 
    	    return false;
    	int myID = getMonthYearID(context, monthyear);
    	if (myID < 0) 
    		return false;
    	mSelectionClause = MonthWorkingDaysTable.YEARMONTHDAY + " = ?";
    	mSelectionArgs[0] = myID + "";
    	 
    	context.getContentResolver().delete(TimesheetConstants.CONTENT_URI_MONTHWKD,
        		 mSelectionClause, mSelectionArgs);
    	
    	
    	HashMap<Integer, WorkingDay> lwd = wm.getWorkingDays();
    	ContentValues[] cvals = new ContentValues[lwd.size()];
    	int i = 0;
    	for (Entry<Integer, WorkingDay> wd : lwd.entrySet()) {
    		ContentValues cv = new ContentValues();
    		WorkingDay wday = wd.getValue();
    		cv.put(MonthWorkingDaysTable.YEARMONTHDAY, myID);
    		cv.put(MonthWorkingDaysTable.STATUSDAY, wday.getStatus());
    		cv.put(MonthWorkingDaysTable.DAYOFMONTH, wday.getDayOfMonth());
    		cv.put(MonthWorkingDaysTable.CREATED, System.currentTimeMillis());
    		
    		cvals[i++] = cv;
    	}
    	context.getContentResolver().bulkInsert(TimesheetConstants.CONTENT_URI_MONTHWKD, cvals);
    	
    	return true;
	}
	
	public static int getMonthYearID(Context context, String monthyear) {
		int  id = -1;
		String[] mSelectionArgs = {""};
    	String mSelectionClause = "";
    	 mSelectionClause = MonthTimesheetTable.YEARMONTH + " = ?";

 	    // Moves the user's input string to the selection arguments.
 	    mSelectionArgs[0] = monthyear;
 	    
 	   Cursor cursor = context.getContentResolver().query(TimesheetConstants.CONTENT_URI_MONTHTMS,
       		null, mSelectionClause, mSelectionArgs, null);
       if (cursor.moveToFirst()) {
             id   =cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID));
       }
       cursor.close();
       return id;
	}

	public static WorkingMonth getMonthDays(Context context,
			String monthyear) {
		int mdID = getMonthYearID(context, monthyear);
		
		String[] mSelectionArgs = new String[1];
    	String mSelectionClause = "";
    	
    	if (TextUtils.isEmpty(monthyear)) 
    	    return null;
    	
    	mSelectionClause = MonthWorkingDaysTable.YEARMONTHDAY + " = ?";
    	mSelectionArgs[0] = mdID + "";
    	 
    	Cursor cursor = context.getContentResolver().query(TimesheetConstants.CONTENT_URI_MONTHWKD,
        		 null, mSelectionClause, mSelectionArgs, null);
    	WorkingMonth wm = null;
    	
    	if(cursor != null && cursor.getCount() > 0){
    		wm = new WorkingMonth();
    		  cursor.moveToFirst();
    		  do{
    			  int dOfm = cursor.getInt(cursor.getColumnIndexOrThrow(MonthWorkingDaysTable.DAYOFMONTH));
    			  int sOfd = cursor.getInt(cursor.getColumnIndexOrThrow(MonthWorkingDaysTable.STATUSDAY));
    			  wm.addDay(new WorkingDay(dOfm, sOfd));
    		  }while(cursor.moveToNext());
    		
    		}
    	cursor.close();
		return wm;
	}
	
}
