package com.asiantech.intern20summer1.week10.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.model.User
import com.asiantech.intern20summer1.week4.other.isValidEmail
import com.asiantech.intern20summer1.week4.other.isValidPassword
import kotlinx.android.synthetic.`at-longphan`.fragment_sign_up.*

class SignUpFragment : Fragment() {

    companion object {

    }

    private var userRegister = User()
    private var isFullNameValid = false
    private var isEmailValid = false
    private var isPasswordValid = false
    private var isConfirmPasswordValid = false
    private var isSignUpEnabled = false

    internal var onRegisterSuccess: (user: User) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListener()
    }

    private fun handleListener() {
        handleButtonBackListener()
        handleEditTextFullNameListener()
        handleEditTextEmailListener()
        handleEditTextPasswordListener()
        handleEditTextConfirmPasswordListener()
        handleButtonSignUpListener()
    }

    private fun handleButtonBackListener() {
        imgBtnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun handleEditTextFullNameListener() {
        edtFullName.addTextChangedListener {
            val fullName = it.toString()
            userRegister.fullName = fullName
            isFullNameValid = fullName.isNotBlank()
            checkSignUpButton()
        }
    }

    private fun handleEditTextEmailListener() {
        edtEmail.addTextChangedListener {
            val email = it.toString()
            userRegister.email = email
            isEmailValid = isValidEmail(email)
            checkSignUpButton()
        }
    }

    private fun handleEditTextPasswordListener() {
        edtPassword.addTextChangedListener {
            val password = it.toString()
            userRegister.password = password
            isPasswordValid = isValidPassword(password)
            checkSignUpButton()
        }
    }

    private fun handleEditTextConfirmPasswordListener() {
        edtConfirmPassword.addTextChangedListener {
            val confirmPassword = it.toString()
            isConfirmPasswordValid = edtPassword.text.toString() == confirmPassword
            checkSignUpButton()
        }
    }

    private fun handleButtonSignUpListener() {
        btnSignUp.setOnClickListener {
            onRegisterSuccess(userRegister)
            Toast.makeText(context, getString(R.string.text_sign_up_success), Toast.LENGTH_SHORT)
                .show()
            fragmentManager?.popBackStack()
        }
    }

    private fun checkSignUpButton() {
        isSignUpEnabled =
            isFullNameValid && isEmailValid  && isPasswordValid && isConfirmPasswordValid
        toggleSignUpButton()
        btnSignUp.isEnabled = isSignUpEnabled
    }

    private fun toggleSignUpButton() {
        if (isSignUpEnabled) {
            btnSignUp.setBackgroundResource(R.drawable.bg_button_enable)
        } else {
            btnSignUp.setBackgroundResource(R.drawable.bg_button_unable)
        }
    }
}
