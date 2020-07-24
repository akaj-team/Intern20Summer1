package com.asiantech.intern20summer1.week4.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.extensions.changeColorStatusBar

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val DELAY_SECOND_SPLASH: Long = 3000
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        changeColorStatusBar(window, titleColor)
        handleChangeActivityDelay()
    }

    private fun handleChangeActivityDelay() {
        Handler().postDelayed({
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(Intent(this, SignInActivity::class.java))
        }, DELAY_SECOND_SPLASH)
    }
}
