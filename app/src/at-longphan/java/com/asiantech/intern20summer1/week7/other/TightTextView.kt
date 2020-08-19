package com.asiantech.intern20summer1.week7.other

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import kotlin.math.ceil
import kotlin.math.max

@SuppressLint("AppCompatCustomView")
class TightTextView(context: Context?, attrs: AttributeSet?, defStyle: Int) :
    TextView(context, attrs, defStyle) {

    private var hasMaxWidth = false

    constructor(context: Context?) : this(context, null, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (hasMaxWidth) {
            val specModeW = MeasureSpec.getMode(widthMeasureSpec)
            if (specModeW != MeasureSpec.EXACTLY) {
                val layout = layout
                val linesCount = layout.lineCount
                if (linesCount > 1) {
                    var textRealMaxWidth = 0f
                    for (n in 0 until linesCount) {
                        textRealMaxWidth =
                            max(textRealMaxWidth, layout.getLineWidth(n))
                    }
                    val w = ceil(textRealMaxWidth).toInt()
                    if (w < measuredWidth) {
                        super.onMeasure(
                            MeasureSpec.makeMeasureSpec(w, MeasureSpec.AT_MOST),
                            heightMeasureSpec
                        )
                    }
                }
            }
        }
    }

    override fun setMaxWidth(maxpixels: Int) {
        super.setMaxWidth(maxpixels)
        hasMaxWidth = true
    }

    override fun setMaxEms(maxems: Int) {
        super.setMaxEms(maxems)
        hasMaxWidth = true
    }
}
