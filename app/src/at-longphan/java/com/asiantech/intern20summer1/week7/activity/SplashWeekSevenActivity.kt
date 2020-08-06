package com.asiantech.intern20summer1.week7.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-longphan`.activity_splash_w7.*

class SplashWeekSevenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_w7)
        Thread(Runnable {
            runProgressBar()
            startApp()
            finish()
        }).start()
    }

    private fun runProgressBar() {
        var progress = 0
        while (progress <= 100) {
            try {
                Thread.sleep(50)
                progressBarSplashActivityW7.progress = progress
            } catch (e: Exception) {
                e.printStackTrace()
            }
            progress += 20
        }
    }

    private fun startApp() {
        val intent = Intent(this, RegisterWeekSevenActivity::class.java)
        startActivity(intent)
    }
}