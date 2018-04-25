package com.example.cyber.testone;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cyber on 25/04/2018.
 */

public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            Log.d("SelfAdaptingViewPager", "measuredHeight=" + h);
//            Log.d("SelfAdaptingViewPager", "getChildCount=" + getChildCount());
//            Log.d("SelfAdaptingViewPager", "getChildAt(i)=" + i);
//            if(h > height){
//                height = h;
//            }
//        }
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
//                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
