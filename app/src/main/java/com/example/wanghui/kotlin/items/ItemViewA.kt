package com.example.wanghui.kotlin.items

import android.content.Context
import android.view.View
import com.example.wanghui.kotlin.R
import kotlinx.android.synthetic.main.activity_test_fall.view.*
import org.jetbrains.anko.find

/**
 * Created by wanghui on 2019-05-28.
 */
class ItemViewA(context: Context): View(context), IBind {
    init {

    }

    override fun onBind(data: ViewModel) {

        find<View>(R.id.fallView).setOnClickListener {

            if (data.action?.invoke(ItemClickAction("")) != true){
                //do default click action
                //补偿曝光
            }

        }
    }
}