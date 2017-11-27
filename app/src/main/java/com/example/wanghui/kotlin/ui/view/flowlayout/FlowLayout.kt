package com.example.wanghui.kotlin.ui.view.flowlayout

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.Scroller
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/8/14.
 * 流布局
 * 从左往右依次摆放、从上往下依次摆放
 * 自定义间距、自适应间距
 * 滚动查看
 */
class FlowLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private val TAG = "FlowLayout"
    val VERTICAL = -1     //从上往下布局
    val HORIZONTAL = -2   //从左往右布局
    var orientation  = 0
    private var itemDivideHorizontalAttr = 0
    private var itemDivideVerticalAttr = 0
    private var divideVertical = 0
    private var divideHorizontal = 0

    private var itemPaddingFixable = false
    private var linePoints : MutableList<Point> = ArrayList()   //上一行所有的点坐标


    private val scroller : Scroller  //可以滑动查看
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

    constructor(context: Context):this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs, defStyleAttr, 0)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.FlowLayout).apply {
            orientation = getInt(R.styleable.FlowLayout_orientation, HORIZONTAL)
            val itemPadding = getDimension(R.styleable.FlowLayout_item_padding, 0f)
            itemDivideHorizontalAttr = getDimension(R.styleable.FlowLayout_item_divide_horizontal, itemPadding).toInt()
            itemDivideVerticalAttr = getDimension(R.styleable.FlowLayout_item_divide_vertical, itemPadding).toInt()
            itemPaddingFixable = getBoolean(R.styleable.FlowLayout_item_padding_fixable, false)
        }.recycle()

        scroller = Scroller(context)
        ViewConfiguration.get(context).run {
            touchSlop = scaledPagingTouchSlop
            minVelocity = scaledMinimumFlingVelocity
            maxVelocity = scaledMaximumFlingVelocity
        }
        isClickable = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        when(orientation){
//            VERTICAL ->
//        }
    }

    /**
     * 根据配置是否是自适应间距来设置padding
     */
    private fun initPadding(){
        if (itemPaddingFixable && orientation == VERTICAL){
            divideVertical = 0
            divideHorizontal = itemDivideHorizontalAttr
        }else if (itemPaddingFixable && orientation == HORIZONTAL){
            divideHorizontal = 0
            divideVertical = itemDivideVerticalAttr
        }else{
            divideHorizontal = itemDivideHorizontalAttr
            divideVertical = itemDivideVerticalAttr
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        linePoints.clear()
        when(orientation){
            VERTICAL -> layoutVertical(l, t, r, b)
            HORIZONTAL -> layoutHorizontal(l, t, r, b)
        }
    }

    private fun layoutHorizontal(l: Int, t: Int, r: Int, b: Int) {
        initPadding()
        leftBorder = l
        topBorder = t
        var lt  = l + paddingLeft
        var resentLinePoints : MutableList<Point> = ArrayList()
        var lineViews : MutableList<View> = ArrayList()
        for(i in 0 until childCount){
            getChildAt(i).apply {
                var totalDivide = 0
                if (lt + measuredWidth + this@FlowLayout.paddingRight > r){
                    totalDivide = r - lt
                    linePoints.clear()
                    linePoints.addAll(resentLinePoints)
                    resentLinePoints = layoutLine(lineViews, totalDivide, orientation, linePoints)  //已经铺满一行，layout当前已经记录的view
                    lineViews.clear()
                    lt = l + this@FlowLayout.paddingLeft  //重置起点
                }

                left = lt
                lineViews.add(this)
                lt = left + measuredWidth + divideHorizontal

                if (i == childCount -1){  //最后一个view
                    totalDivide = r - lt
                    layoutLine(lineViews, totalDivide, orientation, linePoints)
                    lineViews.clear()
                }
            }
        }
    }

    /**
     * 获取view 的top
     */
    private fun getChildTop(lt: Int, rig: Int, linePoints: MutableList<Point>) : Int {
        var tp = paddingTop
        for (i in 0 until linePoints.size){
            val point = linePoints[i]
            if (point.x < lt ||
                    (i > 0 && point.x > rig)){
                continue
            }else{
                tp = maxOf(tp, point.y)
            }
        }
        if (linePoints.isNotEmpty()){
            tp += divideVertical
        }
        return tp
    }


    /**
     * 自适应layout
     * children layout
     */
    private fun layoutLine(lineViews: MutableList<View>, totalDivide: Int, orientation: Int, linePoints: MutableList<Point>): MutableList<Point> {
        var itemPaddingChangeable = 0
        if (itemPaddingFixable && lineViews.size -1 != 0){
            itemPaddingChangeable = totalDivide/(lineViews.size-1)
        }

        var resentLinePoints : MutableList<Point> = ArrayList()
        for (i in 0 until lineViews.size){
            val view = lineViews[i]
            when(orientation){
                HORIZONTAL ->{
                    view.left += itemPaddingChangeable * i
                    view.right = view.left + view.measuredWidth
                    view.top = getChildTop(view.left, view.right, linePoints)
                    view.bottom = view.top + view.measuredHeight
                    view.layout(view.left, view.top, view.right, view.bottom)
                    bottomBorder = Math.max(view.bottom, bottomBorder)
                    resentLinePoints.add(Point(view.left, view.bottom))
                    resentLinePoints.add(Point(view.right, view.bottom))
                }
            }
        }
        return resentLinePoints
    }

    private fun layoutVertical(l: Int, t: Int, r: Int, b: Int) {

    }


    /**
     * 实现连贯滑动
     */
    override fun computeScroll() {
        if (scroller.computeScrollOffset()){
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
                if (orientation == VERTICAL && Math.abs(currentX - ev.rawX) > touchSlop){
                    currentX = ev.rawX
                    return true
                }else if (orientation == HORIZONTAL && Math.abs(currentY - ev.rawY) > touchSlop){
                    currentY = ev.rawY
                    return true
                }
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
        initVelocityTracker(event)
        when(event.action){
            MotionEvent.ACTION_MOVE -> {
                if (orientation == HORIZONTAL){
                    if (scrollY + (currentY - event.rawY) < topBorder){
                        scrollTo(0, topBorder)
                        return true
                    }else if(scrollY + measuredHeight + currentY - event.rawY > bottomBorder){
                        scrollTo(0, bottomBorder - measuredHeight)
                        return true
                    }
                    scrollBy(0, (currentY - event.rawY).toInt())
                    currentY = event.rawY
                }
            }
//            MotionEvent.ACTION_UP ->{  //todo fling
//                val upVelocityTracker = velocityTracker
//                upVelocityTracker!!.computeCurrentVelocity(1000, maxVelocity.toFloat())
//                if (orientation == HORIZONTAL && Math.abs(upVelocityTracker.yVelocity) > minVelocity){
//                    scroller.fling(event.rawX.toInt(), event.rawY.toInt(), 0, -upVelocityTracker.yVelocity.toInt(),
//                            leftBorder, topBorder, rightBorder, bottomBorder)
//                }
//                releaseVelocityTracker()
//            }

        }
        return super.onTouchEvent(event)
    }


    fun initVelocityTracker(event: MotionEvent){
        if (velocityTracker == null){
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker!!.addMovement(event)
    }

    fun releaseVelocityTracker(){
        if (velocityTracker != null){
            velocityTracker!!.recycle()
            velocityTracker = null
        }
    }

}