package com.asiantech.intern20summer1.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragment.LoginFragment
import com.asiantech.intern20summer1.fragment.RegisterFragment
import kotlinx.android.synthetic.`at-phuongle`.fragment_register.*
import java.util.regex.Pattern


class SignInActivity : AppCompatActivity() {
    private var validFullName: Boolean = false
    private var validEmail: Boolean = false
    private var validMobile: Boolean = false
    private var validPassword: Boolean = false
    private var validConfirmPassword: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportFragmentManager.beginTransaction().add(R.id.frameLayout, LoginFragment.newInstance())
            .commit()
    }

    // Replace fragment
    internal fun replaceFragment(fragment: Fragment, isAddToBackTack: Boolean) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.frameLayout, fragment)
        if (isAddToBackTack) {
            beginTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        beginTransaction.commit()
    }

    internal fun handleFullNameEditText(name: EditText, btn: Button) {
        name.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                validFullName = s.toString().isNotEmpty()
                handleButton(btn)
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

    internal fun handleEmailEditText(email: EditText, btn: Button) {
        email.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                validEmail = isEmailValid(s.toString())
                handleButton(btn)
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

    internal fun handleMobileEditText(mobile: EditText, btn: Button) {
        mobile.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                validMobile = s.length == 10
                handleButton(btn)
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

    internal fun handlePasswordEditText(password: EditText, btn: Button) {
        password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                validPassword = isPasswordValid(s.toString())
                handleButton(btn)
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
                var fragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
                if (fragment is RegisterFragment) {
                    if (edtRegisterConfirmPassword.text.toString().isNotEmpty()) {
                        validConfirmPassword = s.toString() == edtRegisterConfirmPassword.toString()
                    }

                    if (edtRegisterPassword.isFocusable && s.toString().isEmpty()) {
                        validPassword = false
                        validConfirmPassword = false
                    }
                }
            }
        })
    }

    internal fun handleConfirmPasswordEditText(password: EditText, confirm: EditText, btn: Button) {
        confirm.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                validConfirmPassword = s.toString() == password.text.toString() &&
                        validPassword == true
                handleButton(btn)
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

    // Check valid email
    private fun isEmailValid(email: String?): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Check valid password
    private fun isPasswordValid(password: String?): Boolean {
        val regex = """^(?=.*\d)[A-Za-z\d]{8,}$"""
        return Pattern.matches(regex, password)
    }

    // Handle button depend on which fragment
    private fun handleButton(btn: Button) {
        var fragment = supportFragmentManager.findFragmentById(R.id.frameLayout)

        if (fragment is LoginFragment) {
            if (validEmail && validPassword) {
                btn.isEnabled = true
                btn.setBackgroundResource(R.drawable.bg_enable_login_button)
            } else {
                btn.isEnabled = false
                btn.setBackgroundResource(R.drawable.bg_disable_login_button)
            }
        } else if (fragment is RegisterFragment) {
            if (validFullName && validEmail && validMobile && validPassword && validConfirmPassword) {
                btn.isEnabled = true
                btn.setBackgroundResource(R.drawable.bg_enable_register_button)
            } else {
                btn.isEnabled = false
                btn.setBackgroundResource(R.drawable.bg_disable_register_button)
            }
        }
    }
}
