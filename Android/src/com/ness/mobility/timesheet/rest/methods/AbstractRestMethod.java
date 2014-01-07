package com.ness.mobility.timesheet.rest.methods;

import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.content.Context;

import com.ness.mobility.timesheet.rest.client.Request;
import com.ness.mobility.timesheet.rest.client.Response;
import com.ness.mobility.timesheet.rest.client.RestClient;
import com.ness.mobility.timesheet.rest.client.RestMethod;
import com.ness.mobility.timesheet.rest.client.RestMethodResult;
import com.ness.mobility.timesheet.rest.resource.Resource;

public abstract class AbstractRestMethod <T extends Resource> implements RestMethod<T> {

    private static final String DEFAULT_ENCODING = "UTF-8";

    public RestMethodResult<T> execute() {

            Request request = buildRequest();
            if (requiresAuthorization()) {
                 //   RequestSigner signer = AuthorizationManager.getInstance(getContext());
                 //   signer.authorize(request);
            }
            Response response = doRequest(request);
            return buildResult(response);
    }
    
    protected abstract Context getContext();

    /**
     * Subclasses can overwrite for full control, eg. need to do special
     * inspection of response headers, etc.
     * 
     * @param response
     * @return
     */
    protected RestMethodResult<T> buildResult(Response response) {

            int status = response.getStatus();
            String statusMsg = "";
            String responseBody = null;
            T resource = null;

            try {
                   // responseBody = new String(response.getBody(), getCharacterEncoding(response.getHeaders()));
                    resource = parseResponseBody(response.getBody());
            } catch (Exception ex) {
                    // TODO Should we set some custom status code?
                    status = 506; // spec only defines up to 505
                    statusMsg = ex.getMessage();
            }
            return new RestMethodResult<T>(status, statusMsg, resource);
    }

    protected abstract Request buildRequest();
    
    protected boolean requiresAuthorization() {
            return true;
    }

    protected abstract T parseResponseBody(String responseBody) throws Exception;

    private Response doRequest(Request request) {

            RestClient client = new RestClient();
            try {
				return client.execute(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return null;
    }

    private String getCharacterEncoding(Header[] headers) {
            // TODO get value from headers
            return DEFAULT_ENCODING;
    }
}
