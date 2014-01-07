/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ness.mobility.timesheet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ness.mobility.timesheet.activity.base.RESTfulActivity;
import com.ness.mobility.timesheet.constants.Constants.WDStatus;
import com.ness.mobility.timesheet.datasource.TimesheetConstants;
import com.ness.mobility.timesheet.datasource.dao.TimesheetDAO;
import com.ness.mobility.timesheet.domain.WorkingDay;
import com.ness.mobility.timesheet.domain.WorkingMonth;
import com.ness.mobility.timesheet.services.WorkingHoursServiceHelper;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ScreenSlidePageFragment extends Fragment {
	
	 private static final String TAG = ScreenSlidePageFragment.class.getSimpleName();
	 
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    
    
    
    private GridView calendarView;
	private GridCellAdapter adapter;
	private Calendar _calendar;
	private int month, year;
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM yyyy";
	private Button currentMonth;
	
	private BroadcastReceiver requestReceiver;
	private Long requestId;
	private WorkingHoursServiceHelper mwhServiceHelper;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber); 
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        _calendar = Calendar.getInstance(Locale.getDefault());
        calendarView = (GridView) rootView.findViewById(R.id.calendar);
		
		
		_calendar.add(Calendar.MONTH, mPageNumber - 4);
		
		month = _calendar.get(Calendar.MONTH);
		year = _calendar.get(Calendar.YEAR);
		
		currentMonth = (Button) rootView.findViewById(R.id.currentMonth);
		currentMonth.setText(dateFormatter.format(dateTemplate, _calendar.getTime()));
        
		adapter = new GridCellAdapter(rootView.getContext(), R.id.calendar_day_gridcell, month, year);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	WorkingMonth wm = getWorkingMonthFromContentProvider();
    	if (wm != null) {
    		
            adapter.setWorkingMonth(wm);
            ((RESTfulActivity)getActivity()).setRefreshing(false);
    	}
    	else {
    		 IntentFilter filter = new IntentFilter(WorkingHoursServiceHelper.ACTION_REQUEST_RESULT);
             requestReceiver = new BroadcastReceiver() {

                     @Override
                     public void onReceive(Context context, Intent intent) {

                             long resultRequestId = intent
                                             .getLongExtra(WorkingHoursServiceHelper.EXTRA_REQUEST_ID, 0);

                             Log.d(TAG, "Received intent " + intent.getAction() + ", request ID "
                                             + resultRequestId);

                             if (resultRequestId == requestId) {

                                     Log.d(TAG, "Result is for our request ID");
                                     
                                     

                                     int resultCode = intent.getIntExtra(WorkingHoursServiceHelper.EXTRA_RESULT_CODE, 0);

                                     Log.d(TAG, "Result code = " + resultCode);

                                     if (resultCode == 200) {

                                             Log.d(TAG, "Updating UI with new data");

                                             WorkingMonth wd = getWorkingMonthFromContentProvider();
                                             adapter.setWorkingMonth(wd);

                                     } else {
                                    	 showMessage("Can not reach server! Please try later.");
                                     }
                                     ((RESTfulActivity)getActivity()).setRefreshing(false);
                             } else {
                                     Log.d(TAG, "Result is NOT for our request ID");
                             }

                     }
             };

             mwhServiceHelper = WorkingHoursServiceHelper.getInstance(getActivity().getApplicationContext());
             getActivity().registerReceiver(requestReceiver, filter);

             if (requestId == null) {
            	 	((RESTfulActivity)getActivity()).setRefreshing(true);
                     requestId = mwhServiceHelper.getMonthWorkingHours("P3700322", "" + (month) + "" +year);
             } else if (mwhServiceHelper.isRequestPending(requestId)) {
            	 ((RESTfulActivity)getActivity()).setRefreshing(true);
             } else {
            	 ((RESTfulActivity)getActivity()).setRefreshing(false);
            	 WorkingMonth wd = getWorkingMonthFromContentProvider();
                 adapter.setWorkingMonth(wd);
             }
    	}
    	
    }
    
    @Override
    public void onPause() {
            super.onPause();

            // Unregister for broadcast
            if (requestReceiver != null) {
                    try {
                    	getActivity().unregisterReceiver(requestReceiver);
                    } catch (IllegalArgumentException e) {
                            Log.e(TAG, e.getLocalizedMessage(), e);
                    }
            }
    }
    
    
    private WorkingMonth getWorkingMonthFromContentProvider() {

    	WorkingMonth wm = null;

        Cursor cursor = getActivity().getContentResolver().query(TimesheetConstants.CONTENT_URI_MONTHTMS, null, null, null, null);

        String monthDay = "" + (month) + "" +year;
        if (TimesheetDAO.isMonthDataFetched(getActivity().getApplicationContext(), monthDay)) {
        	wm = TimesheetDAO.getMonthDays(getActivity().getApplicationContext(), monthDay);
        }

        cursor.close();

        return wm;
}
    
   

    		private void showMessage(String message) {
        
                Toast toast = Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
      
}
    
 // Inner Class
 		public class GridCellAdapter extends BaseAdapter implements OnClickListener
 			{
 				private static final String tag = "GridCellAdapter";
 				private final Context _context;

 				private  List<WorkingDay> list;
 				private static final int DAY_OFFSET = 1;
 				private final String[] weekdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
 				
 				
 				private final int month, year;
 				private int daysInMonth, prevMonthDays;
 				private int currentDayOfMonth;
 				private int currentMonth;
 				private int currentWeekDay;
 				
 				private TextView num_events_per_day;
 				private final HashMap eventsPerMonthMap;
 				private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
 				private WorkingMonth workingMonth;
 				final Animation animZoomin;
 				final Animation animZoomout;
 				
 				


 				// Days in Current Month
 				public GridCellAdapter(Context context, int textViewResourceId, int month, int year)
 					{
 						super();
 						this._context = context;
 						this.list = new ArrayList<WorkingDay>();
 						this.month = month;
 						this.year = year;

 						animZoomin = AnimationUtils.loadAnimation(this._context, R.anim.anim_scale_zoomin);
 						animZoomout = AnimationUtils.loadAnimation(this._context, R.anim.anim_scale_zoomout);
 						Log.d(tag, "==> Passed in Date FOR Month: " + month + " " + "Year: " + year);
 						Calendar calendar = Calendar.getInstance();
 						setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
 						setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
 						Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
 						Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
 						Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

 						// Print Month
 						printMonth(month, year);

 						// Find Number of Events
 						eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
 					}
 				

 				private String getWeekDayAsString(int i)
 					{
 						return weekdays[i];
 					}

 				

 				public WorkingDay getItem(int position)
 					{
 						return list.get(position);
 					}

 				@Override
 				public int getCount()
 					{
 						return list.size();
 					}

 				
 				public void setWorkingMonth(WorkingMonth wm) {
 					workingMonth = wm;
 					this.notifyDataSetChanged();
 				}
 				
 				
 				@Override
 				public void notifyDataSetChanged() {
 					if (getCount() > 0)
 						printMonth(month, year);
 					super.notifyDataSetChanged();
 				}
 				
 				/**
 				 * Prints Month
 				 * 
 				 * @param mm
 				 * @param yy
 				 */
 				private void printMonth(int mm, int yy)
 					{
 						list = new ArrayList<WorkingDay>();
 						Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
 						// The number of days to leave blank at
 						// the start of this month.
 						int trailingSpaces = 0;
 						int leadSpaces = 0;
 						int daysInPrevMonth = 0;
 						

 						int currentMonth = mm;
 						
 						GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
 						
 						
 						

 						

 						// Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
 						
 						Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

 						cal.add(Calendar.MONTH, -1);
 						if (currentMonth == 11)
 							{
 							
 								daysInPrevMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
 							
 							}
 						else if (currentMonth == 0)
 							{
 								
 								daysInPrevMonth = 31; 								
 							}
 						else
 							{
 								
 								daysInPrevMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 								
 							}

 						cal.add(Calendar.MONTH, 1);
 						// Compute how much to leave before before the first day of the
 						// month.
 						// getDay() returns 0 for Sunday.
 						daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
 						int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
 						trailingSpaces = currentWeekDay;

 						Log.d(tag, "Week Day:" + currentWeekDay + " is " + getWeekDayAsString(currentWeekDay));
 						Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
 						Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

 						if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1)
 							{
 								++daysInMonth;
 							}
 						
 						//header Days
 						for (int i = 0; i < 7;i++) {
 							list.add(null);
 						}

 						// Trailing Month days
 						for (int i = 0; i < trailingSpaces; i++)
 							{
 								WorkingDay wd = new WorkingDay();
 								wd.setDayOfMonth(daysInPrevMonth - (trailingSpaces-i) + DAY_OFFSET);
 								wd.setStatus(WDStatus.OTHER_MONTH);
 								list.add(wd);
 							}

 						// Current Month Days
 						if (workingMonth != null && workingMonth.getWorkingDays() != null)
 						{
 						HashMap<Integer, WorkingDay> wmonth = workingMonth.getWorkingDays();
 						for (int i = 1; i <= daysInMonth; i++)
 							{
 							//	Log.d(currentMonthName, String.valueOf(i) + " " + getMonthAsString(currentMonth) + " " + yy);
 								
								WorkingDay wd = wmonth.get(i);
								int curDaysNoAdded = list.size();
								if ((curDaysNoAdded) % 7 == 0 || curDaysNoAdded % 7   == 6)
 									wd.setStatus(WDStatus.WEEKEND);								
 								list.add(wd);
 															
 							}
 						}
 						else
 						{
 							for (int i = 1; i <= daysInMonth; i++)
 							{
 							//	Log.d(currentMonthName, String.valueOf(i) + " " + getMonthAsString(currentMonth) + " " + yy);
 								
								WorkingDay wd = new WorkingDay();
 								wd.setDayOfMonth(i); 								
 								list.add(wd);
 								int curDaysNoAdded = list.size();
 								if ((curDaysNoAdded) % 7 == 0 || curDaysNoAdded % 7   == 1)
 									wd.setStatus(WDStatus.WEEKEND);
 								else
 									wd.setStatus(WDStatus.UNCOMPLETE); 								
 							}
 						}
 						// Leading Month days
 						for (int i = 0; i < list.size() % 7; i++)
 							{
 								
 								WorkingDay wd = new WorkingDay();
 								wd.setDayOfMonth(i + 1);
 								wd.setStatus(WDStatus.OTHER_MONTH);
 								list.add(wd);
 							}
 					}

 				/**
 				 * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
 				 * ALL entries from a SQLite database for that month. Iterate over the
 				 * List of All entries, and get the dateCreated, which is converted into
 				 * day.
 				 * 
 				 * @param year
 				 * @param month
 				 * @return
 				 */
 				private HashMap findNumberOfEventsPerMonth(int year, int month)
 					{
 						HashMap map = new HashMap<String, Integer>();
 						// DateFormat dateFormatter2 = new DateFormat();
 						//						
 						// String day = dateFormatter2.format("dd", dateCreated).toString();
 						//
 						// if (map.containsKey(day))
 						// {
 						// Integer val = (Integer) map.get(day) + 1;
 						// map.put(day, val);
 						// }
 						// else
 						// {
 						// map.put(day, 1);
 						// }
 						return map;
 					}

 				@Override
 				public long getItemId(int position)
 					{
 						return position;
 					}

 				@Override
 				public View getView(int position, View convertView, ViewGroup parent)
 					{
 					
 					View row = convertView;
 					if (position < 7) {
 						if (row == null)
						{
							LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							row = inflater.inflate(R.layout.calendar_day_gridcell_header, parent, false);
						}
 						
 						TextView gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell_header);
 						
 						gridcell.setText(weekdays[position]);
 						
 					}
 					else {
 						
 						if (row == null)
 							{
 								LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 								row = inflater.inflate(R.layout.calendar_day_gridcell, parent, false);
 							}

 						// Get a reference to the Day gridcell
 						Button gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
 						

 						// ACCOUNT FOR SPACING

 				//		Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
 						WorkingDay day_color = list.get(position);  
 						
 						if (day_color == null)
 							return row;
 						int theday = day_color.getDayOfMonth();
 						//String themonth = day_color[2];
 						//String theyear = day_color[3];
 						if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null))
 							{
 								if (eventsPerMonthMap.containsKey(theday))
 									{
 										num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
 										Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
 										num_events_per_day.setText(numEvents.toString());
 									}
 							}

 						// Set the Day GridCell
 						gridcell.setText(String.valueOf(day_color.getDayOfMonth()));
 						gridcell.setTag(theday );
 				//		Log.d(tag, "Setting GridCell " + theday + "-" );
 						
 						gridcell.setBackgroundColor(Color.rgb(235, 235, 235));
 						gridcell.setTextColor(Color.DKGRAY);
 						boolean enabled = true;
 						if (day_color.getStatus() == WDStatus.UNCOMPLETE)
 							{
 							gridcell.setBackgroundColor(Color.rgb(190, 216, 230));
 							}
 						if (day_color.getStatus() == WDStatus.COMPLETE_APPROVWED)
							{
 							gridcell.setBackgroundColor(Color.rgb(168, 179, 230));
							}
 						if (day_color.getStatus() == WDStatus.COMPLETE_SAVED)
						{
 							gridcell.setBackgroundColor(Color.rgb(169, 180, 170));
						}
 						if (day_color.getStatus() == WDStatus.HOLIDAY)
						{
 							gridcell.setBackgroundColor(Color.rgb(250, 249, 250));
						}
 						if (day_color.getStatus() == WDStatus.OTHER_MONTH)
 						{
 							gridcell.setTextColor(Color.rgb(249, 249, 249));
 							gridcell.setBackgroundColor(Color.rgb(249, 249, 249));
 							gridcell.setEnabled(false);
 							enabled = false;
 						}
 						if (day_color.getStatus() == WDStatus.WEEKEND)
						{
							gridcell.setTextColor(Color.LTGRAY);
							gridcell.setBackgroundColor(Color.rgb(249, 249, 249));
							gridcell.setEnabled(false);
							enabled = false;
						}
 						
 						if (enabled) {
 							gridcell.setOnClickListener(new View.OnClickListener() {
 							    public void onClick(View v) {
 							    	Log.d(tag, "!!!! ########Short Click: " +  v.getTag());
 							    	
 							    }
 							});
 							
 							gridcell.setOnLongClickListener(new View.OnLongClickListener() {
								
								@Override
								public boolean onLongClick(View v) {
									Log.d(tag, "!!!! ########Long Click: " +  v.getTag());
									return false;
								}
							});
 							gridcell.setOnTouchListener(new View.OnTouchListener() {
								
								@Override
								public boolean onTouch(View v, MotionEvent event) {
									if(event.getAction() == MotionEvent.ACTION_DOWN) {
									//	RelativeLayout rl = ((RelativeLayout)v.getParent());
										v.bringToFront();
										v.startAnimation(animZoomout);
										
									//	rl.addView(v);
									}
									if(event.getAction() == MotionEvent.ACTION_UP) {
										v.startAnimation(animZoomin);
										v.invalidate();
									}
									return false;
								}
							});
 						}
 					}
 					return row;
 				}
 				
 				@Override
 				public void onClick(View view)
 					{
 						String date_month_year = (String) view.getTag();
 					//	selectedDayMonthYearButton.setText("Selected: " + date_month_year);

 						try
 							{
 								Date parsedDate = dateFormatter.parse(date_month_year);
 								Log.d(tag, "Parsed Date: " + parsedDate.toString());

 							}
 						catch (ParseException e)
 							{
 								e.printStackTrace();
 							}
 					}

 				public int getCurrentDayOfMonth()
 					{
 						return currentDayOfMonth;
 					}

 				private void setCurrentDayOfMonth(int currentDayOfMonth)
 					{
 						this.currentDayOfMonth = currentDayOfMonth;
 					}
 				public void setCurrentWeekDay(int currentWeekDay)
 					{
 						this.currentWeekDay = currentWeekDay;
 					}
 				public int getCurrentWeekDay()
 					{
 						return currentWeekDay;
 					}
 				
 			}
    
    
}
