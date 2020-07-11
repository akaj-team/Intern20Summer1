package com.asiantech.intern20summer1

import android.graphics.Color
import android.media.MediaCodec
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-sonnguyen`.activity_main.*
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException
import com.asiantech.intern20summer1.R.string as string1
import com.asiantech.intern20summer1.R.string.changebackground as changebackground1

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        handleListionerEdtEmail()
        handleListionerEdtPassword()
        handleListionerEdtRetypePassword()
        handleListionerBtnSignup()
        handleListionerTvSignin()

    }

    private fun handleListionerBtnSignup() {
        btnSignUp.setOnClickListener {
            Toast.makeText(this, "click Sign Up", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleListionerTvSignin() {
        txtSignIn.setOnClickListener {
            Toast.makeText(this, "Click Sign In", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleListionerEdtEmail() {
        edtEmail.setOnFocusChangeListener { _, isFocus ->
            edtEmail.isSelected = isFocus
        }
        edtEmail.setBackgroundResource(R.drawable.select_custom_edt)

        edtEmail.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (isValidEmail(edtEmail.text.toString())) {
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_tick, 0)
                    edtEmail.setBackgroundResource(R.drawable.custom_edt_true)
                } else {
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_error, 0)
                    edtEmail.setBackgroundResource(R.drawable.custom_edt_false)

                }
            }

        })

    }

    private fun handleListionerEdtPassword() {
        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isValidPassword(edtPassword.text.toString())) {
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    edtPassword.setBackgroundResource(R.drawable.custom_edt_true)
                } else {
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    edtPassword.setBackgroundResource(R.drawable.custom_edt_false)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }

    private fun handleListionerEdtRetypePassword() {
        edtRetypePassword.setOnFocusChangeListener { _, isFocus ->
            edtRetypePassword.isSelected = isFocus
        }
        edtRetypePassword.setBackgroundResource(R.drawable.select_custom_edt)
        edtRetypePassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (edtRetypePassword.text.toString() == edtPassword.text.toString() && isValidPassword(
                        edtRetypePassword.text.toString()
                    )
                ) {
                    edtRetypePassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    edtRetypePassword.setBackgroundResource(R.drawable.custom_edt_true)
                } else {
                    edtRetypePassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    edtRetypePassword.setBackgroundResource(R.drawable.custom_edt_false)
                }
                edtRetypePassword.setOnFocusChangeListener { _, isFocus ->
                    edtRetypePassword.isSelected = isFocus
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}


        })
    }
}

fun isValidEmail(string: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()
}

fun isValidPassword(string: String): Boolean {
    if (string.isEmpty()) {
        return false
    }
    return (string[0] == string[0].toUpperCase() && !string[0].isDigit() && string.length >= 6)
}

