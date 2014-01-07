package com.ness.mobility.timesheet.processors;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.ness.mobility.timesheet.constants.Constants;
import com.ness.mobility.timesheet.datasource.TimesheetConstants;
import com.ness.mobility.timesheet.datasource.TimesheetConstants.MonthTimesheetTable;
import com.ness.mobility.timesheet.datasource.dao.TimesheetDAO;
import com.ness.mobility.timesheet.rest.client.RestMethod;
import com.ness.mobility.timesheet.rest.client.RestMethodResult;
import com.ness.mobility.timesheet.rest.methods.MonthWorkingHoursRestMethod;
import com.ness.mobility.timesheet.rest.resource.WorkingMonthRest;

public class WorkingHoursProcessor {
	protected static final String TAG = WorkingHoursProcessor.class
			.getSimpleName();

	private WorkingHoursProcessorCallback mCallback;
	private Context mContext;

	public WorkingHoursProcessor(Context context) {
		mContext = context;
	}

	public int getMonthWorkingHours(
			String userCode, String monthyear) {

		prepareContentProvider(monthyear, Constants.Status.PENDING,
				Constants.Results.UNPROCESSED);

		Map<String, String> params = new HashMap<String, String>();
		params.put("userCode", userCode);
		params.put("monthYear", monthyear);

		RestMethod<WorkingMonthRest> getMonthHoursMethod = new MonthWorkingHoursRestMethod(
				mContext, null, params);
		RestMethodResult<WorkingMonthRest> result = getMonthHoursMethod
				.execute();

		
		if (result.getStatusCode() != 506 && result.getResource() != null)
			updateContentProvider(result, monthyear);

		

		return result.getStatusCode();

	}

	private void updateContentProvider(
			RestMethodResult<WorkingMonthRest> result, String monthyear) {

		if (insertMonthDays(monthyear, result.getResource()))
			prepareContentProvider(monthyear, Constants.Status.DONE,
					Constants.Results.SUCCESS);
	}

	private boolean insertMonthDays(String monthyear, WorkingMonthRest mw) {
		return TimesheetDAO.insertMonth(mContext, monthyear, mw.getDays());
	}

	private void prepareContentProvider(String monthyear, int status, int result) {

		String[] mSelectionArgs = { "" };
		String mSelectionClause = "";

		if (TextUtils.isEmpty(monthyear)) {
			// Setting the selection clause to null will return all words
			mSelectionClause = null;
			mSelectionArgs[0] = "";

		} else {
			// Constructs a selection clause that matches the word that the user
			// entered.
			mSelectionClause = MonthTimesheetTable.YEARMONTH + " = ?";

			// Moves the user's input string to the selection arguments.
			mSelectionArgs[0] = monthyear;

		}

		ContentValues cv = new ContentValues();
		cv.put(MonthTimesheetTable.YEARMONTH, monthyear);
		cv.put(MonthTimesheetTable._RESULT, result);
		cv.put(MonthTimesheetTable._STATUS, status);

		Cursor cursor = mContext.getContentResolver().query(
				TimesheetConstants.CONTENT_URI_MONTHTMS, null,
				mSelectionClause, mSelectionArgs, null);
		if (cursor.moveToFirst()) {
			int id = cursor.getInt(cursor
					.getColumnIndexOrThrow(BaseColumns._ID));
			mContext.getContentResolver().update(
					ContentUris.withAppendedId(
							TimesheetConstants.CONTENT_URI_MONTHTMS, id), cv,
					mSelectionClause, mSelectionArgs);
		} else {
			mContext.getContentResolver().insert(
					TimesheetConstants.CONTENT_URI_MONTHTMS, cv);
		}
		cursor.close();

	}
}
