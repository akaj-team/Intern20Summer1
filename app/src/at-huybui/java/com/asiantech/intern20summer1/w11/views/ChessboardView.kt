package com.asiantech.intern20summer1.w11.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 07/09/2020.
 * This is ChessboardView TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class ChessboardView(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {

    companion object {
        private const val NUMBER_COLUMN = 8
        private const val NUMBER_ROW = 9
        private const val MARGIN_INSIDE = 10
        private const val MARGIN_OUTSIDE = 20
    }

    //-- set half of with and height inside
    private var halfWithInside = 0F
    private var halfHeightInside = 0F

    //-- center point
    private lateinit var center: PointF

    //-- paint
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initSize()
        canvas?.let {
            drawStroke(it)
            drawChessBoard(it)
            drawDiagonalLine(it)
            drawCorner(it)
        }
    }

    private fun initPaintStroke() {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
    }

    private fun initSize() {
        halfWithInside = width / 2F - MARGIN_OUTSIDE
        halfHeightInside = halfWithInside + (halfWithInside / NUMBER_COLUMN)
        center = PointF(width / 2F, height / 2F)
    }

    private fun drawStroke(canvas: Canvas) {
        //--point stroke
        center = PointF(width / 2F, height / 2F)
        val halfWithStroke = halfWithInside + MARGIN_INSIDE
        val halfHeightStroke = halfHeightInside + MARGIN_INSIDE
        val rectStroke =
            RectF(center.x - halfWithStroke,
                center.y - halfHeightStroke,
                center.x + halfWithStroke,
                center.y + halfHeightStroke)
        initPaintStroke()
        canvas.run {
            drawRect(rectStroke, paint)
        }
    }

    private fun initPaintBoard() {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3f
    }

    private fun drawChessBoard(canvas: Canvas) {
        val rectChessBoard =
            RectF(center.x - halfWithInside,
                center.y - halfHeightInside,
                center.x + halfWithInside,
                center.y + halfHeightInside)
        initPaintBoard()
        canvas.run {
            drawRect(rectChessBoard, paint)
        }
        canvas.let {
            for (i in 0..NUMBER_ROW) {
                drawLineWithPoint(canvas, coordinatesPoint(0, i), coordinatesPoint(8, i))
            }
        }

        canvas.let {
            for (i in 0..8) {
                drawLineWithPoint(canvas, coordinatesPoint(i, 0), coordinatesPoint(i, 4))
            }
            for (i in 0..8) {
                drawLineWithPoint(canvas, coordinatesPoint(i, 5), coordinatesPoint(i, NUMBER_ROW))
            }
        }
    }

    private fun drawDiagonalLine(canvas: Canvas) {
        drawLineWithPoint(canvas, coordinatesPoint(3, 0), coordinatesPoint(5, 2))
        drawLineWithPoint(canvas, coordinatesPoint(5, 0), coordinatesPoint(3, 2))
        drawLineWithPoint(canvas, coordinatesPoint(3, 7), coordinatesPoint(5, 9))
        drawLineWithPoint(canvas, coordinatesPoint(5, 7), coordinatesPoint(3, 9))
    }

    private fun drawCorner(canvas: Canvas) {
        drawCornerAtPoint(canvas, coordinatesPoint(1, 2))
        drawCornerAtPoint(canvas, coordinatesPoint(7, 2))
        drawCornerAtPoint(canvas, coordinatesPoint(0, 3))
        drawCornerAtPoint(canvas, coordinatesPoint(2, 3))
        drawCornerAtPoint(canvas, coordinatesPoint(4, 3))
        drawCornerAtPoint(canvas, coordinatesPoint(6, 3))
        drawCornerAtPoint(canvas, coordinatesPoint(8, 3))

        drawCornerAtPoint(canvas, coordinatesPoint(1, 7))
        drawCornerAtPoint(canvas, coordinatesPoint(7, 7))
        drawCornerAtPoint(canvas, coordinatesPoint(0, 6))
        drawCornerAtPoint(canvas, coordinatesPoint(2, 6))
        drawCornerAtPoint(canvas, coordinatesPoint(4, 6))
        drawCornerAtPoint(canvas, coordinatesPoint(6, 6))
        drawCornerAtPoint(canvas, coordinatesPoint(8, 6))
    }

    private fun drawCornerAtPoint(canvas: Canvas, p: PointF) {
        val length = 30
        val padding = 10
        when (p.x) {
            center.x - halfWithInside -> {
                canvas.run {
                    drawLine(p.x + padding, p.y - length, p.x + padding, p.y - padding, paint)
                    drawLine(p.x + padding, p.y - padding, p.x + length, p.y - padding, paint)
                    drawLine(p.x + length, p.y + padding, p.x + padding, p.y + padding, paint)
                    drawLine(p.x + padding, p.y + padding, p.x + padding, p.y + length, paint)

                }
            }
            center.x + halfWithInside -> {
                canvas.run {
                    drawLine(p.x - padding, p.y - length, p.x - padding, p.y - padding, paint)
                    drawLine(p.x - padding, p.y - padding, p.x - length, p.y - padding, paint)
                    drawLine(p.x - padding, p.y + length, p.x - padding, p.y + padding, paint)
                    drawLine(p.x - padding, p.y + padding, p.x - length, p.y + padding, paint)
                }
            }
            else -> {
                canvas.run {
                    drawLine(p.x - padding, p.y - length, p.x - padding, p.y - padding, paint)
                    drawLine(p.x - padding, p.y - padding, p.x - length, p.y - padding, paint)
                    drawLine(p.x + padding, p.y - length, p.x + padding, p.y - padding, paint)
                    drawLine(p.x + padding, p.y - padding, p.x + length, p.y - padding, paint)
                    drawLine(p.x + length, p.y + padding, p.x + padding, p.y + padding, paint)
                    drawLine(p.x + padding, p.y + padding, p.x + padding, p.y + length, paint)
                    drawLine(p.x - padding, p.y + length, p.x - padding, p.y + padding, paint)
                    drawLine(p.x - padding, p.y + padding, p.x - length, p.y + padding, paint)
                }
            }
        }
    }

    private fun coordinatesPoint(x: Int, y: Int): PointF {
        val squareSize = halfWithInside / 4
        val zero = PointF(center.x - halfWithInside, center.y - halfHeightInside)
        return PointF(zero.x + (x * squareSize), zero.y + (y * squareSize))
    }

    private fun drawLineWithPoint(canvas: Canvas, startPoint: PointF, stopPoint: PointF) {
        canvas.drawLine(startPoint.x, startPoint.y, stopPoint.x, stopPoint.y, paint)
    }
}
