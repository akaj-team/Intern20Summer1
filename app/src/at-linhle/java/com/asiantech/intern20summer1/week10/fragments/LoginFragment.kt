package com.asiantech.intern20summer1.week10.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.api.ApiClient
import com.asiantech.intern20summer1.week10.models.User
import com.asiantech.intern20summer1.week10.views.HomeApiActivity
import kotlinx.android.synthetic.`at-linhle`.fragment_api_login.*
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class LoginFragment : Fragment() {
    companion object {
        internal const val KEY_STRING_FULL_NAME = "fullName"
        internal fun newInstance() = LoginFragment()
    }

    private val passwordPattern = Pattern.compile("""^(?=.*).{8,16}$""")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_api_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClickingRegisterTextView()
        handleLoginEmailTextChanged()
        handleLoginPasswordTextChanged()
        handleClickingLoginButton()
    }

    private fun isSignUpPasswordValid(password: String) =
        passwordPattern.matcher(password).matches()

    private fun isSignUpEmailValid(email: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length <= 264

    private fun isCorrectFormat(
        email: String,
        password: String
    ) = isSignUpEmailValid(email) && isSignUpPasswordValid(password)

    private fun setBackGroundLoginButton() {
        if (btnLogin.isEnabled) {
            btnLogin?.setBackgroundResource(R.drawable.bg_register_button_enabled)
        } else {
            btnLogin?.setBackgroundResource(R.drawable.bg_register_button_disabled)
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

    private fun handleClickingLoginButton() {
        btnLogin?.setOnClickListener {
            val email = edtEmail?.text.toString()
            val password = edtPassword?.text.toString()
            val callApi = ApiClient.createUserService()?.login(email, password)
            callApi?.enqueue(object : retrofit2.Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        response.body().apply {
                            val intent = Intent(activity, HomeApiActivity::class.java)
                            intent.putExtra(KEY_STRING_FULL_NAME, this?.email)
                            activity?.startActivity(intent)
                            activity?.finish()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Email or password incorrect!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun handleClickingRegisterTextView() {
        tvRegister.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.add(R.id.flLoginContainer, RegisterFragment.newInstance().apply {
                onRegisterSuccess = { email, password ->
                    this@LoginFragment.edtEmail.setText(email)
                    this@LoginFragment.edtPassword.setText(password)
                }
            })?.addToBackStack(null)?.hide(this)?.commit()
        }
    }
}
