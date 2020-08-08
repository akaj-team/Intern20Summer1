package com.asiantech.intern20summer1.w7.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.activity.HomeActivity
import com.asiantech.intern20summer1.w7.fragment.RegisterFragment.Companion.SHARED_PREFERENCE_FILE
import com.asiantech.intern20summer1.w7.fragment.RegisterFragment.Companion.SHARED_PREFERENCE_USER_NAME_KEY
import kotlinx.android.synthetic.`at-sonnguyen`.w7_fragment_splash.*

class FlashFragment : Fragment() {
    companion object {
        fun newInstance() = FlashFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        imgSplash.setOnClickListener {
            progressBarSplash.progress += 1
        }
        timer.start()
    }

    private fun openRegisterFragment() {
        fragmentManager?.beginTransaction().let {
            it?.replace(R.id.frameLayoutMain, RegisterFragment.newInstance())
            it?.commit()
        }
    }

    private val timer = object : CountDownTimer(10000, 100) {
        override fun onTick(millisUntilFinished: Long) {
            progressBarSplash.progress += 1
        }

        override fun onFinish() {
            if (checkAccount()){
                val intent = Intent(activity,HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                openRegisterFragment()
            }
        }
    }
    private fun checkAccount() : Boolean{
        val sharedRef = activity?.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        if (sharedRef?.getString(SHARED_PREFERENCE_USER_NAME_KEY, null) != null) {
            return true
        }
        return false
    }
}