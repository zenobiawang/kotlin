package com.example.wanghui.kotlin.ui.view.flowlayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.OverScroller
import com.example.wanghui.kotlin.R
import org.jetbrains.anko.padding

/**
 * Created by wanghui on 2017/11/29.
 */
class FlowLayoutNew(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ViewGroup(context, attrs, defStyleAttr) {
    private val TAG = "FlowLayoutNew"
    private var itemDivideHorizontalAttr = 0
    private var itemDivideVerticalAttr = 0
    private var divideVertical = 0
    private var divideHorizontal = 0
    private var columns: Int = 2
    private var columnHeight = ArrayList<Int>()
    private var childWidth = 0

    private val scroller : OverScroller  //可以滑动查看
    private var touchSlop = 0
    private var currentX = 0f
    private var currentY = 0f
    private var rightBorder = 0     //滑动边界
    private var bottomBorder = 0
    private var leftBorder = 0
    private var topBorder = 0
    private var minVelocity = 0
    private var maxVelocity = 0
    private var velocityTracker : VelocityTracker? = null

    constructor(context: Context):this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0)


    init {
        context.obtainStyledAttributes(attrs, R.styleable.FlowLayout).apply {
            val itemPadding = getDimension(R.styleable.FlowLayout_item_padding, 0f)
            itemDivideHorizontalAttr = getDimension(R.styleable.FlowLayout_item_divide_horizontal, itemPadding).toInt()
            itemDivideVerticalAttr = getDimension(R.styleable.FlowLayout_item_divide_vertical, itemPadding).toInt()
            columns = getInt(R.styleable.FlowLayout_columns, 2)
        }.recycle()

        scroller = OverScroller(context)
        ViewConfiguration.get(context).run {
            touchSlop = scaledPagingTouchSlop
            minVelocity = scaledMinimumFlingVelocity
            maxVelocity = scaledMaximumFlingVelocity
        }
        isClickable = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {  //todo measure wrap_content
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        childWidth = (widthSize - divideHorizontal*(columns-1))/columns
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, widthMode), heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        resetColumnsHeight(t)
        topBorder = t
        leftBorder = l
        for (i in 0 until childCount){
            val child = getChildAt(i)
            val top = getChildTop()
            val maxIndex = columnHeight.indexOf(top)
            val left = l + paddingLeft + childWidth*maxIndex + itemDivideHorizontalAttr*maxIndex
            val bottom = top + child.measuredHeight
            val right = paddingLeft + childWidth*(maxIndex + 1) + itemDivideHorizontalAttr*maxIndex
            columnHeight[maxIndex] = bottom + itemDivideVerticalAttr
            child.layout(left, top, right, bottom)

            bottomBorder = Math.max(bottom, bottomBorder)
        }

        resetColumnsHeight(t)
    }

    private fun resetColumnsHeight(t: Int){
        columnHeight.clear()
        (0 until columns).forEach({
            columnHeight.add(paddingTop + t)
        })
    }

    /**
     * 获取view的顶部
     */
    private fun getChildTop(): Int {
        var top = Int.MAX_VALUE
        columnHeight.forEach({
            if (it < top){
                top = it
            }
        })
        return top
    }


    /**
     * 实现连贯滑动
     */
    override fun computeScroll() {
        if (scroller.computeScrollOffset()){
            Log.d(TAG, "debug---computeScroll--${scroller.currY}")
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when(ev.action){
            MotionEvent.ACTION_DOWN ->{
                currentX = ev.rawX
                currentY = ev.rawY
            }
            MotionEvent.ACTION_MOVE ->{
                currentY = ev.rawY
                return true
            }
            MotionEvent.ACTION_UP ->{
                return false
            }
            MotionEvent.ACTION_CANCEL ->{
                return false
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                initVelocityTracker(event)
                currentY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {

                if (scrollY + (currentY - event.rawY) < topBorder){
                    scrollTo(0, topBorder)
                    return true
                }else if(scrollY + measuredHeight + currentY - event.rawY > bottomBorder){
                    scrollTo(0, bottomBorder - measuredHeight)
                    return true
                }
                scrollBy(0, (currentY - event.rawY).toInt())
                currentY = event.rawY
                velocityTracker?.addMovement(event)
            }
            MotionEvent.ACTION_UP ->{  //todo fling
                if (velocityTracker != null){
                    velocityTracker?.computeCurrentVelocity(1000, maxVelocity.toFloat())
                    if (Math.abs(velocityTracker!!.yVelocity) > minVelocity){
                        scroller.fling(scrollX, scrollY, 0, -velocityTracker!!.yVelocity.toInt(),
                                leftBorder, rightBorder, topBorder, bottomBorder - measuredHeight, 0, 50)
                        postInvalidate()
                    }
                    releaseVelocityTracker()
                }
            }
            MotionEvent.ACTION_CANCEL ->{
                releaseVelocityTracker()
            }

        }
        return super.onTouchEvent(event)
    }

    private fun initVelocityTracker(event: MotionEvent){
        if (velocityTracker == null){
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker!!.clear()
        velocityTracker!!.addMovement(event)
    }

    private fun releaseVelocityTracker(){
        if (velocityTracker != null){
            velocityTracker!!.recycle()
            velocityTracker = null
        }
    }
}