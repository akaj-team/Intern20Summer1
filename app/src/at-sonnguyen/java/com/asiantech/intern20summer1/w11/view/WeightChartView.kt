package com.asiantech.intern20summer1.w11.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class WeightChartView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private lateinit var paint: Paint
    private lateinit var paintText: Paint

    companion object {
        private const val PAINT_STROKE_WIDTH = 5f
        private const val START_POINT = 20f
        private const val START_DIV_POINT = 0f
        private const val END_DIV_POINT = 35f
        private const val STEP_NUMBER = 13
        private const val TEXT_SIZE = 25f
        private const val MARGIN_RIGHT = 70f
        private const val MARGIN_BOTTOM = 45f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initPaint()
        initPaintText()
        drawAxis(canvas)
    }

    private fun drawAxis(canvas: Canvas?) {
        canvas?.drawLine(
            START_POINT,
            START_POINT,
            START_POINT,
            height - START_POINT,
            paint
        )
        canvas?.drawLine(
            START_POINT,
            height - START_POINT,
            width - START_POINT,
            height - START_POINT,
            paint
        )
        canvas?.drawText("Weight", END_DIV_POINT, START_POINT, paintText)
        canvas?.drawText("Month",width- MARGIN_RIGHT,height- MARGIN_BOTTOM, paintText)
        drawStrike(canvas)
        drawDivAxis(canvas)
    }

    private fun drawStrike(canvas: Canvas?) {
        canvas?.drawLine(START_POINT, START_POINT, START_DIV_POINT, START_POINT + START_POINT, paint)
        canvas?.drawLine(START_POINT, START_POINT, START_POINT * 2, START_POINT * 2, paint)
        canvas?.drawLine(
            width - START_POINT,
            height - START_POINT,
            width - START_POINT * 2,
            height - START_POINT * 2,
            paint
        )
        canvas?.drawLine(
            width - START_POINT,
            height - START_POINT,
            width - START_POINT * 2,
            height.toFloat(),
            paint
        )
    }

    private fun drawDivAxis(canvas: Canvas?) {
        for (i in 1 until STEP_NUMBER) {
            canvas?.drawLine(
                width.toFloat() / STEP_NUMBER * i,
                height - START_POINT * 2,
                width.toFloat() / STEP_NUMBER * i,
                height.toFloat(),
                paint
            )
            canvas?.drawText(
                i.toString(),
                width.toFloat() / STEP_NUMBER * i - END_DIV_POINT,
                height - START_POINT * 2,
                paintText
            )
        }
        for (i in 1 until STEP_NUMBER) {
            canvas?.drawLine(
                START_DIV_POINT,
                (height.toFloat() - START_POINT) / STEP_NUMBER * i,
                START_POINT * 2,
                (height.toFloat() - START_POINT) / STEP_NUMBER * i,
                paint
            )
        }
    }

    private fun initPaint() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        paint.strokeWidth = PAINT_STROKE_WIDTH
    }

    private fun initPaintText() {
        paintText = Paint(Paint.ANTI_ALIAS_FLAG)
        paintText.color = Color.BLACK
        paintText.textSize = TEXT_SIZE
    }
}
