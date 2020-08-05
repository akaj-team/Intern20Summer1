package com.asiantech.intern20summer1.w4.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.activity.HomeActivity
import com.asiantech.intern20summer1.w4.activity.MainActivity
import com.asiantech.intern20summer1.w4.model.Account
import kotlinx.android.synthetic.`at-huybui`.fragment_sign_in.*

class SignInFragment : Fragment() {

    companion object {
        internal const val REGEX_PASSWORD = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,16}$"""
        internal const val KEY_USE = "USE"
    }

    private var toastStatus: Toast? = null
    private var account = Account()
    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleForLoginButton()
        handleForRegisterTextView()
    }

    /**
     * This function receiver data from sign up fragment
     */
    private fun handleReceiverData(account: Account) {
        edtSignInEmail.setText(account.email)
        edtSignInPassword.setText(account.passWord)
        email = account.email
        password = account.passWord
        this.account = account
    }

    /**
     * Handle for register edit text view
     * Click it will open sign up fragment
     */
    private fun handleForRegisterTextView() {
        tvRegisterNow.setOnClickListener {
            val fragment = SignUpFragment.newInstance()
            fragment.onRegisterClick = this::handleReceiverData
            (activity as? MainActivity)?.addFragment(fragment, true)
        }
    }

    /**
     * This function handle for login button
     * It used to check email and password
     * If true it move to Home Activity
     */
    private fun handleForLoginButton() {
        btnLoginSignIn.setOnClickListener {
            if (edtSignInEmail.text.isEmpty() || edtSignInPassword.text.isEmpty()) {
                showToast(getString(R.string.enter_email_and_password))
            } else if (email == edtSignInEmail.text.toString() && password == edtSignInPassword.text.toString()) {
                val intent = Intent(context, HomeActivity::class.java)
                intent.putExtra(KEY_USE, account)
                startActivity(intent)
                this.activity?.finish()
            } else {
                showToast(getString(R.string.invalid_account_or_password))
            }
        }
    }

    /**
     * This function will show a toast on display
     */
    private fun showToast(text: Any, duration: Int = Toast.LENGTH_SHORT) {
        toastStatus?.cancel()
        toastStatus = Toast.makeText(context, text.toString(), duration).apply { show() }
    }
}
