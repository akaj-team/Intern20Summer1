package com.asiantech.intern20summer1.week11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
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

    private var size = 0f
    private var d0 = 0f
    private var height = 0f
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
        size = width / 8.toFloat()
        height = ((MAX_WEIGHT - MIN_WEIGHT) * 10).toFloat()
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
        for (i in 0..MONTH) {
            weightData.add(Random.nextInt(MIN_WEIGHT, MAX_WEIGHT))
        }
    }

    private fun drawAxis(canvas: Canvas) {
        canvas.drawLine(d0 + 30f, d0 + 30f, d0 + 30f, height - 30f, linePaint)
        canvas.drawLine(width - d0, height - 30f, width - d0, height - d0, linePaint)
    }

    private fun drawAxisText(canvas: Canvas) {
        canvas.drawText("Weight", d0, d0, textPaint)
        canvas.drawText("Month", d0, height - d0, textPaint)

        for (i in 0..MONTH) {
            canvas.drawText(i.toString(), size * i, height - d0, textPaint)
        }
        for (i in 0..7) {
            canvas.drawText(
                (i * 10 + 50).toString(),
                d0,
                height - (i + 50) * 10,
                textPaint
            )
        }
    }

    private fun drawDot(canvas: Canvas) {
        weightData.forEachIndexed { index, i ->
            if (index < weightData.size - 1) {
                canvas.drawCircle(size * i, (index - 50) * 10.toFloat(), 10f, dotPaint)
                canvas.drawText(index.toString(), size * i, ((index - 50) * 10 - 10f), linePaint)
                canvas.drawLine(
                    size * i,
                    (index - 50) * 10 - 10f,
                    d0 + 30f,
                    (index - 50) * 10 - 10f,
                    dashPaint
                )
                canvas.drawLine(size * i, (index - 50) * 10 - 10f, size * i, height, dashPaint)
            }
        }
    }
}
