package com.example.wanghui.kotlin.ui.view.roundview

import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.annotation.IntRange

/**
 * Created by wanghui on 2017/7/27.
 */
class CircleDrawable(bitmap: Bitmap) : Drawable() {
    var mBitmap : Bitmap? = null
    var paint : Paint? = null
    var rectf : RectF? = null
    var radius : Float = 30f

    init {
        mBitmap = bitmap
        paint = Paint()
        val shader = BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint!!.shader = shader
        paint!!.isAntiAlias = true
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
    }

    override fun draw(canvas: Canvas?) {
        val radius = Math.min(mBitmap!!.height, mBitmap!!.width)/2
        canvas!!.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), paint)
    }

    override fun getIntrinsicHeight(): Int {
        return Math.min(mBitmap!!.height, mBitmap!!.width)
    }

    override fun getIntrinsicWidth(): Int {
        return Math.min(mBitmap!!.height, mBitmap!!.width)
    }

    override fun setAlpha(alpha: Int) {
        paint!!.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint!!.colorFilter = colorFilter
    }

}
