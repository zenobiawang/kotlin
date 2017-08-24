package com.example.wanghui.kotlin.ui.view.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/8/24.
 * canvas api
 */
class CanvasViews(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : View(context, attrs, defStyleAttr, defStyleRes) {
    val paint = Paint()
    constructor(context: Context):this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    init {
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = resources.getColor(R.color.little_yellow)
        canvas.drawCircle(100f, 100f, 100f, paint)
        val save1 = canvas.save()
        canvas.translate(50f, 50f)
        paint.color = resources.getColor(R.color.flower_blue)
        canvas.drawCircle(100f, 100f, 100f, paint)
//        canvas.restoreToCount(save1)
        canvas.scale(2f,2f)
        val save2 = canvas.save()
        paint.color = resources.getColor(R.color.pink)
        canvas.drawCircle(100f, 100f, 100f, paint)
        canvas.restoreToCount(save1)
        paint.color = Color.BLACK
        paint.textSize = 30f
        (0..9).map {
            canvas.rotate(36f, 250f, 250f)
            canvas.drawText(it.toString(), 50f, 250f, paint)
        }


    }
}