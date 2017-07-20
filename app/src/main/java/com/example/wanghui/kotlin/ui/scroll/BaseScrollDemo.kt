package com.example.wanghui.kotlin.ui.scroll

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Scroller

/**
 * Created by wanghui on 2017/7/17.
 */
class BaseScrollDemo : FrameLayout {
    var scroller : Scroller? = null

    constructor(context: Context) : super(context) {init(context)}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {init(context)}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {init(context)}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {init(context)}


    fun init(context: Context){
        scroller = Scroller(context)
    }

    override fun computeScroll() {
        if (scroller!!.computeScrollOffset()) {
            scrollTo(scroller!!.currX, scroller!!.currY)
            postInvalidate()
        }
    }

    fun scrollTo(y : Int){
        scroller!!.startScroll(scrollX, scrollY, 0, y)
        invalidate()
    }

}