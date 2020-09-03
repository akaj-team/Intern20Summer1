package com.asiantech.intern20summer1.w10.fragment

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
import com.asiantech.intern20summer1.w10.models.Account
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_sign_in.*
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_sign_up.*
import retrofit2.Call
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
        callApi = Api().newInstance()?.create(ApiAccountService::class.java)
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
        tvRegister_SignIn_w10?.setOnClickListener {
            val fragment = SignUpFragment.newInstance()
            fragment.onRegisterClick = { account ->
                edtEmail_SignIn_w10.setText(account.email)
                edtPassword_SignIn_w10.setText(account.password)
            }
            (activity as ApiMainActivity).addFragment(fragment, true)
        }

        btnSignIn_SignIn_w10?.setOnClickListener {
            isCheckAccount()
        }
    }

    private fun isCheckAccount() {
        val email = edtEmail_SignIn_w10.text.toString()
        val password = edtPassword_SignIn_w10.text.toString()
        callApi?.login(email, password)?.enqueue(object : retrofit2.Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                if (response.body() != null) {
                    response.body()?.let {
                        (activity as ApiMainActivity).replaceFragment(
                            HomeFragment.newInstance(it)
                        )
                    }
                } else {
                    d("signin", response.body().toString())
                    showToast("Tài khoản hoặc mật khẩu không đúng")
                }
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
            }
        })
    }

    private fun handleEdiTextListener(){
        edtEmail_SignIn_w10?.addTextChangedListener {
            checkEditText()
        }

        edtPassword_SignIn_w10?.addTextChangedListener {
            checkEditText()
        }
    }

    private fun checkEditText() {
        if (edtEmail_SignIn_w10.text.isNullOrEmpty() || edtPassword_SignIn_w10.text.isNullOrEmpty()) {
            btnSignIn_SignIn_w10?.setBackgroundResource(R.drawable.w10_bg_button_disable)
            btnSignIn_SignIn_w10?.isEnabled = false
            d("button", "is Enabled: false")
        } else {
            btnSignIn_SignIn_w10?.setBackgroundResource(R.drawable.w10_bg_select_button)
            btnSignIn_SignIn_w10?.isEnabled = true
            d("button", "is Enabled: true")
        }
    }

    private fun showToast(any: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), any.toString(), duration).show()
    }
}
