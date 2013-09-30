package ness.mobile;

import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class WorkingDay {
    
   
    
    
    private String dayOfMonth;    
    private int status;    
    private List workItems;
    
    public WorkingDay() {
        super();
    }

    public void setStatus(int status) {
        this.status = status;
        
    }

    public int getStatus() {
        return status;
    }

    public void setWorkItems(List workItems) {
        this.workItems = workItems;
    }

    public List getWorkItems() {
        return workItems;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }
}
