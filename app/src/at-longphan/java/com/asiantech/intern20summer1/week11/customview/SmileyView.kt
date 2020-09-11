package com.asiantech.intern20summer1.week11.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

open class SmileyView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    private var mSquarePaint: Paint? = null
    private var mCirclePaint: Paint? = null
    private var mEyeAndMouthPaint: Paint? = null
    private var mNosePaint: Paint? = null
    private var mCenterX = 0f
    private var mCenterY = 0f
    private var mRadius = 0f
    private val mArcBounds = RectF()

    constructor(context: Context?) : this(context, null) {}
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)

        val size = w.coerceAtMost(h)

        /** onMeasure(...)
         * call this func at least 1 time or cause crash IllegalStateException
         * setMeasuredDimension(size, size)
         */
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCenterX = w / 2f
        mCenterY = h / 2f
        mRadius = w.coerceAtMost(h) / 2f
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        // draw square
        mNosePaint?.let { canvas?.drawRoundRect( RectF(0f, 0f, 70f, 70f), 7f, 7f, it) }
        // draw circle
        canvas?.drawCircle(mCenterX, mCenterY, mRadius, mCirclePaint!!)
        // draw nose
        canvas?.drawPoint(mCenterX, mCenterY, mNosePaint!!)
        // draw eyes
        val eyeRadius = mRadius / 5f
        val eyeOffsetX = mRadius / 3f
        val eyeOffsetY = mRadius / 3f
        canvas?.drawCircle(
            mCenterX - eyeOffsetX,
            mCenterY - eyeOffsetY,
            eyeRadius,
            mEyeAndMouthPaint!!
        )
        canvas?.drawCircle(
            mCenterX + eyeOffsetX,
            mCenterY - eyeOffsetY,
            eyeRadius,
            mEyeAndMouthPaint!!
        )
        // draw mouth
        val mouthInset = mRadius / 3f
        mArcBounds[mouthInset, mouthInset, mRadius * 2 - mouthInset] = mRadius * 2 - mouthInset
        canvas?.drawArc(mArcBounds, 45f, 90f, false, mEyeAndMouthPaint!!)
    }

    init {
        initPaints()
    }

    private fun initPaints() {
        mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mCirclePaint?.style = Paint.Style.FILL
        mCirclePaint?.color = Color.YELLOW

        mEyeAndMouthPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mEyeAndMouthPaint?.style = Paint.Style.STROKE
        mEyeAndMouthPaint?.strokeWidth = 1 * resources.displayMetrics.density
        mEyeAndMouthPaint?.strokeCap = Paint.Cap.ROUND
        mEyeAndMouthPaint?.color = Color.BLACK

        mNosePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mNosePaint?.strokeWidth = 1 * resources.displayMetrics.density
        mNosePaint?.strokeCap = Paint.Cap.ROUND
        mNosePaint?.color = Color.BLACK

        mSquarePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mSquarePaint?.style
    }
}
