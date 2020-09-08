package com.asiantech.intern20summer1.w11.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log.d
import android.view.MotionEvent
import android.view.View


/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 07/09/2020.
 * This is GraphView Class
 * It is custom view for graph weight
 */
class GraphView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        private const val MARGIN = 20f
        private const val STROKE_WIDTH = 20f
        private const val TEXT_SIZE = 15f
        private const val STEP_VERTICAL = 20
    }

    var data = mutableListOf<Weight>()
    var numberWeek = 1
    var graphTitle = ""
    var graphTitleColor = Color.BLACK
    var graphTitleSize = TEXT_SIZE
    var verticalAxisWidth = STROKE_WIDTH
    var horizontalAxisWidth = STROKE_WIDTH
    var verticalMaxValue = 1
    var marginBottom = MARGIN
    var marginLeft = MARGIN
    var marginRight = MARGIN
    var marginTop = MARGIN
    var stepVertical = STEP_VERTICAL

    private var x0 = 1f
    private var y0 = 1f
    private var xMax = 1f
    private var yMax = 1f
    private var stopMove = 0f
    private var dx = 0f
    private var move = 0f
    private var isWallLeft = false
    private var isWallRight = false

    override fun onDraw(canvasPram: Canvas?) {
        super.onDraw(canvasPram)
        initValue()
        canvasPram?.run {
            drawViewStatic(this)
            writeTitle(this)
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                stopMove = event.x
            }

            MotionEvent.ACTION_MOVE -> {
                dx = event.x - stopMove
                stopMove = event.x
                d("dx2", "dx = $dx |L = $isWallLeft|R = $isWallRight ")
                if (isWallLeft && dx > 0f) {
                    dx = 0f
                    d("dx2", "dxxl = $dx ")
                }
                if (isWallRight && dx < 0f) {
                    dx = 0f
                    d("dx2", "dxxr = $dx ")
                }
                invalidate()

            }

            MotionEvent.ACTION_UP -> {
                dx = 0f
                invalidate()
            }
        }
        return true
    }


    fun replaceAllData(dataWeight: List<Weight>) {
        data.clear()
        dataWeight.toCollection(data)
    }

    private fun initValue() {
        x0 = marginLeft
        y0 = height - marginBottom
        xMax = width - marginRight
        yMax = marginTop
    }

    fun show() {
        invalidate()
    }

    private fun drawViewStatic(canvas: Canvas) {
        move += dx
        dx = 0f
        drawConnectLineWeight(canvas) //1
        drawPointWeight(canvas)  //2
        drawHorizontalAxis(canvas)  //3
        drawMargin(canvas)  //4
        drawVerticalAxis(canvas)   //5
    }

    private fun drawConnectLineWeight(canvas: Canvas) {
        val zeroPoint = PointF(x0 + move, y0)
        var endPoint = PointF()
        val stepY = (yMax - y0) / verticalMaxValue
        val stepX = (xMax - x0) / numberWeek
        data.forEachIndexed { index, weight ->
            val x1 = zeroPoint.x + stepX * index
            val y1 = zeroPoint.y + stepY * weight.weight
            canvas.drawLine(x1, y1, x1, zeroPoint.y, paintDashed())
            canvas.drawCircle(x1, y1, 10f, paintPoint())
            canvas.drawText(weight.weight.toString(), x1 - 10f, y1 - 10f, paintText())
            canvas.drawText("week " + weight.week.toString(), x1, y0 - 20, paintText())
            endPoint = PointF(zeroPoint.x + stepX * (data.size - 1), y1)
        }
        isWallLeft = (zeroPoint.x > 20f)
        isWallRight = (endPoint.x < (width - 20f))
    }

    private fun drawPointWeight(canvas: Canvas) {
        val zeroPoint = PointF(x0 + move, y0)
        val stepY = (yMax - y0) / verticalMaxValue
        val stepX = (xMax - x0) / numberWeek
        data.forEachIndexed { index, weight ->
            if (index < data.size - 1) {
                val x1 = zeroPoint.x + stepX * index
                val y1 = zeroPoint.y + stepY * weight.weight
                val x2 = zeroPoint.x + stepX * (index + 1)
                val y2 = zeroPoint.y + stepY * data[index + 1].weight
                canvas.drawLine(x1, y1, x2, y2, paintLine())
            }
        }
    }

    private fun drawHorizontalAxis(canvas: Canvas){
        canvas.drawLine(0f, y0, width + 0f, y0, paintStroke().apply { strokeWidth = verticalAxisWidth })
    }

    private fun drawMargin(canvas: Canvas) {
        canvas.drawLine(0f, 0f, 0f, height.toFloat(),
            paintMargin().apply { strokeWidth = marginLeft })
        canvas.drawLine(width.toFloat(), 0f, width.toFloat(), height.toFloat(),
            paintMargin().apply { strokeWidth = marginRight })
        canvas.drawLine(0f, 0f, width.toFloat(), 0f,
            paintMargin().apply { strokeWidth = marginTop })
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(),
            paintMargin().apply { strokeWidth = marginBottom })
    }

    private fun drawVerticalAxis(canvas: Canvas) {
        var paint = paintStroke().apply { strokeWidth = verticalAxisWidth }
        canvas.drawLine(x0, y0, x0, yMax, paint) // doc
        paint = paintPoint().apply {
            color = Color.YELLOW
            textSize = 20f
        }
        for ((index, i) in (verticalMaxValue downTo 0 step stepVertical).withIndex()) {
            val dy = ((yMax - y0) / verticalMaxValue * stepVertical) * index
            canvas.drawText(i.toString(), x0, yMax - dy, paint)
        }

    }


    private fun writeTitle(canvas: Canvas) {
        val textPaint = Paint().apply {
            Paint.ANTI_ALIAS_FLAG
            Paint.Align.CENTER
            color = graphTitleColor
            textSize = graphTitleSize
        }

        val xPos = width / 2f
        val yPos = (graphTitleSize)
        canvas.drawText(graphTitle, xPos, yPos, textPaint)
    }

    private fun paintPoint() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.RED
        style = Paint.Style.FILL
        pathEffect = null
    }

    private fun paintMargin() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.WHITE
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = MARGIN
        pathEffect = null
    }

    private fun paintLine() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 4f
        pathEffect = null
    }

    private fun paintText() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.GREEN
        textSize = TEXT_SIZE
        pathEffect = null
    }

    private fun paintDashed() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    private fun paintStroke() = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = verticalAxisWidth
    }
}
