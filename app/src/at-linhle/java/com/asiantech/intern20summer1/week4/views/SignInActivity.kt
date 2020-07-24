package com.asiantech.intern20summer1.week4.views

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.extensions.changeColorStatusBar
import com.asiantech.intern20summer1.week4.fragments.SignInFragment

class SignInActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        changeColorStatusBar(window, titleColor)
        openSignIn()
    }

    private fun openSignIn() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flContainer, SignInFragment())
            .commit()
    }
}
