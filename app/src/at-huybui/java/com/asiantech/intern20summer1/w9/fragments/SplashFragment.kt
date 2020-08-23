package com.asiantech.intern20summer1.w9.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.activitys.MusicActivity
import kotlinx.android.synthetic.`at-huybui`.w9_fragment_splash.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class SplashFragment : Fragment() {

    companion object {
        internal fun newInstance() = SplashFragment()
        private const val VALUE_TIMER = 20000L
        private const val TICK_TIMER = 20L
        private const val PROGRESS_BAR_MAX_VALUE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleProgressBar()
    }

    private fun handleProgressBar() {
        object : CountDownTimer(VALUE_TIMER, TICK_TIMER) {
            override fun onFinish() {}

            override fun onTick(p0: Long) {
                progressBarMusic.progress = progressBarMusic.progress.plus(2)
                when (progressBarMusic.progress) {
                    PROGRESS_BAR_MAX_VALUE -> {
                        (activity as MusicActivity).handleReplaceFragment(
                            MusicFragment.newInstance(),
                            false
                        )
                        this.cancel()
                    }
                }
            }
        }.start()
    }
}
