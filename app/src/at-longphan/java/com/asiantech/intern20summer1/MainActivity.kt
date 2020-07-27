package com.asiantech.intern20summer1

import android.os.Bundle
import android.view.View
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
        // Set black text with light status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        handleEditTextEmailIdEvent()
        handleEditTextPasswordEvent()
        handleEditTextRetypePasswordEvent()
        handleListener()
    }

    private fun handleListener() {
        llChild?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }

        btnSignUp.setOnClickListener {
            //Glide.with( this).load("https://scontent.f-2.fna.fbcdn.net/v/t1.0-9/p960x960/114709498_601899243857223_5635022684478067077_o.jpg?_nc_cat=1&_nc_sid=8024bb&_nc_ohc=OzdH9cDoMfMAX82EOqs&_nc_ht=scontent.fdad3-2.fna&_nc_tp=6&oh=b4f76e51167493843fb3380fb5607af9&oe=5F44E9CC").into(imgLogo)
            Toast.makeText(applicationContext, "Sign Up", Toast.LENGTH_SHORT).show()
        }

        tvSignUp.setOnClickListener {
            Toast.makeText(applicationContext, "Sign Up!", Toast.LENGTH_SHORT).show()
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
