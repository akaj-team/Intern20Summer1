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
import com.asiantech.intern20summer1.w10.models.Account
import com.asiantech.intern20summer1.w10.models.RequestAccount
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_sign_up.*
import retrofit2.Call
import retrofit2.Response

class SignUpFragment : Fragment() {

    companion object {
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
        callApi = Api().newInstance()?.create(ApiAccountService::class.java)
        return inflater.inflate(R.layout.w10_fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        btnBack_SignUp_w10?.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        btnRegister_SignUp_w10?.setOnClickListener {
            registerAccount()
        }

        handleForEditText()
    }

    private fun handleForEditText() {
        edtEmail_SignUp_w10?.addTextChangedListener {
            checkEditText()
        }
        edtPassword_SignUp_w10?.addTextChangedListener {
            checkEditText()
        }
        edtEmail_SignUp_w10?.addTextChangedListener {
            checkEditText()
        }
        edtRePass_SignUp_w10?.addTextChangedListener {
            checkEditText()
        }
    }

    private fun checkEditText() {
        if (isCheckEmail() && isCheckName() && isCheckPassword()) {
            btnRegister_SignUp_w10?.setBackgroundResource(R.drawable.w10_bg_select_button)
            btnRegister_SignUp_w10?.isEnabled = true
            d("button", "is Enabled: true")
        } else {
            btnRegister_SignUp_w10?.setBackgroundResource(R.drawable.w10_bg_button_disable)
            btnRegister_SignUp_w10?.isEnabled = false
            d("button", "is Enabled: false")
        }
    }

    private fun isCheckEmail() =
        PatternsCompat.EMAIL_ADDRESS.matcher(edtEmail_SignUp_w10?.text.toString()).matches()

    private fun isCheckPassword(): Boolean {
        val isCheck = edtPassword_SignUp_w10.text.toString().matches(REGEX_PASSWORD.toRegex())
        val isMatch = edtPassword_SignUp_w10.text.toString() == edtRePass_SignUp_w10.text.toString()
        d("button", "ischeck : $isCheck | isMatch : $isMatch")
        return isCheck && isMatch
    }

    private fun isCheckName(): Boolean {
        val name = edtFullName_SignUp_w10.text.toString()
        return (name.isNotEmpty()) && (name.length < 65)
    }

    private fun registerAccount() {
        val email = edtEmail_SignUp_w10.text.toString()
        val fullName = edtFullName_SignUp_w10.text.toString()
        val password = edtPassword_SignUp_w10.text.toString()
        val requestAccount = RequestAccount(email, password, fullName)
        callApi?.createUser(requestAccount)
            ?.enqueue(object : retrofit2.Callback<Account> {
                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.body() != null) {
                        showToast("Đăng ký thành công")
                        response.body()?.let { onRegisterClick.invoke(requestAccount) }
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {}
            })
    }

    private fun showToast(any: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), any.toString(), duration).show()
    }
}
