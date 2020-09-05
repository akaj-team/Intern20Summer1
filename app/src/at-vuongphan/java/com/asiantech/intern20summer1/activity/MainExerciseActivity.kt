package com.asiantech.intern20summer1.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.w10.ActivityLogin
import com.asiantech.intern20summer1.activity.w3.MainActivity
import com.asiantech.intern20summer1.activity.w4.SplashActivity
import com.asiantech.intern20summer1.activity.w6.ViewPagerActivity
import kotlinx.android.synthetic.`at-vuongphan`.activity_exercise.*

class MainExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        initListenerButtonW3()
        initListenerButtonW4()
        initListenerButtonW6()
        initListenerButtonW10()
    }

    private fun initListenerButtonW3() {
        btnW3?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initListenerButtonW6() {
        btnW6?.setOnClickListener {
            startActivity(Intent(this, ViewPagerActivity::class.java))
        }
    }

    private fun initListenerButtonW4() {
        btnW4.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }
    }

    private fun initListenerButtonW10() {
        btnW10?.setOnClickListener {
            startActivity(Intent(this, ActivityLogin::class.java))
            finish()
        }
    }
}
