package com.example.wanghui.kotlin.ui.scroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 2000);

        int a = 0;
        switch (a){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    public class demo extends Drawable{

        public demo(Paint paint) {
            draw(null);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {

        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }

}
