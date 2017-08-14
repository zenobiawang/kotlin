package com.example.wanghui.kotlin.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/8/14.
 * 流布局
 * 从左往右依次摆放、从上往下依次摆放
 * 自定义间距、自适应间距
 */
class FollowLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    val VERTICAL = -1     //从上往下布局
    val HORIZONTAL = -2   //从左往右布局
    var orientation  = 0
    var itemPadding = 0


    constructor(context: Context):this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs, defStyleAttr, 0)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.FollowLayout).apply {
            orientation = getInt(R.styleable.FollowLayout_orientation, HORIZONTAL)
            itemPadding = getDimension(R.styleable.FollowLayout_item_padding, 0f).toInt()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        measureChildren(widthMeasureSpec, heightMeasureSpec)

//        when(orientation){
//            VERTICAL ->
//        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        when(orientation){
            VERTICAL -> layoutVertical(l, t, r, b)
            HORIZONTAL -> layoutHorizontal(l, t, r, b)
        }
    }

    private fun layoutHorizontal(l: Int, t: Int, r: Int, b: Int) {
        var left = l
        var top = t
        for(i in 0..childCount-1){
            getChildAt(i).apply {
                var tempLeft = left + measuredWidth
                if (tempLeft > right){
                    left = l
                }else{
                    left = tempLeft
                }

            }
        }
    }

    private fun layoutVertical(l: Int, t: Int, r: Int, b: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}