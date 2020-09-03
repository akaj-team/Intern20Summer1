package com.asiantech.intern20summer1.w10.fragment

import android.content.Context.MODE_PRIVATE
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
        private const val NAME_PREFERENCE = "preference"
        private const val KEY_IS_LOGIN = "key_is_login"
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
        handleProgressSplash()

    }

    private fun handleProgressSplash(){
        val preference = requireContext().getSharedPreferences(NAME_PREFERENCE, MODE_PRIVATE)
        val isSignIn = preference.getBoolean(KEY_IS_LOGIN, false)

        val timer = object : CountDownTimer(50000L, 20L) {
            override fun onTick(p0: Long) {
                count++
                when (count) {
                    100 -> {
                        if (isSignIn) {
                         //   (activity as ApiMainActivity).replaceFragment(HomeFragment.newInstance())
                        } else {
                            (activity as ApiMainActivity).replaceFragment(SignInFragment.newInstance())
                        }
                    }
                }
            }
            override fun onFinish() {
            }

        }
        timer.start()

    }
}
