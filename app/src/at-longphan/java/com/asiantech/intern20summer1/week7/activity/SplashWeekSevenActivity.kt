package com.asiantech.intern20summer1.week7.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-longphan`.activity_splash_w7.*

class SplashWeekSevenActivity : AppCompatActivity() {

    var hasAccount = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_w7)

        val sharePref = getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE)
        if(sharePref.getString("userName", null)!= null){
            hasAccount = true
        }
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
                Thread.sleep(100)
                progressBarSplashActivityW7?.progress = progress
            } catch (e: Exception) {
                e.printStackTrace()
            }
            progress += 20
        }
    }

    private fun startApp() {
        val intent = if(hasAccount){
            Intent(this, HomeWeekSevenActivity::class.java)
        } else {
            Intent(this, RegisterWeekSevenActivity::class.java)
        }
        startActivity(intent)
    }
}