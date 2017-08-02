package com.example.wanghui.kotlin.ui.view.textview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/8/2.
 * 两侧都有文字
 */
class BothTextView : TextView{
    var paint = Paint()
    var subText : String? = null


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context, attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(context, attrs)}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes){init(context, attrs)}

    private fun init(context: Context?, attrs: AttributeSet?) {
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.BothTextView)
        subText = a.getString(R.styleable.BothTextView_subText)

    }
}

