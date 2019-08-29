package com.example.wanghui.kotlin.ui.nestedscroll

import android.content.Context
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.NestedScrollingParentHelper
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ScrollerCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * Created by wanghui on 2019-07-15.
 * 一个可以吸顶的view
 * 版本1： recyclerView + tab + recyclerView 测试
 */
class MountingView1 @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr), NestedScrollingParent {
    private val nestedScrollHelper by lazy { NestedScrollingParentHelper(this) }
    private val scroller: ScrollerCompat by lazy { ScrollerCompat.create(getContext(), null) }
    private val FLING_FROM_HEADER_TO_MINE = 0
    private val FLING_FROM_TAIL_TO_MINE = 1

    private var flingState = FLING_FROM_HEADER_TO_MINE

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec)
        measureChild(getChildAt(1), widthMeasureSpec, heightMeasureSpec)
        measureChild(getChildAt(2), widthMeasureSpec, MeasureSpec.makeMeasureSpec(measuredHeight - getChildAt(1).measuredHeight, MeasureSpec.EXACTLY))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var layoutTop = 0
        for (i in 0 until childCount) {
            layoutTop = layoutChild(getChildAt(i), layoutTop)
        }
    }

    private fun layoutChild(child: View?, top: Int): Int {
        child ?: return top
        val bottom = top + (child.measuredHeight)
        child.layout(0, top, child.measuredWidth, bottom)
        return bottom
    }

    /**
     * 是否支持嵌套滑动
     */
    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
        Log.d("wh", "wh------- onStartNestedScroll child-${child.hashCode()} target-${target.hashCode()} nestedScrollAxes-$nestedScrollAxes")
        return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    /**
     * 初始配置调用
     */
    override fun onNestedScrollAccepted(child: View?, target: View?, axes: Int) {
        Log.d("wh", "wh------- onNestedScrollAccepted child-${child.hashCode()} target-${target.hashCode()} axes-$axes")
        nestedScrollHelper.onNestedScrollAccepted(child, target, axes)
    }

    override fun onStopNestedScroll(child: View?) {
        Log.d("wh", "wh------- onStopNestedScroll child-${child.hashCode()}")
        nestedScrollHelper.onStopNestedScroll(child)
    }

    override fun onNestedScroll(target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        Log.d("wh", "wh------- onNestedScroll target-${target.hashCode()} dxConsumed-$dxConsumed " +
                "dyConsumed-$dyConsumed dxUnconsumed-$dxUnconsumed dyUnconsumed-$dyUnconsumed")
        scrollBy(dxUnconsumed, dyUnconsumed)
        postInvalidate()
    }

    override fun onNestedPreScroll(target: View?, dx: Int, dy: Int, consumed: IntArray) {
        Log.d("wh", "wh------- onNestedPreScroll target-${target.hashCode()} dx-$dx dy-$dy " +
                "consumed-${consumed?.get(0)}、${consumed?.get(1)}")
        if ((dy > 0 && scrollY < getScrollRange() && isHeaderBottom())
                || (dy < 0 && scrollY > 0 && isTailTop())) {
            scrollBy(0, dy)
            postInvalidate()
            consumed[1] = dy
        }
    }

    override fun onNestedFling(target: View?, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        Log.d("wh", "wh------- onNestedFling target-${target.hashCode()} velocityX-$velocityX velocityY-$velocityY consumed-$consumed")
        return false
    }

    override fun onNestedPreFling(target: View?, velocityX: Float, velocityY: Float): Boolean {
        Log.d("wh", "wh------- onNestedPreFling target-${target.hashCode()} velocityX-$velocityX velocityY-$velocityY")
        if ((velocityY > 0 && scrollY == getScrollRange())
                || (velocityY < 0 && scrollY == 0)) {
            return false
        }else if (isHeaderBottom() && velocityY > 0 && scrollY < getScrollRange()){
            fling(velocityY)
            return true
        }else if (isTailTop() && velocityY < 0 && scrollY > 0){
            fling(velocityY)
            return true
        }
        //        val canFling = (scrollY > 0 || velocityY > 0) && (scrollY < getScrollRange() || velocityY < 0)
//        if (canFling) {
//            fling(velocityY)
//        }
        return false
    }

    override fun getNestedScrollAxes(): Int {
        Log.d("wh", "wh------- getNestedScrollAxes")
        return nestedScrollHelper.nestedScrollAxes
    }

    private var headerFling = false
    private var tailFling = false
    override fun computeScroll() {
        val currentY = scroller.currY
        val currVelocity = scroller.currVelocity
        Log.d("wh", "wh----- computeScroll ${scroller.currY}   currVelocity $currVelocity")
        if (!scroller.computeScrollOffset()) return
        when {
            currentY < 0 -> scrollTo(0, 0)
            currentY > getScrollRange() -> scrollTo(0, getScrollRange())
            else -> scrollTo(0, currentY)
        }
        postInvalidate()
        if (currentY <= 0 && !headerFling) {
            (getChildAt(0) as RecyclerView).fling(0, -currVelocity.toInt())
            scroller.abortAnimation()
        }else if (currentY >= getScrollRange()){
            (getChildAt(2) as RecyclerView).fling(0, currVelocity.toInt())
            scroller.abortAnimation()
        }
    }

    private fun fling(velocityY: Float) {
        scroller.fling(scrollX, scrollY, 0, velocityY.toInt(), 0, 0, Int.MIN_VALUE,
                Int.MAX_VALUE, 0, 0)
    }

    private fun getScrollRange(): Int {
        var contentHeight = 0
        for (i in 0 until childCount) {
            contentHeight += getChildAt(i).height
        }
        return Math.max(0, contentHeight - height + paddingTop + paddingBottom)
    }

    /**
     * 判断头部是否滚动到底部
     */
    private fun isHeaderBottom(): Boolean {
        val header = getChildAt(0) as? RecyclerView ?: return true
        return header.computeVerticalScrollExtent() + header.computeVerticalScrollOffset() >= header.computeVerticalScrollRange()
    }

    /**
     * 判断尾部是否滚动到头部
     */
    private fun isTailTop(): Boolean {
        val tail = getChildAt(2) as? RecyclerView ?: return true
        return tail.computeVerticalScrollOffset() == 0
    }

    override fun scrollBy(x: Int, y: Int) {
        val consumY: Int = when {
            scrollY + y < 0 -> -scrollY
            scrollY + y > getScrollRange() -> getScrollRange() - scrollY
            else -> y
        }
        super.scrollBy(x, consumY)
    }

}