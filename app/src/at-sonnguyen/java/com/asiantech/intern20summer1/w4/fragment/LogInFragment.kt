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
import com.asiantech.intern20summer1.w4.activity.HomeActivity.Companion.KEY_VALUE
import com.asiantech.intern20summer1.w4.extension.isValidEmail
import com.asiantech.intern20summer1.w4.extension.isValidPassword
import kotlinx.android.synthetic.`at-sonnguyen`.fragment_log_in.*

@Suppress("DEPRECATION")
class LogInFragment : Fragment() {
    private var userLogin = User("", "", "", "", "")
    private var emailText: String = ""
    private var passwordText: String = ""

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
        handleListenerRegisterTextView()
        handleListenerSignInButton()
        getDataFromSignUpFragment()
    }

    private fun handleListenerRegisterTextView() {
        tvRegister.setOnClickListener {
            replaceRegisterFragment()
        }
    }

    private fun replaceRegisterFragment() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.llFragment, RegisterFragment.newInstance())
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    private fun handleEmailEditText() {
        edtSignInEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
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

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
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
            override fun afterTextChanged(p0: Editable?) {
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

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
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
        btnSignIn.isEnabled = isValidEmail(emailText) && isValidPassword(passwordText)
    }

    private fun showIncorrectAccountDialog() {
        AlertDialog.Builder(context)
            .setTitle(resources.getString(R.string.log_in_fragment_incorrect_account_dialog_tittle))
            .setMessage(resources.getString(R.string.log_in_fragment_incorrect_account_dialog_message))
            .setPositiveButton(
                android.R.string.yes
            ) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}
