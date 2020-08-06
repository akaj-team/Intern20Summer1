package com.asiantech.intern20summer1.week7.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.fragments.RegisterFragment.Companion.SHARED_PREFERENCE_FILE
import com.asiantech.intern20summer1.week7.views.HomeActivity
import com.asiantech.intern20summer1.week7.views.LauncherActivity
import kotlinx.android.synthetic.`at-linhle`.fragment_splash.*
import java.util.*

class SplashFragment : Fragment() {

    companion object {
        private const val TIMER_PERIOD = 100L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProgressbar()
    }

    private fun loadProgressbar() {
        var count = 0L
        Timer().schedule(object : TimerTask() {
            override fun run() {
                count++
                progressBar.progress = count.toInt()
                if (count == TIMER_PERIOD) {
                    cancel()
                    if (checkFile()) {
                        (activity as LauncherActivity).openRegisterFragment()
                    } else {
                        val intent = Intent(activity, HomeActivity::class.java)
                        activity?.startActivity(intent)
                        activity?.finish()
                    }
                }
            }
        }, 0, TIMER_PERIOD)
    }

    private fun checkFile(): Boolean {
        val sharedRef = activity?.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        if (sharedRef?.getString("userName", null) == null) {
            return true
        }
        return false
    }
}
