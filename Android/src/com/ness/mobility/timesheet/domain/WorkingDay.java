package com.ness.mobility.timesheet.domain;

import java.util.List;



public class WorkingDay {
    
   
    
    
    private int dayOfMonth;    
    private int status;    
    private List<WorkItem> workItems;
    
    public WorkingDay() {
        super();
    }

    public WorkingDay(int dayOfM, int sts) {
        this.dayOfMonth = dayOfM;
        this.status = sts;
    }
    public void setStatus(int status) {
        this.status = status;
        
    }

    public int getStatus() {
        return status;
    }

    public void setWorkItems(List<WorkItem> workItems) {
        this.workItems = workItems;
    }

    public List<WorkItem> getWorkItems() {
        return workItems;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }
}
