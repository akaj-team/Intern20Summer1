package com.asiantech.intern20summer1.week12

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-hoangtran`.fragment_login.*

@Suppress("DEPRECATION")
class LoginFragment : Fragment() {
    companion object {
        internal const val KEY_FULL_NAME = "fullName"
        internal const val USER_SHARE_PREFERENCE = "userSharePreference"
        internal const val TOKEN_SHARE_REFERENCE = "token"
    }

    var emailCheck = false
    var passCheck = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSignUp()
        handleEditText(edt_login_email)
        handleEditText(edt_login_password)
        handleLogin()
        hideKeyBoard()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyBoard() {
        ln_main_login?.setOnTouchListener { it, _ ->
            it.clearFocus()
            it.hideSoftKeyboard()
            true
        }
    }

    private fun handleSignUp() {
        tv_signUp.setOnClickListener {
            (activity as LoginActivity).showSignUpFragment()
        }
    }

    private fun handleLogin() {
        btn_login?.setOnClickListener {
            val email = edt_login_email?.text.toString()
            val password = edt_login_password?.text.toString()
            LoginViewModel().login(email, password)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        it.body().let { user ->
                            val sharePreference = activity?.getSharedPreferences(
                                USER_SHARE_PREFERENCE, Context.MODE_PRIVATE
                            )
                            val editor: SharedPreferences.Editor? = sharePreference?.edit()
                            editor?.putString(TOKEN_SHARE_REFERENCE, user?.token)
                            editor?.putString(KEY_FULL_NAME, user?.fullName)
                            editor?.apply()
                            val intent = Intent(activity, HomeActivity::class.java)
                            activity?.startActivity(intent)
                            activity?.finish()
                        }
                    }
                }, {

                })
        }
    }

    private fun isValidPassword(str: String): Boolean {
        val regex = "^(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d]{8,}$".toRegex()
        return str.matches(regex)
    }

    private fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun handleEditText(edt: EditText) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val str = s.toString()
                when (edt) {
                    edt_login_email -> {
                        emailCheck = isValidEmail(str)
                    }
                    edt_login_password -> {
                        passCheck = isValidPassword(str)
                    }
                }
                btn_login.isEnabled = emailCheck && passCheck
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}
