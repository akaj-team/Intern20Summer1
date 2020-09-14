package com.asiantech.intern20summer1.week11

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import kotlin.random.Random

class WeightDataView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    companion object {
        private const val STROKE_WIDTH = 4f
        private const val LINE_COLOR = Color.BLACK
        private const val DOT_COLOR = Color.BLUE
        private const val MAX_WEIGHT = 120
        private const val MIN_WEIGHT = 50
        private const val MONTH: Int = 12
    }

    private var sizeX = 0f
    private var sizeY = 0f
    private var d0 = 0f
    private var weightData = arrayListOf<Int>()
    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dashPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var moveX = 0f
    private var startMove = 0f
    private var stopMove = 0f
    private var distanceMove = 0f
    private var oldDistance = 0f
    private var path = Path()
    private var isAddData = false

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        initSize()
        if (!isAddData) {
            initData()
            isAddData = true
        }
        initPaint()
        drawAxis(canvas)
        drawAxisText(canvas)
        drawDot(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initData()
                startMove = x
            }
            MotionEvent.ACTION_MOVE -> {
                moveX = x
                stopMove = x
                distanceMove = startMove - stopMove
                invalidate()
                path.reset()
            }
            MotionEvent.ACTION_UP -> {
                oldDistance += distanceMove
            }
        }
        return true
    }

    private fun initSize() {
        sizeX = width / (MONTH + 1).toFloat()
        sizeY = height / MONTH.toFloat()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun initPaint() {
        linePaint.color = LINE_COLOR
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = STROKE_WIDTH
        dotPaint.color = DOT_COLOR
        dashPaint.color = LINE_COLOR
        setLayerPaint(dashPaint)
        dashPaint.style = Paint.Style.STROKE
        dashPaint.strokeWidth = STROKE_WIDTH
        dashPaint.pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    private fun initData() {
        for (i in 0..MONTH) {
            weightData.add(Random.nextInt(MIN_WEIGHT, MAX_WEIGHT))
        }
    }

    private fun drawAxis(canvas: Canvas) {
        canvas.drawLine(d0 + 50f, d0 + 50f, d0 + 50f, height - sizeY * 4, linePaint)
        canvas.drawLine(d0 + 50f, height - sizeY * 4, width - d0, height - sizeY * 4, linePaint)
    }

    private fun drawAxisText(canvas: Canvas) {
        canvas.drawText("Weight", d0 + 20f, d0 + 30f, textPaint)
        canvas.drawText("Month", d0 + 50f, height - sizeY * 4 + 30f, textPaint)

        for (i in MIN_WEIGHT / 10..(MAX_WEIGHT + 1) / 10) {
            canvas.drawText(
                (i * 10).toString(),
                d0,
                height - sizeY * (i - 1),
                textPaint
            )
        }
    }

    private fun drawDot(canvas: Canvas) {
        val dy = height - sizeY * 4
        var dx = -distanceMove - oldDistance + sizeX + 50f
        val weightUnit = dy / (MAX_WEIGHT + 10 - MIN_WEIGHT)
        for (i in 0 until MONTH) {
            val startX = dx
            val startY = dy - (weightData[i] - MIN_WEIGHT) * weightUnit
            val endX = dx + sizeX
            val endY = dy - (weightData[i + 1] - MIN_WEIGHT) * weightUnit
            canvas.drawCircle(
                startX,
                startY,
                10f,
                dotPaint
            )
            canvas.drawText(
                weightData[i].toString(),
                startX - 8f,
                startY - 15f,
                textPaint
            )
            canvas.drawText(i.toString(), startX, dy + 30f, textPaint)
            path.moveTo(startX, startY)
            path.lineTo(d0 + 50f, startY)
            canvas.drawPath(path, dashPaint)
            path.moveTo(startX, startY)
            path.lineTo(startX, dy)
            canvas.drawPath(path, dashPaint)
            if (i != MONTH - 1) {
                canvas.drawLine(
                    startX,
                    startY,
                    endX,
                    endY,
                    linePaint
                )
            }
            dx += sizeX
        }
    }
}
