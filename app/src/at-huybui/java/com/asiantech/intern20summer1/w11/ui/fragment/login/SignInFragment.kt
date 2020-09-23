package com.asiantech.intern20summer1.w11.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w11.data.source.LocalRepository
import com.asiantech.intern20summer1.w11.data.source.LoginRepository
import com.asiantech.intern20summer1.w11.data.source.remote.network.ApiClient
import com.asiantech.intern20summer1.w11.data.source.remote.network.ErrorUtils
import com.asiantech.intern20summer1.w11.ui.activity.ApiMainActivity
import com.asiantech.intern20summer1.w11.ui.fragment.home.HomeFragment
import com.asiantech.intern20summer1.w11.utils.extension.showToast
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_sign_in.*

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is SignInFragment class. It is fragment to display sign in page
 */

class SignInFragment : Fragment() {

    companion object {
        internal fun newInstance() = SignInFragment()
    }

    private var viewModel: LoginVM? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        return inflater.inflate(R.layout.w10_fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initListenerButton()
        handleEdiTextListener()
    }

    private fun initListenerButton() {
        tvRegister?.setOnClickListener {
            val fragment = SignUpFragment.newInstance()
            fragment.onRegisterClick = { account ->
                edtEmail.setText(account.email)
                edtPassword.setText(account.password)
            }
            (activity as ApiMainActivity).addFragment(fragment, true)
        }

        btnSignIn?.setOnClickListener {
            isCheckAccount()
        }
    }

    private fun isCheckAccount() {
        progressBar?.visibility = View.VISIBLE
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        viewModel
            ?.login(email, password)
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        (activity as ApiMainActivity).replaceFragment(
                            HomeFragment.newInstance()
                        )
                    }
                } else {
                    val error = ErrorUtils().parseError(response)
                    if (error?.message == ApiClient.MESSAGE_LOGIN_INCORRECT) {
                        getString(R.string.w10_email_or_pass_invalid).showToast(requireContext())
                    }
                }

                progressBar?.visibility = View.INVISIBLE

            }, {
                it.toString().showToast(requireContext())
            })

    }

    private fun handleEdiTextListener() {
        edtEmail?.addTextChangedListener {
            checkEditText()
        }

        edtPassword?.addTextChangedListener {
            checkEditText()
        }
    }

    private fun checkEditText() {
        if (edtEmail.text.isNullOrEmpty() || edtPassword.text.isNullOrEmpty()) {
            btnSignIn?.setBackgroundResource(R.drawable.w10_bg_button_disable)
            btnSignIn?.isEnabled = false
        } else {
            btnSignIn?.setBackgroundResource(R.drawable.w10_bg_select_button)
            btnSignIn?.isEnabled = true
        }
    }

    private fun setupViewModel() {
        viewModel = LoginVM(LoginRepository(requireContext()), LocalRepository(requireContext()))
    }
}
