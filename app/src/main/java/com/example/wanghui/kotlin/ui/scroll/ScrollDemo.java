package com.example.wanghui.kotlin.ui.scroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wanghui on 2017/7/17.
 */

public class ScrollDemo extends FrameLayout {

    public ScrollDemo(Context context) {
        this(context, null);
    }

    public ScrollDemo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollDemo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ScrollDemo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void computeScroll() {
        int a = 0;
        switch (a){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.toString();
            }
        });
    }

    int a = true? 0 : 1;




}
