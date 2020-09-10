package com.asiantech.intern20summer1.week11.customs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChessBoardCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    companion object {
        private const val MARGIN = 40f
        private const val NUMBER_OF_COLUMNS = 8
        private const val NUMBER_OF_ROWS = 9
        private const val COLUMN_NUMBER_THREE = 3
        private const val DEFAULT_LINE_NUMBER = 1
    }

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var widthSquare = 0f
    private var heightSquare = 0f
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        widthSquare = (width - 80f) / 8f
        heightSquare = (height - 80f) / 9f
        initPaint()
        drawLines(canvas)
    }

    private fun initPaint() {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 40f
    }

    private fun drawLines(canvas: Canvas?) {
        var startXSquare = MARGIN
        var startYSquare = MARGIN
        var lineCount = DEFAULT_LINE_NUMBER
        canvas?.drawLine(0f, 0f, width / 1f, 0f, paint)
        canvas?.drawLine(0f, 0f, 0f, height / 1f, paint)
        canvas?.drawLine(width / 1f, 0f, width / 1f, height / 1f, paint)
        canvas?.drawLine(0f, height / 1f, width / 1f, height / 1f, paint)
        paint.strokeWidth = 4f
        canvas?.drawLine(
            MARGIN,
            MARGIN,
            width - MARGIN,
            MARGIN,
            paint
        )
        canvas?.drawLine(MARGIN, MARGIN, MARGIN, height - MARGIN, paint)
        canvas?.drawLine(
            width - MARGIN,
            MARGIN,
            width - MARGIN,
            height - MARGIN,
            paint
        )
        canvas?.drawLine(
            MARGIN,
            height - MARGIN,
            width - MARGIN,
            height - MARGIN,
            paint
        )
        while (lineCount < NUMBER_OF_ROWS) {
            startYSquare += heightSquare
            canvas?.drawLine(
                MARGIN,
                startYSquare,
                width - MARGIN,
                startYSquare,
                paint
            )
            lineCount++
        }
        lineCount = DEFAULT_LINE_NUMBER
        while (lineCount < NUMBER_OF_COLUMNS) {
            startXSquare += widthSquare
            when (lineCount) {
                COLUMN_NUMBER_THREE -> {
                    canvas?.drawLine(
                        startXSquare,
                        MARGIN,
                        MARGIN + widthSquare * 5,
                        MARGIN + heightSquare * 2,
                        paint
                    )
                    canvas?.drawLine(
                        startXSquare,
                        MARGIN + heightSquare * 2,
                        startXSquare + widthSquare * 2,
                        MARGIN,
                        paint
                    )
                }
            }
            canvas?.drawLine(startXSquare, MARGIN, startXSquare, heightSquare * 4 + MARGIN, paint)
            lineCount++
        }
        lineCount = DEFAULT_LINE_NUMBER
        startXSquare = MARGIN
        while (lineCount < NUMBER_OF_COLUMNS) {
            startXSquare += widthSquare
            when (lineCount) {
                COLUMN_NUMBER_THREE -> {
                    canvas?.drawLine(
                        startXSquare,
                        height - MARGIN,
                        startXSquare + widthSquare * 2,
                        height - MARGIN - heightSquare * 2,
                        paint
                    )
                    canvas?.drawLine(
                        startXSquare,
                        MARGIN + heightSquare * 7,
                        startXSquare + widthSquare * 2,
                        heightSquare * 9 + MARGIN,
                        paint
                    )
                }
            }
            canvas?.drawLine(
                startXSquare,
                heightSquare * 5 + MARGIN,
                startXSquare,
                height - MARGIN,
                paint
            )
            lineCount++
        }
        drawCorner(7 * widthSquare, 2 * heightSquare, canvas)
        drawCorner(widthSquare, 2 * heightSquare, canvas)

        for (i in 0..8 step 2) {
            drawCorner(i * widthSquare, 3 * heightSquare, canvas)
            drawCorner(i * widthSquare, 6 * heightSquare, canvas)
        }
        drawCorner(widthSquare, 7 * heightSquare, canvas)
        drawCorner(7 * widthSquare, 7 * heightSquare, canvas)

    }

    private fun drawCorner(coordinatesX: Float, coordinatesY: Float, canvas: Canvas?) {
        when (coordinatesX) {
            NUMBER_OF_COLUMNS * widthSquare -> {
                canvas?.apply {
                    drawLine(
                        coordinatesX + 30f,
                        coordinatesY + 30f,
                        coordinatesX + 30f,
                        coordinatesY,
                        paint
                    )
                    drawLine(
                        coordinatesX + 30f,
                        coordinatesY + 30f,
                        coordinatesX,
                        coordinatesY + 30f,
                        paint
                    )
                    drawLine(
                        coordinatesX + 30f,
                        coordinatesY + 50f,
                        coordinatesX + 30f,
                        coordinatesY + 80f,
                        paint
                    )
                    drawLine(
                        coordinatesX + 30f,
                        coordinatesY + 50f,
                        coordinatesX,
                        coordinatesY + 50f,
                        paint
                    )
                }
            }
            0f -> {
                canvas?.apply {
                    drawLine(
                        coordinatesX + 50f,
                        coordinatesY + 30f,
                        coordinatesX + 50f,
                        coordinatesY,
                        paint
                    )
                    drawLine(
                        coordinatesX + 50f,
                        coordinatesY + 30f,
                        coordinatesX + 80f,
                        coordinatesY + 30f,
                        paint
                    )
                    drawLine(
                        coordinatesX + 50f,
                        coordinatesY + 50f,
                        coordinatesX + 50f,
                        coordinatesY + 80f,
                        paint
                    )
                    drawLine(
                        coordinatesX + 50f,
                        coordinatesY + 50f,
                        coordinatesX + 80f,
                        coordinatesY + 50f,
                        paint
                    )
                }
            }
            else -> {
                canvas?.apply {
                    drawLine(
                        coordinatesX + 30f,
                        coordinatesY + 30f,
                        coordinatesX + 30f,
                        coordinatesY,
                        paint
                    )
                    drawLine(
                        coordinatesX + 30f,
                        coordinatesY + 30f,
                        coordinatesX,
                        coordinatesY + 30f,
                        paint
                    )
                    drawLine(
                        coordinatesX + 50f,
                        coordinatesY + 30f,
                        coordinatesX + 50f,
                        coordinatesY,
                        paint
                    )
                    drawLine(
                        coordinatesX + 50f,
                        coordinatesY + 30f,
                        coordinatesX + 80f,
                        coordinatesY + 30f,
                        paint
                    )
                    drawLine(
                        coordinatesX + 30f,
                        coordinatesY + 50f,
                        coordinatesX + 30f,
                        coordinatesY + 80f,
                        paint
                    )
                    drawLine(
                        coordinatesX + 30f,
                        coordinatesY + 50f,
                        coordinatesX,
                        coordinatesY + 50f,
                        paint
                    )
                    drawLine(
                        coordinatesX + 50f,
                        coordinatesY + 50f,
                        coordinatesX + 50f,
                        coordinatesY + 80f,
                        paint
                    )
                    drawLine(
                        coordinatesX + 50f,
                        coordinatesY + 50f,
                        coordinatesX + 80f,
                        coordinatesY + 50f,
                        paint
                    )
                }
            }
        }
    }
}
