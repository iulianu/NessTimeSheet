package com.ness.mobility.timesheet.rest.client;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    
    private URI requestUri;
    private Map<String, List<String>> headers;
    private Map<String, String> params;
    private byte[] body;
    private RequestMethod method;
    
    public Request(RequestMethod method, URI requestUri, Map<String, List<String>> headers, Map<String, String> params,
                    byte[] body) {
            super();
            this.method = method;
            this.requestUri = requestUri;
            this.headers = headers;
            this.params = params;
            this.body = body;
    }
    
    public RequestMethod getMethod() {
            return method;
    }

    public URI getRequestUri() {
            return requestUri;
    }

    public Map<String, List<String>> getHeaders() {
            return headers;
    }

    public byte[] getBody() {
            return body;
    }

    public void addHeader(String key, List<String> value) {
            
            if (headers == null) {
                    headers = new HashMap<String, List<String>>();
            }
            headers.put(key, value);
    }
    
    public void addParam(String key, String value) {
        
        if (params == null) {
                params = new HashMap<String, String>();
        }
        params.put(key, value);
}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
    
}

