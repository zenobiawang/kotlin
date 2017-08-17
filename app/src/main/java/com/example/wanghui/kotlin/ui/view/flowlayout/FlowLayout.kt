package com.example.wanghui.kotlin.ui.view.flowlayout

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
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
    val VERTICAL = -1     //从上往下布局
    val HORIZONTAL = -2   //从左往右布局
    var orientation  = 0
    var itemDivideHorizontalAttr = 0
    var itemDivideVerticalAttr = 0
    var divideVertical = 0
    var divideHorizontal = 0

    var itemPaddingFixable = false
    var linePoints : MutableList<Point> = ArrayList()
    val scroller : Scroller  //可以滑动查看

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
    fun initPadding(){
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
        var lt  = l + paddingLeft
        var tp  = t
        var rig : Int
        var botm : Int
        var lineEnough : Boolean = false
        var resentLinePoints : MutableList<Point> = ArrayList()
        var lineViews : MutableList<View> = ArrayList()
        for(i in 0..childCount-1){
            getChildAt(i).apply {
                var totalDivide = 0
                var tempLeft = lt + measuredWidth + paddingRight
                if (tempLeft > r){
                    lineEnough = true
                    totalDivide = r - lt
                    lt = l + this@FlowLayout.paddingLeft
                    linePoints.clear()
                    linePoints.addAll(resentLinePoints)
                    resentLinePoints.clear()
                }
                rig = lt + measuredWidth

                for (i in 0..linePoints.size-1){
                    val point = linePoints[i]
                    if (point.x < lt ||
                            (i > 0 && point.x > rig && linePoints[i -1].x >= rig)){
                        continue
                    }else{
                        tp = maxOf(tp, point.y) + divideVertical
                    }
                }
                botm = tp + measuredHeight
                resentLinePoints.add(Point(rig, botm))

                if (lineEnough){
                    if (itemPaddingFixable){
                        layoutLine(lineViews, totalDivide, orientation)
                    }else{
                        layoutLine(lineViews, 0, orientation)
                    }
                    lineViews.clear()
                    lineEnough = false
                }

                left = lt
                right = rig
                top = tp
                bottom = botm
                lineViews.add(this)
                lt = rig + divideHorizontal
            }
        }
    }

    /**
     * 自适应layout
     * children layout
     */
    private fun layoutLine(lineViews: MutableList<View>, totalDivide: Int, orientation: Int) {
        var itemPaddingChangeable = totalDivide/(lineViews.size-1)
        for (i in 0..lineViews.size -1){
            val view = lineViews[i]
            when(orientation){
                HORIZONTAL ->{
                    view.layout(view.left + itemPaddingChangeable * i, view.top, view.right + itemPaddingChangeable * i, view.bottom)
                }
            }
        }
    }

    private fun layoutVertical(l: Int, t: Int, r: Int, b: Int) {

    }


    override fun computeScroll() {
        super.computeScroll()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }


}