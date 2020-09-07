package com.asiantech.intern20summer1.w10.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.ApiMainActivity
import com.asiantech.intern20summer1.w10.api.Api
import com.asiantech.intern20summer1.w10.api.ApiAccountService
import com.asiantech.intern20summer1.w10.api.ErrorUtils
import com.asiantech.intern20summer1.w10.models.Account
import com.asiantech.intern20summer1.w10.utils.AppUtils
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_sign_in.*
import retrofit2.Response

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

    private var callApi: ApiAccountService? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callApi = Api.getInstance()?.create(ApiAccountService::class.java)
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
        callApi?.login(email, password)?.enqueue(object : retrofit2.Callback<Account> {
            override fun onResponse(call: retrofit2.Call<Account>, response: Response<Account>) {
                if (response.isSuccessful) {
                    response.body()?.let { account ->
                        AppUtils().putIsLogin(requireContext(), true)
                        AppUtils().putToken(requireContext(), account.token)
                        AppUtils().putIdUser(requireContext(), account.id)
                        (activity as ApiMainActivity).replaceFragment(
                            HomeFragment.newInstance()
                        )
                    }
                } else {
                    val error = ErrorUtils().parseError(response)
                    if (error?.message == Api.MESSAGE_LOGIN_INCORRECT) {
                        AppUtils().showToast(requireContext(),
                            getString(R.string.w10_email_or_pass_invalid))
                    }
                }

                progressBar?.visibility = View.INVISIBLE
            }

            override fun onFailure(call: retrofit2.Call<Account>, t: Throwable) {
            }
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
}
