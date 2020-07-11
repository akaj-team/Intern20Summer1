package com.asiantech.intern20summer1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.`at-longphan`.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        handleEdtEmailId()
        handleEdtPassword()
        handleEdtRetypePassword()
        handleListener()
    }

    private fun handleListener() {
        btnSignup.setOnClickListener {
            Toast.makeText(applicationContext, "Sign Up", Toast.LENGTH_SHORT).show()
        }
        tvSignup.setOnClickListener {
            Toast.makeText(applicationContext, "Sign Up!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEdtEmailId() {
        edtEmailId.addTextChangedListener {
            val EdtEmailId = edtEmailId.text.toString()
            if (EdtEmailId.isNotEmpty()) {
                imgCheckEmailId.visibility = View.VISIBLE
                if (isValidEmailId(EdtEmailId)) {
                    imgCheckEmailId.setImageResource(R.drawable.icon_tick)
                    edtEmailId.setBackgroundResource(R.drawable.custom_edt_valid)
                } else {
                    imgCheckEmailId.setImageResource(R.drawable.icon_error)
                    edtEmailId.setBackgroundResource(R.drawable.custom_edt_invalid)
                }
            }
        }
    }

    private fun handleEdtPassword(){
        edtPassword.addTextChangedListener {
            val EdtPassword = edtPassword.text.toString()
            if(EdtPassword.isNotEmpty()){
                imgCheckPassword.visibility = View.VISIBLE
                if (isValidPassword(EdtPassword)) {
                    imgCheckPassword.setImageResource(R.drawable.icon_tick)
                    edtPassword.setBackgroundResource(R.drawable.custom_edt_valid)
                } else {
                    imgCheckPassword.setImageResource(R.drawable.icon_error)
                    edtPassword.setBackgroundResource(R.drawable.custom_edt_invalid)
                }
            }
        }
    }

    private fun handleEdtRetypePassword(){
        edtRetypePassword.addTextChangedListener {
            val EdtPassword = edtPassword.text.toString()
            val EdtRetypePassword = edtRetypePassword.text.toString()
            if(EdtRetypePassword.isNotEmpty()){
                imgCheckRetypePassword.visibility = View.VISIBLE
                if (EdtPassword.equals(EdtRetypePassword)) {
                    imgCheckRetypePassword.setImageResource(R.drawable.icon_tick)
                    edtRetypePassword.setBackgroundResource(R.drawable.custom_edt_valid)
                } else {
                    imgCheckRetypePassword.setImageResource(R.drawable.icon_error)
                    edtRetypePassword.setBackgroundResource(R.drawable.custom_edt_invalid)
                }
            }
        }
    }

    private fun isValidEmailId(email: String): Boolean {
        val regexForEmail =
            "^[a-z][a-z0-9_.]{5,31}[@][a-z0-9]{2,}(.[a-z0-9]{2,4}){1,2}${'$'}".toRegex()
            /*"^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}${'$'}".toRegex()*/
        return email.matches(regexForEmail)
    }

    private fun isValidPassword(password: String): Boolean {
        val regexForPassword =
            "^[A-Z]{1}[a-zA-Z0-9_.]{5,31}${'$'}".toRegex()
        return password.matches(regexForPassword)
    }
}
