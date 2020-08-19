package com.asiantech.intern20summer1.week4.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.fragment.SignInFragment

class SignInActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        configStatusBarColor()
        addSignInFragment()
    }

    private fun configStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun addSignInFragment() {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.fragmentContainer, SignInFragment())
        transaction.commit()
    }
}
