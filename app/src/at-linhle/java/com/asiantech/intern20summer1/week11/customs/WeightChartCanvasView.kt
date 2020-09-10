package com.asiantech.intern20summer1.week11.customs

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week11.models.Weight

class WeightChartCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val LIMIT_OF_LIST = 13
        private const val RANDOM_NUMBER_FROM = 50
        private const val RANDOM_NUMBER_UTIL = 120
        private const val NUMBER_MINUS_WIDTH = 12
        private const val NUMBER_MINUS_WIDTH_SIZE_IN = 11
        private const val NUMBER_MINUS_HEIGHT = 15
        private const val NUMBER_STROKE_WIDTH = 4f
        private const val NUMBER_PIVOT_STROKE_WIDTH = 6f
        private const val NUMBER_LINE_DASH_STROKE_WIDTH = 2f
        private const val NUMBER_OF_TEXT_SIZE = 18f
    }

    private var widths = 0f
    private var heights = 0f
    private var dX = 0f
    private var dY = 0f
    private var sizeInt = 0
    private val path = Path()
    private var isAddData = false
    private var weightList: MutableList<Weight> = mutableListOf()
    private var moveX = 0f
    private var startMove = 0f
    private var stopMove = 0f
    private var distanceMove = 0f
    private var oldDistance = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isAddData) {
            initData()
            isAddData = true
        }
        widths = width.toFloat()
        heights = height.toFloat()
        dX = (width / NUMBER_MINUS_WIDTH).toFloat()
        dY = (height / NUMBER_MINUS_HEIGHT).toFloat()
        sizeInt = (width / NUMBER_MINUS_WIDTH_SIZE_IN)
        drawWeight(canvas)
        drawGraph(canvas)
        drawData(canvas)
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
                path.reset()
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                oldDistance += distanceMove
            }
        }
        return true
    }

    private fun drawGraph(canvas: Canvas) {
        canvas.drawLine(0f, dY * 14, width.toFloat(), dY * 14, paintPivot)
        canvas.drawLine(0f, dY, 0f, dY * 14, paintPivot)
    }

    private fun drawData(canvas: Canvas) {
        var start = -distanceMove - oldDistance
        weightList.forEachIndexed { index, i ->
            if (index < weightList.size - 1) {
                val startX = start
                val startY = ((i.weight - 140f) / (-10f)) * dY
                val endX = startX + dX
                val endY = ((weightList[index + 1].weight - 140f) / (-10f)) * dY
                canvas.drawText(i.weight.toString(), startX, startY - 50, paintText)
                canvas.drawText(i.month.toString(), startX, dY * 14.5f, paintText)
                canvas.drawLine(startX, startY, endX, endY, paintLine)
                canvas.drawCircle(startX, startY, 10f, paintDot)
                path.moveTo(startX, startY)
                path.lineTo(start, dY * 14)
                canvas.drawPath(path, paintLineDash)
                path.moveTo(startX, startY)
                path.lineTo(0f, startY)
                canvas.drawPath(path, paintLineDash)
                start += dX
            }
        }
    }

    private fun drawWeight(canvas: Canvas) {
        canvas.drawText(
            context.getString(R.string.weight_chart_canvas_weight_text),
            0f,
            dY,
            paintText
        )
    }

    private fun initData() {
        for (i in 1..LIMIT_OF_LIST) {
            weightList.add(Weight(i, (RANDOM_NUMBER_FROM..RANDOM_NUMBER_UTIL).random()))
        }
    }

    private val paintLine = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = NUMBER_STROKE_WIDTH
        color = Color.BLACK
    }

    private val paintText = Paint().apply {
        textSize = NUMBER_OF_TEXT_SIZE
        strokeWidth = NUMBER_STROKE_WIDTH
        color = Color.BLACK
    }

    private val paintPivot = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = NUMBER_PIVOT_STROKE_WIDTH
        color = Color.BLACK
    }

    private val paintDot = Paint().apply {
        style = Paint.Style.FILL
    }

    private val paintLineDash = Paint().apply {
        Paint.DITHER_FLAG
        strokeWidth = NUMBER_LINE_DASH_STROKE_WIDTH
        style = Paint.Style.STROKE
        setARGB(255, 0, 0, 0)
        pathEffect = DashPathEffect(floatArrayOf(10f, 18f, 10f, 18f), 0f)
    }
}
