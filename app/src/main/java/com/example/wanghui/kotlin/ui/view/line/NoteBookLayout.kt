package com.example.wanghui.kotlin.ui.view.line

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

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
class NoteBookLayout : ViewGroup{
    var lines : Int = 0   //范围内分割线的数量
    var lineHeight : Int = 0 //分割线的高度
    var lineColor : Int = 0 //分割线的颜色
    var lineWidthMode : Int = 0 //分割线宽度 -1 父布局有多大，就有多宽  -2 子布局内容有多大，就有多宽

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    constructor(context: Context?) : super(context){}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){init(context, attrs)}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(context, attrs)}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes){init(context, attrs)}


    /**
     * <attr name="lines" format="integer" />
    <attr name="lineHeight" format="dimension"/>
    <attr name="lineColor" format="color"/>
    <attr name="lineWidthMode" format="integer">
    <enum name="match_parent" value="-1"/>
    <enum name="wrap_content" value="-2"/>
    </attr>
     */
    private fun init(context: Context?, attrs: AttributeSet?){

    }
}
