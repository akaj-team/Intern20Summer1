package com.asiantech.intern20summer1.w9.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.fragment.SplashFragment

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is activity class for main activity of music application
 */

class MusicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        handleReplaceFragment(SplashFragment.newInstance(), false)
    }

    internal fun handleReplaceFragment(
        fragment: Fragment,
        isBackStack: Boolean = false,
        nameBackStack: String = ""
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerMain, fragment)
        if (isBackStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }
}
