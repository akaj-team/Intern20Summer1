package com.asiantech.intern20summer1.w11.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.asiantech.intern20summer1.w11.model.Position

class ChessBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private lateinit var paint: Paint
    private lateinit var markedPaint: Paint
    private var points = arrayListOf<Position>(
        Position(1, 2),
        Position(7, 2),
        Position(2, 3),
        Position(4, 3),
        Position(6, 3),
        Position(1, 7),
        Position(7, 7),
        Position(2, 6),
        Position(4, 6),
        Position(6, 6)
    )

    companion object {
        private const val START_POINT = 3f
        private const val BLACK_END_POINT = 4
        private const val WHITE_START_POINT = 5
        private const val COLUM_NUMBER = 8
        private const val ROW_NUMBER = 9
        private const val DIAGONAL_START_POINT = 3
        private const val DIAGONAL_END_POINT = 5
        private const val WHITE_DIAGONAL_START_POINT = 7
        private const val MARKED_POINT_MARGIN = 10f
        private const val MARKED_LENGTH = 30f
        private const val BORDER_LINE_STROKE_WIDTH = 5f
        private const val MARKED_POINT_LINE_STROKE_WIDTH = 3f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initPaint()
        initMarkedPaint()
        drawBorder(canvas)
        drawHorizontalLine(canvas)
        drawVerticalLine(canvas)
        drawDiagonalLine(canvas)
        markTwoPointLeft(canvas, 3)
        markTwoPointLeft(canvas, 6)
        markTwoPointRight(canvas, 3)
        markTwoPointRight(canvas, 6)
        points.forEach{ it ->
            markFourPoint(canvas,it.x , it.y)
        }
    }

    private fun drawHorizontalLine(canvas: Canvas?) {
        for (i in 1..ROW_NUMBER) {
            canvas?.drawLine(
                START_POINT,
                width.toFloat() / COLUM_NUMBER * i,
                width.toFloat(),
                width.toFloat() / COLUM_NUMBER * i,
                paint
            )
        }
    }

    private fun drawVerticalLine(canvas: Canvas?) {
        for (i in 1..COLUM_NUMBER) {
            canvas?.drawLine(
                width.toFloat() / COLUM_NUMBER * i,
                START_POINT,
                width.toFloat() / COLUM_NUMBER * i,
                width.toFloat() / COLUM_NUMBER * BLACK_END_POINT,
                paint
            )
            canvas?.drawLine(
                width.toFloat() / COLUM_NUMBER * i,
                width.toFloat() / COLUM_NUMBER * WHITE_START_POINT,
                width.toFloat() / COLUM_NUMBER * i,
                width.toFloat() * ROW_NUMBER / COLUM_NUMBER,
                paint
            )
        }
    }

    private fun drawDiagonalLine(canvas: Canvas?) {
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * DIAGONAL_START_POINT,
            START_POINT,
            width.toFloat() / COLUM_NUMBER * DIAGONAL_END_POINT,
            width.toFloat() / COLUM_NUMBER * 2,
            paint
        )
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * DIAGONAL_START_POINT,
            width.toFloat() / COLUM_NUMBER * 2,
            width.toFloat() / COLUM_NUMBER * DIAGONAL_END_POINT,
            START_POINT,
            paint
        )
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * DIAGONAL_START_POINT,
            width.toFloat() / COLUM_NUMBER * WHITE_DIAGONAL_START_POINT,
            width.toFloat() / COLUM_NUMBER * DIAGONAL_END_POINT,
            width.toFloat() / COLUM_NUMBER * ROW_NUMBER,
            paint
        )
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * DIAGONAL_START_POINT,
            width.toFloat() / COLUM_NUMBER * ROW_NUMBER,
            width.toFloat() / COLUM_NUMBER * DIAGONAL_END_POINT,
            width.toFloat() / COLUM_NUMBER * WHITE_DIAGONAL_START_POINT,
            paint
        )
    }

    private fun markTwoPointLeft(canvas: Canvas?, y: Int) {
        canvas?.drawLine(
            START_POINT + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            START_POINT + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_LENGTH,
            markedPaint
        )
        canvas?.drawLine(
            START_POINT + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            START_POINT + MARKED_LENGTH,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            markedPaint
        )
        canvas?.drawLine(
            START_POINT + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            START_POINT + MARKED_LENGTH,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            markedPaint
        )
        canvas?.drawLine(
            START_POINT + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            START_POINT + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_LENGTH,
            markedPaint
        )
    }

    private fun markFourPoint(canvas: Canvas?, x: Int, y: Int) {
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * x + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * x + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_LENGTH,
            markedPaint
        )
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * x + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * x + MARKED_LENGTH,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            markedPaint
        )
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * x + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * x + MARKED_LENGTH,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            markedPaint
        )
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * x + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * x + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_LENGTH,
            markedPaint
        )
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * x - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * x - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_LENGTH,
            markedPaint
        )
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * x - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * x - MARKED_LENGTH,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            markedPaint
        )
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * x - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * x - MARKED_LENGTH,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            markedPaint
        )
        canvas?.drawLine(
            width.toFloat() / COLUM_NUMBER * x - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * x - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_LENGTH,
            markedPaint
        )
    }

    private fun markTwoPointRight(canvas: Canvas?, y: Int) {
        canvas?.drawLine(
            width.toFloat() - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            width.toFloat() - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_LENGTH,
            markedPaint
        )
        canvas?.drawLine(
            width.toFloat() - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            width.toFloat() - MARKED_LENGTH,
            width.toFloat() / COLUM_NUMBER * y - MARKED_POINT_MARGIN,
            markedPaint
        )
        canvas?.drawLine(
            width.toFloat() - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            width.toFloat() - MARKED_LENGTH,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            markedPaint
        )
        canvas?.drawLine(
            width.toFloat() - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_POINT_MARGIN,
            width.toFloat() - MARKED_POINT_MARGIN,
            width.toFloat() / COLUM_NUMBER * y + MARKED_LENGTH,
            markedPaint
        )
    }

    private fun drawBorder(canvas: Canvas?) {
        // ngang 1
        canvas?.drawLine(START_POINT, START_POINT, width.toFloat(), START_POINT, paint)

        // doc trai
        canvas?.drawLine(
            START_POINT,
            START_POINT,
            START_POINT,
            width.toFloat() / COLUM_NUMBER * ROW_NUMBER,
            paint
        )
        //doc phai
        canvas?.drawLine(
            width.toFloat() - 2,
            START_POINT,
            width.toFloat() - 2,
            width.toFloat() / COLUM_NUMBER * ROW_NUMBER,
            paint
        )

        // ngang duoi
        canvas?.drawLine(
            START_POINT,
            width.toFloat() / COLUM_NUMBER * ROW_NUMBER,
            width.toFloat(),
            width.toFloat() / COLUM_NUMBER * ROW_NUMBER,
            paint
        )
    }

    private fun initPaint() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        paint.strokeWidth = BORDER_LINE_STROKE_WIDTH
    }

    private fun initMarkedPaint() {
        markedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        markedPaint.color = Color.BLACK
        markedPaint.strokeWidth = MARKED_POINT_LINE_STROKE_WIDTH
    }
}
