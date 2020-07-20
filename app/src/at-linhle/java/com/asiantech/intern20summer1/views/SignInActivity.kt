package com.asiantech.intern20summer1.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragments.SignInFragment

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        openSignIn()
    }

    private fun openSignIn() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flContainer, SignInFragment())
            .commit()
    }
}
