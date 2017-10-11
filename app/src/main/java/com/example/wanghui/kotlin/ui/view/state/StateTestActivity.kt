package com.example.wanghui.kotlin.ui.view.state

import android.app.Activity
import android.os.Bundle
import com.example.wanghui.kotlin.R
import kotlinx.android.synthetic.main.activity_state.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by wanghui on 2017/9/29.
 */
class StateTestActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state)
        var i = 0
        stateRelativeLayout.onClick {
            stateRelativeLayout.setState(i ++)
        }
    }
}