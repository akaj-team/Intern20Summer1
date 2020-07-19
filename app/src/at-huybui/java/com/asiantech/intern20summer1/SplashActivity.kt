package com.asiantech.intern20summer1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val TIME_FINISH_SPLASH = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val runnable = Runnable {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }
        Handler().postDelayed(runnable, TIME_FINISH_SPLASH.toLong())
    }

    override fun onStop() {
        finish()
        super.onStop()
    }
}
