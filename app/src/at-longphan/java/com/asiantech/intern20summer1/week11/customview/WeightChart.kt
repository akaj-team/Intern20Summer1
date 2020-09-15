package com.asiantech.intern20summer1.week11.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.asiantech.intern20summer1.week11.WeightTwo
import kotlin.random.Random
import kotlin.random.nextInt

class WeightChart(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    companion object {
        private const val WEIGHT_TEXT = "Weight"
        private const val MONTH_TEXT = "Month"

        private const val MAX_MONTH = 12
        private const val MAX_WEIGHT = 120
        private const val MIN_WEIGHT = 50
        private const val WEIGHT_STEP = 10

        private const val LEFT_BOUND = 100f
        private const val BOTTOM_BOUND = 100f

        private const val MONTH_MARGIN = 50f
        private const val WEIGHT_MARGIN = 50f

        private var VALUE_POINT_RANGE = 15f
        private var AXIS_VALUE_TEXT_SIZE = 20f

        private const val THREE = 3
        private const val FOUR = 4
        private const val FIVE = 5
        private const val TEN = 10
        private const val THIRTEEN = 13
        private const val FOURTEEN = 14
        private const val EIGHTEEN = 18
    }

    private var maxWeight = 0
    private var weights = mutableListOf<WeightTwo>()
    private var xRangeStep = 150f
    private var yRangeStep = 15f
    private var origin = PointF()
    private var begin = 0f
    private var actionMove = 0f
    private var moveOx = 0f
    private var minOx = 0f
    private var maxOx = 0f
    private var widthUnit = resources.displayMetrics.density
    private var axisStrokeWidth = FOUR * widthUnit

    constructor(context: Context?) : this(context, null) {}
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(w, h)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        origin = PointF(0f + LEFT_BOUND, h.toFloat() - BOTTOM_BOUND)
        minOx = origin.x - THIRTEEN * xRangeStep + w - WEIGHT_MARGIN
        maxOx = origin.x

        for (i in 1..MAX_MONTH) {
            val random = Random.nextInt(MIN_WEIGHT..MAX_WEIGHT)
            if (random > maxWeight) {
                maxWeight = random + TEN
            }
            weights.add(WeightTwo(random, i))
        }
        yRangeStep = (h - BOTTOM_BOUND) / maxWeight.toFloat()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                begin = event.x
                actionMove = 0f
            }
            MotionEvent.ACTION_MOVE -> {
                if (origin.x + moveOx < minOx || origin.x + moveOx > maxOx) {
                    actionMove = (event.x - begin) / FIVE
                    begin = event.x
                    invalidate()
                } else {
                    actionMove = event.x - begin
                    begin = event.x
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (origin.x + moveOx < minOx) {
                    moveOx = origin.x - FOURTEEN * xRangeStep + width - WEIGHT_MARGIN
                    invalidate()
                }
                if (origin.x + moveOx > maxOx) {
                    moveOx = 0f
                    invalidate()
                }
            }
        }
        return true
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        moveOx += actionMove
        //  dash
        var prevPoint = PointF()
        for (i in weights.indices) {
            val point =
                PointF(
                    origin.x + (i + 1) * xRangeStep + moveOx,
                    origin.y - weights[i].weight * yRangeStep
                )
            // point to Ox
            canvas?.drawLine(point.x, origin.y, point.x, point.y, paintDashLine())
            // point to Oy
            canvas?.drawLine(origin.x, point.y, point.x, point.y, paintDashLine())
            // draw line between 2 point and save prev point
            if (i != 0) {
                canvas?.drawLine(prevPoint.x, prevPoint.y, point.x, point.y, paintLine())
            }
            prevPoint = point
        }

        // ox
        canvas?.drawLine(origin.x, origin.y, width.toFloat(), origin.y, paintAxis())

        // month oX
        for (i in 1..MAX_MONTH) {
            val ox = origin.x + i * xRangeStep + moveOx

            canvas?.drawCircle(ox, origin.y, FIVE.toFloat(), paintPoint())
            canvas?.drawText(i.toString(), ox, origin.y + MONTH_MARGIN, paintText())
        }
        canvas?.drawText(
            MONTH_TEXT,
            width - THREE * AXIS_VALUE_TEXT_SIZE,
            height.toFloat(),
            paintText().apply { textSize = AXIS_VALUE_TEXT_SIZE })

        // point
        for (i in weights.indices) {
            val point =
                PointF(
                    origin.x + (i + 1) * xRangeStep + moveOx,
                    origin.y - weights[i].weight * yRangeStep
                )
            //point
            canvas?.drawCircle(
                point.x,
                point.y,
                TEN.toFloat(),
                paintPoint().apply { color = Color.RED })
            //weight above
            canvas?.drawText(
                weights[i].weight.toString(),
                point.x + VALUE_POINT_RANGE,
                point.y - VALUE_POINT_RANGE,
                paintText()
            )
        }

        // margin white of the left
        canvas?.drawRect(
            0f,
            0f,
            origin.x - axisStrokeWidth / 2,
            origin.y + BOTTOM_BOUND,
            paintLine().apply { color = Color.WHITE })
        // oy
        canvas?.drawLine(origin.x, origin.y, origin.x, 0f, paintAxis())
        // weight oy
        for (i in 0 until maxWeight step WEIGHT_STEP) {
            canvas?.drawCircle(origin.x, origin.y - i * yRangeStep, FIVE.toFloat(), paintPoint())
            canvas?.drawText(
                i.toString(),
                origin.x - WEIGHT_MARGIN,
                origin.y - i * yRangeStep,
                paintText()
            )
        }
        canvas?.drawText(
            WEIGHT_TEXT,
            0f,
            AXIS_VALUE_TEXT_SIZE,
            paintText().apply { textSize = AXIS_VALUE_TEXT_SIZE })
    }

    private fun paintAxis() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = axisStrokeWidth
        pathEffect = null
    }

    private fun paintLine() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.GRAY
        strokeWidth = 2 * widthUnit
        pathEffect = null
    }

    private fun paintPoint() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.CYAN
        style = Paint.Style.FILL
        strokeWidth = FIVE * widthUnit
        pathEffect = null
    }

    private fun paintText() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLUE
        style = Paint.Style.FILL
        textSize = EIGHTEEN.toFloat()
        pathEffect = null
    }

    private fun paintDashLine() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = widthUnit
        pathEffect = DashPathEffect(floatArrayOf(TEN.toFloat(), TEN.toFloat()), 0f)
    }
}
