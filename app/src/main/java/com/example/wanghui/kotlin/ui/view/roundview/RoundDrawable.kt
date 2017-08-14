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
    var rect : RectF? = null

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        rect = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    override fun draw(canvas: Canvas) {
        val bitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvasP = Canvas(bitmap)
        val matrix = Matrix()
        canvasP.drawRoundRect(rect, radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvasP.drawBitmap(bitmap, canvas.matrix, paint)
        paint.xfermode = null
        canvas.drawBitmap(bitmap, matrix, paint)

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