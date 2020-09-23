package com.asiantech.intern20summer1.week12.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.data.source.LoginRepository
import com.asiantech.intern20summer1.week12.extensions.handleOnTouchScreen
import com.asiantech.intern20summer1.week12.data.source.datasource.LoginDataSource
import com.asiantech.intern20summer1.week12.ui.register.RegisterFragment
import com.asiantech.intern20summer1.week12.ui.post.HomeRxActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-linhle`.fragment_login.*
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    companion object {
        internal const val KEY_STRING_FULL_NAME = "fullName"
        internal const val MAX_EMAIL_LENGTH = 264
        private const val RESPONSE_CODE = 401
        internal const val SHARED_PREFERENCE_TOKEN = "token"
        internal fun newInstance() =
            LoginFragment()
    }

    private val passwordPattern = Pattern.compile("""^(?=.*).{8,16}$""")
    private var viewModel: LoginDataSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = LoginViewModel(
            LoginRepository()
        )
    }
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
        handleOnTouchScreen(llLoginMain)
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
            transaction?.add(R.id.flLoginContainer, RegisterFragment.newInstance()
                .apply {
                onRegisterSuccess = { email, password ->
                    this@LoginFragment.edtEmail.setText(email)
                    this@LoginFragment.edtPassword.setText(password)
                }
            })?.addToBackStack(null)?.hide(this)?.commit()
        }
    }

    private fun handleClickingLoginButton() {
        btnLogin?.setOnClickListener {
            viewModel?.login(edtEmail.text.toString(), edtPassword.text.toString())
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        it.body()?.let { user ->
                            val intent = Intent(activity, HomeRxActivity::class.java)
                            intent.putExtra(KEY_STRING_FULL_NAME, user.fullName)
                            intent.putExtra(SHARED_PREFERENCE_TOKEN, user.token)
                            activity?.startActivity(intent)
                            activity?.finish()
                        }
                    } else {
                        if (it.code() == RESPONSE_CODE) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.login_fragment_login_incorrect),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.login_fragment_login_fail),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }, {
                    //No-op
                })
        }
    }
}
