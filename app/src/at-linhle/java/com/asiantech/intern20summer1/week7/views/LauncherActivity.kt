package com.asiantech.intern20summer1.week7.views

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.extensions.changeColorStatusBar
import com.asiantech.intern20summer1.week7.fragments.RegisterFragment
import com.asiantech.intern20summer1.week7.fragments.SplashFragment

class LauncherActivity : AppCompatActivity() {
    private val registerFragment = RegisterFragment.newInstance()
    private val splashFragment = SplashFragment.newInstance()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        openSplashFragment()
        changeColorStatusBar(window, titleColor)
    }

    internal fun openRegisterFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flLauncherContainer, registerFragment).commit()
    }

    private fun openSplashFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flLauncherContainer, splashFragment).commit()
    }
}
