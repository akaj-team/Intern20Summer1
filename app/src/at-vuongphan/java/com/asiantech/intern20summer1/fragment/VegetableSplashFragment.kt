package com.asiantech.intern20summer1.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.VegetableSplashActivity
import kotlinx.android.synthetic.`at-vuongphan`.w7_splash_fragment.*

class VegetableSplashFragment : Fragment() {
    companion object {
        const val START = 5000L
        const val STEP_PROGRESS_BAR = 120L
        const val STEP_FINISH = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressSplash?.progress = 0
        val timer = object : CountDownTimer(
            START,
            START / STEP_PROGRESS_BAR
        ) {
            override fun onTick(p0: Long) {
                progressSplash?.progress = progressSplash.progress + 1
                if (progressSplash?.progress == STEP_FINISH) {
                    (activity as? VegetableSplashActivity)?.openFragment(VegetableRegisterFragment.newInstance())
                    this.cancel()
                }
            }

            override fun onFinish() {
            }
        }
        timer.start()
    }
}
