package com.asiantech.intern20summer1.w11.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
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

    private val screenWidth = context?.getScreenWidth() ?: 0
    private val screenHeight = context?.getScreenHeight() ?: 0

    private val rectF =
        RectF(screenWidth / 4F, screenHeight / 4F, screenWidth * 3 / 4F, screenHeight * 3 / 4F)
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initPaint()
        canvas?.run {
            drawRect(rectF, paint)
        }
    }

    private fun initPaint() {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 40f
    }
}
