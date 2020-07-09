package com.asiantech.intern20summer1

import android.R
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.asiantech.intern20summer1.R.layout.activity_main)
    }

    fun onSignUp(v: View?) {
        Toast.makeText(applicationContext,"Sign up!",Toast.LENGTH_SHORT).show()
    }

    fun onSignUpBtn(view: View?) {
        Toast.makeText(applicationContext,"Sign Up",Toast.LENGTH_SHORT).show()
    }
}
