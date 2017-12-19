package com.example.wanghui.kotlin.ui.view.textview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/8/2.
 * 两侧都有文字
 * 文字过多处理
 */
class BothTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : TextView(context, attrs, defStyleAttr) {
    var subText : String? = null
    var subTextColor : Int = 0
    var subTextSize : Int = 0
    var innerPadding : Int = 0
    var subTextPaint : Paint


    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BothTextView).apply {
            subText = getString(R.styleable.BothTextView_subText)
            subTextColor = getColor(R.styleable.BothTextView_subTextColor, context.resources.getColor(R.color.red))
            subTextSize = getDimensionPixelSize(R.styleable.BothTextView_subTextSize,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13f, resources.displayMetrics).toInt())
            innerPadding = getDimensionPixelSize(R.styleable.BothTextView_innerPadding,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, resources.displayMetrics).toInt())
        }.recycle()

        subTextPaint = Paint().apply {
            color = subTextColor
            textSize = subTextSize.toFloat()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!TextUtils.isEmpty(subText)){
            val subTextWidth = subTextPaint.measureText(subText)
            var compoundDrawableWidth = 0
            if (compoundDrawables[2] != null){
                compoundDrawableWidth = compoundDrawables[2].intrinsicWidth + compoundDrawablePadding
            }
            val right = width - subTextWidth - paddingRight - compoundDrawableWidth
//            val rightStart = Math.max(right, paint.measureText(text.toString()) + suggestedMinimumWidth + innerPadding)  //怎么避免文字过长遮挡左侧text
            canvas!!.drawText(subText, right, (height - subTextPaint.descent() - subTextPaint.ascent())/2, subTextPaint)
        }
    }
}

