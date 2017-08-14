package com.example.wanghui.kotlin.ui.view.roundview

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 * Created by wanghui on 2017/8/1.
 * 圆角图片
 */
class RoundDrawable(var bitmap: Bitmap) : Drawable() {
    var paint : Paint = Paint()
    var radius = 30f
    lateinit var rect : RectF

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        rect = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    override fun draw(canvas: Canvas) {
        val bitmapP = Bitmap.createBitmap((rect.right - rect.left).toInt(), (rect.bottom - rect.top).toInt(), Bitmap.Config.ARGB_8888)
        val canvasP = Canvas(bitmapP)
        val matrix = Matrix()
        canvasP.drawRoundRect(rect, radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvasP.drawBitmap(bitmap, matrix, paint)
        paint.xfermode = null
        canvas.drawBitmap(bitmapP, matrix, paint)

//        canvas.drawRoundRect() 两种实现方式，并没有找到配合scaleType的方式
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

    override fun getIntrinsicWidth(): Int {
        return bitmap.width
    }

}