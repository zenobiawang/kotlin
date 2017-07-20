package com.example.wanghui.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import com.example.wanghui.kotlin.ui.scroll.BaseScrollDemo
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*


class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btnClick.setOnClickListener { _ -> Toast.makeText(this, "click", Toast.LENGTH_SHORT).show() }
        testScroller()
    }

    private fun testScroller() {
        val scroller = BaseScrollDemo(this)
        scroller.background = resources.getDrawable(R.color.material_blue_grey_800)
        addContentView(scroller, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500))

        Timer().schedule(object : TimerTask(){
            override fun run() {
                runOnUiThread {
                    Toast.makeText(this@Main2Activity, "scroll", Toast.LENGTH_SHORT).show()
                    scroller.scrollTo(-500) }
            }
        }, 2000)


    }
}
