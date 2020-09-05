package com.asiantech.intern20summer1.fragment.w10

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.w10.RecyclerViewNewFeed
import com.asiantech.intern20summer1.api.w10.ClientAPI
import com.asiantech.intern20summer1.api.w10.ErrorUtils
import com.asiantech.intern20summer1.extension.hideKeyboard
import com.asiantech.intern20summer1.extension.isValidEmail
import com.asiantech.intern20summer1.extension.isValidPasswordW10
import com.asiantech.intern20summer1.fragment.w10.FragmentRegister.Companion.EMAIL_LENGTH
import com.asiantech.intern20summer1.model.w10.UserAutoSignIn
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_login.*
import retrofit2.Call
import retrofit2.Response

class FragmentLogin : Fragment() {
    private var isUserNameValid = false
    private var isPassWordValid = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_login, container, false)
    }

    internal fun setBackgroundButton() {
        if (btnLogin.isEnabled) {
            btnLogin?.setBackgroundResource(R.drawable.bg_button_login)
        } else {
            btnLogin?.setBackgroundResource(R.drawable.bg_button_dis_enable)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initEmail()
        initPassword()
    }

    private fun initListener() {
        initTextViewRegister()
        initButtonLogin()
        initListenerHideKeyboardLogin()
    }

    private fun initTextViewRegister() {
        tvCreateNewAccount?.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.add(R.id.frContainer, FragmentRegister().apply {
                onRegisterSuccess = { email, password ->
                    this@FragmentLogin.edtEmail.setText(email)
                    this@FragmentLogin.edtPassword.setText(password)
                }
            })?.addToBackStack(null)?.hide(this)?.commit()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListenerHideKeyboardLogin() {
        scrViewLogin?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun initButtonLogin() {
        btnLogin?.setOnClickListener {
            val email = edtEmail?.text.toString()
            val password = edtPassword.text.toString()
            val call = ClientAPI.createUserService()?.login(email, password)
            call?.enqueue(object : retrofit2.Callback<UserAutoSignIn> {
                override fun onResponse(
                    call: Call<UserAutoSignIn>,
                    response: Response<UserAutoSignIn>
                ) {
                    if (response.isSuccessful) {
                        response.body().apply {
                            this?.let { it1 -> initPutDataLogin(it1) }
                        }
                    } else {
                        val error = ErrorUtils().parseError(response)
                        if (error?.message == ClientAPI.MESSAGE_LOGIN_INCORRECT) {
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.register_error),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }

                override fun onFailure(call: Call<UserAutoSignIn>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun initPutDataLogin(user: UserAutoSignIn) {
        Intent(activity, RecyclerViewNewFeed::class.java).apply {
            Bundle().let {
                addBundleAccount(it, user)
                putExtras(it)
                initStartActivity(this)
            }
        }
    }

    private fun initStartActivity(intent: Intent) {
        startActivity(intent)
        activity?.finish()
    }

    private fun addBundleAccount(bundle: Bundle, user: UserAutoSignIn) {
        val id = user.id
        val email = user.email
        val full_name = user.full_name
        val token = user.token
        val accountData =
            token.let { it1 ->
                full_name.let { it2 ->
                    email.let { it3 ->
                        id.let { it4 ->
                            UserAutoSignIn(
                                it4, it3, it2,
                                it1
                            )
                        }
                    }
                }
            }
        bundle.putSerializable("data", accountData)
    }

    private fun initEmail() {
        edtEmail.addTextChangedListener {
            isUserNameValid =
                edtEmail.text.toString()
                    .isValidEmail() && edtEmail.text.toString().length <= EMAIL_LENGTH
            btnLogin.isEnabled = isUserNameValid && isPassWordValid
            setBackgroundButton()
        }
    }

    private fun initPassword() {
        edtPassword?.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                btnLogin?.isEnabled = false
            } else {
                isPassWordValid = edtPassword.text.toString().isValidPasswordW10()
                btnLogin?.isEnabled = isPassWordValid && isUserNameValid
                setBackgroundButton()
            }
        }
    }
}
