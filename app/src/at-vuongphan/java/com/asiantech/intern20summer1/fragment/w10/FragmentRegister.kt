package com.asiantech.intern20summer1.fragment.w10

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.w10.ActivityLogin
import com.asiantech.intern20summer1.api.ClientAPI
import com.asiantech.intern20summer1.api.UserClient
import com.asiantech.intern20summer1.extension.hideKeyboard
import com.asiantech.intern20summer1.extension.isValidEmail
import com.asiantech.intern20summer1.extension.isValidPasswordW10
import com.asiantech.intern20summer1.model.UserAutoSignIn
import com.asiantech.intern20summer1.model.UserRegister
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_register.*
import retrofit2.Response

class FragmentRegister : Fragment() {
    private var isEmailValid = false
    private var isPassWordValid = false
    private var isUserNameValid = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListenerHideKeyboardLogin()
        initListener()
        addNewUser()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListenerHideKeyboardLogin() {
        scrViewRegister?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun addNewUser() {
        btnRegister?.setOnClickListener {
            val userName = edtUserName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val service = ClientAPI.createServiceClient()?.create(UserClient::class.java)
            val call = service?.addNewUserRegister(UserRegister(0, email, password, userName))
            call?.enqueue(object : retrofit2.Callback<UserAutoSignIn> {
                override fun onFailure(call: retrofit2.Call<UserAutoSignIn>, t: Throwable) {
                    t.message?.let { it1 -> NewFeedFragment().displayErrorDialog(it1) }
                }

                override fun onResponse(
                    call: retrofit2.Call<UserAutoSignIn>,
                    response: Response<UserAutoSignIn>
                ) {
                }
            })
            (activity as ActivityLogin).openFragment(FragmentLogin())
        }
    }

    private fun initListener() {
        initEmail()
        initPassword()
        initUserName()
    }

    private fun initEmail() {
        edtEmail.addTextChangedListener {
            isEmailValid = edtEmail.text.toString().isValidEmail()
            btnRegister.isEnabled = isEmailValid && isPassWordValid && isUserNameValid
        }
    }

    private fun initPassword() {
        edtPassword?.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                btnRegister?.isEnabled = false
            } else {
                isPassWordValid = edtPassword.text.toString().isValidPasswordW10()
                btnRegister?.isEnabled = isPassWordValid && isUserNameValid && isEmailValid
            }
        }
    }

    private fun initUserName() {
        edtUserName?.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                btnRegister?.isEnabled = false
            } else {
                isUserNameValid = true
                btnRegister?.isEnabled = isEmailValid && isPassWordValid && isUserNameValid
            }
        }
    }
}
