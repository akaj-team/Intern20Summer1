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

class WeightChart2(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    companion object {
        const val MAX_WEIGHT = 130
        const val MAX_MONTH = 12
    }

    private var weights = mutableListOf<WeightTwo>()
    private var leftBound = 100f
    private var bottomBound = 100f
    private var xRangeStep = 150f
    private var weightStep = 10
    private var yRangeStep = 15f
    private var valuePointRange = 15f
    private var monthValueMargin = 50f
    private var weightValueMargin = 50f
    private var origin = PointF()
    private var normalBlackLine: Paint? = null
    private var begin = 0f
    private var s = 0f
    private var moveOx = 0f
    private var minOx = 0f
    private var maxOx = 0f
    private var widthUnit = resources.displayMetrics.density
    private var axisStrokeWidth = 4 * widthUnit
    private var axisValuesTextSize = 20f

    constructor(context: Context?) : this(context, null) {}
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(w, h)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        origin = PointF(0f + leftBound, h.toFloat() - bottomBound)
        minOx = origin.x - 13 * xRangeStep + width - weightValueMargin
        maxOx = origin.x
        //yRangeStep = h / MAX_WEIGHT.toFloat()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                begin = event.x
                s = 0f
            }
            MotionEvent.ACTION_MOVE -> {
                if (origin.x + moveOx < minOx || origin.x + moveOx > maxOx) {
                    s = (event.x - begin) / 5
                    begin = event.x
                    invalidate()
                } else {
                    s = event.x - begin
                    begin = event.x
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                //isSpeed = true
                if (origin.x + moveOx < minOx) {
                    moveOx = origin.x - 14 * xRangeStep + width
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

        moveOx += s
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

            canvas?.drawCircle(ox, origin.y, 5f, paintPoint())
            canvas?.drawText(i.toString(), ox, origin.y + monthValueMargin, paintText())
        }
        canvas?.drawText(
            "Month",
            width - 3 * axisValuesTextSize,
            height.toFloat(),
            paintText().apply { textSize = axisValuesTextSize })

        // point
        for (i in weights.indices) {
            val point =
                PointF(
                    origin.x + (i + 1) * xRangeStep + moveOx,
                    origin.y - weights[i].weight * yRangeStep
                )
            //point
            canvas?.drawCircle(point.x, point.y, 10f, paintPoint().apply { color = Color.RED })
            //weight above
            canvas?.drawText(
                weights[i].weight.toString(),
                point.x + valuePointRange,
                point.y - valuePointRange,
                paintText()
            )
        }

        // margin white of the left
        canvas?.drawRect(
            0f,
            0f,
            origin.x - axisStrokeWidth / 2,
            origin.y + bottomBound,
            paintLine().apply { color = Color.WHITE })
        // oy
        canvas?.drawLine(origin.x, origin.y, origin.x, 0f, paintAxis())
        // weight oy
        for (i in 0..MAX_WEIGHT step weightStep) {
            canvas?.drawCircle(origin.x, origin.y - i * yRangeStep, 5f, paintPoint())
            canvas?.drawText(
                i.toString(),
                origin.x - weightValueMargin,
                origin.y - i * yRangeStep,
                paintText()
            )
        }
        canvas?.drawText(
            "Weight",
            0f,
            axisValuesTextSize,
            paintText().apply { textSize = axisValuesTextSize })
    }

    init {
        initPaints()
        for (i in 1..12) {
            weights.add(WeightTwo(Random.nextInt(50..120), i))
        }
    }

    private fun initPaints() {
        normalBlackLine = Paint(Paint.ANTI_ALIAS_FLAG)
        normalBlackLine?.color = Color.BLACK
        normalBlackLine?.strokeCap = Paint.Cap.BUTT
        normalBlackLine?.strokeWidth = 1 * widthUnit
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
        //style = Paint.Style.STROKE
        strokeWidth = 2 * widthUnit
        pathEffect = null
    }

    private fun paintPoint() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.CYAN
        style = Paint.Style.FILL
        strokeWidth = 5 * widthUnit
        pathEffect = null
    }

    private fun paintText() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLUE
        style = Paint.Style.FILL
        textSize = 18f
        pathEffect = null
    }

    private fun paintDashLine() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 1 * widthUnit
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }
}
