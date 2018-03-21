package com.example.wanghui.kotlin.ui.view.flowlayout

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ScrollerCompat
import android.util.AttributeSet
import android.view.*
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 17/12/26.
 */
class FlowLayout3(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ViewGroup(context, attrs, defStyleAttr) {
    private val TAG = "FlowLayoutNew"
    private var itemDivideHorizontal = 0
    private var itemDivideVertical = 0
    private var columns: Int = 2
    private var childWidth = 0

    private val scroller : ScrollerCompat  //可以滑动查看
    private var touchSlop = 0
    private var currentX = 0f
    private var currentY = 0f
    private var minVelocity = 0
    private var maxVelocity = 0
    private var velocityTracker : VelocityTracker? = null
    private var isBeingDragged = false //是否达到滚动条件

    constructor(context: Context):this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0)


    init {
        context.obtainStyledAttributes(attrs, R.styleable.FlowLayout).apply {
            val itemPadding = getDimension(R.styleable.FlowLayout_item_padding, 0f)
            itemDivideHorizontal = getDimension(R.styleable.FlowLayout_item_divide_horizontal, itemPadding).toInt()
            itemDivideVertical = getDimension(R.styleable.FlowLayout_item_divide_vertical, itemPadding).toInt()
            columns = getInt(R.styleable.FlowLayout_columns, 2)
        }.recycle()

        scroller = ScrollerCompat.create(context)
        ViewConfiguration.get(context).run {
            touchSlop = scaledPagingTouchSlop
            minVelocity = scaledMinimumFlingVelocity
            maxVelocity = scaledMaximumFlingVelocity
        }
        isFocusable = true  //todo 为什么设置他们
        descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

        childWidth = (widthSize - itemDivideHorizontal*(columns-1) - paddingLeft - paddingRight)/columns
        (0 until childCount).forEach{
            getChildAt(it).measure(getMeasureChildSpec(widthMeasureSpec, childWidth, 0),
                    getMeasureChildSpec(heightMeasureSpec, LayoutParams.WRAP_CONTENT, 0))
        }

        //todo 自测量
    }

    /**
     * get child measureSpec
     * @param parentMeasureSpec 当前view的measureSpec
     * @param childDimension childView 的layoutParams
     */
    private fun getMeasureChildSpec(parentMeasureSpec: Int, childDimension: Int, padding: Int): Int{
        val parentSize = MeasureSpec.getSize(parentMeasureSpec)
        val parentMode = MeasureSpec.getMode(parentMeasureSpec)
        return when(parentMode){
            MeasureSpec.EXACTLY ->{
                when(childDimension){
                    LayoutParams.MATCH_PARENT -> MeasureSpec.makeMeasureSpec(parentSize - padding, MeasureSpec.EXACTLY)
                    LayoutParams.WRAP_CONTENT -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.AT_MOST)
                    else -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.EXACTLY)
                }
            }
            MeasureSpec.AT_MOST ->{
                when(childDimension){
                    LayoutParams.MATCH_PARENT -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.AT_MOST)
                    LayoutParams.WRAP_CONTENT -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.AT_MOST)
                    else -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.EXACTLY)
                }
            }
            MeasureSpec.UNSPECIFIED ->{
                when(childDimension){
                    LayoutParams.MATCH_PARENT -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.UNSPECIFIED)
                    LayoutParams.WRAP_CONTENT -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.UNSPECIFIED)
                    else -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.EXACTLY)
                }
            }
            else ->{
                when(childDimension){
                    LayoutParams.MATCH_PARENT -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.UNSPECIFIED)
                    LayoutParams.WRAP_CONTENT -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.UNSPECIFIED)
                    else -> MeasureSpec.makeMeasureSpec(childDimension, MeasureSpec.EXACTLY)
                }
            }
        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentColumn = 0
        var topArray = IntArray(columns){paddingTop}
        for (i in 0 until childCount){
            val child = getChildAt(i)
            val top = topArray[currentColumn]
            val left = l + paddingLeft + childWidth*currentColumn + itemDivideHorizontal*currentColumn
            val bottom = top + child.measuredHeight
            val right = paddingLeft + childWidth*(currentColumn + 1) + itemDivideHorizontal*currentColumn
            topArray[currentColumn] = bottom + itemDivideVertical
            child.layout(left, top, right, bottom)
            currentColumn = topArray.indexOf(topArray.min()?:0)
        }
    }


    /**
     * 实现连贯滑动
     */
    override fun computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.currX, scroller.currY)
            ViewCompat.postInvalidateOnAnimation(this)  //todo
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when(ev.action and MotionEventCompat.ACTION_MASK){  //todo
            MotionEvent.ACTION_DOWN ->{
                currentX = ev.x  //todo
                currentY = ev.y
            }
            MotionEvent.ACTION_MOVE ->{
                if (!isBeingDragged){
                    val dy = ev.y - currentY
                    if (Math.abs(dy) > touchSlop){
                        isBeingDragged = true
                    }
                }
            }
            MotionEvent.ACTION_UP , MotionEvent.ACTION_CANCEL->{
                resetTouch()
                return false
            }
        }
        return isBeingDragged
    }

    //todo
    private fun resetTouch() {

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        initVelocityTracker(event)
        when(event.action and MotionEventCompat.ACTION_MASK){
            MotionEvent.ACTION_DOWN ->{
                currentY = event.y
                currentX = event.x
                scroller.abortAnimation()
                parent.requestDisallowInterceptTouchEvent(true)  //todo

            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y
                val dy = currentY - y
                if (!isBeingDragged && Math.abs(dy) > touchSlop){
                    isBeingDragged = true
                    currentX = x
                    currentY = y
                    parent.requestDisallowInterceptTouchEvent(true)
                }

                if (isBeingDragged){
                    currentX = x
                    currentY = y
                    val verticalScrollRange = computeVerticalScrollRange()
                    var scrollDif = dy
                    if (scrollY + dy <= 0){
                        scrollDif = scrollY.toFloat()
                    }else if (scrollY + dy >= verticalScrollRange){
                        scrollDif = (verticalScrollRange - scrollY).toFloat()
                    }
                    scrollBy(0, scrollDif.toInt())
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP ->{
                if (isBeingDragged && velocityTracker != null){
                    velocityTracker!!.computeCurrentVelocity(1000, maxVelocity.toFloat())
                    val velocityY = velocityTracker!!.yVelocity
                    if (Math.abs(velocityY) > minVelocity){
                        //todo 限定整个滚动区域位置，如果复用，则需要动态计算
                        val verticalScrollRange = computeVerticalScrollRange()
                        scroller.fling(scrollX, scrollY, 0, -velocityY.toInt(), 0, 0, 0, verticalScrollRange, 0, 30)
                        postInvalidate()
                    }

                }
                releaseVelocityTracker()
            }
            MotionEvent.ACTION_CANCEL ->{
                releaseVelocityTracker()
            }

        }
        return true
    }

    override fun computeVerticalScrollExtent(): Int {
        val childBottom = ((childCount - columns) until childCount).map { getChildAt(it).bottom }.maxBy { it }?:0
        return childBottom + paddingBottom
    }

    override fun computeVerticalScrollRange(): Int {
        return computeVerticalScrollExtent() - height
    }

    private fun initVelocityTracker(event: MotionEvent){
        if (velocityTracker == null){
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker!!.addMovement(event)
    }

    private fun releaseVelocityTracker(){
        if (velocityTracker != null){
            velocityTracker!!.recycle()
            velocityTracker = null
        }
    }
}