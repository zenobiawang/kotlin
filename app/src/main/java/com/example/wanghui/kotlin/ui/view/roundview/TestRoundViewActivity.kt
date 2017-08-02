package com.example.wanghui.kotlin.ui.view.roundview

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle

import com.example.wanghui.kotlin.R
import kotlinx.android.synthetic.main.activity_test_round_view.*

class TestRoundViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_round_view)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test)
        val drawable = CircleDrawable(bitmap)
        imageView_circle.setImageDrawable(drawable)
        val roundDrawable = RoundDrawable(bitmap)
        imageView_round.setImageDrawable(roundDrawable)
    }
}
