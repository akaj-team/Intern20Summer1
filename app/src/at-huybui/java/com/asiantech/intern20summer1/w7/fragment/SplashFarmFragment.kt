package com.asiantech.intern20summer1.w7.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.MainFarmActivity
import kotlinx.android.synthetic.`at-huybui`.fragment_splash_farm.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/04/20
 * This is fragment class for splash fragments
 */


class SplashFarmFragment : Fragment() {

    companion object {
        private const val SPLASH_TIMER = 10000L
        private const val PROGRESS_TIMER_STEP = 40L
        private const val PROGRESS_MAX_VALUE = 100
        internal fun newInstance() = SplashFarmFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_farm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleForProgressBar()
    }

    private fun handleForProgressBar() {
        progressBarFarm?.progress = 0
        object : CountDownTimer(SPLASH_TIMER, PROGRESS_TIMER_STEP) {
            override fun onTick(millisUntilFinished: Long) {
                progressBarFarm?.progress = progressBarFarm.progress + 1
                if (progressBarFarm?.progress == PROGRESS_MAX_VALUE) {
                    (activity as MainFarmActivity).handleReplaceFragment(RegisterFarmFragment.newInstance())
                }
            }

            override fun onFinish() {}
        }.start()
    }
}
