package com.asiantech.intern20summer1.w10.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.HomeActivity
import com.asiantech.intern20summer1.w10.activity.LoginActivity
import com.asiantech.intern20summer1.w10.api.APIClient
import com.asiantech.intern20summer1.w10.api.UserAPI
import com.asiantech.intern20summer1.w10.data.User
import com.asiantech.intern20summer1.w10.extension.isValidEmail
import com.asiantech.intern20summer1.w10.extension.isValidPassword
import com.asiantech.intern20summer1.w10.fragment.RegisterFragment.Companion.KEY_VALUE_EMAIL
import com.asiantech.intern20summer1.w10.fragment.RegisterFragment.Companion.KEY_VALUE_PASSWORD
import kotlinx.android.synthetic.`at-sonnguyen`.w10_fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment(){
    private var emailText: String = ""
    private var passwordText: String = ""

    companion object{
        internal fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_login,container,false)
    }

    override fun onResume() {
        super.onResume()
        setEnableSignInButton()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromRegisterFragment()
        initListener()
    }

    private fun initListener(){
        handleEmailEditTextListener()
        handlePasswordEditTextListener()
        setEnableSignInButton()
        handleRegisterTextViewListener()
        handleLoginButtonListener()
    }

    private fun handleEmailEditTextListener(){
        edtEmailLogin.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (isValidEmail(p0.toString())) {
                    edtEmailLogin.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    emailText = p0.toString()
                } else {
                    edtEmailLogin.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    emailText = ""
                }
            }

        })
    }

    private fun handleRegisterTextViewListener() {
        tvRegister.setOnClickListener {
            (activity as? LoginActivity)?.replaceFragment(RegisterFragment?.newInstance())
        }
    }

    private fun handleLoginButtonListener() {
        btnLogin?.setOnClickListener {
            val service = APIClient.createServiceClient()?.create(UserAPI::class.java)
            val call =
                service?.login(edtEmailLogin.text.toString(), edtPasswordLogin.text.toString())
            call?.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        response.body().apply {
                            val intent = Intent(activity, HomeActivity::class.java)
                            intent.putExtra("Email", this?.email)
                            activity?.startActivity(intent)
                            activity?.finish()
                            Log.d("TAG0000", "onResponse: success")
                        }
                    } else {
                        Log.d("TAG0000", "onResponse: failure ${edtEmailLogin.text.toString()} ")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("TAG0000", "onFailure: Login Failure ")
                }
            })
        }
    }

    private fun getDataFromRegisterFragment() {
        (arguments?.getString(KEY_VALUE_EMAIL))?.let {
            edtEmailLogin.setText(it)
        }
        (arguments?.getString(KEY_VALUE_PASSWORD))?.let {
            edtPasswordLogin.setText(it)
        }
    }

    private fun handlePasswordEditTextListener(){
        edtPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isValidPassword(p0.toString())) {
                    edtPasswordLogin.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    passwordText = p0.toString()
                    setEnableSignInButton()
                } else {
                    edtPasswordLogin.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    passwordText = ""
                    setEnableSignInButton()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun setEnableSignInButton() {
        btnLogin.isEnabled = isValidEmail(edtEmailLogin.text.toString()) && isValidPassword(edtPasswordLogin.text.toString())
    }
}
