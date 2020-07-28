package com.asiantech.intern20summer1.week4.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.activity.HomeActivity
import com.asiantech.intern20summer1.week4.activity.SignInActivity
import com.asiantech.intern20summer1.week4.model.User
import kotlinx.android.synthetic.`at-phuongle`.fragment_login.*
import java.util.regex.Pattern


class LoginFragment : Fragment() {
    private var user: User? = null
    private var validEmailLogin: Boolean = false
    private var validPasswordLogin: Boolean = false

    companion object {
        const val REGISTER_DATA_KEY = "register_data"
        const val LOGIN_DATA_KEY = "login_data"

        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        handleLoginEmailEditText()
        handlePasswordEditText()
        handleLoginRegisterTextView()
        user = getDataFromRegisterFragment()
        bindDataToEditText(user)
        handleLoginButton()
    }

    private fun getDataFromRegisterFragment(): User? {
        val bundle = this.arguments
        if (bundle != null) {
            return bundle.getParcelable(REGISTER_DATA_KEY)
        }
        return null
    }

    private fun bindDataToEditText(user: User?) {
        edtEmail.setText(user?.email)
        edtPass.setText(user?.password)
    }

    private fun isValidAccount(): Boolean {
        return edtEmail.text.toString() == user?.email && edtPass.text.toString() == user?.password
    }

    private fun handleLoginButton() {
        btnLogin.setOnClickListener {
            if (isValidAccount()) {
                val intent = Intent(activity as? SignInActivity, HomeActivity::class.java)
                intent.putExtra(LOGIN_DATA_KEY, user)
                startActivity(intent)
                (activity as? SignInActivity)?.finish()
            } else {
                val dialog = AlertDialog.Builder(context)
                dialog.setCancelable(false)
                dialog.setTitle(getString(R.string.login_button_alert_title))
                dialog.setMessage(getString(R.string.login_button_alert_message))
                dialog.setPositiveButton(R.string.login_button_alert_positive_button,
                    DialogInterface.OnClickListener { _, _ ->
                        //Action for "Cancel".
                    })

                val alert: AlertDialog = dialog.create()
                alert.show()
            }
        }
    }

    private fun handleLoginRegisterTextView() {
        tvLoginRegister.setOnClickListener {
            (activity as? SignInActivity)?.replaceRegisterFragment(true)
        }
    }

    private fun handleLoginEmailEditText() {
        edtEmail.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                validEmailLogin = isEmailValid(s.toString())
                handleEnableLoginButton()
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

    private fun handlePasswordEditText() {
        edtPass.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                validPasswordLogin = isPasswordValid(s.toString())
                handleEnableLoginButton()
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
    private fun handleEnableLoginButton() {
        if (validEmailLogin && validPasswordLogin) {
            btnLogin.isEnabled = true
            btnLogin.setBackgroundResource(R.drawable.bg_enable_login_button)
        } else {
            btnLogin.isEnabled = false
            btnLogin.setBackgroundResource(R.drawable.bg_disable_login_button)
        }
    }
}
