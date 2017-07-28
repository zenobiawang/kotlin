package com.example.wanghui.kotlin.ui.view.roundview

import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.annotation.IntRange

/**
 * Created by wanghui on 2017/7/27.
 */
class RoundDrawable(bitmap: Bitmap) : Drawable() {
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
        rectf = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    override fun draw(canvas: Canvas?) {
        canvas!!.drawRoundRect(rectf, radius, radius, paint)
    }

    override fun getIntrinsicHeight(): Int {
        return mBitmap!!.height
    }

    override fun getIntrinsicWidth(): Int {
        return mBitmap!!.width
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
