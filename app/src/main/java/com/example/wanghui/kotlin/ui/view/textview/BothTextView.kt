package com.example.wanghui.kotlin.ui.view.textview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextUtils
import android.text.style.TypefaceSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/8/2.
 * 两侧都有文字
 * 文字过多处理
 */
class BothTextView : TextView{
    var paint = Paint()
    var subText : String? = null
    var subTextColor : Int = 0
    var subTextSize : Int = 0
    var innerPadding : Int = 0


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context, attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(context, attrs)}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes){init(context, attrs)}

    private fun init(context: Context?, attrs: AttributeSet?) {
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.BothTextView)
        subText = a.getString(R.styleable.BothTextView_subText)
        subTextColor = a.getColor(R.styleable.BothTextView_subTextColor, context.resources.getColor(R.color.red))
        subTextSize = a.getDimensionPixelSize(R.styleable.BothTextView_subTextSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13f, resources.displayMetrics).toInt())
        innerPadding = a.getDimensionPixelSize(R.styleable.BothTextView_innerPadding,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, resources.displayMetrics).toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!TextUtils.isEmpty(subText)){
            val subTextPaint = Paint()
            subTextPaint.color = subTextColor
            subTextPaint.textSize = subTextSize.toFloat()
            val subTextWidth = subTextPaint.measureText(subText)
            var compoundDrawableWidth = 0
            if (compoundDrawables[2] != null){
                compoundDrawableWidth = compoundDrawables[2].intrinsicWidth + compoundDrawablePadding
            }
            val right = width - subTextWidth - paddingRight - compoundDrawableWidth
            val rightStart = Math.max(right, paint.measureText(text.toString()) + suggestedMinimumWidth + innerPadding)  //怎么避免文字过长遮挡左侧text
            canvas!!.drawText(subText, right, (height - subTextPaint.descent() - subTextPaint.ascent())/2, subTextPaint)
        }
    }
}

