package com.ness.startup;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.ness.restservices.OrdersService;
import com.ness.restservices.TimeSheetService;

public class StartUpScanResource extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        // register resources
        classes.add(OrdersService.class);
        classes.add(TimeSheetService.class);
        return classes;
    }
}
