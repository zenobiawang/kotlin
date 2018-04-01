package com.example.wanghui.kotlin.ui.view.flowlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView

import com.example.wanghui.kotlin.R
import kotlinx.android.synthetic.main.activity_test_flow_layout.*
import org.jetbrains.anko.backgroundColor

class TestFlowLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_flow_layout)
        Log.d("wh----", "wh-----setContentView")
        setFlowContent()
    }

    private fun setFlowContent() {
        val inputStream = assets.open("txt/text1.txt")
        flowLayout.setAdapter(MyFlowLayoutAdataper(this, inputStream.bufferedReader().readLines() as MutableList<String>))
//        inputStream.bufferedReader().readLines().map {
//            TextView(this).apply {
//                text = it
//                gravity = Gravity.CENTER
//                backgroundColor = ContextCompat.getColor(context, R.color.little_yellow)
//            }
//        }.forEach {
//            flowLayout.addView(it)
//        }
    }
}
