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
        private const val COL = 9
        private const val ROW = 10
    }

    private var size: Float = 0f
    private val paintOut = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintIn = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initPaint()
        size = (width / COL).toFloat()
        canvas.drawRect(30f, 30f, size * 9 - 30f, size * 10 - 30f, paintOut)
        canvas.drawRect(60f, 60f, size * 9 - 60f, size * 10 - 60f, paintIn)
        drawHorizontalLine(canvas)
        drawVerticalLine(canvas)
        drawDiagonalLine(canvas)
        drawSpot(canvas)
    }

    private fun initPaint() {
        paintOut.color = Color.BLACK
        paintOut.style = Paint.Style.STROKE
        paintOut.strokeWidth = 10f
        paintIn.color = Color.BLACK
        paintIn.style = Paint.Style.STROKE
        paintIn.strokeWidth = 4f
    }

    private fun drawHorizontalLine(canvas: Canvas) {
        for (i in 2..ROW) {
            canvas.drawLine(60f, size * i - 60f, size * 9 - 60f, size * i - 60f, paintIn)
        }
    }

    private fun drawVerticalLine(canvas: Canvas) {
        for (i in 2..COL) {
            canvas.drawLine(size * i - 60f, size - 60f, size * i - 60f, size * 5 - 60f, paintIn)
            canvas.drawLine(
                size * i - 60f,
                size * 6 - 60f,
                size * i - 60f,
                size * ROW - 60f,
                paintIn
            )
        }
    }

    private fun drawDiagonalLine(canvas: Canvas) {
        canvas.drawLine(size * 4 - 60f, 60f, size * 6 - 60f, size * 3 - 60f, paintIn)
        canvas.drawLine(size * 4 - 60f, size * 3 - 60f, size * 6 - 60f, size - 60f, paintIn)
        canvas.drawLine(size * 4 - 60f, size * 8 - 60f, size * 6 - 60f, size * 10 - 60f, paintIn)
        canvas.drawLine(size * 4 - 60f, size * 10 - 60f, size * 6 - 60f, size * 8 - 60f, paintIn)
    }

    private fun drawRightSpot(canvas: Canvas, x: Float, y: Float) {
        canvas.drawLine(
            x - 50f,
            y - 70f,
            x + size / 3 - 60f,
            y - 70f,
            paintIn
        )

        canvas.drawLine(
            x - 50f,
            y - 50f,
            x + size / 3 - 60f,
            y - 50f,
            paintIn
        )


        canvas.drawLine(
            x - 50f,
            y - 70f,
            x - 50f,
            y - size / 3 - 60f,
            paintIn
        )

        canvas.drawLine(
            x - 50f,
            y - 50f,
            x - 50f,
            y + size / 3 - 60f,
            paintIn
        )
    }

    private fun drawLeftSpot(canvas: Canvas, x: Float, y: Float) {
        canvas.drawLine(
            x - 70f,
            y - 70f,
            x - size / 3 - 60f,
            y - 70f,
            paintIn
        )

        canvas.drawLine(
            x - 70f,
            y - 50f,
            x - size / 3 - 60f,
            y - 50f,
            paintIn
        )

        canvas.drawLine(
            x - 70f,
            y - 70f,
            x - 70f,
            y - size / 3 - 60f,
            paintIn
        )

        canvas.drawLine(
            x - 70f,
            y - 50f,
            x - 70f,
            y + size / 3 - 60f,
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
