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

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        //black bound
        normalBlackLine?.let {
            canvas?.drawRoundRect(
                RectF(
                    0f,
                    0f,
                    widthSquareView + 4 * rangeBound,
                    heightSquareView + 4 * rangeBound
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
                    widthSquareView + 3 * rangeBound,
                    heightSquareView + 3 * rangeBound
                ),
                WHITE_ROUND,
                WHITE_ROUND,
                it
            )
        }
        // 10 horizontal line
        for (i in 0..9) {
            val startX = origin
            val startY = origin + widthBox * i
            val endX = startX + 8 * widthBox
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
                origin + 9 * widthBox,
                it
            )
        }
        normalBlackLine?.let {
            canvas?.drawLine(
                origin + 8 * widthBox,
                origin + 0 * widthBox,
                origin + 8 * widthBox,
                origin + 9 * widthBox,
                it
            )
        }

        /**
         * ABOVE SIDE
         */
        //9 verLines above side(long: 4 wbox)
        for (i in 0 until 9) {
            val startX = origin + widthBox * i
            val startY = origin
            val endX = origin + widthBox * i
            val endY = origin + 4 * widthBox
            val line = floatArrayOf(startX, startY, endX, endY)
            normalBlackLine?.let { canvas?.drawLines(line, it) }
        }
        // 2 across lines above side
        normalBlackLine?.let {
            canvas?.drawLine(
                origin + widthBox * 3,
                origin,
                origin + widthBox * 5,
                origin + widthBox * 2,
                it
            )
        }
        normalBlackLine?.let {
            canvas?.drawLine(
                origin + widthBox * 5,
                origin,
                origin + widthBox * 3,
                origin + widthBox * 2,
                it
            )
        }

        /**
         * BOTTOM SIDE
         */
        //9 verLine bottom side(long: 4 wbox)
        for (i in 0 until 9) {
            val startX = origin + widthBox * i
            val startY = origin + 5 * widthBox
            val endX = origin + widthBox * i
            val endY = origin + 9 * widthBox
            val line = floatArrayOf(startX, startY, endX, endY)
            normalBlackLine?.let { canvas?.drawLines(line, it) }
        }
        // 2 across lines bottom side
        normalBlackLine?.let {
            canvas?.drawLine(
                origin + widthBox * 3,
                origin + widthBox * 7,
                origin + widthBox * 5,
                origin + widthBox * 9,
                it
            )
        }
        normalBlackLine?.let {
            canvas?.drawLine(
                origin + widthBox * 5,
                origin + widthBox * 7,
                origin + widthBox * 3,
                origin + widthBox * 9,
                it
            )
        }

        /**
         * draw AIM above side
         */
        //full
        drawFullAim(canvas, origin + widthBox, origin + 2 * widthBox)
        drawFullAim(canvas, origin + 2 * widthBox, origin + 3 * widthBox)
        drawFullAim(canvas, origin + 4 * widthBox, origin + 3 * widthBox)
        drawFullAim(canvas, origin + 6 * widthBox, origin + 3 * widthBox)
        drawFullAim(canvas, origin + 7 * widthBox, origin + 2 * widthBox)
        //half
        drawAimTopRight(canvas, origin, origin + 3 * widthBox)
        drawAimBottomRight(canvas, origin, origin + 3 * widthBox)

        drawAimTopLeft(canvas, origin + 8 * widthBox, origin + 3 * widthBox)
        drawAimBottomLeft(canvas, origin + 8 * widthBox, origin + 3 * widthBox)

        /**
         * drawAIM above side
         */
        //full
        drawFullAim(canvas, origin + widthBox, origin + 7 * widthBox)
        drawFullAim(canvas, origin + 2 * widthBox, origin + 6 * widthBox)
        drawFullAim(canvas, origin + 4 * widthBox, origin + 6 * widthBox)
        drawFullAim(canvas, origin + 6 * widthBox, origin + 6 * widthBox)
        drawFullAim(canvas, origin + 7 * widthBox, origin + 7 * widthBox)
        //half
        drawAimTopRight(canvas, origin, origin + 6 * widthBox)
        drawAimBottomRight(canvas, origin, origin + 6 * widthBox)

        drawAimTopLeft(canvas, origin + 8 * widthBox, origin + 6 * widthBox)
        drawAimBottomLeft(canvas, origin + 8 * widthBox, origin + 6 * widthBox)
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
}
