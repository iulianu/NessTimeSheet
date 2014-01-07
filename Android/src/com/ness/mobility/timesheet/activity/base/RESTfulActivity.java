package com.ness.mobility.timesheet.activity.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ness.mobility.timesheet.R;

public abstract class RESTfulActivity extends FragmentActivity {

    protected Button mRefreshButton;
    protected ProgressBar mRefreshProgressIndicator;
    private int mContentResId;
    protected boolean mRefreshable = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    		
            super.onCreate(savedInstanceState);

           

            
    }

    protected abstract void refresh();

    protected void setContentResId(int id) {
            mContentResId = id;
    }

    protected void setRefreshable(boolean refreshable) {
            mRefreshable = refreshable;
    }

    public void setRefreshing(boolean refreshing) {

            mRefreshProgressIndicator.setVisibility(refreshing ? View.VISIBLE
                            : View.INVISIBLE);
            mRefreshButton
                            .setVisibility(refreshing ? View.INVISIBLE : View.VISIBLE);

    }

}
