package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-linhle`.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    // Length >=6, capitalize the first character.
    private val passwordPattern =
        Pattern.compile("""^([A-Z])(?=.*[a-zA-Z]).{6,}$""")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeColorForStatusBar()
        handleOnTouchScreen()
        handleEmailTextChanged()
        handlePasswordTextChanged()
        handleRetypePassTextChanged()
        handleClickingSignUpButton()
        handleClickingSignUpTextView()
    }

    private fun changeColorForStatusBar() {
        // Clear FLAG_TRANSLUCENT_STATUS flag:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        // Add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        // Change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = this.titleColor
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleOnTouchScreen() {
        llMain?.setOnTouchListener { it, _ ->
            it.requestFocus()
            it.clearFocus()
            this.hideSoftKeyboard()
            true
        }
    }

    //Check Email
    private fun isEmailValid(email: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    //Check Password
    private fun isPasswordValid(password: String) = passwordPattern.matcher(password).matches()

    // Handle email when nothing in edit text
    private fun handleEmailEditText() {
        if (imgEmailTick.visibility == View.INVISIBLE
            && imgEmailError.visibility == View.INVISIBLE
        ) {
            imgEmailError.visibility = View.VISIBLE
            edtEmail.setBackgroundResource(R.drawable.bg_edit_text_error)
        }
    }

    // Handle password when nothing in edit text
    private fun handlePasswordEditText() {
        if (imgPasswordTick.visibility == View.INVISIBLE
            && imgPasswordError.visibility == View.INVISIBLE
        ) {
            imgPasswordError.visibility = View.VISIBLE
            edtPassword.setBackgroundResource(R.drawable.bg_edit_text_error)
        }
    }

    // Handle retype password when nothing in edit text
    private fun handleRetypePasswordEditText() {
        if (imgRetypePassTick.visibility == View.INVISIBLE
            && imgRetypePassError.visibility == View.INVISIBLE
        ) {
            imgRetypePassError.visibility = View.VISIBLE
            edtRetype.setBackgroundResource(R.drawable.bg_edit_text_error)
        }
    }

    // Handle event between password and retype password
    private fun handlePassword() {
        if (edtRetype.text.toString() == edtPassword.text.toString()
            && isPasswordValid(edtRetype.text.toString())
        ) {
            imgRetypePassTick.visibility = View.VISIBLE
            imgRetypePassError.visibility = View.INVISIBLE
            edtRetype.setBackgroundResource(R.drawable.bg_edit_text_focus_pass)
        } else {
            imgRetypePassError.visibility = View.VISIBLE
            imgRetypePassTick.visibility = View.INVISIBLE
            edtRetype.setBackgroundResource(R.drawable.bg_edit_text_focus_error)
        }
    }

    // Handle event when focus on edit text
    private fun handleStatusFocusEditText(imgError: ImageView, editText: View) {
        if (imgError.visibility == View.VISIBLE) {
            editText.setBackgroundResource(R.drawable.bg_edit_text_focus_error)
        } else {
            editText.setBackgroundResource(R.drawable.bg_edit_text_focus_pass)
        }
    }

    // Handle event when not focus on edit text
    private fun handleStatusNotFocusEditText(imgError: ImageView, editText: View) {
        if (imgError.visibility == View.VISIBLE) {
            editText.setBackgroundResource(R.drawable.bg_edit_text_error)
        } else {
            editText.setBackgroundResource(R.drawable.bg_edit_view_shape)
        }
    }

    // Handle event when change email edit text
    private fun handleEmailTextChanged() {
        // Event focus
        edtEmail.setOnFocusChangeListener { edtEmail, hasFocus ->
            if (hasFocus) {
                handleStatusFocusEditText(imgEmailError, edtEmail)
            } else {
                handleStatusNotFocusEditText(imgEmailError, edtEmail)
            }
        }
        // Event change
        edtEmail.addTextChanged(onTextChanged = { p0: CharSequence?, _, _, _ ->
            if (isEmailValid(p0.toString())) {
                imgEmailTick.visibility = View.VISIBLE
                imgEmailError.visibility = View.INVISIBLE
                edtEmail.setBackgroundResource(R.drawable.bg_edit_text_focus_pass)
            } else {
                imgEmailError.visibility = View.VISIBLE
                imgEmailTick.visibility = View.INVISIBLE
                edtEmail.setBackgroundResource(R.drawable.bg_edit_text_focus_error)
            }
        })
    }

    // Handle event when change password edit text
    private fun handlePasswordTextChanged() {
        // Event focus
        edtPassword.setOnFocusChangeListener { edtPassword, hasFocus ->
            if (hasFocus) {
                handleStatusFocusEditText(imgPasswordError, edtPassword)
            } else {
                handleStatusNotFocusEditText(imgPasswordError, edtPassword)
            }
        }
        // Event change
        edtPassword.addTextChanged(
            afterTextChanged = {
                if (it.toString() == "") {
                    //Don't do anything
                } else {
                    handlePassword()
                }
            },
            onTextChanged = { p0: CharSequence?, _, _, _ ->
                if (isPasswordValid(p0.toString())) {
                    imgPasswordTick.visibility = View.VISIBLE
                    imgPasswordError.visibility = View.INVISIBLE
                    edtPassword.setBackgroundResource(R.drawable.bg_edit_text_focus_pass)
                } else {
                    imgPasswordError.visibility = View.VISIBLE
                    imgPasswordTick.visibility = View.INVISIBLE
                    edtPassword.setBackgroundResource(R.drawable.bg_edit_text_focus_error)
                }
            })
    }

    private fun handleRetypePassTextChanged() {
        edtRetype.setOnFocusChangeListener { edtRetype, hasFocus ->
            if (hasFocus) {
                handleStatusFocusEditText(imgRetypePassError, edtRetype)
            } else {
                handleStatusNotFocusEditText(imgRetypePassError, edtRetype)
            }
        }
        edtRetype.addTextChanged(onTextChanged = { _, _, _, _ ->
            handlePassword()
        })
    }

    // Event click of button sign up
    private fun handleClickingSignUpButton() {
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
    private fun handleClickingSignUpTextView() {
        tvSignUp.setOnClickListener {
            Toast.makeText(
                this@MainActivity, "You clicked sign up.", Toast.LENGTH_SHORT
            ).show()
        }
    }
}
