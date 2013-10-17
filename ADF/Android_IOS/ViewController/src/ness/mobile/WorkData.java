package ness.mobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import  ness.mobile.Constants.WDStatus;

import oracle.adfmf.dc.ws.rest.RestServiceAdapter;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.api.Model;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;

public class WorkData {
    
    private int month;
    private int year;
    private String textCurrDate;
    
    private Integer clipboardDay;
    private Integer clipboardWeek;
    private Integer newWeek;
    private  int firstDayOfMonthInWeek;
    
    private String selectedDay;
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    protected transient ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);
    
    public static Map months = new HashMap();
    static{
            months.put(new Integer(0), "Jan");
            months.put(new Integer(1), "Feb");
            months.put(new Integer(2), "Mar");
            months.put(new Integer(3), "May");
            months.put(new Integer(4), "Apr");
            months.put(new Integer(5), "Jun");
            months.put(new Integer(6), "Jul");
            months.put(new Integer(7), "Aug");
            months.put(new Integer(8), "Sept");
            months.put(new Integer(9), "Oct");
            months.put(new Integer(10), "Nov");
            months.put(new Integer(11), "Dec");

        }

    public WorkData() {
        super();
        Calendar cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setTextCurrDate(months.get(new Integer(month)) + ", " + year);
        clipboardDay = new Integer(-1);
        clipboardWeek = new Integer(-1);
        newWeek = new Integer(-1);

    }
    
    
    public WorkingWeek[] getCurrentMonthWD() {
        
            WorkingWeek[] wds = null;
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.DAY_OF_MONTH,1);
            firstDayOfMonthInWeek = cal.get(Calendar.DAY_OF_WEEK);
            double fDOfMonthInWeek = firstDayOfMonthInWeek;
            double noDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            double noWeeksInMonth = (noDaysInMonth - (7 - (fDOfMonthInWeek - 1)))/7 + 1;
            noWeeksInMonth =  Math.ceil(noWeeksInMonth);
            int idxDayOfMonth = 0;
            List wdsl = new ArrayList();
            for (int i = 0; i < noWeeksInMonth;i++) {
                WorkingWeek workW = new WorkingWeek();
                for (int j = 0; j < 7;j++) {
                    WorkingDay wd = new WorkingDay();
                    if (fDOfMonthInWeek > 1) {
                        wd.setDayOfMonth("-1");
                        fDOfMonthInWeek--;
                    }
                    else {
                        wd.setDayOfMonth(String.valueOf(++idxDayOfMonth));
                        if (i * j % 2 == 0)
                            wd.setStatus(WDStatus.COMPLETE_APPROVWED);
                        else 
                        if (i * j % 3 == 0)
                            wd.setStatus(WDStatus.COMPLETE_SAVED);
                        else
                        if (i * j % 5 == 0)
                            wd.setStatus(WDStatus.UNCOMPLETE);
                        else
                            wd.setStatus(WDStatus.UNCOMPLETE);
                        
                        if (j == 6 || j == 0) {
                                wd.setStatus(WDStatus.HOLIDAY);
                            }
                        
                    }
                   
                    workW.setDayOfWeek(j + 1, wd);
                    if (idxDayOfMonth > noDaysInMonth) {
                        wd.setDayOfMonth("-1");  
                    }
                    }  
                wdsl.add(workW);
            }
            wds = (WorkingWeek[])wdsl.toArray(new WorkingWeek[wdsl.size()]);
            return wds;
        }
    
    
    
    public void setSelDay(Integer d) {
       setSelectedDay(String.valueOf(d));
      /*   RestServiceAdapter restServ = Model.createRestServiceAdapter();
        restServ.clearRequestProperties();
        restServ.setConnectionName("timesheetConn");
        restServ.setRequestType(RestServiceAdapter.REQUEST_TYPE_GET);
        restServ.setRetryLimit(0);
        restServ.setRequestURI("/timesheet/workingDay?userCode=du&dayMonthYear=101013");
        String send = "nu merge";


        try {
            send = restServ.send("");            
        } catch (Exception e) {
            send = e.getMessage();
        }

        setSelectedDay(send);
*/
    }
    
    public void gotoPrevious() {
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(),
                                                                   "adf.mf.api.amx.doNavigation", new Object[] { "swipeLeft" });
        if (month == Calendar.JANUARY) {
            month = Calendar.DECEMBER;
            year--;
        }
        else
        {
            month--;
        }
        setTextCurrDate(months.get(new Integer(month)) + ", " + year);
    //    providerChangeSupport.fireProviderRefresh("currentMonthWD");
        
       
    }
    
    public void gotoNext() {
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(),
                                                                   "adf.mf.api.amx.doNavigation", new Object[] { "swipeRight" });
        if (month == Calendar.DECEMBER) {
            month = Calendar.JANUARY;
            year++;
        }
        else
        {
            month++;
        }
        setTextCurrDate(months.get(new Integer(month)) + ", " + year);
   //     providerChangeSupport.fireProviderRefresh("currentMonthWD");
       
        
    }
    
    public void copyDay() {
        setClipboardDay( new Integer(1));
        setClipboardWeek(Integer.valueOf("-1"));
            AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(), "unhighlightWeek",
                                                                             new Object[] {});
        }
    
    public void pasteDay() {
            setClipboardDay(new Integer(-1));
            setClipboardWeek(new Integer(-1));
            AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(), "unhighlightWeek",
                                                                            new Object[] {});
        }
    
    public void copyWeek() {
            setClipboardDay(new Integer(1));
            setClipboardWeek( new Integer(1));
            AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(), "unhighlightWeek",
                                                                             new Object[] {});
        }
    
    public void pasteWeek() {
            setClipboardDay(new Integer(-1));
            setClipboardWeek(new Integer(-1));
            AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(), "unhighlightWeek",
                                                                             new Object[] {});
        }
    
    public void callJSHilightRow(Integer z) {
        int lastDayOfWeek = 8 - firstDayOfMonthInWeek;
        int week = 1;
        
        for (int i = 1; i <= 6;i++) {
            if (z.intValue() <= lastDayOfWeek) {
                week = i;
                break;
            }
            lastDayOfWeek = lastDayOfWeek + 7;
        }
        setNewWeek(new Integer(week));
            AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(), "highlightWeek",
                                                                             new Object[] { "" + z + "" , "" + week + ""});
    }
    
    public void tapHold(Integer z) {
            callJSHilightRow(z);
        }
        
    
    
    public String[] getStrings() {
        
        return new String[3];
        
        }

    public void setMonth(int month) {
        int oldMonth = this.month;
        this.month = month;
        propertyChangeSupport.firePropertyChange("month", oldMonth, month);
        
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void addProviderChangeListener(ProviderChangeListener l) {   
        providerChangeSupport.addProviderChangeListener(l);  
      }  
      public void removeProviderChangeListener(ProviderChangeListener l) {   
        providerChangeSupport.removeProviderChangeListener(l);  
      }  

    public int getMonth() {
        return month;
    }

    public void setYear(int year) {
        int oldYear = this.year;
        this.year = year;
        propertyChangeSupport.firePropertyChange("year", oldYear, year);
    }

    public int getYear() {
        return year;
    }


    public void setTextCurrDate(String textCurrDate) {
        String oldTextCurrDate = this.textCurrDate;
        this.textCurrDate = textCurrDate;
        propertyChangeSupport.firePropertyChange("textCurrDate", oldTextCurrDate, textCurrDate);
    }

    public String getTextCurrDate() {
        return textCurrDate;
    }

    public void setSelectedDay(String selectedDay) {
        String oldSelectedDay = this.selectedDay;
        this.selectedDay = selectedDay;
        propertyChangeSupport.firePropertyChange("selectedDay", oldSelectedDay, selectedDay);
    }

    public String getSelectedDay() {
        return selectedDay;
    }

    public void setClipboardDay(Integer clipboardDay) {
        Integer oldClipboardDay = this.clipboardDay;
        this.clipboardDay = clipboardDay;
        propertyChangeSupport.firePropertyChange("clipboardDay", oldClipboardDay, clipboardDay);
    }

    public Integer getClipboardDay() {
        return clipboardDay;
    }

    public void setClipboardWeek(Integer clipboardWeek) {
        Integer oldClipboardWeek = this.clipboardWeek;
        this.clipboardWeek = clipboardWeek;
        propertyChangeSupport.firePropertyChange("clipboardWeek", oldClipboardWeek, clipboardWeek);
    }

    public Integer getClipboardWeek() {
        return clipboardWeek;
    }

    public void setNewWeek(Integer newWeek) {
        Integer oldNewWeek = this.newWeek;
        this.newWeek = newWeek;
        propertyChangeSupport.firePropertyChange("newWeek", oldNewWeek, newWeek);
    }

    public Integer getNewWeek() {
        return newWeek;
    }
}
