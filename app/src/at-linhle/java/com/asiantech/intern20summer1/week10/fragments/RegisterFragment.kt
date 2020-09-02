package com.asiantech.intern20summer1.week10.fragments

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
import com.asiantech.intern20summer1.week10.models.UserRegister
import kotlinx.android.synthetic.`at-linhle`.fragment_api_register.*
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class RegisterFragment : Fragment() {
    companion object {
        internal fun newInstance() = RegisterFragment()
    }

    internal var onRegisterSuccess: (email: String, password: String) -> Unit = { _, _ -> }
    private val passwordPattern = Pattern.compile("""^(?=.*).{8,16}$""")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_api_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClickingArrowBack()
        handleClickingRegisterButton()
        handleRegisterEmailTextChanged()
        handleRegisterFullNameTextChanged()
        handleRegisterPasswordTextChanged()
    }

    private fun isSignUpFullNameValid(fullName: String) = fullName.length <= 64

    private fun isSignUpPasswordValid(password: String) =
        passwordPattern.matcher(password).matches()

    private fun isSignUpEmailValid(email: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length <= 264

    private fun isCorrectFormat(
        fullName: String,
        email: String,
        password: String
    ) = isSignUpFullNameValid(fullName) && isSignUpEmailValid(email) && isSignUpPasswordValid(
        password
    )

    private fun handleRegisterFullNameTextChanged() {
        edtUserName?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister?.isEnabled = isCorrectFormat(
                p0.toString(),
                edtEmail?.text.toString(),
                edtPassword?.text.toString()
            )
            setBackgroundButton()
        })
    }

    private fun handleRegisterEmailTextChanged() {
        edtEmail?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister?.isEnabled = isCorrectFormat(
                edtUserName?.text.toString(),
                p0.toString(),
                edtPassword?.text.toString()
            )
            setBackgroundButton()
        })
    }

    private fun handleRegisterPasswordTextChanged() {
        edtPassword?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister?.isEnabled = isCorrectFormat(
                edtUserName?.text.toString(),
                edtEmail?.text.toString(),
                p0.toString()
            )
            setBackgroundButton()
        })
    }

    private fun setBackgroundButton() {
        if (btnRegister.isEnabled) {
            btnRegister?.setBackgroundResource(R.drawable.bg_register_button_enabled)
        } else {
            btnRegister?.setBackgroundResource(R.drawable.bg_register_button_disabled)
        }
    }

    private fun handleClickingArrowBack() {
        imgArrowLeft?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun handleClickingRegisterButton() {
        btnRegister?.setOnClickListener {
            val fullName = edtUserName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val callApi =
                ApiClient.createUserService()?.addNewUser(UserRegister(email, password, fullName))
            callApi?.enqueue(object : retrofit2.Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(activity, "Đăng Ký Thất Bại", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        Toast.makeText(activity, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show()
                        onRegisterSuccess(email, password)
                        activity?.onBackPressed()
                    }else {
                        Toast.makeText(activity, "Email Đã Có Người Đăng Ký", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}
