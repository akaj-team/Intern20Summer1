package com.asiantech.intern20summer1.w10.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.api.APIClient
import com.asiantech.intern20summer1.w10.api.UserAPI
import com.asiantech.intern20summer1.w10.data.User
import com.asiantech.intern20summer1.w10.data.UserRegister
import com.asiantech.intern20summer1.w10.extension.isValidEmail
import com.asiantech.intern20summer1.w10.extension.isValidPassword
import com.example.demo_week_10.fragment.LoginFragment
import kotlinx.android.synthetic.`at-sonnguyen`.w10_fragment_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {
    private var emailText = ""
    private var fullNameText = ""
    private var passwordText = ""
    private var checkFullName = false

    companion object {
        internal fun newInstance() = RegisterFragment()
        internal const val KEY_VALUE_EMAIL = "email_key"
        internal const val KEY_VALUE_PASSWORD = "password_key"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        handleEmailEditTextListener()
        handleFullNameEditTextListener()
        handlePasswordEditTextListener()
        setEnableRegisterButton()
        handleRegisterButtonListener()
    }

    private fun setEnableRegisterButton() {
        btnRegister.isEnabled =
            isValidEmail(emailText) && isValidPassword(passwordText) && checkFullName
    }

    private fun handleEmailEditTextListener() {
        edtEmailRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (isValidEmail(p0.toString())) {
                    edtEmailRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    emailText = p0.toString()
                    setEnableRegisterButton()
                } else {
                    edtEmailRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    emailText = ""
                    setEnableRegisterButton()
                }
            }
        })
    }

    private fun handleRegisterButtonListener(){
        btnRegister.setOnClickListener {
            val service = APIClient.createServiceClient()?.create(UserAPI::class.java)
            val call = service?.addUser(UserRegister(emailText,passwordText,fullNameText))
            Log.d("TAG0000", "handleRegisterButtonListener: $emailText")
            Log.d("TAG0000", "handleRegisterButtonListener: $passwordText")
            Log.d("TAG0000", "handleRegisterButtonListener: $fullNameText")
            call?.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Log.d("TAG0000", "onResponse: success")
                    } else {
                        Log.d("TAG0000", "handleRegisterButtonListener1: $emailText")
                        Log.d("TAG0000", "handleRegisterButtonListener1: $passwordText")
                        Log.d("TAG0000", "handleRegisterButtonListener1: $fullNameText")
                        Log.d("TAG0000", "onResponse: failure")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("TAG0000", "onFailure: ")
                }
            })
            sendDataToLoginFragment()
        }
    }

    private fun sendDataToLoginFragment(){
        val bundle = Bundle()
        bundle.putString(KEY_VALUE_EMAIL,emailText)
        bundle.putString(KEY_VALUE_PASSWORD,passwordText)
        val loginFragment = LoginFragment.newInstance()
        val fragmentTransaction : FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        loginFragment.arguments = bundle
        fragmentTransaction?.replace(R.id.frameLayoutMain,loginFragment)
        fragmentManager?.popBackStack()
        fragmentTransaction?.commit()
    }

    private fun handlePasswordEditTextListener() {
        edtPasswordRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isValidPassword(p0.toString())) {
                    edtPasswordRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    passwordText = p0.toString()
                    setEnableRegisterButton()
                } else {
                    edtPasswordRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    passwordText = ""
                    setEnableRegisterButton()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun handleFullNameEditTextListener() {
        edtFullNameRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isEmpty()) {
                    edtFullNameRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    fullNameText = ""
                    checkFullName = false
                    setEnableRegisterButton()
                } else {
                    edtFullNameRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    fullNameText = p0.toString()
                    checkFullName = true
                    setEnableRegisterButton()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }
}
