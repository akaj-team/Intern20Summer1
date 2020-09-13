package com.asiantech.intern20summer1.week11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.random.Random

class WeightDataView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    companion object {
        private const val STROKE_WIDTH = 4f
        private const val LINE_COLOR = Color.BLACK
        private const val DOT_COLOR = Color.BLUE
        private const val MAX_WEIGHT = 120
        private const val MIN_WEIGHT = 50
        private const val MONTH = 12
    }

    private var sizeX = 0f
    private var sizeY = 0f
    private var d0 = 0f
    private var weightData = arrayListOf<Int>()
    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dashPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        initSize()
        initData()
        initPaint()
        drawAxis(canvas)
        drawAxisText(canvas)
        drawDot(canvas)
    }

    private fun initSize() {
        sizeX = width / 8.toFloat()
        sizeY = height / 12.toFloat()
    }

    private fun initPaint() {
        linePaint.color = LINE_COLOR
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = STROKE_WIDTH
        dotPaint.color = DOT_COLOR
        dashPaint.color = LINE_COLOR
        dashPaint.style = Paint.Style.STROKE
        dashPaint.strokeWidth = STROKE_WIDTH
        dashPaint.pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 10f)
    }

    private fun initData() {
        for (i in 0..13) {
            weightData.add(Random.nextInt(MIN_WEIGHT, MAX_WEIGHT))
            Log.d("aaa", weightData[i].toString())
        }
    }

    private fun drawAxis(canvas: Canvas) {
        canvas.drawLine(d0 + 50f, d0 + 50f, d0 + 50f, height - sizeY * 4, linePaint)
        canvas.drawLine(d0 + 50f, height - sizeY * 4, width - d0, height - sizeY * 4, linePaint)
    }

    private fun drawAxisText(canvas: Canvas) {
        canvas.drawText("Weight", d0 + 20f, d0 + 30f, textPaint)
        canvas.drawText("Month", d0 + 50f, height - sizeY * 4 + 30f, textPaint)

        for (i in 1..MONTH) {
            canvas.drawText(i.toString(), sizeX * i + 50f, height - sizeY * 4 + 30f, textPaint)
        }
        for (i in 5..13) {
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
        val weightUnit = dy / (MAX_WEIGHT + 10 - MIN_WEIGHT)
        for (i in 0..12) {
            canvas.drawCircle(
                sizeX * (i + 1) + 50f,
                dy - (weightData[i] - MIN_WEIGHT) * weightUnit,
                10f,
                dotPaint
            )

            canvas.drawText(
                weightData[i].toString(),
                sizeX * (i + 1) + 40f,
                dy - (weightData[i] - MIN_WEIGHT) * weightUnit - 15f,
                textPaint
            )
            if (i > 0) {
                canvas.drawLine(
                    sizeX * (i + 1) + 50f,
                    dy - (weightData[i] - MIN_WEIGHT) * weightUnit,
                    sizeX * (i) + 50f,
                    dy - (weightData[i-1] - MIN_WEIGHT) * weightUnit,
                    linePaint
                )
            }
        }
    }
}
