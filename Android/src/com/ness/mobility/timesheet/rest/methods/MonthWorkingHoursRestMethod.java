package com.ness.mobility.timesheet.rest.methods;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ness.mobility.timesheet.rest.client.Request;
import com.ness.mobility.timesheet.rest.client.RequestMethod;
import com.ness.mobility.timesheet.rest.resource.WorkingMonthRest;

public class MonthWorkingHoursRestMethod extends AbstractRestMethod<WorkingMonthRest>{
    
    private Context mContext;
    
    private static final URI M_URI = URI.create("http://172.30.146.144:9081/timesheet-server/timesheet/workingMonth");
    
    private Map<String, List<String>> headers;
    private Map<String, String> params;
    
    public MonthWorkingHoursRestMethod(Context context, Map<String, List<String>> headers, Map<String, String> params){
            mContext = context.getApplicationContext();
            this.headers = headers;
            this.params = params;
    }

    @Override
    protected Request buildRequest() {
            
            Request request = new Request(RequestMethod.GET, M_URI, headers, params, null);
            return request;
    }

    @Override
    protected WorkingMonthRest parseResponseBody(String responseBody) throws Exception {
            
            JSONObject json = new JSONObject(responseBody);
            return new WorkingMonthRest(json);
            
    }

    @Override
    protected Context getContext() {
            return mContext;
    }


}
