package com.example.wanghui.kotlin.ui.view.fall

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.wanghui.kotlin.R
import kotlinx.android.synthetic.main.activity_test_fall.*
import org.jetbrains.anko.dip

/**
 * Created by wanghui on 2019/2/21.
 * 飘落动画
 */
class TestFallActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_fall)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.timg)
        val matrix = Matrix().apply { postScale(dip(30)/bitmap.width.toFloat(), dip(30)/bitmap.height.toFloat())}
        val newB = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        val fallEntity = FallView.FallEntity(beginY = 100,  endY = 1500, disappearY = 1200, image = newB, speedRange = "500-550", viewWidth = 1080)
        fallView.show(mutableListOf(fallEntity), 20, 10000)
    }
}