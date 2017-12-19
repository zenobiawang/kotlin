package com.example.wanghui.kotlin.ui.view.line

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/8/7.
 * 条形纸练习本类似
 * ------------
 * ------------
 * ------------
 * ------------
 * ------------
 * ------------
 * ------------
 */
class NoteBookLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ViewGroup(context, attrs, defStyleAttr) {
    val TAG = "NoteBookLayout"
    var lines : Int = 5   //范围内分割线的数量
    var lineHeight : Int = 1 //分割线的高度
    var lineColor : Int = resources.getColor(R.color.red) //分割线的颜色
    var lineWidthMode : Int = 0 //分割线宽度 -1 父布局有多大，就有多宽  -2 子布局内容有多大，就有多宽
    var interval :  Int = 0 //行与行之间的间隔
    var paint : Paint? = null
    var linePadding = 0 //线条与边框的边距

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    /**
     * <attr name="lines" format="integer" />
    <attr name="lineHeight" format="dimension"/>
    <attr name="lineColor" format="color"/>
    <attr name="lineWidthMode" format="integer">
    <enum name="match_parent" value="-1"/>
    <enum name="wrap_content" value="-2"/>
    </attr>
     */
    init {
        context.obtainStyledAttributes(attrs, R.styleable.NoteBookLayout).apply {
            lines = getInt(R.styleable.NoteBookLayout_lines, 5)
            lineHeight = getDimensionPixelSize(R.styleable.NoteBookLayout_lineHeight,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics).toInt())
            lineColor = getColor(R.styleable.NoteBookLayout_lineColor, resources.getColor(R.color.red))
        }.recycle()
        paint = Paint()
        paint!!.isAntiAlias = true  //消除锯齿
        setWillNotDraw(false)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0..childCount -1){
            getChildAt(i).layout(l, i * interval, r, (i + 1) * interval)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (i in 0..childCount -1){
            measureChild(getChildAt(i), widthMeasureSpec, MeasureSpec.makeMeasureSpec(interval, MeasureSpec.EXACTLY))
        }
        interval = measuredHeight/lines
    }


    override fun onDraw(canvas: Canvas?) {
        paint!!.color = lineColor
        paint!!.strokeWidth = lineHeight.toFloat()
        for (i in 0..lines){
            canvas!!.drawLine(linePadding.toFloat(), (i * interval - lineHeight).toFloat(), (width - linePadding).toFloat(), (i * interval).toFloat(), paint)
        }
    }
}
