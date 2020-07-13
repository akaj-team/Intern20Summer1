package com.asiantech.intern20summer1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.`at-longphan`.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        handleEditTextEmailIdEvent()
        handleEditTextPasswordEvent()
        handleEditTextRetypePasswordEvent()
        handleListener()
    }

    private fun handleListener() {
        scrollView?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }

        btnSignup.setOnClickListener {
            Toast.makeText(applicationContext, "Sign Up", Toast.LENGTH_SHORT).show()
        }

        tvSignup.setOnClickListener {
            Toast.makeText(applicationContext, "Sign Up!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEditTextEmailIdEvent() {
        edtEmailId.addTextChangedListener {
            val emailId = edtEmailId.text.toString()
            if (emailId.isNotEmpty()) {
                handleValid(
                    isValidEmailId(emailId),
                    imgCheckEmailId,
                    edtEmailId
                )
            }
        }

        edtEmailId.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.custom_edt_focused)
            } else {
                handleValid(
                    isValidEmailId(edtEmailId.text.toString()),
                    imgCheckEmailId,
                    edtEmailId
                )
            }
        }
    }

    private fun handleEditTextPasswordEvent() {
        edtPassword.addTextChangedListener {
            val password = edtPassword.text.toString()
            if (password.isNotEmpty()) {
                handleValid(
                    isValidPassword(password),
                    imgCheckPassword,
                    edtPassword
                )
            }
        }

        edtPassword.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.custom_edt_focused)
            } else {
                handleValid(
                    isValidPassword(edtPassword.text.toString()),
                    imgCheckPassword,
                    edtPassword
                )
            }
        }
    }

    private fun handleEditTextRetypePasswordEvent() {
        edtRetypePassword.addTextChangedListener {
            val password = edtPassword.text.toString()
            val retypePassword = edtRetypePassword.text.toString()
            if (retypePassword.isNotEmpty()) {
                handleValid(
                    password == retypePassword,
                    imgCheckRetypePassword,
                    edtRetypePassword
                )
            }
        }

        edtRetypePassword.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.custom_edt_focused)
            } else {
                handleValid(
                    edtPassword.text.toString() == edtRetypePassword.text.toString(),
                    imgCheckRetypePassword,
                    edtRetypePassword
                )
            }
        }
    }

    private fun handleValid(expression: Boolean, imageView: ImageView, editText: EditText) {
        imageView.visibility = View.VISIBLE
        if (expression) {
            imageView.setImageResource(R.drawable.icon_tick)
            editText.setBackgroundResource(R.drawable.custom_edt_valid)
        } else {
            imageView.setImageResource(R.drawable.icon_error)
            editText.setBackgroundResource(R.drawable.custom_edt_invalid)
        }
    }

    private fun isValidEmailId(email: String): Boolean {
        val regexForEmail =
            "^[a-z][a-z0-9_.]{5,32}[@][a-z0-9]{2,}([.][a-z0-9]{2,4}){1,2}${'$'}".toRegex()
        return email.matches(regexForEmail)
    }

    private fun isValidPassword(password: String): Boolean {
        return if (password.length >= 6 && password[0].isUpperCase()) true else false
    }
}
