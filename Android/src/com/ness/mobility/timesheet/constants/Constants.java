package com.ness.mobility.timesheet.constants;

public interface Constants {
    interface WDStatus {
            int UNCOMPLETE = 0;
            int COMPLETE_SAVED = 1;
            int COMPLETE_APPROVWED = 2;
            int HOLIDAY = 3;
            int WEEKEND = 4;
            int OTHER_MONTH = 5;
        }
    
    public interface Status {
    	int PENDING = 0;
    	int DONE = 1;
   	}
    
    
    public interface Results {
    	int UNPROCESSED = -1;
    	int SUCCESS = 0;
    	int ERROR = 1;
   	}
}
