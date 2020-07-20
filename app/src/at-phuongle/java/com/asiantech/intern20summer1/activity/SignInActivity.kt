package com.asiantech.intern20summer1.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragment.LoginFragment
import com.asiantech.intern20summer1.fragment.RegisterFragment

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportFragmentManager.beginTransaction().add(R.id.frameLayout, LoginFragment()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, RegisterFragment())
            .addToBackStack("LoginFragment")
            .commit()
    }
}
