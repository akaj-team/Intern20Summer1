package com.asiantech.intern20summer1.week11.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

open class ChessTable(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    companion object {
        private const val NUMBER_HORIZONTAL_BOX = 8
        private const val NUMBER_VERTICAL_BOX = 9
        private const val WIDTH_RATIO = 8.4f
        private const val HEIGHT_RATIO = 9.4f
        private const val BLACK_ROUND = 7f
        private const val WHITE_ROUND = 10f
        private const val RANGE_BOUND_AND_WIDTH_BOX_RATIO = 1 / 10f
        private const val RANGE_AND_WIDTH_BOX_RATIO = 8 / 100f
        private const val AIM_LENGTH_AND_WIDTH_BOX_RATIO = 24 / 100f
        private const val THREE = 3
        private const val FOUR = 4
        private const val FIVE = 5
        private const val SIX = 6
        private const val SEVEN = 7
        private const val EIGHT = 8
        private const val NINE = 9
    }

    private var widthSquareView = 0f
    private var heightSquareView = 0f
    private var widthBox = 0f
    private var rangeBound = 0f
    private var aimLength = 0f
    private var origin = 0f
    private var range = 0f
    private var normalBlackLine: Paint? = null
    private var normalWhiteLine: Paint? = null
    private var aimLinePaint: Paint? = null

    /*private var pointX = 0f
    private var pointY = 0f

    private var day = 13
    private var month = 9
    private var year = 2020*/

    constructor(context: Context?) : this(context, null) {}
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var w = MeasureSpec.getSize(widthMeasureSpec)
        var h = MeasureSpec.getSize(heightMeasureSpec)

        if (w * HEIGHT_RATIO / WIDTH_RATIO <= h) {
            h = (w * HEIGHT_RATIO / WIDTH_RATIO).toInt()
        } else {
            w = (h * WIDTH_RATIO / HEIGHT_RATIO).toInt()
        }
        setMeasuredDimension(w, h)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widthBox = w / WIDTH_RATIO
        widthSquareView = widthBox * NUMBER_HORIZONTAL_BOX
        heightSquareView = widthBox * NUMBER_VERTICAL_BOX
        rangeBound = widthBox * RANGE_BOUND_AND_WIDTH_BOX_RATIO
        origin = 2 * rangeBound
        aimLength = widthBox * AIM_LENGTH_AND_WIDTH_BOX_RATIO
        range = widthBox * RANGE_AND_WIDTH_BOX_RATIO
    }

    /*override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        var startX = 0f
        var endX = 0f
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                startX = x
            }
            MotionEvent.ACTION_MOVE -> {
                endX
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return super.onTouchEvent(event)
    }*/

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        //black bound
        normalBlackLine?.let {
            canvas?.drawRoundRect(
                RectF(
                    0f,
                    0f,
                    widthSquareView + FOUR * rangeBound,
                    heightSquareView + FOUR * rangeBound
                ),
                BLACK_ROUND,
                BLACK_ROUND,
                it
            )
        }
        //white bound
        normalWhiteLine?.let {
            canvas?.drawRoundRect(
                RectF(
                    rangeBound,
                    rangeBound,
                    widthSquareView + THREE * rangeBound,
                    heightSquareView + THREE * rangeBound
                ),
                WHITE_ROUND,
                WHITE_ROUND,
                it
            )
        }
        // 10 horizontal line
        for (i in 0..NUMBER_VERTICAL_BOX) {
            val startX = origin
            val startY = origin + widthBox * i
            val endX = startX + NUMBER_HORIZONTAL_BOX * widthBox
            val endY = origin + widthBox * i
            val line = floatArrayOf(startX, startY, endX, endY)
            normalBlackLine?.let { canvas?.drawLines(line, it) }
        }
        // 2 vertical bound line
        normalBlackLine?.let {
            canvas?.drawLine(
                origin,
                origin,
                origin,
                origin + NUMBER_VERTICAL_BOX * widthBox,
                it
            )
        }
        normalBlackLine?.let {
            canvas?.drawLine(
                origin + NUMBER_HORIZONTAL_BOX * widthBox,
                origin + 0 * widthBox,
                origin + NUMBER_HORIZONTAL_BOX * widthBox,
                origin + NUMBER_VERTICAL_BOX * widthBox,
                it
            )
        }

        /**
         * ABOVE SIDE
         */
        //9 verLines above side(long: 4 wbox)
        for (i in 0..NUMBER_HORIZONTAL_BOX) {
            val startX = origin + widthBox * i
            val startY = origin
            val endX = origin + widthBox * i
            val endY = origin + FOUR * widthBox
            val line = floatArrayOf(startX, startY, endX, endY)
            normalBlackLine?.let { canvas?.drawLines(line, it) }
        }
        // 2 across lines above side
        normalBlackLine?.let {
            canvas?.drawLine(
                origin + widthBox * THREE,
                origin,
                origin + widthBox * FIVE,
                origin + widthBox * 2,
                it
            )
        }
        normalBlackLine?.let {
            canvas?.drawLine(
                origin + widthBox * FIVE,
                origin,
                origin + widthBox * THREE,
                origin + widthBox * 2,
                it
            )
        }

        /**
         * BOTTOM SIDE
         */
        //9 verLine bottom side(long: 4 wbox)
        for (i in 0..NUMBER_HORIZONTAL_BOX) {
            val startX = origin + widthBox * i
            val startY = origin + FIVE * widthBox
            val endX = origin + widthBox * i
            val endY = origin + NUMBER_VERTICAL_BOX * widthBox
            val line = floatArrayOf(startX, startY, endX, endY)
            normalBlackLine?.let { canvas?.drawLines(line, it) }
        }
        // 2 across lines bottom side
        normalBlackLine?.let {
            canvas?.drawLine(
                origin + widthBox * THREE,
                origin + widthBox * SEVEN,
                origin + widthBox * FIVE,
                origin + widthBox * NINE,
                it
            )
        }
        normalBlackLine?.let {
            canvas?.drawLine(
                origin + widthBox * FIVE,
                origin + widthBox * SEVEN,
                origin + widthBox * THREE,
                origin + widthBox * NINE,
                it
            )
        }

        /**
         * draw AIM above side
         */
        //full
        drawFullAim(canvas, origin + widthBox, origin + 2 * widthBox)
        drawFullAim(canvas, origin + 2 * widthBox, origin + THREE * widthBox)
        drawFullAim(canvas, origin + FOUR * widthBox, origin + THREE * widthBox)
        drawFullAim(canvas, origin + SIX * widthBox, origin + THREE * widthBox)
        drawFullAim(canvas, origin + SEVEN * widthBox, origin + 2 * widthBox)
        //half
        drawAimTopRight(canvas, origin, origin + THREE * widthBox)
        drawAimBottomRight(canvas, origin, origin + THREE * widthBox)

        drawAimTopLeft(canvas, origin + EIGHT * widthBox, origin + THREE * widthBox)
        drawAimBottomLeft(canvas, origin + EIGHT * widthBox, origin + THREE * widthBox)

        /**
         * drawAIM above side
         */
        //full
        drawFullAim(canvas, origin + widthBox, origin + SEVEN * widthBox)
        drawFullAim(canvas, origin + 2 * widthBox, origin + SIX * widthBox)
        drawFullAim(canvas, origin + FOUR * widthBox, origin + SIX * widthBox)
        drawFullAim(canvas, origin + SIX * widthBox, origin + SIX * widthBox)
        drawFullAim(canvas, origin + SEVEN * widthBox, origin + SEVEN * widthBox)
        //half
        drawAimTopRight(canvas, origin, origin + SIX * widthBox)
        drawAimBottomRight(canvas, origin, origin + SIX * widthBox)

        drawAimTopLeft(canvas, origin + EIGHT * widthBox, origin + SIX * widthBox)
        drawAimBottomLeft(canvas, origin + EIGHT * widthBox, origin + SIX * widthBox)
    }

    init {
        initPaints()
    }

    private fun initPaints() {
        normalBlackLine = Paint(Paint.ANTI_ALIAS_FLAG)
        normalBlackLine?.color = Color.BLACK
        normalBlackLine?.strokeCap = Paint.Cap.BUTT
        normalBlackLine?.strokeWidth = 1 * resources.displayMetrics.density

        normalWhiteLine = Paint(Paint.ANTI_ALIAS_FLAG)
        normalWhiteLine?.color = Color.WHITE
        normalWhiteLine?.strokeCap = Paint.Cap.ROUND

        aimLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        aimLinePaint?.color = Color.BLACK
        aimLinePaint?.strokeCap = Paint.Cap.ROUND
        aimLinePaint?.strokeWidth = 1 * resources.displayMetrics.density
    }

    private fun drawAimBottomRight(canvas: Canvas?, aimX: Float, aimY: Float) {
        aimLinePaint?.let {
            canvas?.drawLine(
                aimX + range, aimY + range, aimX + range + aimLength, aimY + range,
                it
            )
            canvas?.drawLine(
                aimX + range, aimY + range, aimX + range, aimY + range + aimLength,
                it
            )
        }
    }

    private fun drawAimBottomLeft(canvas: Canvas?, aimX: Float, aimY: Float) {
        aimLinePaint?.let {
            canvas?.drawLine(
                aimX - range, aimY + range, aimX - range - aimLength, aimY + range,
                it
            )
            canvas?.drawLine(
                aimX - range, aimY + range, aimX - range, aimY + range + aimLength,
                it
            )
        }
    }

    private fun drawAimTopLeft(canvas: Canvas?, aimX: Float, aimY: Float) {
        aimLinePaint?.let {
            canvas?.drawLine(
                aimX - range, aimY - range, aimX - range - aimLength, aimY - range,
                it
            )
            canvas?.drawLine(
                aimX - range, aimY - range, aimX - range, aimY - range - aimLength,
                it
            )
        }
    }

    private fun drawAimTopRight(canvas: Canvas?, aimX: Float, aimY: Float) {
        aimLinePaint?.let {
            canvas?.drawLine(
                aimX + range, aimY - range, aimX + range + aimLength, aimY - range,
                it
            )
            canvas?.drawLine(
                aimX + range, aimY - range, aimX + range, aimY - range - aimLength,
                it
            )
        }
    }

    private fun drawFullAim(canvas: Canvas?, aimX: Float, aimY: Float) {
        drawAimBottomRight(canvas, aimX, aimY)
        drawAimBottomLeft(canvas, aimX, aimY)
        drawAimTopLeft(canvas, aimX, aimY)
        drawAimTopRight(canvas, aimX, aimY)
    }

    /*private fun drawPointOnClick(canvas: Canvas?, aimX: Float, aimY: Float) {
        aimLinePaint?.let { canvas?.drawPoint(aimX, aimY, it) }
    }*/
}
