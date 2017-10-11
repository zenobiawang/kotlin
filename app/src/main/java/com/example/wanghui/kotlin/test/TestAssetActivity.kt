package com.example.wanghui.kotlin.test

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.wanghui.kotlin.R
import kotlinx.android.synthetic.main.activity_test_asset.*

class TestAssetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_asset)
        val input = resources.assets.open("test_asset_2.jpg")
        val bitmap = BitmapFactory.decodeStream(input)
        imageView.setImageBitmap(bitmap)
        val input2 = resources.assets.open("test_asset.jpg")
        imageView2.setImageBitmap(BitmapFactory.decodeStream(input2))

        val test = listOf("1", "2")
        test.filter { it.equals("1") }

        test {
            val a = 1
            val b = 2
            if (a > b){
                "1"
            }else{
                "2"
            }
        }
    }

    fun test(testA: () -> String){
        testA()
    }




}
