package com.example.wanghui.kotlin.ui.view.flowlayout

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * Created by wanghui on 17/12/20.
 * 新建view、刷新view数据
 */
abstract class FlowLayoutAdapter {
    abstract fun bindView(context: Context, position: Int, parent: ViewGroup)

    abstract fun initData(position: Int, view: View)
}