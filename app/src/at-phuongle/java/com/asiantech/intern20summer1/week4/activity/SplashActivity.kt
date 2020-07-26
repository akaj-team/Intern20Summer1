package com.asiantech.intern20summer1.week4.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}
