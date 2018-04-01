package com.example.wanghui.kotlin.ui.view.flowlayout

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wanghui.kotlin.R
import org.jetbrains.anko.backgroundColor

/**
 * Created by wanghui on 2018/3/21.
 */
class MyFlowLayoutAdataper<String>(context: Context, items: MutableList<String>): FlowLayoutAdapter<String>(context, items){
    override fun getView(context: Context, position: Int, parent: ViewGroup): View{
        return TextView(context).apply {
            gravity = Gravity.CENTER
            backgroundColor = ContextCompat.getColor(context, R.color.little_yellow)
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun bindView(position: Int, view: View) {
        (view as TextView).text = getItem(position).toString()
    }
}