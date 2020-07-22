package com.asiantech.intern20summer1.w4.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.account.User
import com.asiantech.intern20summer1.w4.activity.HomeActivity
import kotlinx.android.synthetic.`at-sonnguyen`.fragment_log_in.*

@Suppress("DEPRECATION")
class SignInFragment : Fragment() {
    private var userLogin = User("", "", "", "", "")
    private var emailText: String = ""
    private var passwordText: String = ""

    companion object {
        private const val INCORRECT_ACCOUNT_DIALOG_TITLE = "WARNING"
        private const val INCORRECT_ACCOUNT_DIALOG_MESSAGE =
            "your email or your password is incorrect"
        private const val KEY_VALUE = "data"
        private const val MIN_LENGTH_PASSWORD = 8
        private const val MAX_LENGTH_PASSWORD = 16
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onResume() {
        super.onResume()
        handleEmailEditText()
        handleSignInPasswordEditText()
        setEnableSignInButton()
        tvRegister.setOnClickListener {
            replaceSignUpFragment()
        }
        handleListenerSignInButton()
        getDataFromSignUpFragment()
    }

    private fun replaceSignUpFragment() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.llFragment, SignUpFragment())
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    private fun handleEmailEditText() {
        edtSignInEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isValidEmail(p0.toString())) {
                    edtSignInEmail.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    emailText = p0.toString()
                } else {
                    edtSignInEmail.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    emailText = ""
                }
            }
        })
    }

    private fun getDataFromSignUpFragment() {
        (arguments?.getSerializable(KEY_VALUE) as? User)?.let {
            userLogin.email = it.email
            userLogin.password = it.password
            userLogin.phoneNumber = it.phoneNumber
            userLogin.fullName = it.fullName
            userLogin.avatarUri = it.avatarUri
            edtSignInEmail.setText(it.email)
            edtSignInPassword.setText(it.password)
        }
    }

    private fun handleSignInPasswordEditText() {
        edtSignInPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isValidPassword(p0.toString())) {
                    edtSignInPassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    passwordText = p0.toString()
                    setEnableSignInButton()
                } else {
                    edtSignInPassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    passwordText = ""
                    setEnableSignInButton()
                }
            }
        })
    }

    private fun handleListenerSignInButton() {
        btnSignIn.setOnClickListener {
            if (emailText == userLogin.email && passwordText == userLogin.password) {
                val intent = Intent(activity, HomeActivity::class.java)
                intent.putExtra(KEY_VALUE, userLogin)
                activity?.startActivity(intent)
                activity?.finish()
            } else {
                showIncorrectAccountDialog()
            }
        }
    }

    private fun setEnableSignInButton() {
        btnSignIn.isEnabled = (isValidEmail(emailText) && isValidPassword(passwordText))
    }

    private fun showIncorrectAccountDialog() {
        AlertDialog.Builder(context)
            .setTitle(INCORRECT_ACCOUNT_DIALOG_TITLE)
            .setMessage(INCORRECT_ACCOUNT_DIALOG_MESSAGE)
            .setPositiveButton(
                android.R.string.yes
            ) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun isValidEmail(string: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()

    fun isValidPassword(string: String): Boolean {
        if (string.length !in MIN_LENGTH_PASSWORD..MAX_LENGTH_PASSWORD) {
            return false
        } else {
            for (i in string.indices) {
                if (string[i].isDigit()) return true
            }
        }
        return false
    }
}
