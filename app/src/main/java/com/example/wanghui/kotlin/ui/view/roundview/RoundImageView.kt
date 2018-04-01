package com.example.wanghui.kotlin.ui.view.roundview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ImageView
import com.example.wanghui.kotlin.R
import android.util.TypedValue



/**
 * Created by wanghui on 2017/7/26.
 * 圆角图片
 */
class RoundImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ImageView(context, attrs, defStyleAttr) {
    val STORAGE_INSTANCE = "storage_instance"
    val STORAGE_TYPE = "storage_type"
    val STORAGE_RADIUS = "storage_radius"
    val CIRCLE : Int = 1
    val ROUND : Int = 2
    var type : Int = 0
    var radius : Int = 0
    var roundRect : RectF? = null
    val bitmapPaint = Paint()

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.RoundImageView).apply {
            type = getInt(R.styleable.RoundImageView_type, 1)
            radius = getDimensionPixelSize(
                    R.styleable.RoundImageView_radius, TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics).toInt())// 默认为10dp
        }.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (type == CIRCLE){
            val width = Math.min(measuredHeight, measuredWidth)
            setMeasuredDimension(width, width)
            radius = width/2
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable == null){
            return
        }

        bitmapPaint.isAntiAlias = true
        bitmapPaint.shader = setUpShader()

        when(type){
            CIRCLE -> canvas.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), bitmapPaint)
            ROUND -> canvas.drawRoundRect(roundRect, radius.toFloat(), radius.toFloat(), bitmapPaint)
        }
    }

    private fun setUpShader() : BitmapShader{
        val bitmap : Bitmap
        if (drawable is BitmapDrawable){
            bitmap = (drawable as BitmapDrawable).bitmap
        }else{
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(canvas)
        }

        val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val scale : Float
        scale = when(type){
            CIRCLE -> measuredHeight * 1.0f/Math.min(bitmap.height, bitmap.width)
            else -> Math.max(measuredWidth * 1.0f/bitmap.width, measuredHeight * 1.0f/bitmap.height)
        }
        val matrix = Matrix().apply { setScale(scale, scale) }
        shader.setLocalMatrix(matrix)
        return shader
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (type == ROUND){
            roundRect = RectF(0F, 0F, width.toFloat(), height.toFloat())
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable(STORAGE_INSTANCE, super.onSaveInstanceState())
            putInt(STORAGE_TYPE, type)
            putInt(STORAGE_RADIUS, radius)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle){
            super.onRestoreInstanceState(state.getParcelable(STORAGE_INSTANCE))
            type = state.getInt(STORAGE_TYPE)
            radius = state.getInt(STORAGE_RADIUS)
        }else{
            super.onRestoreInstanceState(state)
        }
    }


}