package com.asiantech.intern20summer1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-phuongle`.activity_main.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    companion object {
        private const val PASSWORD_LENGTH = 6
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleEmailEditText()

        handlePasswordEditText()

        handleRetypePasswordEditText()

        handleSignUpButton()

        handleSignUpTextView()
    }

    // Check valid email
    private fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email.toString())
        return matcher.matches()
    }

    // Check valid password
    private fun isPasswordValid(pass: String) =
        (pass.length >= PASSWORD_LENGTH) && (pass[0].isUpperCase())

    // Check valid retype password
    private fun isPassword2Valid(pass: String, retypePass: String) = (pass == retypePass)

    // Handle event when click into button sign up
    private fun handleSignUpButton() {
        btnSignUp.setOnClickListener {
            Toast.makeText(applicationContext, "Sign Up", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle event when click into text view sign up
    private fun handleSignUpTextView() {
        tvSignUp.setOnClickListener {
            Toast.makeText(applicationContext, "Sign up!", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle event when focus on Email field
    private fun handleEmailEditText() {
        edtEmail.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            edtEmail.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    if (isEmailValid(s.toString())) {
                        edtEmail.setBackgroundResource(R.drawable.edit_text_border)
                        imgEmail.setBackgroundResource(R.drawable.icon_tick)
                        imgEmail.visibility = View.VISIBLE
                    } else {
                        edtEmail.setBackgroundResource(R.drawable.edit_text_border_wrong)
                        imgEmail.setBackgroundResource(R.drawable.icon_error)
                        imgEmail.visibility = View.VISIBLE
                    }

                    if (hasFocus && s.toString().isEmpty()) {
                        edtEmail.setBackgroundResource(R.drawable.edit_text_border_normal)
                        imgEmail.setBackgroundResource(0)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                }
            })
        }
    }

    // Handle event when focus on Password field
    private fun handlePasswordEditText() {
        edtPass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            edtPass.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    if (isPasswordValid(s.toString())) {
                        edtPass.setBackgroundResource(R.drawable.edit_text_border)
                        imgPass.setBackgroundResource(R.drawable.icon_tick)
                        imgPass.visibility = View.VISIBLE
                    } else {
                        edtPass.setBackgroundResource(R.drawable.edit_text_border_wrong)
                        imgPass.setBackgroundResource(R.drawable.icon_error)
                        imgPass.visibility = View.VISIBLE
                    }

                    if (hasFocus && s.toString().isEmpty()) {
                        edtPass.setBackgroundResource(R.drawable.edit_text_border_normal)
                        imgPass.setBackgroundResource(0)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                }
            })
        }
    }

    // Handle event when focus on Password2 field
    private fun handleRetypePasswordEditText() {
        edtRetypePass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            edtRetypePass.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    if (isPassword2Valid(edtPass.text.toString(), s.toString())) {
                        edtRetypePass.setBackgroundResource(R.drawable.edit_text_border)
                        imgRetypePass.setBackgroundResource(R.drawable.icon_tick)
                        imgRetypePass.visibility = View.VISIBLE
                    } else {
                        edtRetypePass.setBackgroundResource(R.drawable.edit_text_border_wrong)
                        imgRetypePass.setBackgroundResource(R.drawable.icon_error)
                        imgRetypePass.visibility = View.VISIBLE
                    }

                    if (hasFocus && s.toString().isEmpty()) {
                        edtRetypePass.setBackgroundResource(R.drawable.edit_text_border_normal)
                        imgRetypePass.setBackgroundResource(0)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                }
            })
        }
    }
}
