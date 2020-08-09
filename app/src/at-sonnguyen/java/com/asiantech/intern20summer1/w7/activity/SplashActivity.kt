package com.asiantech.intern20summer1.w7.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.extension.replaceFragment
import com.asiantech.intern20summer1.w7.fragment.SplashFragment

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w7_activity_splash)
        replaceFragment(R.id.frameLayoutMain, SplashFragment.newInstance(),false)
    }
}
