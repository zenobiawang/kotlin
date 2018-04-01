package com.example.wanghui.kotlin.ui.view.flowlayout

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * Created by wanghui on 17/12/20.
 * 新建view、刷新view数据
 */
abstract class FlowLayoutAdapter<T>(context: Context, items: MutableList<T>?) {
    private val localItems : MutableList<T> = ArrayList()

    init {
        items?.apply { localItems.addAll(this) }
    }
    abstract fun getView(context: Context, position: Int, parent: ViewGroup) : View

    abstract fun bindView(position: Int, view: View)

    fun getItem(position: Int) : T{
        return localItems[position]
    }

    fun getCount(): Int{
        return localItems.size
    }

    fun swapItems(data: MutableList<T>){  //todo
        localItems.clear()
        localItems.addAll(data)
    }

    fun addItems(data: MutableList<T>){ //todo
        localItems.addAll(data)
    }
}