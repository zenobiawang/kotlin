package com.example.wanghui.kotlin.ui.view.roundview

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 * Created by wanghui on 2017/8/1.
 */
class RoundDrawable(bitmap: Bitmap) : Drawable() {
    var mBitmap : Bitmap? = null
    var paint : Paint? = null
    var radius = 30f
    var rect : RectF? = null
    init {
        mBitmap = bitmap
        paint = Paint()
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        rect = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    override fun draw(canvas: Canvas?) {
        val bitmap = Bitmap.createBitmap(mBitmap!!.width, mBitmap!!.height, Bitmap.Config.ARGB_8888)
        val canvasP = Canvas(bitmap)
        val matrix = Matrix()
        canvasP!!.drawRoundRect(rect, radius, radius, paint)
        paint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvasP!!.drawBitmap(mBitmap, matrix, paint)
        paint!!.xfermode = null
        canvas!!.drawBitmap(bitmap, null, RectF(), paint)
    }

    override fun setAlpha(alpha: Int) {
        paint!!.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint!!.colorFilter = colorFilter
    }

    override fun getIntrinsicWidth(): Int {
        return mBitmap!!.width
    }

}