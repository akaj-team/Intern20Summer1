package com.asiantech.intern20summer1.w11.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class WeightChartView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private lateinit var paint: Paint

    companion object {
        private const val PAINT_STROKE_WIDTH = 5f
        private const val START_POINT = 20f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initPaint()
        drawAxis(canvas)
    }

    private fun drawAxis(canvas: Canvas?) {
        canvas?.drawLine(
            START_POINT,
            START_POINT,
            START_POINT,
            height.toFloat() - START_POINT,
            paint
        )
        canvas?.drawLine(
            START_POINT,
            height.toFloat() - START_POINT,
            width.toFloat() - START_POINT,
            height.toFloat() - START_POINT,
            paint
        )
    }

    private fun initPaint() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        paint.strokeWidth = PAINT_STROKE_WIDTH
    }
}