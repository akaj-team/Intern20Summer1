package com.asiantech.intern20summer1.w9.views

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.util.AttributeSet

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is cuctom view class for dvd view
 */

class DvdRotateView(context: Context?, attrs: AttributeSet?) :
    de.hdodenhof.circleimageview.CircleImageView(context, attrs) {

    companion object {
        private const val START = 0.0F
        private const val END = 360.0F
        private const val ANIM_MODE = "rotation"
        private const val DURATION = 20000L
    }

    private var animRotate = ObjectAnimator()
    fun createAnim() {
        animRotate = ObjectAnimator.ofFloat(this, ANIM_MODE, START, END)
        animRotate.duration = DURATION
        animRotate.repeatCount = ObjectAnimator.INFINITE
        animRotate.repeatMode = ObjectAnimator.RESTART
    }

    internal fun startAnim() {
        animRotate.start()
    }

    internal fun pauseAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animRotate.pause()
        }
    }

    internal fun resumeAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animRotate.resume()
        }
    }

    internal fun endAnim() {
        animRotate.end()
    }
}
