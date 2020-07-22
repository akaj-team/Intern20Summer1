package com.asiantech.intern20summer1.w4.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    companion object {
        private const val DELAY_TIME_TO_CHANGE_ACTIVITY = 5000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_TIME_TO_CHANGE_ACTIVITY.toLong())
    }
}
