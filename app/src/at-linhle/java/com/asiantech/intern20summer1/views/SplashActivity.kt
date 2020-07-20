package com.asiantech.intern20summer1.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R


class SplashActivity : AppCompatActivity() {
    companion object {
        private const val DELAY_SECOND_SPLASH: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(Intent(this, SignInActivity::class.java))
        }, DELAY_SECOND_SPLASH)
    }
}
