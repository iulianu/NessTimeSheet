package com.ness.mobility.timesheet.datasource.provider;

import android.net.Uri;

import com.ness.mobility.timesheet.datasource.TimesheetConstants.MonthTimesheetTable;
import com.ness.mobility.timesheet.datasource.TimesheetConstants.MonthWorkingDaysTable;

public class TimesheetProviderContract {
    
    

    public static final String AUTHORITY = "com.ness.mobility.timesheet.datasource.provider";

    // TIMELINE TABLE CONTRACT
    public static final class MonthWorkingDaysPrvCtrct  {
            
            // URI DEFS
            static final String SCHEME = "content://";
            public static final String URI_PREFIX = SCHEME + AUTHORITY;
            private static final String URI_PATH_MONTHWORKINGDAYS = "/" + MonthWorkingDaysTable.TABLE_NAME;
           
            private static final String URI_PATH_INSERTWORKINGDAY = "/" + MonthWorkingDaysTable.TABLE_NAME + "/";
            public static final int TWEET_ID_PATH_POSITION = 1;

            
            public static final Uri CONTENT_URI = Uri.parse(URI_PREFIX + URI_PATH_MONTHWORKINGDAYS);
            //  -- used for content provider insert() call
            public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + URI_PATH_INSERTWORKINGDAY);
            
            public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY + URI_PATH_INSERTWORKINGDAY + "#");

          
    }
    
    public static final class MonthTimesheetPrvCtrct  {
        
        // URI DEFS
        static final String SCHEME = "content://";
        public static final String URI_PREFIX = SCHEME + AUTHORITY;
        private static final String URI_PATH_MONTHTMSH = "/" + MonthTimesheetTable.TABLE_NAME;
       
        private static final String URI_PATH_INSERTMONTHTMSH = "/" + MonthTimesheetTable.TABLE_NAME + "/";
        public static final int TWEET_ID_PATH_POSITION = 1;

        
        public static final Uri CONTENT_URI = Uri.parse(URI_PREFIX + URI_PATH_MONTHTMSH);
        //  -- used for content provider insert() call
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + URI_PATH_INSERTMONTHTMSH);
        
        public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY + URI_PATH_INSERTMONTHTMSH + "#");

      
}

    private TimesheetProviderContract() {
            // disallow instantiation
    }

	
}
