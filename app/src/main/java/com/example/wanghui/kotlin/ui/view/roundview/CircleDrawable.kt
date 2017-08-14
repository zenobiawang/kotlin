package com.example.wanghui.kotlin.ui.view.roundview

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 * Created by wanghui on 2017/7/27.
 * 圆形drawable
 */
class CircleDrawable(var bitmap: Bitmap) : Drawable() {
    var paint : Paint = Paint()

    init {
        val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.apply {
            this.shader = shader
            isAntiAlias = true
        }
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
    }

    override fun draw(canvas: Canvas) {
        val radius = Math.min(bitmap.height, bitmap.width)/2
        canvas.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), paint)
    }

    override fun getIntrinsicHeight(): Int {
        return Math.min(bitmap.height, bitmap.width)
    }

    override fun getIntrinsicWidth(): Int {
        return Math.min(bitmap.height, bitmap.width)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

}
