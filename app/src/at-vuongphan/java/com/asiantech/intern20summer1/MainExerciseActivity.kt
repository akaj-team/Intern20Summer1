package com.asiantech.intern20summer1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.week12.activity.ActivityLogin
import com.asiantech.intern20summer1.week3.activity.MainActivity
import com.asiantech.intern20summer1.week4.activity.SplashActivity
import com.asiantech.intern20summer1.week6.activity.ViewPagerActivity
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
