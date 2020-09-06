package com.asiantech.intern20summer1.w10.fragment

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.api.Api
import com.asiantech.intern20summer1.w10.api.ApiAccountService
import com.asiantech.intern20summer1.w10.api.ErrorUtils
import com.asiantech.intern20summer1.w10.models.Account
import com.asiantech.intern20summer1.w10.models.RequestAccount
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_sign_up.*
import retrofit2.Call
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is SignInFragment class. It is fragment to display sign up page
 */

class SignUpFragment : Fragment() {

    companion object {
        private const val MAX_LENGTH_EMAIL = 65
        private const val REGEX_PASSWORD = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,16}$"""
        internal fun newInstance() = SignUpFragment()
    }

    internal var onRegisterClick: (account: RequestAccount) -> Unit = {}

    private var callApi: ApiAccountService? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callApi = Api.getInstance()?.create(ApiAccountService::class.java)
        return inflater.inflate(R.layout.w10_fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        btnBack?.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        btnRegister?.setOnClickListener {
            registerAccount()
        }

        handleForEditText()
    }

    private fun handleForEditText() {
        edtEmail?.addTextChangedListener {
            checkEditText()
        }
        edtPassword?.addTextChangedListener {
            checkEditText()
        }
        edtEmail?.addTextChangedListener {
            checkEditText()
        }
        edtRePass?.addTextChangedListener {
            checkEditText()
        }
    }

    private fun checkEditText() {
        if (isCheckEmail() && isCheckName() && isCheckPassword()) {
            btnRegister?.setBackgroundResource(R.drawable.w10_bg_select_button)
            btnRegister?.isEnabled = true
            d("button", "is Enabled: true")
        } else {
            btnRegister?.setBackgroundResource(R.drawable.w10_bg_button_disable)
            btnRegister?.isEnabled = false
            d("button", "is Enabled: false")
        }
    }

    private fun isCheckEmail() =
        PatternsCompat.EMAIL_ADDRESS.matcher(edtEmail?.text.toString()).matches()

    private fun isCheckPassword(): Boolean {
        val isCheck = edtPassword.text.toString().matches(REGEX_PASSWORD.toRegex())
        val isMatch = edtPassword.text.toString() == edtRePass.text.toString()
        d("button", "ischeck : $isCheck | isMatch : $isMatch")
        return isCheck && isMatch
    }

    private fun isCheckName(): Boolean {
        val name = edtFullName.text.toString()
        return (name.isNotEmpty()) && (name.length < MAX_LENGTH_EMAIL)
    }

    private fun registerAccount() {
        progressBar?.visibility = View.VISIBLE
        val email = edtEmail.text.toString()
        val fullName = edtFullName.text.toString()
        val password = edtPassword.text.toString()
        val requestAccount = RequestAccount(email, password, fullName)
        callApi?.createUser(requestAccount)
            ?.enqueue(object : retrofit2.Callback<Account> {
                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.isSuccessful) {
                        showToast("Đăng ký thành công")
                        response.body()?.let { onRegisterClick.invoke(requestAccount) }
                        fragmentManager?.popBackStack()
                    } else {
                        val error = ErrorUtils().parseError(response)
                        if (error?.message ==
                            Api.MESSAGE_EMAIL_HAS_BEEN_TAKEN
                        ) {
                            showToast("Tài khoản Email đã tồn tại")
                        }
                    }
                    progressBar?.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {}
            })
    }

    private fun showToast(any: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), any.toString(), duration).show()
    }
}
