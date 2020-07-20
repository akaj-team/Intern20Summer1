package com.asiantech.intern20summer1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragment.LoginFragment

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportFragmentManager.beginTransaction().add(
            R.id.frameLayout,
            LoginFragment()
        )
            .commit()
    }
}
