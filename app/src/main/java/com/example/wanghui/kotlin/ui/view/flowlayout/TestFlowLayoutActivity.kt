package com.example.wanghui.kotlin.ui.view.flowlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView

import com.example.wanghui.kotlin.R
import kotlinx.android.synthetic.main.activity_test_flow_layout.*

class TestFlowLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_flow_layout)

        setFlowContent()
    }

    private fun setFlowContent() {
        (0..9).map {
                    TextView(this).apply {
                        text = "这是测试$it"
                        gravity = Gravity.CENTER
                        layoutParams = ViewGroup.LayoutParams(300 , 200 + it * 50)
                        background = resources.getDrawable(R.color.white)
                    }
                }
                .forEach { flowLayout.addView(it) }
    }
}
