package com.asiantech.intern20summer1.week11.customs

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.asiantech.intern20summer1.week11.models.Weight
import kotlin.random.Random

class WeightChartCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val NUMBER_DIVISOR_HEIGHT = 150
        private const val LIMIT_OF_LIST = 12
        private const val RANDOM_NUMBER_FROM = 50
        private const val RANDOM_NUMBER_UTIL = 120
        private const val NUMBER_MINUS_WIDTH = 10
        private const val NUMBER_DIVISOR_WIDTH = 15f
    }

    private var widths = 0f
    private var heights = 0f
    private var weightData = mutableListOf<Weight>()
    private var scaleAxisY = 0f
    private var path = Path()
    private var moveX = 0f
    private var startMove = 0f
    private var stopMove = 0f
    private var distanceMove = 0f
    private var isAddData = false
    private var start = 0f
    private var oldDistance = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isAddData) {
            addData()
            isAddData = true
        }
        widths = width.toFloat()
        heights = height.toFloat()
        scaleAxisY = heights / NUMBER_DIVISOR_HEIGHT
        drawNumberWeight(canvas)
        drawAxisLine(canvas)
        drawText(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                addData()
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

    private fun addData() {
        weightData.apply {
            for (i in 1..LIMIT_OF_LIST) {
                weightData.add(Weight(i, Random.nextInt(RANDOM_NUMBER_FROM, RANDOM_NUMBER_UTIL)))
            }
        }
    }

    private fun drawNumberWeight(canvas: Canvas?) {
        val rangeWidth = widths / NUMBER_MINUS_WIDTH - NUMBER_DIVISOR_WIDTH
        start = -distanceMove - oldDistance
        weightData.forEachIndexed { index, it ->
            if (index < weightData.size - 1) {
                val startX = start
                val startY = heights - it.weight * scaleAxisY
                val endX = start + rangeWidth
                val endY = heights - (weightData[index + 1].weight) * scaleAxisY
                canvas?.drawText("${it.weight}", start, startY - 30f, textPaint)
                canvas?.drawLine(startX, startY, endX, endY, linePaint)
                canvas?.drawCircle(startX, startY, 12f, pointPaint)
                canvas?.drawText("${it.month}", start + 20f, heights - 20f, textPaint)
                path.moveTo(startX, startY)
                path.lineTo(start, heights)
                canvas?.drawPath(path, dashedLinePaint)
                start += rangeWidth
            }
        }
    }

    private fun drawAxisLine(canvas: Canvas) {
        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), axisLinePaint)
    }

    private fun drawText(canvas: Canvas) {
        canvas.drawText("Kg", 20f, 30f, textPaint)
    }

    private val dashedLinePaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 5f
        pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)

    }
    private val linePaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLACK
        strokeWidth = 7f
        style = Paint.Style.FILL
    }

    private val pointPaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.RED
    }

    private val textPaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLUE
        textSize = 30f
    }

    private val axisLinePaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.RED
        strokeWidth = 10f
    }
}
