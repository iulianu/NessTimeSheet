package com.ness.mobility.timesheet.datasource;

import com.ness.mobility.timesheet.datasource.TimesheetConstants.MonthWorkingDaysTable;
import com.ness.mobility.timesheet.datasource.TimesheetConstants.MonthTimesheetTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TimesheetDBHelper extends SQLiteOpenHelper {

    public final String TAG = getClass().getSimpleName();

    //Name of the database file
    private static final String DATABASE_NAME = "resttimesheet.db";
    private static final int DATABASE_VERSION = 1;

    public TimesheetDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
    	super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
            
    	 db.execSQL("PRAGMA foreign_keys=ON;");
    	 // CREATE monthtimesheet TABLE
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE " + MonthTimesheetTable.TABLE_NAME + " (");
        sqlBuilder.append(MonthTimesheetTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(MonthTimesheetTable._STATUS + " TEXT, ");
        sqlBuilder.append(MonthTimesheetTable._RESULT + " INTEGER, ");
        sqlBuilder.append(MonthTimesheetTable.YEARMONTH + " TEXT, ");        
        sqlBuilder.append(MonthWorkingDaysTable.CREATED + " INTEGER ");
        sqlBuilder.append(");");
        String sql = sqlBuilder.toString();
        Log.i(TAG, "Creating DB table with string: '" + sql + "'");
        db.execSQL(sql);
    	
        // CREATE MONTHWORIKINGDAYS TABLE
         sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE " + MonthWorkingDaysTable.TABLE_NAME + " (");
        sqlBuilder.append(MonthWorkingDaysTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(MonthWorkingDaysTable._STATUS + " TEXT, ");
        sqlBuilder.append(MonthWorkingDaysTable._RESULT + " INTEGER, ");
        sqlBuilder.append(MonthWorkingDaysTable.YEARMONTHDAY + " INTEGER, ");
        sqlBuilder.append(MonthWorkingDaysTable.DAYOFMONTH + " INTEGER, ");
        sqlBuilder.append(MonthWorkingDaysTable.STATUSDAY + " INTEGER, ");
        sqlBuilder.append(MonthWorkingDaysTable.CREATED + " INTEGER, ");
        sqlBuilder.append(" FOREIGN KEY("+MonthWorkingDaysTable.YEARMONTHDAY+") REFERENCES "+MonthTimesheetTable.TABLE_NAME+"("+MonthTimesheetTable._ID+") ");
        sqlBuilder.append(");");
         sql = sqlBuilder.toString();
        Log.i(TAG, "Creating DB table with string: '" + sql + "'");
        db.execSQL(sql);
            
            
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //Gets called when the database is upgraded, i.e. the version number changes
    }

}
