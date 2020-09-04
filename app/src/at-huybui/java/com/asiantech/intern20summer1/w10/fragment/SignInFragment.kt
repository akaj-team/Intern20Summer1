package com.asiantech.intern20summer1.w10.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.ApiMainActivity
import com.asiantech.intern20summer1.w10.api.Api
import com.asiantech.intern20summer1.w10.api.ApiAccountService
import com.asiantech.intern20summer1.w10.api.ErrorUtils
import com.asiantech.intern20summer1.w10.models.Account
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_sign_in.*
import retrofit2.Response

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

    private fun initView(){
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
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        callApi?.login(email, password)?.enqueue(object : retrofit2.Callback<Account> {
            override fun onResponse(call: retrofit2.Call<Account>, response: Response<Account>) {
                if (response.isSuccessful) {
                    response.body()?.let { account ->
                        val preference = requireContext().getSharedPreferences(
                            SplashFragment.NAME_PREFERENCE,
                            MODE_PRIVATE
                        )
                        preference.edit().putBoolean(SplashFragment.KEY_IS_LOGIN, true).apply()
                        preference.edit().putString(SplashFragment.KEY_TOKEN, account.token)
                            .apply()
                        (activity as ApiMainActivity).replaceFragment(
                            HomeFragment.newInstance(account)
                        )
                    }
                } else {
                    val error = ErrorUtils().parseError(response)
                    if (error?.message == Api.MESSAGE_LOGIN_INCORRECT) {
                        showToast("Tài khoản hoặc mật khẩu không đúng")
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<Account>, t: Throwable) {
            }
        })
    }

    private fun handleEdiTextListener(){
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
            d("button", "is Enabled: false")
        } else {
            btnSignIn?.setBackgroundResource(R.drawable.w10_bg_select_button)
            btnSignIn?.isEnabled = true
            d("button", "is Enabled: true")
        }
    }

    private fun showToast(any: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), any.toString(), duration).show()
    }
}
