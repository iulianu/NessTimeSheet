package ness.mobile;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class WorkingWeek {
    
    
    final WorkingDay[] workingdays;
    
    
    

    public WorkingWeek() {
        super();
        workingdays = new WorkingDay[7];
    }
    
    public void setDayOfWeek(int dw, WorkingDay wd) {
        if (dw > 7 || dw < 1) 
            return;//todo thwor exp
        
        workingdays[dw - 1] = wd;
        }

    public WorkingDay[] getWorkingdays() {
        return workingdays;
    }

   
}
