package com.example.wanghui.kotlin.ui.scroll

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Scroller

/**
 * Created by wanghui on 2017/7/20.
 * 刷新控件
 */
abstract class PullToRefreshLayout<T : View> : ViewGroup{
    val TAG : String = "PullToRefreshLayout"
    var headerView : View? = null
    var footerView : View? = null
    var contentView : T? = null
    var scroller : Scroller? = null
    var lastY : Int = 0
    var maxHeightForHeaderAndFooter : Int = 0  //可滑动的最大头部和尾部
    var minHeightForHeaderAndFooter : Int = 0

    constructor(context: Context?) : super(context){init(context)}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){init(context)}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(context)}

    private fun init(context: Context?) {
        initHeaderView(context)
        initFooterView(context)
        initContentView(context)
        scroller = Scroller(context)
        val windowManager : WindowManager = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        maxHeightForHeaderAndFooter = windowManager.defaultDisplay.height/4
        maxHeightForHeaderAndFooter = windowManager.defaultDisplay.height/8
    }

    private fun initContentView(context: Context?) {
        contentView = loadContentView(context)
        var contentLayoutParams : LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(contentView, 1, contentLayoutParams)
    }

    private fun initFooterView(context: Context?) {
        footerView = loadFooterView(context)  //todo 是否设置尾部的高度限制
        addView(footerView, 1)
    }

    private fun initHeaderView(context: Context?) {
        headerView = loadHeaderView(context)  //todo 是否设置头部的高度限制
        addView(headerView)
    }

    abstract fun loadHeaderView(context: Context?): View
    abstract fun loadFooterView(context: Context?): View
    abstract fun loadContentView(context: Context?): T

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {  //todo 这种实现方式是否合理
        headerView!!.layout(0, -headerView!!.measuredHeight, headerView!!.measuredWidth, 0)
        contentView!!.layout(0, 0, contentView!!.measuredWidth, contentView!!.measuredHeight)
        footerView!!.layout(0, contentView!!.measuredHeight, footerView!!.measuredWidth, contentView!!.measuredHeight + footerView!!.measuredHeight)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (i in 0..childCount-1) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun computeScroll() {
        if (scroller!!.computeScrollOffset()){
            Log.d(TAG, "wh----" + scroller!!.currY)
            scrollTo(0, scroller!!.currY)
            postInvalidate()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                lastY = ev!!.rawY.toInt()
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                var offset = ev!!.rawY - lastY
//                if (){
//                    return true
//                }
            }
            MotionEvent.ACTION_UP -> {
                return false
            }
            MotionEvent.ACTION_CANCEL -> {
                return false
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_MOVE ->{
                var currentY = event!!.rawY
                var offset = currentY - lastY
                lastY = currentY.toInt()
                scrollBy(0, - offset.toInt())
            }
            MotionEvent.ACTION_UP ->{
                //todo 刷新操作
            }
        }
        return true
    }

}
