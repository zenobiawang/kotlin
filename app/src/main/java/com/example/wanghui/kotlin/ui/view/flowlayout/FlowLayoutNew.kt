package com.example.wanghui.kotlin.ui.view.flowlayout

import android.content.Context
import android.util.AttributeSet
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/11/29.
 */
class FlowLayoutNew(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private var itemDivideHorizontalAttr = 0
    private var itemDivideVerticalAttr = 0
    private var divideVertical = 0
    private var divideHorizontal = 0
    private val scroller : OverScroller  //可以滑动查看
    private var touchSlop: Int = 0
    private var minVelocity: Int = 0
    private var maxVelocity: Int = 0

    private var itemPaddingFixable = false
    constructor(context: Context):this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs, defStyleAttr, 0)


    init {
        context.obtainStyledAttributes(attrs, R.styleable.FlowLayout).apply {
            val itemPadding = getDimension(R.styleable.FlowLayout_item_padding, 0f)
            itemDivideHorizontalAttr = getDimension(R.styleable.FlowLayout_item_divide_horizontal, itemPadding).toInt()
            itemDivideVerticalAttr = getDimension(R.styleable.FlowLayout_item_divide_vertical, itemPadding).toInt()
            itemPaddingFixable = getBoolean(R.styleable.FlowLayout_item_padding_fixable, false)
        }.recycle()

        scroller = OverScroller(context)
        ViewConfiguration.get(context).run {
            touchSlop = scaledPagingTouchSlop
            minVelocity = scaledMinimumFlingVelocity
            maxVelocity = scaledMaximumFlingVelocity
        }
        isClickable = true
    }

    /**
     * 根据配置是否是自适应间距来设置padding
     */
    private fun initPadding(){
        if (itemPaddingFixable){
            divideHorizontal = 0
            divideVertical = itemDivideVerticalAttr
        }else{
            divideHorizontal = itemDivideHorizontalAttr
            divideVertical = itemDivideVerticalAttr
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {  //todo measure wrap_content
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}