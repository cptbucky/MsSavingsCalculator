package com.avantics.savingscalc.common;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by tom on 02/06/13.
 */
public class ViewPagerCustom extends ViewPager {

    public ViewPagerCustom(Context context) {
        super(context);
    }

    public ViewPagerCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//        if (v instanceof ViewPagerCustom) {
//            // the vertCollectionPagerAdapter has logic on getItem that determines if the next fragment should have swipe disabled
//            vertCollectionPagerAdapter a = (vertCollectionPagerAdapter) ((ViewPagerCustom) v).getAdapter();
//
//
//            return true;
//        }
//
//        return super.canScroll(v, checkV, dx, x, y);
//    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        int eventAction = event.getAction();
//
////        switch (eventaction) {
////            case MotionEvent.ACTION_DOWN:
////                // finger touches the screen
////                break;
////            case MotionEvent.ACTION_MOVE:
////                // finger moves on the screen
////                break;
////            case MotionEvent.ACTION_UP:
////                // finger leaves the screen
////                break;
////        }
//
//        switch (eventAction) {
//            case MotionEvent.ACTION_MOVE:
//                return false;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_DOWN:
//                return false;
//        }
//
////        boolean rtrn = super.onInterceptTouchEvent(event);
//
//        return false;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                return true;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_DOWN:
//                return false;
//        }

        return false;
    }
}
