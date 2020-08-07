package com.asiantech.intern20summer1.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.MainActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-vuongphan`.activity_exercise.*

class MainExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        initListenerButtonW7()
        initListenerButtonW3()
        initListenerButtonW4()
        initListenerButtonW6()
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
        btnW4?.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }
    }

    private fun initListenerButtonW7() {
        btnW1?.setOnClickListener {
            startActivity(Intent(this, VegetableSplashActivity::class.java))
        }
    }
}
