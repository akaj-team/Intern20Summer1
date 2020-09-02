package com.asiantech.intern20summer1.w10.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.ApiMainActivity

class SplashFragment : Fragment() {

    companion object {
        internal fun newInstance() = SplashFragment()
    }

    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timer = object : CountDownTimer(50000L, 20L) {
            override fun onTick(p0: Long) {
                count++
                when (count) {
                    100 -> {
                        (activity as ApiMainActivity).replaceFragment(SignInFragment.newInstance())
                    }
                }
            }

            override fun onFinish() {
            }

        }
        timer.start()
    }
}
