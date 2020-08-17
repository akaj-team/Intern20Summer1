package com.asiantech.intern20summer1.week3

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.hideKeyboard
import kotlinx.android.synthetic.`at-longphan`.activity_login_w3.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_w3)
        configStatusBarColor()
        handleListeners()
    }

    private fun handleListeners() {
        handleLinearLayoutChildListener()
        handleButtonSignUpListener()
        handleEditTextEmailIdEvent()
        handleEditTextPasswordEvent()
        handleEditTextRetypePasswordEvent()
        handleTextViewSignUpListener()
    }

    private fun configStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun handleLinearLayoutChildListener(){
        llChild?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun handleTextViewSignUpListener(){
        tvSignUp.setOnClickListener {
            Toast.makeText(applicationContext, "Sign Up!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleButtonSignUpListener(){
        btnSignUp.setOnClickListener {
            Toast.makeText(applicationContext, "Sign Up", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEditTextEmailIdEvent() {
        edtEmailId.addTextChangedListener {
            val emailId = it.toString()
            if (emailId.isNotEmpty()) {
                handleValid(isValidEmailId(emailId), imgCheckEmailId, edtEmailId)
            }
        }

        edtEmailId.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.bg_edit_text_focused)
            } else {
                handleValid(isValidEmailId(edtEmailId.text.toString()), imgCheckEmailId, edtEmailId)
            }
        }
    }

    private fun handleEditTextPasswordEvent() {
        edtPassword.addTextChangedListener {
            val password = it.toString()
            if (password.isNotEmpty()) {
                handleValid(isValidPassword(password), imgCheckPassword, edtPassword)
            }
        }

        edtPassword.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.bg_edit_text_focused)
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
            val retypePassword = it.toString()
            if (retypePassword.isNotEmpty()) {
                handleValid(password == retypePassword, imgCheckRetypePassword, edtRetypePassword)
            }
        }

        edtRetypePassword.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.bg_edit_text_focused)
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
            editText.setBackgroundResource(R.drawable.bg_edit_text_valid)
        } else {
            imageView.setImageResource(R.drawable.icon_error)
            editText.setBackgroundResource(R.drawable.bg_edit_text_invalid)
        }
    }

    private fun isValidEmailId(email: String): Boolean {
        val regexForEmail = "^[a-zA-Z0-9]{1,32}[@][a-zA-Z0-9]+[.][a-zA-Z0-9]+${'$'}".toRegex()
        return email.matches(regexForEmail)
    }

    private fun isValidPassword(password: String): Boolean {
        val regexForPassword = "^[A-Z](.){5,32}${'$'}".toRegex()
        return password.matches(regexForPassword)
    }
}
