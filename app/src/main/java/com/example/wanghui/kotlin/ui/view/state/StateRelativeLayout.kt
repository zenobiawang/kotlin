package com.example.wanghui.kotlin.ui.view.state

import android.content.Context
import android.support.annotation.IntDef
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.example.wanghui.kotlin.R
import java.util.*

/**
 * Created by wanghui on 2017/9/29.
 * 多种状态的相对布局
 */
class StateRelativeLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    private val state1 : IntArray = kotlin.IntArray(1).apply { set(0, R.attr.state_1) }
    private val state2 : IntArray = kotlin.IntArray(1).apply { set(0, R.attr.state_2) }
    private val state3 : IntArray = kotlin.IntArray(1).apply { set(0, R.attr.state_3) }
    private val state4 : IntArray = kotlin.IntArray(1).apply { set(0, R.attr.state_4) }
    private val state5 : IntArray = kotlin.IntArray(1).apply { set(0, R.attr.state_5) }
    private val state6 : IntArray = kotlin.IntArray(1).apply { set(0, R.attr.state_6) }
    private val state7 : IntArray = kotlin.IntArray(1).apply { set(0, R.attr.state_7) }
    private val state8 : IntArray = kotlin.IntArray(1).apply { set(0, R.attr.state_8) }
    private val state9 : IntArray = kotlin.IntArray(1).apply { set(0, R.attr.state_9) }
    private val states = listOf(state1, state2, state3, state4, state5, state6, state7, state8, state9)

    companion object {
        val STATENONE_FLAG = 0
        val STATE1_FLAG = 1
        val STATE2_FLAG = 2
        val STATE3_FLAG = 3
        val STATE4_FLAG = 4
        val STATE5_FLAG = 5
        val STATE6_FLAG = 6
        val STATE7_FLAG = 7
        val STATE8_FLAG = 8
        val STATE9_FLAG = 9
    }

    var stateFlag = 0
    fun setState(flag : Int){  //todo 注解约束
        stateFlag = flag
        if(this.isShown){
            refreshDrawableState()
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        if (stateFlag != 0){
            val drawState = super.onCreateDrawableState(extraSpace + 1)
            mergeDrawableStates(drawState, states[stateFlag -1])
            return drawState
        }
        return super.onCreateDrawableState(extraSpace)
    }


}