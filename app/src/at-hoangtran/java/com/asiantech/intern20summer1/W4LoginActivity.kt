package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class W4LoginActivity : AppCompatActivity() {
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w4_activity_login)
        showLoginFragment()
    }

    private fun showLoginFragment(){
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.fl_container, W4LoginFragment()).commit()
    }
}
