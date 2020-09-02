package com.asiantech.intern20summer1.week10

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.fragment_login.*

class LoginActivity : AppCompatActivity() {
    @SuppressLint("ShowToast", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        showLoginFragment()
    }

    private fun showLoginFragment() {
        val trans = supportFragmentManager.beginTransaction()
        val fragment = LoginFragment()
        trans.replace(R.id.fl_container, fragment).commit()
    }

    internal fun showSignUpFragment() {
        tv_signUp.setOnClickListener {
            val trans = supportFragmentManager.beginTransaction()
            trans.add(
                R.id.fl_container, SignUpFragment()
            )
                .addToBackStack(null)
                .hide(LoginFragment())
                .commit()
        }
    }
}
