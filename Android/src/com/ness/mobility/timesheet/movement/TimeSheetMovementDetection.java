package com.ness.mobility.timesheet.movement;

import com.ness.mobility.timesheet.constants.GlobalVariables;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class TimeSheetMovementDetection extends SimpleOnGestureListener
{

	SwipeSupport swipeClient;
	
	public TimeSheetMovementDetection(SwipeSupport swCl) {
		swipeClient = swCl;
	}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

    	if (e1 == null || e2 == null)
    		return false;

        if (Math.abs(e1.getY() - e2.getY()) > GlobalVariables.SWIPE_MAX_OFF_PATH) {
            return false;
        }

        // right to left swipe
        if(e1.getX() - e2.getX() > GlobalVariables.SWIPE_MIN_DISTANCE && Math.abs(velocityX) > GlobalVariables.SWIPE_THRESHOLD_VELOCITY) {

        	if (swipeClient != null) {
        		swipeClient.moveLeft();
        	}

            //  left to right  swipe
        }  else if (e2.getX() - e1.getX() > GlobalVariables.SWIPE_MIN_DISTANCE && Math.abs(velocityX) > GlobalVariables.SWIPE_THRESHOLD_VELOCITY) {

        	if (swipeClient != null) {
        		swipeClient.moveRight();
        	}

        }

        return false;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }



}



