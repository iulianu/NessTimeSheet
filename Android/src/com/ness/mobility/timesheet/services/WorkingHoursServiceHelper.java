package com.ness.mobility.timesheet.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class WorkingHoursServiceHelper {

    public static String ACTION_REQUEST_RESULT = "REQUEST_RESULT";
    public static String EXTRA_REQUEST_ID = "EXTRA_REQUEST_ID";
    public static String EXTRA_RESULT_CODE = "EXTRA_RESULT_CODE";

    private static final String REQUEST_ID = "REQUEST_ID";
    
    private static final String timelineHashkey = "TIMELINE";

    private static Object lock = new Object();

    private static WorkingHoursServiceHelper instance;

    //TODO: refactor the key
    private Map<String,Long> pendingRequests = new HashMap<String,Long>();
    private Context ctx;

    private WorkingHoursServiceHelper(Context ctx){
            this.ctx = ctx.getApplicationContext();
    }

    public static WorkingHoursServiceHelper getInstance(Context ctx){
            synchronized (lock) {
                    if(instance == null){
                            instance = new WorkingHoursServiceHelper(ctx);                        
                    }
            }

            return instance;                
    }

    public long getMonthWorkingHours(String usercode, String monthYear) {

            long requestId = generateRequestID();
            pendingRequests.put(timelineHashkey, requestId);

            ResultReceiver serviceCallback = new ResultReceiver(null){
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                    	handleGetMonthWorkingHoursResponse(resultCode, resultData);
                    }
            };

            Intent intent = new Intent(this.ctx, WorkingHoursService.class);
            intent.putExtra(WorkingHoursService.METHOD_EXTRA, WorkingHoursService.METHOD_GET);
            intent.putExtra(WorkingHoursService.RESOURCE_TYPE_EXTRA, WorkingHoursService.RESOURCE_TYPE_TIMELINE);
            intent.putExtra(WorkingHoursService.SERVICE_CALLBACK, serviceCallback);
            intent.putExtra(WorkingHoursService.PARAM_USRCODE, usercode);
            intent.putExtra(WorkingHoursService.PARAM_MONTHYEAR, monthYear);
            intent.putExtra(REQUEST_ID, requestId);

            this.ctx.startService(intent);
            
            return requestId;                
    }
    
    

    private long generateRequestID() {
            long requestId = UUID.randomUUID().getLeastSignificantBits();
            return requestId;
    }

    public boolean isRequestPending(long requestId){
            return this.pendingRequests.containsValue(requestId);
    }

    

    private void handleGetMonthWorkingHoursResponse(int resultCode, Bundle resultData){

            Intent origIntent = (Intent)resultData.getParcelable(WorkingHoursService.ORIGINAL_INTENT_EXTRA);

            if(origIntent != null){
                    long requestId = origIntent.getLongExtra(REQUEST_ID, 0);

                    pendingRequests.remove(timelineHashkey);

                    Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
                    resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
                    resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

                    ctx.sendBroadcast(resultBroadcast);
            }
    }

}
