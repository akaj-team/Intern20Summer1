package com.asiantech.intern20summer1.week11.customs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChessBoardCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val lineColor = Color.BLACK
    private val borderWidthIn = 4.0f
    private val borderWidthOut = 10.0f
    private val paintBorder = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var size: Float = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        size = (width / 10).toFloat()
        initPaint()
        canvas.drawRect(size - 20f, size - 20f, size * 9 + 20f, size * 10 + 20f, paintBorder)
        canvas.drawRect(size, size, size * 9, size * 10, paint)
        drawVerticalLine(canvas)
        drawHorizontalLine(canvas)
        drawDiagonalLine(size * 5, size * 2, canvas)
        drawDiagonalLine(size * 5, size * 9, canvas)
        drawSmallLine(size * 2, size * 3, canvas)
        drawSmallLine(size * 8, size * 3, canvas)
        drawSmallLine(size * 2, size * 8, canvas)
        drawSmallLine(size * 8, size * 8, canvas)
        drawSmallLine(size * 3, size * 4, canvas)
        drawSmallLine(size * 5, size * 4, canvas)
        drawSmallLine(size * 7, size * 4, canvas)
        drawSmallLine(size * 3, size * 7, canvas)
        drawSmallLine(size * 5, size * 7, canvas)
        drawSmallLine(size * 7, size * 7, canvas)
        drawLeftPawn(size, size * 4, canvas)
        drawLeftPawn(size, size * 7, canvas)
        drawRightPawn(size * 9, size * 4, canvas)
        drawRightPawn(size * 9, size * 7, canvas)
    }

    private fun initPaint() {
        paintBorder.color = lineColor
        paintBorder.style = Paint.Style.STROKE
        paintBorder.strokeWidth = borderWidthOut
        paint.color = lineColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidthIn
    }

    private fun drawSmallLine(dX: Float, dY: Float, canvas: Canvas) {
        canvas.drawLine(dX - 10, dY - 10, dX - 30, dY - 10, paint)
        canvas.drawLine(dX - 10, dY + 10, dX - 30, dY + 10, paint)
        canvas.drawLine(dX + 10, dY - 10, dX + 30, dY - 10, paint)
        canvas.drawLine(dX + 10, dY + 10, dX + 30, dY + 10, paint)
        canvas.drawLine(dX - 10, dY - 10, dX - 10, dY - 30, paint)
        canvas.drawLine(dX - 10, dY + 10, dX - 10, dY + 30, paint)
        canvas.drawLine(dX + 10, dY - 10, dX + 10, dY - 30, paint)
        canvas.drawLine(dX + 10, dY + 10, dX + 10, dY + 30, paint)
    }

    private fun drawLeftPawn(dX: Float, dY: Float, canvas: Canvas) {
        canvas.drawLine(dX + 10, dY - 10, dX + 30, dY - 10, paint)
        canvas.drawLine(dX + 10, dY + 10, dX + 30, dY + 10, paint)
        canvas.drawLine(dX + 10, dY - 30, dX + 10, dY - 10, paint)
        canvas.drawLine(dX + 10, dY + 30, dX + 10, dY + 10, paint)
    }

    private fun drawRightPawn(dX: Float, dY: Float, canvas: Canvas) {
        canvas.drawLine(dX - 10, dY - 10, dX - 30, dY - 10, paint)
        canvas.drawLine(dX - 10, dY + 10, dX - 30, dY + 10, paint)
        canvas.drawLine(dX - 10, dY - 30, dX - 10, dY - 10, paint)
        canvas.drawLine(dX - 10, dY + 30, dX - 10, dY + 10, paint)
    }

    private fun drawHorizontalLine(canvas: Canvas) {
        for (i in 2..9) {
            canvas.drawLine(size, size * i, size * 9, size * i, paint)
        }
    }

    private fun drawVerticalLine(canvas: Canvas) {
        for (i in 2..8) {
            canvas.drawLine(size * i, size * 1, size * i, size * 5, paint)
            canvas.drawLine(size * i, size * 6, size * i, size * 10, paint)
        }
    }

    private fun drawDiagonalLine(dX: Float, dY: Float, canvas: Canvas) {
        canvas.drawLine(dX - size, dY - size, dX + size, dY + size, paint)
        canvas.drawLine(dX - size, dY + size, dX + size, dY - size, paint)
    }
}
