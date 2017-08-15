package com.example.wanghui.kotlin.ui.view.flowlayout

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/8/14.
 * 流布局
 * 从左往右依次摆放、从上往下依次摆放
 * 自定义间距、自适应间距
 */
class FlowLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    val VERTICAL = -1     //从上往下布局
    val HORIZONTAL = -2   //从左往右布局
    var orientation  = 0
    var itemPadding = 0
    var linePoints : MutableList<Point> = ArrayList()


    constructor(context: Context):this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs, defStyleAttr, 0)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.FlowLayout).apply {
            orientation = getInt(R.styleable.FlowLayout_orientation, HORIZONTAL)
            itemPadding = getDimension(R.styleable.FlowLayout_item_padding, 0f).toInt()
        }
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


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        linePoints.clear()
        when(orientation){
            VERTICAL -> layoutVertical(l, t, r, b)
            HORIZONTAL -> layoutHorizontal(l, t, r, b)
        }
    }

    private fun layoutHorizontal(l: Int, t: Int, r: Int, b: Int) {
        var lt  = l
        var tp  = t
        var rig : Int
        var botm : Int
        var lineEnough : Boolean = false
        var resentLinePoints : MutableList<Point> = ArrayList()
        var lineViews : MutableList<View> = ArrayList()
        for(i in 0..childCount-1){
            getChildAt(i).apply {
                var totalPadding = 0
                var tempLeft = lt + measuredWidth
                if (tempLeft > r){
                    lineEnough = true
                    totalPadding = r - lt
                    lt = l
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
                        tp = maxOf(tp, point.y)
                    }
                }
                botm = tp + measuredHeight
                resentLinePoints.add(Point(rig, botm))

                Log.d("w", "wh-----$lineEnough")
                if (lineEnough){
                    layoutLine(lineViews, totalPadding, orientation)
                    lineViews.clear()
                    lineEnough = false
                }

                left = lt
                right = rig
                top = tp
                bottom = botm
                lineViews.add(this)
                lt = rig
            }
        }
    }

    /**
     * children layout
     */
    private fun layoutLine(lineViews: MutableList<View>, totalPadding: Int, orientation: Int) {
        var itemPadding = totalPadding/(lineViews.size-1)
        for (i in 0..lineViews.size -1){
            val view = lineViews[i]
            when(orientation){
                HORIZONTAL ->{
                    Log.d("tag", "wh------$view")
                    view.layout(view.left + itemPadding * i, view.top, view.right + itemPadding * i, view.bottom)
                }
            }
        }
    }

    private fun layoutVertical(l: Int, t: Int, r: Int, b: Int) {
    }



}