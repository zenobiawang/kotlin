package com.example.wanghui.kotlin.ui.layoutmanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.wanghui.kotlin.R
import kotlinx.android.synthetic.main.activity_layout_manager1.*

class LayoutManager1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_manager1)
        recyclerView.layoutManager= MyLayoutManager()
        val adapter= LayoutManagerAdapter(this,(0..100).map { "Item:$it" })
        recyclerView.adapter=adapter
    }
}
