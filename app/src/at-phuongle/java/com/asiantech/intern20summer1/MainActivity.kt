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

    // Handle event password edit text
    private fun handlePasswordEditText() {
        handleFocusPasswordEditText(edtPass, imgPass)

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

    // Handle event retype edit text
    private fun handleRetypePasswordEditText() {
        handleFocusRetypePasswordEditText(edtRetypePass, imgRetypePass)

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

    // Handle event when focus on email edit text
    private fun handleFocusEmailEditText(edt: EditText, img: ImageView) {
        edt.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (edt.text.toString().isEmpty()) {
                if (hasFocus) {
                    setBackGroundActive(edt, img)
                } else {
                    setBackGroundNormal(edt, img)
                }
            } else {
                if (hasFocus) {
                    if (isEmailValid(edt.text.toString())) {
                        setBackGroundActiveRight(edt, img)
                    } else {
                        setBackGroundActiveWrong(edt, img)
                    }
                } else {
                    if (isEmailValid(edt.text.toString())) {
                        setBackGroundRight(edt, img)
                    } else {
                        setBackGroundWrong(edt, img)
                    }
                }
            }
        }
    }

    // Handle event when focus on pass edit text
    private fun handleFocusPasswordEditText(edt: EditText, img: ImageView) {
        edt.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (edt.text.toString().isEmpty()) {
                if (hasFocus) {
                    setBackGroundActive(edt, img)
                } else {
                    setBackGroundNormal(edt, img)
                }
            } else {
                if (hasFocus) {
                    if (isPasswordValid(edt.text.toString())) {
                        setBackGroundActiveRight(edt, img)
                    } else {
                        setBackGroundActiveWrong(edt, img)
                    }
                } else {
                    if (isPasswordValid(edt.text.toString())) {
                        setBackGroundRight(edt, img)
                    } else {
                        setBackGroundWrong(edt, img)
                    }
                }
            }
        }
    }

    // Handle event when focus on retype password edit text
    private fun handleFocusRetypePasswordEditText(edt: EditText, img: ImageView) {
        edt.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (edt.text.toString().isEmpty()) {
                if (hasFocus) {
                    setBackGroundActive(edt, img)
                } else {
                    setBackGroundNormal(edt, img)
                }
            } else {
                if (hasFocus) {
                    if (isRetypePassValid(edtPass.text.toString(), edt.text.toString())) {
                        setBackGroundActiveRight(edt, img)
                    } else {
                        setBackGroundActiveWrong(edt, img)
                    }
                } else {
                    if (isRetypePassValid(edtPass.text.toString(), edt.text.toString())) {
                        setBackGroundRight(edt, img)
                    } else {
                        setBackGroundWrong(edt, img)
                    }
                }
            }
        }
    }

    // Change background of Edit Text to normal
    private fun setBackGroundNormal(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_normal)
        img.visibility = View.GONE
    }

    // Change background of Edit Text to active
    private fun setBackGroundActive(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_active)
        img.visibility = View.GONE
    }

    // Change background of Edit Text to normal right
    private fun setBackGroundRight(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_right)
        img.setBackgroundResource(R.drawable.icon_tick)
        img.visibility = View.VISIBLE
    }

    // Change background of Edit Text to active right
    private fun setBackGroundActiveRight(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_active_right)
        img.setBackgroundResource(R.drawable.icon_tick)
        img.visibility = View.VISIBLE
    }

    // Change background of Edit Text to normal wrong
    private fun setBackGroundWrong(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_wrong)
        img.setBackgroundResource(R.drawable.icon_error)
        img.visibility = View.VISIBLE
    }

    // Change background of Edit Text to active wrong
    private fun setBackGroundActiveWrong(edt: EditText, img: ImageView) {
        edt.setBackgroundResource(R.drawable.edit_text_border_active_wrong)
        img.setBackgroundResource(R.drawable.icon_error)
        img.visibility = View.VISIBLE
    }
}
