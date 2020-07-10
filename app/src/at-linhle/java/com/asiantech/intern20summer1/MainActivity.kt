package com.asiantech.intern20summer1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-linhle`.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    // At least 1 digit, 1 lower char, 1 upper char, must have from 8 to 30 char
    private val passwordPattern =
        Pattern.compile("""^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,30}$""")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleEmailTextChanged()
        handlePasswordTextChanged()
        handleRetypePassTextChanged()
        btnSignUpOnClick()
        tvSignUpOnClicked()
    }

    //Check Email
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //Check Password
    fun isPasswordValid(password: String): Boolean {
        return passwordPattern.matcher(password).matches()
    }

    // Handle email when nothing in edit text
    private fun handleEmailEditText() {
        if (imgEmailTick.visibility == View.INVISIBLE
            && imgEmailError.visibility == View.INVISIBLE
        ) {
            imgEmailError.visibility = View.VISIBLE
            edtEmail.setBackgroundResource(R.drawable.edit_text_error)
        }
    }

    // Handle password when nothing in edit text
    private fun handlePasswordEditText() {
        if (imgPasswordTick.visibility == View.INVISIBLE
            && imgPasswordError.visibility == View.INVISIBLE
        ) {
            imgPasswordError.visibility = View.VISIBLE
            edtPassword.setBackgroundResource(R.drawable.edit_text_error)
        }
    }

    // Handle retype password when nothing in edit text
    private fun handleRetypePasswordEditText() {
        if (imgRetypePassTick.visibility == View.INVISIBLE
            && imgRetypePassError.visibility == View.INVISIBLE
        ) {
            imgRetypePassError.visibility = View.VISIBLE
            edtRetype.setBackgroundResource(R.drawable.edit_text_error)
        }
    }

    // Handle event between password and retype password
    private fun handlePassword() {
        if (edtRetype.text.toString() == edtPassword.text.toString()
            && isPasswordValid(edtRetype.text.toString())
        ) {
            imgRetypePassTick.visibility = View.VISIBLE
            imgRetypePassError.visibility = View.INVISIBLE
            edtRetype.setBackgroundResource(R.drawable.edit_text_focus_pass)
        } else {
            imgRetypePassError.visibility = View.VISIBLE
            imgRetypePassTick.visibility = View.INVISIBLE
            edtRetype.setBackgroundResource(R.drawable.edit_text_focus_error)
        }
    }

    // Handle event when focus on edit text
    private fun handleStatusFocusEditText(imgError: ImageView, editText: View) {
        if (imgError.visibility == View.VISIBLE) {
            editText.setBackgroundResource(R.drawable.edit_text_focus_error)
        } else {
            editText.setBackgroundResource(R.drawable.edit_text_focus_pass)
        }
    }

    // Handle event when not focus on edit text
    private fun handleStatusNotFocusEditText(imgError: ImageView, editText: View) {
        if (imgError.visibility == View.VISIBLE) {
            editText.setBackgroundResource(R.drawable.edit_text_error)
        } else {
            editText.setBackgroundResource(R.drawable.edit_view_shape)
        }
    }

    // Handle event when change email edit text
    private fun handleEmailTextChanged() {
        // Event focus
        edtEmail.setOnFocusChangeListener { edtEmail, hasFocus ->
            when (hasFocus) {
                true -> handleStatusFocusEditText(imgEmailError, edtEmail)
                else -> handleStatusNotFocusEditText(imgEmailError, edtEmail)
            }
        }
        // Event change
        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isEmailValid(edtEmail.text.toString())) {
                    imgEmailTick.visibility = View.VISIBLE
                    imgEmailError.visibility = View.INVISIBLE
                    edtEmail.setBackgroundResource(R.drawable.edit_text_focus_pass)
                } else {
                    imgEmailError.visibility = View.VISIBLE
                    imgEmailTick.visibility = View.INVISIBLE
                    edtEmail.setBackgroundResource(R.drawable.edit_text_focus_error)
                }
            }
        })
    }

    // Handle event when change password edit text
    private fun handlePasswordTextChanged() {
        // Event focus
        edtPassword.setOnFocusChangeListener { edtPassword, hasFocus ->
            when (hasFocus) {
                true -> handleStatusFocusEditText(imgPasswordError, edtPassword)
                else -> handleStatusNotFocusEditText(imgPasswordError, edtPassword)
            }
        }
        // Event change
        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (edtRetype.text.toString() == "") {
                    //Don't do anything
                } else {
                    handlePassword()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isPasswordValid(edtPassword.text.toString())) {
                    imgPasswordTick.visibility = View.VISIBLE
                    imgPasswordError.visibility = View.INVISIBLE
                    edtPassword.setBackgroundResource(R.drawable.edit_text_focus_pass)
                } else {
                    imgPasswordError.visibility = View.VISIBLE
                    imgPasswordTick.visibility = View.INVISIBLE
                    edtPassword.setBackgroundResource(R.drawable.edit_text_focus_error)
                }
            }
        })
    }

    private fun handleRetypePassTextChanged() {
        edtRetype.setOnFocusChangeListener { edtRetype, hasFocus ->
            when (hasFocus) {
                true -> handleStatusFocusEditText(imgRetypePassError, edtRetype)
                else -> handleStatusNotFocusEditText(imgRetypePassError, edtRetype)
            }
        }
        edtRetype.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                handlePassword()
            }
        })
    }

    // Event click of button sign up
    private fun btnSignUpOnClick() {
        btnSignUp.setOnClickListener {
            if (imgEmailTick.visibility == View.VISIBLE
                && imgPasswordTick.visibility == View.VISIBLE
                && imgRetypePassTick.visibility == View.VISIBLE
            ) {
                Toast.makeText(
                    this@MainActivity, "Sign up success.", Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@MainActivity, "Email or password is incorrect.", Toast.LENGTH_SHORT
                ).show()
                handleEmailEditText()
                handlePasswordEditText()
                handleRetypePasswordEditText()
            }
        }
    }

    // Event click of text view sign up
    private fun tvSignUpOnClicked() {
        tvSignUp.setOnClickListener {
            Toast.makeText(
                this@MainActivity, "You are clicked sign up.", Toast.LENGTH_SHORT
            ).show()
        }
    }
}
