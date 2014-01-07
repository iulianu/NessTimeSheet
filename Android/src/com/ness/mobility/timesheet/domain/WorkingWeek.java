package com.ness.mobility.timesheet.domain;


public class WorkingWeek {
    
    
    final WorkingDay[] workingdays;
    
    
    

    public WorkingWeek() {
        super();
        workingdays = new WorkingDay[7];
    }
    
    public void setDayOfWeek(int dw, WorkingDay wd) {
        if (dw > 7 || dw < 1) 
            return;//todo throw exp
        
        workingdays[dw - 1] = wd;
        }

    public WorkingDay[] getWorkingdays() {
        return workingdays;
    }

   
}
