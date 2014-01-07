package com.ness.mobility.timesheet.rest.client;

import org.apache.http.Header;

public class Response {
    
    /**
     * The HTTP status code
     */
    private int status;
    
    /**
     * The HTTP headers received in the response
     */
    private  Header[] headers;
    
    /**
     * The response body, if any
     */
    private String body;
    
    /**
     * Error message
     */
    private String message;
    
    /**
     * 
     * @param status
     * @param headers
     * @param message
     * @param body
     */
    
    protected Response(int status,  Header[] headers, String message, String body) {
        this.status = status; 
        this.headers = headers;
        this.message = message;
        this.body = body;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Header[] getHeaders() {
		return headers;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
    
}