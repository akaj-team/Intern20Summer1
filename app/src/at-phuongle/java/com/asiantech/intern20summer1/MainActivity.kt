package com.asiantech.intern20summer1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
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
        pass.length >= PASSWORD_LENGTH && pass[0].isUpperCase()

    // Check valid retype password
    private fun isRetypePassValid(pass: String, retypePass: String) = pass == retypePass

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

    // Handle event email edit text
    private fun handleEmailEditText() {
        handleFocusEmailEditText(edtEmail, imgEmail)

        edtEmail.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (edtEmail.isFocusable) {
                    when {
                        s.toString().isEmpty() -> {
                            setActiveBackground(edtEmail, imgEmail)
                        }
                        isEmailValid(s.toString()) -> {
                            setCorrectActiveBackground(edtEmail, imgEmail)
                        }
                        else -> {
                            setIncorrectActiveBackground(edtEmail, imgEmail)
                        }
                    }
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

    // Handle event password edit text
    private fun handlePasswordEditText() {
        handleFocusPasswordEditText(edtPass, imgPass)

        edtPass.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (edtPass.isFocusable) {
                    when {
                        s.toString().isEmpty() -> {
                            setActiveBackground(edtPass, imgPass)
                        }
                        isPasswordValid(s.toString()) -> {
                            setCorrectActiveBackground(edtPass, imgPass)
                        }
                        else -> {
                            setIncorrectActiveBackground(edtPass, imgPass)
                        }
                    }
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
                if (edtRetypePass.text.toString().isNotEmpty()) {
                    if (isRetypePassValid(s.toString(), edtRetypePass.toString())) {
                        setCorrectBackground(edtRetypePass, imgRetypePass)
                    } else {
                        setIncorrectBackground(edtRetypePass, imgRetypePass)
                    }
                }

                if (edtPass.isFocusable && s.toString().isEmpty()) {
                    setNormalBackGround(edtRetypePass, imgRetypePass)
                }
            }
        })
    }

    // Handle event retype edit text
    private fun handleRetypePasswordEditText() {
        handleFocusRetypePasswordEditText(edtRetypePass, imgRetypePass)

        edtRetypePass.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (edtRetypePass.isFocusable) {
                    when {
                        s.toString().isEmpty() -> {
                            setActiveBackground(edtRetypePass, imgRetypePass)
                        }
                        isRetypePassValid(edtPass.text.toString(), s.toString()) -> {
                            setCorrectActiveBackground(edtRetypePass, imgRetypePass)
                        }
                        else -> {
                            setIncorrectActiveBackground(edtRetypePass, imgRetypePass)
                        }
                    }
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

    // Handle event when focus on email edit text
    private fun handleFocusEmailEditText(edt: EditText, img: ImageView) {
        val email = edt.text
        edt.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (email.toString().isEmpty()) {
                if (hasFocus) {
                    setActiveBackground(edt, img)
                } else {
                    setNormalBackGround(edt, img)
                }
            } else {
                if (hasFocus) {
                    if (isEmailValid(email.toString())) {
                        setCorrectActiveBackground(edt, img)
                    } else {
                        setIncorrectActiveBackground(edt, img)
                    }
                } else {
                    if (isEmailValid(email.toString())) {
                        setCorrectBackground(edt, img)
                    } else {
                        setIncorrectBackground(edt, img)
                    }
                }
            }
        }
    }

    // Handle event when focus on pass edit text
    private fun handleFocusPasswordEditText(edt: EditText, img: ImageView) {
        val password = edt.text

        edt.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (password.toString().isEmpty()) {
                if (hasFocus) {
                    setActiveBackground(edt, img)
                } else {
                    setNormalBackGround(edt, img)
                }
            } else {
                if (hasFocus) {
                    if (isPasswordValid(password.toString())) {
                        setCorrectActiveBackground(edt, img)
                    } else {
                        setIncorrectActiveBackground(edt, img)
                    }
                } else {
                    if (isPasswordValid(password.toString())) {
                        setCorrectBackground(edt, img)
                    } else {
                        setIncorrectBackground(edt, img)
                    }
                }
            }
        }
    }

    // Handle event when focus on retype password edit text
    private fun handleFocusRetypePasswordEditText(edt: EditText, img: ImageView) {
        val password = edtPass.text
        val retypePassword = edt.text

        edt.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (retypePassword.toString().isEmpty()) {
                if (hasFocus) {
                    setActiveBackground(edt, img)
                } else {
                    setNormalBackGround(edt, img)
                }
            } else {
                if (hasFocus) {
                    if (isRetypePassValid(password.toString(), retypePassword.toString())) {
                        setCorrectActiveBackground(edt, img)
                    } else {
                        setIncorrectActiveBackground(edt, img)
                    }
                } else {
                    if (isRetypePassValid(password.toString(), retypePassword.toString())) {
                        setCorrectBackground(edt, img)
                    } else {
                        setIncorrectBackground(edt, img)
                    }
                }
            }
        }
    }

    // Change background of Edit Text to normal
    private fun setNormalBackGround(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.bg_normal_edit_text)
        img.visibility = View.GONE
    }

    // Change background of Edit Text to active
    private fun setActiveBackground(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.bg_active_edit_text)
        img.visibility = View.GONE
    }

    // Change background of Edit Text to normal right
    private fun setCorrectBackground(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.bg_correct_edit_text)
        img.setBackgroundResource(R.drawable.icon_tick)
        img.visibility = View.VISIBLE
    }

    // Change background of Edit Text to active right
    private fun setCorrectActiveBackground(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.bg_correct_active_edit_text)
        img.setBackgroundResource(R.drawable.icon_tick)
        img.visibility = View.VISIBLE
    }

    // Change background of Edit Text to normal wrong
    private fun setIncorrectBackground(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.bg_incorrect_edit_text)
        img.setBackgroundResource(R.drawable.icon_error)
        img.visibility = View.VISIBLE
    }

    // Change background of Edit Text to active wrong
    private fun setIncorrectActiveBackground(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.bg_incorrect_active_edit_text)
        img.setBackgroundResource(R.drawable.icon_error)
        img.visibility = View.VISIBLE
    }
}
