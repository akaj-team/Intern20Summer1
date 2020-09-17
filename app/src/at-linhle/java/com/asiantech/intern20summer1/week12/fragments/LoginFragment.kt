package com.asiantech.intern20summer1.week12.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.viewmodels.LoginViewModel
import com.asiantech.intern20summer1.week12.views.HomeRxActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-linhle`.fragment_login.*
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    companion object {
        internal const val KEY_STRING_FULL_NAME = "fullName"
        internal const val MAX_EMAIL_LENGTH = 264
        internal const val SHARED_PREFERENCE_FILE = "userSharedPreference"
        internal const val SHARED_PREFERENCE_TOKEN = "token"
        internal fun newInstance() = LoginFragment()
    }

    private val passwordPattern = Pattern.compile("""^(?=.*).{8,16}$""")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClickingRegisterTextView()
        handleLoginEmailTextChanged()
        handleLoginPasswordTextChanged()
        handleClickingLoginButton()
    }

    private fun isSignUpPasswordValid(password: String) =
        passwordPattern.matcher(password).matches()

    private fun isSignUpEmailValid(email: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email)
            .matches() && email.length <= MAX_EMAIL_LENGTH

    private fun isCorrectFormat(
        email: String,
        password: String
    ) = isSignUpEmailValid(email) && isSignUpPasswordValid(password)

    private fun setBackGroundLoginButton() {
        if (btnLogin.isEnabled) {
            btnLogin?.setBackgroundResource(R.drawable.bg_button_enabled)
        } else {
            btnLogin?.setBackgroundResource(R.drawable.bg_button_disabled)
        }
    }

    private fun handleLoginEmailTextChanged() {
        edtEmail?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnLogin?.isEnabled = isCorrectFormat(
                p0.toString(),
                edtPassword.text.toString()
            )
            setBackGroundLoginButton()
        })
    }

    private fun handleLoginPasswordTextChanged() {
        edtPassword?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnLogin?.isEnabled = isCorrectFormat(
                edtEmail.text.toString(),
                p0.toString()
            )
            setBackGroundLoginButton()
        })
    }

    private fun handleClickingRegisterTextView() {
        tvRegister.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.add(R.id.flLoginContainer, RegisterFragment.newInstance().apply {
                onRegisterSuccess = { email, password ->
                    this@LoginFragment.edtEmail.setText(email)
                    this@LoginFragment.edtPassword.setText(password)
                }
            })?.addToBackStack(null)?.hide(this)?.commit()
        }
    }

    private fun handleClickingLoginButton() {
        btnLogin?.setOnClickListener {
            LoginViewModel().login(edtEmail.text.toString(), edtPassword.text.toString())
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        it.body()?.let { user ->
                            val sharedPreferences = activity?.getSharedPreferences(
                                SHARED_PREFERENCE_FILE,
                                Context.MODE_PRIVATE
                            )
                            val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                            editor?.putString(SHARED_PREFERENCE_TOKEN, user.token)
                            editor?.apply()
                            val intent = Intent(activity, HomeRxActivity::class.java)
                            intent.putExtra(KEY_STRING_FULL_NAME, user.fullName)
                            intent.putExtra(SHARED_PREFERENCE_TOKEN, user.token)
                            activity?.startActivity(intent)
                            activity?.finish()
                        }
                    }
                }, {
                    //No-op
                })
        }
    }
}
