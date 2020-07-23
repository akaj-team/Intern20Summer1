package com.asiantech.intern20summer1.w4.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val TIME_FINISH_SPLASH = 5000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val runnable = Runnable {
            val intent = Intent(this@SplashActivity, MainActivityFour::class.java)
            startActivity(intent)
        }
        Handler().postDelayed(runnable, TIME_FINISH_SPLASH)
    }

    override fun onStop() {
        finish()
        super.onStop()
    }
}
