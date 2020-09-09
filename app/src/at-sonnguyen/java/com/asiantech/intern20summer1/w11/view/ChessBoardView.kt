package com.asiantech.intern20summer1.w11.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChessBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private lateinit var paint: Paint

    companion object {
        private const val START_POINT = 10f
        private const val BLACK_END_POINT = 4
        private const val WHITE_START_POINT = 5
        private const val COLUM_NUMBER = 8
        private const val ROW_NUMBER = 9
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initPaint()
        drawBorder(canvas)
        drawHorizontalLine(canvas)
        drawVerticalLine(canvas)
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
        paint.strokeWidth = 5f
    }
}