package com.asiantech.intern20summer1.fragment.w10

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
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
    companion object {
        internal const val EMAIL_LENGTH = 264
        internal const val FULL_NAME_LENGTH = 64
    }

    internal var onRegisterSuccess: (email: String, password: String) -> Unit = { _, _ -> }
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
            val call = service?.addNewUserRegister(UserRegister(email, password, userName))
            call?.enqueue(object : retrofit2.Callback<UserAutoSignIn> {
                override fun onFailure(call: retrofit2.Call<UserAutoSignIn>, t: Throwable) {
                    // t.message?.let { it1 -> NewFeedFragment().displayErrorDialog(it1) }
                    Toast.makeText(requireContext(), "that bai", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: retrofit2.Call<UserAutoSignIn>,
                    response: Response<UserAutoSignIn>
                ) {
                }
            })
            onRegisterSuccess(email, password)
            activity?.onBackPressed()
        }
    }

    private fun initListener() {
        initEmail()
        initPassword()
        initUserName()
    }

    internal fun setBackgroundButton() {
        if (btnRegister.isEnabled) {
            btnRegister?.setBackgroundResource(R.drawable.bg_button_login)
        } else {
            btnRegister?.setBackgroundResource(R.drawable.bg_button_dis_enable)
        }
    }

    private fun initEmail() {
        edtEmail.addTextChangedListener {
            isEmailValid =
                edtEmail.text.toString()
                    .isValidEmail() && edtEmail.text.toString().length <= EMAIL_LENGTH
            btnRegister.isEnabled = isEmailValid && isPassWordValid && isUserNameValid
            setBackgroundButton()
        }
    }

    private fun initPassword() {
        edtPassword?.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                btnRegister?.isEnabled = false
            } else {
                isPassWordValid = edtPassword.text.toString().isValidPasswordW10()
                btnRegister?.isEnabled = isPassWordValid && isUserNameValid && isEmailValid
                setBackgroundButton()
            }
        }
    }

    private fun initUserName() {
        edtUserName?.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                btnRegister?.isEnabled = false
            } else {
                isUserNameValid = edtUserName.text.toString().length <= FULL_NAME_LENGTH
                btnRegister?.isEnabled = isEmailValid && isPassWordValid && isUserNameValid
                setBackgroundButton()
            }
        }
    }
}
