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
        (pass.length >= PASSWORD_LENGTH) && (pass[0].isUpperCase())

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

    // Handle event when focus on Email field
    private fun handleEmailEditText() {
        edtEmail.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (edtEmail.text.toString().isEmpty()) {
                if (hasFocus) {
                    setBackGroundActive(edtEmail, imgEmail)
                } else {
                    setBackGroundNormal(edtEmail, imgEmail)
                }
            } else {
                if (hasFocus) {
                    if (isEmailValid(edtEmail.text.toString())) {
                        setBackGroundActiveRight(edtEmail, imgEmail)
                    } else {
                        setBackGroundActiveWrong(edtEmail, imgEmail)
                    }
                } else {
                    if (isEmailValid(edtEmail.text.toString())) {
                        setBackGroundRight(edtEmail, imgEmail)
                    } else {
                        setBackGroundWrong(edtEmail, imgEmail)
                    }
                }
            }
        }

        edtEmail.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (edtEmail.isFocusable) {
                    when {
                        s.toString().isEmpty() -> {
                            setBackGroundActive(edtEmail, imgEmail)
                        }
                        isEmailValid(s.toString()) -> {
                            setBackGroundActiveRight(edtEmail, imgEmail)
                        }
                        else -> {
                            setBackGroundActiveWrong(edtEmail, imgEmail)
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

    // Handle event when focus on Password field
    private fun handlePasswordEditText() {
        edtPass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (edtPass.text.toString().isEmpty()) {
                if (hasFocus) {
                    setBackGroundActive(edtPass, imgPass)
                } else {
                    setBackGroundNormal(edtPass, imgPass)
                }
            } else {
                if (hasFocus) {
                    if (isPasswordValid(edtPass.text.toString())) {
                        setBackGroundActiveRight(edtPass, imgPass)
                    } else {
                        setBackGroundActiveWrong(edtPass, imgPass)
                    }
                } else {
                    if (isPasswordValid(edtPass.text.toString())) {
                        setBackGroundRight(edtPass, imgPass)
                    } else {
                        setBackGroundWrong(edtPass, imgPass)
                    }
                }
            }
        }

        edtPass.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (edtPass.isFocusable) {
                    when {
                        s.toString().isEmpty() -> {
                            setBackGroundActive(edtPass, imgPass)
                        }
                        isPasswordValid(s.toString()) -> {
                            setBackGroundActiveRight(edtPass, imgPass)
                        }
                        else -> {
                            setBackGroundActiveWrong(edtPass, imgPass)
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
                        setBackGroundRight(edtRetypePass, imgRetypePass)
                    } else {
                        setBackGroundWrong(edtRetypePass, imgRetypePass)
                    }
                }

                if (edtPass.isFocusable && s.toString().isEmpty()) {
                    setBackGroundNormal(edtRetypePass, imgRetypePass)
                }
            }
        })
    }

    // Handle event when focus on Password2 field
    private fun handleRetypePasswordEditText() {
        edtRetypePass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (edtRetypePass.text.toString().isEmpty()) {
                if (hasFocus) {
                    setBackGroundActive(edtRetypePass, imgRetypePass)
                } else {
                    setBackGroundNormal(edtRetypePass, imgRetypePass)
                }
            } else {
                if (hasFocus) {
                    if (isRetypePassValid(edtPass.text.toString(), edtRetypePass.text.toString())) {
                        setBackGroundActiveRight(edtRetypePass, imgRetypePass)
                    } else {
                        setBackGroundActiveWrong(edtRetypePass, imgRetypePass)
                    }
                } else {
                    if (isRetypePassValid(edtPass.text.toString(), edtRetypePass.text.toString())) {
                        setBackGroundRight(edtRetypePass, imgRetypePass)
                    } else {
                        setBackGroundWrong(edtRetypePass, imgRetypePass)
                    }
                }
            }
        }

        edtRetypePass.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (edtRetypePass.isFocusable) {
                    when {
                        s.toString().isEmpty() -> {
                            setBackGroundActive(edtRetypePass, imgRetypePass)
                        }
                        isRetypePassValid(edtPass.text.toString(), s.toString()) -> {
                            setBackGroundActiveRight(edtRetypePass, imgRetypePass)
                        }
                        else -> {
                            setBackGroundActiveWrong(edtRetypePass, imgRetypePass)
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

    private fun setBackGroundNormal(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_normal)
        img.visibility = View.GONE
    }

    private fun setBackGroundActive(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_active)
        img.visibility = View.GONE
    }

    private fun setBackGroundRight(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_right)
        img.setBackgroundResource(R.drawable.icon_tick)
        img.visibility = View.VISIBLE
    }

    private fun setBackGroundActiveRight(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_active_right)
        img.setBackgroundResource(R.drawable.icon_tick)
        img.visibility = View.VISIBLE
    }

    private fun setBackGroundWrong(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_wrong)
        img.setBackgroundResource(R.drawable.icon_error)
        img.visibility = View.VISIBLE
    }

    private fun setBackGroundActiveWrong(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_active_wrong)
        img.setBackgroundResource(R.drawable.icon_error)
        img.visibility = View.VISIBLE
    }
}
