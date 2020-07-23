package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-hoangtran`.activity_main.*
import kotlinx.android.synthetic.`at-hoangtran`.w4_activity_login.*

class W4LoginActivity : AppCompatActivity() {
    @SuppressLint("ShowToast", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w4_activity_login)
        showLoginFragment()
        fl_container?.setOnTouchListener { it, _ ->
            it.requestFocus()
            this.hideSoftKeyboard()
            true
        }
    }

    private fun showLoginFragment(){
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.fl_container, W4LoginFragment()).commit()
    }
}
