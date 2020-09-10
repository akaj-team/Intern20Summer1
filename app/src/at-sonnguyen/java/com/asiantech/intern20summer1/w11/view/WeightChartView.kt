package com.asiantech.intern20summer1.w11.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.asiantech.intern20summer1.R
import kotlin.random.Random

class WeightChartView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private lateinit var paint: Paint
    private lateinit var paintText: Paint
    private lateinit var dashLinePaint: Paint
    private lateinit var path: Path

    companion object {
        private const val PAINT_STROKE_WIDTH = 5f
        private const val START_POINT = 20f
        private const val START_DIV_POINT = 0f
        private const val END_DIV_POINT = 35f
        private const val STEP_NUMBER = 13
        private const val TEXT_SIZE = 25f
        private const val MARGIN_RIGHT = 70f
        private const val MARGIN_BOTTOM = 45f
        private const val DASH_LINE_STROKE_WIDTH = 2f
        private const val DASH_PATH_EFFECT = 5f
        private const val DASH_PATH_PHASE = 10f
        private const val MAX_RANDOM = 8
        private const val MIN_RANDOM = 5
        private const val MULTIPLIER = 10

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initPaint()
        initPaintText()
        initDashLinePaint()
        drawAxis(canvas)
        drawChart(canvas)
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
        canvas?.drawText(
            context.getString(R.string.w11_weight_text),
            END_DIV_POINT,
            START_POINT,
            paintText
        )
        canvas?.drawText(
            context.getString(R.string.w11_month_text),
            width - MARGIN_RIGHT,
            height - MARGIN_BOTTOM,
            paintText
        )
        drawStrike(canvas)
        drawDivAxis(canvas)
    }

    private fun drawStrike(canvas: Canvas?) {
        canvas?.drawLine(
            START_POINT,
            START_POINT,
            START_DIV_POINT,
            START_POINT + START_POINT,
            paint
        )
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
            canvas?.drawText(
                context.getString(R.string.w11_chart_root_text),
                START_DIV_POINT,
                height - START_POINT,
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

    private fun drawChart(canvas: Canvas?) {
        var startX = START_POINT
        var startY = height - START_POINT
        for (i in 1 until STEP_NUMBER) {
            val number = Random.nextInt(MAX_RANDOM) + MIN_RANDOM
            path.moveTo(
                START_DIV_POINT,
                (height - START_POINT) / STEP_NUMBER * (STEP_NUMBER - number)
            )
            path.quadTo(
                START_DIV_POINT,
                (height - START_POINT) / STEP_NUMBER * (STEP_NUMBER - number),
                width.toFloat() / STEP_NUMBER * i,
                (height - START_POINT) / STEP_NUMBER * (STEP_NUMBER - number)
            )
            canvas?.drawPath(path, dashLinePaint)
            path.moveTo(width.toFloat() / STEP_NUMBER * i, height - START_POINT * 2)
            path.quadTo(
                width.toFloat() / STEP_NUMBER * i,
                height.toFloat() - START_POINT * 2,
                width.toFloat() / STEP_NUMBER * i,
                (height - START_POINT) / STEP_NUMBER * (STEP_NUMBER - number)
            )
            canvas?.drawPath(path, dashLinePaint)
            canvas?.drawCircle(
                width.toFloat() / STEP_NUMBER * i,
                (height.toFloat() - START_POINT) / STEP_NUMBER * (STEP_NUMBER - number),
                10f,
                paint
            )
            canvas?.drawLine(
                startX,
                startY,
                width.toFloat() / STEP_NUMBER * i,
                (height - START_POINT) / STEP_NUMBER * (STEP_NUMBER - number),
                paint
            )
            startX = width.toFloat() / STEP_NUMBER * i
            startY = (height - START_POINT) / STEP_NUMBER * (STEP_NUMBER - number)
            val weight = number * MULTIPLIER
            canvas?.drawText(
                weight.toString(),
                width.toFloat() / STEP_NUMBER * i + START_POINT,
                (height - START_POINT) / STEP_NUMBER * (STEP_NUMBER - number) - START_POINT,
                paintText
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

    private fun initDashLinePaint() {
        dashLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        path = Path()
        dashLinePaint.color = Color.BLACK
        dashLinePaint.style = Paint.Style.STROKE
        dashLinePaint.strokeWidth = DASH_LINE_STROKE_WIDTH
        dashLinePaint.pathEffect =
            DashPathEffect(floatArrayOf(DASH_PATH_EFFECT, DASH_PATH_EFFECT), DASH_PATH_PHASE)
    }
}
