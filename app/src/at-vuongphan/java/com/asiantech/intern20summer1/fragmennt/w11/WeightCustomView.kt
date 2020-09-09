package com.asiantech.intern20summer1.fragmennt.w11

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.data.w11.Weight
import kotlin.random.Random

class WeightCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
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

    companion object {
        private const val RANDOM_START = 50
        private const val RANDOM_END = 120
        private const val DATA_STEP_END = 12
        private const val DIV_HEIGHT = 150
        private const val NUMBER_TEN = 10
        private const val NUMBER_FLOAT_FIFTEEN = 15f
        private const val DY_DRAW_TEXT = 30f
        private const val STROKE_WIDTH_LINE_PAINT = 7f
        private const val FLOAT_TEN = 10f
        private const val FLOAT_RADIUS = 12f
        private const val FLOAT_Twenty = 20f
        private const val FLOAT_Thirty = 30f
        private const val DX_MONTH = 100f
        private const val DY_MONTH = 50
        private const val FLOAT_ARRAY = 3f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isAddData) {
            addData()
            isAddData = true
        }
        widths = width.toFloat()
        heights = height.toFloat()
        scaleAxisY = heights / DIV_HEIGHT
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
            for (i in 1..DATA_STEP_END) {
                weightData.add(Weight(i, Random.nextInt(RANDOM_START, RANDOM_END)))
            }
        }
    }

    private fun drawNumberWeight(canvas: Canvas?) {
        val rangeWidth = widths / NUMBER_TEN - NUMBER_FLOAT_FIFTEEN
        start = -distanceMove - oldDistance
        weightData.forEachIndexed { index, it ->
            if (index < weightData.size - 1) {
                val startX = start
                val startY = heights - it.weight * scaleAxisY
                val endX = start + rangeWidth
                val endY = heights - (weightData[index + 1].weight) * scaleAxisY
                canvas?.drawText("${it.weight}", start, startY - DY_DRAW_TEXT, textPaint) //number
                canvas?.drawLine(startX, startY, endX, endY, linePaint)
                canvas?.drawCircle(startX, startY, FLOAT_RADIUS, pointPaint)
                canvas?.drawText(
                    "${it.month}",
                    start + FLOAT_Twenty,
                    heights - FLOAT_Twenty,
                    textPaint
                ) // day time
                path.moveTo(startX, startY)
                path.lineTo(start, heights)
                canvas?.drawPath(path, dashedLinePaint)
                path.moveTo(startX, startY)
                path.lineTo(0f, startY)
                start += rangeWidth
            }
        }
    }

    private fun drawAxisLine(canvas: Canvas) {
        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), axisLinePaint)
    }

    private fun drawText(canvas: Canvas) {
        canvas.drawText(
            resources.getString(R.string.description_text_weight),
            FLOAT_Twenty,
            FLOAT_Thirty,
            textPaint
        )
        canvas.drawText("Month", width.toFloat() - DX_MONTH, height.toFloat() - DY_MONTH, textPaint)
    }

    private val dashedLinePaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
        pathEffect = DashPathEffect(floatArrayOf(FLOAT_ARRAY, FLOAT_ARRAY), 0f)

    }
    private val linePaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLACK
        strokeWidth = STROKE_WIDTH_LINE_PAINT
        style = Paint.Style.FILL
    }

    private val pointPaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.RED
    }

    private val textPaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLUE
        textSize = FLOAT_Thirty
    }

    private val axisLinePaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.RED
        strokeWidth = FLOAT_TEN
    }
}
