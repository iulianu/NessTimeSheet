package com.ness.mobility.timesheet.datasource;

import com.ness.mobility.timesheet.datasource.provider.TimesheetProviderContract;

import android.net.Uri;

public class TimesheetConstants {
    
        
    
    public static final Uri CONTENT_URI_MONTHWKD = Uri.parse("content://" + TimesheetProviderContract.AUTHORITY + "/" + MonthWorkingDaysTable.TABLE_NAME);
    
    public static final Uri CONTENT_URI_MONTHTMS = Uri.parse("content://" + TimesheetProviderContract.AUTHORITY + "/" + MonthTimesheetTable.TABLE_NAME);

    public static final class MonthTimesheetTable implements ResourceTable {

        public static final String TABLE_NAME = "monthtimesheet";
       

        public static final String[] ALL_COLUMNS;
        public static final String[] DISPLAY_COLUMNS;

        static {
                ALL_COLUMNS = new String[] { 
                		MonthTimesheetTable._ID, 
                		MonthTimesheetTable._STATUS, 
                		MonthTimesheetTable._RESULT,                                         
                		MonthTimesheetTable.YEARMONTH                           
                };
                
                DISPLAY_COLUMNS = new String[] { 
                		MonthTimesheetTable._ID, 
                		MonthTimesheetTable.YEARMONTH
                };
        }
        
        
        /**
         * Column name for the year month day(YYMMDD)
         * <P>
         * Type: TEXT
         * </P>
         */
        public static final String YEARMONTH = "yearmonth";
       

        /**
         * Column name for the creation date
         * <P>
         * Type: LONG  (UNIX timestamp)
         * </P>
         */
        public static final String CREATED = "timestamp";

        
        // Prevent instantiation of this class
        private MonthTimesheetTable() {
        }

    }
 
    public static final class MonthWorkingDaysTable implements ResourceTable {

            public static final String TABLE_NAME = "monthworkingdays";
           

            public static final String[] ALL_COLUMNS;
            public static final String[] DISPLAY_COLUMNS;

            static {
                    ALL_COLUMNS = new String[] { 
                                    MonthWorkingDaysTable._ID, 
                                    MonthWorkingDaysTable._STATUS, 
                                    MonthWorkingDaysTable._RESULT,                                         
                                    MonthWorkingDaysTable.YEARMONTHDAY, 
                                    MonthWorkingDaysTable.STATUSDAY,
                                    MonthWorkingDaysTable.DAYOFMONTH
                    };
                    
                    DISPLAY_COLUMNS = new String[] { 
                    		MonthWorkingDaysTable._ID, 
                    		MonthWorkingDaysTable.YEARMONTHDAY, 
                    		MonthWorkingDaysTable.STATUSDAY,
                    		MonthWorkingDaysTable.DAYOFMONTH
                    };
            }
            
            
            /**
             * Column name for 
             * <P>
             * Type: INTEGER
             * </P>
             */
            public static final String DAYOFMONTH = "dayofmonth";
            
            /**
             * Column name for 
             * <P>
             * Type: INTEGER
             * </P>
             */
            public static final String YEARMONTHDAY = "monthyear";

            /**
             * Column name for tweet content
             * <P>
             * Type: TEXT
             * </P>
             */
            public static final String STATUSDAY = "statusday";

            /**
             * Column name for the creation date
             * <P>
             * Type: LONG  (UNIX timestamp)
             * </P>
             */
            public static final String CREATED = "timestamp";

            
            // Prevent instantiation of this class
            private MonthWorkingDaysTable() {
            }


    }
}
