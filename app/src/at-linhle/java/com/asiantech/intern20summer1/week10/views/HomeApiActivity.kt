package com.asiantech.intern20summer1.week10.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.fragments.LoginFragment.Companion.KEY_STRING_FULL_NAME
import kotlinx.android.synthetic.`at-linhle`.activity_api_home.*

class HomeApiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_home)
        getUser()
    }

    private fun getUser() {
        val fullName = intent.getStringExtra(KEY_STRING_FULL_NAME)
        tvFullName?.text = fullName
    }
}
