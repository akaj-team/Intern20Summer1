package com.asiantech.intern20summer1.fragmennt.w11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChessBoardCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    companion object {
        private const val MARGIN = 40f
        private const val NUMBER_OF_COLUMNS = 8
        private const val NUMBER_OF_ROWS = 9
        private const val COLUMN_NUMBER_THREE = 3
        private const val DEFAULT_LINE_NUMBER = 1
        private const val WIDTH_SQUARE = 80f
        private const val NUMBER_SQUARE_HORIZONTAL = 8f
        private const val NUMBER_SQUARE_VERTICAL = 9f
        private const val PAINT_STROKE = 4f
    }

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var widthChess = 0f
    private var heightChess = 0f
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        widthChess = (width - WIDTH_SQUARE) / NUMBER_SQUARE_HORIZONTAL
        heightChess = (height - WIDTH_SQUARE) / NUMBER_SQUARE_VERTICAL
        initPaint()
        drawLines(canvas)
    }

    private fun initPaint() {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = MARGIN
    }

    private fun drawLines(canvas: Canvas?) {
        var startXSquare = MARGIN
        var startYSquare = MARGIN
        var lineCount = DEFAULT_LINE_NUMBER
        paintRectangleOutside(canvas)
        paint.strokeWidth = PAINT_STROKE
        paintRectangleInner(canvas)
    }

    private fun paintRectangleOutside(canvas: Canvas?) {
        canvas?.drawLine(0f, 0f, width / 1f, 0f, paint)
        canvas?.drawLine(0f, 0f, 0f, height / 1f, paint)
        canvas?.drawLine(width / 1f, 0f, width / 1f, height / 1f, paint)
        canvas?.drawLine(0f, height / 1f, width / 1f, height / 1f, paint)
    }

    private fun paintRectangleInner(canvas: Canvas?) {
        canvas?.drawLine(MARGIN, MARGIN, width - MARGIN, MARGIN, paint)
        canvas?.drawLine(MARGIN, MARGIN, MARGIN, height - MARGIN, paint)
        canvas?.drawLine(width - MARGIN, MARGIN, width - MARGIN, height - MARGIN, paint)
        canvas?.drawLine(MARGIN, height - MARGIN, width - MARGIN, height - MARGIN, paint)
    }
}
