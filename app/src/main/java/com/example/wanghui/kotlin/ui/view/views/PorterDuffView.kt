package com.example.wanghui.kotlin.ui.view.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/8/21.
 */
class PorterDuffView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : View(context, attrs, defStyleAttr, defStyleRes) {
    val paint = Paint()
    var bitmap : Bitmap? = null
    var bitmaps : Bitmap? = null
    var bg : BitmapShader
    val modes : Array<Xfermode> = arrayOf(
            PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            PorterDuffXfermode(PorterDuff.Mode.SRC),
            PorterDuffXfermode(PorterDuff.Mode.DST),
            PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            PorterDuffXfermode(PorterDuff.Mode.XOR),
            PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            PorterDuffXfermode(PorterDuff.Mode.SCREEN))
    val labels = arrayOf(
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen")

    constructor(context: Context):this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    init {
        paint.isAntiAlias = true
        bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = 0xEEFFCC44.toInt()
        canvas.drawCircle(75f, 75f, 75f, paint)
        bitmaps = Bitmap.createBitmap(150, 150, Bitmap.Config.RGB_565)
        val canvass = Canvas(bitmap)
        paint.color = 0xEE66AAFF.toInt()
        canvass.drawRect(50f, 50f, 200f, 200f, paint)

        // make a ckeckerboard pattern
        val bm = Bitmap.createBitmap(intArrayOf(0xFFFFFFFF.toInt(), 0xFFCCCCCC.toInt(), 0xFFCCCCCC.toInt(), 0xFFFFFFFF.toInt()), 2, 2,
                Bitmap.Config.RGB_565)
        bg = BitmapShader(bm,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT)
        val m = Matrix()
        m.setScale(6f, 6f)
        bg.setLocalMatrix(m)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        paint.shader = bg
        paint.isFilterBitmap = false
        var x = 0f
        var y = 0f
        var raw = 1
        for (i in 0..labels.size-1){
            // draw the src/dst example into our offscreen bitmap
            val sc = canvas.saveLayer(x, y, x + 200, y + 200, null,
                    Canvas.MATRIX_SAVE_FLAG or
                            Canvas.CLIP_SAVE_FLAG or
                            Canvas.HAS_ALPHA_LAYER_SAVE_FLAG or
                            Canvas.FULL_COLOR_LAYER_SAVE_FLAG or
                            Canvas.CLIP_TO_LAYER_SAVE_FLAG)

            canvas.translate(x, y)
            canvas.drawBitmap(bitmap, 0f, 0f, paint)
            paint.xfermode = modes[i]
            canvas.drawBitmap(bitmaps, 0f, 0f, paint)
            paint.xfermode = null
            canvas.restoreToCount(sc)

            if (raw == 4){
                raw = 1
                x = 0f
                y += 200 + 50
            }else{
                raw ++
                x += 200 + 50
            }
        }
    }
}

