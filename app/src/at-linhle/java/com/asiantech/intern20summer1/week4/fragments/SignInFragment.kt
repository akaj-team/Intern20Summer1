package com.asiantech.intern20summer1.week4.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.extensions.hideSoftKeyboard
import com.asiantech.intern20summer1.week4.models.User
import com.asiantech.intern20summer1.week4.views.HomeActivity
import kotlinx.android.synthetic.`at-linhle`.fragment_sign_in.*
import java.util.regex.Pattern

class SignInFragment : Fragment() {

    companion object {
        internal const val KEY_STRING_USER = "user"
    }

    // At least 1 digit and have from 8 to 16 characters
    private val passwordPattern = Pattern.compile("""^(?=.*[0-9]).{8,16}$""")
    private var user = User()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEmailTextChanged()
        handlePasswordTextChanged()
        handleOnTouchScreen()
        handleClickingLoginButton()
        handleClickingRegisterTextView()
    }

    private fun isEmailValid(email: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid(password: String) = passwordPattern.matcher(password).matches()

    // Check all edit text correct validate
    private fun isCorrectFormat(email: String, password: String) =
        isEmailValid(email) && isPasswordValid(password)

    @SuppressLint("ClickableViewAccessibility")
    private fun handleOnTouchScreen() {
        llSignInMain?.setOnTouchListener { it, _ ->
            it.clearFocus()
            it.hideSoftKeyboard()
            true
        }
    }

    // Pass data when click button login and change activity
    private fun handleClickingLoginButton() {
        btnLogin.setOnClickListener {
            if (edtEmail.text.toString() == user.email
                && edtPassword.text.toString() == user.password
            ) {
                val intent = Intent(activity, HomeActivity::class.java)
                intent.putExtra(KEY_STRING_USER, user)
                activity?.startActivity(intent)
                activity?.finish()
            } else {
                displayAlert()
            }
        }
    }

    private fun handleEmailTextChanged() {
        edtEmail.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnLogin.isEnabled = isCorrectFormat(p0.toString(), edtPassword.text.toString())
        })
    }

    private fun handlePasswordTextChanged() {
        edtPassword.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnLogin.isEnabled = isCorrectFormat(edtEmail.text.toString(), p0.toString())
        })
    }

    // Display dialog
    private fun displayAlert() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.sign_in_fragment_title_dialog)
                .setMessage(R.string.sign_in_fragment_message_dialog)
                .setPositiveButton(
                    R.string.sign_in_fragment_ok_button_description
                ) { _, _ -> }.show()
        }
    }

    // Change fragment and receive data when call back
    private fun handleClickingRegisterTextView() {
        tvRegister.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.add(R.id.flContainer, SignUpFragment().apply {
                onRegisterSuccess = { user ->
                    this@SignInFragment.user = user
                    this@SignInFragment.edtEmail.setText(user.email)
                    this@SignInFragment.edtPassword.setText(user.password)
                }
            })?.addToBackStack(null)?.hide(this)?.commit()
        }
    }
}
