package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-hoangtran`.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mToast: Toast? = null

    fun validPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        return (password.matches(passwordPattern.toRegex()))
    }

    private fun Any.toast(leng: Int = Toast.LENGTH_SHORT) {
        mToast?.cancel()
        mToast = Toast.makeText(this@MainActivity, this.toString(), leng).apply { show() }
    }

    private fun View.hideKeyboard() {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                rlEmail?.setBackgroundResource(R.drawable.ontextchange)
            } else {
                rlEmail?.setBackgroundResource(R.drawable.edittextshape)
            }
        }
        edtPass.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                rlPass?.setBackgroundResource(R.drawable.ontextchange)
            } else {
                rlPass?.setBackgroundResource(R.drawable.edittextshape)
            }
        }
        edtRetype.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                rlRetype?.setBackgroundResource(R.drawable.ontextchange)
            } else {
                rlRetype?.setBackgroundResource(R.drawable.edittextshape)
            }
        }

        lnMain?.setOnTouchListener { it, _ ->
            it.requestFocus()
            it.clearFocus()
            it.hideKeyboard()
            true
        }

        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edtEmail.text.toString().isEmpty()) {
                    imgEmailError.visibility = View.INVISIBLE
                    imgEmailTick.visibility = View.INVISIBLE
                } else {
                    if (Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches()) {
                        imgEmailTick.visibility = View.VISIBLE
                        imgEmailError.visibility = View.INVISIBLE
                    } else {
                        imgEmailTick.visibility = View.INVISIBLE
                        imgEmailError.visibility = View.VISIBLE
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        edtPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edtPass.text.toString().isEmpty()) {
                    imgPassError.visibility = View.INVISIBLE
                    imgPassTick.visibility = View.INVISIBLE
                } else {
                    if (validPassword(edtPass.text.toString())) {
                        imgPassTick.visibility = View.VISIBLE
                        imgPassError.visibility = View.INVISIBLE
                    } else {
                        imgPassTick.visibility = View.INVISIBLE
                        imgPassError.visibility = View.VISIBLE
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        edtRetype.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edtRetype.text.toString().isEmpty()) {
                    imgRetypeError.visibility = View.INVISIBLE
                    imgRetypeTick.visibility = View.INVISIBLE
                } else {
                    if (edtRetype.text.toString() == edtPass.text.toString() && validPassword(
                            edtRetype.text.toString()
                        )
                    ) {
                        imgRetypeTick.visibility = View.VISIBLE
                        imgRetypeError.visibility = View.INVISIBLE
                    } else {
                        imgRetypeTick.visibility = View.INVISIBLE
                        imgRetypeError.visibility = View.VISIBLE
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        tvSignUp?.setOnClickListener {
            "Sign up".toast()
        }
        imgFB?.setOnClickListener {
            "Facebook".toast()
        }
        imgTwitter?.setOnClickListener {
            "Twitter".toast()
        }
        imgGGPlus?.setOnClickListener {
            "Google+".toast()
        }

        btnSignUp?.setOnClickListener {
            if (edtEmail.text.toString().isEmpty() || edtPass.text.toString().isEmpty()
                && edtRetype.text.toString().isEmpty()
            ) {
                "Email or password is empty".toast()
            } else if (imgEmailError.visibility == View.VISIBLE) {
                "Wrong email".toast()
            } else if (imgPassError.visibility == View.VISIBLE) {
                "Wrong password".toast()
            } else if (imgRetypeError.visibility == View.VISIBLE) {
                "Password not match".toast()
            }
        }
    }

}

