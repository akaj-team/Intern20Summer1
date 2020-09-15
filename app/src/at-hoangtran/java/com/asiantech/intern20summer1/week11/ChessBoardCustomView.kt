package com.asiantech.intern20summer1.week11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChessBoardCustomView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    companion object {
        private const val COL = 8
        private const val ROW = 9
        private const val OUTLINE_WIDTH = 10f
        private const val INLINE_WIDTH = 4f
        private const val SPOT_LINE_DISTANCE = 10f
    }

    private var size: Float = 0f
    private val paintOut = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintIn = Paint(Paint.ANTI_ALIAS_FLAG)
    private var d0: Float = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initPaint()
        size = (width / (COL + 1)).toFloat()
        d0 = size / 2
        canvas.drawRect(
            size / 6,
            size / 6,
            size * 8 + d0 + size / 3,
            size * 9 + d0 + size / 3,
            paintOut
        )
        canvas.drawRect(d0, d0, size * 8 + d0, size * 9 + d0, paintIn)
        drawHorizontalLine(canvas)
        drawVerticalLine(canvas)
        drawDiagonalLine(canvas)
        drawSpot(canvas)
    }

    private fun initPaint() {
        paintOut.color = Color.BLACK
        paintOut.style = Paint.Style.STROKE
        paintOut.strokeWidth = OUTLINE_WIDTH
        paintIn.color = Color.BLACK
        paintIn.style = Paint.Style.STROKE
        paintIn.strokeWidth = INLINE_WIDTH
    }

    private fun drawHorizontalLine(canvas: Canvas) {
        for (i in 1..ROW) {
            canvas.drawLine(
                d0, size * i + d0, size * 8 + d0, size * i + d0, paintIn
            )
        }
    }

    private fun drawVerticalLine(canvas: Canvas) {
        for (i in 1..COL) {
            canvas.drawLine(size * i + d0, d0, size * i + d0, size * 4 + d0, paintIn)
            canvas.drawLine(
                size * i + d0,
                size * 5 + d0,
                size * i + d0,
                size * (COL + 1) + d0,
                paintIn
            )
        }
    }

    private fun drawDiagonalLine(canvas: Canvas) {
        canvas.drawLine(size * 4 - d0, d0, size * 6 - d0, size * 3 - d0, paintIn)
        canvas.drawLine(size * 4 - d0, size * 3 - d0, size * 6 - d0, size - d0, paintIn)
        canvas.drawLine(size * 4 - d0, size * 8 - d0, size * 6 - d0, size * 10 - d0, paintIn)
        canvas.drawLine(size * 4 - d0, size * 10 - d0, size * 6 - d0, size * 8 - d0, paintIn)
    }

    private fun drawRightSpot(canvas: Canvas, x: Float, y: Float) {
        canvas.drawLine(
            x - d0 + SPOT_LINE_DISTANCE,
            y - d0 - SPOT_LINE_DISTANCE,
            x + size / 3 - d0,
            y - d0 - SPOT_LINE_DISTANCE,
            paintIn
        )

        canvas.drawLine(
            x - d0 + SPOT_LINE_DISTANCE,
            y - d0 + SPOT_LINE_DISTANCE,
            x + size / 3 - d0,
            y - d0 + SPOT_LINE_DISTANCE,
            paintIn
        )


        canvas.drawLine(
            x - d0 + SPOT_LINE_DISTANCE,
            y - d0 - SPOT_LINE_DISTANCE,
            x - d0 + SPOT_LINE_DISTANCE,
            y - size / 3 - d0,
            paintIn
        )

        canvas.drawLine(
            x - d0 + SPOT_LINE_DISTANCE,
            y - d0 + SPOT_LINE_DISTANCE,
            x - d0 + SPOT_LINE_DISTANCE,
            y + size / 3 - d0,
            paintIn
        )
    }

    private fun drawLeftSpot(canvas: Canvas, x: Float, y: Float) {
        canvas.drawLine(
            x - d0 - SPOT_LINE_DISTANCE,
            y - d0 - SPOT_LINE_DISTANCE,
            x - size / 3 - d0,
            y - d0 - SPOT_LINE_DISTANCE,
            paintIn
        )

        canvas.drawLine(
            x - d0 - SPOT_LINE_DISTANCE,
            y - d0 + SPOT_LINE_DISTANCE,
            x - size / 3 - d0,
            y - d0 + SPOT_LINE_DISTANCE,
            paintIn
        )

        canvas.drawLine(
            x - d0 - SPOT_LINE_DISTANCE,
            y - d0 - SPOT_LINE_DISTANCE,
            x - d0 - SPOT_LINE_DISTANCE,
            y - size / 3 - d0,
            paintIn
        )

        canvas.drawLine(
            x - d0 - SPOT_LINE_DISTANCE,
            y - d0 + SPOT_LINE_DISTANCE,
            x - d0 - SPOT_LINE_DISTANCE,
            y + size / 3 - d0,
            paintIn
        )
    }

    private fun drawSpot(canvas: Canvas) {
        drawRightSpot(canvas, size * 2, size * 3)
        drawLeftSpot(canvas, size * 2, size * 3)

        drawRightSpot(canvas, size * 8, size * 3)
        drawLeftSpot(canvas, size * 8, size * 3)

        drawRightSpot(canvas, size, size * 4)

        drawRightSpot(canvas, size * 3, size * 4)
        drawLeftSpot(canvas, size * 3, size * 4)

        drawRightSpot(canvas, size * 5, size * 4)
        drawLeftSpot(canvas, size * 5, size * 4)

        drawRightSpot(canvas, size * 7, size * 4)
        drawLeftSpot(canvas, size * 7, size * 4)

        drawLeftSpot(canvas, size * 9, size * 4)

        drawRightSpot(canvas, size, size * 7)

        drawRightSpot(canvas, size * 3, size * 7)
        drawLeftSpot(canvas, size * 3, size * 7)

        drawRightSpot(canvas, size * 5, size * 7)
        drawLeftSpot(canvas, size * 5, size * 7)

        drawRightSpot(canvas, size * 7, size * 7)
        drawLeftSpot(canvas, size * 7, size * 7)

        drawLeftSpot(canvas, size * 9, size * 7)

        drawRightSpot(canvas, size * 2, size * 8)
        drawLeftSpot(canvas, size * 2, size * 8)

        drawRightSpot(canvas, size * 8, size * 8)
        drawLeftSpot(canvas, size * 8, size * 8)
    }
}
