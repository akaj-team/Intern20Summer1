package com.asiantech.intern20summer1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class W4LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w4_activity_login)
        showLoginFragment()
    }

    fun showLoginFragment(){
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.fl_container, W4LoginFragment())
            .commit()
    }
}