package com.asiantech.intern20summer1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-sonnguyen`.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val MIN_LENGTH_PASSWORD = 6
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleListenerEmailEditText()
        handleListenerPasswordEditText()
        handleListenerRetypePasswordEditText()
        handleListenerButtonSignUp()
        handleListenerSignUpTextView()
    }

    private fun handleListenerSignUpTextView() {
        tvSignIn.setOnClickListener {
            Toast.makeText(this, "Sign Up !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleListenerEmailEditText() {
        edtEmail.setOnFocusChangeListener { _, isFocus ->
            edtEmail.isSelected = isFocus
        }
        edtEmail.setBackgroundResource(R.drawable.select_custom_edit_text)

        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (isValidEmail(p0.toString())) {
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    edtEmail.setBackgroundResource(R.drawable.bg_edit_text_input_pass)
                } else {
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    edtEmail.setBackgroundResource(R.drawable.bg_edit_text_input_error)
                }
            }
        })
    }

    private fun handleListenerPasswordEditText() {
        edtPassword.setOnFocusChangeListener { _, isFocus ->
            edtPassword.isSelected = isFocus
        }
        edtPassword.setBackgroundResource(R.drawable.select_custom_edit_text)
        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isValidPassword(p0.toString())) {
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    edtPassword.setBackgroundResource(R.drawable.bg_edit_text_input_pass)
                } else {
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    edtPassword.setBackgroundResource(R.drawable.bg_edit_text_input_error)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun handleListenerRetypePasswordEditText() {
        edtRetypePassword.setOnFocusChangeListener { _, isFocus ->
            edtRetypePassword.isSelected = isFocus
        }
        edtRetypePassword.setBackgroundResource(R.drawable.select_custom_edit_text)
        edtRetypePassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (edtRetypePassword.text.toString() == edtPassword.text.toString() &&
                    isValidPassword(edtRetypePassword.text.toString())
                ) {
                    edtRetypePassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    edtRetypePassword.setBackgroundResource(R.drawable.bg_edit_text_input_pass)
                } else {
                    edtRetypePassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    edtRetypePassword.setBackgroundResource(R.drawable.bg_edit_text_input_error)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun handleListenerButtonSignUp() {
        btnSignIn.setOnClickListener {
            Toast.makeText(this, "Sign Up ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidPassword(string: String) = string.isNotEmpty() &&
            (string[0] == string[0].toUpperCase() && !string[0].isDigit()
                    && string.length >= MIN_LENGTH_PASSWORD)

    private fun isValidEmail(string: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()
}
