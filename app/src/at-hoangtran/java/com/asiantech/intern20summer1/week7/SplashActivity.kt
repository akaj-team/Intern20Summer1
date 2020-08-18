package com.asiantech.intern20summer1.week7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        showSplashFragment()
    }

    private fun showSplashFragment() {
        val transSplash = supportFragmentManager.beginTransaction()
        transSplash.add(R.id.fl_container, SplashFragment()).commit()
    }

    internal fun showRegisterFragment() {
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.fl_container, RegisterFragment()).commit()
    }
}
