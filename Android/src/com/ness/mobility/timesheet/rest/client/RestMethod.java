package com.ness.mobility.timesheet.rest.client;

import com.ness.mobility.timesheet.rest.resource.Resource;

public interface RestMethod<T extends Resource>{

    public RestMethodResult<T> execute();
}
