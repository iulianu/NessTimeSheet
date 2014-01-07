package com.ness.mobility.timesheet.datasource.provider;

import static android.provider.BaseColumns._ID;
import com.ness.mobility.timesheet.datasource.TimesheetConstants;
import com.ness.mobility.timesheet.datasource.TimesheetConstants.MonthTimesheetTable;
import com.ness.mobility.timesheet.datasource.TimesheetConstants.MonthWorkingDaysTable;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.ness.mobility.timesheet.datasource.TimesheetDBHelper;
import com.ness.mobility.timesheet.datasource.provider.TimesheetProviderContract.MonthWorkingDaysPrvCtrct;

public class MonthWorkingDaysProvider extends ContentProvider {

    private static final String TAG = MonthWorkingDaysProvider.class.getSimpleName();

    private static final int WORKINGDAYS = 1;
    private static final int WORKINGDAY = 2;
    private static final int MONTHTIMESHEET = 3;
    private static final int MONTHDAY = 4;

    /**
     * The MIME type of a directory of events
     */
    private static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.example.mworkdays";

    /**
     * The MIME type of a single event
     */
    private static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.example.mworkday";

    private TimesheetDBHelper dbHelper;
    private UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(TimesheetProviderContract.AUTHORITY, "monthworkingdays", WORKINGDAYS);
            uriMatcher.addURI(TimesheetProviderContract.AUTHORITY, "monthworkingdays/#", WORKINGDAY);
            uriMatcher.addURI(TimesheetProviderContract.AUTHORITY, "monthtimesheet", MONTHTIMESHEET);
            uriMatcher.addURI(TimesheetProviderContract.AUTHORITY, "monthtimesheet/#", MONTHDAY);
            this.dbHelper = new TimesheetDBHelper(this.getContext());
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                    String[] selectionArgs, String orderBy) {
            if (uriMatcher.match(uri) == WORKINGDAY || uriMatcher.match(uri) == MONTHDAY) {
                    long id = Long.parseLong(uri.getPathSegments().get(1));
                    selection = appendRowId(selection, id);
            }

            String tablname = getTableName(uri);
            // Get the database and run the query
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(tablname, projection, selection,
                            selectionArgs, null, null, orderBy);

            // Tell the cursor what uri to watch, so it knows when its
            // source data changes
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
    }

    
    
    @Override
    public String getType(Uri uri) {
            switch (uriMatcher.match(uri)) {
            case WORKINGDAYS:
                    return CONTENT_TYPE;
            case WORKINGDAY:
                    return CONTENT_ITEM_TYPE;
            case MONTHTIMESHEET:
                return CONTENT_TYPE;
            case MONTHDAY:
                return CONTENT_ITEM_TYPE;
            default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Validate the requested uri
            if (uriMatcher.match(uri) != WORKINGDAYS && uriMatcher.match(uri) != MONTHTIMESHEET) {
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
            
            // Insert into database
            long id = db.insertOrThrow(getTableName(uri), null, values);

            // Notify any watchers of the change
            Uri newUri = ContentUris.withAppendedId(getContentUri(uri), id);

            Log.d(TAG, "New profile URI: " + newUri.toString());

            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
    }
    
    //Default bulkInsert is terrible.  Make it better!
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
            this.validateOrThrow(uri);
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            db.beginTransaction();
            int insertedCount = 0;
            long newRowId = -1;
            try {
                    for (ContentValues cv : values) {
                            newRowId = this.insert(uri, cv, db);
                            insertedCount++;
                    }
                    db.setTransactionSuccessful();
                    // Build a new Node URI appended with the row ID of the last node to get inserted in the batch
                    Uri nodeUri = ContentUris.withAppendedId(MonthWorkingDaysPrvCtrct.CONTENT_ID_URI_BASE, newRowId);
                    // Notify observers that our data changed.
                    getContext().getContentResolver().notifyChange(nodeUri, null);
                    return insertedCount;

            } finally {
                    db.endTransaction();
            }
    }
    
    //Used by our implementation of builkInsert
    private long insert(Uri uri, ContentValues initialValues, SQLiteDatabase writableDb) {
            // NOTE: this method does not initiate a transaction - this is up to the caller!
            ContentValues values;
            if (initialValues != null) {
                    values = new ContentValues(initialValues);
            } else {
                    throw new SQLException("ContentValues arg for .insert() is null, cannot insert row.");
            }

            long newRowId = writableDb.insert(getTableName(uri), null, values);
            if (newRowId == -1) { // if rowID is -1, it means the insert failed
                    throw new SQLException("Failed to insert row into " + uri); // Insert failed: halt and catch fire.
            }
            return newRowId;
    }

    private void validateOrThrow(Uri uri) {
            // Validate the incoming URI.
            if (uriMatcher.match(uri) != WORKINGDAYS) {
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

    	SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Validate the requested uri
        if (uriMatcher.match(uri) != WORKINGDAYS && uriMatcher.match(uri) != MONTHDAY) {
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        
        return db.delete(getTableName(uri), selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Validate the requested uri
            if (uriMatcher.match(uri) != WORKINGDAY && uriMatcher.match(uri) != MONTHDAY) {
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }

            long id = ContentUris.parseId(uri);
            int affected = db.update(getTableName(uri), values, "_id="+id, null);

            Log.d(TAG, "Updated profile URI: " + uri.toString());

            getContext().getContentResolver().notifyChange(uri, null);
            return affected;
    }
    
    
    
    

    /**
     * Append an id test to a SQL selection expression
     */
    private String appendRowId(String selection, long id) {
            return _ID
                            + "="
                            + id
                            + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')'
                                            : "");
    }
    
    
    /**
     * 
     * @param uri
     * @return
     */
    
    private String getTableName(Uri uri) {
    	switch(uriMatcher.match(uri)){
    	case WORKINGDAY:
    		return MonthWorkingDaysTable.TABLE_NAME;
    	case WORKINGDAYS:
    		return MonthWorkingDaysTable.TABLE_NAME;
    	case MONTHTIMESHEET:
    		return MonthTimesheetTable.TABLE_NAME;
    	case MONTHDAY:
    		return MonthTimesheetTable.TABLE_NAME;
    	default:
    		return null;
    	}
    }
    
    
    private Uri getContentUri(Uri uri) {
    	switch(uriMatcher.match(uri)){
    	case WORKINGDAY:
    		return TimesheetConstants.CONTENT_URI_MONTHWKD;
    	case WORKINGDAYS:
    		return TimesheetConstants.CONTENT_URI_MONTHWKD;
    	case MONTHTIMESHEET:
    		return TimesheetConstants.CONTENT_URI_MONTHTMS;
    	case MONTHDAY:
    		return TimesheetConstants.CONTENT_URI_MONTHTMS;
    	default:
    		return null;
    	}
    }

}
