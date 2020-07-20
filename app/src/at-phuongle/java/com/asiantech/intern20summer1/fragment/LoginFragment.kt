package com.asiantech.intern20summer1.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-phuongle`.fragment_login.*
import java.util.regex.Pattern

class LoginFragment : Fragment() {
    companion object {
        var validEmail: Boolean = false
        var validPassword: Boolean = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onResume() {
        handleEmailEditText(edtEmail)
        handlePasswordEditText(edtPass)


        super.onResume()
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

    private fun handleLoginButton(validEmail: Boolean, validPassword: Boolean) {
        if (validEmail && validPassword) {
            btnLogin.isEnabled = true
            btnLogin.setBackgroundResource(R.drawable.bg_enable_login_button)
        } else {
            btnLogin.isEnabled = false
            btnLogin.setBackgroundResource(R.drawable.bg_disable_login_button)
        }
    }

    private fun handleEmailEditText(email: EditText) {
        email.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                validEmail = isEmailValid(s.toString())
                handleLoginButton(validEmail, validPassword)
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

    private fun handlePasswordEditText(password: EditText) {
        password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                validPassword = isPasswordValid(s.toString())
                handleLoginButton(validEmail, validPassword)
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

