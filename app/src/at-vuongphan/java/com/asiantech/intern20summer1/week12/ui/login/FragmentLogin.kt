@file:Suppress("NAME_SHADOWING")

package com.asiantech.intern20summer1.week12.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.extension.hideKeyboard
import com.asiantech.intern20summer1.extension.isValidEmail
import com.asiantech.intern20summer1.extension.isValidPasswordW10
import com.asiantech.intern20summer1.week12.activity.NewFeedActivity
import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.source.LocalRepository
import com.asiantech.intern20summer1.week12.data.source.UserRepository
import com.asiantech.intern20summer1.week12.data.source.remote.network.ClientAPI
import com.asiantech.intern20summer1.week12.data.source.remote.network.ErrorUtils
import com.asiantech.intern20summer1.week12.ui.register.FragmentRegister
import com.asiantech.intern20summer1.week12.ui.register.FragmentRegister.Companion.EMAIL_LENGTH
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_login.*
import retrofit2.Response

class FragmentLogin : Fragment() {
    private var isUserNameValid = false
    private var isPassWordValid = false

    companion object {
        internal fun newInstance() = FragmentLogin()
    }

    private var viewModel: LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = LoginViewModel(UserRepository(), LocalRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_login, container, false)
    }

    internal fun setBackgroundButton() {
        if (btnLogin.isEnabled) {
            btnLogin?.setBackgroundResource(R.drawable.bg_button_login)
        } else {
            btnLogin?.setBackgroundResource(R.drawable.bg_button_dis_enable)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initEmail()
        initPassword()
    }

    private fun initListener() {
        initTextViewRegister()
        initButtonLogin()
        initListenerHideKeyboardLogin()
    }

    private fun initTextViewRegister() {
        tvCreateNewAccount?.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.add(R.id.frContainer, FragmentRegister().apply {
                onRegisterSuccess = { email, password ->
                    this@FragmentLogin.edtEmail.setText(email)
                    this@FragmentLogin.edtPassword.setText(password)
                }
            })?.addToBackStack(null)?.hide(this)?.commit()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListenerHideKeyboardLogin() {
        scrViewLogin?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun initButtonLogin() {
        btnLogin?.setOnClickListener {
            viewModel?.login(edtEmail.text.toString(), edtPassword.text.toString())
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { it: Response<UserAutoSignIn> ->
                    if (it.isSuccessful) {
                        startActivity(Intent(requireContext(), NewFeedActivity::class.java))
                        activity?.finish()
                    } else {
                        val error = ErrorUtils().parseError(it)
                        if (error?.message == ClientAPI.MESSAGE_LOGIN_INCORRECT) {
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.register_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }

    private fun initEmail() {
        edtEmail.addTextChangedListener {
            isUserNameValid =
                edtEmail.text.toString()
                    .isValidEmail() && edtEmail.text.toString().length <= EMAIL_LENGTH
            btnLogin.isEnabled = isUserNameValid && isPassWordValid
            setBackgroundButton()
        }
    }

    private fun initPassword() {
        edtPassword.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                btnLogin.isEnabled = false
            } else {
                isPassWordValid = edtPassword.text.toString().isValidPasswordW10()
                btnLogin.isEnabled = isPassWordValid && isUserNameValid
                setBackgroundButton()
            }
        }
    }
}
