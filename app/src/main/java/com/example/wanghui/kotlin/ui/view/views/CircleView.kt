package com.example.wanghui.kotlin.ui.view.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.wanghui.kotlin.R
import org.jetbrains.anko.padding

/**
 * Created by wanghui on 2017/8/21.
 * 圆
 */
class CircleView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {
    val paint : Paint = Paint()

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        paint.color = resources.getColor(R.color.red)
        paint.isAntiAlias = true   //消除锯齿
//        paint.colorFilter = LightingColorFilter()    图像处理
        paint.style = Paint.Style.FILL_AND_STROKE
//        setLayerType(LAYER_TYPE_SOFTWARE, paint)  //阴影处理一定要关闭硬件加速
        paint.setShadowLayer(20f, 20f, 20f, resources.getColor(android.R.color.black))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width : Int = MeasureSpec.getSize(widthMeasureSpec)
        val height : Int = MeasureSpec.getSize(heightMeasureSpec)
        val size = Math.min(width, height)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        val radius = width/2f
        canvas.drawCircle(radius, radius, radius - paddingLeft, paint)
    }
}