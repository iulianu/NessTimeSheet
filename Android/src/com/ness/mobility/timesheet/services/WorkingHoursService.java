package com.ness.mobility.timesheet.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.ness.mobility.timesheet.processors.WorkingHoursProcessor;
import com.ness.mobility.timesheet.processors.WorkingHoursProcessorCallback;

public class WorkingHoursService extends IntentService {

	 private static final String TAG = WorkingHoursService.class.getSimpleName();
	
    public static final String METHOD_EXTRA = "com.ness.mobility.timesheet.services.METHOD_EXTRA";
    
    public static final String PARAM_USRCODE = "com.ness.mobility.timesheet.services.USERCODE";
    
    public static final String PARAM_MONTHYEAR = "com.ness.mobility.timesheet.services.MONTHYEAR";

    public static final String METHOD_GET = "GET";

    public static final String RESOURCE_TYPE_EXTRA = "com.ness.mobility.timesheet.services.RESOURCE_TYPE_EXTRA";

    public static final int MONTHWORKINGDAYS_TYPE_PROFILE = 1;

    public static final int RESOURCE_TYPE_TIMELINE = 2;

    public static final String SERVICE_CALLBACK = "com.ness.mobility.timesheet.services.SERVICE_CALLBACK";

    public static final String ORIGINAL_INTENT_EXTRA = "com.ness.mobility.timesheet.services.ORIGINAL_INTENT_EXTRA";

    private static final int REQUEST_INVALID = -1;

    private ResultReceiver mCallback;

    private Intent mOriginalRequestIntent;

    public WorkingHoursService() {
            super("WorkingHoursService");
    }

    @Override
    protected void onHandleIntent(Intent requestIntent) {

    	 Log.d(TAG, "!!!!!####### ### onHandleIntent START");
            mOriginalRequestIntent = requestIntent;

            // Get request data from Intent
            String method = requestIntent.getStringExtra(WorkingHoursService.METHOD_EXTRA);
            int resourceType = requestIntent.getIntExtra(WorkingHoursService.RESOURCE_TYPE_EXTRA, -1);
            mCallback = requestIntent.getParcelableExtra(WorkingHoursService.SERVICE_CALLBACK);
            String monthyear = requestIntent.getStringExtra(WorkingHoursService.PARAM_MONTHYEAR);
            String userCode = requestIntent.getStringExtra(WorkingHoursService.PARAM_USRCODE);

            switch (resourceType) {
            case RESOURCE_TYPE_TIMELINE:

                    if (method.equalsIgnoreCase(METHOD_GET)) {
                            WorkingHoursProcessor processor = new WorkingHoursProcessor(getApplicationContext());
                            int resultCode = processor.getMonthWorkingHours( userCode, monthyear);
                             mCallback.send(resultCode, getOriginalIntentBundle());
                            Log.d(TAG, "!!!!!####### ### onHandleIntent END");
                    } else {
                            mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
                    }
                    break;

            
                    
            default:
                    mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
                    break;
            }

    }

    private WorkingHoursProcessorCallback makeWorkingHoursProcessorCallback() {
    	WorkingHoursProcessorCallback callback = new WorkingHoursProcessorCallback() {

                    @Override
                    public void send(int resultCode) {
                            if (mCallback != null) {
                                    mCallback.send(resultCode, getOriginalIntentBundle());
                            }
                    }
            };
            return callback;
    }

   

    protected Bundle getOriginalIntentBundle() {
            Bundle originalRequest = new Bundle();
            originalRequest.putParcelable(ORIGINAL_INTENT_EXTRA, mOriginalRequestIntent);
            return originalRequest;
    }

}
