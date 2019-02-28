package com.example.wanghui.kotlin.ui.view.fall

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*

/**
 * Created by wanghui on 2019/2/21.
 */
class FallView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0): View(context, attributeSet, defStyleAttr) {
    val fallStyles = mutableListOf<FallEntity>()
    val drawFalls = mutableListOf<FallEntity>()
    val intervalTime: Long = 5 //时间间隔（ms）
    var fallTime = 8 * 1000
    var density = 20
    var addFallRandom = 0.0
    var lastTime = System.currentTimeMillis()
    var timeForLastMake = 3 * 1000
    init {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (drawFalls.isNotEmpty()){
            val hasUseTime = System.currentTimeMillis() - lastTime
            Log.d("wh", "wh------------ $hasUseTime")
            drawFalls.forEach {
                it.drawObject(canvas, hasUseTime < fallTime - timeForLastMake)
            }
            postDelayed({
                if (hasUseTime < fallTime - timeForLastMake
                        && drawFalls.size < 20
                        && Math.random() <= addFallRandom ){
                    drawFalls.add(fallStyles[(Math.random() * fallStyles.size).toInt()].copy())
                }
                invalidate()
            }, intervalTime)
        }
    }

    fun show(fallStyles: MutableList<FallEntity>, density: Int, fallTime: Int){
        this.fallStyles.addAll(fallStyles)
        this.density = density
        this.fallTime = fallTime
        this.lastTime = System.currentTimeMillis()
        this.addFallRandom = 0.035
        val fall = fallStyles[(Math.random() * fallStyles.size).toInt()].copy()
        drawFalls.add(fall)
        invalidate()
    }

    class FallEntity(val beginY: Int = 0,  //动画起始坐标  px
                     val endY: Int = 1920, //动画结束坐标  px
                     val disappearY: Int = 1500, //执行渐隐动画坐标  px
                     val image: Bitmap? = null,  //图片资源
                     val speedRange: String = "0-12", //速度范围 px/s
                     val viewWidth: Int = 0  //view的宽度  px
    ){
        val paint = Paint()
        val random = Random()
        var currentY: Float = beginY.toFloat()
        var currentX: Float = initCurrentX()
        val maxAlpha = 255
        var currentAlpha = maxAlpha
        var speedYMax : Int = 0
        var speedYMin : Int = 0
        var randomSpeedY : Int = 0
        var speedX: Int = speedYMin
        var lastTime = System.currentTimeMillis()

        init {
            val speeds = speedRange.split("-")
            if (speeds.size == 2){
                speedYMin = speeds[0].toInt()
                speedYMax = speeds[1].toInt()
                speedX = (random.nextInt(speedYMax/2) * (Math.random() * 2 - 1)).toInt()
                randomSpeedY = Math.min(speedYMax - speedYMin, 1)
            }
        }

        private fun initCurrentX(): Float = if (viewWidth > 0)random.nextInt(viewWidth - 60) + 60.toFloat() else 0f//屏幕的宽度


        fun drawObject(canvas: Canvas, canReset: Boolean){
            //画图并更换下一次绘制的数值
            if (image == null) return
            if (currentAlpha > 0){
                paint.alpha = currentAlpha
                canvas.drawBitmap(image, currentX, currentY, paint)
                calculateData()
            }else if (canReset){
                currentY = beginY.toFloat()
                currentX = initCurrentX()
                currentAlpha = maxAlpha
            }
        }

        private fun calculateData(){
            val intervalTime = System.currentTimeMillis() - lastTime
            lastTime = System.currentTimeMillis()
            currentY += (random.nextInt(speedYMax - speedYMin) + speedYMin) * intervalTime/1000f
            currentX += speedX * intervalTime/1000f
            if (currentY > disappearY){
                currentAlpha = maxAlpha - (maxAlpha * (currentY - disappearY)/(endY - disappearY)).toInt()
            }
        }

        fun copy(): FallEntity{
            return FallEntity(beginY, endY, disappearY, image, speedRange, viewWidth)
        }


//        class Builder{
//            private var beginY: Int = 0
//            private var endY : Int = 1920
//            private var disappearY: Int = 1500
//            private var image: Bitmap? = null
//            private var time: Int = 5
//            private var speedRange: String = "0-12"
//            private var density: Int = 20
//
//            fun setBeginY(value: Int): Builder{
//                this.beginY = value
//                return this
//            }
//
//            fun setEndY(value: Int): Builder{
//                this.endY = value
//                return this
//            }
//
//            fun setDisappearY(value: Int): Builder{
//                this.disappearY = value
//                return this
//            }
//
//            fun setTime(value: Int): Builder{
//                this.time = value
//                return this
//            }
//
//            fun setDensity(value: Int): Builder{
//                this.density = value
//                return this
//            }
//
//            fun setImage(value: Bitmap): Builder{
//                this.image= value
//                return this
//            }
//
//            fun setSpeedRange(value: String): Builder{
//                this.speedRange = value
//                return this
//            }
//
//            fun build(): FallEntity{
//                return FallEntity(beginY = beginY, endY = endY, disappearY = disappearY, image = image,
//                        time = time, speedRange = speedRange, density = density)
//            }
//        }
    }

}