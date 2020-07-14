package com.asiantech.intern20summer1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-vuongphan`.activity_main.*
import utils.afterTextChanged
import utils.isValidEmail
import utils.isValidPassword

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initListenerEmailEditText()
        initListenerTypePasswordEditText()
        initListenerRetypePasswordEditText()
        initToast()
    }

    /*
     * Event handler edit text Email
     */
    private fun initListenerEmailEditText() {
        edtEmail.afterTextChanged {
            if (it.isValidEmail()) {
                edtEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_tick, 0)
                edtEmail.isActivated = false
            } else {
                edtEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_error, 0)
                edtEmail.isActivated = true
            }
        }
        edtEmail.setOnFocusChangeListener { _, isFocus ->
            edtEmail.isSelected = isFocus
        }
    }

    /*
     * Event handler edit text type password
     */
    private fun initListenerTypePasswordEditText() {
        edtTypePassword.afterTextChanged {
            if (it.isValidPassword()) {
                edtTypePassword.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.icon_tick,
                    0
                )
                edtTypePassword.isActivated = false
            } else {
                edtTypePassword.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.icon_error,
                    0
                )
                edtTypePassword.isActivated = true
            }
        }
        edtTypePassword.setOnFocusChangeListener { _, isFocus ->
            edtTypePassword.isSelected = isFocus
        }
    }

    /*
     * Event handler edit text retype password
     */
    private fun initListenerRetypePasswordEditText() {
        edtRetypePassword.afterTextChanged {
            if (it.isValidPassword() && (edtRetypePassword.text.toString() == edtTypePassword.text.toString())) {
                edtRetypePassword.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.icon_tick,
                    0
                )
                edtRetypePassword.isActivated = false
            } else {
                edtRetypePassword.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.icon_error,
                    0
                )
                edtRetypePassword.isActivated = true
            }
        }
        edtRetypePassword.setOnFocusChangeListener { _, isFocus ->
            edtRetypePassword.isSelected = isFocus
        }
    }

    /*
     * Initialize initial values for the interface
     */
    private fun initViews() {
        edtEmail.isActivated = false
        edtEmail.isSelected = false
    }

    /*
     * Handle events for SignIn TextView and Login Button
     */
    private fun initToast() {
        tvSign.setOnClickListener {
            Toast.makeText(this, "Sign up! ", Toast.LENGTH_SHORT).show()
        }
        btnLogin.setOnClickListener {
            Toast.makeText(this, "Sign Up", Toast.LENGTH_SHORT).show()
        }
    }
}
