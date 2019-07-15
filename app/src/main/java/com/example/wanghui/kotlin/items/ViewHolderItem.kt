package com.example.wanghui.kotlin.items

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by wanghui on 2019-05-28.
 * 最小单元的 viewHolder(没有嵌套)
 */
class ViewHolderItem(itemView: View): RecyclerView.ViewHolder(itemView) {
    /**
     * 当执行bind的时候，实际上是给view填充数据
     */
    fun onBind(ob: Any){
        val viewModel = ViewModel().apply {
            data = ob
            exposure = {

                true
            }
            action = {
                //上报点击事件

                false
            }
        }
        if (itemView is IBind){
            itemView.onBind(viewModel)
        }

        //fillUI
    }
}